<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>

<%@ page import="com.bean.*"%>
<%@ page import="javasrc.*"%>
  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("button").click(function(){
		$("p").hide();
	});
	$("p").mouseenter(function(){
	    $("p").css("background-color","yellow");
	  });
	  $("p").mouseleave(function(){
	    $("p").css("background-color","#E9E9E4");
	  });
});

var my_json='{FBI:[{name:"rose",age:"25"},{name:"jack",age:"23"}],NBA:[{name:"tom",sex:"man"},{name:"jack",sex:"women"}]}';
var my_object=eval('('+my_json+')');
document.writeln(my_object.FBI[1].name + my_object.FBI[1].age);
document.writeln("xx");
document.writeln(my_json.toJSONString());
document.writeln(my_object.toJSONString());
document.writeln("xx" + my_json.toJSONString());
document.writeln("xx" + my_object.toJSONString());
</script>
<body>
hello world!新年快乐!
<%
	out.println("Hello World！");
%>
----------------------------------
<h2>${message}</h2>
----------------------------------
<h2>This is a heading</h2>
<p>This is a paragraph.</p>
<p>This is another paragraph.</p>
<button type="button">Click me</button>
</body>
</html>