package org.mushare.tsukuba.service.impl;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.mushare.common.util.Debug;
import org.mushare.common.util.FileTool;
import org.mushare.tsukuba.bean.CategoryBean;
import org.mushare.tsukuba.domain.Category;
import org.mushare.tsukuba.service.CategoryManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RemoteProxy(name = "CategoryManager")
public class CategoryMangerImpl extends ManagerTemplate implements CategoryManager {

    @RemoteMethod
    public List<CategoryBean> getAll() {
        List<CategoryBean> categoryBeans = new ArrayList<CategoryBean>();
        for (Category category : categoryDao.findAll("createAt", true)) {
            categoryBeans.add(new CategoryBean(category));
        }
        return categoryBeans;
    }

    public List<CategoryBean> getActivedByRev(int rev) {
        List<CategoryBean> categoryBeans = new ArrayList<CategoryBean>();
        for (Category category : categoryDao.findActivedByRev(rev)) {
            categoryBeans.add(new CategoryBean(category));
        }
        return categoryBeans;
    }

    @RemoteMethod
    public CategoryBean get(String cid) {
        Category category = categoryDao.get(cid);
        if (category == null) {
            return null;
        }
        return new CategoryBean(category);
    }

    @RemoteMethod
    @Transactional
    public Result create(String identifier, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Category category = new Category();
        category.setCreateAt(System.currentTimeMillis());
        category.setIdentifier(identifier);
        category.setIcon(configComponent.DefaultIcon);
        category.setEnable(false);
        category.setActive(false);
        if (categoryDao.save(category) == null) {
            return Result.SaveInternalError;
        }
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result active(String cid, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Category category = categoryDao.get(cid);
        if (category == null) {
            Debug.error("Cannot find a category by this cid.");
            return Result.ObjectIdError;
        }
        category.setActive(true);
        category.setRev(categoryDao.getMaxRev() + 1);
        categoryDao.update(category);
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result enable(String cid, boolean enable, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Category category = categoryDao.get(cid);
        if (category == null) {
            Debug.error("Cannot find a category by this cid.");
            return Result.ObjectIdError;
        }
        category.setEnable(enable);
        categoryDao.update(category);
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result remove(String cid, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Category category = categoryDao.get(cid);
        if (category == null) {
            Debug.error("Cannot find a category by this cid.");
            return Result.ObjectIdError;
        }
        if (category.getActive()) {
            return Result.CategoryRemoveNotAllow;
        }
        // Try to delete icon file.
        deleteIconFile(category.getIcon());
        categoryDao.delete(category);
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result modifyName(String cid, String name, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Category category = categoryDao.get(cid);
        if (category == null) {
            Debug.error("Cannot find a category by this cid.");
            return Result.ObjectIdError;
        }
        category.setName(name);
        categoryDao.save(category);
        return Result.Success;
    }

    @Transactional
    public String handleUploadedIcon(String cid, String fileName) {
        Category category = categoryDao.get(cid);
        if (category == null) {
            return null;
        }
        // If category has icon before, try to delete the old icon at first.
        if (category.getIcon() != null) {
            deleteIconFile(category.getIcon());
        }
        String path = configComponent.rootPath + configComponent.CategoryIconPath;
        String newName = UUID.randomUUID().toString() + "." + FileTool.getFormat(fileName);
        FileTool.modifyFileName(path, fileName, newName);
        category.setIcon(configComponent.CategoryIconPath + File.separator + newName);
        categoryDao.update(category);
        return category.getIcon();
    }

    /**
     * Try to delete icon file if icon is not default,.
     *
     * @param icon
     */
    private void deleteIconFile(String icon) {
        if (!icon.equals(configComponent.DefaultIcon)) {
            File file = new File(configComponent.rootPath + icon);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
