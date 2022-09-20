package org.zerock.controller3.service;

import java.util.List;

import org.zerock.controller3.domain.Comment;

public interface CommentService {
	// 해당 게시글의 댓글 수
	public int getCommentListCount(int num);
	
	// 댓글 리스트
	public List<Comment> commentList(int board_num, int page);
	
	// 댓글 등록
	public int commentAdd(Comment comm);
	
	// 댓글 수정
	public int commentUpdate(Comment comm);
	
	// 댓글 삭제
	public int commentDelete(int num);
}
