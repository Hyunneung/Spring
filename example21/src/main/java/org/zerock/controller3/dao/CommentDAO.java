package org.zerock.controller3.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.zerock.controller3.domain.Comment;

@Repository
public class CommentDAO {
	
	private SqlSessionTemplate sqlSession;
	
	@Autowired // ������ �ڵ�����
	public CommentDAO (SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// �ش� �Խñ��� ��� ��
	public int getCommentListCount(int board_num) {
		return sqlSession.selectOne("Comment.getCommentListCount", board_num);
	}
	
	// ��� ����Ʈ
	public List<Comment> commentList(Map<String, Object> map) {
		return sqlSession.selectList("Comment.commentList", map);
	}
	
	// ��� ���
	public int commentAdd(Comment comm) {
		return sqlSession.insert("Comment.commentAdd", comm);
	}
	
	// ��� ����
	public int commentUpdate(Comment comm) {
		return sqlSession.update("Comment.commentUpdate", comm);
	}
	
	// ��� ����
	public int commentDelete(int num) {
		return sqlSession.delete("Comment.commentDelete", num);
	}
}
