package org.zerock.controller1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.zerock.controller1.model.BbsBean;

public class BbsWriteController extends AbstractController{
	
	private BbsBean bean;
	public void setBbsBean(BbsBean bean) {
		this.bean = bean;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		bean.setId(id);
		bean.setPass(pass);
		
		ModelAndView mv = new ModelAndView("list");
		mv.addObject("bean", bean);
		
		return mv;
	}

}
