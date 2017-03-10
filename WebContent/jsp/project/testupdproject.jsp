<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/HelloWeb/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/HelloWeb/js/json2.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_updprojectres.js"></script>
<script type="text/javascript" src="/HelloWeb/js/project/js_updproject.js"></script>
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
	<h2>更新项目</h2>
	<br>
	<form action="" method="post" name="formname" id="formid">
		<table>
			<tr>
				<td>项目号:</td>
				<td><div id="id"></div></td>
			</tr>
			<tr>
				<td>项目名:</td>
				<td><input type="text" name="name" id="name" value=""></td>
			</tr>
			<tr>
				<td>项目概述:</td>
				<td><input type="text" name="overview" id="overview" value=""></td>
			</tr>
			<tr>
				<td>项目描述:</td>
				<td><input type="text" name="description" id="description" value=""></td>
			</tr>
			<tr>
				<td>项目经理:</td>
				<td><input type="text" name="owner_id" id="owner_id" value=""></td>
			</tr>
			<tr>
				<td>开始时间:</td>
				<td><input type="text" name="start_time" id="start_time" value=""></td>
			</tr>
			<tr>
				<td>结束时间:</td>
				<td><input type="text" name="finish_time" id="finish_time" value=""></td>
			</tr>
			<tr>
				<td>创建人:</td>
				<td><div id="create_id"></div></td>
			</tr>
			<tr>
				<td>创建时间:</td>
				<td><div id="create_time"></div></td>
			</tr>
			<tr>
				<td>备注:</td>
				<td><input type="text" name="remark" id="remark" value=""></td>
			</tr>
			<tr>
				<td>状态:</td>
				<td><input type="text" name="status" id="status" value=""></td>
			</tr>
			<tr>
				<td><input type="button" value="更新" id="updbtnid"></td>
				<td><input type="button" value="返回" id="updbackbtnid"></td>
			</tr>
		</table>
	</form>
<!-- 	<div id="updprojectresdiv"></div> -->

	<br>
	<p>---以下为debug数据，请无视---</p>
	<br>
	<div id="msg"></div>
</body>
</html>
