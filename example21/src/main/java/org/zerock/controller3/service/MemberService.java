package org.zerock.controller3.service;

import java.util.List;

import org.zerock.controller3.domain.Member;

public interface MemberService {
	
	// 회원가입 - 아이디 중복체크
	public int isId(String id); // 아이디 없으면 -1, 있으면 1
	
	// 회원가입
	public int join(Member m); // 가입 성공하면 1, 실패하면 0
	
	// 로그인 - 아이디, 비밀번호 일치하는지 확인
	public int isId(String id, String password); // 일치(로그인 성공)하면 1, 아니면 0
	
	// 정보수정 폼 이동
	public Member member_info(String id);
	// 정보수정
	public int updateProcess(Member member);
	
	// 회원정보 리스트
	public List<Member> list(int index, String search_word, int page, int limit);
	// 회원 수
	public int listCount(int index, String search_word);
	
	// 회원 삭제
	public int delete(String id); // 삭제 성공하면 1, 실패하면 0
}
