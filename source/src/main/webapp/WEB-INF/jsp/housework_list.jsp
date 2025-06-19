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
    <!-- 一旦コメントアウト 
        <form method="POST" id="tabsearch_form" action="<c:url value='/HWSearchServlet' />">
        <input type="submit" name="searchType" value="掃除">
        <input type="submit" name="searchType" value="洗濯">
        <input type="submit" name="searchType" value="料理">
        <input type="submit" name="searchType" value="その他">
        </form>-->
        <form method="GET" id="tabsearch_form" action="<c:url value='/HWSearchServlet' />">
		  <input type="hidden" name="sortOrder" value="${param.sortOrder != null ? param.sortOrder : 'asc'}" />
		  <button type="submit" name="searchType" value="掃除">掃除</button>
		  <button type="submit" name="searchType" value="洗濯">洗濯</button>
		  <button type="submit" name="searchType" value="料理">料理</button>
		  <button type="submit" name="searchType" value="その他">その他</button>
		</form>
    </div>

<!-- 家事一覧表示 -->
    <!-- 負担度、家事名、消去ラベル表示 -->
     <!-- 昇降順切替用フォーム -->
	<form id="sortForm" method="GET" action="<c:url value='/HWSearchServlet' />">
	  <input type="hidden" name="sortOrder" id="sortOrderInput" value="${param.sortOrder != null ? param.sortOrder : 'asc'}" />
	  <input type="hidden" name="searchType" value="${param.searchType != null ? param.searchType : ''}" />
	</form>

    <!-- ソート切替ボタン付きテーブル -->
    <table>
        <tr class="card_label">
            <th>
			  <button type="button" id="sortToggleBtn" class="sort-button" title="負担度で並び替え">
			    <img id="sortIcon" src="${pageContext.request.contextPath}/img/<c:out value='${param.sortOrder == "desc" ? "sort_down.svg" : "sort_up.svg"}'/>" 
			         alt="ソートアイコン" style="width:16px; height:16px; vertical-align:middle;">
			  </button>
			  負担度
			</th>
            <th>家事名</th>
            <th>削除</th>
        </tr>
    <!--  <table>
    <tr class="card_label">
        <th>負担度</th>
        <th>家事名</th>
        <th>削除</th>
    </tr>-->

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
				  <form method="POST" id="delete_form_${e.housework_id}" action="<c:url value='/HWUpdateDeleteServlet' />" style="display:none;">
				    <input type="hidden" name="housework_id" value="${e.housework_id}">
				    <input type="hidden" name="housework_name" value="${e.housework_name}">
				    <input type="hidden" name="family_id" value="${e.family_id}">
				    <input type="hidden" name="category_id" value="${e.category_id}">
				    <input type="hidden" name="housework_level" value="${e.housework_level}">
				    <input type="hidden" name="noti_flag" value="${e.noti_flag}">
				    <input type="hidden" name="noti_time" value="${e.noti_time}">
				    <input type="hidden" name="frequency" value="${e.frequency}">
				    <input type="hidden" name="manual" value="${e.manual}">
				    <input type="hidden" name="fixed_role" value="${e.fixed_role}">
				    <input type="hidden" name="variable_role" value="${e.variable_role}">
				    <input type="hidden" name="log" value="${e.log}">
				    <input type="hidden" name="action_type" value="削除">
				  </form>
				  <button class="js-modal-button" data-id="${e.housework_id}">削除</button>
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
  	<span class="close-button">&times;</span>
    <p>この情報で更新しますか？</p>
    <button id="confirmCancel">Cancel</button>
    <button id="confirmOk">OK</button>
  </div>
</div>
<!-- 削除確認モーダルの中身 -->
<div id="deleteConfirmModal" class="modal" style="display:none;">
  <div class="modal-content">
  	<span class="close-button">&times;</span>
    <p>本当に削除しますか？</p>
    <button id="cancelDeleteBtn">Cancel</button>
    <button id="confirmDeleteBtn">OK</button>
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
// 負担度で昇順降順
	/*document.addEventListener("DOMContentLoaded", function () {
	    const sortToggleBtn = document.getElementById("sortToggleBtn");
	    const sortOrderInput = document.getElementById("sortOrderInput");
	    const sortIcon = document.getElementById("sortIcon");
	
	    // JSTLの変数をJavaScriptで使うために事前に定義しておく
	    const contextPath = "<c:out value='${pageContext.request.contextPath}' />";
	
	    sortToggleBtn.addEventListener("click", function () {
	    	  if (sortOrderInput.value === "asc") {
	    	    sortOrderInput.value = "desc";
	    	    sortIcon.src = contextPath + "/img/sort_down.svg";
	    	  } else {
	    	    sortOrderInput.value = "asc";
	    	    sortIcon.src = contextPath + "/img/sort_up.svg";
	    	  }

	    	  // カテゴリ選択があれば保持してform送信
	    	  document.getElementById("sortForm").submit();
	    	});*/
	    // 負担度で昇順降順
		document.addEventListener("DOMContentLoaded", function () {
		    const sortToggleBtn = document.getElementById("sortToggleBtn");
		    const sortOrderInput = document.getElementById("sortOrderInput");
		    const sortIcon = document.getElementById("sortIcon");
		
		    // JSTLの変数をJavaScriptで使うために事前に定義しておく
		    const contextPath = "<c:out value='${pageContext.request.contextPath}' />";
		
		    sortToggleBtn.addEventListener("click", function () {
		      if (sortOrderInput.value === "asc") {
		        sortOrderInput.value = "desc";
		        sortIcon.src = contextPath + "/img/sort_down.svg";
		      } else {
		        sortOrderInput.value = "asc";
		        sortIcon.src = contextPath + "/img/sort_up.svg";
		      }
		
		      document.getElementById("sortForm").submit();
		    });
		  });
    
	// 検索画面をモーダル表示 	
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
document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("deleteConfirmModal");
    let currentForm = null;

    // 削除ボタンをクリックしてモーダルを開く
    document.querySelectorAll(".js-modal-button").forEach(button => {
        button.addEventListener("click", function () {
            const formId = "delete_form_" + this.getAttribute("data-id");
            currentForm = document.getElementById(formId);
            modal.style.display = "block";
        });
    });

    // Cancelボタンで閉じる
    document.getElementById("cancelDeleteBtn").onclick = function () {
        modal.style.display = "none";
        currentForm = null;
    };

    // OKボタンで削除実行
    document.getElementById("confirmDeleteBtn").onclick = function () {
        if (currentForm) {
            currentForm.submit();
        }
    };

    // ×ボタン（クローズボタン）で閉じる
    const closeBtn = modal.querySelector(".close-button");
    if (closeBtn) {
        closeBtn.onclick = function () {
            modal.style.display = "none";
            currentForm = null;
        };
    }

    // モーダル背景クリックでも閉じる（オプション）
    window.addEventListener("click", function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
            currentForm = null;
        }
    });
});


// 処理の結果表示
    window.onload = function () {
    // 更新
    const update_message = "<c:out value='${update_message}' />";
    const update_error = "<c:out value='${update_error}' />";
    
    if (update_message && update_message.trim().length > 0) {
        alert(update_message);
    }

    if (update_error && update_error.trim().length > 0) {
        alert(update_error);
    }

    // 削除
    const delete_message = "<c:out value='${delete_message}' />";
    const delete_error = "<c:out value='${delete_error}' />";

    if (delete_message && delete_message.trim().length > 0) {
        alert(delete_message);
    }

    if (delete_error && delete_error.trim().length > 0) {
        alert(delete_error);
    }
};
</script>
</body>
</html>