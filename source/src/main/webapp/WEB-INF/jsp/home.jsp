<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home2.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home3.css">
<title>ホーム</title>
</head>
<body>
<div id="housework">
<div id="housework_list">
	<h2>本日の家事</h2>
	 <c:forEach var="e" items="${houseworkList}" varStatus="status" >
	 <button value="完了" class="button3 js-modal-button3">完了</button>
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
        <form method="POST" action="/E1/HomeServlet" id="form${status.index}">
	 <input type="hidden" name="housework_id" value="${e.housework_id}" 
	 name="housework_id">
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
	  <c:out value="${e.housework_name}" /> 
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
	          <form method="POST" action="/E1/HomeServlet" id="form${status.index}">
				 <select name="housework_id">
				 <c:forEach var="e" items="${irregular_houseworkList}" varStatus="status" >
					<option value="${e.housework_id}"><c:out value="${e.housework_name}" /></option>
				</c:forEach>
					</select>
					<input type="submit" name="submit" value="家事追加">
				 </form>
	        </div>
	      </div>
	    </div>
	  </div>
	</div>
	
 <div id="memo">
	 <c:forEach var="e" items="${memoList}" varStatus="status" >
	 <c:out value="${e.memo}" />
	 <br>
	 </c:forEach>
	 <div id="memo_add">
	<button class="button2 js-modal-button2">+</button>
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
          <form method="POST" action="/E1/HomeServlet" id="form${status.index}">
			<input type="text" name="memo" id="memo_new">
			<input type="submit" name="submit" value="メモ追加" id="memo_submit">
			 </form>
        </div>
      </div>
    </div>
  </div>
</div>
 

          

 
 <script>
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
 
 </script>
 
</body>

</html>