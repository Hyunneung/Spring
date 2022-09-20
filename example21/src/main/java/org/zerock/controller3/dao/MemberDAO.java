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
	
	@Autowired // ������ �ڵ�����
	public MemberDAO (SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// ȸ������ - ���̵� �ߺ�üũ / �α��� - ���̵�, ��й�ȣ ��ġ�ϴ���, ���̵� �����ϴ��� Ȯ��
	public Member isId(String id) {
		return sqlSession.selectOne("Member.isId", id); // ���̵� ������ null
	}
	
	// ȸ������
	public int join(Member member) {
		return sqlSession.insert("Member.join", member); // ���� �����ϸ� 1, �����ϸ� 0
	}
	
	// ��������
	public int updateProcess(Member member) {
		return sqlSession.update("Member.updateProcess", member); // �������� �����ϸ� 1, �����ϸ� 0
	}
	
	// ȸ������ ����Ʈ
	public List<Member> list(Map<String, Object> map) {
		return sqlSession.selectList("Member.list", map);
	}
	// ȸ�� ��
	public int listCount(Map<String, Object> map) {
		return sqlSession.selectOne("Member.listCount", map);
	}
	
	// ȸ�� ����
	public int delete(String id) {
		return sqlSession.insert("Member.delete", id); // ���� �����ϸ� 1, �����ϸ� 0
	}
}
