<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>通知</title>
<link rel="stylesheet" type="text/css" href="css/notification.css">
</head>
<body>
<a href="#" onClick="history.back(); return false;"><img src="img/chevron-left-solid.svg" id="back_img"></a>
<h2 id="notititle">通知</h2>
<div id="bar">
<img src="img/bar.png" alt="" id="bar_img">
</div>
<div id="noti_container">
	<c:forEach var="e" items="${notiList}">
		<div class="noti">
			<h3>${e.noti_content}</h3><br>
			<p>${e.noti_datetime}</p>
			<hr>
		</div>
	</c:forEach>
	<c:if test="${empty notiList}">
		<p>通知はありません。</p>
	</c:if>
</div>
</body>
<script src="js/notification.js"></script>
</html>