$(function() {
	$("#insbtnid").click(function() {
		var parms = {
			"project_id" : 0,
			"project_name" : $("#name").val().trim(),
			"project_overview" : $("#overview").val().trim(),
			"project_description" : $("#description").val().trim(),
			"project_owner_id" : $("#owner_id").val().trim(),
			"project_start_time" : $("#start_time").val().trim(),
			"project_finish_time" : $("#finish_time").val().trim(),
			"project_create_id" : $("#create_id").text(),
			//"create_time" : $("#create_time").val().trim(),
			"project_create_time" : "",
			"project_remark" : $("#remark").val().trim(),
			"project_status" : "P"
		};
		$.ajax({
			type : "post",
			url : "/HelloWeb/createProject",
			// 页面输入采用json时的写法:
			/*
			 * contentType : "application/json;charset=UTF-8", data :
			 * JSON.stringify(parms),
			 */
			contentType : "application/x-www-form-urlencoded",
			data : parms,
			dataType : "json",
			success : insprojectsuccess,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("insproject:XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				console.log("insproject:XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				//alert(XMLHttpRequest.readyState);
				//alert(textStatus);
			}
		});
	});

	/*$("#insupdbackbtnid").click(function() {
		$.ajax({
			type : "post",
			url : "/HelloWeb/smproject",
			// 页面输入采用json时的写法:
			
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify(parms),
			
			contentType : "application/x-www-form-urlencoded",
			data : "",
			dataType : "json",
			success : smprojectsuccess,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("smproject:XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				console.log("smproject:XMLHttpRequest.status:" + XMLHttpRequest.status + "\nXMLHttpRequest.readyState:" + XMLHttpRequest.readyState + "\ntextStatus:" + textStatus);
				//alert(XMLHttpRequest.readyState);
				//alert(textStatus);
			}
		});
	});*/
});

function insprojectsuccess(insprojectdata) {
	console.log("insprojectdata.status:"+insprojectdata.status+"\ninsprojectdata.message:"+insprojectdata.message);
	if (insprojectdata.status == "0000") {
		$("#id").html(insprojectdata.project.id);
		$("#create_time").html(insprojectdata.project.create_time);
		$("#status").html(insprojectdata.project.status);
		alert(insprojectdata.status+":"+insprojectdata.message);
	} else if (insprojectdata.status == "0001" || insprojectdata.status == "0002") {
		alert(insprojectdata.status+":"+insprojectdata.message);
	} else {
		alert("未知错误,请联系管理员");
	}
}

/*function smprojectsuccess(smprojectdata) {
	if (smprojectdata.status == "0000" || smprojectdata.status == "0001") {
		// 使用JSON.stringify将json对象转成json字符串
		// 使用setItem将json字符串暂存入sessionStorage，名称为logindatatemp
		// 接收页使用getItem取出
		sessionStorage.setItem("logindatatemp", JSON.stringify(smprojectdata));
		window.location.href = "testallprojects.jsp";
	} else {
		$("#msg").html("未知错误,请联系管理员");
	}
}*/