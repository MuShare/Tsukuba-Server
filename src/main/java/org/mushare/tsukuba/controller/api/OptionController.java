package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("/api/option")
public class OptionController extends ControllerTemplate {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getOptionList(@RequestParam(defaultValue = "0") int rev) {

        return generateOK(new HashMap<String, Object>() {{
            put("options", null);
        }});
    }

}
