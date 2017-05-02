package org.mushare.tsukuba.controller.web;

import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/logout")
public class LogoutController extends ControllerTemplate {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminLogout(HttpServletRequest request) throws IOException {
        adminManager.logout(request.getSession());
        return "redirect:/admin";
    }

}
