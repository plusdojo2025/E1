<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>通知</title>
<link rel="stylesheet" type="text/css" href="css/notification.css">
</head>
<body>
<a href="#" onClick="history.back(); return false;"><img src="img/chevron-left-solid.svg" id="backimg"></a>
<h1 id="notititle">通知</h1>
<hr id="notiline">
<div id="allnoti">
	<c:forEach var="e" items="${notiList}">
		<div class="noti">
			<h2>${e.noti_content}</h2><br>
			<p>${e.noti_datetime}</p>
			<hr>
		</div>
	</c:forEach>
	<c:if test="${empty notiList}">
		<p>通知はありません。</p>
	</c:if>
</div>
</body>
</html>