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
	
	@Autowired // ������ �ڵ�����
	public BoardDAO (SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	// �� �Խñ� ��
	public int getListCount() {
		return sqlSession.selectOne("Board.getListCount");
	}
	// �� �Խñ� ����Ʈ
	public List<Board> getList(HashMap<String, Integer> map) { // map���� startrow�� endrow�� ����ִ�
		return sqlSession.selectList("Board.getList", map);
	}
	
	// �۾���
	public void writeBoard(Board board) {
		sqlSession.insert("Board.writeBoard", board);
	}
	
	// �� �󼼺���
	public Board getDetail(int num) {
		return sqlSession.selectOne("Board.getDetail", num);
	}
	
	// ��ȸ�� ����
	public int setReadCountUpdate(int num) {
		return sqlSession.update("Board.setReadCountUpdate", num);
	}
	
	
	// �� ���� �� ��й�ȣ �´��� Ȯ��
	public Board isBoardWriter(Map<String, Object> map) {
		return sqlSession.selectOne("Board.isBoardWriter", map);
	}
	// �� ����
	public int modifyProcess(Board board) {
		return sqlSession.update("Board.modifyProcess", board);
	}
	
	// �亯 �� �ޱ�
	public int replyProcess(Board board) {
		return sqlSession.insert("Board.replyProcess", board);
	}
	
	// �� ����
	public int deleteBoard(Board board) {
		return sqlSession.delete("Board.deleteBoard", board);
	}

}
