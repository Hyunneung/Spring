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
	
	@Autowired // 생성자 자동주입
	public MemberController(MemberService memberService, PasswordEncoder passwordEncoder, SendMail sendMail) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
		this.sendMail = sendMail;
	}
	
	
	
	// 로그인 폼 이동
	@RequestMapping(value="/login")
	public ModelAndView login(ModelAndView mv, HttpSession session, Principal userPrincipal,
			            	  @CookieValue(value="remember-me", required=false)Cookie rememberMe) {
		// 로그인 유지하기 체크했을 때
		if(rememberMe != null) {
			logger.info("*** 로그인 유지되고 있는 아이디 : " + userPrincipal.getName());
			mv.setViewName("redirect:/board/list"); // 로그인 과정 거치지 않고 자동 로그인 되어 게시글 리스트로 이동
		} else {
			mv.setViewName("member/member_loginForm");
			mv.addObject("state", session.getAttribute("state")); // 세션에 저장된 값을 한 번만 실행될 수 있도록 합니다.
			session.removeAttribute("state"); // 세션 값 제거
		}
		return mv;
	}
	
		
	
	
	// 회원가입 폼 이동
	@RequestMapping(value="/join")
	public String join() {
		return "member/member_joinForm";
	}
	// 회원가입 id 중복체크
	@RequestMapping(value="/idcheck")
	public void idcheck(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
		int result = memberService.isId(id); // 아이디 없으면 -1, 있으면 1
		response.setContentType("text/html;charset-utf-8"); // 응답 한글 처리
		response.getWriter().print(result); // ajax에 응답 보낸다 - 아이디 없으면 -1, 있으면 1
	}
	// 회원가입
	@RequestMapping(value="/joinProcess", method=RequestMethod.POST)
	public String joinProcess(Member member, RedirectAttributes rattr, Model model, HttpServletRequest request) {
		
		// 비밀번호 암호화
		String encPassword = passwordEncoder.encode(member.getPassword());
		logger.info(encPassword); // 암호화된 비밀번호
		member.setPassword(encPassword);
		
		int result = memberService.join(member); // 가입 성공하면 1, 실패하면 0
		
		if(result == 1) {
			// 회원가입 축하 메일 발송
			MailVO mail = new MailVO();
			mail.setTo(member.getEmail());
			mail.setContent(member.getId() + "님 환영합니다!");
			sendMail.sendMail(mail);
			
			rattr.addFlashAttribute("state", "joinSuccess");
			return "redirect:login"; // redirect 이동
		} else {
			model.addAttribute("url", request.getRequestURL());
			model.addAttribute("message", "회원가입 실패");
			return "error/error"; // forward 이동
		}
	}
	
	// 정보수정 폼 이동
	@RequestMapping(value="/update")
	public ModelAndView updateForm(Principal principal, ModelAndView mv) {
		String id = principal.getName();
		logger.info("*****아이디 = " + id);
		
		// 세션 만료되어 세션에 id 사라진 경우
		if(id == null) {
			mv.setViewName("redirect:login");
			logger.info("*****로그인 정보 만료됨");
		} else {
			Member member = memberService.member_info(id);
			mv.setViewName("member/member_updateForm");
			mv.addObject("member", member);
			logger.info("*****정보 수정 페이지 이동 성공");
		}
		return mv;
	}
	// 정보수정
	@PostMapping("/updateProcess")
	public String updateProcess(Member member, ModelAndView mv,
									  HttpServletRequest request, RedirectAttributes rattr) {
		int result = memberService.updateProcess(member); // 정보수정 성공하면 1, 실패하면 0
		
		// 정보수정 실패
		if(result == 0) {
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "회원가입 실패");
			return "error/error";
		} else {
			// 정보수정 성공
			rattr.addFlashAttribute("state", "updateSuccess"); // redirect로 보낼 때는 전송할 데이터 RedirectAttributes에 저장해서 보내기
			return "redirect:/board/list";
		}
	}
	
	
	// 회원정보 리스트
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value="page", defaultValue="1", required=false) int page,
							 @RequestParam(value="limit", defaultValue="3", required=false) int limit,
							 ModelAndView mv, 
							 @RequestParam(value="search_field", defaultValue="-1", required=false) int index,
							 @RequestParam(value="search_word", defaultValue="", required=false) String search_word) {
		int listCount = memberService.listCount(index, search_word); // 총 멤버 수
		List<Member> list = memberService.list(index, search_word, page, limit);
		
		// 총 페이지 수
		int maxpage = (listCount + limit - 1) / limit;
		
		// 현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
		int startpage = ((page - 1) / 10) * 10 + 1;
		
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30 등 ...)
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
	// 회원정보
	@RequestMapping("/info")
	public ModelAndView info(String id, ModelAndView mv, HttpServletRequest request) {
		Member member = memberService.member_info(id);
		if(member == null) {
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "회원 정보 가져오기 실패");
		} else {
			mv.setViewName("member/member_info");
			mv.addObject("member", member);
		}
		return mv;
	}
	// 회원 삭제
	@RequestMapping("/delete")
	public ModelAndView delete(String id, ModelAndView mv, HttpServletRequest request, RedirectAttributes rattr) {
		int result = memberService.delete(id); // 삭제 성공하면 1, 실패하면 0
		if(result == 0) { // 회원 삭제 실패
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "회원 정보 가져오기 실패");
		} else { // 회원 삭제 성공
			logger.info(id + "=> 회원삭제 성공");
			mv.setViewName("redirect:list");
			rattr.addFlashAttribute("result", result);
			rattr.addFlashAttribute("deleteId", id);
		}
		return mv;
	}
	
	
	
	
}
