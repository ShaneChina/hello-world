<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/HelloWeb/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/HelloWeb/js/json2.js"></script>
<script type="text/javascript" src="/HelloWeb/js/task/js_alltasks.js"></script>
<script type="text/javascript" src="/HelloWeb/js/task/js_seltask.js"></script>
<script type="text/javascript" src="/HelloWeb/js/task/js_selupdtask.js"></script>
<script type="text/javascript" src="/HelloWeb/js/task/js_deltask.js"></script>
<script type="text/javascript" src="/HelloWeb/js/task/js_freshT.js"></script>
<script type="text/javascript" src="/HelloWeb/js/task/js_sort.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_freshP.js"></script>
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
	<!-- <a href='/HelloWeb/jsp/project/testallprojects.jsp'>返回项目列表</a> -->
	<a href='javascript:void(0)' onclick='freshP()'>返回项目列表</a>
	<h2>任务列表</h2>
	<div id="sortdiv"></div>
	<div id="taskssizediv"></div>
	<br>
	<div id="tasksdiv"></div>
	<br>
	<a href='testinstask.jsp'>新增任务</a>

	<br>
	<p>---以下为debug数据，请无视---</p>
	<br>
	<div id="msg"></div>
	<div id="projectiddiv"></div>
	<div id="useriddiv"></div>
	<div id="usernamediv"></div>
	<div id="userssizediv"></div>
	<div id="usersdiv"></div>
</body>
</html>
