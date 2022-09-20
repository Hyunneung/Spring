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
	
	@Autowired // 생성자 자동주입
	public CommentDAO (SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 해당 게시글의 댓글 수
	public int getCommentListCount(int board_num) {
		return sqlSession.selectOne("Comment.getCommentListCount", board_num);
	}
	
	// 댓글 리스트
	public List<Comment> commentList(Map<String, Object> map) {
		return sqlSession.selectList("Comment.commentList", map);
	}
	
	// 댓글 등록
	public int commentAdd(Comment comm) {
		return sqlSession.insert("Comment.commentAdd", comm);
	}
	
	// 댓글 수정
	public int commentUpdate(Comment comm) {
		return sqlSession.update("Comment.commentUpdate", comm);
	}
	
	// 댓글 삭제
	public int commentDelete(int num) {
		return sqlSession.delete("Comment.commentDelete", num);
	}
}
