package org.zerock.controller3.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.zerock.controller3.domain.Member;

@Repository
public class MemberDAO {
	private SqlSessionTemplate sqlSession;
	
	@Autowired // 생성자 자동주입
	public MemberDAO (SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 회원가입 - 아이디 중복체크 / 로그인 - 아이디, 비밀번호 일치하는지, 아이디 존재하는지 확인
	public Member isId(String id) {
		return sqlSession.selectOne("Member.isId", id); // 아이디 없으면 null
	}
	
	// 회원가입
	public int join(Member member) {
		return sqlSession.insert("Member.join", member); // 가입 성공하면 1, 실패하면 0
	}
	
	// 정보수정
	public int updateProcess(Member member) {
		return sqlSession.update("Member.updateProcess", member); // 정보수정 성공하면 1, 실패하면 0
	}
	
	// 회원정보 리스트
	public List<Member> list(Map<String, Object> map) {
		return sqlSession.selectList("Member.list", map);
	}
	// 회원 수
	public int listCount(Map<String, Object> map) {
		return sqlSession.selectOne("Member.listCount", map);
	}
	
	// 회원 삭제
	public int delete(String id) {
		return sqlSession.insert("Member.delete", id); // 삭제 성공하면 1, 실패하면 0
	}
}
