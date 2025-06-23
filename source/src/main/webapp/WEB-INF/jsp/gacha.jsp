<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/gacha.css">
<title>分担ガチャ</title>
</head>

<body>
<form method="POST" action="${pageContext.request.contextPath}/GachaServlet" id="gachaForm">
<input type="hidden" name="click" value="on">
</form>
<div id="gacha">

<section class="wrapper">
<div class="toy">
<svg x="0px" y="0px" viewBox="0 0 420 600">
<g id="capsuletoy">
  <path id="body" class="st0" d="M410,600H10c-5.5,0-10-4.5-10-10V50C0,22.4,22.4,0,50,0h320c27.6,0,50,22.4,50,50v540
    C420,595.5,415.5,600,410,600z"/>
  <path id="box" class="st1" d="M370,440H50c-27.6,0-50-22.4-50-50V70c0-27.6,22.4-50,50-50h320c27.6,0,50,22.4,50,50v320
    C420,417.6,397.6,440,370,440z"/>
  <path id="shadow" class="st2" d="M370,420H50c-27.6,0-50-22.4-50-50V50C0,22.4,22.4,0,50,0h320c27.6,0,50,22.4,50,50v320
    C420,397.6,397.6,420,370,420z"/>
  <path id="exit" class="st3" d="M115,460c-35.9,0-65,29.1-65,65v75h130v-75C180,489.1,150.9,460,115,460z"/>
  <g id="coin">
    <rect x="360" y="466" class="st4" width="38" height="76"/>
    <rect x="374" y="476" class="st3" width="10" height="56"/>
  </g>
  <polygon class="st5" points="92.8,530 50,600 114.8,600 115,600 179.8,600 137,530  "/>
  <circle id="holder_x5F_back" class="st3" cx="290" cy="518" r="56"/>
  <circle id="holder_x5F_front" class="st0" cx="290" cy="518" r="50"/>
</g>
<g id="holder">
  <path id="handle" class="st6" d="M275.2,564.7c4.7,1.5,9.8,2.3,15,2.3s10.3-0.8,15-2.3v-95.4c-4.7-1.5-9.8-2.3-15-2.3
    s-10.3,0.8-15,2.3V564.7z"/>
</g>
<g id="c9">
  <g>
    <path class="st6" d="M305,115.7c-33.1,0-60,29.2-60,65.3c0,8.3,26.9,15,60,15s60-6.7,60-15C365,144.9,338.1,115.7,305,115.7z"/>
    <ellipse class="st5" cx="305" cy="181" rx="60" ry="15"/>
    <circle class="st0" cx="294" cy="173" r="37"/>
    <path class="st7" d="M305,196c-33.1,0-60-6.7-60-15c0,47.7,26.9,58,60,58s60-10.3,60-58C365,189.3,338.1,196,305,196z"/>
  </g>
</g>
<g id="c8">
  <g>
    <path class="st6" d="M203.6,230.9c23.4-23.4,21.8-63.1-3.7-88.6c-3.5-3.5-25.4,12.6-48.8,36.1s-39.6,45.3-36.1,48.8
      C140.5,252.7,180.2,254.4,203.6,230.9z"/>
    <ellipse transform="matrix(0.7071 -0.7071 0.7071 0.7071 -84.5545 165.4397)" class="st5" cx="157.4" cy="184.8" rx="60" ry="9"/>
    <circle class="st0" cx="151.8" cy="196.1" r="37"/>
    <path class="st8" d="M151.1,178.4c23.4-23.4,45.3-39.6,48.8-36.1c-33.7-33.7-60-22-83.4,1.4s-35.1,49.7-1.4,83.4
      C111.5,223.7,127.6,201.9,151.1,178.4z"/>
  </g>
</g>
<g id="c7">
  <g>
    <path class="st6" d="M41.6,302.7c-25.5-25.5-27.2-65.2-3.7-88.6s63.1-21.8,88.6,3.7L41.6,302.7z"/>
    <circle class="st0" cx="84" cy="249" r="37"/>
    <path class="st9" d="M73.4,249.7c-23.4,23.4-37.7,47.2-31.8,53c33.7,33.7,60,22,83.4-1.4s35.1-49.7,1.4-83.4
      C120.6,212,96.8,226.3,73.4,249.7z"/>
  </g>
</g>
<g id="c6">
  <g>
    <path class="st6" d="M286,263c0-36.1,26.9-65.3,60-65.3s60,29.2,60,65.3H286z"/>
    <circle class="st0" cx="354" cy="255" r="37"/>
    <path class="st9" d="M346,248c-33.1,0-60,6.7-60,15c0,47.7,26.9,58,60,58s60-10.3,60-58C406,254.7,379.1,248,346,248z"/>
  </g>
