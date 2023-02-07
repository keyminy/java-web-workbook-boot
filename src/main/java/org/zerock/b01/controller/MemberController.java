package org.zerock.b01.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.MemberJoinDTO;
import org.zerock.b01.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/join")
	public void joinGET() {
		log.info("join get....");
	}
	
	@PostMapping("/join")
	public String joinPOST(MemberJoinDTO memberJoinDTO
			,RedirectAttributes redirectAttributes) {
		log.info("join post....");
		log.info(memberJoinDTO);
		try {
			memberService.join(memberJoinDTO);
			//MidExistException은 static 내부 클래스로 선언되어있어서 .가능하군
		} catch (MemberService.MidExistException e) {
			redirectAttributes.addFlashAttribute("error","mid");
			return "redirect:/member/join";//joinForm?error로
		}
		redirectAttributes.addFlashAttribute("result","success");
		return "redirect:/member/login";//회원 가입후 로그인한다
	}

    @GetMapping("/login")
    public void loginGET(String error, String logout) {
        log.info("login get..............");
        log.info("logout: " + logout);
        
        if(logout != null) {
        	//logout했으면?
        	log.info("user logout.......");
        }
    }

}
