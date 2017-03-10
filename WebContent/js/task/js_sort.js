function sorttasksone(id) {
	var parms = {
		"project_id" : id
	};
	$.ajax({
		type : "post",
		url : "/HelloWeb/getTaskAllInfoByProjectId",
		// 绝对路径  url : "/HelloWeb/loginT"
		// 相对路径  url : "loginT"
		// 页面输入采用json时的写法:
		/*
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify(parms),
		*/
		contentType : "application/x-www-form-urlencoded",
		data : parms,
		dataType : "json",
		success : sorttasksonesuccess,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
		}
	});
};

function sorttasksonesuccess(taskdata) {
	console.log("taskdata.status:"+taskdata.status+"\ntaskdata.message:"+taskdata.message);
	if (taskdata.status == "0000" || taskdata.status == "0001") {
		// 使用JSON.stringify将json对象转成json字符串
		// 使用setItem将json字符串暂存入sessionStorage，名称为taskdatatemp
		// 接收页使用getItem取出
		sessionStorage.setItem("taskdatatemp", JSON.stringify(taskdata));
		window.location.href = "/HelloWeb/jsp/task/testalltasks.jsp";
	} /*else if (taskdata.status == "0001") {
		// 对应页面的“<div id="msg"></div>”
		alert(taskdata.status+":"+taskdata.message);
		//$("#msg").html(taskdata.status+":"+taskdata.message);
	}*/ else {
		$("#msg").html("未知错误,请联系管理员");
	}
}

function sorttaskstwo(id) {
	var parms = {
		"project_id" : id
	};
	$.ajax({
		type : "post",
		url : "/HelloWeb/getTaskAllInfoByProjectIdSort",
		// 绝对路径  url : "/HelloWeb/loginT"
		// 相对路径  url : "loginT"
		// 页面输入采用json时的写法:
		/*
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify(parms),
		*/
		contentType : "application/x-www-form-urlencoded",
		data : parms,
		dataType : "json",
		success : sorttaskstwosuccess,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
		}
	});
};

function sorttaskstwosuccess(taskdata) {
	console.log("taskdata.status:"+taskdata.status+"\ntaskdata.message:"+taskdata.message);
	if (taskdata.status == "0000" || taskdata.status == "0001") {
		// 使用JSON.stringify将json对象转成json字符串
		// 使用setItem将json字符串暂存入sessionStorage，名称为taskdatatemp
		// 接收页使用getItem取出
		sessionStorage.setItem("taskdatatemp", JSON.stringify(taskdata));
		window.location.href = "/HelloWeb/jsp/task/testalltasks.jsp";
	} /*else if (taskdata.status == "0001") {
		// 对应页面的“<div id="msg"></div>”
		alert(taskdata.status+":"+taskdata.message);
		//$("#msg").html(taskdata.status+":"+taskdata.message);
	}*/ else {
		$("#msg").html("未知错误,请联系管理员");
}
}

