package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.bean.OptionBean;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.domain.Option;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/option")
public class OptionController extends ControllerTemplate {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getOptionList(@RequestParam(defaultValue = "0") int rev) {
        final List<OptionBean> optionBeans = optionManager.getActivedByRev(rev);
        final int globalRev = optionManager.getGlobalRev();
        return generateOK(new HashMap<String, Object>() {{
            put("update", optionBeans.size() > 0);
            put("rev", globalRev);
            put("options", optionBeans);
        }});
    }

}
