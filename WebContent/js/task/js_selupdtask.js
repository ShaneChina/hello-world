function upd(id){
	var parms = {
			"task_id" : id
		};
		$.ajax({
			type : "post",
			url : "/HelloWeb/getTaskByID",
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
}

function updtasksuccess(updtaskdata) {
	console.log("updtaskdata.status:"+updtaskdata.status+"\nupdtaskdata.message:"+updtaskdata.message);
	if (updtaskdata.status == "0000") {
		// 使用JSON.stringify将json对象转成json字符串
		// 使用setItem将json字符串暂存入sessionStorage，名称为updtaskdatatemp
		// 接收页使用getItem取出
		sessionStorage.setItem("updtaskdatatemp", JSON.stringify(updtaskdata));
		window.location.href = "testupdtask.jsp";
	} else if (updtaskdata.status == "0001") {
		alert(updtaskdata.status+":"+updtaskdata.message);
	} else {
		alert("未知错误,请联系管理员");
	}
}
