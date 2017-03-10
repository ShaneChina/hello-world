<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	$("button").click(function(){
		$("p").hide();
	});
	$("p").mouseenter(function(){
	    $("p").css("background-color","yellow");
	  });
	  $("p").mouseleave(function(){
	    $("p").css("background-color","#E9E9E4");
	  });
 $('.someClass').each(function() {
  var $this = $(this);
  var defaultVal = $this.attr('title');
  $this.focus(function() {
   if ($this.val() == defaultVal) {
    $this.val('');
   }
  });
  $this.blur(function() {
   if ($this.val().length == 0) {
    $this.val(defaultVal);
   }
  });
 });
});

	//shift:从数组中把第一个元素删除，并返回这个元素的值。
	//unshift: 在数组的开头添加一个或更多元素，并返回新的长度
	//push:在数组的中末尾添加元素，并返回新的长度
	//pop:从数组中把最后一个元素删除，并返回这个元素的值。

	//创建一个数组来模拟堆栈
	var a = new Array();
	console.log(a);
	//push: 在数组的末尾添加一个或更多元素，并返回新的长度
	console.log("入栈");
	a.push("a")
	console.log(a);//----->a
	a.push("b");
	console.log(a);//----->a,b
	a.push("c");
	console.log(a);//----->a,b,c
	a.push("d");
	console.log(a);//----->a,b,c,d

	//
	console.log("栈最后一位");
	console.log(a[a.length-1]);

	console.log("出栈，后进先出");
	console.log(a);

	//pop：从数组中把最后一个元素删除，并返回这个元素的值
	a.pop();//----->d
	console.log(a);
	a.pop();//----->c
	console.log(a);
	a.pop();//----->b
	console.log(a);
	a.pop();//----->a
	console.log(a);
</script>
<style>
input {
 display:block;
 margin-bottom:5px;
}
</style>
</head>
<body>
<p>This is another paragraph.</p>
<input class="someClass" type="text" title="Name" value="Name" />
<input class="someClass" type="text" title="Email Address"
value="Email Address" />
<input class="someClass" type="text" title="Default Value Here"
value="Insert form refill here" />
<form id="myform" action="#">
地址2：<input type="text" id="txt1" value="请输入邮箱地址"/><br /><br />
密码1：<input type="text" id="txt2" value="请输入邮箱密码"/>
<input type="password" id="pswd" /><br /><br />
<input type="button"  id="btn"value="登陆" />
</form>
<div id="msg"></div>
</body>
</html>
