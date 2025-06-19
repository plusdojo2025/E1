<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<input type="text" id="housework_id">
<div id="result"></div>


<c:forEach var="e" items="${notiList}" varStatus="status">
<div class="noti" data-housework-id="${e.housework_id}"
 data-noti-time="${e.noti_time}">
</div>
</c:forEach>

</body>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/push.js/1.0.7/push.min.js"></script>
<script>
const houseworkIds = [];
const notiTimes = [];
const nodes = document.querySelectorAll('.noti');
	
	nodes.forEach(function(el) {
	    houseworkIds.push(el.dataset.houseworkId);
	    notiTimes.push(el.dataset.notiTime);
	  });    
	



const getData = () =>{
	let request = new XMLHttpRequest();
	 request.onreadystatechange = function(e){
	 if (request.readyState == 4){
		 if (request.status == 200){

			 let jsonObject = 
				 JSON.parse(request.responseText);
			 let node = document.getElementById("result");
		        let recv = request.responseText;
		        let abc = new Array();
		        abc += jsonObject.data;
		        node.innerHTML = abc + "JSON TEXT:" + recv;
		        
		        
		        Push.create("お知らせ", {
		            body: "これは Push.js を使ったテスト通知です。",
		            timeout: 8000, // ミリ秒後に自動で閉じる
		            onClick: function () {
		              window.focus();
		              this.close();
		              console.log('通知がクリックされました');
		            }
		        })
		 }else{
			 
		 }
	}
	 }
	let housework = document.getElementById("housework_id").value;
	request.type = "json";
	  request.open('GET', 'http://localhost:8080/E1/TestServlet?housework_id=' + housework, true);
	  request.send();
}

// 一度だけ通知をスケジュールする関数
function scheduleNotificationOnce(timeStr) {
  const [h, m, s] = timeStr.split(':').map(Number);
  const now = new Date();
  const target = new Date(now);
  target.setHours(h, m, s, 0);

  // 時刻が過ぎている場合は、実行しない
  if (target <= now) return;

  const delay = target.getTime() - now.getTime();
  setTimeout(() => {
    sendNotif(timeStr);
  }, delay);
}

setInterval(getData,1000);

</script>
</html>