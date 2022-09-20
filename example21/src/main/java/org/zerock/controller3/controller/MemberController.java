package org.zerock.controller3.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.controller3.domain.MailVO;
import org.zerock.controller3.domain.Member;
import org.zerock.controller3.service.MemberService;
import org.zerock.controller3.task.SendMail;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private MemberService memberService;
	private PasswordEncoder passwordEncoder;
	private SendMail sendMail;
	
	@Autowired // ������ �ڵ�����
	public MemberController(MemberService memberService, PasswordEncoder passwordEncoder, SendMail sendMail) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
		this.sendMail = sendMail;
	}
	
	
	
	// �α��� �� �̵�
	@RequestMapping(value="/login")
	public ModelAndView login(ModelAndView mv, HttpSession session, Principal userPrincipal,
			            	  @CookieValue(value="remember-me", required=false)Cookie rememberMe) {
		// �α��� �����ϱ� üũ���� ��
		if(rememberMe != null) {
			logger.info("*** �α��� �����ǰ� �ִ� ���̵� : " + userPrincipal.getName());
			mv.setViewName("redirect:/board/list"); // �α��� ���� ��ġ�� �ʰ� �ڵ� �α��� �Ǿ� �Խñ� ����Ʈ�� �̵�
		} else {
			mv.setViewName("member/member_loginForm");
			mv.addObject("state", session.getAttribute("state")); // ���ǿ� ����� ���� �� ���� ����� �� �ֵ��� �մϴ�.
			session.removeAttribute("state"); // ���� �� ����
		}
		return mv;
	}
	
		
	
	
	// ȸ������ �� �̵�
	@RequestMapping(value="/join")
	public String join() {
		return "member/member_joinForm";
	}
	// ȸ������ id �ߺ�üũ
	@RequestMapping(value="/idcheck")
	public void idcheck(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
		int result = memberService.isId(id); // ���̵� ������ -1, ������ 1
		response.setContentType("text/html;charset-utf-8"); // ���� �ѱ� ó��
		response.getWriter().print(result); // ajax�� ���� ������ - ���̵� ������ -1, ������ 1
	}
	// ȸ������
	@RequestMapping(value="/joinProcess", method=RequestMethod.POST)
	public String joinProcess(Member member, RedirectAttributes rattr, Model model, HttpServletRequest request) {
		
		// ��й�ȣ ��ȣȭ
		String encPassword = passwordEncoder.encode(member.getPassword());
		logger.info(encPassword); // ��ȣȭ�� ��й�ȣ
		member.setPassword(encPassword);
		
		int result = memberService.join(member); // ���� �����ϸ� 1, �����ϸ� 0
		
		if(result == 1) {
			// ȸ������ ���� ���� �߼�
			MailVO mail = new MailVO();
			mail.setTo(member.getEmail());
			mail.setContent(member.getId() + "�� ȯ���մϴ�!");
			sendMail.sendMail(mail);
			
			rattr.addFlashAttribute("state", "joinSuccess");
			return "redirect:login"; // redirect �̵�
		} else {
			model.addAttribute("url", request.getRequestURL());
			model.addAttribute("message", "ȸ������ ����");
			return "error/error"; // forward �̵�
		}
	}
	
	// �������� �� �̵�
	@RequestMapping(value="/update")
	public ModelAndView updateForm(Principal principal, ModelAndView mv) {
		String id = principal.getName();
		logger.info("*****���̵� = " + id);
		
		// ���� ����Ǿ� ���ǿ� id ����� ���
		if(id == null) {
			mv.setViewName("redirect:login");
			logger.info("*****�α��� ���� �����");
		} else {
			Member member = memberService.member_info(id);
			mv.setViewName("member/member_updateForm");
			mv.addObject("member", member);
			logger.info("*****���� ���� ������ �̵� ����");
		}
		return mv;
	}
	// ��������
	@PostMapping("/updateProcess")
	public String updateProcess(Member member, ModelAndView mv,
									  HttpServletRequest request, RedirectAttributes rattr) {
		int result = memberService.updateProcess(member); // �������� �����ϸ� 1, �����ϸ� 0
		
		// �������� ����
		if(result == 0) {
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "ȸ������ ����");
			return "error/error";
		} else {
			// �������� ����
			rattr.addFlashAttribute("state", "updateSuccess"); // redirect�� ���� ���� ������ ������ RedirectAttributes�� �����ؼ� ������
			return "redirect:/board/list";
		}
	}
	
	
	// ȸ������ ����Ʈ
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value="page", defaultValue="1", required=false) int page,
							 @RequestParam(value="limit", defaultValue="3", required=false) int limit,
							 ModelAndView mv, 
							 @RequestParam(value="search_field", defaultValue="-1", required=false) int index,
							 @RequestParam(value="search_word", defaultValue="", required=false) String search_word) {
		int listCount = memberService.listCount(index, search_word); // �� ��� ��
		List<Member> list = memberService.list(index, search_word, page, limit);
		
		// �� ������ ��
		int maxpage = (listCount + limit - 1) / limit;
		
		// ���� �������� ������ ���� ������ ��(1, 11, 21 ��...)
		int startpage = ((page - 1) / 10) * 10 + 1;
		
		// ���� �������� ������ ������ ������ ��(10, 20, 30 �� ...)
		int endpage = startpage + 10 - 1;
		
		if(endpage > maxpage) {
			endpage = maxpage;
		}
		
		System.out.println("*****list => " + list);
		System.out.println("*****listCount => " + listCount);
		System.out.println("*****search_word => " + search_word);
		
		mv.setViewName("member/member_list");
		mv.addObject("page", page);
		mv.addObject("maxpage", maxpage);
		mv.addObject("startpage", startpage);
		mv.addObject("endpage", endpage);
		mv.addObject("listCount", listCount);
		mv.addObject("list", list);
		mv.addObject("index", index);
		mv.addObject("search_word", search_word);
		return mv;
	}
	// ȸ������
	@RequestMapping("/info")
	public ModelAndView info(String id, ModelAndView mv, HttpServletRequest request) {
		Member member = memberService.member_info(id);
		if(member == null) {
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "ȸ�� ���� �������� ����");
		} else {
			mv.setViewName("member/member_info");
			mv.addObject("member", member);
		}
		return mv;
	}
	// ȸ�� ����
	@RequestMapping("/delete")
	public ModelAndView delete(String id, ModelAndView mv, HttpServletRequest request, RedirectAttributes rattr) {
		int result = memberService.delete(id); // ���� �����ϸ� 1, �����ϸ� 0
		if(result == 0) { // ȸ�� ���� ����
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "ȸ�� ���� �������� ����");
		} else { // ȸ�� ���� ����
			logger.info(id + "=> ȸ������ ����");
			mv.setViewName("redirect:list");
			rattr.addFlashAttribute("result", result);
			rattr.addFlashAttribute("deleteId", id);
		}
		return mv;
	}
	
	
	
	
}
