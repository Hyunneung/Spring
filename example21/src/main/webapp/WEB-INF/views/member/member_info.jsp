<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html>
<head>
<title>회원관리 시스템 관리자 모드</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<style>
  tr>td:nth-child(odd){font-weight: bold}
  td{text-align:center}
  .container{width:50%}
</style>
</head>
<body>
<jsp:include page="../board/header.jsp"/>
	<c:set var="m" value="${member}"/>
	<div class="container">
		<table class="table table-bordered">
			<tr>
				<td>아이디</td>
				<td>${m.id}</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>${m.password}</td>
			</tr>
			<tr>
				<td>이름</td>
				<td>${m.name}</td>
			</tr>
			<tr>
				<td>나이</td>
				<td>${m.age}</td>
			</tr>
			<tr>
				<td>성별</td>
				<td>${m.gender}</td>
			</tr>
			<tr>
				<td>이메일 주소</td>
				<td>${m.email}</td>
			</tr>
			<tr>
				<td colspan="2">
				<a href="list">리스트로 돌아가기</a></td>
			</tr>
		</table>
	</div>	
</body>
</html>