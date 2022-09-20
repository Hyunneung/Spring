<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>회원관리 시스템 로그인 페이지</title>
<link href="${pageContext.request.contextPath}/resources/css/login.css" type="text/css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script>
	var state = "${state}";
	if( state == 'joinSuccess' ){ // 회원가입 성공 여부
		alert("회원가입을 축하합니다!");
	} else if( state == "loginFail" ){ // 로그인 실패
		alert("아이디나 비밀번호 오류입니다.");
	}
	
	$(function(){
		$(".join").click(function(){
			location.href="${pageContext.request.contextPath}/member/join"
		})
	})
</script>
</head>
<body>
	<form action="${pageContext.request.contextPath}/member/loginProcess" method="post" name="loginForm">
		<h1>로그인</h1>
		<hr>
		
		<b>아이디</b>  
  		<input type="text" name="id" placeholder="Enter id" id="id" required
  			<c:if test="${!empty saveid}">
  				value="${saveid}"   		  
  			</c:if>
  		><br>
  		
  		<b>Password</b>
		<input type="password" name="password" placeholder="Enter password" required>
		
		<label>
			<input type="checkbox" name="remember-me" id="remember-me" style="margin-bottom:15px">로그인 유지하기
		</label>
		
		<div class="clearfix">
			<button type="submit" class="submitbtn">로그인</button>
			<button type="button" class="join">회원가입</button>
		</div>
		
		<!-- CSRF 토큰 생성 - 403에러 뜨지 않는다 -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> 
	</form>
</body>
</html>