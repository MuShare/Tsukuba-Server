package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.controller.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("/api/user")
public class UserController extends ControllerTemplate {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerByEmail(@RequestParam String email, @RequestParam String name, @RequestParam String password) {
        if (!userManager.registerByEmail(email, password, name)) {
            return generateBadRequest(ErrorCode.ErrorEmailExist);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("success", true);
        }});
    }

}
