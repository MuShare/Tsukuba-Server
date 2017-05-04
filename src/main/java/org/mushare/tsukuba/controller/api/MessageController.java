package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/api/message")
public class MessageController extends ControllerTemplate {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createMessage(@RequestParam String cid, @RequestParam String title,
                                        @RequestParam String oids, @RequestParam int price ,
                                        @RequestParam boolean sell, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>() {{

        }});
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity modifyMessage(@RequestParam String mid, String title, String oids,
                                        String introduction, int price, HttpServletRequest request) {

        return generateOK(new HashMap<String, Object>() {

        });
    }

    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResponseEntity enableMessgae(@RequestParam String mid, @RequestParam boolean enable, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>() {{

        }});
    }

    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public ResponseEntity uploadPictureToMessage(@RequestParam String mid, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>() {{

        }});
    }

    @RequestMapping(value = "/picture", method = RequestMethod.DELETE)
    public ResponseEntity deletePictureFromMessage(@RequestParam String pid, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>() {{

        }});
    }

}
