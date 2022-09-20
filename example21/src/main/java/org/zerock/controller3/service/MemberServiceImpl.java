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
	
	// ȸ������ - ���̵� �ߺ�üũ
	@Override
	public int isId(String id) {
		Member m = dao.isId(id);
		return (m == null) ? -1 : 1; // ���̵� ������(null) -1, ������ 1
	}
	
	// ȸ������
	@Override
	public int join(Member m) {
		return dao.join(m);  // ���� �����ϸ� 1, �����ϸ� 0
	}
	
	// �α��� - ���̵�, ��й�ȣ ��ġ�ϴ���, ���̵� �����ϴ��� Ȯ��
	@Override
	public int isId(String id, String password) {
		Member member = dao.isId(id);
		int result = -1;
		
		// ���̵� ������(null) -1, ��ġ(�α��� ����)�ϸ� 1, �ƴϸ� 0
		if(member == null) {
			result = -1;
		} else if(passwordEncoder.matches(password, member.getPassword())) {
			result = 1;
		} else {
			result = 0;
		}
		return result ; // ���̵� ������(null) -1, ��ġ(�α��� ����)�ϸ� 1, �ƴϸ� 0
	}
	
	// �������� �� �̵�
	@Override
	public Member member_info(String id) {
		Member member = dao.isId(id);
		return member;
	}
	// ��������
	@Override
	public int updateProcess(Member member) {
		int result = dao.updateProcess(member);
		return result;
	}
	
	// ȸ������ ����Ʈ
	@Override
	public List<Member> list(int index, String search_word, int page, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// index�� �Ѿ�� ��� (�Ѿ���� ���� ���� -1)
		if(index != -1) {
			String[] search_field = new String[] {"id", "name", "age", "gender"};
			map.put("search_field", search_field[index]);
			map.put("search_word", "%" + search_word + "%"); // like�� �˻�
		}
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("startrow", startrow);
		map.put("endrow", endrow);
		return dao.list(map);
	}
	// ȸ�� ��
	public int listCount(int index, String search_word) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// index�� �Ѿ�� ��� (�Ѿ���� ���� ���� -1)
		if(index != -1) {
			String[] search_field = new String[] {"id", "name", "age", "gender"};
			map.put("search_field", search_field[index]);
			map.put("search_word", "%" + search_word + "%"); // like�� �˻�
		}
		return dao.listCount(map);
	}
	
	// ȸ�� ����
	@Override
	public int delete(String id) {
		return dao.delete(id); // ���� �����ϸ� 1, �����ϸ� 0
	}

}
