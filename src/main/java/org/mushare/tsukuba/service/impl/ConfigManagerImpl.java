package org.mushare.tsukuba.service.impl;

import net.sf.json.JSONObject;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.mushare.common.util.JsonTool;
import org.mushare.tsukuba.service.AdminManager;
import org.mushare.tsukuba.service.ConfigManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;

@Service
@RemoteProxy(name = "ConfigManager")
public class ConfigManagerImpl extends ManagerTemplate implements ConfigManager {

    private JsonTool config = null;

    private JsonTool getConfig() {
        if (config == null) {
            loadConfig();
        }
        return config;
    }

    private void loadConfig() {
        String rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
        String pathname = rootPath + File.separator + CONFIG_PATH;
        config = new JsonTool(pathname);
    }

    @RemoteMethod
    public JSONObject getConfigObject(HttpSession session) {
        if (session.getAttribute(AdminManager.ADMIN_FLAG) == null) {
            return null;
        }
        return getConfig().getJSONConfig();
    }

    @RemoteMethod
    public boolean saveConfig(String jsonString, HttpSession session) {
        if (session.getAttribute(AdminManager.ADMIN_FLAG) == null) {
            return false;
        }
        config.setJSONConfig(JSONObject.fromObject(jsonString));
        config.writeObject();
        return true;
    }

}
