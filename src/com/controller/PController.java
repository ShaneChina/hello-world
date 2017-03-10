package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bean.Project;
import com.bean.ProjectSummary;

import javasrc.ProjectService;
import javasrc.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller // Controller
public class PController {

	@Autowired // 自动加载UserService
	// 定义UserService对象
	UserService userService;

	@Autowired // 自动加载ProjectService
	// 定义ProjectService对象
	ProjectService projectService;

	@RequestMapping(value = "/getProjectAllInfo") // 路径:/getProjectAllInfo
	public String getProjectAllInfo(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getProjectAllInfo()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 查询多条
		List<ProjectSummary> listproject = new ArrayList<ProjectSummary>();
		listproject = projectService.selectMulProject();
		System.out.println("listproject:" + listproject);
		System.out.println("listproject.size():" + listproject.size());

		if (listproject.size() == 0) {
			beanJson.put("status", "0001");
			beanJson.put("message", "暂无任何项目数据");
		} else {
			// 定义json数组用于向页面传值
			JSONArray jsonArrayProject = JSONArray.fromObject(listproject);
			// JSONObject赋值
			beanJson.put("status", "0000");
			beanJson.put("message", "已显示全部项目数据");
			beanJson.put("projectssize", listproject.size());
			beanJson.put("projects", jsonArrayProject);
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

	// 查询单条项目
	@RequestMapping(value = "/getProjectById") // 路径:/getProjectById
	public String getProjectById(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getProjectById()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// print前端页面值
		System.out.println("project_id:" + request.getParameter("project_id"));
		int id = 0;
		if (request.getParameter("project_id") != null)
			id = Integer.parseInt(request.getParameter("project_id"));
		System.out.println("id:" + id);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

/*		//项目id检查
		if (id == 0){
			beanJson.put("status", "0002");
			beanJson.put("message","项目id错误");
			try {
				System.out.println("try:response.getWriter");
				//将JSONObject传回页面
				response.getWriter().print(beanJson);
			} catch (IOException e) {
				System.out.println("catch:response.getWriter");
				e.printStackTrace();
			}
			return null;
		}*/

		// 调用projectService.selectSimProject，查询project信息
		Project project = new Project();
		project = projectService.selectSimProject(id);
		//project = projectService.selectSimProject(1); // ***测试用数据***

		// 判断查询结果
		if (project != null) { // 查询结果不为空
			System.out.println("project:" + project);

			// JSONObject赋值
			beanJson.put("status", "0000");
			beanJson.put("message", "查询项目成功");
			beanJson.put("project", project);
		} else { // 查询结果为空
			System.out.println("project == null");
			beanJson.put("status", "0001");
			beanJson.put("message", "没有项目数据");
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

	// 录入项目
	@RequestMapping(value = "/createProject") // 路径:/createProject
	public String createProject(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("createProject()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		Project project = new Project();

		// print前端页面值,写入project对象中
		System.out.println("request.project_name:" + request.getParameter("project_name"));
		System.out.println("request.project_overview:" + request.getParameter("project_overview"));
		System.out.println("request.project_description:" + request.getParameter("project_description"));
		System.out.println("request.project_owner_id:" + request.getParameter("project_owner_id"));
		System.out.println("request.project_start_time:" + request.getParameter("project_start_time"));
		System.out.println("request.project_finish_time:" + request.getParameter("project_finish_time"));
		System.out.println("request.project_status:" + request.getParameter("project_status"));
		System.out.println("request.project_create_id:" + request.getParameter("project_create_id"));
		System.out.println("request.project_remark:" + request.getParameter("project_remark"));

		project.setId(0);
		project.setName(request.getParameter("project_name"));
		project.setOverview(request.getParameter("project_overview"));
		project.setDescription(request.getParameter("project_description"));
		if (request.getParameter("project_owner_id") != null)
			project.setOwner_id(Integer.parseInt(request.getParameter("project_owner_id")));
		else
			project.setOwner_id(0);
		project.setStart_time(request.getParameter("project_start_time"));
		project.setFinish_time(request.getParameter("project_finish_time"));
		project.setStatus(request.getParameter("project_status"));
		if (request.getParameter("project_create_id") != null)
			project.setCreate_id(Integer.parseInt(request.getParameter("project_create_id")));
		else
			project.setCreate_id(0);
		project.setCreate_time("");
		project.setRemark(request.getParameter("project_remark"));
/*		// ***测试用数据***
		project.setName("数学");
		project.setOverview("概述");
		project.setDescription("1至3页");
		project.setOwner_id(2);
		project.setStart_time("2017-9-22");
		project.setFinish_time("2019-3-1");
		project.setStatus("P");
		project.setCreate_id(3);
		project.setRemark("好好学习天天向上");*/
		System.out.println("project:" + project);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 创建人为登录人，不再重复检查存在性
		// 检查项目经理是否存在
		String username = userService.selectName(project.getOwner_id());
		System.out.println("username:" + username);
		// 判断项目经理是否存在
		if (username != null) { // 项目经理存在
			System.out.println("项目经理id正确");

			Project insproject = projectService.insertProject(project);

			// debug
			System.out.println("insproject:" + insproject);
			// 判断录入结果
			if (insproject != null) { // 录入正常
				System.out.println("项目录入成功");
				// JSONObject赋值
				beanJson.put("status", "0000");
				beanJson.put("message", "项目录入成功");
				beanJson.put("project", insproject);
			} else { // 录入异常
				System.out.println("项目录入失败");
				beanJson.put("status", "0002");
				beanJson.put("message", "项目录入失败");
			}
		} else { // 项目经理不存在
			System.out.println("错误的项目经理，项目录入失败");
			beanJson.put("status", "0001");
			beanJson.put("message", "错误的项目经理，项目录入失败");
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

	// 更新项目
	@RequestMapping(value = "/updateProjectById") // 路径:/updateProjectById
	public String updateProjectById(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("updateProjectById()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		Project project = new Project();

		// print前端页面值,写入project对象中
		System.out.println("request.project_id:" + request.getParameter("project_id"));
		System.out.println("request.project_name:" + request.getParameter("project_name"));
		System.out.println("request.project_overview:" + request.getParameter("project_overview"));
		System.out.println("request.project_description:" + request.getParameter("project_description"));
		System.out.println("request.project_owner_id:" + request.getParameter("project_owner_id"));
		System.out.println("request.project_start_time:" + request.getParameter("project_start_time"));
		System.out.println("request.project_finish_time:" + request.getParameter("project_finish_time"));
		System.out.println("request.project_remark:" + request.getParameter("project_remark"));
		System.out.println("request.project_status:" + request.getParameter("project_status"));

		if (request.getParameter("project_id") != null)
			project.setId(Integer.parseInt(request.getParameter("project_id")));
		else
			project.setId(0);
		project.setName(request.getParameter("project_name"));
		project.setOverview(request.getParameter("project_overview"));
		project.setDescription(request.getParameter("project_description"));
		if (request.getParameter("project_owner_id") != null)
			project.setOwner_id(Integer.parseInt(request.getParameter("project_owner_id")));
		else
			project.setOwner_id(0);
		project.setStart_time(request.getParameter("project_start_time"));
		project.setFinish_time(request.getParameter("project_finish_time"));
		project.setCreate_id(0);
		project.setCreate_time("");
		project.setRemark(request.getParameter("project_remark"));
		project.setStatus(request.getParameter("project_status"));
		// ***测试用数据***
/*		project.setId(5);
		project.setName("数学1");
		project.setOverview("概述1");
		project.setDescription("1至3页1");
		project.setOwner_id(3);
		project.setStart_time("2017-9-30");
		project.setFinish_time("2019-3-31");
		project.setRemark("好好学习天天向上1");
		project.setStatus("F");*/
		System.out.println("project:" + project);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 判断待更新的项目是否存在
		if (projectService.selectSimProject(project.getId()) == null) {
			System.out.println("待更新的项目不存在");
			beanJson.put("status", "0011");
			beanJson.put("message", "待更新的项目不存在");
		} else {
			String username = userService.selectName(project.getOwner_id());
			// debug
			System.out.println("username:" + username);
			// 判断项目经理用户号是否存在
			if (username != null) { // 项目经理存在
				System.out.println("项目经理id正确");

				Project updproject = projectService.updateProject(project);

				// debug
				System.out.println("updproject:" + updproject);
				// 判断录入结果
				if (updproject != null) { // 更新正常
					System.out.println("项目更新成功");
					// JSONObject赋值
					beanJson.put("status", "0000");
					beanJson.put("message", "项目更新成功");
					beanJson.put("project", updproject);
				} else { // 更新异常
					System.out.println("项目更新失败");
					beanJson.put("status", "0002");
					beanJson.put("message", "项目更新失败");
				}
			} else { // 项目经理不存在
				System.out.println("错误的项目经理，项目更新失败");
				beanJson.put("status", "0001");
				beanJson.put("message", "错误的项目经理，项目更新失败");
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

	// 删除项目
	@RequestMapping(value = "/deleteProjectById") // 路径:/deleteProjectById
	public String deleteProjectById(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("deleteProjectById()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// print前端页面值
		System.out.println("request.project_id:" + request.getParameter("project_id"));
		int id = 0;
		if (request.getParameter("project_id") != null)
			id = Integer.parseInt(request.getParameter("project_id"));
		//id = 4; // ***测试用数据***
		System.out.println("id:" + id);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 判断待删除的项目是否存在
		if (projectService.selectSimProject(id) == null) {
			System.out.println("待删除的项目不存在");
			beanJson.put("status", "0011");
			beanJson.put("message", "待删除的项目不存在");
		} else {
/*			// 项目id检查
			if (id == 0) {
				beanJson.put("status", "0012");
				beanJson.put("message", "项目id错误");
				try {
					System.out.println("try:response.getWriter");
					// 将JSONObject传回页面
					response.getWriter().print(beanJson);
				} catch (IOException e) {
					System.out.println("catch:response.getWriter");
					e.printStackTrace();
				}
				return null;
			}*/

			// 调用projectService.deleteProject，使用用户名和密码查询user信息
			boolean delflag = false;
			delflag = projectService.deleteProject(id);
			System.out.println("delflag:" + delflag);

			// 判断查询结果
			if (delflag) { // 删除正常
				System.out.println("项目删除成功");
				// JSONObject赋值
				beanJson.put("status", "0000");
				beanJson.put("message", "项目删除成功");
				beanJson.put("project_id", id);
			} else { // 删除异常
				beanJson.put("status", "0001");
				System.out.println("项目删除失败");
				beanJson.put("message", "项目删除失败");
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
}