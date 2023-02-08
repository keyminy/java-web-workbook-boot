package org.zerock.b01.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.b01.security.dto.MemberSecurityDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements 
	AuthenticationSuccessHandler{

	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("-----------------------------");
		log.info("CustomLoginSuccessHandler onAuthenticationSuccess........");
		log.info(authentication.getPrincipal());
		MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO)authentication.getPrincipal();
		System.out.println("되라 ㅠㅠ");
		System.out.println(memberSecurityDTO.isSocial());
		System.out.println("왜?? : " + memberSecurityDTO);
		//소셜 로그인이고 회원의 패스워드가 1111
		 if (memberSecurityDTO.isSocial()
	                && (memberSecurityDTO.getMpw().equals("54321")
	                    ||  passwordEncoder.matches("54321", memberSecurityDTO.getMpw())
	        )) {
			log.info("Should Change Password");
			log.info("Redirect to Member Modify");
			response.sendRedirect("/member/modify");
			return;
		}else {
			System.out.println("적용 되고 있음??");
			response.sendRedirect("/board/list");
		}
	}
	
}
