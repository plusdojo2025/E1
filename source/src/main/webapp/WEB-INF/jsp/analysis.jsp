<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<link rel="stylesheet" type="text/css" href="css/analysis.css">
<title>分析</title>
</head>
<body>
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
<main>
<div id="share_goal">
  <h2>分担目標</h2>
  <form method="POST" action="${pageContext.request.contextPath}/AnalysisServlet" id="form${status.index}" onsubmit="return validateGoals();">
	  <c:forEach var="e" items="${userList}" varStatus="status">
	      <label for="goal${status.index}"><c:out value="${e.user_id}" />の分担割合</label>
	      <input type="text" name="goal" class="goal_input" id="goal${status.index}" value="<c:out value='${e.share_goal}'/>">
	      <input type="hidden" name="user_id" value="<c:out value='${e.user_id}'/>">
	  </c:forEach>
	  <input type="submit" name="submit" value="設定" id="goal_submit">
	  <p id="error_message">${errorMessage}</p>
  </form>
</div>

<nav id="chart_nav">
  <ul>
    <li id="daily_nav"><a href="#daily_chart">前日の負担割合</a></li>
    <li id="monthly_nav"><a href="#monthly_chart">月ごとの負担割合の推移</a></li>
  </ul>
</nav>

<div id="daily_chart">
  <c:choose>
    <c:when test="${empty yesterdayList}">
      <p id="no_yesterday">前日の実績データがありません。</p>
    </c:when>
    <c:otherwise>
      <canvas id="daily_chart_Canvas"></canvas>
      
    </c:otherwise>
  </c:choose>
</div>

<div id="monthly_chart">
  <c:choose>
    <c:when test="${empty yearList}">
      <p>過去12か月分の実績データがありません。</p>
    </c:when>
    <c:otherwise>
      <canvas id="monthly_chart_Canvas"></canvas>
 
    </c:otherwise>
  </c:choose>
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
'use strict';
  const yesterdayList = [
    <c:forEach var="a" items="${yesterdayList}" varStatus="status">
    {
      user_id: "<c:out value='${a.user_id}'/>",
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
        title: {
          display: true,
          text: '前日の負担割合'
        }
      }
    }
  });
  
    const yearList = [
      <c:forEach var="a" items="${yearList}" varStatus="status">
      {
        user_id: "<c:out value='${a.user_id}'/>",
        month: "<c:out value='${a.date}'/>",
        monthly_score: ${a.achieve_history}
      }<c:if test="${!status.last}">,</c:if>
      </c:forEach>
    ];

    const months = [...new Set(yearList.map(item => item.month))].sort();
    const users = [...new Set(yearList.map(item => item.user_id))];

    const totalByMonth = {};
    months.forEach(month => {
      totalByMonth[month] = yearList
        .filter(item => item.month === month)
        .reduce((sum, item) => sum + item.monthly_score, 0);
    });

    const colorPalette = [
      'rgba(255, 99, 132, 0.6)',
      'rgba(54, 162, 235, 0.6)',
      'rgba(255, 206, 86, 0.6)',
      'rgba(75, 192, 192, 0.6)',
      'rgba(153, 102, 255, 0.6)',
      'rgba(255, 159, 64, 0.6)',
      'rgba(100, 200, 255, 0.6)'
    ];

    function getColor(index) {
      return colorPalette[index % colorPalette.length];
    }

    const datasets = users.map((userId, idx) => {
      const data = months.map(month => {
        const record = yearList.find(item => item.user_id === userId && item.month === month);
        const total = totalByMonth[month];
        return record && total > 0 ? (record.monthly_score / total * 100).toFixed(2) : 0;
      });

      return {
        label: userId,
        data: data,
        borderColor: getColor(idx),
        backgroundColor: getColor(idx),
        tension: 0.3
      };
    });

    new Chart(document.getElementById('monthly_chart_Canvas'), {
      type: 'line',
      data: {
        labels: months,
        datasets: datasets
      },
      options: {
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: '月ごとの家事負担割合の推移（%）'
          }
        },
        scales: {
          y: {
            title: {
              display: true,
              text: '負担割合 (%)'
            },
            beginAtZero: true,
            max: 100
          },
          x: {
            title: {
              display: true,
              text: '月'
            }
          }
        }
      }
    });

function validateGoals() {
	  const inputs = document.querySelectorAll('.goal_input');
	  const errorEl = document.getElementById('error_message');
	  let total = 0;
	  let hasInvalid = false;

	  errorEl.textContent = ''; // 初期化

	  // 半角数字＋小数点のみの正規表現（マイナス不要なら外す）
	  const halfWidthNumberRegex = /^[0-9]*\.?[0-9]+$/;

	  inputs.forEach(input => {
	    const val = input.value.trim();

	    // 半角数字かどうかチェック
	    if (!halfWidthNumberRegex.test(val)) {
	      hasInvalid = true;
	      return;
	    }

	    const num = parseFloat(val);
	    if (isNaN(num)) {
	      hasInvalid = true;
	    } else {
	      total += num;
	    }
	  });

	  if (hasInvalid) {
	    errorEl.textContent = '半角数字で正しい値を入力してください。';
	    return false;
	  }

	  if (Math.abs(total - 1.0) > 0.0001) {
	    errorEl.textContent = '分担割合の合計が1になっていません。';
	    return false;
	  }

	  return true;
	}
</script>
</body>
</html>
