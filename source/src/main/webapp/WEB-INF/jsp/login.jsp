<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログインページ</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/login.css' />">
</head>
<body>
  <!-- ヘッダー（ここから） -->
  <!-- ヘッダー（ここまで） -->
  <!-- メイン（ここから） -->
<img src="<c:url value='/img/kajilogo.png' />" alt="カジミエールのロゴ" id="kaji_logo">
<img src="<c:url value='/img/logo_lightblue.png' />" alt="カジミエールのロゴ" id="kajimieru">
<div id="container">
<h2 id="login_title">ログイン</h2>
<form method="POST"  id="login_form" action="<c:url value='/LoginServlet' />">
	<table id="login_content">
		<tr>
			<td>
				<label>ユーザーID<br>
				<input type="text" name="user_id" class="login_input" maxlength=10 >
				</label>
			
			</td>
		</tr>
		<tr>
			<td>
				<label>パスワード<br>
				<input type="password" id="password" name="password" class="login_input" minlength=8 maxlength=20>
				 <button type="button" onclick="togglePassword()">表示</button>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<span>${result}</span>
				<span id="error_message">${errorMessage}</span><br>
				<input type="submit" name="submit" value="ログイン" id="login_btn">
			</td>
		</tr>
	</table>
</form>
<form method="GET" action="<c:url value='/FamilyRegistServlet' />">
        <input type="submit" value="IDをお持ちでない方はこちら" id="no_id">
</form>
<form method="GET" action="<c:url value='/UserDeleteServlet' />">
        <input type="submit" value="アカウント削除はこちら" id="no_account">
</form>
</div>
  <!-- メイン（ここまで） -->
  <!-- フッター（ここから） -->
<script>
  function togglePassword() {
    const pwd = document.getElementById("password");
    const btn = event.target;
    if (pwd.type === "password") {
      pwd.type = "text";
      btn.textContent = "非表示";
    } else {
      pwd.type = "password";
      btn.textContent = "表示";
    }
  }
</script>
</body>
</html>