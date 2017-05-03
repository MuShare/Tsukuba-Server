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
    public List<SelectionBean> getByCid(String cid) {
        Category category = categoryDao.get(cid);
        if (category == null) {
            Debug.error("Can not find a category by the cid.");
            return null;
        }
        List<SelectionBean> selectionBeans = new ArrayList<SelectionBean>();
        for (Selection selection : selectionDao.findByCategory(category)) {
            selectionBeans.add(new SelectionBean(selection));
        }
        return selectionBeans;
    }

    public List<SelectionBean> getActivedByRev(int rev) {
        List<SelectionBean> selectionBeans = new ArrayList<SelectionBean>();
        for (Selection selection : selectionDao.findActivedByRev(rev)) {
            selectionBeans.add(new SelectionBean(selection));
        }
        return selectionBeans;
    }

    @RemoteMethod
    public SelectionBean get(String sid) {
        Selection selection = selectionDao.get(sid);
        if (selection == null) {
            return null;
        }
        return new SelectionBean(selection);
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

    @RemoteMethod
    @Transactional
    public Result active(String sid, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Selection selection = selectionDao.get(sid);
        if (selection == null) {
            Debug.error("Cannot find a selection by this sid.");
            return Result.ObjectIdError;
        }
        selection.setActive(true);
        selection.setRev(selectionDao.getMaxRev() + 1);
        selectionDao.update(selection);
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result enable(String sid, boolean enable, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Selection selection = selectionDao.get(sid);
        if (selection == null) {
            Debug.error("Cannot find a selection by this sid.");
            return Result.ObjectIdError;
        }
        selection.setEnable(enable);
        selectionDao.update(selection);
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result remove(String sid, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Selection selection = selectionDao.get(sid);
        if (selection == null) {
            Debug.error("Cannot find a selection by this sid.");
            return Result.ObjectIdError;
        }
        if (selection.getActive() || optionDao.getCountBySelection(selection) > 0) {
            return Result.SelectionRemoveNotAllow;
        }
        selectionDao.delete(selection);
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result modifyName(String sid, String name, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Selection selection = selectionDao.get(sid);
        if (selection == null) {
            Debug.error("Cannot find a selection by this sid.");
            return Result.ObjectIdError;
        }
        selection.setName(name);
        selectionDao.save(selection);
        return Result.Success;
    }
}
