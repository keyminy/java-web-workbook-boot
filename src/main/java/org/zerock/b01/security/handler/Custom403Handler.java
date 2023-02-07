package org.zerock.b01.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Custom403Handler implements AccessDeniedHandler{
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.info("----------ACCESS DENIED-------------");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		//JSON(ajax)요청인지 확인하기
		String contentType = request.getHeader("Content-Type");
		boolean jsonRequest = contentType.startsWith("application/json");
		log.info("isJSON : " + jsonRequest);
		
		//일반 request면? -> login페이지로 redirect
		if(!jsonRequest) {
			response.sendRedirect("/member/login?error=ACCESS_DENIED");
		}
	}

}
