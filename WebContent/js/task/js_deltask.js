function del(id) {
	var delconfirm = "您真的确定要删除吗？\n请确认！";
	if (confirm(delconfirm)) {
		var parms = {
			"task_id" : id
		};
		$.ajax({
			type : "post",
			url : "/HelloWeb/deleteTaskByID",
			// 页面输入采用json时的写法:
			/*
			 * contentType : "application/json;charset=UTF-8", data :
			 * JSON.stringify(parms),
			 */
			contentType : "application/x-www-form-urlencoded",
			data : parms,
			dataType : "json",
			success : deltasksuccess,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			}
		});
	} else {
		return false;
	}
}

function deltasksuccess(deltaskdata) {
	console.log("deltaskdata.status:"+deltaskdata.status+"\ndeltaskdata.message"+deltaskdata.message);
	if (deltaskdata.status == "0000") {
		var projectid = sessionStorage.getItem("projectidtemp");
		freshT(projectid);
		alert(deltaskdata.status+":"+deltaskdata.message);
	} else if (deltaskdata.status == "0001" || deltaskdata.status == "0011") {
		alert(deltaskdata.status+":"+deltaskdata.message);
	} else {
		alert("未知错误,请联系管理员");
	}
}
