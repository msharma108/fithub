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

	@ExceptionHandler(NoSuchElementException.class)
	public ModelAndView handlerNoSuchElementException(HttpServletRequest request, NoSuchElementException exception) {
		LOG.error("Excepion ={} appeared at URL={}", exception.getMessage(), request.getRequestURL());
		ModelAndView model = new ModelAndView();
		model.addObject("errorUrl", request.getRequestURL());
		model.addObject("exception", exception.getMessage());
		model.setViewName("user/customErrorPage");
		return model;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handlerIllegalArgumentException(HttpServletRequest request,
			IllegalArgumentException exception) {
		LOG.error("Excepion ={} appeared at URL={}", exception.getMessage(), request.getRequestURL());
		ModelAndView model = new ModelAndView();
		model.addObject("exception", "Blank value(s) provided for the field(s), please provide valid value(s)");
		model.addObject("errorUrl", request.getRequestURL());
		model.setViewName("user/customErrorPage");
		return model;
	}

	@ExceptionHandler(IllegalStateException.class)
	public ModelAndView handlerIllegalStateException(HttpServletRequest request, IllegalStateException exception) {
		LOG.error("Excepion ={} appeared at URL={}", exception.getMessage(), request.getRequestURL());
		ModelAndView model = new ModelAndView();
		model.addObject("exception", exception.getMessage());
		model.addObject("errorUrl", request.getRequestURL());
		model.setViewName("product/shoppingCart");
		return model;
	}

}
