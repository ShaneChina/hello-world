function ins(create_id){
	var insprojectdata = {
			"create_id" : create_id
		};

	// 使用setItem将json字符串暂存入sessionStorage，名称为insprojectdatatemp
	// 接收页使用getItem取出
	sessionStorage.setItem("insprojectdatatemp", JSON.stringify(insprojectdata));
	window.location.href = "testinsproject.jsp";
}
