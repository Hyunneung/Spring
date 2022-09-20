package org.zerock.controller3.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.controller3.dao.MemberDAO;
import org.zerock.controller3.domain.Member;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO dao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 회원가입 - 아이디 중복체크
	@Override
	public int isId(String id) {
		Member m = dao.isId(id);
		return (m == null) ? -1 : 1; // 아이디 없으면(null) -1, 있으면 1
	}
	
	// 회원가입
	@Override
	public int join(Member m) {
		return dao.join(m);  // 가입 성공하면 1, 실패하면 0
	}
	
	// 로그인 - 아이디, 비밀번호 일치하는지, 아이디 존재하는지 확인
	@Override
	public int isId(String id, String password) {
		Member member = dao.isId(id);
		int result = -1;
		
		// 아이디 없으면(null) -1, 일치(로그인 성공)하면 1, 아니면 0
		if(member == null) {
			result = -1;
		} else if(passwordEncoder.matches(password, member.getPassword())) {
			result = 1;
		} else {
			result = 0;
		}
		return result ; // 아이디 없으면(null) -1, 일치(로그인 성공)하면 1, 아니면 0
	}
	
	// 정보수정 폼 이동
	@Override
	public Member member_info(String id) {
		Member member = dao.isId(id);
		return member;
	}
	// 정보수정
	@Override
	public int updateProcess(Member member) {
		int result = dao.updateProcess(member);
		return result;
	}
	
	// 회원정보 리스트
	@Override
	public List<Member> list(int index, String search_word, int page, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// index가 넘어온 경우 (넘어오지 않은 경우는 -1)
		if(index != -1) {
			String[] search_field = new String[] {"id", "name", "age", "gender"};
			map.put("search_field", search_field[index]);
			map.put("search_word", "%" + search_word + "%"); // like로 검색
		}
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("startrow", startrow);
		map.put("endrow", endrow);
		return dao.list(map);
	}
	// 회원 수
	public int listCount(int index, String search_word) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// index가 넘어온 경우 (넘어오지 않은 경우는 -1)
		if(index != -1) {
			String[] search_field = new String[] {"id", "name", "age", "gender"};
			map.put("search_field", search_field[index]);
			map.put("search_word", "%" + search_word + "%"); // like로 검색
		}
		return dao.listCount(map);
	}
	
	// 회원 삭제
	@Override
	public int delete(String id) {
		return dao.delete(id); // 삭제 성공하면 1, 실패하면 0
	}

}
