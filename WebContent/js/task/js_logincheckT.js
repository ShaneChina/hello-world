$(function() {
	// 简写：$(function() {
	// 等同于：$(document).ready(function() {
	// 等同于：$().ready(function(){

	// 设置页面焦点初始位置
	//$("#unid").focus();

	// 设置输入初始化
	$(".someClass").each(function() {
		var $this = $(this);
		var defaultVal = $this.attr("title");
		$this.focus(function() {
			if ($this.val().trim() == defaultVal) {
				$this.val("");
			}
			$this.css("color","#000");
		});
		$this.blur(function() {
			if ($this.val().trim().length == 0) {
				$this.val(defaultVal);
				$this.css("color","#999");
			}
		});
	});

/*	// 判断用户名不能为空
	$("#unid").blur(function() {
		var str = $(this).val();
		str = $.trim(str);
		if (str == '') {
			alert("请输入用户名");
			$(this).focus();
			// 等同于：$("#username").focus();
			// 等同于：document.getElementById("username").focus();
		}
	});

	// 判断密码不能为空
	$("#pwid").blur(function() {
		var str = $(this).val();
		str = $.trim(str);
		if (str == '') {
			alert("请输入密码");
			$(this).focus();
			// 等同于：document.getElementById("password").focus();
			// 等同于：$("#password").focus();
		}
	});*/

	$("#resetbtnid").click(function() {
		/*
		 * document.getElementById()返回的是DOM对象，而$()返回的是jQuery对象
		 * 使用“$("#formid").reset();”是无法重置表单的，原因是JQuery没有reset方法
		 * 可以使用“document.getElementById("formid").reset();”重置表单
		 * 或者将jQuery对象转换成DOM对象，使用“$('#formid')[0].reset();” 1、DOM对象转jQuery对象
		 * 普通的Dom对象一般可以通过$()转换成jQuery对象。 如：$(document.getElementById("msg"))
		 * 返回的就是jQuery对象，可以使用jQuery的方法。 2、jQuery对象转DOM对象
		 * 由于jQuery对象本身是一个集合。所以如果jQuery对象要转换为Dom对象则必须取出其中的某一项，一般可通过索引取出。 如：
		 * $("#msg")[0]，$("div").eq(1)[0]，$("div").get()[1]，$("td")[5]
		 */
		$(".someClass").css("color","#999");
		//对应页面的“<div id="msg"></div>”
		$("#msg").html("");
		$("#formid")[0].reset();
		$("#unid").focus();
	});

});