<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ユーザー登録</title>
<link rel="stylesheet" type="text/css" href="css/user_regist.css">
</head>
<body>
<img src="img/kajilogo.png" alt="カジミエールのロゴ" id="kaji_logo">
<img src="img/logo_lightblue.png" alt="カジミエールのロゴの文字" id="kajimieru">
<span>${result}</span>
<div id="container">
<form method="POST"  id="user_regist_form" action="${pageContext.request.contextPath}/UserRegistServlet">
<h2>ユーザー登録</h2>
	<table id="user_content">
		<tr>
			<td>
				<label><br>ファミリーID
				<input type="text" name="family_id" class="user_input" placeholder="半角英数記号10文字以内" maxlength=10 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>あいことば
				<input type="password" name="fami_pass" id="fami_pass" class="user_input" placeholder="半角英数8文字以上20文字以内" minlength=8 maxlength=20 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				<img src="img/eye_slash.svg" class="togglePasswordIcon" data-target="fami_pass" alt="表示切替">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>ユーザーID
				<input type="text" name="user_id" class="user_input" placeholder="半角英数記号10文字以内" maxlength=10 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				</label>
			</td>
		</tr>
	<!-- <tr>
			<td>
				<label>ニックネーム
				<input type="text" name="user_name" class="user_input" placeholder="10文字以内" maxlength=10 required>
				</label>
			</td>
		</tr> -->
		<tr>
			<td>
				<label>パスワード
				<input type="password" name="password" id="password" class="user_input" placeholder="半角英数記号8文字以上20文字以内" minlength=8 maxlength=20 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				<img src="img/eye_slash.svg" class="togglePasswordIcon" data-target="password" alt="表示切替">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>パスワード(確認)
				<input type="password" name="confirmPassword" id="password_a" class="user_input" placeholder="半角英数記号8文字以上20文字以内" minlength=8 maxlength=20 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				<img src="img/eye_slash.svg" class="togglePasswordIcon" data-target="password_a" alt="表示切替">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label id="usable">使用可能記号　! @ # $ % ^ & * ( ) _ + = -
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" name="share_goal" value="0">
        		<p>※分担目標はデフォルトでは設定されません。</p>
        	</td>
        </tr>
        <tr>
        	<td>
        		<span class="message ${not empty result ? 'visible' : ''}">${result}</span>
        	</td>
        </tr>
		<tr>
			<td>
				<span id="error_message" class="message ${not empty errorMessage ? 'visible' : ''}">${errorMessage}</span>
				<span id="user_error" class="message ${not empty UserErrorMessage ? 'visible' : ''}">${UserErrorMessage}</span>
				<input type="submit" name="submit" value="登録" id="user_btn">
			</td>
		</tr>
	</table>
</form>
<form action="${pageContext.request.contextPath}/LoginServlet" method="get">
    <input type="image" src="img/back.svg" alt="ログイン画面へ戻る" id="to_login">
</form>
</div>
<!-- パスワード切り替えスクリプト -->
<script>
'use strict';
  document.querySelectorAll(".togglePasswordIcon").forEach(icon => {
    icon.addEventListener("click", () => {
      const inputId = icon.getAttribute("data-target");
      const input = document.getElementById(inputId);
      if (input.type === "password") {
        input.type = "text";
        icon.src = "img/eye.svg";
        icon.alt = "非表示にする";
      } else {
        input.type = "password";
        icon.src = "img/eye_slash.svg";
        icon.alt = "表示する";
      }
    });
  });
</script>
</body>
</html>