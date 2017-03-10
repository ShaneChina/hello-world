$(function() {

	// 使用getItem将sessionStorage中名为updtaskdatatemp的json字符串取出
	// 使用JSON.parse将json字符串转成json对象
	var updtaskresdata = JSON.parse(sessionStorage.getItem("updtaskdatatemp"));
	sessionStorage.removeItem("updtaskresdata");
	// sessionStorage.removeItem("updtaskdatatemp"); // 删除名称为“updtaskdatatemp”的信息。
	// sessionStorage.clear();​ // 清空sessionStorage中所有信息

	// 页面展示信息
	$("#msg").html(updtaskresdata.status + updtaskresdata.message);
	$("#id").html(updtaskresdata.task.id);
	$("#name").val(updtaskresdata.task.name);
	$("#overview").val(updtaskresdata.task.overview);
	$("#description").val(updtaskresdata.task.description);
	$("#owner_id").val(updtaskresdata.task.owner_id);
	$("#start_time").val(updtaskresdata.task.start_time);
	$("#finish_time").val(updtaskresdata.task.finish_time);
	$("#progress").val(updtaskresdata.task.progress);
	$("#remark").val(updtaskresdata.task.remark);
	$("#status").val(updtaskresdata.task.status);
	$("#project_id").html(updtaskresdata.task.project_id);
	$("#parent_task_id").val(updtaskresdata.task.parent_task_id);

/*	var updtaskres = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
 	updtaskres += ("<tr><td>任务号</td><td><input type='text' name='id' id='id' value=" + updtaskresdata.task.id + "></td></tr>");
 	updtaskres += ("<tr><td>任务名</td><td><input type='text' name='name' id='name' value=" + updtaskresdata.task.name + "></td></tr>");
 	updtaskres += ("<tr><td>任务描述</td><td><input type='text' name='description' id='description' value=" + updtaskresdata.task.description + "></td></tr>");
 	updtaskres += ("<tr><td>用户号</td><td><input type='text' name='owner_id' id='owner_id' value=" + updtaskresdata.task.owner_id + "></td></tr>");
 	updtaskres += ("<tr><td>开始时间</td><td><input type='text' name='start_time' id='start_time' value=" + updtaskresdata.task.start_time + "></td></tr>");
 	updtaskres += ("<tr><td>结束时间</td><td><input type='text' name='finish_time' id='finish_time' value=" + updtaskresdata.task.finish_time + "></td></tr>");
 	updtaskres += ("<tr><td>完成比例</td><td><input type='text' name='progress' id='progress' value=" + updtaskresdata.task.progress + "></td></tr>");
 	updtaskres += ("<tr><td>备注</td><td><input type='text' name='remark' id='remark' value=" + updtaskresdata.task.remark + "></td></tr>");
	updtaskres += ("</table>");
	$("#updtaskresdiv").html(updtaskres);*/

});
