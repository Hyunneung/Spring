<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> <!-- 시큐리티 태그 라이브러리 -->
<!DOCTYPE html>
<html>
<head>
<title>글 쓰 기</title>
<jsp:include page="header.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/writeform.js"></script>
<style>
	h1{font-size:1.5em; text-align:center; color:#1a92b9}
	.container{width:60%}
	label{font-weight:bold}
	#upfile{display:none}
	img{width:20px}
</style>
</head>
<body>
	<div class="container">
		<form action="add" method="post" enctype="multipart/form-data" name="boardForm">
			<h1>MVC 게시판 - write 페이지</h1>
			
			<sec:authorize access="isAuthenticated()">
				<sec:authentication property="principal" var="pinfo" />
				
				<div class="form-group">
					<label for="board_name">글쓴이</label>
					<input type="text" class="form-control" name="BOARD_NAME" id="board_name" value="${pinfo.username}" readonly>
				</div>
				<div class="form-group">
					<label for="board_pass">비밀번호</label>
					<input type="password" class="form-control" name="BOARD_PASS" id="board_pass" placeholder="Enter board_pass">
				</div>
				<div class="form-group">
					<label for="board_subject">제목</label>
					<input type="text" class="form-control" name="BOARD_SUBJECT" id="board_subject" placeholder="Enter board_subject">
				</div>
				<div class="form-group">
					<label for="board_content">내용</label>
					<textarea name="BOARD_CONTENT" id="board_content" cols="70" rows="10" class="form-control"></textarea>
				</div>
				<div class="form-group">
					<label for="board_file">파일첨부</label>
					<label for="upfile">
						<img src="${pageContext.request.contextPath}/resources/image/attach.png" alt="파일첨부">
					</label>
					<input id="upfile" name="uploadfile" type="file">
					<span id="filevalue"></span>
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-primary">등록</button>
					<button type="reset" class="btn btn-danger">취소</button>
				</div>
				
				
				<!-- 토큰 설정 -->
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">	
			</sec:authorize>
		</form>
	</div> 
</body>
</html>