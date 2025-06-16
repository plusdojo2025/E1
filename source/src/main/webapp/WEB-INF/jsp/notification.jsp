<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>通知</h1>
<hr>
<div>
	<c:forEach var="e" items="${notiList}">
		<div>
			<h2>${e.noti_content}</h2><br>
			<p>${e.noti_datetime}</p>
		</div>
	</c:forEach>
</div>
</body>
</html>