<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="POST"  id="user_regist_form" action="/E1/UserRegistServlet">
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
				<label>あいことば<br>
				<input type="password" name="fami_pass">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>ユーザーID<br>
				<input type="text" name="user_id">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>ニックネーム<br>
				<input type="text" name="user_name">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>パスワード<br>
				<input type="password" name="password">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>パスワード(確認)<br>
				<input type="password" name="password">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" name="share_goal" value="0.5">
        		<p>※あなたの目標達成率はデフォルトで50%に設定されます。</p>
        	</td>
        </tr>
		<tr>
			<td>
				<input type="submit" name="submit" value="登録">
				<span id="error_message">${errorMessage}</span>
			</td>
		</tr>
	</table>
</form>
</body>
</html>