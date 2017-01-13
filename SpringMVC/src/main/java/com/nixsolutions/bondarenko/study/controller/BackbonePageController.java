package com.nixsolutions.bondarenko.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BackbonePageController {

    @RequestMapping(value = "/admin/backbone", method = RequestMethod.GET)
    public String admin_backbone() {
        return "admin_backbone";
    }

}
