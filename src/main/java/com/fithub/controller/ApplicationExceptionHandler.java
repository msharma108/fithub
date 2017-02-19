package com.fithub.controller;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * A central exception handler for the application that intercepts all the
 * thrown exceptions
 *
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

	private static Logger LOG = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

	/**
	 * Method handles No Such Element Exception thrown from anywhere in the
	 * application
	 * 
	 * @param request
	 *            HttpServlet request object
	 * @param exception
	 *            NoSuchElementException object thrown by the application
	 * @return model Model object that contains exception details to be made
	 *         visible on UI
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ModelAndView handlerNoSuchElementException(HttpServletRequest request, NoSuchElementException exception) {
		LOG.error("Excepion ={} appeared at URL={}", exception.getMessage(), request.getRequestURL());
		ModelAndView model = new ModelAndView();
		model.addObject("errorUrl", request.getRequestURL());
		model.addObject("exception", exception.getMessage());

		if (request.getRequestURL().toString().contains("passwordRetrieval"))
			model.setViewName("user/passwordRetrieval");
		else
			model.setViewName("customErrorPage");
		return model;
	}

	/**
	 * Method handles Illegal Argument Exception thrown from anywhere in the
	 * application
	 * 
	 * @param request
	 *            HttpServlet request object
	 * @param exception
	 *            IllegalArgumentException object thrown by the application
	 * @return model Model object that contains exception details to be made
	 *         applications Custom error page
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handlerIllegalArgumentException(HttpServletRequest request,
			IllegalArgumentException exception) {
		LOG.error("Excepion ={} appeared at URL={}", exception.getMessage(), request.getRequestURL());
		ModelAndView model = new ModelAndView();
		model.addObject("exception", exception.getMessage());
		model.addObject("errorUrl", request.getRequestURL());
		model.setViewName("customErrorPage");
		return model;
	}

	/**
	 * Method handles Illegal State Exception thrown from anywhere in the
	 * application
	 * 
	 * @param request
	 *            HttpServlet request object
	 * @param exception
	 *            IllegalStateException object thrown by the application
	 * @return model Model object that contains exception details to be made
	 *         applications Custom error page
	 */
	@ExceptionHandler(IllegalStateException.class)
	public ModelAndView handlerIllegalStateException(HttpServletRequest request, IllegalStateException exception) {
		LOG.error("Excepion ={} appeared at URL={}", exception.getMessage(), request.getRequestURL());
		ModelAndView model = new ModelAndView();
		model.addObject("exception", exception.getMessage());
		model.addObject("errorUrl", request.getRequestURL());
		if (request.getRequestURL().toString().contains("constructUrlForProductOperations"))
			model.setViewName("product/shoppingCart");
		else
			model.setViewName("customErrorPage");
		return model;
	}

}
