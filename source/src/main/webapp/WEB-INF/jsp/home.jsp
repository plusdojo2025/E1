<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" type="text/css" href="css/home.css">
<!-- <link rel="stylesheet" type="text/css" href="css/common.css"> -->
<title>ホーム</title>
</head>
<body>
<!-- ヘッダー（ここから） -->
<header>
<div id="top_nav">
	<a href="${pageContext.request.contextPath}/HomeServlet">
	<img src="img/logo_lightblue.png" alt="カジミエール" id="logo_img">
	</a>
	<a href="${pageContext.request.contextPath}/NotificationServlet">
	<img src="img/noti.svg" alt="通知" id="noti_img">
	</a>
</div>
<div id="bar">
<img src="img/bar.png" alt="" id="bar_img">
</div>
</header>
<!-- ヘッダー（ここまで） -->

<main>
<div id="housework_container">
<div id="housework_list">
	<h2 id="today_housework">本日の家事</h2>
	 <c:forEach var="e" items="${houseworkList}" varStatus="status" >
	 <div class="housework">
	 <button value="完了" class="button3 js-modal-button3" id="complete${e.housework_id}">完了</button>
	 <div id="task${e.housework_id}">
	 <c:out value="${e.housework_name}" /> 
	</div>
	</div>
	  <div class="layer3 js-modal3">
  <div class="modal3">
    <div class="modal__inner3">
     <div class="modal__button-wrap3">
       <button class="close-button3 js-close-button3">
         <span></span>
         <span></span>
       </button>
      </div>
      <div class="modal__contents3">
        <div class="modal__content3">
        <form method="POST" action="${pageContext.request.contextPath}/HomeServlet" id="form${status.index}">
	 <input type="hidden" name="housework_id" value="${e.housework_id}">
	 <h6>完了チェックを行いますか？</h6>
	 <br>
	 <button class="cancel">Cancel</button>
	 <button class="ok" name="submit" value="完了">OK</button>
</form>
        </div>
      </div>
    </div>
  </div>
</div>
	 <br> 
	 </c:forEach>
	 </div>
	 <div id="housework_add">
	<button class="button js-modal-button">+</button>
	</div>
</div>
	<div class="layer js-modal">
	  <div class="modal">
	    <div class="modal__inner">
	     <div class="modal__button-wrap">
	       <button class="close-button js-close-button">
	         <span></span>
	         <span></span>
	       </button>
	      </div>
	      <div class="modal__contents">
	        <div class="modal__content">
	          <form method="POST" action="${pageContext.request.contextPath}/HomeServlet" id="form${status.index}">
				 <select name="housework_id" id="irreguular">
				 <c:forEach var="e" items="${irregular_houseworkList}" varStatus="status" >
					<option value="${e.housework_id}"><c:out value="${e.housework_name}" /></option>
				</c:forEach>
					</select>
					<input type="submit" name="submit" value="家事追加" id="housework_submit">
					
				 </form>
				 <br>
					<div id="housework_error"></div>
	        </div>
	      </div>
	    </div>
	  </div>
	</div>
<div id="memo_container">
 <div id="memo">
 	<h3 id="today_memo">memo</h3>
	 <c:forEach var="e" items="${memoList}" varStatus="status" >
	 <c:out value="${e.memo}" />
	 <br>
	 </c:forEach>
	 <div id="memo_add">
	<button class="button js-modal-button2">+</button>
	</div>
</div>

<div class="layer2 js-modal2">
  <div class="modal2">
    <div class="modal__inner2">
     <div class="modal__button-wrap2">
       <button class="close-button2 js-close-button2">
         <span></span>
         <span></span>
       </button>
      </div>
      <div class="modal__contents2">
        <div class="modal__content2">
          <form method="POST" action="${pageContext.request.contextPath}/HomeServlet" id="form${status.index}">
			<input type="text" name="memo" id="memo_new">
			<input type="submit" name="submit" value="メモ追加" id="memo_submit">
			 </form>
			 <br>
			 <div id="memo_error"></div>
        </div>
      </div>
    </div>
  </div>
</div>
</div>
 </main>
 <!-- フッター（ここから） -->
