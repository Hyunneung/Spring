<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>list.jsp</title>
<link href="css/list.css" rel="stylesheet">
</head>
<body>
	<table border=1>
		<tr>
			<th>ID</th><td>${bean.id}</td>
		</tr>
		<tr>
			<th>비밀번호</th><td>${bean.pass}</td>
		</tr>
	</table>	
</body>
</html>