package org.mushare.tsukuba.controller.web;

import org.mushare.tsukuba.bean.VerificationBean;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.service.VerificationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/verification")
public class VerificationController extends ControllerTemplate {

    @RequestMapping(value = "/{vid}")
    public String verification(@PathVariable String vid, HttpServletRequest request) {
        VerificationBean verificationBean = verificationManager.validate(vid);
        if (verificationBean == null || !verificationBean.isActive()) {
            return "redirect:/password/overtime.html";
        }
        request.getSession().setAttribute(VerificationManager.VerificationFlag, verificationBean);
        return "redirect:/password/reset.html";
    }

}
