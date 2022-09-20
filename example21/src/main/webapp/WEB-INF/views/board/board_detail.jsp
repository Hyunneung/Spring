<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- 코어라이브러리 사용 -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> <!-- 시큐리티 태그 라이브러리 -->
<html>
<head>
<title>MVC 게시판 - view</title>
<jsp:include page="header.jsp" />
<script src="../resources/js/jquery-3.6.0.js"></script>
<script src="../resources/js/boarddetail.js" charset="UTF-8"></script>
<style>
body > div > table > tbody >tr:nth-child(1) {
	text-align: center
}

td:nth-child(1) {
	width: 20%
}

a {
	color: white
}

body > div > table > tbody tr:last-child {
	text-align: center;
}

.btn-primary {
	background-color: #4f97e5
}

#myModal {
	display: none
}

#comment > table > tbody > tr > td:nth-child(2){
 width:60%
}
#count{
    position: relative;
    top: -10px;
    left: -10px;
    background: orange;
    color: white;
    border-radius: 30%;
}

textarea{resize:none}

form[action=down] > input[type=submit]{
    position: relative;
    top: -20px;
    left: 10px;
    border: none;
    cursor : pointer;
}

</style>
<script>
	$(function(){
		/* 삭제모달 */
		$("form[name=deleteFrom]").submit(function(){
			if($("#board_pass").val() == "") {
				$("#board_pass").focus();
				alert("비밀번호를 입력하세요");
				return false;
			}
		})	
	}) // ready end
	
	
	var state = "${state}";
	if(state == 'passFail'){
		alert("비밀번호가 일치하지 않습니다.");
	}
</script>
</head>
<body>
	<div class="container">
		<table class="table table-striped">
			<tr>
				<th colspan="2">MVC 게시판 - view 페이지</th>
			</tr>
			<tr>
				<td><div>글쓴이</div></td>
				<td><div>${board.BOARD_NAME}</div></td>
			</tr>
			<tr>
				<td><div>제목</div></td>
				<td><div><c:out value="${board.BOARD_SUBJECT}"/></div></td>
			</tr>
			<tr>
				<td><div>내용</div></td>
				<td style="padding-right:0px">
					<textarea class="form-control" rows="5" readOnly>${board.BOARD_CONTENT}</textarea>
				</td>
			</tr>
			
			<!-- 원문글인 경우에만 첨부파일 추가 가능 -->
			<c:if test="${board.BOARD_RE_LEV == 0 }">
				<tr>
					<td><div>첨부파일</div></td>
					
					<!-- 파일 첨부한 경우 -->
					<c:if test="${!empty board.BOARD_FILE}"> 
						<td>
							<img src="../resources/image/down.png" width="10px">
							<form method="post" action="down" style="height:0px"> <!-- 파일 다운로드 -->
								<input type="hidden" value="${board.BOARD_FILE}" name="filename">
								<input type="hidden" value="${board.BOARD_ORIGINAL}" name="original">
								<input type="submit" value="${board.BOARD_ORIGINAL}">
							</form>
						</td>
					</c:if>
					
					<!-- 파일 첨부하지 않은 경우 -->
					<c:if test="${empty board.BOARD_FILE}">
						<td></td>
					</c:if>
				</tr>			
			</c:if>
			
			<!-- 댓글 수정 삭제 답변 목록 버튼 -->
			<tr>
				<td colspan="2" class="center">
					<button class="btn btn-primary">댓글</button>
					<span id="count">${commentListCount}</span> <!-- 댓글 수 -->
					
					<!-- 글쓴이이거나 관리자인 경우 글 수정, 삭제 가능 -->
					<sec:authorize access="isAuthenticated()">
						<sec:authentication property="principal" var="pinfo" /> 
						<c:if test="${board.BOARD_NAME == pinfo.username || pinfo.username == 'admin'}">
							<!-- 글 수정 버튼 -->
							<a href="modifyView?num=${board.BOARD_NUM}">
								<button class="btn btn-warning">수정</button>
							</a>
							
							<!-- 글 삭제 버튼: 모달창 발생 -->
							<a href="#">
								<button class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">삭제</button>
							</a>
						</c:if>
					</sec:authorize>			
						
					<a href="replyView?num=${board.BOARD_NUM}">
						<button class="btn btn-info">답변</button>
					</a>
					<a href="list">
						<button class="btn btn-success">목록</button>
					</a>
				</td>
			</tr>
		</table>
		<!-- 게시판 view end -->
		
		
		<!-- 게시글 삭제 모달 -->
		<div class="modal" id="deleteModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<form action="delete" method="post" name="deleteFrom">
							<input type="hidden" value="${board.BOARD_NUM}" name="board_num" id="board_num"> <!-- 삭제할 글의 번호 -->
							<div class="form-group">
								<!-- 글 삭제하려면 글 작성 시 설정한 비밀번호 입력해야 한다 -->
								<label for="password">비밀번호</label>
								<input type="password" class="form-control" placeholder="Enter password..." name="BOARD_PASS" id="board_pass">
							</div>
							<button type="submit" class="btn btn-primary">삭제</button>
							<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- 게시글 삭제 모달 end -->
		
		
		<!-- 댓글창 -->
		<div id="comment">
			<button class="btn btn-info float-left">총 50자까지 가능합니다.</button>
			<button class="btn btn-info float-right" id="write">등록</button>
			<textarea rows="3" class="form-control" id="content" maxlength="50"></textarea>
			
			<!-- 작성된 댓글들 -->
			<table class="table table-striped">
				<thead>
					<tr>
						<td>아이디</td><td>내용</td><td>날짜</td>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			
			<div id="commentMessage" style="cursor:pointer"></div>
		</div>
	</div>	
</body>
</html>