package org.mushare.tsukuba.service.impl;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.OptionBean;
import org.mushare.tsukuba.bean.SelectionBean;
import org.mushare.tsukuba.domain.Category;
import org.mushare.tsukuba.domain.Option;
import org.mushare.tsukuba.domain.Selection;
import org.mushare.tsukuba.service.OptionManager;
import org.mushare.tsukuba.service.SelectionManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RemoteProxy(name = "OptionManager")
public class OptionManagerImpl extends ManagerTemplate implements OptionManager {

    @RemoteMethod
    public List<OptionBean> getBySid(String sid) {
        Selection selection = selectionDao.get(sid);
        if (selection == null) {
            Debug.error("Can not find a selection by the sid.");
            return null;
        }
        List<OptionBean> optionBeans = new ArrayList<OptionBean>();
        for (Option option : optionDao.findBySelection(selection)) {
            optionBeans.add(new OptionBean(option));
        }
        return optionBeans;
    }

    public List<OptionBean> getActivedByRev(int rev) {
        List<OptionBean> optionBeans = new ArrayList<OptionBean>();
        for (Option option : optionDao.findActivedByRev(rev)) {
            optionBeans.add(new OptionBean(option));
        }
        return optionBeans;
    }

    @RemoteMethod
    public OptionBean get(String oid) {
        Option option = optionDao.get(oid);
        if (option == null) {
            return null;
        }
        return new OptionBean(option);
    }

    public int getGlobalRev() {
        return optionDao.getMaxRev();
    }

    @RemoteMethod
    @Transactional
    public Result create(String identifier, String sid, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Selection selection = selectionDao.get(sid);
        if (selection == null) {
            Debug.error("Can not find the selection by sid");
            return Result.ObjectIdError;
        }
        Option option = new Option();
        option.setCreateAt(System.currentTimeMillis());
        option.setIdentifier(identifier);
        option.setActive(false);
        option.setEnable(false);
        option.setPriority(0);
        option.setSelection(selection);
        if (optionDao.save(option) == null) {
            Debug.error("Option save failed");
            return Result.SaveInternalError;
        }
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result active(String oid, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Option option = optionDao.get(oid);
        if (option == null) {
            Debug.error("Cannot find a option by this oid.");
            return Result.ObjectIdError;
        }
        option.setActive(true);
        option.setRev(optionDao.getMaxRev() + 1);
        optionDao.update(option);
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result enable(String oid, boolean enable, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Option option = optionDao.get(oid);
        if (option == null) {
            Debug.error("Cannot find a option by this oid.");
            return Result.ObjectIdError;
        }
        option.setEnable(enable);
        optionDao.update(option);
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result remove(String oid, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Option option = optionDao.get(oid);
        if (option == null) {
            Debug.error("Cannot find a option by this oid.");
            return Result.ObjectIdError;
        }
        if (option.getActive()) {
            return Result.OptionRemoveNotAllow;
        }
        optionDao.delete(option);
        return Result.Success;
    }

    @RemoteMethod
    @Transactional
    public Result modifyName(String oid, String name, HttpSession session) {
        if (!checkAdminSession(session)) {
            return Result.SessionError;
        }
        Option option = optionDao.get(oid);
        if (option == null) {
            Debug.error("Cannot find a option by this oid.");
            return Result.ObjectIdError;
        }
        option.setName(name);
        optionDao.save(option);
        return Result.Success;
    }
}
