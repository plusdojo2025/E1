<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/analysis.css">-->
<title>分析</title>
</head>
<body>
<div id="share_goal">
<h2>分担目標</h2>
<c:forEach var="e" items="${userList}" varStatus="status">
	<form method="POST" action="${pageContext.request.contextPath}/AnalysisServlet" id="form${status.index}">
		<label>${e.user_id}の分担割合<br>
			<input type="text" name="goal" value="${e.share_goal}">
			<input type="hidden" name="user_id" value="${e.user_id}">
			<input type="submit" name="submit" value="分担目標設定" id="goal_submit">
		</label>
	</form>
</c:forEach>
</div>
<c:out value="${userList}" default="userList is null or empty" />
</body>
</html>