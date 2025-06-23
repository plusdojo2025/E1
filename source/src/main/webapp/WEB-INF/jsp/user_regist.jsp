<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/user_regist.css' />">
</head>
<body>
<img src="<c:url value='/img/kajilogo.png' />" alt="カジミエールのロゴ" id="kaji_logo">
<img src="<c:url value='/img/logo_lightblue.png' />" alt="カジミエールのロゴ" id="kajimieru">
<span>${result}</span>
<div id="container">
<form method="POST"  id="user_regist_form" action="<c:url value='/UserRegistServlet' />">
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
				<input type="password" name="fami_pass" class="user_input" minlength=8 maxlength=20 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
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
		<tr>
			<td>
				<label>ニックネーム
				<input type="text" name="user_name" class="user_input" placeholder="10文字以内" maxlength=10 required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>パスワード
				<input type="password" name="password" class="user_input" placeholder="半角英数記号8文字以上20文字以内" minlength=8 maxlength=20 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>パスワード(確認)
				<input type="password" name="confirmPassword" class="user_input" placeholder="半角英数記号8文字以上20文字以内" minlength=8 maxlength=20 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>使用可能記号　! @ # $ % ^ & * ( ) _ + = -
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" name="share_goal" value="0.5">
        		<p>※あなたの分担目標はデフォルトで50%に設定されます。</p>
        	</td>
        </tr>
        <tr>
        	<td>
        		<span>${result}</span>
        	</td>
        </tr>
		<tr>
			<td>
				<span id="error_message">${errorMessage}</span>
				<span id="user_error">${UserErrorMessage}</span>
				<input type="submit" name="submit" value="登録" id="user_btn">
			</td>
		</tr>
	</table>
</form>
<form action="<c:url value='/LoginServlet' />" method="get">
    <button type="submit" >
    <img src="<c:url value='/img/back.svg' />" alt="ログイン画面へ戻る" id="to_login">
    </button>
</form>
</div>
</body>
</html>