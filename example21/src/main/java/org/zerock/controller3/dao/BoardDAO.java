package org.zerock.controller3.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.zerock.controller3.domain.Board;

@Repository
public class BoardDAO {
	private SqlSessionTemplate sqlSession;
	
	@Autowired // 생성자 자동주입
	public BoardDAO (SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	// 총 게시글 수
	public int getListCount() {
		return sqlSession.selectOne("Board.getListCount");
	}
	// 총 게시글 리스트
	public List<Board> getList(HashMap<String, Integer> map) { // map에는 startrow와 endrow가 담겨있다
		return sqlSession.selectList("Board.getList", map);
	}
	
	// 글쓰기
	public void writeBoard(Board board) {
		sqlSession.insert("Board.writeBoard", board);
	}
	
	// 글 상세보기
	public Board getDetail(int num) {
		return sqlSession.selectOne("Board.getDetail", num);
	}
	
	// 조회수 증가
	public int setReadCountUpdate(int num) {
		return sqlSession.update("Board.setReadCountUpdate", num);
	}
	
	
	// 글 수정 시 비밀번호 맞는지 확인
	public Board isBoardWriter(Map<String, Object> map) {
		return sqlSession.selectOne("Board.isBoardWriter", map);
	}
	// 글 수정
	public int modifyProcess(Board board) {
		return sqlSession.update("Board.modifyProcess", board);
	}
	
	// 답변 글 달기
	public int replyProcess(Board board) {
		return sqlSession.insert("Board.replyProcess", board);
	}
	
	// 글 삭제
	public int deleteBoard(Board board) {
		return sqlSession.delete("Board.deleteBoard", board);
	}

}
