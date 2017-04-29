package org.mushare.tsukuba.service;

import javax.servlet.http.HttpSession;

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

}
