package org.mushare.tsukuba.service;


import org.mushare.tsukuba.bean.SelectionBean;
import org.mushare.tsukuba.service.common.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface SelectionManager {
    /**
     * Get all selections
     *
     * @return
     */
    List<SelectionBean> getAll();


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


}
