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

    @RemoteMethod
    public boolean refreshConfig(HttpSession session) {
        if (!checkAdminSession(session)) {
            return false;
        }
        configComponent.load();
        return true;
    }

    @RemoteMethod
    public JSONObject getConfigObject(HttpSession session) {
        if (!checkAdminSession(session)) {
            return null;
        }
        return configComponent.configTool.getJSONConfig();
    }

    @RemoteMethod
    public boolean saveConfig(String jsonString, HttpSession session) {
        if (!checkAdminSession(session)) {
            return false;
        }
        configComponent.configTool.setJSONConfig(JSONObject.fromObject(jsonString));
        configComponent.configTool.writeObject();
        return true;
    }

    @RemoteMethod
    public String[] languages() {
        return configComponent.global.languages;
    }


}
