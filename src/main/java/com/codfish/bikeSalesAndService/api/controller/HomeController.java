package com.codfish.bikeSalesAndService.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@AllArgsConstructor
public class HomeController {
    public static final String HOME_PAGE_URL = "/";

    @RequestMapping(value = HOME_PAGE_URL, method = RequestMethod.GET)
    public static String displayHomePage() {
        return "home/home";
    }
}