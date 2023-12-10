package org.iq47.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequestMapping(("${urls.base}/${urls.home.base}"))
public class HomeController {

    @RequestMapping(value = "/{urls.home.index}") // <2>
    public String index() {
        return "index"; // <3>
    }

    @RequestMapping(value = "/{urls.home.create}") // <2>
    public String create() {
        return "index"; // <3>
    }

    @RequestMapping(value = "/{urls.home.login}") // <2>
    public String login() {
        return "index"; // <3>
    }

    @RequestMapping(value = "/{urls.home.details}") // <2>
    public String details(@RequestParam String id) {
        return "index"; // <3>
    }

    @RequestMapping(value = "/{urls.home.order}") // <2>
    public String order(@RequestParam String id) {
        return "index"; // <3>
    }

    @RequestMapping(value = "/{urls.home.basket}") // <2>
    public String basket() {
        return "index"; // <3>
    }

    @RequestMapping(value = "/{urls.home.favourites}") // <2>
    public String favourites() {
        return "index"; // <3>
    }

    @RequestMapping(value = "/{urls.home.orders}") // <2>
    public String orders() {
        return "index"; // <3>
    }
}
