<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
  <!-- ヘッダー（ここから） -->
  <!-- ヘッダー（ここまで） -->
  <!-- メイン（ここから） -->
<img src="img/kajilogo.png" alt="カジミエールのロゴ" id="kaji_logo">
<img src="img/logo_lightblue.png" alt="カジミエールのロゴ文字" id="kajimieru">
<div id="container">
<h2 id="login_title">ログイン</h2>
<form method="POST"  id="login_form" action="${pageContext.request.contextPath}/LoginServlet">
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
				 <img src="img/eye_slash.svg" id="togglePasswordIcon" onclick="togglePassword()" alt="表示切替">
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
<form method="GET" action="${pageContext.request.contextPath}/FamilyRegistServlet">
        <input type="submit" value="IDをお持ちでない方はこちら" id="no_id">
</form>
<form method="GET" action="${pageContext.request.contextPath}/UserDeleteServlet">
        <input type="submit" value="アカウント削除はこちら" id="no_account">
</form>
</div>
  <!-- メイン（ここまで） -->
  <!-- フッター（ここから） -->
<script>
function togglePassword() {
    const pwd = document.getElementById("password");
    const icon = document.getElementById("togglePasswordIcon");

    if (pwd.type === "password") {
      pwd.type = "text";
      icon.src = "img/eye.svg";
      icon.alt = "非表示にする";
    } else {
      pwd.type = "password";
      icon.src = "img/eye_slash.svg";
      icon.alt = "表示する";
    }
  }
</script>
</body>
</html>