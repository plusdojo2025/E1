<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!DOCTYPE html>
    <html>

    <head>
      <meta charset="UTF-8">
      <title>家事一覧</title>
      <link rel="stylesheet" type="text/css" href="css/housework_list.css">
      <!-- ↑cssのパスを動的に取得 -->
      <!--  <link rel="stylesheet" type="text/css" href="<css/common.css">-->
    </head>

    <body>
      <!-- ヘッダー -->
      <header>
        <!-- 通知とログアウトを入れる -->
      </header>
      <!-- メイン -->
      <main class="housework_list_wrapper">
        <h2>家事一覧</h2>
        <!--家事タブを押したときはカテゴリごとに検索、表示 -->
        <div class="tab_container">
          <form method="GET" id="tabsearch_form" action="${pageContext.request.contextPath}/HWSearchServlet">
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
        <form id="sortForm" method="GET" action="${pageContext.request.contextPath}/HWSearchServlet">
          <input type="hidden" name="sortOrder" id="sortOrderInput"
            value="${param.sortOrder != null ? param.sortOrder : 'asc'}" />
          <input type="hidden" name="searchType" value="${param.searchType != null ? param.searchType : ''}" />
          <input type="hidden" name="housework_name" value="${housework_name}">
          <input type="hidden" name="category_id" value="${category_id}">
          <input type="hidden" name="noti_flag" value="${noti_flag}">
          <input type="hidden" name="frequency" value="${frequency}">
        </form>

        <!-- ソート切替ボタン付きテーブル -->
        <table>
          <tr class="card_label">
            <th>
              <button type="button" id="sortToggleBtn" class="sort-button" title="負担度で並び替え">
                <img id="sortIcon" src="img/<c:out value='${param.sortOrder == " desc" ? "sort_down.svg" : "sort_up.svg"
                  }' />"
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
                <input type="hidden" name="housework_level" value="${e.housework_level}">
                <!-- 家事負担度<input type="text" name="housework_level" value="${e.housework_level}"> -->
              </td>
              <td class="housework_name open-modal" data-housework-name="${e.housework_name}"
                data-housework-id="${e.housework_id}" data-family-id="${e.family_id}"
                data-category-id="${e.category_id}" data-housework-level="${e.housework_level}"
                data-noti-flag="${e.noti_flag}" data-noti-time="${e.noti_time}" data-frequency="${e.frequency}"
                data-manual="${e.manual}" data-fixed-role="${e.fixed_role}" data-variable-role="${e.variable_role}"
                data-log="${e.log}">

                <!--家事名押下時更新モーダル表示-->
                <!-- <form id="updateForm" method="POST" onsubmit="return cancelsubmit()" action="<c:url value='/HWUpdateDeleteServlet' />">
		           -->
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
                <input type="hidden" name="housework_name" value="${e.housework_name}">
                <!-- 家事名<input type="text" name="housework_name" value="${e.housework_name}"> -->
                <!-- </form> -->
              </td>
              <td class="delete">
                <form method="POST" id="delete_form_${e.housework_id}"
                  action="${pageContext.request.contextPath}/HWUpdateDeleteServlet" style="display:none;">
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
                <button class="js-modal-button" data-id="${e.housework_id}">
                  <img src="<c:url value='/img/trash.svg' />" alt="削除" width="24" height="24">
                </button>
              </td>
              <!-- 負担度、家事名の範囲を押下時、家事更新画面をモーダル表示 -->
            </tr>
          </c:forEach>
          <!--</div>-->
        </table>
        <!-- 家事が追加されるごとに行を追加 -->

        <!-- 検索アイコン押下時モーダル画面を表示 -->
        <button id="openSearchModal">検索</button>
        <!-- 検索モーダルの中身 -->
        <div id="searchModal" class="modal" style="display: none;">
          <div class="modal-content">
            <!-- <span class="close-button">&times;</span> -->
            <span class="close-button">&times;</span>
            <h2>家事検索</h2>
            <form id="userInput" method="GET" action="${pageContext.request.contextPath}/HWSearchServlet">
              <input type="hidden" name="housework_id" id="modal-housework-id" />
              <input type="hidden" name="family_id" />
              <input type="hidden" name="housework_level" />
              <input type="hidden" name="noti_time" id="modal-noti-time" />
              <input type="hidden" name="manual" />
              <input type="hidden" name="fixd_role" />
              <input type="hidden" name="variable_role" />

              <label>カテゴリー:</label>
              <select name="category_id">
              	<option value="0">すべて</option>
                <option value="1">掃除</option>
                <option value="2">洗濯</option>
                <option value="3">料理</option>
                <option value="4">その他</option>
              </select><br>

              <label>家事名:</label>
              <input type="text" name="housework_name"><br>

              <label>頻度:</label>
              <select name="frequency">
              	<option value="-1">すべて</option>
                <option value="0">毎日</option>
                <option value="1">月</option>
                <option value="2">火</option>
                <option value="3">水</option>
                <option value="4">木</option>
                <option value="5">金</option>
                <option value="6">土</option>
                <option value="7">日</option>
                <option value="8">不定期</option>
              </select><br>

              <label>通知ON/OFF:</label>
              <input type="radio" name="noti_flag" value="-1" checked> すべて            
              <input type="radio" name="noti_flag" value="0"> OFF
              <input type="radio" name="noti_flag" value="1"> ON<br>
              <input type="submit" name="search" value="検索">
            </form>
          </div>
        </div>

        <!-- 更新モーダルの中身 -->
        <div id="updateModal" class="modal modal-inner" style="display: none;">
          <div class="modal-content">
            <span class="close-button">&times;</span>
            <h2>家事情報を編集</h2>
            <!-- 更新フォームに渡す値 -->
            <form id="updateForm" method="POST" action="${pageContext.request.contextPath}/HWUpdateDeleteServlet">
              <!-- 家事ID非表示 hidden-->
              <label>家事ID（最終はhidden）：
                <input type="text" name="housework_id" id="housework-id" value="" /></label><br>
              <label>家事名（必須）：</label>
              <input type="text" name="housework_name" id="modal-housework-name" value="" /><br>
              <!-- ファミリーID非表示 hidden-->
              <label>ファミリーID（最終はhidden）：</label>
              <input type="text" name="family_id" id="family-id" value="" /><br>

              <label>カテゴリ：</label>
              <!-- <input type="hidden" name="category_id" id="modal-category-id" value="" /><br> -->
              <select name="category_id" id="modal-category-id">
                <option value="1">掃除</option>
                <option value="2">洗濯</option>
                <option value="3">料理</option>
                <option value="4">その他</option>
              </select><br>

              <label>家事負担度（必須）：</label>
              <!-- <input type="text" name="housework_level" id="modal-housework-level" value="" /><br> -->

              <select name="housework_level" id="modal-housework-level">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
              </select><br>

              <label>通知：</label>
              <!-- <input type="text" name="noti_flag" id="modal-noti-flag" value="" /><br> -->

              <select name="noti_flag" id="modal-noti-flag">
                <option value="0">OFF</option>
                <option value="1">ON</option>
              </select>

              <!-- 通知 -->
              <!-- <label>通知</label>
              <input type="radio" name="noti_flag" value=0 checked id="noti-off">off
              <input type="radio" name="noti_flag" value=1 id="noti-on">on

              <br>
              <label for="noti_time"></label>
              <input type="time" id="noti_time" name="noti_time" class="noti-hidden">
              </label> -->

              <label>通知時間：
                <input type="time" name="noti_time" id="noti-time" value="" style="display: none;" /><br>
              </label>
              <!-- セレクトにしたい -->
              <div class="form-group">
                <label>家事頻度（必須）：</label>
                <!-- <input type="text" name="frequency" id="modal-frequency" value="" /><br> -->
                <select name="frequency" id="modal-frequency">
                  <!-- 曜日チェックボックスが使えるならこっちを採用
                  <option value="0">毎日</option>
                  <option value="1">曜日を選択</option>
                  <option value="8">不定期</option> 
                  -->

                  <!-- 応急処置としてプルダウンからの選択にしてあります -->
                  <option value="0">毎日</option>
                  <option value="1">月曜日</option>
                  <option value="2">火曜日</option>
                  <option value="3">水曜日</option>
                  <option value="4">木曜日</option>
                  <option value="5">金曜日</option>
                  <option value="6">土曜日</option>
                  <option value="7">日曜日</option>
                  <option value="8">不定期</option>

                </select><br>
                <!-- 曜日チェック（最初は非表示） -->
                <div id="daysContainer" class="days-container">
                  <input type="checkbox" id="mon" name="days" value="1" class="day-checkbox">
                  <label for="mon" class="day-label">月</label>
                  <input type="checkbox" id="tue" name="days" value="2" class="day-checkbox">
                  <label for="tue" class="day-label">火</label>
                  <input type="checkbox" id="wed" name="days" value="3" class="day-checkbox">
                  <label for="wed" class="day-label">水</label>
                  <input type="checkbox" id="thu" name="days" value="4" class="day-checkbox">
                  <label for="thu" class="day-label">木</label>
                  <input type="checkbox" id="fri" name="days" value="5" class="day-checkbox">
                  <label for="fri" class="day-label">金</label>
                  <input type="checkbox" id="sat" name="days" value="6" class="day-checkbox">
                  <label for="sat" class="day-label">土</label>
                  <input type="checkbox" id="sun" name="days" value="7" class="day-checkbox">
                  <label for="sun" class="day-label">日</label>
                </div>
              </div>

              <!-- <label>メモ（マニュアルなど）：</label>
              <input type="text" name="manual" id="modal-manual" value="" /><br> -->

              <!-- メモ -->
              <label>マニュアル</label>
              <!-- メモ記入用のモーダル -->
              <div id="memoModal" class="modal"
                style="display:none; position:fixed; top:20%; left:50%; transform:translate(-50%, 0); background:white; padding:20px; border:1px solid #ccc; z-index:1000;">
                <h3>メモを入力</h3>
                <textarea id="modal-manual" name="manual" rows="10" cols="40"></textarea><br>
                <button type="button" onclick="saveMemo()">保存</button>
                <button type="button" onclick="closeModal()">キャンセル</button>
              </div>
              <!-- モーダルを開くボタン -->
              <button type="button" onclick="openModal()">マニュアルを書く</button>
              <!-- 実際のフォームの中に隠しフィールドとして保持 -->
              <input type="hidden" name="manual" id="manual">
              <br>


              <label>固定担当者：</label>
              <!-- 担当者と通知はラジオボタンにしたい
              <input type="radio" name="fixed_role" id="modal-fixed-role" value="0" checked> OFF
              <input type="radio" name="fixed_role" id="modal-fixed-role" value="1"> ON
              -->
              <!-- <input type="text" name="fixed_role" id="modal-fixed-role" value="" /><br> -->

              <select name="fixed_role" id="modal-fixed-role">
                <option value="0">決定しない</option>
                <option value="1">決定する</option>
              </select><br>

              <label>可変担当者：
                <!-- <input type="text"> ダメなら手入力 -->
                <select name="variable_role" id="modal-variable-role" style="display: none;">
                  <option value="0">テスト担当者</option>
                  <option value="1">テスト担当者</option>
                </select>
              </label>

              <!-- 同じ家族IDのデータを表示する -->
              <!-- 担当者選択プルダウン -->
              <!-- <select name="variable_role" id="modal-variable_role" style="display: none;">
                <c:forEach var="user" items="${userList}">
                  <option value="${user.user_id}">
                    <c:out value="${user.user_id}" />
                  </option>
                </c:forEach>
              </select>
              <br> -->

              <!-- ログ非表示 hidden-->
              <label>ログ 最終はhidden：</label>
              <input type="text" name="log" id="modal-log" value="" /><br>

              <!-- エラーメッセージ表示エリア -->
              <span id="update_error_message">※必須項目</span><br>

              <!--  <button type="button" id="updateTrigger">更新</button> -->
              <input type="submit" id="updateTrigger" name="submit" value="更新" />
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
      </main>

      <!-- フッター -->
      <footer>
        <!-- 各コンテンツのアイコンを横に並べる -->
        <div class="contents">
          <form method="GET" id="contents_form" action="${pageContext.request.contextPath}/HWSearchServlet">
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
        const openModalBtn = document.getElementById("openSearchModal");
        const closeBtn = document.querySelector(".close-button");
        const submitBtn = document.getElementById("submitButton");
        const userInput = document.getElementById("userInput");

        openModalBtn.onclick = function () {
          searchModal.style.display = "block";
        }
        closeBtn.onclick = function () {
          searchModal.style.display = "none";
        }
        window.onclick = function (event) {
          if (event.target === searchModal) {
            searchModal.style.display = "none";
          }
        }

        /*    submitBtn.onclick = function() {
            alert("入力 " + userInput.value);
            } */

        //  家事名押下時のスクリプト 家事カードを押下時更新画面をモーダル表示 
        document.addEventListener("DOMContentLoaded", function () {
          const updateModal = document.getElementById("updateModal");
          const closeBtn = updateModal.querySelector(".close-button");
          const updateForm = document.getElementById("updateForm");
          const updateTrigger = document.getElementById("updateTrigger");
          //const modalName = document.getElementById("modal-housework-name");

          document.querySelectorAll(".open-modal").forEach(function (td) {
            td.addEventListener("click", function () {
              // エラーメッセージの初期化
              document.getElementById("update_error_message").textContent = "※必須項目";

              //const name = this.dataset.houseworkName;
              //modalName.value = "家事名: " + name;
              //updateModal.style.display = "block";

              // モーダル内に値を格納
              const housework_name = this.dataset.houseworkName;
              const housework_id = this.dataset.houseworkId;
              const family_id = this.dataset.familyId;
              const category_id = this.dataset.categoryId;
              const housework_level = this.dataset.houseworkLevel;
              const noti_flag = this.dataset.notiFlag;
              const noti_time = this.dataset.notiTime;
              const frequency = this.dataset.frequency;
              const manual = this.dataset.manual;
              const fixed_role = this.dataset.fixedRole;
              const variable_role = this.dataset.variableRole;
              const log = this.dataset.log;

              // 更新モーダルに値を表示
              console.log(housework_level);
              document.getElementById("modal-housework-name").value = housework_name;
              // ↓なぜかmoodal-を付けたら動かない
              //document.getElementById("modal-housework-id").value = housework_id;
              console.log("housework_id:", housework_id);
              document.getElementById("family-id").value = family_id;
              document.getElementById("modal-category-id").value = category_id;
              document.getElementById("modal-housework-level").value = housework_level;
              document.getElementById("modal-noti-flag").value = noti_flag;
              document.getElementById("noti-time").value = noti_time;
              document.getElementById("modal-frequency").value = frequency;
              document.getElementById("modal-manual").value = manual;
              document.getElementById("modal-fixed-role").value = fixed_role;
              document.getElementById("modal-variable-role").value = variable_role;
              document.getElementById("modal-log").value = log;
              document.getElementById("housework-id").value = housework_id;
              updateModal.style.display = "block";
            });
          });

          updateTrigger.addEventListener("click", function (event) {
            //必須項目の入力チェック
            const updateErrorCheck1 = document.getElementById("modal-housework-name").value.trim();
            const updateErrorCheck2 = document.getElementById("family-id").value.trim();
            const updateErrorCheck3 = document.getElementById("modal-category-id").value.trim();
            const updateErrorCheck4 = document.getElementById("modal-housework-level").value.trim();
            const updateErrorCheck5 = document.getElementById("modal-noti-flag").value.trim();
            const updateErrorCheck6 = document.getElementById("modal-frequency").value.trim();
            const updateErrorCheck7 = document.getElementById("modal-fixed-role").value.trim();
            const updateErrorCheck8 = document.getElementById("housework-id").value.trim();
            // const updateErrorCheck9 = document.getElementById("").value.trim();
            // const updateErrorCheck10 = document.getElementById("").value.trim();
            // const updateErrorCheck11 = document.getElementById("").value.trim();
            // const updateErrorCheck12 = document.getElementById("").value.trim();

            if (updateErrorCheck1 === '' ||
              updateErrorCheck2 === '' ||
              updateErrorCheck3 === '' ||
              updateErrorCheck4 === '' ||
              updateErrorCheck5 === '' ||
              updateErrorCheck6 === '' ||
              updateErrorCheck7 === '' ||
              updateErrorCheck8 === ''
            ) {
              document.getElementById("update_error_message").textContent = "必須項目が未入力です";
              event.preventDefault();
              return;
            }

            const confirmedCheck = window.confirm("この情報で更新しますか？");
            if (confirmedCheck === true) {
              const housework_name = this.dataset.houseworkName;
              const housework_id = this.dataset.houseworkId;
              const family_id = this.dataset.familyId;
              const category_id = this.dataset.categoryId;
              const housework_level = this.dataset.houseworkLevel;
              const noti_flag = this.dataset.notiFlag;
              const noti_time = this.dataset.notiTime;
              const frequency = this.dataset.frequency;
              const manual = this.dataset.manual;
              const fixed_role = this.dataset.fixedRole;
              const variable_role = this.dataset.variableRole;
              const log = this.dataset.log
              updateForm.submit();
            } else {
              window.alert("送信を取り消しました");
              event.preventDefault();
            }
          });
          closeBtn.onclick = function () {
            updateModal.style.display = "none";
          };
          window.onclick = function (event) {
            if (event.target === updateModal) {
              updateModal.style.display = "none";
            }
          };
        });

        //   // 更新ボタン押下時 → 確認モーダル表示
        //   updateTrigger.addEventListener("click", function () {
        //     confirmModal.style.display = "block";
        //   });
        //   // キャンセル → 確認モーダル非表示
        //   confirmCancel.addEventListener("click", function () {
        //     confirmModal.style.display = "none";
        //   });
        //   // OK → モーダル閉じてフォーム送信
        //   confirmOk.addEventListener("click", function () {
        //     confirmModal.style.display = "none";
        //     updateForm.submit(); // 実際に送信
        //   });
        //   // 背景クリックで閉じる
        //   window.addEventListener("click", function (event) {
        //     if (event.target === confirmModal) {
        //       confirmModal.style.display = "none";
        //     }
        //   });
        // });

        //ごみ箱アイコンを押下時消去確認モーダル表示
        document.addEventListener("DOMContentLoaded", function () {
          const modal = document.getElementById("deleteConfirmModal");
          let currentForm = null;

          // ごみ箱アイコンを押したらモーダルを開く
          document.querySelectorAll(".js-modal-button").forEach(button => {
            button.addEventListener("click", function () {
              const houseworkId = this.getAttribute("data-id");
              const formId = "delete_form_" + houseworkId;
              currentForm = document.getElementById(formId);
              if (currentForm) {
                modal.style.display = "block";
              } else {
                console.error("フォームが見つかりません:", formId);
              }
            });
          });

          // Cancelボタンでモーダルを閉じる
          document.getElementById("cancelDeleteBtn").addEventListener("click", function () {
            modal.style.display = "none";
            currentForm = null;
          });

          // OKボタンでフォーム送信
          document.getElementById("confirmDeleteBtn").addEventListener("click", function () {
            if (currentForm) {
              currentForm.submit();
            }
          });

          // ×（クローズボタン）で閉じる
          const closeBtn = modal.querySelector(".close-button");
          if (closeBtn) {
            closeBtn.addEventListener("click", function () {
              modal.style.display = "none";
              currentForm = null;
            });
          }

          // 背景クリックで閉じる
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


        // 曜日選択プルダウン
        // プルダウンの選択に応じて曜日ボタンを制御
        document.getElementById('modal-frequency').addEventListener('change', function () {
          const selection = this.value;
          const daysContainer = document.getElementById('daysContainer');
          const checkboxes = document.querySelectorAll('.day-checkbox');

          if (selection === '1') {
            // 「曜日を選択」の場合：ボタンを表示し、選択状態をリセット
            daysContainer.classList.remove('hidden');
            checkboxes.forEach(checkbox => {
              checkbox.checked = false;
            });
          } else if (selection === '0') {
            // 「毎日」の場合：ボタンを表示し、すべて選択
            daysContainer.classList.remove('hidden');
            checkboxes.forEach(checkbox => {
              checkbox.checked = true;
            });
          } else if (selection === '8') {
            // 「不定期」の場合：ボタンを非表示にし、選択状態をリセット
            daysContainer.classList.add('hidden');
            checkboxes.forEach(checkbox => {
              checkbox.checked = false;
            });
          }
        });

        document.getElementById('updateForm').addEventListener('submit', function (event) {
          const daySelection = document.getElementById('modal-freqency').value;
          const frequencyInput = document.getElementById('frequency');
          if (daySelection === "0" || daySelection === "8") {
            frequencyInput.value = daySelection;
          } else if (daySelection === "1") {
            const selectedDays = Array.from(document.querySelectorAll('.day-checkbox:checked'))
              .map(cb => cb.value);
            frequencyInput.value = selectedDays.join(",");
          }
        });
        // フォーム送信時の処理
        document.getElementById('updateForm').addEventListener('submit', function (event) {
          const selectedDays = Array.from(document.querySelectorAll('.day-checkbox:checked'))
            .map(checkbox => checkbox.value);
          const daySelection = document.getElementById('modal-frequency').value;
          console.log('選択タイプ:', daySelection);
          console.log('選択された曜日:', selectedDays);
        });

        // メモモーダル
        function openModal() {
          document.getElementById('memoModal').style.display = 'block';
        }
        function closeModal() {
          document.getElementById('memoModal').style.display = 'none';
        }
        function saveMemo() {
          var memo = document.getElementById('modal-manual').value;
          document.getElementById('manual').value = memo;
          closeModal();
        }

        // 通知スクリプト
        // 通知ON/OFFで時間入力を有効・無効にする
        // document.getElementById("noti-off").addEventListener("change", function () {
        //   document.getElementById("noti_time").classList.add("noti-hidden");
        // });
        // document.getElementById("noti-on").addEventListener("change", function () {
        //   document.getElementById("noti_time").classList.remove("noti-hidden");
        // });
        // // ページ読み込み時に状態を正しく初期化（特に戻ってきたとき対策）
        // window.addEventListener("DOMContentLoaded", () => {
        //   const isNotiOn = document.getElementById("noti-on").checked;
        //   document.getElementById("noti_time").classList.toggle("noti-hidden", !isNotiOn);
        // });
        // // 通知ON/OFFに応じて通知時間の表示切替
        // document.querySelectorAll('input[name="noti_flag"]').forEach(radio => {
        //   radio.addEventListener('change', () => {
        //     const timeInput = document.getElementById('noti_time');
        //     if (document.querySelector('input[name="noti_flag"]:checked').value === '1') {
        //       timeInput.classList.remove('noti-hidden');
        //     } else {
        //       timeInput.classList.add('noti-hidden');
        //     }
        //   });
        // });
        // // 初期状態の制御（ページ読み込み時）
        // window.addEventListener("DOMContentLoaded", () => {
        //   const timeInput = document.getElementById('noti_time');
        //   const isOn = document.querySelector('input[name="noti_flag"]:checked').value === '1';
        //   timeInput.classList.toggle('noti-hidden', !isOn);
        // });


        // window.addEventListener("DOMContentLoaded", () => {
        //   const timeInput = document.getElementById("modal-noti-time");
        //   const radios = document.querySelectorAll('input[name="noti_flag"]');

        //   // 初期表示
        //   const isOn = document.querySelector('input[name="noti_flag"]:checked').value === '1';
        //   timeInput.classList.toggle("noti-hidden", !isOn);

        //   // ラジオ変更時
        //   radios.forEach(radio => {
        //     radio.addEventListener("change", () => {
        //       const isNowOn = document.querySelector('input[name="noti_flag"]:checked').value === '1';
        //       timeInput.classList.toggle("noti-hidden", !isNowOn);
        //     });
        //   });
        // });

        // 初期状態の制御（ページ読み込み時）
        window.addEventListener("DOMContentLoaded", () => {
          const timeInput = document.getElementById('noti_time');
          const isOn = document.querySelector('input[name="noti_flag"]:checked').value === '1';
          timeInput.classList.toggle('noti-hidden', !isOn);
        });


        window.addEventListener("load", function () {
          if (!localStorage.getItem("loaded")) {
            localStorage.setItem("loaded", "true");
            window.location.reload();
          }
        });

        // 通知時間 セレクトによる表示・非表示の切り替え valueをチェック
        document.addEventListener("DOMContentLoaded", function () {
          const selectButtons = document.getElementById("modal-noti-flag");
          const dropdown = document.getElementById("noti-time");

          selectButtons.addEventListener("change", function () {
            console.log("選ばれた値:", this.value);
            if (document.getElementById("modal-noti-flag").value === "1") {
              //dropdown.style.display = "inline-block";
              document.getElementById("noti-time").style.display = "inline-block";
              //console.log("通知時間を表示しました");

            } else {
              //dropdown.style.display = "none";
              document.getElementById("noti-time").style.display = "none";
              //console.log("通知時間を非表示にしました");
            }
          });
          //初期状態に応じて切り替え
          if (selectButtons.value === "1") {
            document.getElementById("noti-time").style.display = "inline-block";
          } else {
            document.getElementById("noti-time").style.display = "none";
            //console.log("通知時間を非表示にしました");
          }

        });

        //　担当者 セレクトによる表示・非表示の切り替え valueをチェック
        document.addEventListener("DOMContentLoaded", function () {
          const selectButtons = document.getElementById("modal-fixed-role");
          const dropdown = document.getElementById("modal-variable-role");

          selectButtons.addEventListener("change", function () {
            console.log("選ばれた値:", this.value);
            if (document.getElementById("modal-fixed-role").value === "1") {
              //dropdown.style.display = "inline-block";
              document.getElementById("modal-variable-role").style.display = "inline-block";
              //console.log("通知時間を表示しました");

            } else {
              //dropdown.style.display = "none";
              document.getElementById("modal-variable-role").style.display = "none";
              //console.log("通知時間を非表示にしました");
            }
          });
          //初期状態に応じて切り替え　→　初期状態に応じて切り替えられない
          if (selectButtons.value === "1") {
            document.getElementById("modal-variable-role").style.display = "inline-block";
          } else {
            document.getElementById("modal-variable-role").style.display = "none";
            //console.log("通知時間を非表示にしました");
          }

        });




      </script>
    </body>

    </html>