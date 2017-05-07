package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.SelectionBean;
import org.mushare.tsukuba.service.common.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface SelectionManager {

    /**
     * Get all selections in the category of cid.
     *
     * @return
     */
    List<SelectionBean> getByCid(String cid);

    /**
     * Get activied selections by revision.
     *
     * @param rev
     * @return
     */
    List<SelectionBean> getActivedByRev(int rev);

    /**
     * Get a selection by sid.
     *
     * @param sid
     * @return
     */
    SelectionBean get(String sid);

    /**
     * Get current global rev.
     *
     * @return
     */
    int getGlobalRev();

    //******************* Admin *********************

    /**
     * Admin create a new selection.
     *
     * @param identifier
     * @param cid
     * @param session
     * @return
     */
    Result create(String identifier, String cid, HttpSession session);

    /**
     *
     * @param sid
     * @param session
     * @return
     */
    Result active(String sid, HttpSession session);

    /**
     * Admin change enable attribute of a selection.
     *
     * @param sid
     * @param enable
     * @param session
     * @return
     */
    Result enable(String sid, boolean enable, HttpSession session);

    /**
     * Admin remove a selection which is not bind to any option or category.
     *
     * @param sid
     * @param session
     * @return
     */
    Result remove(String sid, HttpSession session);

    /**
     * Admin modify the name JSON string of a selection.
     *
     * @param sid
     * @param name
     * @param session
     * @return
     */
    Result modifyName(String sid, String name, HttpSession session);

}
