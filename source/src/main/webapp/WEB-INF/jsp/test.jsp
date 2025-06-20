<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<input type="text" id="housework_id">
<button id="getbox" type="button"></button>

<div id="result"></div>
</body>
<script>
const getData = () =>{
	let request = new XMLHttpRequest();
	 request.onreadystatechange = function(e){
	 if (request.onreadyState == 4){
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
getbox.addEventListener('click', getData, false)

</script>
</html>