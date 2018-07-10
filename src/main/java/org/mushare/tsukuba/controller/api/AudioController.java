package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.server.ServerEndpoint;

@Controller
@RequestMapping("api/audio")
@ServerEndpoint(value = "/websocket/signaling", configurator = SpringConfigurator.class)
public class AudioController extends ControllerTemplate {


}
