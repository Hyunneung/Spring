<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- 코어라이브러리 사용 -->
<html>
<head>
<jsp:include page="header.jsp"/>
<style>
	select.form-control {
		width:auto;
		margin-bottom:2em;
		display: inline-block;
	}
	
	.rows {
		text-align: right;
	}
	
	.gray {
		color:gray;
	}
	
	body > div > table > thead > tr:nth-child(2) > th:nth-child(1){width:8%}
	body > div > table > thead > tr:nth-child(2) > th:nth-child(2){width:50%}
	body > div > table > thead > tr:nth-child(2) > th:nth-child(3){width:14%}
	body > div > table > thead > tr:nth-child(2) > th:nth-child(4){width:17%}
	body > div > table > thead > tr:nth-child(2) > th:nth-child(5){width:11%}
</style>
<script>
	var state = "${state}";
	if(state == 'deleteSuccess'){
		alert("삭제 성공입니다.");
	} else if(state == 'updateSuccess'){
		alert("회원정보가 수정되었습니다.");
	}
</script>
<script src="../resources/js/boardlist.js"></script>
<title>MVC 게시판</title>
</head>
<body>
	<div class="container">
	
		<!-- 게시글이 있는 경우 -->
		<c:if test="${listcount != 0}">
			<div class="rows">
				<span>줄보기</span>
				<select class="form-control" id="viewcount">
					<option value="1">1</option>
					<option value="5">5</option>
					<option value="10" selected>10</option>
				</select>
			</div>
			
			<!-- 게시글 리스트 -->
			<table class="table table-striped">
				<thead>
					<tr>
						<th colspan="3">MVC 게시판 - list</th>
						<th colspan="2"><font size=3>글 개수: ${listcount}</font></th>
					</tr>
					<tr>
						<th><div>번호</div></th>
						<th><div>제목</div></th>
						<th><div>작성자</div></th>
						<th><div>날짜</div></th>
						<th><div>조회수</div></th>
					</tr>
				</thead>
				<tbody>
					<c:set var="num" value="${listcount - (page - 1)*limit}" /> <!-- 해당 페이지에 나올 첫 게시글 번호 -->
					<c:forEach var="list" items="${boardlist}">
						<tr>
							<td> <!-- 번호 -->
								<c:out value="${num}"/>
								<c:set var="num" value="${num-1}"/> <!-- 밑에 나오는 게시글 번호가 1씩 감소한다 -->
							</td>
							<td> <!-- 제목 -->
								<div>
									<!-- 답글인 경우 -->
									<c:if test="${list.BOARD_RE_LEV != 0}">
										<c:forEach var="LEV" begin="0" end="${list.BOARD_RE_LEV*2}" step="1">
											&nbsp;
										</c:forEach>
										<img src='${pageContext.request.contextPath}/resources/image/line.gif'>
									</c:if>
									
									<!-- 원문인 경우 -->
									<c:if test="${list.BOARD_RE_LEV == 0}">
										&nbsp;
									</c:if>
									
									<a href="detail?num=${list.BOARD_NUM }">
										<c:out value="${list.BOARD_SUBJECT}" escapeXml="true" />
										<span class="gray small">[<c:out value="${list.CNT}"/>]</span>
									</a>
								</div>
							</td>
							<td> <!-- 작성자 -->
								<c:out value="${list.BOARD_NAME }" />
							</td>
							<td> <!-- 등록 날짜 -->
								<c:out value="${list.BOARD_DATE }" />
							</td>
							<td> <!-- 조회수 -->
								<c:out value="${list.BOARD_READCOUNT }" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table> <!-- 게시글 리스트 end -->
			
			<!-- 페이지네이션 -->
			<div class="center-block">
				<!-- 페이지 버튼 활성화 여부 -->
				<ul class="pagination justify-content-center">
					<!-- 1. 이전페이지 버튼 활성화 여부 -->
					<c:if test="${page <= 1 }"> <!-- page(현재 페이지)가 1페이지보다 작으면 이동할 이전 페이지가 없다 (a태그에 href 없음) -->
						<li class="page-item">
							<a class="page-link gray">이전&nbsp;</a>
						</li>
					</c:if>
					<c:if test="${page > 1}"> <!-- page(현재 페이지)가 1페이지보다 크면 이동할 이전 페이지가 생긴다 -->
						<li class="page-item">
							<a href="list?page=${page-1}" class="page-link">이전&nbsp;</a> 
						</li>
					</c:if>

					<!-- 2. 페이지 번호 -->
					<c:forEach var="pageNumber" begin="${startpage}" end="${endpage}">
						<c:if test="${pageNumber == page }"> <!-- 선택된 페이지번호가 현재페이지인 경우 페이지번호 비활성화 (a태그에 href 없음) -->
							<li class="page-item active">
								<a class="page-link">${pageNumber}</a>
							</li>
						</c:if>
						<c:if test="${pageNumber != page }"> <!-- 선택된 페이지번호가 현재페이지가 아닌 경우 페이지번호 활성화 -->
							<li class="page-item">
								<a href="list?page=${pageNumber}" class="page-link">${pageNumber}</a>
							</li>
						</c:if>
					</c:forEach>
					
					<!-- 3. 다음페이지 버튼 활성화 여부 -->
					<c:if test="${page >= maxpage}"> <!-- page(현재 페이지)가 최대페이지보다 크면 이동할 다음 페이지가 없다 (a태그에 href 없음) -->
						<li class="page-item">
							<a class="page-link gray">&nbsp;다음</a>
						</li>
					</c:if>
					<c:if test="${page < maxpage}"> <!-- page(현재 페이지)가 최대페이지보다 작으면 이동할 다음 페이지가 생긴다 -->
						<li class="page-item">
							<a href="list?num=${page+1}" class="page-link">&nbsp;다음</a>
						</li>
					</c:if>
				</ul>
			</div> <!-- 페이지네이션 end -->
		</c:if> <!-- 게시글 있는 경우 end -->
		
		
		
		<!-- 게시글이 없는 경우 -->
		<c:if test="${listcount == 0}">
			<font size=5>등록된 글이 없습니다.</font>
		</c:if>
		
		
		<button type="button" class="btn btn-info float-right">글 쓰 기</button>	
	</div>
</body>
</html>