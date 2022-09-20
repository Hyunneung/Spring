package org.zerock.controller3.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.controller3.dao.CommentDAO;
import org.zerock.controller3.domain.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDAO dao;
	
	// �ش� �Խñ��� ��� ��
	@Override
	public int getCommentListCount(int board_num) {
		return dao.getCommentListCount(board_num);
	}
	
	// ��� ����Ʈ
	@Override
	public List<Comment> commentList(int board_num, int page) {
		int startrow = 1;
		int endrow = page * 3;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board_num", board_num);
		map.put("startrow", startrow);
		map.put("endrow", endrow);
		return dao.commentList(map);
	}
	
	
	// ��� ���
	@Override
	public int commentAdd(Comment comm) {
		return dao.commentAdd(comm);
	}
	
	// ��� ����
	@Override
	public int commentUpdate(Comment comm) {
		return dao.commentUpdate(comm);
	}
	
	// ��� ����
	@Override
	public int commentDelete(int num) {
		return dao.commentDelete(num);
	}
	
}
