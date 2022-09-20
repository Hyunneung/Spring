<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- 코어라이브러리 사용 -->
<html>
<head>
<title>회원관리 시스템 회원수정 페이지</title>
<link href="${pageContext.request.contextPath}/resources/css/join.css" rel="stylesheet" type="text/css">
<style>
	h3 {text-align:center; color:#1a92b9}
	input[type=file] {display:none;}
</style>
</head>
<body>
<jsp:include page="../board/header.jsp"/>
	<form name="joinform" action="updateProcess" method="post">
		<h3>회원 정보 수정</h3>
		<hr>
		<b>아이디</b>
        <input type="text" name="id" value="${member.id}" required readonly>
        
        <b>비밀번호</b>
        <input type="password" name="pass" value="${member.password}" required readonly>
		
		<b>이름</b>
        <input type="text" name="name" value="${member.name}" placeholder="Enter name" required>  	
		
		<b>나이</b>
        <input type="text" name="age" value="${member.age}" maxLength="2" placeholder="Enter age" required>
		
		<b>성별</b>
		<div>
        	<input type="radio" name="gender" value="남"><span>남자</span>
        	<input type="radio" name="gender" value="여"><span>여자</span>
		</div>
		
		<b>이메일 주소</b>
		<input type="text" name="email" value="${member.email}" placeholder="Enter email" required>
		<span id="email_message"></span>
		
		<div class="clearfix">
			<button type="submit" class="submitbtn">수정</button>
      	  	<button type="button" class="cancelbtn">취소</button>
		</div>
		
		<!-- 토큰 설정 -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">	
	</form>
	
	<script>
		/* 성별 체크해주는 부분 */
		$("input[value='${member.gender}']").prop('checked', true);
		
		/* 취소버튼 */
		$(".cancelbtn").click(function(){
			history.back();
		})
		
		/* 이메일 */		
		var checkemail = true; // 기존 이메일은 유효한 이메일
		$("body > form > input[type=text]:nth-child(14)").on('keyup', function(){
			$("#email_message").empty();
			var pattern = /^\w+@\w+[.]\w{3}$/;
			var email = $("body > form > input[type=text]:nth-child(14)").val();
			
			// 유효한 이메일 아닐 경우
			if(!pattern.test(email)) {
				$("#email_message").css('color', 'red').text("이메일 형식이 맞지 않습니다")
				checkemail = false;
			} else {
				$("#email_message").css('color', 'green').text("이메일 형식에 맞습니다")
				checkemail = true;
			}
		})
		
		
		/* 폼 제출 */
		$("form[name=joinform]").submit(function(){
			// 각 입력란 검사
			// 1. 나이 자료형 숫자 아니면 수정 불가
			if(!$.isNumeric($("input[name='age']").val())){
				alert("나이는 숫자를 입력하세요");
				$("input[name='age']").val('').focus();
				return false;
			}
			// 2. 이메일 검사
			if(!checkemail){
				alert("이메일 형식을 확인하세요");
				$("body > form > input[type=text]:nth-child(14)").focus();
				return false;
			}
		})
	</script>
</body>
</html>