$(function() {

	// 使用getItem将sessionStorage中名为logindatatemp的json字符串取出
	// 使用JSON.parse将json字符串转成json对象
	var logindata = JSON.parse(sessionStorage.getItem("logindatatemp"));
	// sessionStorage.removeItem("logindatatemp");
	// sessionStorage.removeItem("logindatatemp"); // 删除名称为“logindatatemp”的信息。
	// sessionStorage.clear();​ // 清空sessionStorage中所有信息

	var userdata = JSON.parse(sessionStorage.getItem("userdatatemp"));
	var projectdata = JSON.parse(sessionStorage.getItem("projectdatatemp"));

	if (logindata.status == "0001"){
		$("#projectssizediv").html("共0条项目记录");
	} else{
		$("#projectssizediv").html("共" + projectdata.projectssize + "条项目记录");

		var projectslist = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
		projectslist += ("<tr><th>序号</th><th>项目号</th><th>项目名称</th><th>项目概述</th><th>项目经理</th><th>状态</th><th>任务数</th><th>操作</th></tr>");
		$.each(projectdata.projects, function(i, value) {
			projectslist += ("<tr>");
			projectslist += ("<td align=center>" + (i+1) + "</td>");
			projectslist += ("<td align=center>" + value.id + "</td>");
			projectslist += ("<td align=center>" + value.name + "</td>");
			projectslist += ("<td align=center>" + value.overview + "</td>");
			projectslist += ("<td align=center>" + value.owner_id + "</td>");
			projectslist += ("<td align=center>" + value.status + "</td>");
			projectslist += ("<td align=center>" + value.tasksize + "</td>");
			projectslist += ("<td align=center><a href='javascript:void(0)' onclick='sel(" + value.id + ")'>查看</a>");
			projectslist += ("&nbsp&nbsp<a href='javascript:void(0)' onclick='upd(" + value.id + ")'>更新</a>");
			projectslist += ("&nbsp&nbsp<a href='javascript:void(0)' onclick='del(" + value.id + ")'>删除</a>");
			projectslist += ("&nbsp&nbsp<a href='javascript:void(0)' onclick='showtasks(" + value.id + ")'>显示任务</a></td>");
			projectslist += ("</tr>");
		});
		projectslist += ("</table>");
		$("#projectsdiv").html(projectslist);
	}

	$("#testinsprojectdiv").html("<a href='javascript:void(0)' onclick='ins(" + logindata.id + ")'>新增项目</a>");

	// 页面展示信息(debug)
	$("#msg").html(logindata.status + logindata.message + projectdata.status + projectdata.message);
	$("#useriddiv").html("useriddiv:" + logindata.id);
	$("#usernamediv").html("usernamediv:" + logindata.name);
	$("#userssizediv").html("userssizediv:" + userdata.userssize);
	var userslist = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
	userslist += ("<tr><th>用户号</th><th>用户名</th></tr>");
	$.each(userdata.users, function(i, value) {
		userslist += ("<tr>");
		userslist += ("<td>" + value.id + "</td>");
		userslist += ("<td>" + value.name + "</td>");
		userslist += ("</tr>");
	});
	userslist += ("</table>");
	$("#usersdiv").html(userslist);

});
