package org.zerock.b01.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {

	//BindException => @Valid과정에서 오류난거 처리
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ResponseEntity<Map<String,String>> handleBindException(BindException e){
		log.error(e);
		Map<String,String> errorMap = new HashMap<>();
		if(e.hasErrors()) {
			BindingResult bindingResult = e.getBindingResult();
			
			bindingResult.getFieldErrors().forEach(fieldError -> {
				errorMap.put(fieldError.getField(),fieldError.getCode());
			});
		}
		return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
	}
}
