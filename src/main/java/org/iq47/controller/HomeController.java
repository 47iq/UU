package org.iq47.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class HomeController {

    @RequestMapping(value = "/") // <2>
    public String index() {
        return "index"; // <3>
    }

    @RequestMapping(value = "/create") // <2>
    public String create() {
        return "index"; // <3>
    }

    @RequestMapping(value = "/login") // <2>
    public String login() {
        return "index"; // <3>
    }
}
