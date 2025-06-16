<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<span>${result}</span>
<span>${UserErrorMessage}</span>
<form method="POST"  id="user_regist_form" action="/E1/UserRegistServlet">
	<table>
		<tr>
			<td>
				<label><br>ファミリーID
				<input type="text" name="family_id" maxlength=10 pattern="^[a-zA-Z0-9]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>あいことば
				<input type="password" name="fami_pass" maxlength=10 pattern="^[a-zA-Z0-9]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>ユーザーID
				<input type="text" name="user_id" maxlength=10 pattern="^[a-zA-Z0-9]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>ニックネーム
				<input type="text" name="user_name" maxlength=10 required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>パスワード
				<input type="password" name="password" minlength=8 maxlength=15 pattern="^[a-zA-Z0-9]+$" required>
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>パスワード(確認)
				<input type="password" name="confirmPassword" minlength=8 maxlength=15 pattern="^[a-zA-Z0-9]+$" required>
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
				<input type="submit" name="submit" value="登録">
				<span id="error_message">${errorMessage}</span>
			</td>
		</tr>
	</table>
</form>
<script src="user_regist.js"></script>
</body>
</html>