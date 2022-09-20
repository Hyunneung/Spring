package org.zerock.controller3.service;

import java.util.List;

import org.zerock.controller3.domain.Comment;

public interface CommentService {
	// �ش� �Խñ��� ��� ��
	public int getCommentListCount(int num);
	
	// ��� ����Ʈ
	public List<Comment> commentList(int board_num, int page);
	
	// ��� ���
	public int commentAdd(Comment comm);
	
	// ��� ����
	public int commentUpdate(Comment comm);
	
	// ��� ����
	public int commentDelete(int num);
}
