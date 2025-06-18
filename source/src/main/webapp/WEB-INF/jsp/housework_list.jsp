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
    <h2>家事一覧</h2>
    <!--家事タブを押したときはカテゴリごとに検索、表示 -->        
    <div class="tab_container">
        <form method="POST" id="tabsearch_form" action="<c:url value='/HWSearchServlet' />">
        <input type="submit" name="searchType" value="掃除">
        <input type="submit" name="searchType" value="洗濯">
        <input type="submit" name="searchType" value="料理">
        <input type="submit" name="searchType" value="その他">
        </form>
    </div>

<!-- 家事一覧表示 -->
    <!-- 負担度、家事名、消去ラベル表示 -->
    <table>
    <tr class="card_label">
        <th>負担度</th>
        <th>家事名</th>
        <th>消去</th>
    </tr>

<!-- 取得した家事を一覧表示 -->          
<!--<div class="card_container">-->             
      	<c:forEach var="e" items="${cardList}" varStatus="status">
        	<tr class="card">        
                 <td class="housework_level">
                     <c:out value="${e.housework_level}" />
                     <!-- 家事負担度<input type="text" name="housework_level" value="${e.housework_level}"> -->
                 </td>
                 <td class="housework_name open-modal"
                 data-housework-name="${e.housework_name}"
                 data-housework-id="${e.housework_id}">
                   <!--家事名押下時更新モーダル表示-->
                  <form method="POST" id="search_result_form" action="<c:url value='/HWUpdateDeleteServlet' />">
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
                 
                 <!-- 家事名のみ表示 -->
                 	
                     <c:out value="${e.housework_name}" />                       
                     <!-- 家事名<input type="text" name="housework_name" value="${e.housework_name}"> -->
                 	</form>
                 </td>
                 <td class="delete">
                     <!--  <form method="POST" id="search_result_form" action="<c:url value='/HWUpdateDeleteServlet' />">-->
                     <!-- ごみ箱のイメージを張り付ける -->
                         <button class="js-modal-button" value="消去">消去</button>
                     <!--</form>-->
                 </td>			                
          <!-- 負担度、家事名の範囲を押下時、家事更新画面をモーダル表示 -->
          			</tr>        		
	 		</c:forEach>           		
       	<!--</div>-->
       </table>
        <!-- 家事が追加されるごとに行を追加 -->
    <!-- 検索アイコン表示 -->
    <!-- 検索アイコン押下時モーダル画面を表示 -->
<button id="openModal">検索</button>


<!-- 更新モーダルの中身 -->
<div id="updateModal" class="modal modal-inner" style="display: none;">
  <div class="modal-content">
    <span class="close-button">&times;</span>
    <h2>家事情報を編集</h2>
    <!-- 更新フォームに渡す値 -->
    <form id="updateForm" method="POST" action="<c:url value='/HWUpdateServlet' />">
        <!-- 家事ID -->
      <input type="hidden" name="housework_id" id="modal-housework-id" value="${e.housework_id}" />
      <label>家事名：</label>
      <input type="text" name="housework_name" id="modal-housework-name" value="${e.housework_name}" />
	  <!-- ファミリーID -->
	  <input type="hidden" name="family_id" value="${e.family_id}" />
      <label>カテゴリID：</label>
      <input type="number" name="category_id" id="modal-category-id" value="${e.category_id}"/>
	  <label>家事負担度：</label>
	  <input type="text" name="housework_level" value="${e.housework_level}" />
	　　<label>通知有無：</label>
	  <input type="text" name="noti_flag" value="${e.noti_flag}" />
      <label>通知時間：</label>
      <input type="time" name="noti_time" id="modal-noti-time" value="${e.noti_time}"/>
      <label>家事頻度：</label>
  		<input type="text" name="frequency" value="${e.frequency}" />
     	<label>メモ（マニュアルなど）：</label>
  		  <input type="text" name="manual" value="${e.manual}" />
     	<label>固定担当者：</label>
     	  <input type="text" name="fixd_role" value="${e.fixed_role}" />
      	<label>可変担当者：</label>
      	  <input type="text" name="variable_role" value="${e.variable_role}" />
	  <!-- ファミリーID -->
		<input type="hidden" name="log" value="${e.log}" />    	    	
     	
      <!-- 他にも編集したい項目を追加 -->

      <button type="submit" id="updateTrigger">更新</button>
    </form>
  </div>
</div>
<!-- 更新確認モーダルの中身 -->
<div id="confirmModal" class="modal" style="display: none;">
  <div class="modal-content">
    <p>この情報で更新しますか？</p>
    <button id="confirmCancel">Cancel</button>
    <button id="confirmOk">OK</button>
  </div>
</div>







                      



<!-- 検索モーダルの中身 -->
<div id="searchModal" class="modal">
    <div class="modal-content">
        <span class="close-button">&times;</span>
        <h2>家事検索</h2>
        <input type="text" id="userInput" placeholder="ここに入力してください">
        <button id="submitButton">検索</button>
    </div>
</div> 


</main>
<!-- フッター -->
<footer>
    <!-- 各コンテンツのアイコンを横に並べる -->
    <div class="contents">
     <form method="GET" id="contents_form" action="<c:url value='/HWSearchServlet' />">
         <a>ホーム</a>
         <input type="submit" name="searchType" value="一覧">
         <a>登録</a>
         <a>分析</a>
         <a>くじ</a>        
     </form>
    </div>
</footer>
<script>
    'use strict';
    
/* 検索画面をモーダル表示 */
    const searchModal = document.getElementById("searchModal");
    const openModalBtn = document.getElementById("openModal");
    const closeBtn = document.querySelector(".close-button");
    const submitBtn = document.getElementById("submitButton");
    const userInput = document.getElementById("userInput");

    openModalBtn.onclick = function() {
    searchModal.style.display = "block";
    }

    closeBtn.onclick = function() {
    searchModal.style.display = "none";
    }

    window.onclick = function(event) {
    if (event.target === modal) {
        searchModal.style.display = "none";
    }
    }

    submitBtn.onclick = function() {
    alert("入力 " + userInput.value);
    }
    
//  家事名押下時のスクリプト 家事カードを押下時更新画面をモーダル表示 
    document.addEventListener("DOMContentLoaded", function () {
      const updateModal = document.getElementById("updateModal");
      const closeBtn = updateModal.querySelector(".close-button");
      const modalName = document.getElementById("modal-housework-name");

      document.querySelectorAll(".open-modal").forEach(function (td) {
        td.addEventListener("click", function () {
          const name = this.dataset.houseworkName;
          const id = this.dataset.houseworkId;
          modalName.textContent = "家事名: " + name + "（ID: " + id + "）";
          updateModal.style.display = "block";
        });
      });

      closeBtn.onclick = function () {
        updateModal.style.display = "none";
      };

      window.onclick = function (event) {
        if (event.target === modal) {
          updateModal.style.display = "none";
        }
      };
    });
    
//  更新ボタンを押下時、更新確認モーダルを表示 スクリプト


// ごみ箱アイコンを押下時消去確認モーダル表示


    
    
</script>
</body>
</html>