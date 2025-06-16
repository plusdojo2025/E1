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
		
			<select name="category_id">
			<option value="1">掃除</option>
			<option value="2">洗濯</option>
			<option value="3">料理</option>
			<option value="4">その他</option>
			</select>
			 
			
				
			<label>家事名<input type="text" name="housework_name"><br></label>
		 				  		
		  		頻度
			<select name="frequency"> 
			<option value="everyday">毎日</option>
			<option value="week">曜日ごと</option>
			<option value="irregular">不定期</option>
			</select>
				
			<label>負担度</label>
			<div class="stars" id="star-rating">
				  <span class="star" data-value="1">★</span>
				  <span class="star" data-value="2">★</span>
				  <span class="star" data-value="3">★</span>
				  <span class="star" data-value="4">★</span>
				  <span class="star" data-value="5">★</span>
			</div>
			<input type="hidden" name="housework_level" id="housework_level" value="0">
				
			<label>メモ<input type="text" name="manual"><br></label>
				
			<label>通知<input type="text" name="noti_flag"><br></label>
				
			<label>担当者<input type="text" name="fixed_role"><br></label>
				
				
			
        <td colspan="2">
			<input type="submit" name="regist" value="登録"><br>
			<span id="error_message"></span>
        </td>
      
		
		<script src="js/housework_regist.js"></script>
		</form>

	</main>

	</body>
</html>
		