function sel(id) {
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
		success : seltasksuccess,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
		}
	});
}

function seltasksuccess(seltaskdata) {
	console.log("seltaskdata.status:"+seltaskdata.status+"\nseltaskdata.message:"+seltaskdata.message);
	if (seltaskdata.status == "0000") {
		// 使用JSON.stringify将json对象转成json字符串
		// 使用setItem将json字符串暂存入sessionStorage，名称为logindatatemp
		// 接收页使用getItem取出
		sessionStorage.setItem("seltaskdatatemp", JSON.stringify(seltaskdata));
		window.location.href = "testseltask.jsp";
	} else if (seltaskdata.status == "0001") {
		alert(seltaskdata.status+":"+seltaskdata.message);
	} else {
		alert("未知错误,请联系管理员");
	}
}
