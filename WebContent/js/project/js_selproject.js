function sel(id) {
	var parms = {
		"project_id" : id
	};
	$.ajax({
		type : "post",
		url : "/HelloWeb/getProjectById",
		// 页面输入采用json时的写法:
		/*
		 * contentType : "application/json;charset=UTF-8", data :
		 * JSON.stringify(parms),
		 */
		contentType : "application/x-www-form-urlencoded",
		data : parms,
		dataType : "json",
		success : selprojectsuccess,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
			//alert(XMLHttpRequest.readyState);
			//alert(textStatus);
		}
	});
}

function selprojectsuccess(selprojectdata) {
	console.log("selprojectdata.status:" + selprojectdata.status + "\nselprojectdata.message:" + selprojectdata.message);
	if (selprojectdata.status == "0000") {
		// 使用JSON.stringify将json对象转成json字符串
		// 使用setItem将json字符串暂存入sessionStorage，名称为selprojectdatatemp
		// 接收页使用getItem取出
		sessionStorage.setItem("selprojectdatatemp", JSON.stringify(selprojectdata));
		window.location.href = "testselproject.jsp";
	} else if (selprojectdata.status == "0001") {
		alert(selprojectdata.message);
	} else {
		alert("未知错误,请联系管理员");
	}
}
