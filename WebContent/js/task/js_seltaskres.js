$(function() {

	// 使用getItem将sessionStorage中名为seltaskdatatemp的json字符串取出
	// 使用JSON.parse将json字符串转成json对象
	var taskresdata = JSON.parse(sessionStorage.getItem("seltaskdatatemp"));
	// sessionStorage.removeItem("seltaskdatatemp");
	// sessionStorage.removeItem("seltaskdatatemp"); // 删除名称为“seltaskdatatemp”的信息。
	// sessionStorage.clear();​ // 清空sessionStorage中所有信息

	// 页面展示信息
	$("#msg").html(taskresdata.status + taskresdata.message);

	var taskres = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
	taskres += ("<tr><td>任务号</td><td>" + taskresdata.task.id + "</td></tr>");
	taskres += ("<tr><td>任务名</td><td>" + taskresdata.task.name + "</td></tr>");
	taskres += ("<tr><td>任务概述</td><td>" + taskresdata.task.overview + "</td></tr>");
	taskres += ("<tr><td>任务描述</td><td>" + taskresdata.task.description + "</td></tr>");
	taskres += ("<tr><td>用户号</td><td>" + taskresdata.task.owner_id + "</td></tr>");
	taskres += ("<tr><td>开始时间</td><td>" + taskresdata.task.start_time + "</td></tr>");
	taskres += ("<tr><td>结束时间</td><td>" + taskresdata.task.finish_time + "</td></tr>");
	taskres += ("<tr><td>完成比例</td><td>" + taskresdata.task.progress + "</td></tr>");
	taskres += ("<tr><td>备注</td><td>" + taskresdata.task.remark + "</td></tr>");
	taskres += ("<tr><td>状态</td><td>" + taskresdata.task.status + "</td></tr>");
	taskres += ("<tr><td>项目号</td><td>" + taskresdata.task.project_id + "</td></tr>");
	taskres += ("<tr><td>父任务号</td><td>" + taskresdata.task.parent_task_id + "</td></tr>");
	taskres += ("</table>");
	$("#taskresdiv").html(taskres);

});
