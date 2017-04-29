package org.mushare.tsukuba.service.impl;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.CategoryBean;
import org.mushare.tsukuba.domain.Category;
import org.mushare.tsukuba.service.CategoryManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    @RemoteMethod
    @Transactional
    public boolean createCategory(String identifier, HttpSession session) {
        if (!checkAdminSession(session)) {
            return false;
        }
        Category category = new Category();
        category.setCreateAt(System.currentTimeMillis());
        category.setIdentifier(identifier);
        category.setEnable(false);
        category.setRev(categoryDao.getMaxRev() + 1);
        return categoryDao.save(category) != null;
    }

    @RemoteMethod
    @Transactional
    public boolean enable(String cid, boolean enable, HttpSession session) {
        if (!checkAdminSession(session)) {
            return false;
        }
        Category category = categoryDao.get(cid);
        if (category == null) {
            Debug.error("Cannot find a category by this cid.");
            return false;
        }
        category.setEnable(enable);
        categoryDao.update(category);
        return true;
    }

}
