<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!--  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/analysis.css">-->
<title>分析</title>
</head>
<body>
<div id="share_goal">
<h2>分担目標</h2>
<c:forEach var="e" items="${userList}" varStatus="status">
	<form method="POST" action="${pageContext.request.contextPath}/AnalysisServlet" id="form${status.index}">
		<label>${e.user_id}の分担割合<br>
			<input type="text" name="goal" value="${e.share_goal}">
			<input type="hidden" name="user_id" value="${e.user_id}">
			<input type="submit" name="submit" value="設定" id="goal_submit">
		</label>
	</form>
</c:forEach>
</div>
<nav class="chart_nav">
	<ul>
		<li id="daily_nav"><a href="#daily_chart">前日の負担割合</a></li>
		<li id="monthly_nav"><a href="#monthly_chart">月ごとの負担割合の推移</a></li>
	</ul>
</nav>
<!-- <div id="daily_chart">
	<canvas id="yesterdayChartCanvas" width="400" height="400"></canvas>
</div> -->
<div id="daily_chart">
<c:choose>
<c:when test="${empty yesterdayList}">
<p>前日の実績データがありません。</p>
</c:when>
<c:otherwise>
<canvas id="daily_chart_Canvas" width="400" height="400"></canvas>
<script>
const yesterdayList = [
<c:forEach var="a" items="${yesterdayList}" varStatus="status">
{
user_id: "${a.user_id}",
daily_score: ${a.achieve_history}
}<c:if test="${!status.last}">,</c:if>
</c:forEach>
];

const labels = yesterdayList.map(item => item.user_id);
const data = yesterdayList.map(item => item.daily_score);

new Chart(document.getElementById('daily_chart_Canvas'), {
type: 'pie',
data: {
labels: labels,
datasets: [{
label: '前日の負担割合',
data: data,
backgroundColor: [
'rgba(255, 99, 132, 0.6)',
'rgba(54, 162, 235, 0.6)',
'rgba(255, 206, 86, 0.6)',
'rgba(75, 192, 192, 0.6)',
'rgba(153, 102, 255, 0.6)'
]
}]
},
options: {
responsive: true,
plugins: {
text: '前日の負担割合'
}
}
}
});
</script>
</c:otherwise>
</c:choose>
</div>

<div id="monthly_chart">
</div>
<!--<script src="${pageContext.request.contextPath}/analysis.js"></script>-->
<!-- <script>
	const yesterdayList = [
		<c:forEach var="a" items="${yesterdayList}" varStatus="status">
			{
				user_id: "${a.user_id}",
				daily_score: ${a.achieve_history}
			}<c:if test="${!status.last}">,</c:if>
		</c:forEach>
	];

	const labels = yesterdayList.map(item => item.user_id);
	const data = yesterdayList.map(item => item.daily_score);

	new Chart(document.getElementById('yesterdayChartCanvas'), {
		type: 'pie',
		data: {
			labels: labels,
			datasets: [{
				label: '前日の負担割合',
				data: data,
				backgroundColor: [
					'rgba(255, 99, 132, 0.6)',
					'rgba(54, 162, 235, 0.6)',
					'rgba(255, 206, 86, 0.6)',
					'rgba(75, 192, 192, 0.6)',
					'rgba(153, 102, 255, 0.6)'
				]
			}]
		},
		options: {
			responsive: true,
			plugins: {
				title: {
					display: true,
					text: '前日の負担割合'
				}
			}
		}
	});
</script> -->
</body>
</html>