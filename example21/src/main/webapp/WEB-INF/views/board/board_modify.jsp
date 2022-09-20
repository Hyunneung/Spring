<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- 코어라이브러리 사용 -->
<html>
<head>
<title>MVC 게시판 - modify</title>
<jsp:include page="header.jsp" />
<script src="../resources/js/jquery-3.6.0.js"></script>
<script src="../resources/js/boardmodify.js" charset="UTF-8"></script>
<style>
	.container{width:60%}
	h1{font-size:1.5em; text-align:center; color:#1a92b9}
	#upfile{display:none}
</style>
<script>
	/* 수정 폼 제출 시 비밀번호 틀렸을 때 다시 수정폼으로 돌아오고 틀렸다는 문구 나온다 */
	var state = "${state}";
	if(state == 'passFail'){
		alert("비밀번호가 일치하지 않습니다.");
	}
</script>
</head>
<body>
	<div class="container">
		<form action="modifyProcess" method="post" name="modifyForm" enctype="multipart/form-data">
			<input type="hidden" name="BOARD_NUM" value="${board.BOARD_NUM}">
			<input type="hidden" name="BOARD_FILE" value="${board.BOARD_FILE}">
			
			<h1>MVC 게시판 - 수정</h1>
			<div class="form-group">
				<label for="board_name">글쓴이</label>
				<input type="text" class="form-control" name="BOARD_NAME" value="${board.BOARD_NAME}" readOnly>
			</div>
			<div class="form-group">
				<label for="board_subject">제목</label>
				<input type="text" class="form-control" name="BOARD_SUBJECT" id="board_subject" value="${board.BOARD_SUBJECT}">
			</div>
			<div class="form-group">
				<label for="board_content">내용</label>
				<textarea class="form-control" rows="15" name="BOARD_CONTENT" id="board_content">${board.BOARD_CONTENT}</textarea>
			</div>
			
			<!-- 원문글인 경우에만 파일 첨부 수정 가능 -->
			<c:if test="${board.BOARD_RE_LEV == 0 }">
				<div class="form-group">
					<label for="board_file">파일 첨부</label>
					<label for="upfile">
						<img src="${pageContext.request.contextPath}/resources/image/attach.png" alt="파일첨부" width="20px">
					</label>
					<input id="upfile" name="uploadfile" type="file">
					<span id="upfilename">${board.BOARD_ORIGINAL}</span>
					
					<img src="../resources/image/remove.png" alt="파일삭제" width="10px" class="remove">
				</div>
			</c:if>
			
			<!-- 글 작성 시 입력했던 비밀번호 맞아야만 수정 가능 -->
			<div class="form-group">
				<label for="board_pass">비밀번호</label>
				<input name="BOARD_PASS" id="board_pass" type="password" size="10" class="form-control" placeholder="Enter password">
			</div>
			
			<!-- 수정, 취소 버튼 -->
			<div class="form-group">
				<button type="submit" class="btn btn-primary">수정</button>
				<button type="button" class="btn btn-danger" onclick="history.do(-1)">취소</button>
			</div>
		</form>
	</div>	
</body>
</html>