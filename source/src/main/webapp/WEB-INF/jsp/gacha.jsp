<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/gacha.css' />">
<title>Insert title here</title>
</head>

<body>
<form method="POST" action="<c:url value='/GachaServlet' />" id="gachaForm">
<input type="hidden" name="click" value="on">
</form>
<div id="gacha">
ガチャ
</div>
</body>
<script>
document.getElementById('gacha').addEventListener('click', function() {
	  // フォームを送信
	  document.getElementById('gachaForm').submit();
	});
</script>
</html>