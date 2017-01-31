package com.sing.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sing.payment.dto.ErrorMsgDto;
import com.sing.payment.exception.ControllerGenericException;

@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorMsgDto handleAllException(Exception ex) {
		ErrorMsgDto errorMsgDto = new ErrorMsgDto();
		errorMsgDto.setErrorCode("001");
		errorMsgDto.setErrorMsg("exception");
		return errorMsgDto;
	}

	@ExceptionHandler(ControllerGenericException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorMsgDto handleControllerException(ControllerGenericException ex) {
		ErrorMsgDto errorMsgDto = new ErrorMsgDto();
		errorMsgDto.setErrorCode(ex.getErrCode());
		errorMsgDto.setErrorMsg(ex.getErrMsg());
		return errorMsgDto;
	}

}