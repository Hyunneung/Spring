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
	
	@Autowired // 자동주입
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	
	// 댓글 리스트
	//@ResponseBody
	@PostMapping("/list")
	public Map<String, Object> commentList(int board_num, int page) {
		List<Comment> list = commentService.commentList(board_num, page); // 댓글 리스트
		int listcount = commentService.getCommentListCount(board_num); // 댓글 수
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentList", list);
		map.put("listcount", listcount);
		return map;
	}
	
	// 댓글 등록
	//@ResponseBody
	@PostMapping("/add")
	public int commentAdd(Comment comm) { // ajax로 넘어온 content, id, board_num 데이터를 Comment 객체로 한 번에 받을 수 있다
		int result = commentService.commentAdd(comm);
		if(result == 1) {
			logger.info("댓글 등록 성공");
		} else {
			logger.info("댓글 등록 실패");
		}
		return result;
	}
	
	// 댓글 수정
	//@ResponseBody
	@PostMapping(value="/update")
	public int commentUpdate(Comment comm) { // ajax로 넘어온 content, num 데이터를 Comment 객체로 한 번에 받을 수 있다
		int result = commentService.commentUpdate(comm);
		if(result == 1) {
			logger.info("댓글 수정 성공");
		} else {
			logger.info("댓글 수정 실패");
		}
		return result;
	}
	
	// 댓글 삭제
	//@ResponseBody
	@PostMapping("/delete")
	public int commentDelete(int num) {
		int result = commentService.commentDelete(num);
		if(result == 1) {
			logger.info("댓글 삭제 성공 => " + result);
		} else {
			logger.info("댓글 삭제 실패 => " + result);
		}
		return result;
	}
}
