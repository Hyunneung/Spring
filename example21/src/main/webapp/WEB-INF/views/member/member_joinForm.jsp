<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>회원관리 시스템 회원가입 페이지</title>
<link href="${pageContext.request.contextPath}/resources/css/join.css" type="text/css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>	
<script>
	$(function(){
		
		// id 체크
		var checkid = false;
		$("input:eq(0)").on('keyup', function(){
				$("#id_message").empty();
				
				var pattern = /^\w{5,12}$/;
				var id = $(this).val();
				
				if(!pattern.test(id)){
					$("#id_message").css('color','red').html("영문자 숫자 _로 5~12자 가능합니다.");
					checkid = false;
					return;
				}
				
				$.ajax({
					url: "idcheck", // 아이디 중복 체크
					data: {"id": id},
					success : function(result){ // 아이디 없으면 -1, 있으면 1
						if(result == -1){
							$("#id_message").css('color','green').html("사용 가능한 아이디입니다.");
							checkid = true;
						} else {
							$("#id_message").css('color','red').html("사용 중인 아이디입니다.");
							checkid = false;
						}
					} // success end
				}) // ajax end
		}) // id keyup end
		
		
		// email 체크
		var checkemail = false;
		$("input:eq(6)").on('keyup', function(){
				$("#email_message").empty();
				
				var pattern = /^\w+@\w+[.]\w{3}$/;
				var email = $(this).val();
				
				if(!pattern.test(email)){
					$("#email_message").css('color','red').html("이메일 형식이 맞지 않습니다.");
					checkemail = false;
				} else {
					$("#email_message").css('color','green').html("이메일 형식에 맞습니다.");
					checkemail = true;
				}
		}); // email keyup end
		
		
		// 가입하기 버튼
		$('form').submit(function(){
			if(!$.isNumberic($("input[name='age']").val())){
				alert('나이는 숫자를 입력하세요');
				$("input[name='age']").val('').focus();
				return false;
			}
			
			if(!checkid){
				alert('사용가능한 id로 입력하세요.');
				$("input:eq(0)").val('').focus();
				return false;
			}
			
			if(!checkemail){
				alert('email 형식을 확인하세요.');
				$("input:eq(6)").focus();
				return false;
			}
		}) // form submit end
		
	}) // ready end
</script>
</head>
<body>
	<form name="joinform" action="joinProcess" method="post">
		<h1>회원가입 페이지</h1>
		<hr>
		<b>아이디</b>
        <input type="text" name="id" placeholder="Enter id" required maxLength="12" style="margin-bottom:0px">
        <span id="id_message"></span>
        
        <b>비밀번호</b>
        <input type="password" name="password" placeholder="Enter password" required>
		
		<b>이름</b>
        <input type="text" name="name" placeholder="Enter name" maxLength="5" required>  	
		
		<b>나이</b>
        <input type="text" name="age" maxLength="2" placeholder="Enter age" required>
		
		<b>성별</b>
		<div>
        	<input type="radio" name="gender" value="남" checked><span>남자</span>
        	<input type="radio" name="gender" value="여"><span>여자</span>
		</div>
		
		<b>이메일 주소</b>
		<input type="text" name="email" id="email" placeholder="Enter email" maxLength="30" required style="margin-bottom:0px">
		<span id="email_message"></span>
		<div class="clearfix">
			<button type="submit" class="submitbtn">회원가입</button>
      	  	<button type="reset" class="cancelbtn">다시작성</button>
		</div>
		
		<!-- CSRF 토큰 생성 - 403에러 뜨지 않는다 -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">	
	</form>
</body>
</html>