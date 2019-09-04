package com.ljc.review.web.controller;

import com.ljc.review.web.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public BaseResult login(String username, String password) {
        
        return BaseResult.success("loginSuccess");
    }

    @GetMapping("/index")
    public BaseResult index() {
        return BaseResult.success("indexSuccess");
    }

    @GetMapping("/userInfo")
    public BaseResult userInfo() {
        return BaseResult.success(SecurityContextHolder.getContext().getAuthentication());
    }

}