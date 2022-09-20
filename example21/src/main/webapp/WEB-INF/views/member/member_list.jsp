<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>회원관리 시스템 관리자모드(회원 목록 보기)</title>
<jsp:include page="../board/header.jsp" />
<style>
table caption {
	caption-side: top;
	text-align: center
}

h1 {
	text-align: center
}

li .gray {
	color: gray;
}

body>div>table>tbody>tr>td:last-child>a {
	color: red
}

form {
	margin: 0 auto;
	width: 80%;
	text-align: center
}

select {
	color: #495057;
	background-color: #fff;
	background-clip: padding-box;
	border: 1px solid #ced4da;
	border-radius: .25rem;
	transition: border-color .15s ease-in-out, box-shadow .15s ease-in-out;
	outline: none;
}

.container {
	width: 60%
}

td:nth-child(1) {
	width: 33%
}

.input-group {
	margin-bottom: 3em
}
</style>
<script>
	$(function(){
		// 1. 검색어를 입력한 후 다시 member_list.jsp으로 온 경우 검색 필드와 나타나도록 합니다.
		var index = '${index}';
		// index가 넘어온 경우 (넘어오지 않은 경우는 -1)
		if(index != -1) {
			$("#viewcount").val(index);
		}
		
		// 2. 검색필드 변경
		$("#viewcount").change(function(){
			index = $(this).val();
			$("input").val('');
			
			message = ['아이디를', '이름을', '나이를', '여 또는 남을']; // message 변수는 배열
			$("input").attr('placeholder', message[index] + " 입력하세요.");
		})
		
		
		// 3. 검색버튼 클릭
		$("form[name='searchForm']").submit(function(){
			// 1) 검색어 공백 검사
			if($("input").val() == ''){
				alert("검색어를 입력하세요.");
				$("input").focus();
				return false;
			}
			// 2) 검색어 형식 검사
			var search_word = $("input").val();
			if(index == 2) { // (1) 나이 검사
				pattern = /^[0-9]{2}$/;
				if(!pattern.test(search_word)){
					alert("나이는 형식에 맞게 입력하세요(2자리 숫자)");
					return false;
				}
			} else if(index == 3){ // (2) 성별 형식 검사
				if(search_word != '남' && search_word != '여') {
					alert("성별은 여 또는 남을 입력하세요");
					return false;
				}
			}
		}) // 검색버튼 클릭 end
		
		// 회원 삭제 성공
		var result = "${result}";
		var deleteId = "${deleteId}";
		console.log("삭제 결과 => " + result);
		console.log("삭제 아이디 => " + deleteId);
		if(result == "1") {
			alert(deleteId + "=> 회원 삭제 성공");
		}
	}) // ready end
</script>
</head>
<body>
	<div class="container">
		<form action="list" method="post" name="searchForm">
			<div class="input-group">
				<select id="viewcount" name="search_field">
					<option value="0" selected>아이디</option>
					<option value="1">이름</option>
					<option value="2">나이</option>
					<option value="3">성별</option>
				</select>
				<input type="text" name="search_word" class="form-control" value="${search_word}" placeholder="아이디를 입력하세요">
				<button type="submit" class="btn btn-primary">검색</button>
			</div>
		</form>
		
		<!-- 회원이 있는 경우 -->
		<c:if test="${listCount > 0}">
			<table class="table table-striped">
				<caption style="font-weight: bold">회원 목록</caption>
				<thead>
					<tr>
						<th colspan="2">MVC 게시판 - 회원 정보 list</th>
						<th><font size=3>회원수 : ${listCount}</font></th>
					</tr>
					<tr>
						<th>아이디</th>
						<th>이름</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="memberlist" items="${list}">
						<tr>
							<td><a href="info?id=${memberlist.id}">${memberlist.id}</a></td>
							<td>${memberlist.name}</td>
							<td><a href="delete?id=${memberlist.id}">삭제</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<!-- 페이지네이션 -->
			<div>
				<ul class="pagination justify-content-center">
					<!-- 현재페이지가 1페이지 이하인 경우 이동할 이전 페이지 없음 -->
					<c:if test="${page <= 1}">
						<li class="page-item">
							<a class="page-link gray">이전&nbsp;</a>
						</li>
					</c:if>
					
					<!-- 페이지가 1페이지 초과인 경우 -->
					<c:if test="${page > 1}">
						<li class="page-item">
							<a href="list?page=${page-1}&search_field=${search_field}&search_word=${search_word}"
							   class="page-link">이전&nbsp;</a>
						</li>
					</c:if>
					
					<c:forEach var="a" begin="${startpage}" end="${endpage}">
						<!-- 선택된 페이지 -->
						<c:if test="${a == page}">
							<li class="page-item active"><a class="page-link">${a}</a></li>
						</c:if>
						<!-- 선택 안 된 페이지 -->
						<c:if test="${a != page}">
							<c:url var="go" value="list">
								<c:param name="search_field" value="${search_field}" />
								<c:param name="search_word" value="${search_word}" />
								<c:param name="page" value="${a}" />
							</c:url>
							<li class="page-item"><a href="${go}" class="page-link">${a}</a></li>
						</c:if>
					</c:forEach>
					
					<!-- 현재페이지가 최대페이지보다 큰 경우 -->
					<c:if test="${page >= maxpage}">
						<li class="page-item"><a class="page-link gray">&nbsp;다음</a>
						</li>
					</c:if>
					<!-- 현재페이지가 최대페이지보다 작은 경우 -->
					<c:if test="${page < maxpage}">
						<c:url var="next" value="list">
							<c:param name="search_field" value="${search_field}" />
							<c:param name="search_word" value="${search_word}" />
							<c:param name="page" value="${page+1}" />
						</c:url>
						<li class="page-item"><a href="${next}" class="page-link">&nbsp;다음</a></li>
					</c:if>
				</ul>
			</div>
		</c:if>
		
		<!-- 회원이 없는 경우 -->
		<c:if test="${listCount == 0 && empty search_word}">
			<h1>회원이 없습니다.</h1>
		</c:if>
		<c:if test="${listCount == 0 && !empty search_word}">
			<h1>검색결과가 없습니다.</h1>
		</c:if>
	</div>
</body>
</html>