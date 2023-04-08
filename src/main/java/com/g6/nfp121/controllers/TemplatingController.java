package com.g6.nfp121.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Controller
public class TemplatingController {

    @GetMapping("/login")
    public String loginPage() {
        return "login-template";
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index-template";
    }
}