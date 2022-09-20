package org.zerock.controller3.controller;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.controller3.domain.Board;
import org.zerock.controller3.service.BoardService;
import org.zerock.controller3.service.CommentService;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private BoardService boardService;
	private CommentService commentService;
	
	@Autowired // 생성자 자동주입
	public BoardController (BoardService boardService, CommentService commentService) {
		this.boardService = boardService;
		this.commentService = commentService;
	}
	
	@Value("#{savefolder['savefoldername']}")
	private String saveFolder;
	
	
	// 게시판
	// 기본 페이지
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView mv, @RequestParam(value="page", defaultValue="1", required=false) int page) {
		int limit = 10; // 한 페이지에 보여줄 게시글 수
		int listcount = boardService.getListCount(); // 총 게시글 수
		int maxpage = (listcount + limit - 1) / limit; // 총 페이지 수
		int startpage = ((page - 1) / 10) * 10 + 1; // 현재 페이지에 보여줄 시작 페이지 번호
		int endpage = startpage + 10 - 1; // 현재 페이지에 보여줄 마지막 페이지 번호
		
		if(endpage > maxpage) {
			endpage = maxpage;
		}
		
		List<Board> boardlist = boardService.getList(page, limit);
		
		mv.addObject("page", page);
		mv.addObject("limit", limit);
		mv.addObject("listcount", listcount);
		mv.addObject("maxpage", maxpage);
		mv.addObject("startpage", startpage);
		mv.addObject("endpage", endpage);
		mv.addObject("boardlist", boardlist);
		mv.setViewName("board/board_list");
		return mv;
	}
	// 줄보기 변경
	@ResponseBody // ajax 통신
	@RequestMapping("/list_ajax")
	public Map<String, Object> boardListAjax(@RequestParam(value="page", defaultValue="1", required=false) int page,
											 @RequestParam(value="limit", defaultValue="10", required=false) int limit){
		int listcount = boardService.getListCount(); // 총 게시글 수
		int maxpage = (listcount + limit - 1) / limit; // 총 페이지 수
		int startpage = ((page - 1) / 10) * 10 + 1; // 현재 페이지에 보여줄 시작 페이지 번호
		int endpage = startpage + 10 - 1; // 현재 페이지에 보여줄 마지막 페이지 번호
		
		if(endpage > maxpage) {
			endpage = maxpage;
		}
		
		List<Board> boardlist = boardService.getList(page, limit);
		Map<String, Object> map = new HashMap<String, Object>(); // Map 사용해 키,밸류로 담기
		map.put("page", page);
		map.put("limit", limit);
		map.put("listcount", listcount);
		map.put("maxpage", maxpage);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("boardlist", boardlist);
		return map; // @ResponseBody는 문자열만 해당 주소로 보낸다
    }
	
	// 글 상세보기
	@RequestMapping("/detail")
	public ModelAndView getDetail(@RequestParam(value="num") int num, ModelAndView mv,
								  @RequestHeader(value="referer") String beforeURL, HttpServletRequest request) {
		logger.info("referer = " + beforeURL); // 이전 주소값
		if(beforeURL.endsWith("list")) { // 게시글페이지에서 글 눌렀을 때만 조회수 증가
			boardService.setReadCountUpdate(num);
		}
		
		Board board = boardService.getDetail(num);
		if(board != null) {
			logger.info("상세보기 성공");
			int commentListCount = commentService.getCommentListCount(num); // 댓글 수
			mv.addObject("commentListCount", commentListCount);
			mv.addObject("board", board);
			mv.setViewName("board/board_detail");
		} else {
			logger.info("상세보기 실패");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "상세보기 실패입니다.");
			mv.setViewName("error/error");
		}
		return mv;
	}
	
	// 글 수정 폼 이동
	@GetMapping(value="/modifyView")
	public ModelAndView modifyView(int num, ModelAndView mv, HttpServletRequest request) {
		Board board = boardService.getDetail(num); // 글 수정 위해 기존 작성한 내용들 가져옴
		
		if(board == null) {
			logger.info("수정보기 실패");
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "수정보기 실패입니다.");
		} else {
			logger.info("수정보기 성공");
			mv.addObject("board", board);
			mv.setViewName("board/board_modify");
		}
		return mv;
	}
	// 글 수정
	@PostMapping("/modifyProcess")
	public String modifyProcess(Board board, String check, RedirectAttributes rattr,
							    HttpServletRequest request, Model model) throws Exception {
		// 비밀번호 맞는지 확인
		boolean usercheck = boardService.isBoardWriter(board.getBOARD_NUM(), board.getBOARD_PASS());
		
		if(usercheck == false) { // 비밀번호가 다른 경우
			rattr.addFlashAttribute("state", "passFail");
			rattr.addAttribute("num", board.getBOARD_NUM());
			return "redirect:modifyView";
		} else { // 비밀번호 일치하는 경우
			MultipartFile uploadfile = board.getUploadfile();
			
			// 기존 파일 그대로 사용하는 경우
			if(check != null && !check.equals("")) {
				logger.info("기존 파일 그대로 사용");
				board.setBOARD_ORIGINAL(check);
			} else {
				// 파일 변경된 경우
				if(uploadfile != null && !uploadfile.equals("")) {
					logger.info("파일 변경했습니다.");
					
					String fileName = uploadfile.getOriginalFilename(); // 원래 파일명
					board.setBOARD_ORIGINAL(fileName);
					
					String fileDBName = fileDBName(fileName, saveFolder);
					logger.info("fileDBName = " + fileDBName);
					
					// 업로드한 파일을 매개변수의 경로에 저장
					uploadfile.transferTo(new File(saveFolder + fileDBName));
					logger.info("filetransferTo path = " + saveFolder + fileDBName);
					
					// 바뀐 파일명으로 저장
					board.setBOARD_FILE(fileDBName);
				} else {
					// 기존 파일 없고 수정 파일도 없는 경우 or 기존 파일 있었는데 삭제한 경우
					logger.info("선택된 파일이 없슨니다");
					board.setBOARD_FILE("");
					board.setBOARD_ORIGINAL("");
				}
			}
			
			// 글 수정
			int result = boardService.modifyProcess(board);
			
			// 글 수정 실패
			if(result == 0) {
				logger.info("게시판 수정 실패");
				model.addAttribute("url", request.getRequestURL());
				model.addAttribute("messager", "게시판 수정 실패");
				return "error/error";
			} else {
				// 글 수정 성공
				logger.info("게시판 수정 성공");
				rattr.addAttribute("num", board.getBOARD_NUM());
				return "redirect:detail";
			}
		}
	}
	
	
	
	
	// 글쓰기 폼 이동
	@GetMapping(value="/write")
	public String board_write() {
		return "board/board_write";
	}
	// 글쓰기
	@PostMapping(value="/add")
	public String add(Board board, HttpServletRequest request) throws Exception {
		MultipartFile uploadfile = board.getUploadfile(); // board_write.jsp에서 저장된 <input type="file" id="upfile" name="uploadfile">
		
		if(!uploadfile.isEmpty()) { // 파일 첨부 된 경우
			String fileName = uploadfile.getOriginalFilename(); // 원래 파일명
			board.setBOARD_ORIGINAL(fileName); // board 객체에 원래 파일명 저장
			String fileDBName = fileDBName(fileName, saveFolder);
			
			// 업로드한 파일을 매개변수 경로에 저장
			uploadfile.transferTo(new File(saveFolder + fileDBName));
			logger.info("fileDBName = " + fileDBName);
			
			board.setBOARD_FILE(fileDBName); // 바뀐 파일명으로 저장
		}
		boardService.writeBoard(board);
		logger.info(board.toString());
		return "redirect:list";
	}
	// 가공한 파일명 저장
	public String fileDBName(String fileName, String saveFolder) {
		// 새로운 폴더 이름 만들기 : 오늘 연월일
		Calendar c = Calendar.getInstance(); // 오늘 날짜
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1; // 0월부터이므로 당월은 +1 해줘야 함
		int date = c.get(Calendar.DATE);
		String homedir = saveFolder + "/" + year + "-" + month + "-" + date; // 새로운 폴더 이름
		logger.info(homedir);
		
		File path1 = new File(homedir);
		if(!(path1.exists())) {
			path1.mkdir(); // path1이 없으면 새로운 폴더 생성
		}
		
		Random ran = new Random();
		int random = ran.nextInt(100000000); // 0~100000000 미만의 정수형 난수 반환
		
		// 확장자 구하기
		int index = fileName.lastIndexOf(".");
		logger.info("index = " + index);
		String fileExtension = fileName.substring(index + 1); // 확장자는 . 다음부터이므로 +1 해준다
		logger.info("fileExtension = " + fileExtension);
		
		// 새로운 파일명
		String refileName = "bbs" + year + month + date + random + "." + fileExtension;
		logger.info("refileName = " + refileName);
		
		// 오라클 DB에 저장될 파일명
		String fileDBName = File.separator + year + "-" + month + "-" + date + File.separator + refileName;
		logger.info("fileDBName = " + fileDBName);
		return fileDBName;
	}
	
	// 답변글 폼 이동
	@GetMapping("/replyView")
	public ModelAndView replyView(int num, ModelAndView mv, HttpServletRequest request) {
		Board board = boardService.getDetail(num);
		
		if(board == null) {
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "게시판 답변글 가져오기 실패");
		} else {
			mv.addObject("board", board);
			mv.setViewName("board/board_reply");
		}
		return mv;
	}
	// 답변글 작성
	@PostMapping("/replyProcess")
	public ModelAndView replyProcess(Board board, ModelAndView mv, HttpServletRequest request, RedirectAttributes rattr) {
		int result = boardService.replyProcess(board);
		if(result == 1) {
			rattr.addAttribute("num", board.getBOARD_NUM());
			mv.setViewName("redirect:list");
		} else {
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "답변글 작성 실패");
		}
		return mv;
	}
	
	// 글 삭제
	@PostMapping("/delete")
	public String deleteBoard(String BOARD_PASS, int board_num,
						 RedirectAttributes rattr, Model model, HttpServletRequest request) {
		// 비밀번호 맞는지 확인
		boolean usercheck = boardService.isBoardWriter(board_num, BOARD_PASS);
		
		// 비밀번호 일치하지 않는 경우
		if(usercheck == false) {
			rattr.addFlashAttribute("state", "passFail");
			rattr.addAttribute("num", board_num);
			return "redirect:detail";
		} else {
			// 비밀번호 일치하는 경우 삭제 실행
			int result = boardService.deleteBoard(board_num);
			
			// 삭제 실패
			if(result == 0) {
				logger.info("게시판 삭제 실패");
				model.addAttribute("url", request.getRequestURL());
				model.addAttribute("message", "게시판 삭제 실패");
				return "error/error";
			} else {
				// 삭제 성공
				logger.info("게시판 삭제 성공");
				rattr.addFlashAttribute("state", "deleteSuccess");
				return "redirect:list";
			}
		}
	}
	
	
	// 파일 다운로드
	@ResponseBody
	@PostMapping("/down")
	public byte[] boardFileDown(String filename, HttpServletRequest request,
								String original, HttpServletResponse response ) throws Exception{
		
		String sFilePath = saveFolder + filename;
		
		File file = new File(sFilePath);
		
		byte[] bytes = FileCopyUtils.copyToByteArray(file);
		
		String sEncoding = new String(original.getBytes("utf-8"), "ISO-8859-1");
		
		// Content-Disposition: attachment: 브라우저는 해당 Content를 처리하지 않고, 다운로드 하게 됩니다.
		response.setHeader("Content-Disposition", "attachment;filename=" + sEncoding);
		response.setContentLength(bytes.length);
		
		return bytes;
	}
	
	
}
