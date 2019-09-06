package com.ljc.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 会员登录
 */
@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) {
		String captchaId = UUID.randomUUID().toString().replaceAll("-", "");
        model.addAttribute("captchaId", captchaId);
        if (request.getParameter("username") != null) {
        	model.addAttribute("username", request.getParameter("username"));
        }
        if (request.getParameter("password") != null) {
        	model.addAttribute("password", request.getParameter("password"));
        }
        if (request.getAttribute("message") != null) {
        	model.addAttribute("message", request.getAttribute("message"));
        }
        if (request.getParameter("redirectUrl") != null) {
        	try {
				model.addAttribute("redirectUrl", URLEncoder.encode(request.getParameter("redirectUrl"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        }
		return "/login/index";
	}

	@RequestMapping("/index")
	@ResponseBody
	public String index() {
		return "THIS IS INDEX!";
	}
}
