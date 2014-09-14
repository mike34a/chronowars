package com.miksinouf.chronowars.domain.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@SuppressWarnings("UnusedDeclaration")
public class ChronowarsStaticFilesController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndex() {
        return "index.html";
    }
}
