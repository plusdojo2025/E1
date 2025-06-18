<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="POST"  id="family_regist_form" action="/E1/FamilyRegistServlet">
	<table>
		<tr>
			<td>
				<label><br>ファミリーID
				<input type="text" name="family_id" placeholder="半角英数10文字以内" maxlength=10 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>あいことば
				<input type="password" name="confirm_fami_pass" placeholder="半角英数8文字以上15文字以内" minlength=8 maxlength=15 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>あいことば(確認)
				<input type="password" name="fami_pass" placeholder="半角英数8文字以上15文字以内" minlength=8 maxlength=15 pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
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
	
<span>${FamilyErrorMessage}</span><br>
<input type="submit" value="登録">
</form>
<!--  <form method="GET" action="/E1/UserRegistServlet">

</form> -->
<button type="button" onclick="location.href='/E1/UserRegistServlet'">ファミリーIDをお持ちの方はこちら</button>
<form action="/E1/LoginServlet" method="get">
    <button type="submit">戻る</button>
</form>
</body>
</html>