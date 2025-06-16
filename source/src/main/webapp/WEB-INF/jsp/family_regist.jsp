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
				<input type="text" name="family_id">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>あいことば
				<input type="password" name="fami_pass">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>あいことば(確認)
				<input type="password" name="fami_pass">
				</label>
			</td>
		</tr>
		<%-- <tr>
			<td>
				<input type="submit" name="submit" value="登録">
				<span id="error_message">${errorMessage}</span>
			</td>
		</tr> --%>
	</table>
</form>
<form method="get" action="/E1/UserRegistServlet">
        <input type="submit" value="個人登録">
</form>
</body>
</html>