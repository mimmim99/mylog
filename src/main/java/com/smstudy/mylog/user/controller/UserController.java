package com.smstudy.mylog.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/user/login")
	public String login() {
		return "/user/loginForm";
	}
	
	@GetMapping("/user/join")
	public String join() {
		return "/user/joinForm";
	}
}
