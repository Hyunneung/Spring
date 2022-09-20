package org.zerock.controller3.service;

import java.util.List;

import org.zerock.controller3.domain.Board;

public interface BoardService {
	
	// �� �Խñ� ��
	public int getListCount();
	
	// �� �Խñ� ����Ʈ
	public List<Board> getList(int page, int limit);
	
	// �۾���
	public void writeBoard(Board board);
	
	// �� �󼼺���
	public Board getDetail(int num);
	
	// ��ȸ�� ����
	public int setReadCountUpdate(int num);
	
	// �� ���� �� ��й�ȣ �´��� Ȯ��
	public boolean isBoardWriter(int board_num, String board_pass);
	// �� ����
	public int modifyProcess(Board board);
	
	// �亯 �� �ޱ�
	public int replyProcess(Board board);
	
	// �� ����
	public int deleteBoard(int board_num);
	
}
