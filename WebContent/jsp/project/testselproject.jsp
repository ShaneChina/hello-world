<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/HelloWeb/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/HelloWeb/js/json2.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_selprojectres.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_backP.js"></script>
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
	<h2>项目明细</h2>
	<br>
	<div id="projectresdiv"></div>
	<input type="button" value="返回" id="selbackbtnid">
	<br>
	<p>---以下为debug数据，请无视---</p>
	<br>
	<div id="msg"></div>
</body>
</html>
