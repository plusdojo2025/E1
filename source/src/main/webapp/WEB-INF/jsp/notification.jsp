<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/notification.css">
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<a href="#" onClick="history.back(); return false;"><img src="img/仮置き.png"></a>
<h1 id=notititle>通知</h1>
<hr>
<div id="allnoti">
	<c:forEach var="e" items="${notiList}">
		<div>
			<h2>${e.noti_content}</h2><br>
			<p>${e.noti_datetime}</p>
			<hr>
		</div>
	</c:forEach>
</div>
</body>
</html>