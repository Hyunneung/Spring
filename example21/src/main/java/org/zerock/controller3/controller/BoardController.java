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
	
	@Autowired // ������ �ڵ�����
	public BoardController (BoardService boardService, CommentService commentService) {
		this.boardService = boardService;
		this.commentService = commentService;
	}
	
	@Value("#{savefolder['savefoldername']}")
	private String saveFolder;
	
	
	// �Խ���
	// �⺻ ������
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView mv, @RequestParam(value="page", defaultValue="1", required=false) int page) {
		int limit = 10; // �� �������� ������ �Խñ� ��
		int listcount = boardService.getListCount(); // �� �Խñ� ��
		int maxpage = (listcount + limit - 1) / limit; // �� ������ ��
		int startpage = ((page - 1) / 10) * 10 + 1; // ���� �������� ������ ���� ������ ��ȣ
		int endpage = startpage + 10 - 1; // ���� �������� ������ ������ ������ ��ȣ
		
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
	// �ٺ��� ����
	@ResponseBody // ajax ���
	@RequestMapping("/list_ajax")
	public Map<String, Object> boardListAjax(@RequestParam(value="page", defaultValue="1", required=false) int page,
											 @RequestParam(value="limit", defaultValue="10", required=false) int limit){
		int listcount = boardService.getListCount(); // �� �Խñ� ��
		int maxpage = (listcount + limit - 1) / limit; // �� ������ ��
		int startpage = ((page - 1) / 10) * 10 + 1; // ���� �������� ������ ���� ������ ��ȣ
		int endpage = startpage + 10 - 1; // ���� �������� ������ ������ ������ ��ȣ
		
		if(endpage > maxpage) {
			endpage = maxpage;
		}
		
		List<Board> boardlist = boardService.getList(page, limit);
		Map<String, Object> map = new HashMap<String, Object>(); // Map ����� Ű,����� ���
		map.put("page", page);
		map.put("limit", limit);
		map.put("listcount", listcount);
		map.put("maxpage", maxpage);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("boardlist", boardlist);
		return map; // @ResponseBody�� ���ڿ��� �ش� �ּҷ� ������
    }
	
	// �� �󼼺���
	@RequestMapping("/detail")
	public ModelAndView getDetail(@RequestParam(value="num") int num, ModelAndView mv,
								  @RequestHeader(value="referer") String beforeURL, HttpServletRequest request) {
		logger.info("referer = " + beforeURL); // ���� �ּҰ�
		if(beforeURL.endsWith("list")) { // �Խñ����������� �� ������ ���� ��ȸ�� ����
			boardService.setReadCountUpdate(num);
		}
		
		Board board = boardService.getDetail(num);
		if(board != null) {
			logger.info("�󼼺��� ����");
			int commentListCount = commentService.getCommentListCount(num); // ��� ��
			mv.addObject("commentListCount", commentListCount);
			mv.addObject("board", board);
			mv.setViewName("board/board_detail");
		} else {
			logger.info("�󼼺��� ����");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "�󼼺��� �����Դϴ�.");
			mv.setViewName("error/error");
		}
		return mv;
	}
	
	// �� ���� �� �̵�
	@GetMapping(value="/modifyView")
	public ModelAndView modifyView(int num, ModelAndView mv, HttpServletRequest request) {
		Board board = boardService.getDetail(num); // �� ���� ���� ���� �ۼ��� ����� ������
		
		if(board == null) {
			logger.info("�������� ����");
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "�������� �����Դϴ�.");
		} else {
			logger.info("�������� ����");
			mv.addObject("board", board);
			mv.setViewName("board/board_modify");
		}
		return mv;
	}
	// �� ����
	@PostMapping("/modifyProcess")
	public String modifyProcess(Board board, String check, RedirectAttributes rattr,
							    HttpServletRequest request, Model model) throws Exception {
		// ��й�ȣ �´��� Ȯ��
		boolean usercheck = boardService.isBoardWriter(board.getBOARD_NUM(), board.getBOARD_PASS());
		
		if(usercheck == false) { // ��й�ȣ�� �ٸ� ���
			rattr.addFlashAttribute("state", "passFail");
			rattr.addAttribute("num", board.getBOARD_NUM());
			return "redirect:modifyView";
		} else { // ��й�ȣ ��ġ�ϴ� ���
			MultipartFile uploadfile = board.getUploadfile();
			
			// ���� ���� �״�� ����ϴ� ���
			if(check != null && !check.equals("")) {
				logger.info("���� ���� �״�� ���");
				board.setBOARD_ORIGINAL(check);
			} else {
				// ���� ����� ���
				if(uploadfile != null && !uploadfile.equals("")) {
					logger.info("���� �����߽��ϴ�.");
					
					String fileName = uploadfile.getOriginalFilename(); // ���� ���ϸ�
					board.setBOARD_ORIGINAL(fileName);
					
					String fileDBName = fileDBName(fileName, saveFolder);
					logger.info("fileDBName = " + fileDBName);
					
					// ���ε��� ������ �Ű������� ��ο� ����
					uploadfile.transferTo(new File(saveFolder + fileDBName));
					logger.info("filetransferTo path = " + saveFolder + fileDBName);
					
					// �ٲ� ���ϸ����� ����
					board.setBOARD_FILE(fileDBName);
				} else {
					// ���� ���� ���� ���� ���ϵ� ���� ��� or ���� ���� �־��µ� ������ ���
					logger.info("���õ� ������ �����ϴ�");
					board.setBOARD_FILE("");
					board.setBOARD_ORIGINAL("");
				}
			}
			
			// �� ����
			int result = boardService.modifyProcess(board);
			
			// �� ���� ����
			if(result == 0) {
				logger.info("�Խ��� ���� ����");
				model.addAttribute("url", request.getRequestURL());
				model.addAttribute("messager", "�Խ��� ���� ����");
				return "error/error";
			} else {
				// �� ���� ����
				logger.info("�Խ��� ���� ����");
				rattr.addAttribute("num", board.getBOARD_NUM());
				return "redirect:detail";
			}
		}
	}
	
	
	
	
	// �۾��� �� �̵�
	@GetMapping(value="/write")
	public String board_write() {
		return "board/board_write";
	}
	// �۾���
	@PostMapping(value="/add")
	public String add(Board board, HttpServletRequest request) throws Exception {
		MultipartFile uploadfile = board.getUploadfile(); // board_write.jsp���� ����� <input type="file" id="upfile" name="uploadfile">
		
		if(!uploadfile.isEmpty()) { // ���� ÷�� �� ���
			String fileName = uploadfile.getOriginalFilename(); // ���� ���ϸ�
			board.setBOARD_ORIGINAL(fileName); // board ��ü�� ���� ���ϸ� ����
			String fileDBName = fileDBName(fileName, saveFolder);
			
			// ���ε��� ������ �Ű����� ��ο� ����
			uploadfile.transferTo(new File(saveFolder + fileDBName));
			logger.info("fileDBName = " + fileDBName);
			
			board.setBOARD_FILE(fileDBName); // �ٲ� ���ϸ����� ����
		}
		boardService.writeBoard(board);
		logger.info(board.toString());
		return "redirect:list";
	}
	// ������ ���ϸ� ����
	public String fileDBName(String fileName, String saveFolder) {
		// ���ο� ���� �̸� ����� : ���� ������
		Calendar c = Calendar.getInstance(); // ���� ��¥
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1; // 0�������̹Ƿ� ����� +1 ����� ��
		int date = c.get(Calendar.DATE);
		String homedir = saveFolder + "/" + year + "-" + month + "-" + date; // ���ο� ���� �̸�
		logger.info(homedir);
		
		File path1 = new File(homedir);
		if(!(path1.exists())) {
			path1.mkdir(); // path1�� ������ ���ο� ���� ����
		}
		
		Random ran = new Random();
		int random = ran.nextInt(100000000); // 0~100000000 �̸��� ������ ���� ��ȯ
		
		// Ȯ���� ���ϱ�
		int index = fileName.lastIndexOf(".");
		logger.info("index = " + index);
		String fileExtension = fileName.substring(index + 1); // Ȯ���ڴ� . ���������̹Ƿ� +1 ���ش�
		logger.info("fileExtension = " + fileExtension);
		
		// ���ο� ���ϸ�
		String refileName = "bbs" + year + month + date + random + "." + fileExtension;
		logger.info("refileName = " + refileName);
		
		// ����Ŭ DB�� ����� ���ϸ�
		String fileDBName = File.separator + year + "-" + month + "-" + date + File.separator + refileName;
		logger.info("fileDBName = " + fileDBName);
		return fileDBName;
	}
	
	// �亯�� �� �̵�
	@GetMapping("/replyView")
	public ModelAndView replyView(int num, ModelAndView mv, HttpServletRequest request) {
		Board board = boardService.getDetail(num);
		
		if(board == null) {
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "�Խ��� �亯�� �������� ����");
		} else {
			mv.addObject("board", board);
			mv.setViewName("board/board_reply");
		}
		return mv;
	}
	// �亯�� �ۼ�
	@PostMapping("/replyProcess")
	public ModelAndView replyProcess(Board board, ModelAndView mv, HttpServletRequest request, RedirectAttributes rattr) {
		int result = boardService.replyProcess(board);
		if(result == 1) {
			rattr.addAttribute("num", board.getBOARD_NUM());
			mv.setViewName("redirect:list");
		} else {
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "�亯�� �ۼ� ����");
		}
		return mv;
	}
	
	// �� ����
	@PostMapping("/delete")
	public String deleteBoard(String BOARD_PASS, int board_num,
						 RedirectAttributes rattr, Model model, HttpServletRequest request) {
		// ��й�ȣ �´��� Ȯ��
		boolean usercheck = boardService.isBoardWriter(board_num, BOARD_PASS);
		
		// ��й�ȣ ��ġ���� �ʴ� ���
		if(usercheck == false) {
			rattr.addFlashAttribute("state", "passFail");
			rattr.addAttribute("num", board_num);
			return "redirect:detail";
		} else {
			// ��й�ȣ ��ġ�ϴ� ��� ���� ����
			int result = boardService.deleteBoard(board_num);
			
			// ���� ����
			if(result == 0) {
				logger.info("�Խ��� ���� ����");
				model.addAttribute("url", request.getRequestURL());
				model.addAttribute("message", "�Խ��� ���� ����");
				return "error/error";
			} else {
				// ���� ����
				logger.info("�Խ��� ���� ����");
				rattr.addFlashAttribute("state", "deleteSuccess");
				return "redirect:list";
			}
		}
	}
	
	
	// ���� �ٿ�ε�
	@ResponseBody
	@PostMapping("/down")
	public byte[] boardFileDown(String filename, HttpServletRequest request,
								String original, HttpServletResponse response ) throws Exception{
		
		String sFilePath = saveFolder + filename;
		
		File file = new File(sFilePath);
		
		byte[] bytes = FileCopyUtils.copyToByteArray(file);
		
		String sEncoding = new String(original.getBytes("utf-8"), "ISO-8859-1");
		
		// Content-Disposition: attachment: �������� �ش� Content�� ó������ �ʰ�, �ٿ�ε� �ϰ� �˴ϴ�.
		response.setHeader("Content-Disposition", "attachment;filename=" + sEncoding);
		response.setContentLength(bytes.length);
		
		return bytes;
	}
	
	
}
