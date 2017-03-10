$(function() {
	$("#updbtnid").click(function() {
		var parms = {
			"task_id" : $("#id").text(),
			"task_name" : $("#name").val().trim(),
			"task_overview" : $("#overview").val().trim(),
			"task_description" : $("#description").val().trim(),
			"task_owner_id" : $("#owner_id").val().trim(),
			"task_start_time" : $("#start_time").val().trim(),
			"task_finish_time" : $("#finish_time").val().trim(),
			"task_progress" : $("#progress").val().trim(),
			"task_remark" : $("#remark").val().trim(),
			"task_status" : $("#status").val().trim(),
			"task_project_id" : $("#project_id").text(),
			"task_parent_task_id" : $("#parent_task_id").val().trim()
		};
		$.ajax({
			type : "post",
			url : "/HelloWeb/updateTaskByID",
			// 页面输入采用json时的写法:
			/*
			 * contentType : "application/json;charset=UTF-8", data :
			 * JSON.stringify(parms),
			 */
			contentType : "application/x-www-form-urlencoded",
			data : parms,
			dataType : "json",
			success : updtasksuccess,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			}
		});
	});
});

function updtasksuccess(updtaskdata) {
	console.log("updtaskdata.status:"+updtaskdata.status+"\nupdtaskdata.message"+updtaskdata.message);
	if (updtaskdata.status == "0000") {
		alert(updtaskdata.status+":"+updtaskdata.message);
	} else if (updtaskdata.status == "0001" || updtaskdata.status == "0002" || updtaskdata.status == "0011") {
		alert(updtaskdata.status+":"+updtaskdata.message);
	} else {
		alert("未知错误,请联系管理员");
	}
}