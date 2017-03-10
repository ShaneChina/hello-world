function upd(id){
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
			success : updprojectsuccess,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				console.log("XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				//alert(XMLHttpRequest.readyState);
				//alert(textStatus);
			}
		});
}

function updprojectsuccess(updprojectdata) {
	console.log("updprojectdata.status:"+updprojectdata.status+"\nupdprojectdata.message:"+updprojectdata.message);
	if (updprojectdata.status == "0000") {
		// 使用JSON.stringify将json对象转成json字符串
		// 使用setItem将json字符串暂存入sessionStorage，名称为logindatatemp
		// 接收页使用getItem取出
		sessionStorage.setItem("updprojectdatatemp", JSON.stringify(updprojectdata));
		window.location.href = "testupdproject.jsp";
	} else if (updprojectdata.status == "0001") {
		alert(updprojectdata.status+":"+updprojectdata.message);
	} else {
		alert("未知错误,请联系管理员");
	}
}
