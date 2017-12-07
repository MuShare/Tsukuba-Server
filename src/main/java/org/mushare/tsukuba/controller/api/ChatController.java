package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("api/chat")
public class ChatController extends ControllerTemplate {

    @RequestMapping(value = "/text", method = RequestMethod.POST)
    public ResponseEntity sendPlainText(@RequestParam String uid, @RequestParam String content, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>(){{

        }});
    }

    @RequestMapping(value = "/text", method = RequestMethod.POST)
    public ResponseEntity sendPicture(@RequestParam String uid, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>(){{

        }});
    }

    @RequestMapping(value = "/list/{rid}", method = RequestMethod.GET)
    public ResponseEntity getChatList(@PathVariable String rid, @RequestParam(defaultValue = "0") Long last, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>(){{

        }});
    }

}
