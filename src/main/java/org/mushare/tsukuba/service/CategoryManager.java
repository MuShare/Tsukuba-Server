package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.CategoryBean;
import org.mushare.tsukuba.service.common.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CategoryManager {

    /**
     * Get all categories order by create date.
     *
     * @return
     */
    List<CategoryBean> getAll();

    //******************* Admin *********************

    /**
     * Admin create a new category.
     *
     * @param identifier
     * @param session
     * @return
     */
    Result createCategory(String identifier, HttpSession session);

    /**
     * Admin change enable attribute of a category.
     *
     * @param cid
     * @param enable
     * @param session
     * @return
     */
    Result enable(String cid, boolean enable, HttpSession session);

    /**
     * Admin remove a category which is not bind to any message or selection.
     *
     * @param cid
     * @param session
     * @return
     */
    Result removeCategory(String cid, HttpSession session);

    /**
     * Admin modify the name JSON string of a category.
     *
     * @param cid
     * @param name
     * @param session
     * @return
     */
    Result modifyName(String cid, String name, HttpSession session);

}
