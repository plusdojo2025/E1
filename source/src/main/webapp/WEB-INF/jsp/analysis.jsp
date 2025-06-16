<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/analysis.css">
<title>分析</title>
</head>
<body>
<div id="share_goal">
<c:forEach var="e" items="${yesterdayList}">
    <p>ユーザーID: ${e.user_id}</p>
</c:forEach>
</div>
</body>
</html>