</g>
<g id="c5">
  <g>
    <path class="st6" d="M248.8,175.5c-32.2-8-65.3,13.9-73.9,48.9c-2,8,22.5,21,54.6,29c32.2,8,59.9,7.9,61.8-0.1
      C300,218.3,281,183.5,248.8,175.5z"/>
    <ellipse transform="matrix(0.2404 -0.9707 0.9707 0.2404 -54.802 407.7581)" class="st5" cx="233.1" cy="238.9" rx="15" ry="60"/>
    <circle class="st0" cx="224.4" cy="228.5" r="37"/>
    <path class="st10" d="M229.5,253.5c-32.2-8-56.6-20.9-54.6-29c-11.5,46.3,12.1,62.8,44.3,70.7c32.2,8,60.7,4.4,72.2-41.9
      C289.4,261.4,261.7,261.4,229.5,253.5z"/>
  </g>
</g>
<g id="c4">
  <g>
    <path class="st6" d="M268,291.3c-19.4,26.9-11.3,65.8,17.9,86.8c4,2.9,23-16.5,42.4-43.4c19.4-26.9,31.8-51,27.7-54
      C326.7,259.7,287.3,264.5,268,291.3z"/>
    <ellipse transform="matrix(0.5842 -0.8116 0.8116 0.5842 -133.9644 397.4725)" class="st5" cx="321" cy="329.5" rx="60" ry="9"/>
    <circle class="st0" cx="324.7" cy="317.4" r="37"/>
    <path class="st8" d="M328.3,334.7c-19.4,26.9-38.3,46.3-42.4,43.4c38.7,27.8,62.8,12.1,82.1-14.8c19.4-26.9,26.7-54.7-12-82.6
      C360,283.7,347.6,307.9,328.3,334.7z"/>
  </g>
</g>
<g id="c3">
  <g>
    <path class="st6" d="M68.4,293.8c-23.4,23.4-21.8,63.1,3.7,88.6c5.9,5.9,29.6-8.4,53-31.8s37.7-47.2,31.8-53
      C131.5,272,91.8,270.3,68.4,293.8z"/>
    
      <ellipse transform="matrix(0.7071 -0.7071 0.7071 0.7071 -206.8116 180.5803)" class="st5" cx="114.6" cy="339.9" rx="60" ry="15"/>
    <circle class="st0" cx="101.1" cy="342.1" r="37"/>
    <path class="st11" d="M125.2,350.5c-23.4,23.4-47.2,37.7-53,31.8c33.7,33.7,60,22,83.4-1.4s35.1-49.7,1.4-83.4
      C162.9,303.4,148.6,327.1,125.2,350.5z"/>
  </g>
</g>
<g id="c2">
  <g>
    <path class="st6" d="M207,287.7c-33.1,0-60,29.2-60,65.3c0,5,26.9,9,60,9s60-4,60-9C267,316.9,240.1,287.7,207,287.7z"/>
    <ellipse class="st5" cx="207" cy="353" rx="60" ry="9"/>
    <circle class="st0" cx="207" cy="344" r="37"/>
    <path class="st12" d="M207,362c-33.1,0-60-4-60-9c0,47.7,26.9,58,60,58s60-10.3,60-58C267,358,240.1,362,207,362z"/>
  </g>
</g>
<g id="c1">
  <g>
    <path class="st6" d="M115,466.7c-33.1,0-60,29.2-60,65.3c0,5,26.9,9,60,9s60-4,60-9C175,495.9,148.1,466.7,115,466.7z"/>
    <ellipse class="st5" cx="115" cy="532" rx="60" ry="9"/>
    <circle class="st0" cx="115" cy="523" r="37"/>
    <path class="st12" d="M115,541c-33.1,0-60-4-60-9c0,47.7,26.9,58,60,58s60-10.3,60-58C175,537,148.1,541,115,541z"/>
  </g>
</g>
<g id="front">
  <g>
    <polygon class="st13" points="255,0 55,420 105,420 305,0    "/>
    <path class="st13" d="M50,0C22.4,0,0,22.4,0,50v320c0,14.3,6,27.1,15.6,36.2L209,0H50z"/>
  </g>
</g>
</svg>
</div>
</section>
</div>
<p id="none"></p>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js">
</script>
<script>
$('#gacha').on('click', function(e) {	
	const a = "${today_housework}";
	if (a === null){
	e.preventDefault();
	  
	  $('.toy').toggleClass('act');
	  const $form = $('#gachaForm');
	  const duration = parseFloat($('.toy').css('transition-duration')) * 1000;
	  const wait = duration + 1500;
	  let called = false;

	  $('.toy').one('transitionend', function(e) {
	    if (!called) {
	      called = true;
	      $form.submit();
	    }
	  });

	  setTimeout(() => {
	    if (!called) {
	      called = true;
	      $form.submit();
	    }
	  }, wait);
	}else{
		document.getElementById("none").textContent　= ("今日の家事が登録されていません");
	}
	});

</script>
</html>