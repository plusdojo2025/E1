<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>家事一覧</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/housework_list.css">

<!-- ↑cssのパスを動的に取得 -->
</head>
<body>
    <!-- ヘッダー -->
    <header>
        <!-- ロゴ挿入 -->
        <!-- navタグで通知とログアウトを入れる -->

    </header>

    <!-- メイン -->
    <main class="housework_list_wrapper">
        <!-- 家事一覧を文字で表示 -->
        <h2>家事一覧</h2>

        <!-- 家事タブを横に並べる、家事タブを押したときはカテゴリごとに表示 -->
        
        <div class="tab_container">
            <div><p>掃除</p></div>
            <div><p>洗濯</p></div>
            <div><p>料理</p></div>
            <div><p>その他</p></div>
        </div>

        <!-- 家事一覧表示 -->
            <!-- 負担度、家事名、消去ラベル表示 -->
            <table>
            <tr class="card_label">
                <th>負担度</th>
                <th>家事名</th>
                <th>消去</th>
            </tr>

            <!-- 負担度、家事名、消去アイコンを横表示 -->

            <!-- ↓サンプル家事 -->
            

			<div class="card_container">
                <tr class="card">
                	<c:forEach var="e" items="${cardList}" varStatus="status">
                		<form method="POST" id="search_result_form" action="/webapp/HWSearchServlet">
						<input type="hidden" name="housework_id" value="${e.housework_id}">
		                <input type="hidden" name="family_id" value="${e.family_id}">
		                <input type="hidden" name="category_id" value="${e.category_id}">
		                <input type="hidden" name="noti_flag" value="${e.noti_flag}">
		                <input type="hidden" name="noti_time" value="${e.noti_time}">
		                <input type="hidden" name="frequency" value="${e.frequency}">
		                <input type="hidden" name="manual" value="${e.manual}">
		                <input type="hidden" name="fixed_role" value="${e.fixed_role}">
		                <input type="hidden" name="variable_role" value="${e.variable_role}">
		                <input type="hidden" name="log" value="${e.log}">               
	                    <td class="housework_level">
	                        <c:out value="${e.housework_level}" />
	                        <!-- 家事負担度<input type="text" name="housework_level" value="${e.housework_level}"> -->
	                    </td>
	                    <td class="housework_name">
	                        <c:out value="${e.housework_name}" />                       
	                        <!-- 家事名<input type="text" name="housework_name" value="${e.housework_name}"> -->
	                    </td>
	                    <td class="delete">
	                        <form method="POST" id="search_result_form" action="/webapp/UpdateDeleteServlet">
	                        <!-- ごみ箱のイメージを張り付ける -->
	                            <button class="js-modal-button" value="消去">消去</button>
	                        </form>
	                    </td>			                
              <!-- 負担度、家事名の範囲を押下時、家事更新画面をモーダル表示 -->
              			</form>
       		 		</c:forEach>
           		</tr>
           	</div>
           </table>
            <!-- 家事が追加されるごとに行を追加 -->
        <!-- 検索アイコン表示 -->
        <!-- 検索アイコン押下時モーダル画面を表示 -->
    <button id="openModal">検索</button>

    <div id="modal" class="modal">
        <div class="modal-content">
            <span class="close-button">&times;</span>
            <h2>家事検索</h2>
            <input type="text" id="userInput" placeholder="ここに入力してください">
            <button id="submitButton">検索</button>
        </div>
    </div> 

        <div class="contents">
            <a>ホーム</a>
            <a href="">一覧</a>
            <a>登録</a>
            <a>分析</a>
            <a>くじ</a>
        </div>
    </main>

<!-- フッター -->
    <footer>
        <!-- 各コンテンツのアイコンを横に並べる -->

    </footer>
<script>
    'use strict';
/* 検索画面をモーダル表示 */
//家事カードを押下時更新画面をモーダル表示
    // 更新ボタンを押下時、更新確認モーダルを表示
// ごみ箱アイコンを押下時消去確認モーダル表示示 
    const modal = document.getElementById("modal");
    const openModalBtn = document.getElementById("openModal");
    const closeBtn = document.querySelector(".close-button");
    const submitBtn = document.getElementById("submitButton");
    const userInput = document.getElementById("userInput");

    openModalBtn.onclick = function() {
    modal.style.display = "block";
    }

    closeBtn.onclick = function() {
    modal.style.display = "none";
    }

    window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
    }

    submitBtn.onclick = function() {
    alert("入力された内容: " + userInput.value);
    }



</script>
</body>
</html>