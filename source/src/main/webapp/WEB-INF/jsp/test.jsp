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
	scheduleAt();     
	
	function scheduleAt(timeStr, task) {
		  const [h, m, s] = timeStr.split(':').map(Number);
		  const now = new Date();
		  const target = new Date(now);
		  target.setHours(h, m, s, 0);

		  // 今日のうちに実行時刻が過ぎていたら、翌日に
		  if (target <= now) {
		    target.setDate(target.getDate() + 1);
		  }

		  const delay = target - now; // ミリ秒
		  setTimeout(() => {
		    task();
		    // 翌日も同じ時刻に再スケジュール
		    scheduleAt(timeStr, task);
		  }, delay);
		}

		// 実行：getData関数を指定時刻に実行
		notiTimes.forEach(time => {
		  scheduleAt(time, () => {
		    getData();
		    console.log(`📌 getData実行 at ${time}`, new Date());
		  });
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
		 }else{
			 
		 }
	}
	 }
	let housework = document.getElementById("housework_id").value;
	request.type = "json";
	  request.open('GET', 'http://localhost:8080/E1/TestServlet?housework_id=' + housework, true);
	  request.send();
}



setInterval(scheduleTasksAt(notiTimes, getData), 10000);

</script>
</html>