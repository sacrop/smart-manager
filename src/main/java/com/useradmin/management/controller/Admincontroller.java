package com.useradmin.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class Admincontroller {
    @GetMapping("/")
    public String home(){
        return "/admin/home";
    }
}
