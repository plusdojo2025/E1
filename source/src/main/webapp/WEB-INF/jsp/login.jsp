<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログインページ</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
  <!-- ヘッダー（ここから） -->
  <!-- ヘッダー（ここまで） -->
  <!-- メイン（ここから） -->
<img src="${pageContext.request.contextPath}/img/kajilogo.png" alt="カジミエールのロゴ" id="kaji_logo">
<img src="${pageContext.request.contextPath}/img/logo_lightblue.png" alt="カジミエールのロゴ" id="kajimieru">
<div id="container">
<h2 id="login_title">ログイン</h2>
<form method="POST"  id="login_form" action="/E1/LoginServlet">
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
				<input type="password" name="password" class="login_input" minlength=8 maxlength=20>
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
<form method="GET" action="/E1/FamilyRegistServlet">
        <input type="submit" value="IDをお持ちでない方はこちら">
</form>
<form method="GET" action="/E1/UserDeleteServlet">
        <input type="submit" value="アカウント削除はこちら">
</form>
</div>
  <!-- メイン（ここまで） -->
  <!-- フッター（ここから） -->

</body>
</html>