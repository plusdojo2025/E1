<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/gacha.css">
<title>分担ガチャ結果</title>
</head>
<body>
<c:forEach var="e" items="${role}">
<c:set var="role" value="${e}" />
<c:out value="${e}" />
<c:forEach var="e" items="${roleList}">
<c:if test="${role == e.role}">
<ul>
<li>
<c:out value="${e.housework_name}" />
</li>
</ul>
</c:if>
</c:forEach>
</c:forEach>
</body>
</html>