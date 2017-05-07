package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.OptionBean;
import org.mushare.tsukuba.service.common.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OptionManager {

    /**
     * Get all options in the selection of sid.
     *
     * @return
     */
    List<OptionBean> getBySid(String sid);

    /**
     * Get activied options by revision.
     *
     * @param rev
     * @return
     */
    List<OptionBean> getActivedByRev(int rev);

    /**
     * Get a option by oid.
     *
     * @param oid
     * @return
     */
    OptionBean get(String oid);

    /**
     * Get current global rev.
     *
     * @return
     */
    int getGlobalRev();

    //******************* Admin *********************

    /**
     * Admin create a new option.
     *
     * @param identifier
     * @param sid
     * @param session
     * @return
     */
    Result create(String identifier, String sid, HttpSession session);

    /**
     *
     * @param oid
     * @param session
     * @return
     */
    Result active(String oid, HttpSession session);

    /**
     * Admin change enable attribute of a option.
     *
     * @param oid
     * @param enable
     * @param session
     * @return
     */
    Result enable(String oid, boolean enable, HttpSession session);

    /**
     * Admin remove a option which is not bind to any selection.
     *
     * @param oid
     * @param session
     * @return
     */
    Result remove(String oid, HttpSession session);

    /**
     * Admin modify the name JSON string of a option.
     *
     * @param oid
     * @param name
     * @param session
     * @return
     */
    Result modifyName(String oid, String name, HttpSession session);

}
