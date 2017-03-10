function freshT(id){
		var parms = {
			"project_id" : id
		};
	$.ajax({
		type : "post",
		url : "/HelloWeb/getTaskAllInfoByProjectId",
		// 页面输入采用json时的写法:
		/*
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify(parms),
		*/
		contentType : "application/x-www-form-urlencoded",
		data : parms,
		dataType : "json",
		success : getTaskAllInfoByProjectIdsuccess,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
		}
	});
}

function getTaskAllInfoByProjectIdsuccess(smtaskdata) {
	console.log("smtaskdata.status:"+smtaskdata.status+"\nsmtaskdata.message:"+smtaskdata.message);
	if (smtaskdata.status == "0000" || smtaskdata.status == "0001") {
		// 使用JSON.stringify将json对象转成json字符串
		// 使用setItem将json字符串暂存入sessionStorage，名称为taskdatatemp
		// 接收页使用getItem取出
		sessionStorage.setItem("taskdatatemp", JSON.stringify(smtaskdata));
		window.location.href = "testalltasks.jsp";
	} else {
		$("#msg").html("未知错误,请联系管理员");
	}
}