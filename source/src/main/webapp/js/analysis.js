// 前月から過去12か月分の負担割合の推移の折れ線グラフ

// 月の一覧を抽出して昇順に並べる
const monthsSet = new Set(yearList.map(item => item.month));
const months = Array.from(monthsSet).sort();
const formattedMonths = months.map(m => {
const [year, month] = m.split("-");
return `${year}年${parseInt(month)}月`;
});

// ユーザーIDの一覧を抽出
const userIdsSet = new Set(yearList.map(item => item.user_id));
const userIds = Array.from(userIdsSet);

// ユーザーごとの月別負担を構築
const datasets = userIds.map((userId, index) => {
const achievement = months.map(month => {
const record = yearList.find(item => item.user_id === userId && item.month === month);
return record ? record.monthly_score : 0;
});

const colors = [
'rgba(255, 99, 132, 0.6)',
'rgba(54, 162, 235, 0.6)',
'rgba(255, 206, 86, 0.6)',
'rgba(75, 192, 192, 0.6)',
'rgba(153, 102, 255, 0.6)',
'rgba(255, 159, 64, 0.6)'
];

return {
label: userId,
data: achievement,
borderColor: colors[index % colors.length],
: colors[index % colors.length],
fill: false,
tension: 0.3
};
});

// Chart.js グラフ描画
new Chart(document.getElementById('monthly_chart_Canvas'), {
type: 'line',
data: {
labels: formattedMonths,
datasets: datasets
},
options: {
responsive: true,
plugins: {
title: {
display: true,
text: '月ごとの負担割合の推移（過去12か月）'
},
legend: {
position: 'top'
}
},
scales: {
y: {
beginAtZero: true,
title: {
displaytext: '負担割合'
}
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
