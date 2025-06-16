<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/gacha.css">
<title>Insert title here</title>
</head>
<body>

<c:forEach var="e" items="${roleList}" varStatus="status" >
<c:set var="role" value="${e.role }" />
<c:out value="${e.role}" />
<c:forEach var="e" items="${roleList}" varStatus="status" >
<ul>
<c:if test="${e.role == role}">
<li><c:out value="${e.housework_name}" />
</c:if>
</ul>
</c:forEach>
</c:forEach>

</body>
</html>