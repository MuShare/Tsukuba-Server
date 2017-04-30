package org.mushare.tsukuba.controller.web;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.controller.common.ErrorCode;
import org.mushare.tsukuba.service.CategoryManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/upload")
public class UploadController extends ControllerTemplate {

    @RequestMapping(value = "/icon/category", method = RequestMethod.POST)
    public ResponseEntity uploadIcon(@RequestParam String cid, HttpServletRequest request) {
        if (!checkAdminSession(request.getSession())) {
            return generateBadRequest(ErrorCode.ErrorAdminSession);
        }
        String fileName = upload(request, configComponent.rootPath + CategoryManager.CategoryIconPath);
        final String icon = categoryManager.handleUploadedIcon(cid, fileName);
        if (icon == null) {
            return generateBadRequest(ErrorCode.ErrorObjecId);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("icon", icon);
        }});
    }

}
