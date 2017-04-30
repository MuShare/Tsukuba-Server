package org.mushare.tsukuba.component;

import net.sf.json.JSONObject;
import org.mushare.common.util.JsonTool;
import org.mushare.tsukuba.component.config.Global;
import org.mushare.tsukuba.component.config.Mail;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ConfigComponent {

    public static final String ConfigPath = "/WEB-INF/config.json";

    public String rootPath;
    public JsonTool configTool = null;
    public Mail mail;
    public Global global;

    public ConfigComponent() {
        rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
        load();
    }

    public void load() {
        String pathname = rootPath + ConfigPath;
        configTool = new JsonTool(pathname);
        global = new Global(configTool.getJSONObject("global"));
        mail = new Mail(configTool.getJSONObject("mail"));
    }

}
