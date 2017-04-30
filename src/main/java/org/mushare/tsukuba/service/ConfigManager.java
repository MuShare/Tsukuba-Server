package org.mushare.tsukuba.service;

import net.sf.json.JSONObject;
import org.mushare.common.util.JsonTool;

import javax.servlet.http.HttpSession;

public interface ConfigManager {

    public static final String CONFIG_PATH = "WEB-INF/config.json";

    /**
     *
     * @param session
     * @return
     */
    JSONObject getConfigObject(HttpSession session);

    /**
     *
     * @param jsonString
     * @param session
     * @return
     */
    boolean saveConfig(String jsonString, HttpSession session);
}
