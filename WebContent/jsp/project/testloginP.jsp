<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>登录页面</title>
		<script type="text/javascript" src="/HelloWeb/js/jquery-3.1.1.js"></script>
		<script type="text/javascript" src="/HelloWeb/js/json2.js"></script>
		<script charset="UTF-8" type="text/javascript" src="/HelloWeb/js/project/js_logincheckP.js"></script>
		<script charset="UTF-8" type="text/javascript" src="/HelloWeb/js/project/js_loginP.js"></script>
		<!-- script中加入“charset="UTF-8"”，避免页面中的中文产生乱码或不显示 -->
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
		<h2>欢迎登录</h2>
		<h4>请输入您的用户名和密码</h4>
		<form action="" method="post" name="formname" id="formid">
			<table>
				<tr>
					<td>用户名:</td>
					<td><input type="text" size="20" name="username" id="unid"
					class="someClass" title="请输入姓名" value="请输入姓名" style="color:#999">
					</td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type="text" size="20" name="password" id="pwtxid"
					class="someClass" title="请输入密码" value="请输入密码" style="color:#999">
					</td>
				</tr>
				<tr>
					<td><input type="button" value="登录" id="loginbtnid"></td>
					<td><input type="button" value="重置" id="resetbtnid"></td>
				</tr>
				<tr>
					<td colspan="2">
						<!-- 此div用于显示返回的错误信息 -->
						<div id="msg"></div>
					</td>
				</tr>
			</table>
		</form>
		<p>This is another paragraph.</p>
	</body>
</html>