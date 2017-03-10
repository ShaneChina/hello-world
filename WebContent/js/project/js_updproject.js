$(function() {
	$("#updbtnid").click(function() {
		var parms = {
			"project_id" : $("#id").text(),
			"project_name" : $("#name").val().trim(),
			"project_overview" : $("#overview").val().trim(),
			"project_description" : $("#description").val().trim(),
			"project_owner_id" : $("#owner_id").val().trim(),
			"project_start_time" : $("#start_time").val().trim(),
			"project_finish_time" : $("#finish_time").val().trim(),
			"project_create_id" : $("#create_id").text(),
			"project_create_time" : $("#create_time").text(),
			"project_remark" : $("#remark").val().trim(),
			"project_status" : $("#status").val().trim(),
		};
		$.ajax({
			type : "post",
			url : "/HelloWeb/updateProjectById",
			// 页面输入采用json时的写法:
			/*
			 * contentType : "application/json;charset=UTF-8", data :
			 * JSON.stringify(parms),
			 */
			contentType : "application/x-www-form-urlencoded",
			data : parms,
			dataType : "json",
			success : updprojectsuccess,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				//alert(XMLHttpRequest.readyState);
				//alert(textStatus);
			}
		});
	});
});

function updprojectsuccess(updprojectdata) {
	console.log("updprojectdata.status:"+updprojectdata.status+"\nupdprojectdata.message:"+updprojectdata.message);
	if (updprojectdata.status == "0000") {
		alert(updprojectdata.status+":"+updprojectdata.message);
	} else if (updprojectdata.status == "0001" || updprojectdata.status == "0002" || updprojectdata.status == "0011") {
		alert(updprojectdata.status+":"+updprojectdata.message);
	} else {
		alert("未知错误,请联系管理员");
	}
}