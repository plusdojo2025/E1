<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/gacha.css">
<title>Insert title here</title>
</head>
<body>

<div class="gacha">
  <div class="window">
    <div class="capsule-container">
      <div class="capsule"></div>
      <div class="capsule"></div>
      <div class="capsule"></div>
      <div class="capsule"></div>
      <div class="capsule"></div>
    </div>
  </div>
  
<div class="handle">
  <div class="handle-plate"></div>
  <div class="handle-knob">
    <div class="knob-stick"></div>
    <div class="knob-tip"></div>
  </div>
  <div class="handle-center"></div>
</div>
  <div class="slot"></div>
  <div class="capsule-drop"></div>
</div>

<script>
const handle = document.querySelector('.handle');
const knob = document.querySelector('.handle-knob');

let rotated = false;

handle.addEventListener('click', () => {
  if (!rotated) {
    knob.style.transition = 'transform 0.5s ease';
    knob.style.transform = 'rotate(180deg)';
    rotated = true;
  } else {
    knob.style.transition = 'transform 0.5s ease';
    knob.style.transform = 'rotate(0deg)';
    rotated = false;
  }
});
</script>


</body>
</html>