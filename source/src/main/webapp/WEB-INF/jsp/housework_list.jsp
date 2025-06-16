<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>家事一覧</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/housework_list.css">

<!-- ↑cssのパスを動的に取得 -->
</head>
<body>
    <!-- ヘッダー -->
    <header>
        <!-- ロゴ挿入 -->
        <!-- navタグで通知とログアウトを入れる -->

    </header>

    <!-- メイン -->
    <main class="housework_list_wrapper">
        <!-- 家事一覧を文字で表示 -->
        <h2>家事一覧</h2>

        <!-- 家事タブを横に並べる、家事タブを押したときはカテゴリごとに表示 -->
        
        <div class="tab_container">
            <div><p>掃除</p></div>
            <div><p>洗濯</p></div>
            <div><p>料理</p></div>
            <div><p>その他</p></div>
        </div>

        <!-- 家事一覧表示 -->
            <!-- 負担度、家事名、消去ラベル表示 -->
            <div class="contents_label">
                <div><p>負担度</p></div>
                <div><p>家事名</p></div>
                <div><p>消去</p></div>
            </div>

            <!-- 負担度、家事名、消去アイコンを横表示 -->

            <!-- ↓サンプル家事 -->
            

			<div class="contents">
            <c:forEach var="e" items="${cardList}" >
                <form method="POST" id="search_result_form" action="/webapp/UpdateDeleteServlet">
				家事ID<input type="hidden" name="housework_id" value="${e.housework_id}">
                家事名<input type="text" name="housework_name" value="${e.housework_name}"><br>
                ファミリーID<input type="hidden" name="family_id" value="${e.family_id}"><br>
                カテゴリID<input type="hidden" name="category_id" value="${e.category_id}"><br>
                家事負担度<input type="text" name="housework_level" value="${e.housework_level}"><br>
                通知有無<input type="hidden" name="noti_flag" value="${e.noti_flag}"><br>
                通知時間<input type="hidden" name="noti_time" value="${e.noti_time}"><br>
                家事頻度<input type="hidden" name="frequency" value="${e.frequency}"><br>
                マニュアル<input type="hidden" name="manual" value="${e.manual}"><br>
                固定担当者<input type="hidden" name="fixed_role" value="${e.fixed_role}"><br>
                可変担当者<input type="hidden" name="variable_role" value="${e.variable_role}"><br>
                更新履歴<input type="hidden" name="log" value="${e.log}"><br>

                <!-- ごみ箱のイメージを張り付ける -->
                <input type="submit" name="submit" value="削除"><br>


                


                <!-- 負担度、家事名の範囲を押下時、家事更新画面をモーダル表示 -->
                </form>
            </c:forEach>
            </div>
            <!-- 家事が追加されるごとに行を追加 -->
        <!-- 検索アイコン表示 -->
        <!-- 検索アイコン押下時モーダル画面を表示 -->
        <div><p>検索</p></div>

    </main>

<!-- フッター -->
    <footer>
        <!-- 各コンテンツのアイコンを横に並べる -->

    </footer>
<script>
    'use strict';
/* 検索画面をモーダル表示 */
//家事カードを押下時更新画面をモーダル表示
    // 更新ボタンを押下時、更新確認モーダルを表示
// ごみ箱アイコンを押下時消去確認モーダル表示示 


</script>
</body>
</html>