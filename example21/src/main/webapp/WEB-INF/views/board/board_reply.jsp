<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>MVC 게시판 - 답변글</title>
<jsp:include page="header.jsp"/>
<style>
	h1{font-size:1.5em; text-align:center; color:#1a92b9}
	label{font-weight:bold}
	.container{width:60%}
</style>
</head>
<body>
	<div class="container">
		<form action="replyProcess" method="post" name="replyForm">
			<input type="hidden" name="BOARD_RE_REF" value="${board.BOARD_RE_REF}">
			<input type="hidden" name="BOARD_RE_LEV" value="${board.BOARD_RE_LEV}">
			<input type="hidden" name="BOARD_RE_SEQ" value="${board.BOARD_RE_SEQ}">
			
			<h1>MVC 게시판 - 답변글</h1>
			<div class="form-group">
				<label for="board_name">글쓴이</label>
				<input type="text" value="${board.BOARD_NAME}" name="BOARD_NAME" id="board_name" class="form-control" readonly>
			</div>
			<div class="form-group">
				<label for="board_subject">제목</label>
				<input type="text" value="Re:${board.BOARD_SUBJECT}" name="BOARD_SUBJECT" id="board_subject" class="form-control">
			</div>
			<div class="form-group">
				<label for="board_content">내용</label>
				<textarea id="board_content" name="BOARD_CONTENT" rows="10" class="form-control"></textarea>
			</div>
			<div class="form-group">
				<label for="board_pass">비밀번호</label>
				<input type="password" name="BOARD_PASS" id="board_pass" class="form-control">
			</div>
			
			<div class="form-group">
				<button type="submit" class="btn btn-primary">등록</button>
				<button type="button" class="btn btn-danger" onclick="history.go(-1)">취소</button>
			</div>
		</form>
	</div>
</body>
</html>