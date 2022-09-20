package org.zerock.controller3.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView common(Exception e, HttpServletRequest request) {
		logger.info("*****common에러 발생 => " + e.toString());
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error/common");
		mv.addObject("exception", e);
		mv.addObject("url", request.getRequestURL());
		return mv;
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleError404(Exception e, HttpServletRequest request) {
		logger.info("*****404에러 발생 => " + e.toString());
		ModelAndView  mav = new ModelAndView();
		mav.setViewName("error/404");
		mav.addObject("exception", "404 오류 발생했습니다.");
		mav.addObject("url", request.getRequestURL());
		return mav;
	}

}
