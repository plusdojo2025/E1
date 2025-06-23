<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" type="text/css" href="css/housework_regist.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<title>家事登録</title>
</head>
	
	<!-- ヘッダー（ここから） -->
	<header id="header">
	
		<h1 id="logo">
	      <a href="${pageContext.request.contextPath}/HomeServlet">
	      <img src="img/logo_lightblue.png"  alt="カジミエール">
	    </a>
	    </h1>
		
	</header>
<!-- ヘッダー（ここまで） -->
	
	<body>
<!-- メイン（ここから） -->
	
		<h2>家事登録（新しい家事を入力してください）</h2>
		<main class="form-wrapper">
		<form id="form" method="POST" action="${pageContext.request.contextPath}/HWRegistServlet">
		 <!-- カテゴリ -->
		 <div class="form-group">
		<label>カテゴリ:
			<select name="category_id">
			<option value="1">掃除</option>
			<option value="2">洗濯</option>
			<option value="3">料理</option>
			<option value="4">その他</option>
			</select>
		</label>
		</div>	
			
		<!-- 家事名 -->
		<div class="form-group">		
		<label>家事名(必須)
		<input type="text" name="housework_name">
		</label>
		</div> 
		 	
		<!-- 頻度 -->	 	
		<div class="form-group"> 				  		
		<label>頻度（必須）
		
            <select id="daySelection" name="frequency">
            <!-- 頻度の値（送信用） -->
			
            	
                <option value="1">曜日を選択</option>
                <option value="0">毎日</option>
                <option value="8">不定期</option>
            </select>
		</label>
		<br>
		
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
				
		<!-- 負担度 -->		
		
		<div class="stars" id="star-rating">
		<label>負担度
			
				  <span class="star" data-value="1">★</span>
				  <span class="star" data-value="2">★</span>
				  <span class="star" data-value="3">★</span>
				  <span class="star" data-value="4">★</span>
				  <span class="star" data-value="5">★</span>
			
			<input type="hidden" name="housework_level" id="housework_level" value="0">
			
		</label>
		</div>
		
		<!-- メモ -->	
		<label>マニュアル</label>	
		<!-- メモ記入用のモーダル -->
		<div id="memoModal" class="modal" style="display:none; position:fixed; top:20%; left:50%; transform:translate(-50%, 0); background:white; padding:20px; border:1px solid #ccc; z-index:1000;">
		    <h3>メモを入力</h3>
		    <textarea id="memoInput" rows="10" cols="40"></textarea><br>
		    <button type="button" onclick="saveMemo()">保存</button>
		    <button type="button" onclick="closeModal()">キャンセル</button>
		</div>
		
		<!-- モーダルを開くボタン -->
		<button type="button" onclick="openModal()">マニュアルを書く</button>
		<!-- 実際のフォームの中に隠しフィールドとして保持 -->
		<input type="hidden" name="manual" id="manual">
		<br>
		<!-- 通知 -->		
		<label>通知
		<input type="radio" name="noti_flag" value= 0 checked id="noti-off">off
		<input type="radio" name="noti_flag" value= 1 id="noti-on">on
		</label>
		<br>
		<label for="notify_time"></label>
		<input type="time" id="noti_time" name="noti_time" class="noti-hidden">
		
		
		<br>
		
		<!-- 担当者 -->	
		<label>担当者
		<input type="radio" name="fixed_role" value= 0 checked>決定しない
		<input type="radio" name="fixed_role" value= 1 >決定する
		</label>
			
		<!-- 担当者選択プルダウン -->
		 <select name="variable_role" id="variable_role" style="display: none;">
		  <c:forEach var="user" items="${userList}">
		    <option value="${user.user_id}"><c:out value="${user.user_id}"/></option>
		  </c:forEach>
		</select>
		<br>		
				
		<div class="form-submit">	
        <td colspan="2">
			<input type="submit" name="regist" value="登録"><br>
			<span id="error_message"></span>
        </td>
  		<c:out value="${errorMessage}"/>
	
		</div>		
		</form>
		</main>
	<script>
	'use strict'
		//負担度を表す星
	const stars = document.querySelectorAll('#star-rating .star');
	const burdenInput = document.getElementById('housework_level');
	
	stars.forEach(star => {
	  star.addEventListener('click', () => {
	    const value = parseInt(star.dataset.value);
	    burdenInput.value = value; // hiddenに保存
	    // 星を色付け
	    stars.forEach(s => {
	      if (parseInt(s.dataset.value) <= value) {
	        s.classList.add('selected');
	      } else {
	        s.classList.remove('selected');
	      }
	    });
	  });
	});
		//担当者決定方法によるプルダウン表示切替
		document.querySelectorAll('input[name="fixed_role"]').forEach(radio => {
		  radio.addEventListener('change', () => {
		    const variable_role = document.getElementById('variable_role');
		    variable_role.style.display = (document.querySelector('input[name="fixed_role"]:checked').value === '1') ? 'block' : 'none';
		  });
		});
		
		// ページ読み込み時にも状態を初期化
		window.addEventListener('DOMContentLoaded', () => {
		  const variable_role = document.getElementById('variable_role');
		  const isSelected = document.querySelector('input[name="fixed_role"]:checked').value === '1';
		  variable_role.style.display = isSelected ? 'block' : 'none';
		});
		
		
		// プルダウンの選択に応じて曜日ボタンを制御
        document.getElementById('daySelection').addEventListener('change', function() {
            const selection = this.value;
            const daysContainer = document.getElementById('daysContainer');
            const checkboxes = document.querySelectorAll('.day-checkbox');
            if (selection === "1") {
                // 「曜日を選択」の場合：ボタンを表示し、選択状態をリセット
                daysContainer.classList.remove('hidden');
                checkboxes.forEach(checkbox => {
                    checkbox.checked = false;
                });
            } else if (selection === "0") {
                // 「毎日」の場合：ボタンを表示し、すべて選択
                daysContainer.classList.remove('hidden');
                checkboxes.forEach(checkbox => {
                    checkbox.checked = true;
                });
            } else if (selection === "8") {
                // 「不定期」の場合：ボタンを非表示にし、選択状態をリセット
                daysContainer.classList.add('hidden');
                checkboxes.forEach(checkbox => {
                    checkbox.checked = false;
                });
            }
        });
		
		
        document.getElementById('form').addEventListener('submit', function(event) {
            const daySelection = document.getElementById('daySelection').value;
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
        document.getElementById('form').addEventListener('submit', function(event) {
            const selectedDays = Array.from(document.querySelectorAll('.day-checkbox:checked'))
                                     .map(checkbox => checkbox.value);
            const daySelection = document.getElementById('daySelection').value;
            console.log('選択タイプ:', frequency);
            console.log('選択された曜日:', selectedDays);
        });
	
		
		
        //モーダル
        function openModal() {
            document.getElementById('memoModal').style.display = 'block';
        }
        function closeModal() {
            document.getElementById('memoModal').style.display = 'none';
        }
        function saveMemo() {
            var memo = document.getElementById('memoInput').value;
            document.getElementById('manual').value = memo;
            closeModal();
        }
		
     // 通知ON/OFFで時間入力を有効・無効にする
        document.getElementById("noti-off").addEventListener("change", function() {
          document.getElementById("noti_time").classList.add("noti-hidden");
        });
        document.getElementById("noti-on").addEventListener("change", function() {
          document.getElementById("noti_time").classList.remove("noti-hidden");
        });
        // ページ読み込み時に状態を正しく初期化（特に戻ってきたとき対策）
        window.addEventListener("DOMContentLoaded", () => {
          const isNotiOn = document.getElementById("noti-on").checked;
          document.getElementById("noti_time").classList.toggle("noti-hidden", !isNotiOn);
        });
     // 通知ON/OFFに応じて通知時間の表示切替
        document.querySelectorAll('input[name="noti_flag"]').forEach(radio => {
          radio.addEventListener('change', () => {
            const timeInput = document.getElementById('noti_time');
            if (document.querySelector('input[name="noti_flag"]:checked').value === '1') {
              timeInput.classList.remove('noti-hidden');
            } else {
              timeInput.classList.add('noti-hidden');
            }
          });
        });
        // 初期状態の制御（ページ読み込み時）
        window.addEventListener("DOMContentLoaded", () => {
          const timeInput = document.getElementById('noti_time');
          const isOn = document.querySelector('input[name="noti_flag"]:checked').value === '1';
          timeInput.classList.toggle('noti-hidden', !isOn);
        });
        
        
        window.addEventListener("load", function() {
        	  if (!localStorage.getItem("loaded")) {
        	    localStorage.setItem("loaded", "true");
        	    window.location.reload();
        	  }
        });

	
	</script>
	
	
	</body>
	
	
</html>