<div id="footer">
	<div id="bottom_bar">
	<img src="img/bar.png" alt="" id="bottom_bar_img">
	</div>
	  <nav class="bottom_nav">
	    <ul>
	      <li><a href="${pageContext.request.contextPath}/HomeServlet"><img src="img/home.svg" alt="ホーム" id="home_img"></a></li>
	      <li><a href="${pageContext.request.contextPath}/HWSearchServlet"><img src="img/list.svg" alt="一覧" id="list_img"></a></li>
	      <li><a href="${pageContext.request.contextPath}/HWRegistServlet"><img src="img/regist.svg" alt="登録" id="regist_img"></a></li>
	      <li><a href="${pageContext.request.contextPath}/GachaServlet"><img src="img/circle.svg" alt="くじ" id="gacha_img"></a></li>
	      <li><a href="${pageContext.request.contextPath}/AnalysisServlet"><img src="img/analysis.svg" alt="分析" id="analysis_img"></a></li>
	    </ul>
	  </nav>
</div>


<!-- フッター（ここまで） -->
 <script>
 //モーダルのスクリプト
 const modal = document.querySelector('.js-modal'); // layer要素に付与したjs-modalクラスを取得し変数に格納
 const modalButton = document.querySelector('.js-modal-button'); // button要素に付与したjs-modal-buttonクラスを取得し、変数に格納

 // 追記
 const modalClose = document.querySelector('.js-close-button');　// xボタンのjs-close-buttonを取得し変数に格納

 modalButton.addEventListener('click', () => {
   modal.classList.add('is-open');
 });

 // 追記
 modalClose.addEventListener('click', () => { // xボタンをクリックしたときのイベントを登録
   modal.classList.remove('is-open'); 
 });
 
 const modal2 = document.querySelector('.js-modal2'); // layer要素に付与したjs-modalクラスを取得し変数に格納
 const modalButton2 = document.querySelector('.js-modal-button2'); // button要素に付与したjs-modal-buttonクラスを取得し、変数に格納

 // 追記
 const modalClose2 = document.querySelector('.js-close-button2');　// xボタンのjs-close-buttonを取得し変数に格納

 modalButton2.addEventListener('click', () => {
   modal2.classList.add('is-open2');
 });

 // 追記
 modalClose2.addEventListener('click', () => { // xボタンをクリックしたときのイベントを登録
   modal2.classList.remove('is-open2'); 
 });
 
 const modal3 = document.querySelectorAll('.js-modal3'); // layer要素に付与したjs-modalクラスを取得し変数に格納
 const modalButton3 = document.querySelectorAll('.js-modal-button3'); // button要素に付与したjs-modal-buttonクラスを取得し、変数に格納

 // 追記
 const modalClose3 = document.querySelectorAll('.js-close-button3');　// xボタンのjs-close-buttonを取得し変数に格納

 modalButton3.forEach((button, index) => {
	 button.addEventListener('click', () => {
	 modal3[index].classList.add('is-open3');
 });
 });

 // 追記
 modalClose3.forEach((button, index) => {
 	button.addEventListener('click', () => { // xボタンをクリックしたときのイベントを登録
   modal3[index].classList.remove('is-open3'); 
 	});
 });
 
 document.querySelectorAll('.cancel').forEach((button, index) => {
	  button.addEventListener('click', function(event) {
	    event.preventDefault(); // フォーム送信を防ぐ
	    modal3[index].classList.remove('is-open3');
	  });
	});
 
 //完了チェックのスクリプト
 const houseworkIdList = [<c:forEach var="id" items="${idList}" varStatus="status">
 "${id}"<c:if test="${!status.last}">,</c:if>
  </c:forEach>];
 
 houseworkIdList.forEach(id => {
	    const btn = document.getElementById("complete" + id);
	    const text = document.getElementById("task" + id);
	    if (btn) {
	      btn.disabled = true;
	      btn.classList.remove('button3');
	      btn.classList.add('complete');
	      text.classList.add('complete_task');
	    }
	  });
 
 document.getElementById("housework_submit").addEventListener('click', function(event){
	 const housework = document.getElementById("irreguular").value;
	 if (housework === ""){
		 event.preventDefault();
		 document.getElementById("housework_error").textContent = ("頻度が不定期の家事がありません");
	 }
 })
 
 document.getElementById("memo_submit").addEventListener('click', function(event){
	 const memo = document.getElementById("memo_new").value;
	 if (memo === ""){
		 event.preventDefault();
		 document.getElementById("memo_error").textContent = ("未入力です");
	 }
 })
 </script>
 </body>
</html>