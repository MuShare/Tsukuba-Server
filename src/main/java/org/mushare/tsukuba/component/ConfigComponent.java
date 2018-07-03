package org.mushare.tsukuba.component;

import org.mushare.common.util.FileTool;
import org.mushare.common.util.JsonTool;
import org.mushare.tsukuba.component.config.APNs;
import org.mushare.tsukuba.component.config.Facebook;
import org.mushare.tsukuba.component.config.Global;
import org.mushare.tsukuba.component.config.Mail;
import org.springframework.stereotype.Component;

@Component
public class ConfigComponent {

    public static final String ConfigPath = "/WEB-INF/config.json";

    public String DefaultIcon = "/static/images/icon.png";
    public String DefaultAvatar = "/static/images/avatar.png";
    public String CategoryIconPath = "/files/category";
    public String AvatarPath = "/files/avatar";
    public String MessagePicturePath = "/files/picture";
    public String ChatPicturePath = "/WEB-INF/files/picture";

    public String rootPath;
    public JsonTool configTool = null;
    public Mail mail;
    public Global global;
    public Facebook facebook;
    public APNs apns;

    public ConfigComponent() {
        rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
        FileTool.createDirectoryIfNotExsit(rootPath + "/files");
        FileTool.createDirectoryIfNotExsit(rootPath + "/WEB-INF/files");
        FileTool.createDirectoryIfNotExsit(rootPath + CategoryIconPath);
        FileTool.createDirectoryIfNotExsit(rootPath + AvatarPath);
        FileTool.createDirectoryIfNotExsit(rootPath + MessagePicturePath);
        FileTool.createDirectoryIfNotExsit(rootPath + ChatPicturePath);
        load();
    }

    public void load() {
        String pathname = rootPath + ConfigPath;
        configTool = new JsonTool(pathname);
        global = new Global(configTool.getJSONObject("global"));
        mail = new Mail(configTool.getJSONObject("mail"));
        facebook = new Facebook(configTool.getJSONObject("facebook"));
        apns = new APNs(configTool.getJSONObject("apns"));
    }

}
