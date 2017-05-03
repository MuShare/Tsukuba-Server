package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.bean.SelectionBean;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/selection")
public class SelectionController extends ControllerTemplate {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getSelectionList(@RequestParam(defaultValue = "0") int rev) {
        final List<SelectionBean> selectionBeans = selectionManager.getActivedByRev(rev);
        return generateOK(new HashMap<String, Object>() {{
            put("update", selectionBeans.size() > 0);
            put("selections", selectionBeans);
        }});
    }

}
