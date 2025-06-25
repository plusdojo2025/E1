<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="css/gacha_result.css">
<title>分担ガチャ結果</title>
</head>
<body>
<!-- ヘッダー（ここから） -->
<header>
<div id="top_nav">
	<a href="${pageContext.request.contextPath}/LogoutServlet" id="logout_link" title="ログアウト">
	<img src="img/user.svg" alt="ログアウト" id="user_img">
	</a>
	<a href="${pageContext.request.contextPath}/HomeServlet">
	<img src="img/logo_lightblue.png" alt="カジミエール" id="logo_img">
	</a>
	<a href="${pageContext.request.contextPath}/NotificationServlet">
	<img src="img/noti.svg" alt="通知" id="noti_img">
	</a>

</div>
<div id="bar">
<img src="img/bar.png" alt="" id="bar_img">
</div>
</header>
<!-- ヘッダー（ここまで） -->
<main>
<div id="container">
<h2 id="result_title">分担ガチャ結果</h2>
<div id="result_content">
<c:forEach var="e" items="${familyList}">
<c:set var="role" value="${e.user_id}" />
<c:out value="${e.user_id}" />
<c:forEach var="e" items="${roleList}">
<c:if test="${role == e.role}">
<ul>
<li id="housework_name">
<c:out value="${e.housework_name}" />
</li>
</ul>
</c:if>
</c:forEach>
</c:forEach>
</div>
</div>
</main>
 <!-- フッター（ここから） -->
<div id="footer">
	<div id="bottom_bar">
	<img src="img/bar.png" alt="" id="bottom_bar_img">
	</div>
	  <nav class="bottom_nav">
	    <ul>
	      <li><a href="${pageContext.request.contextPath}/HomeServlet"><img src="img/home.svg" alt="ホーム" id="home_img"></a></li>
	      <li><a href="${pageContext.request.contextPath}/HWSearchServlet"><img src="img/list.svg" alt="一覧" id="list_img"></a></li>
	      <li><a href="${pageContext.request.contextPath}/HWRegistServlet"><img src="img/regist.svg" alt="登録" id="regist_img"></a></li>
	      <li><a href="${pageContext.request.contextPath}/GachaServlet"><img src="img/circle.svg" alt="くじ" id="gacha_img"></a></li>
	      <li><a href="${pageContext.request.contextPath}/AnalysisServlet"><img src="img/analysis.svg" alt="分析" id="analysis_img"></a></li>
	    </ul>
	  </nav>
</div>
<!-- フッター（ここまで） -->
</body>
</html>