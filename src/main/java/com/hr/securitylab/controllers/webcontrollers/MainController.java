package com.hr.securitylab.webcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Main controller, serves the main page upon a GET to '/main'
 */

@Controller
@RequestMapping("/main")
public class MainController {
    @RequestMapping(method = RequestMethod.GET)
    public String returnView() {
        return "main";
    }
}
