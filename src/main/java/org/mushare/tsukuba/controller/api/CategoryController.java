package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.bean.CategoryBean;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/category")
public class CategoryController extends ControllerTemplate {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getCategoryList(@RequestParam(defaultValue = "0") int rev) {
        final List<CategoryBean> categoryBeans = categoryManager.getActivedByRev(rev);
        final int globalRev = categoryManager.getGlobalRev();
        return generateOK(new HashMap<String, Object>() {{
            put("update", categoryBeans.size() > 0);
            put("rev", globalRev);
            put("categories", categoryBeans);
        }});
    }

}
