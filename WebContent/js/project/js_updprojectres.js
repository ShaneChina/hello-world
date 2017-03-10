$(function() {

	// 使用getItem将sessionStorage中名为updprojectdatatemp的json字符串取出
	// 使用JSON.parse将json字符串转成json对象
	var updprojectresdata = JSON.parse(sessionStorage.getItem("updprojectdatatemp"));
	sessionStorage.removeItem("updprojectresdata");
	// sessionStorage.removeItem("updprojectresdata"); // 删除名称为“logindatatemp”的信息。
	// sessionStorage.clear();​ // 清空sessionStorage中所有信息

	// 页面展示信息
	$("#msg").html(updprojectresdata.status + updprojectresdata.message);
	$("#id").html(updprojectresdata.project.id);
	$("#name").val(updprojectresdata.project.name);
	$("#overview").val(updprojectresdata.project.overview);
	$("#description").val(updprojectresdata.project.description);
	$("#owner_id").val(updprojectresdata.project.owner_id);
	$("#start_time").val(updprojectresdata.project.start_time);
	$("#finish_time").val(updprojectresdata.project.finish_time);
	$("#create_id").html(updprojectresdata.project.create_id);
	$("#create_time").html(updprojectresdata.project.create_time);
	$("#remark").val(updprojectresdata.project.remark);
	$("#status").val(updprojectresdata.project.status);

});
