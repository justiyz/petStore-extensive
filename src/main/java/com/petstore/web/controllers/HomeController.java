package com.petstore.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/welcome")
    public @ResponseBody String welcomeMessage(){
        return "welcome here...";
    }

    @GetMapping("/")
    public String displayWelcomePage(){
        return "welcome";
    }


}
