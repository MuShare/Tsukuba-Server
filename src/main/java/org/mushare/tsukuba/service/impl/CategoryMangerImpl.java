package org.mushare.tsukuba.service.impl;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.mushare.tsukuba.domain.Category;
import org.mushare.tsukuba.service.CategoryManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RemoteProxy(name = "CategoryManager")
public class CategoryMangerImpl extends ManagerTemplate implements CategoryManager {

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

}
