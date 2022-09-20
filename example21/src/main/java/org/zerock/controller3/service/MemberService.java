package org.zerock.controller3.service;

import java.util.List;

import org.zerock.controller3.domain.Member;

public interface MemberService {
	
	// ȸ������ - ���̵� �ߺ�üũ
	public int isId(String id); // ���̵� ������ -1, ������ 1
	
	// ȸ������
	public int join(Member m); // ���� �����ϸ� 1, �����ϸ� 0
	
	// �α��� - ���̵�, ��й�ȣ ��ġ�ϴ��� Ȯ��
	public int isId(String id, String password); // ��ġ(�α��� ����)�ϸ� 1, �ƴϸ� 0
	
	// �������� �� �̵�
	public Member member_info(String id);
	// ��������
	public int updateProcess(Member member);
	
	// ȸ������ ����Ʈ
	public List<Member> list(int index, String search_word, int page, int limit);
	// ȸ�� ��
	public int listCount(int index, String search_word);
	
	// ȸ�� ����
	public int delete(String id); // ���� �����ϸ� 1, �����ϸ� 0
}
