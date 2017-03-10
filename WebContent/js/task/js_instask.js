$(function() {
	$("#insbtnid").click(function() {
		var projectid = sessionStorage.getItem("projectidtemp");
		var parms = {
			"task_id" : 0,
			"task_name" : $("#name").val().trim(),
			"task_overview" : $("#overview").val().trim(),
			"task_description" : $("#description").val().trim(),
			"task_owner_id" : $("#owner_id").val().trim(),
			"task_start_time" : $("#start_time").val().trim(),
			"task_finish_time" : $("#finish_time").val().trim(),
			"task_progress" : $("#progress").val().trim(),
			"task_remark" : $("#remark").val().trim(),
			"task_status" : $("#status").text(),
			"task_project_id" : $("#project_id").text(),
			"task_parent_task_id" : $("#parent_task_id").val().trim()
		};
		$.ajax({
			type : "post",
			url : "/HelloWeb/createTaskByID",
			// 页面输入采用json时的写法:
			/*
			 * contentType : "application/json;charset=UTF-8", data :
			 * JSON.stringify(parms),
			 */
			contentType : "application/x-www-form-urlencoded",
			data : parms,
			dataType : "json",
			success : instasksuccess,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			}
		});
	});
});

function instasksuccess(instaskdata) {
	console.log("instaskdata.status:"+instaskdata.status+"\ninstaskdata.message:"+instaskdata.message);
	if (instaskdata.status == "0000") {
		$("#id").html(instaskdata.task.id);
		$("#status").html(instaskdata.task.status);
		$("#project_id").html(instaskdata.task.project_id);
		alert(instaskdata.status+":"+instaskdata.message);
	} else if (instaskdata.status == "0001" || instaskdata.status == "0002" || instaskdata.status == "0003" || instaskdata.status == "0004") {
		alert(instaskdata.status+":"+instaskdata.message);
	} else {
		alert("未知错误,请联系管理员");
	}
}
