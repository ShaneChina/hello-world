$(function() {

	// 使用getItem将sessionStorage中名为insprojectdatatemp的json字符串取出
	// 使用JSON.parse将json字符串转成json对象
	var insprojectresdata = JSON.parse(sessionStorage.getItem("insprojectdatatemp"));
	sessionStorage.removeItem("insprojectresdata");
	// sessionStorage.removeItem("insprojectresdata"); // 删除名称为“logindatatemp”的信息。
	// sessionStorage.clear();​ // 清空sessionStorage中所有信息

	var d = new Date();
	var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();

	// 页面展示信息
	$("#create_id").html(insprojectresdata.create_id);
	$("#create_time").html(str);
	$("#status").html("P");

});

/*
var myDate = new Date();
myDate.getYear();        //获取当前年份(2位)
myDate.getFullYear();    //获取完整的年份(4位,1970-????)
myDate.getMonth();       //获取当前月份(0-11,0代表1月)
myDate.getDate();        //获取当前日(1-31)
myDate.getDay();         //获取当前星期X(0-6,0代表星期天)
myDate.getTime();        //获取当前时间(从1970.1.1开始的毫秒数)
myDate.getHours();       //获取当前小时数(0-23)
myDate.getMinutes();     //获取当前分钟数(0-59)
myDate.getSeconds();     //获取当前秒数(0-59)
myDate.getMilliseconds();    //获取当前毫秒数(0-999)
myDate.toLocaleDateString();     //获取当前日期
var mytime=myDate.toLocaleTimeString();     //获取当前时间
myDate.toLocaleString( );        //获取日期与时间
*/