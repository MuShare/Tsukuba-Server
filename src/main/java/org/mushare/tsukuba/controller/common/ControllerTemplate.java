package org.mushare.tsukuba.controller.common;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.mushare.tsukuba.component.ConfigComponent;
import org.mushare.tsukuba.service.AdminManager;
import org.mushare.tsukuba.service.CategoryManager;
import org.mushare.tsukuba.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ControllerTemplate {

    // Default file upload folder.
    public static final String UploadFolder = "/upload";

    // Limitation of uploaded file.
    private static final int FileMaxSize = 512 * 1024 * 1024;

    @Autowired
    protected ConfigComponent configComponent;

    @Autowired
    protected UserManager userManager;

    @Autowired
    protected CategoryManager categoryManager;

    protected ResponseEntity generateOK(Map<String, Object> result) {
        return generateResponseEntity(result, HttpStatus.OK, null, null);
    }

    protected ResponseEntity generateBadRequest(int errorCode, String errorMessage) {
        return generateResponseEntity(null, HttpStatus.BAD_REQUEST, errorCode, errorMessage);
    }

    protected ResponseEntity generateBadRequest(ErrorCode errorCode) {
        return generateBadRequest(errorCode.code, errorCode.message);
    }

    protected ResponseEntity generateResponseEntity(Map<String, Object> result, HttpStatus status, Integer errCode, String errMsg) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (result != null) {
            data.put("result", result);
        }
        data.put("status", status.value());
        if (errCode != null) {
            data.put("errorCode", errCode);
        }
        if (errMsg != null) {
            data.put("errorMessage", errMsg);
        }
        return new ResponseEntity(data, status);
    }

    public boolean checkAdminSession(HttpSession session) {
        return session.getAttribute(AdminManager.ADMIN_FLAG) != null;
    }

    public String upload(HttpServletRequest request, String filepath) {
        String fileName = null;
        // Create factory object.
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Set cache size to 4KB.
        factory.setSizeThreshold(1024 * 4);
        // Set upload file path.
        factory.setRepository(new File(filepath));
        // Create servlet file upload object.
        ServletFileUpload upload = new ServletFileUpload(factory);
        // Set limitation of uploaded file.
        upload.setSizeMax(FileMaxSize);
        try {
            // Get all uploading files information.
            List<FileItem> list = upload.parseRequest(request);
            Iterator<FileItem> it = list.iterator();
            while (it.hasNext()) {
                FileItem item = it.next();
                if (item.isFormField() == false) {
                    fileName = item.getName();
                    fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length());
                    if (!fileName.equals("") && !(fileName == null)) {
                        // If file name is null, that means there is no uploading file.
                        File uploadedFile = new File(filepath, fileName);
                        try {
                            item.write(uploadedFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
