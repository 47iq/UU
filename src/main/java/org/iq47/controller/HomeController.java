package org.iq47.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/details") // <2>
    public String details(@RequestParam String id) {
        return "index"; // <3>
    }

    @RequestMapping(value = "/order") // <2>
    public String order(@RequestParam String id) {
        return "index"; // <3>
    }

    @RequestMapping(value = "/basket") // <2>
    public String basket() {
        return "index"; // <3>
    }

    @RequestMapping(value = "/favourites") // <2>
    public String favourites() {
        return "index"; // <3>
    }

    @RequestMapping(value = "/orders") // <2>
    public String orders() {
        return "index"; // <3>
    }
}
