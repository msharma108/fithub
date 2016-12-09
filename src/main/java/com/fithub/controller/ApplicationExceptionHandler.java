package com.fithub.controller;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ApplicationExceptionHandler {

	private static Logger LOG = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

	@ExceptionHandler({ NoSuchElementException.class, IllegalArgumentException.class })
	public ModelAndView handlerNoSuchElementException(HttpServletRequest request, Exception exception) {
		LOG.error("Excepion ={} appeared at URL={}", exception.getMessage(), request.getRequestURL());
		ModelAndView model = new ModelAndView();
		model.addObject("errorUrl", request.getRequestURL());
		model.addObject("exception", exception.getMessage());
		model.setViewName("customErrorPage");
		return model;
	}

}
