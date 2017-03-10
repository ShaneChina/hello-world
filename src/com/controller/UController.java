package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.bean.ProjectSummary;
//import com.bean.Task;
import com.bean.User;

//import javasrc.ProjectService;
//import javasrc.TaskService;
import javasrc.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller // Controller
public class UController {

	@Autowired // 自动加载UserService
	// 定义UserService对象
	UserService userService;

/*	@Autowired // 自动加载ProjectService
	// 定义ProjectService对象
	ProjectService projectService;*/

/*	@Autowired // 自动加载TaskService
	// 定义TaskService对象
	TaskService taskService;*/

	@RequestMapping(value = "/login") // 路径:/login
	// 简写:@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("login()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// print前端页面值
		System.out.println("request.name:" + request.getParameter("name"));
		System.out.println("request.password:" + request.getParameter("password"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		// name = "杨扬"; // ***测试用数据***
		// password = "c"; // ***测试用数据***
		System.out.println("name:" + name);
		System.out.println("password:" + password);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 调用userService.selectUser，使用用户名和密码查询user信息
		User user = new User();
		user = userService.selectUser(name);

		// 判断查询结果
		if (user == null) { // 查询结果为空
			System.out.println("user == null");
			beanJson.put("status", "0001");
			beanJson.put("message", "用户名错误，请重新登录");
		} else { // 查询结果不为空
			System.out.println("user:" + user.toString());
			// 判断密码是否正确
			if (password.equals(user.getPassword())) {
				// JSONObject赋值
				beanJson.put("status", "0000");
				beanJson.put("message", "登录成功");
				beanJson.put("id", user.getId());
				beanJson.put("name", user.getName());
			} else {
				System.out.println("password != user.getPassword()");
				beanJson.put("status", "0002");
				beanJson.put("message", "密码错误，请重新登录");
			}
		}

		try {
			System.out.println("try:response.getWriter");
			// 将JSONObject传回页面
			System.out.println("beanJson:" + beanJson);
			response.getWriter().print(beanJson);
		} catch (IOException e) {
			System.out.println("catch:response.getWriter");
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/getUserAllInfo") // 路径:/getUserAllInfo
	// 简写:@RequestMapping("/getUserAllInfo")
	public String getUserAllInfo(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getUserAllInfo()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 查询多条
		List<User> listuser = new ArrayList<User>();
		listuser = userService.selectMulUser();
		System.out.println("listuser:" + listuser);
		System.out.println("listuser.size():" + listuser.size());

		// 定义json数组用于向页面传值
		JSONArray jsonArrayUser = JSONArray.fromObject(listuser);

		// JSONObject赋值
		beanJson.put("status", "0000");
		beanJson.put("message", "已成功查询所有用户信息");
		beanJson.put("userssize", listuser.size());
		beanJson.put("users", jsonArrayUser);

		try {
			System.out.println("try:response.getWriter");
			// 将JSONObject传回页面
			System.out.println("beanJson:" + beanJson);
			response.getWriter().print(beanJson);
			System.out.println("response:" + response);
		} catch (IOException e) {
			System.out.println("catch:response.getWriter");
			e.printStackTrace();
		}

		return null;
	}

/*
	@RequestMapping(value = "/loginP") // 路径:/loginP
	// 简写:@RequestMapping("/loginP")
	public String loginP(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("loginP()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// print前端页面值
		System.out.println("request:" + request);
		System.out.println("request.name:" + request.getParameter("name"));
		System.out.println("request.password:" + request.getParameter("password"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		// name = "杨扬"; // ***测试用数据***
		// password = "c"; // ***测试用数据***
		System.out.println("name:" + name);
		System.out.println("password:" + password);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 调用userService.selectUser，使用用户名和密码查询user信息
		User user = new User();
		user = userService.selectUser(name);

		// 判断查询结果
		if (user == null) { // 查询结果为空
			System.out.println("user == null");
			beanJson.put("status", "0001");
			beanJson.put("message", "用户名错误，请重新登录");
		} else { // 查询结果不为空
			System.out.println("user:" + user.toString());
			// PrintSomething.printUser(user);

			// 判断密码是否正确
			if (password.equals(user.getPassword())) {
				// 查询多条
				List<ProjectSummary> listproject = new ArrayList<ProjectSummary>();
				listproject = projectService.selectMulProject();
				System.out.println("listproject:" + listproject);
				System.out.println("listproject.size():" + listproject.size());

				// 查询多条
				List<User> listuser = new ArrayList<User>();
				listuser = userService.selectMulUser();
				System.out.println("listuser:" + listuser);
				System.out.println("listuser.size():" + listuser.size());

				// 定义json数组用于向页面传值
				JSONArray jsonArrayUser = JSONArray.fromObject(listuser);

				if (listproject.size() == 0) {
					beanJson.put("status", "0003");
					beanJson.put("message", "登录成功，暂无任何任务数据");
				} else {
					// 定义json数组用于向页面传值
					JSONArray jsonArrayProject = JSONArray.fromObject(listproject);
					// JSONObject赋值
					beanJson.put("status", "0000");
					beanJson.put("message", "登录成功，已显示全部任务数据");
					beanJson.put("projectssize", listproject.size());
					beanJson.put("projects", jsonArrayProject);
				}
				beanJson.put("id", user.getId());
				beanJson.put("name", user.getName());
				// beanJson.put("password",user.getPassword());
				beanJson.put("userssize", listuser.size());
				beanJson.put("users", jsonArrayUser);
			} else {
				System.out.println("password != user.getPassword()");
				beanJson.put("status", "0002");
				beanJson.put("message", "密码错误，请重新登录");
			}
		}

		try {
			System.out.println("try:response.getWriter");
			// 将JSONObject传回页面
			System.out.println("beanJson:" + beanJson);
			response.getWriter().print(beanJson);
			System.out.println("response:" + response);
		} catch (IOException e) {
			System.out.println("catch:response.getWriter");
			e.printStackTrace();
		}

		return null;
	}*/

/*	@RequestMapping(value = "/loginT") // 路径:/loginT
	// 简写:@RequestMapping("/loginT")
	public String loginT(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("loginT()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// print前端页面值
		System.out.println("request:" + request);
		System.out.println("request.name:" + request.getParameter("name"));
		System.out.println("request.password:" + request.getParameter("password"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		// name = "杨扬"; // ***测试用数据***
		// password = "c"; // ***测试用数据***
		System.out.println("name:" + name);
		System.out.println("password:" + password);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 调用userService.selectUser，使用用户名和密码查询user信息
		User user = new User();
		user = userService.selectUser(name);

		// 判断查询结果
		if (user == null) { // 查询结果为空
			System.out.println("user == null");
			beanJson.put("status", "0001");
			beanJson.put("message", "用户名错误，请重新登录");
		} else { // 查询结果不为空
			System.out.println("user:" + user.toString());
			// PrintSomething.printUser(user);

			// 判断密码是否正确
			if (password.equals(user.getPassword())) {
				// 查询多条
				List<Task> listtask = new ArrayList<Task>();
				listtask = taskService.selectMulTask();
				System.out.println("listtask:" + listtask);
				System.out.println("listtask.size():" + listtask.size());

				// 查询多条
				List<User> listuser = new ArrayList<User>();
				listuser = userService.selectMulUser();
				System.out.println("listuser:" + listuser);
				System.out.println("listuser.size():" + listuser.size());

				// 定义json数组用于向页面传值
				JSONArray jsonArrayUser = JSONArray.fromObject(listuser);

				if (listtask.size() == 0) {
					beanJson.put("status", "0003");
					beanJson.put("message", "登录成功，暂无任何任务数据");
				} else {
					// 定义json数组用于向页面传值
					JSONArray jsonArrayTask = JSONArray.fromObject(listtask);
					// JSONObject赋值
					beanJson.put("status", "0000");
					beanJson.put("message", "登录成功，已显示全部任务数据");
					beanJson.put("taskssize", listtask.size());
					beanJson.put("tasks", jsonArrayTask);
				}
				beanJson.put("id", user.getId());
				beanJson.put("name", user.getName());
				// beanJson.put("password",user.getPassword());
				beanJson.put("userssize", listuser.size());
				beanJson.put("users", jsonArrayUser);
			} else {
				System.out.println("password != user.getPassword()");
				beanJson.put("status", "0002");
				beanJson.put("message", "密码错误，请重新登录");
			}
		}

		try {
			System.out.println("try:response.getWriter");
			// 将JSONObject传回页面
			System.out.println("beanJson:" + beanJson);
			response.getWriter().print(beanJson);
			System.out.println("response:" + response);
		} catch (IOException e) {
			System.out.println("catch:response.getWriter");
			e.printStackTrace();
		}

		return null;
	}*/

}