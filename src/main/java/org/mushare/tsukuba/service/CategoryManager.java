package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.CategoryBean;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CategoryManager {

    //******************* Admin *********************

    /**
     * Admin create a new category.
     *
     * @param identifier
     * @param session
     * @return
     */
    boolean createCategory(String identifier, HttpSession session);

    /**
     * Get all categories order by create date.
     *
     * @return
     */
    List<CategoryBean> getAll();

}
