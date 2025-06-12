<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home2.css">
<title>ホーム</title>
</head>
<body>
 <c:forEach var="e" items="${houseworkList}" varStatus="status" >
 <form method="POST" action="/E1/HomeServlet" id="form${status.index}">
 <input type="hidden" value="${e.housework_level}" 
 name="housework_level">
  <c:out value="${e.housework_name}" />
 <input type="submit" name="submit" value="完了">
 </form>
 </c:forEach>
<button class="button js-modal-button">+</button>
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
 
 <c:forEach var="e" items="${memoList}" varStatus="status" >
 <c:out value="${e.memo}" />
 </c:forEach>
 
<button class="button2 js-modal-button2">+</button>
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
			<input type="text" name="memo">
			<input type="submit" name="submit" value="メモ追加">
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
 </script>
 
</body>

</html>