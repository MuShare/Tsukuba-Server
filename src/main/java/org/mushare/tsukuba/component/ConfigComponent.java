package org.mushare.tsukuba.component;

import net.sf.json.JSONObject;
import org.mushare.common.util.JsonTool;
import org.mushare.tsukuba.component.config.Global;
import org.mushare.tsukuba.component.config.Mail;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ConfigComponent {

    public static final String CONFIG_PATH = "WEB-INF/config.json";

    public JsonTool configTool = null;

    public Mail mail;
    public Global global;

    public ConfigComponent() {
        load();
    }

    public void load() {
        String rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
        String pathname = rootPath + File.separator + CONFIG_PATH;
        configTool = new JsonTool(pathname);
        global = new Global(configTool.getJSONObject("global"));
        mail = new Mail(configTool.getJSONObject("mail"));
    }

}
