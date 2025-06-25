<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ファミリー登録</title>
<link rel="stylesheet" type="text/css" href="css/family_regist.css">
</head>
<body>

<img src="img/kajilogo.png" alt="カジミエールのロゴ" id="kaji_logo">
<img src="img/logo_lightblue.png" alt="カジミエールのロゴの文字" id="kajimieru">

<div id="container">
<form method="POST" id="family_regist_form" action="${pageContext.request.contextPath}/FamilyRegistServlet">
  <h2>ファミリー登録</h2>
  <table id="family_content">
    <tr>
      <td>
        <label><br>ファミリーID
          <input type="text" name="family_id" class="family_input" placeholder="半角英数10文字以内" maxlength="10"
            pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
        </label>
      </td>
    </tr>

    <tr>
      <td>
        <label>あいことば
          <input type="password" name="confirm_fami_pass" id="password" class="family_input"
            placeholder="半角英数8文字以上20文字以内" minlength="8" maxlength="20"
            pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
          <img src="img/eye_slash.svg" class="togglePasswordIcon" data-target="password" alt="表示切替">
        </label>
      </td>
    </tr>

    <tr>
      <td>
        <label>あいことば(確認)
          <input type="password" name="fami_pass" id="password_a" class="family_input"
            placeholder="半角英数8文字以上20文字以内" minlength="8" maxlength="20"
            pattern="^[a-zA-Z0-9!@#$%^&*()_+=-]+$" required>
          <img src="img/eye_slash.svg" class="togglePasswordIcon" data-target="password_a" alt="表示切替">
        </label>
      </td>
    </tr>

    <tr>
      <td>
        <label>使用可能記号　! @ # $ % ^ & * ( ) _ + = -</label>
      </td>
    </tr>

    <tr>
      <td>
        <span class="message ${not empty result ? 'visible' : ''}">${result}</span>
      </td>
    </tr>
  </table>

  <span id="family_error" class="message ${not empty FamilyErrorMessage ? 'visible' : ''}">${FamilyErrorMessage}</span><br>
  <input type="submit" value="登録" id="family_btn">
</form>

<button type="button" onclick="location.href='${pageContext.request.contextPath}/UserRegistServlet'" id="family_id">
  ファミリーIDをお持ちの方はこちら
</button>

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