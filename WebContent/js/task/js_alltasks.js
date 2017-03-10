$(function() {

	// 使用getItem将sessionStorage中名为taskdatatemp的json字符串取出
	// 使用JSON.parse将json字符串转成json对象
	var taskdata = JSON.parse(sessionStorage.getItem("taskdatatemp"));
	var projectid = sessionStorage.getItem("projectidtemp");
	// sessionStorage.removeItem("taskdatatemp");
	// sessionStorage.removeItem("taskdatatemp"); // 删除名称为“taskdatatemp”的信息。
	// sessionStorage.clear();​ // 清空sessionStorage中所有信息

	if (taskdata.status == "0001"){
		$("#taskssizediv").html("共0条任务记录");
	} else {
		$("#sortdiv").html("<a href='javascript:void(0)' onclick='sorttasksone(" + projectid + ")'>排序方式1</a>&nbsp&nbsp<a href='javascript:void(0)' onclick='sorttaskstwo(" + projectid + ")'>排序方式2</a>");
		$("#taskssizediv").html("共" + taskdata.taskssize + "条任务记录");
		var taskslist = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
		taskslist += ("<tr><th>序号</th><th>任务号</th><th>任务名称</th><th>任务概述</th><th>责任人</th><th>任务进度</th><th>状态</th><th>父任务号</th><th>层级</th><th>操作</th></tr>");
		$.each(taskdata.tasks, function(i, value) {
			taskslist += ("<tr>");
			taskslist += ("<td align=center>" + (i+1) + "</td>");
			taskslist += ("<td align=center>" + value.id + "</td>");
			taskslist += ("<td align=center>" + value.name + "</td>");
			taskslist += ("<td align=center>" + value.overview + "</td>");
			taskslist += ("<td align=center>" + value.owner_id + "</td>");
			taskslist += ("<td align=center>" + (value.progress * 100).toFixed(0) + "%" + "</td>");
			taskslist += ("<td align=center>" + value.status + "</td>");
			taskslist += ("<td align=center>" + value.parent_task_id + "</td>");
			taskslist += ("<td align=center>" + value.subtasksize + "</td>");
			taskslist += ("<td align=center><a href='javascript:void(0)' onclick='sel(" + value.id + ")'>查看</a>");
			taskslist += ("&nbsp&nbsp<a href='javascript:void(0)' onclick='upd(" + value.id + ")'>更新</a>");
			taskslist += ("&nbsp&nbsp<a href='javascript:void(0)' onclick='del(" + value.id + ")'>删除</a></td>");
	/*		taskslist += ("<td><input type='button' onclick='selbtn(" + value.id + ")' value='查看'></td>");
			taskslist += ("<td><input type='button' onclick='updbtn(" + value.id + ")' value='更新'></td>");
			taskslist += ("<td><input type='button' onclick='delbtn(" + value.id + ")' value='删除'></td>");*/
			taskslist += ("</tr>");
		});
		taskslist += ("</table>");
		$("#tasksdiv").html(taskslist);
	}

	// 页面展示信息
	$("#msg").html(taskdata.status + taskdata.message);
	$("#projectiddiv").html("projectiddiv:" + projectid);
/*	$("#useriddiv").html("useriddiv:" + logindata.id);
	$("#usernamediv").html("usernamediv:" + logindata.name);
	$("#userssizediv").html("userssizediv:" + logindata.userssize);*/
/*	var userslist = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
	userslist += ("<tr><th>用户号</th><th>用户名</th></tr>");
	$.each(logindata.users, function(i, value) {
		userslist += ("<tr>");
		userslist += ("<td>" + value.id + "</td>");
		userslist += ("<td>" + value.name + "</td>");
		userslist += ("</tr>");
	});
	userslist += ("</table>");
	$("#usersdiv").html(userslist);*/

});
