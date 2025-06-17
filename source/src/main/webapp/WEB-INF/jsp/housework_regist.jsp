<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

   
<title>登録</title>
<link rel="stylesheet" type="text/css" href="css/housework_regist.css">

</head>
	<body>


<!-- メイン（ここから） -->
	<main>
		<h2>家事登録（新しい家事を入力してください）</h2>
		<hr>
		<form id="form" method="POST" action="/E1/HWRegistServlet">
		<!-- カテゴリ -->
		<label>カテゴリ:
			<select name="category_id">
			<option value="1">掃除</option>
			<option value="2">洗濯</option>
			<option value="3">料理</option>
			<option value="4">その他</option>
			</select>
		</label>	 
			
		<!-- 家事名 -->		
		<label>家事名
		<input type="text" name="housework_name"><br>
		</label>
		 	
		<!-- 頻度 -->	 	
		 				  		
		<label>頻度
		 <div>
            <select id="daySelection" name="daySelection">
                <option value="select">曜日を選択</option>
                <option value="daily">毎日</option>
                <option value="irregular">不定期</option>
            </select>
        </div>
		</label>
		<br>
		
		<!-- 曜日チェック（最初は非表示） -->
		
		<div id="daysContainer" class="days-container">
            <input type="checkbox" id="mon" name="days" value="Monday" class="day-checkbox">
            <label for="mon" class="day-label">月</label>

            <input type="checkbox" id="tue" name="days" value="Tuesday" class="day-checkbox">
            <label for="tue" class="day-label">火</label>

            <input type="checkbox" id="wed" name="days" value="Wednesday" class="day-checkbox">
            <label for="wed" class="day-label">水</label>

            <input type="checkbox" id="thu" name="days" value="Thursday" class="day-checkbox">
            <label for="thu" class="day-label">木</label>

            <input type="checkbox" id="fri" name="days" value="Friday" class="day-checkbox">
            <label for="fri" class="day-label">金</label>

            <input type="checkbox" id="sat" name="days" value="Saturday" class="day-checkbox">
            <label for="sat" class="day-label">土</label>

            <input type="checkbox" id="sun" name="days" value="Sunday" class="day-checkbox">
            <label for="sun" class="day-label">日</label>
        </div>
		  
				
		<!-- 負担度 -->		
		<label>負担度
			<div class="stars" id="star-rating">
				  <span class="star" data-value="1">★</span>
				  <span class="star" data-value="2">★</span>
				  <span class="star" data-value="3">★</span>
				  <span class="star" data-value="4">★</span>
				  <span class="star" data-value="5">★</span>
			</div>
			<input type="hidden" name="housework_level" id="housework_level" value="0">
			
		</label>
		
		<!-- メモ -->	
		<label>メモ</label>	
		<!-- メモ記入用のモーダル -->
		<div id="memoModal" class="modal" style="display:none; position:fixed; top:20%; left:50%; transform:translate(-50%, 0); background:white; padding:20px; border:1px solid #ccc; z-index:1000;">
		    <h3>メモを入力</h3>
		    <textarea id="memoInput" rows="10" cols="40"></textarea><br>
		    <button type="button" onclick="saveMemo()">保存</button>
		    <button type="button" onclick="closeModal()">キャンセル</button>
		</div>-->
		
		<!-- モーダルを開くボタン -->
		<button type="button" onclick="openModal()">メモを書く</button>

		<!-- 実際のフォームの中に隠しフィールドとして保持 -->
		<input type="hidden" name="manual" id="manual">
		<br>
		<!-- 通知 -->		
		<label>通知
		<input type="radio" name="noti_flag" value= 0 checked>off
		<input type="radio" name="noti_flag" value= 1 >on
		</label>
		<br>
		<label for="notify-time">通知時間:</label>
		<input type="time" id="noti-time" name="noti-time">
		
		
		<br>
		
		<!-- 担当者 -->	
		<label>担当者
		<input type="radio" name="fixed_role" value= 0 checked>決定しない
		<input type="radio" name="fixed_role" value= 1 >決定する
		</label>
			
		<!-- 担当者選択プルダウン -->
		 <select name="variable_role" id="variable_role" style="display:none;">
		    <option value="user1">ユーザー1</option>
		    <option value="user2">ユーザー2</option>
	     <!--家庭ごとに動的 -->
  		</select>
		<br>		
				
			
        <td colspan="2">
			<input type="submit" name="regist" value="登録"><br>
			<span id="error_message"></span>
        </td>
      
		
		
		</form>
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
		document.querySelectorAll('input[name="assign_method"]').forEach(radio => {
		  radio.addEventListener('change', () => {
		    const assignedUser = document.getElementById('assigned_user');
		    assignedUser.style.display = (document.querySelector('input[name="assign_method"]:checked').value === 'variable') ? 'block' : 'none';
		  });
		});
	
	
	
		// プルダウンの選択に応じて曜日ボタンを制御
        document.getElementById('daySelection').addEventListener('change', function() {
            const selection = this.value;
            const daysContainer = document.getElementById('daysContainer');
            const checkboxes = document.querySelectorAll('.day-checkbox');

            if (selection === 'select') {
                // 「曜日を選択」の場合：ボタンを表示し、選択状態をリセット
                daysContainer.classList.remove('hidden');
                checkboxes.forEach(checkbox => {
                    checkbox.checked = false;
                });
            } else if (selection === 'daily') {
                // 「毎日」の場合：ボタンを表示し、すべて選択
                daysContainer.classList.remove('hidden');
                checkboxes.forEach(checkbox => {
                    checkbox.checked = true;
                });
            } else if (selection === 'irregular') {
                // 「不定期」の場合：ボタンを非表示にし、選択状態をリセット
                daysContainer.classList.add('hidden');
                checkboxes.forEach(checkbox => {
                    checkbox.checked = false;
                });
            }
        });

        // フォーム送信時の処理
        document.getElementById('daysContainer').addEventListener('submit', function(event) {
            const selectedDays = Array.from(document.querySelectorAll('.day-checkbox:checked'))
                                     .map(checkbox => checkbox.value);
            const daySelection = document.getElementById('daySelection').value;
            console.log('選択タイプ:', daySelection);
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
		
	</script>
	
	</main>
	
	
	</body>
	
	
</html>
		