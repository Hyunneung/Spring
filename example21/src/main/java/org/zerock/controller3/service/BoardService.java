package org.zerock.controller3.service;

import java.util.List;

import org.zerock.controller3.domain.Board;

public interface BoardService {
	
	// 총 게시글 수
	public int getListCount();
	
	// 총 게시글 리스트
	public List<Board> getList(int page, int limit);
	
	// 글쓰기
	public void writeBoard(Board board);
	
	// 글 상세보기
	public Board getDetail(int num);
	
	// 조회수 증가
	public int setReadCountUpdate(int num);
	
	// 글 수정 시 비밀번호 맞는지 확인
	public boolean isBoardWriter(int board_num, String board_pass);
	// 글 수정
	public int modifyProcess(Board board);
	
	// 답변 글 달기
	public int replyProcess(Board board);
	
	// 글 삭제
	public int deleteBoard(int board_num);
	
}
