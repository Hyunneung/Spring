package org.zerock.controller3.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.controller3.dao.BoardDAO;
import org.zerock.controller3.domain.Board;

@Service
public class BoardServiceImpl implements BoardService {
	private BoardDAO dao;
	
	@Autowired // ������ �ڵ�����
	public BoardServiceImpl(BoardDAO dao) {
		this.dao = dao;
	}
	
	// �� �Խñ� ��
	@Override
	public int getListCount() {
		return dao.getListCount();
	}
	// �� �Խñ� ����Ʈ
	@Override
	public List<Board> getList(int page, int limit) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startrow = (page-1) * limit + 1; // �Խñ� ����Ʈ �� ù �۹�ȣ    (1, 11, 21, 31, 41...)
		int endrow = startrow + limit - 1;   // �Խñ� ����Ʈ �� ������ �۹�ȣ (10, 20, 30, 40, 50...)
		map.put("startrow", startrow);
		map.put("endrow", endrow);
		return dao.getList(map);
	}
	
	//�۾���
	@Override
	public void writeBoard(Board board) {
		dao.writeBoard(board);
	}
	
	// �� �󼼺���
	@Override
	public Board getDetail(int num) {
		return dao.getDetail(num);
	}
	
	// ��ȸ�� ����
	@Override
	public int setReadCountUpdate(int num) {
		return dao.setReadCountUpdate(num);
	}

	// �� ���� �� ��й�ȣ �´��� Ȯ��
	@Override
	public boolean isBoardWriter(int board_num, String board_pass) {
		// �ѱ� �Ű������� 2�� �̻� �϶��� Map���� �ѱ��
		// �Ʒ� Map�� key �ڷ��� String, value �ڷ��� Object - ��, value�� Object �ڷ������� int, String ��� ���� 
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("board_num", board_num);
		map.put("board_pass", board_pass);
		
		System.out.println("*** �� ��ȣ = " + board_num + " / �н����� = " + board_pass);
		Board resultBoard = dao.isBoardWriter(map);
		
		// ��й�ȣ ����ġ
		if (resultBoard == null) {
			return false;
		} else {
			// ��й�ȣ ��ġ
			return true;
		}
	}
	// �� ����
	@Override
	public int modifyProcess(Board board) {
		return dao.modifyProcess(board);
	}
	
	// �亯 �� �ޱ�
	@Override
	public int replyProcess(Board board) {
		// �亯���� ���� ����(BOARD_RE_LEV), �亯 ���� ����(BOARD_RE_SEQ) 1�� ����
		board.setBOARD_RE_LEV(board.getBOARD_RE_LEV() + 1);
		board.setBOARD_RE_SEQ(board.getBOARD_RE_SEQ() + 1);
		return dao.replyProcess(board);
	}
	
	// �� ����
	@Override
	public int deleteBoard(int board_num) {
		int result = 0;
		Board board = dao.getDetail(board_num);
		if(board != null) {
			result = dao.deleteBoard(board);
		}
		return result;
	}

}
