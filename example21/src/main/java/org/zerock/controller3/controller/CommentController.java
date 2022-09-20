package org.zerock.controller3.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.controller3.domain.Comment;
import org.zerock.controller3.service.CommentService;

@RestController
@RequestMapping(value="/comment")
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	private CommentService commentService;
	
	@Autowired // �ڵ�����
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	
	// ��� ����Ʈ
	//@ResponseBody
	@PostMapping("/list")
	public Map<String, Object> commentList(int board_num, int page) {
		List<Comment> list = commentService.commentList(board_num, page); // ��� ����Ʈ
		int listcount = commentService.getCommentListCount(board_num); // ��� ��
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentList", list);
		map.put("listcount", listcount);
		return map;
	}
	
	// ��� ���
	//@ResponseBody
	@PostMapping("/add")
	public int commentAdd(Comment comm) { // ajax�� �Ѿ�� content, id, board_num �����͸� Comment ��ü�� �� ���� ���� �� �ִ�
		int result = commentService.commentAdd(comm);
		if(result == 1) {
			logger.info("��� ��� ����");
		} else {
			logger.info("��� ��� ����");
		}
		return result;
	}
	
	// ��� ����
	//@ResponseBody
	@PostMapping(value="/update")
	public int commentUpdate(Comment comm) { // ajax�� �Ѿ�� content, num �����͸� Comment ��ü�� �� ���� ���� �� �ִ�
		int result = commentService.commentUpdate(comm);
		if(result == 1) {
			logger.info("��� ���� ����");
		} else {
			logger.info("��� ���� ����");
		}
		return result;
	}
	
	// ��� ����
	//@ResponseBody
	@PostMapping("/delete")
	public int commentDelete(int num) {
		int result = commentService.commentDelete(num);
		if(result == 1) {
			logger.info("��� ���� ���� => " + result);
		} else {
			logger.info("��� ���� ���� => " + result);
		}
		return result;
	}
}
