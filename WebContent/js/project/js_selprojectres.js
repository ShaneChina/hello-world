$(function() {

	// 使用getItem将sessionStorage中名为selprojectdatatemp的json字符串取出
	// 使用JSON.parse将json字符串转成json对象
	var projectresdata = JSON.parse(sessionStorage.getItem("selprojectdatatemp"));
	// sessionStorage.removeItem("selprojectdatatemp");
	// sessionStorage.removeItem("selprojectdatatemp"); // 删除名称为“selprojectdatatemp”的信息。
	// sessionStorage.clear();​ // 清空sessionStorage中所有信息

	// 页面展示信息
	$("#msg").html(projectresdata.status + projectresdata.message);

	var projectres = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
	projectres += ("<tr><td>项目号</td><td>" + projectresdata.project.id + "</td></tr>");
	projectres += ("<tr><td>项目名</td><td>" + projectresdata.project.name + "</td></tr>");
	projectres += ("<tr><td>项目概述</td><td>" + projectresdata.project.overview + "</td></tr>");
	projectres += ("<tr><td>项目描述</td><td>" + projectresdata.project.description + "</td></tr>");
	projectres += ("<tr><td>项目经理</td><td>" + projectresdata.project.owner_id + "</td></tr>");
	projectres += ("<tr><td>开始时间</td><td>" + projectresdata.project.start_time + "</td></tr>");
	projectres += ("<tr><td>结束时间</td><td>" + projectresdata.project.finish_time + "</td></tr>");
	projectres += ("<tr><td>创建人</td><td>" + projectresdata.project.create_id + "</td></tr>");
	projectres += ("<tr><td>创建时间</td><td>" + projectresdata.project.create_time + "</td></tr>")
	projectres += ("<tr><td>备注</td><td>" + projectresdata.project.remark + "</td></tr>");
	projectres += ("<tr><td>状态</td><td>" + projectresdata.project.status + "</td></tr>");
	projectres += ("</table>");
	$("#projectresdiv").html(projectres);

});
