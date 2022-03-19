package com.smstudy.mylog.member.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smstudy.mylog.config.auth.PrincipalDetail;
import com.smstudy.mylog.member.dto.MemberDto;
import com.smstudy.mylog.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {
	
	private final MemberService memberService;

	@GetMapping("/public/member/login")
	public String login() {
		return "/member/loginForm";
	}
	
	@GetMapping("/public/member/join")
	public String join() {
		return "/member/joinForm";
	}
	
	@GetMapping("/member/info")
	public String info(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
		
		model.addAttribute("member", MemberDto.of(principal.getMember()));
		
		return "/member/updateForm";
	}
}
