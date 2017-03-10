function del(id) {
	var delconfirm = "您真的确定要删除吗？\n请确认！";
	if (confirm(delconfirm)) {
		var parms = {
			"project_id" : id
		};
		$.ajax({
			type : "post",
			url : "/HelloWeb/deleteProjectById",
			// 页面输入采用json时的写法:
			/*
			 * contentType : "application/json;charset=UTF-8", data :
			 * JSON.stringify(parms),
			 */
			contentType : "application/x-www-form-urlencoded",
			data : parms,
			dataType : "json",
			success : delprojectsuccess,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			}
		});
	} else {
		return false;
	}
}

function delprojectsuccess(delprojectdata) {
	console.log("delprojectdata.status:"+delprojectdata.status+"\ndelprojectdata.message:"+delprojectdata.message);
	if (delprojectdata.status == "0000") {
		freshP();
		alert(delprojectdata.status+":"+delprojectdata.message);
	} else if (delprojectdata.status == "0001" || delprojectdata.status == "0011") {
		alert(delprojectdata.status+":"+delprojectdata.message);
	} else {
		alert("未知错误,请联系管理员");
	}
}
