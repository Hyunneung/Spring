<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인 폼</title>
<link href="css/form.css" rel="stylesheet">
</head>
<body>
	<form name="myform" method="post" action="login_ok.bo">
		<table border=1>
			<tr>
				<td>ID</td>
				<td><input type="text" name="id"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pass"></td>
			</tr>
			<tr>
				<td colspan=2 style="background:lightgreen">
					<input type="submit" value="로그인">
				</td>
			</tr>
		</table>	
	</form>
</body>
</html>