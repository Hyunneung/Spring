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
	
	@Autowired // 생성자 자동주입
	public BoardServiceImpl(BoardDAO dao) {
		this.dao = dao;
	}
	
	// 총 게시글 수
	@Override
	public int getListCount() {
		return dao.getListCount();
	}
	// 총 게시글 리스트
	@Override
	public List<Board> getList(int page, int limit) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startrow = (page-1) * limit + 1; // 게시글 리스트 중 첫 글번호    (1, 11, 21, 31, 41...)
		int endrow = startrow + limit - 1;   // 게시글 리스트 중 마지막 글번호 (10, 20, 30, 40, 50...)
		map.put("startrow", startrow);
		map.put("endrow", endrow);
		return dao.getList(map);
	}
	
	//글쓰기
	@Override
	public void writeBoard(Board board) {
		dao.writeBoard(board);
	}
	
	// 글 상세보기
	@Override
	public Board getDetail(int num) {
		return dao.getDetail(num);
	}
	
	// 조회수 증가
	@Override
	public int setReadCountUpdate(int num) {
		return dao.setReadCountUpdate(num);
	}

	// 글 수정 시 비밀번호 맞는지 확인
	@Override
	public boolean isBoardWriter(int board_num, String board_pass) {
		// 넘길 매개변수가 2개 이상 일때는 Map으로 넘기기
		// 아래 Map의 key 자료형 String, value 자료형 Object - 즉, value는 Object 자료형으로 int, String 모두 가능 
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("board_num", board_num);
		map.put("board_pass", board_pass);
		
		System.out.println("*** 글 번호 = " + board_num + " / 패스워드 = " + board_pass);
		Board resultBoard = dao.isBoardWriter(map);
		
		// 비밀번호 불일치
		if (resultBoard == null) {
			return false;
		} else {
			// 비밀번호 일치
			return true;
		}
	}
	// 글 수정
	@Override
	public int modifyProcess(Board board) {
		return dao.modifyProcess(board);
	}
	
	// 답변 글 달기
	@Override
	public int replyProcess(Board board) {
		// 답변글은 글의 깊이(BOARD_RE_LEV), 답변 글의 순서(BOARD_RE_SEQ) 1씩 증가
		board.setBOARD_RE_LEV(board.getBOARD_RE_LEV() + 1);
		board.setBOARD_RE_SEQ(board.getBOARD_RE_SEQ() + 1);
		return dao.replyProcess(board);
	}
	
	// 글 삭제
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
