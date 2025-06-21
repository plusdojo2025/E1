<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/UserDeleteServlet" method="post">
    <label for="user_id">削除するユーザーID</label>
    <input type="text" name="user_id" id="user_id">
    
    <label for="password">パスワード</label>
    <input type="password" name="password" id="password">
    
    <button type="submit">削除</button>
</form>
<form action="${pageContext.request.contextPath}/FamilyDeleteServlet" method="post">
    <label for="family_id">削除するファミリーID</label>
    <input type="text" name="family_id" id="family_id">
    
    <label for="password">あいことば</label>
    <input type="password" name="fami_pass" id="fami_pass">
    
    <button type="submit">削除</button>
</form>
<form action="${pageContext.request.contextPath}/LoginServlet" method="get">
    <button type="submit">戻る</button>
</form>
<span>${result}</span>
</body>
</html>