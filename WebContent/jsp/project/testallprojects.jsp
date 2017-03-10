<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/HelloWeb/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/HelloWeb/js/json2.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_allprojects.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_selproject.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_selinsproject.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_selupdproject.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_delproject.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_freshP.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_PToAllT.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("p").mouseenter(function() {
			$("p").css("background-color", "yellow");
		});
		$("p").mouseleave(function() {
			$("p").css("background-color", "#E9E9E4");
		});
	});
</script>
</head>
<body>
	<h2>项目列表</h2>
	<div id="projectssizediv"></div>
	<br>
	<div id="projectsdiv"></div>
	<br>
	<div id="testinsprojectdiv"></div>
	<!-- <a href='testinsproject.jsp'>新增项目</a> -->

	<br>
	<p>---以下为debug数据，请无视---</p>
	<br>
	<div id="msg"></div>
	<div id="useriddiv"></div>
	<div id="usernamediv"></div>
	<div id="userssizediv"></div>
	<div id="usersdiv"></div>
</body>
</html>
