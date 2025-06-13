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
<h2>ログイン</h2>
<form method="POST"  id="login_form" action="/E1/LoginServlet">
	<table>
		<tr>
			<td>
				<label>ユーザーID<br>
				<input type="text" name="user_id">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>パスワード<br>
				<input type="password" name="password">
				</label>
			</td>
		<tr>
		<tr>
			<td>
				<input type="submit" name="submit" value="ログイン">
				<span id="error_message">${errorMessage}</span>
			<td>
		</tr>
	</table>
</form>
<form method="GET" action="/E1/FamilyRegistServlet">
        <input type="submit" value="IDをお持ちでない方はこちら">
</form>
  <!-- メイン（ここまで） -->
  <!-- フッター（ここから） -->

</body>
</html>