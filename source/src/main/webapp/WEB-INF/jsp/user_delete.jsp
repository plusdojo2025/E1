<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ユーザー情報削除</title>
<link rel="stylesheet" type="text/css" href="css/user_delete.css">
</head>
<body>

<img src="img/kajilogo.png" alt="カジミエールのロゴ" id="kaji_logo">
<img src="img/logo_lightblue.png" alt="カジミエールのロゴの文字" id="kajimieru">

<div id="container">
  <h2>ユーザー情報削除</h2>
  <div id="content">

    <!-- ユーザー削除フォーム -->
    <form action="${pageContext.request.contextPath}/UserDeleteServlet" method="post" id="user_delete_form">
      <label for="user_id">削除するユーザーID</label>
      <input type="text" name="user_id" id="user_id">

      <label for="user_password">パスワード</label>
      <div class="input_with_icon">
        <input type="password" name="password" id="user_password">
        <img src="img/eye_slash.svg" class="togglePasswordIcon" data-target="user_password" alt="表示切替">
      </div>

      <button type="submit" class="delete_btn">削除</button>
    </form>

    <!-- ファミリー削除フォーム -->
    <form action="${pageContext.request.contextPath}/FamilyDeleteServlet" method="post" id="family_delete_form">
      <label for="family_id">削除するファミリーID</label>
      <input type="text" name="family_id" id="family_id">

      <label for="fami_pass">あいことば</label>
      <div class="input_with_icon">
        <input type="password" name="fami_pass" id="fami_pass">
        <img src="img/eye_slash.svg" class="togglePasswordIcon" data-target="fami_pass" alt="表示切替">
      </div>

      <button type="submit" class="delete_btn">削除</button>
    </form>

    <!-- 戻るボタン -->
    <form action="${pageContext.request.contextPath}/LoginServlet" method="get">
      <input type="image" src="img/back.svg" alt="ログイン画面へ戻る" id="to_login">
    </form>

    <!-- 結果表示 -->
    <pre>${result}</pre>
  </div>
</div>

<!-- JS -->
<script>
'use strict';

document.querySelectorAll(".togglePasswordIcon").forEach(icon => {
  icon.addEventListener("click", () => {
    const inputId = icon.getAttribute("data-target");
    const input = document.getElementById(inputId);
    if (!input) return;

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

// 削除確認
function confirmUserDelete(event) {
  if (!confirm("本当にこのユーザーを削除しますか？")) {
    event.preventDefault();
  }
}

function confirmFamilyDelete(event) {
  if (!confirm("本当にこのファミリーを削除しますか？")) {
    event.preventDefault();
  }
}

window.addEventListener("DOMContentLoaded", function () {
  document.querySelector("#user_delete_form .delete_btn")?.addEventListener("click", confirmUserDelete);
  document.querySelector("#family_delete_form .delete_btn")?.addEventListener("click", confirmFamilyDelete);
});
</script>

</body>
</html>