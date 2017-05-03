package org.mushare.tsukuba.service.impl;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.SelectionBean;
import org.mushare.tsukuba.domain.Category;
import org.mushare.tsukuba.domain.Selection;
import org.mushare.tsukuba.service.SelectionManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RemoteProxy(name = "SelectionManager")
public class SelectionManagerImpl extends ManagerTemplate implements SelectionManager {

    @RemoteMethod
    public List<SelectionBean> getAll() {
        List<SelectionBean> selectionBeans = new ArrayList<SelectionBean>();
        for (Selection selection : selectionDao.findAll("createAt", true)) {
            selectionBeans.add(new SelectionBean(selection));
        }
        return selectionBeans;
    }

    @RemoteMethod
    @Transactional
    public Result create(String identifier, String cid, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Category category = categoryDao.get(cid);
        if (category == null) {
            Debug.error("Can not find the category by cid");
            return Result.ObjectIdError;
        }
        Selection selection = new Selection();
        selection.setCreateAt(System.currentTimeMillis());
        selection.setIdentifier(identifier);
        selection.setActive(false);
        selection.setEnable(false);
        selection.setCategory(category);
        if (selectionDao.save(selection) == null) {
            Debug.error("Selection save failed");
            return Result.SaveInternalError;
        }
        return Result.Success;
    }
}
