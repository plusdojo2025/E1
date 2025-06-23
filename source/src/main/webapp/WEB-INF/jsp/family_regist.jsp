<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ファミリー登録</title>
<link rel="stylesheet" type="text/css" href="css/family_regist.css">
</head>
<body>
<img src="img/kajilogo.png" alt="カジミエールのロゴ" id="kaji_logo">
<img src="img/logo_lightblue.png" alt="カジミエールのロゴの文字" id="kajimieru">
<div id="container">
<form method="POST"  id="family_regist_form" action="${pageContext.request.contextPath}/FamilyRegistServlet">
<h2>ファミリー登録</h2>
	<table id="family_content">
		<tr>
			<td>
				<label><br>ファミリーID
				<input type="text" name="family_id" class="family_input" placeholder="半角英数10文字以内" maxlength=10 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>あいことば
				<input type="password" name="confirm_fami_pass"  class="family_input" placeholder="半角英数8文字以上20文字以内" minlength=8 maxlength=20 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>あいことば(確認)
				<input type="password" name="fami_pass"  class="family_input" placeholder="半角英数8文字以上20文字以内" minlength=8 maxlength=20 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				</label>
			</td>
		</tr>
		<%-- <tr>
			<td>
				<input type="submit" name="submit" value="登録">
				<span id="error_message">${errorMessage}</span>
			</td>
		</tr> --%>
		<tr>
			<td>
				<label>使用可能記号　! @ # $ % ^ & * ( ) _ + = -　
				</label>
			</td>
		</tr>
		<tr>
        	<td>
        		<span>${result}</span>
        	</td>
        </tr>
	</table>
	
<span id="family_error">${FamilyErrorMessage}</span><br>
<input type="submit" value="登録" id="family_btn">
</form>
<!--  <form method="GET" action="/E1/UserRegistServlet">

</form> -->
<button type="button" onclick="location.href='/e1/UserRegistServlet'" id="family_id">ファミリーIDをお持ちの方はこちら</button>
<form action="${pageContext.request.contextPath}/LoginServlet" method="get">
    <button type="submit">
    <img src="img/back.svg" alt="ログイン画面へ戻る" id="to_login">
    </button>
</form>
</div>
</body>
</html>