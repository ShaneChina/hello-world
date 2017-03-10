function freshP(){
	$.ajax({
		type : "post",
		url : "/HelloWeb/getProjectAllInfo",
		// 页面输入采用json时的写法:
		/*
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify(parms),
		*/
		contentType : "application/x-www-form-urlencoded",
		data : "",
		dataType : "json",
		success : smprojectsuccess,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
		}
	});
}

function smprojectsuccess(smprojectdata) {
	console.log("smprojectdata.status:"+smprojectdata.status+"\nsmprojectdata.message:"+smprojectdata.message);
	if (smprojectdata.status == "0000" || smprojectdata.status == "0001") {
/*		var logindata = JSON.parse(sessionStorage.getItem("logindatatemp"));
		var userdata = JSON.parse(sessionStorage.getItem("userdatatemp"));
		sessionStorage.setItem("logindatatemp", JSON.stringify(logindata));
		sessionStorage.setItem("userdatatemp", JSON.stringify(userdata));*/
		sessionStorage.setItem("projectdatatemp", JSON.stringify(smprojectdata));
		window.location.href = "/HelloWeb/jsp/project/testallprojects.jsp";
	} else {
		$("#msg").html("未知错误,请联系管理员");
	}
}