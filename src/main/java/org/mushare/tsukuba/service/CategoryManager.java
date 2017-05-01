package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.CategoryBean;
import org.mushare.tsukuba.service.common.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CategoryManager {

    public static final String CategoryIconPath = "/files/category";

    /**
     * Get all categories order by create date.
     *
     * @return
     */
    List<CategoryBean> getAll();

    /**
     * Get a category by cid.
     *
     * @param cid
     * @return
     */
    CategoryBean get(String cid);

    //******************* Admin *********************

    /**
     * Admin create a new category.
     *
     * @param identifier
     * @param session
     * @return
     */
    Result create(String identifier, HttpSession session);

    /**
     *
     * @param cid
     * @param session
     * @return
     */
    Result active(String cid, HttpSession session);

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
    Result remove(String cid, HttpSession session);

    /**
     * Admin modify the name JSON string of a category.
     *
     * @param cid
     * @param name
     * @param session
     * @return
     */
    Result modifyName(String cid, String name, HttpSession session);

    /**
     * Generate UUID name for icon and modify name after icon is uploaded.
     *
     * @param cid
     * @param fileName
     * @return
     */
    String handleUploadedIcon(String cid, String fileName);

}
