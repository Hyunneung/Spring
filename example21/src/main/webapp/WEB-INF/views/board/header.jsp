<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- 코어라이브러리 사용 -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> <!-- 시큐리티 태그 라이브러리 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

<!-- 시큐리티  -->
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">
<script>
	$(function(){
		$("#logout").click(function(){
			event.preventDefault();
			$("form[name=logout]").submit();
		})
	})
</script>

<style>
	body > nav.navbar {
	justify-content: flex-end; /* 오른쪽 정렬 */
}

.dropdown-menu {
min-width: 0rem; 
}

/* nav 색상 지정 */
.navbar {
	background: #096988;
	margin-bottom: 3em;
	padding-right: 3em;
}

.navbar-dark .navbar-nav .nav-link {
	color: rgb(255, 255, 255);
}

textarea {
	resize: none;
}
</style>
<!-- isAnonymous() : 익명사용자(로그인 하지 않은 사람)인 경우 로그인 페이지로 이동하도록 합니다. -->
<sec:authorize access="isAnonymous()">
	<script>
		location.href = "${pageContext.request.contextPath}/member/login";
	</script>
</sec:authorize>    


<nav class="navbar navbar-expand-sm right-block navbar-dark">
	<ul class="navbar-nav">
		<sec:authorize access="isAuthenticated()">
			<sec:authentication var="pinfo" property="principal" />
				<li class="nav-item">
					<form action="${pageContext.request.contextPath}/member/logout" method="post" style="margin-bottom:0px" name="logout">
						<a class="nav-link" href="#" id="logout">
							<span id="loginid">${pinfo.username}</span>님 (로그아웃)
						</a>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					</form>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/member/update">정보수정</a>
				</li>	
				
				<!-- 관리자인 경우에만 추가로 표시되는 메뉴 -->
				<c:if test="${pinfo.username == 'admin'}">
					<!-- Dropdown -->
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbardrop"
							data-toggle="dropdown"> 관리자 </a>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="${pageContext.request.contextPath}/member/list">회원정보</a>
							<a class="dropdown-item" href="${pageContext.request.contextPath}/board/list">게시판</a>
						</div>
					</li>
				</c:if>	
		</sec:authorize>
	</ul>
</nav>