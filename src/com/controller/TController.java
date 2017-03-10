package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bean.Task;
import com.bean.TaskSummary;

import javasrc.TaskService;
import javasrc.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller // Controller
public class TController {

	@Autowired // 自动加载UserService
	// 定义UserService对象
	UserService userService;

	@Autowired // 自动加载TaskService
	// 定义TaskService对象
	TaskService taskService;

	// 查询多条任务，全部
	@RequestMapping(value = "/getTaskAllInfoByProjectId") // 路径:/getTaskAllInfoByProjectId
	public String getTaskAllInfoByProjectId(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getTaskAllInfoByProjectId()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// print前端页面值
		System.out.println("request.project_id:" + request.getParameter("project_id"));
		int project_id = 0;
		if (request.getParameter("project_id") != null)
			project_id = Integer.parseInt(request.getParameter("project_id"));
		//project_id = 1; // ***测试用数据***
		System.out.println("project_id" + project_id);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 查询多条
		List<TaskSummary> listtask = new ArrayList<TaskSummary>();
		listtask = taskService.selectMulTask(project_id);
		//listtask = taskService.selectMulTaskSort(project_id);
		System.out.println("listtask:" + listtask);
		System.out.println("listtask.size():" + listtask.size());

		if (listtask.size() == 0) {
			beanJson.put("status", "0001");
			beanJson.put("message", "暂无任何任务数据");
		} else {
			// 定义json数组用于向页面传值
			JSONArray jsonArrayTask = JSONArray.fromObject(listtask);
			// JSONObject赋值
			beanJson.put("status", "0000");
			beanJson.put("message", "已显示全部任务数据");
			beanJson.put("taskssize", listtask.size());
			beanJson.put("tasks", jsonArrayTask);
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

	// 查询多条任务，全部
	@RequestMapping(value = "/getTaskAllInfoByProjectIdSort") // 路径:/getTaskAllInfoByProjectIdSort
	public String getTaskAllInfoByProjectIdSort(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getTaskAllInfoByProjectIdSort()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// print前端页面值
		System.out.println("request.project_id:" + request.getParameter("project_id"));
		int project_id = 0;
		if (request.getParameter("project_id") != null)
			project_id = Integer.parseInt(request.getParameter("project_id"));
		//project_id = 1; // ***测试用数据***
		System.out.println("project_id" + project_id);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 查询多条
		List<TaskSummary> listtask = new ArrayList<TaskSummary>();
		//listtask = taskService.selectMulTask(project_id);
		listtask = taskService.selectMulTaskSort(project_id);
		System.out.println("listtask:" + listtask);
		System.out.println("listtask.size():" + listtask.size());

		if (listtask.size() == 0) {
			beanJson.put("status", "0001");
			beanJson.put("message", "暂无任何任务数据");
		} else {
			// 定义json数组用于向页面传值
			JSONArray jsonArrayTask = JSONArray.fromObject(listtask);
			// JSONObject赋值
			beanJson.put("status", "0000");
			beanJson.put("message", "已显示全部任务数据");
			beanJson.put("taskssize", listtask.size());
			beanJson.put("tasks", jsonArrayTask);
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

	// 查询多条任务，单层查询
	@RequestMapping(value = "/getTaskSubInfo") // 路径:/getTaskSubInfo
	public String getTaskSubInfo(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getTaskSubInfo()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// print前端页面值
		System.out.println("request.task_id:" + request.getParameter("task_id"));
		int task_id = 0;
		if (request.getParameter("task_id") != null)
			task_id = Integer.parseInt(request.getParameter("task_id"));
		//task_id = 0; // ***测试用数据***
		System.out.println("task_id:" + task_id);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 查询多条
		List<TaskSummary> listtask = new ArrayList<TaskSummary>();
		listtask = taskService.selectMulTaskById(task_id);
		System.out.println("listtask:" + listtask);
		System.out.println("listtask.size():" + listtask.size());

		if (listtask.size() == 0) {
			beanJson.put("status", "0001");
			beanJson.put("message", "暂无任何任务数据");
		} else {
			// 定义json数组用于向页面传值
			JSONArray jsonArrayTask = JSONArray.fromObject(listtask);
			// JSONObject赋值
			beanJson.put("status", "0000");
			beanJson.put("message", "已显示全部任务数据");
			beanJson.put("taskssize", listtask.size());
			beanJson.put("tasks", jsonArrayTask);
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

	// 查询单条任务
	@RequestMapping(value = "/getTaskByID") // 路径:/getTaskByID
	public String getTaskByID(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getTaskByID()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// print前端页面值
		System.out.println("request.task_id:" + request.getParameter("task_id"));
		int id = 0;
		if (request.getParameter("task_id") != null)
			id = Integer.parseInt(request.getParameter("task_id"));
		System.out.println("id:" + id);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

/*		//任务id检查
		if (id == 0){
			beanJson.put("message","任务id错误");
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

		// 调用taskService.selectSimTask，查询task信息
		Task task = new Task();
		task = taskService.selectSimTask(id);
		//task = taskService.selectSimTask(1); // ***测试用数据***

		// 判断查询结果
		if (task == null) { // 查询结果为空
			System.out.println("task == null");
			beanJson.put("status", "0001");
			beanJson.put("message", "没有任务数据");
		} else { // 查询结果不为空
			System.out.println("task:" + task);

			// JSONObject赋值
			beanJson.put("status", "0000");
			beanJson.put("message", "查询任务成功");
			beanJson.put("task", task);
		}

/*		// 判断查询结果
		if (task == null) { // 查询结果为空
			System.out.println("task == null");
			beanJson.put("status", "0001");
			beanJson.put("message", "没有任务数据");
			try {
				System.out.println("try:response.getWriter");
				// 将JSONObject传回页面
				System.out.println("beanJson:"+beanJson);
				response.getWriter().print(beanJson);
			} catch (IOException e) {
				System.out.println("catch:response.getWriter");
				e.printStackTrace();
			}
			return null;
		}
		// 查询结果不为空
		System.out.println("task:" + task);
		// JSONObject赋值
		beanJson.put("status", "0000");
		beanJson.put("message", "查询任务成功");
		beanJson.put("task", task);*/

		try {
			System.out.println("try:response.getWriter");
			// 将JSONObject传回页面
			System.out.println("beanJson:"+beanJson);
			response.getWriter().print(beanJson);
			
		} catch (IOException e) {
			System.out.println("catch:response.getWriter");
			e.printStackTrace();
		}

		return null;
	}

	// 录入任务
	@RequestMapping(value = "/createTaskByID") // 路径:/createTaskByID
	public String createTaskByID(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("createTaskByID()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		Task task = new Task();

		// print前端页面值,写入task对象中
		System.out.println("request.task_name:" + request.getParameter("task_name"));
		System.out.println("request.task_overview:" + request.getParameter("task_overview"));
		System.out.println("request.task_description:" + request.getParameter("task_description"));
		System.out.println("request.task_owner_id:" + request.getParameter("task_owner_id"));
		System.out.println("request.task_start_time:" + request.getParameter("task_start_time"));
		System.out.println("request.task_finish_time:" + request.getParameter("task_finish_time"));
		System.out.println("request.task_progress:" + request.getParameter("task_progress"));
		System.out.println("request.task_remark:" + request.getParameter("task_remark"));
		System.out.println("request.task_status:" + request.getParameter("task_status"));
		System.out.println("request.task_project_id:" + request.getParameter("task_project_id"));
		System.out.println("request.task_parent_task_id:" + request.getParameter("task_parent_task_id"));

		task.setId(0);
		task.setName(request.getParameter("task_name"));
		task.setOverview(request.getParameter("task_overview"));
		task.setDescription(request.getParameter("task_description"));
		if(request.getParameter("task_owner_id") != null)
			task.setOwner_id(Integer.parseInt(request.getParameter("task_owner_id")));
		else
			task.setOwner_id(0);
		task.setStart_time(request.getParameter("task_start_time"));
		task.setFinish_time(request.getParameter("task_finish_time"));
		if(request.getParameter("task_progress") != null)
			task.setProgress(Float.parseFloat(request.getParameter("task_progress")));
		else
			task.setProgress(0);
		task.setRemark(request.getParameter("task_remark"));
		task.setStatus(request.getParameter("task_status"));
		if(request.getParameter("task_project_id") != null)
			task.setProject_id(Integer.parseInt(request.getParameter("task_project_id")));
		else
			task.setProject_id(0);
		if(request.getParameter("task_parent_task_id") != null)
			task.setParent_task_id(Integer.parseInt(request.getParameter("task_parent_task_id")));
		else
			task.setParent_task_id(0);
/*		// ***测试用数据***
		task.setName("数学");
		task.setOverview("1至3页");
		task.setDescription("1至3页");
		task.setOwner_id(1);
		task.setStart_time("2017-9-22");
		task.setFinish_time("2019-3-1");
		task.setProgress(Float.parseFloat("0.46"));
		task.setRemark("好好学习天天向上");
		task.setStatus("P");
		task.setProject_id(1);
		task.setParent_task_id(0);*/
		System.out.println("task:" + task);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		String username = userService.selectName(task.getOwner_id());
		System.out.println("username:" + username);
		// 判断责任人用户号是否存在
		if (username == null) { // 责任人不存在
			System.out.println("责任人用户号不存在");
			beanJson.put("status", "0002");
			beanJson.put("message", "责任人输入错误，任务录入失败");
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
		// 责任人存在
		System.out.println("责任人id正确");

		// 录入的任务不是主任务
		if(task.getParent_task_id() != 0){
			// 调用taskService.selectSimTask，查询父任务的信息
			Task parenttask = new Task();
			parenttask = taskService.selectSimTask(task.getParent_task_id());

			// 判断查询结果
			if (parenttask == null) { // 查询结果为空
				System.out.println("parent_task == null");
				beanJson.put("status", "0003");
				beanJson.put("message", "父任务不存在，任务录入失败");
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
			} else if (parenttask.getProject_id() != task.getProject_id()){
				System.out.println("parenttask.getProject_id():" + parenttask.getProject_id());
				beanJson.put("status", "0004");
				beanJson.put("message", "父任务不属于本项目，任务录入失败");
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
			// 父任务正确
			System.out.println("父任务正确");
		}

		Task instask = taskService.insertTask(task);
		System.out.println("instask:" + instask);

		// 判断录入结果
		if (instask != null) { // 录入正常
			System.out.println("任务录入成功");
			// JSONObject赋值
			beanJson.put("status", "0000");
			beanJson.put("message", "任务录入成功");
			beanJson.put("task", instask);
		} else { // 录入异常
			System.out.println("任务录入失败");
			beanJson.put("status", "0001");
			beanJson.put("message", "任务录入失败");
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

	// 更新任务
	@RequestMapping(value = "updateTaskByID") // 路径:/updateTaskByID
	public String updateTaskByID(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("updateTaskByID()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		Task task = new Task();

		// print前端页面值,写入task对象中
		System.out.println("request.task_id:" + request.getParameter("task_id"));
		System.out.println("request.task_name:" + request.getParameter("task_name"));
		System.out.println("request.task_overview:" + request.getParameter("task_overview"));
		System.out.println("request.task_description:" + request.getParameter("task_description"));
		System.out.println("request.task_owner_id:" + request.getParameter("task_owner_id"));
		System.out.println("request.task_start_time:" + request.getParameter("task_start_time"));
		System.out.println("request.task_finish_time:" + request.getParameter("task_finish_time"));
		System.out.println("request.task_progress:" + request.getParameter("task_progress"));
		System.out.println("request.task_remark:" + request.getParameter("task_remark"));
		System.out.println("request.task_status:" + request.getParameter("task_status"));
		System.out.println("request.task_project_id:" + request.getParameter("task_project_id"));
		System.out.println("request.task_parent_task_id:" + request.getParameter("task_parent_task_id"));
		
		if(request.getParameter("task_id") != null)
			task.setId(Integer.parseInt(request.getParameter("task_id")));
		else
			task.setId(0);
		task.setName(request.getParameter("task_name"));
		task.setOverview(request.getParameter("task_overview"));
		task.setDescription(request.getParameter("task_description"));
		if(request.getParameter("task_owner_id") != null)
			task.setOwner_id(Integer.parseInt(request.getParameter("task_owner_id")));
		else
			task.setOwner_id(0);
		task.setStart_time(request.getParameter("task_start_time"));
		task.setFinish_time(request.getParameter("task_finish_time"));
		if(request.getParameter("task_progress") != null)
			task.setProgress(Float.parseFloat(request.getParameter("task_progress")));
		else
			task.setProgress(0);
		task.setRemark(request.getParameter("task_remark"));
		task.setStatus(request.getParameter("task_status"));
		if(request.getParameter("task_project_id") != null)
			task.setProject_id(Integer.parseInt(request.getParameter("task_project_id")));
		else
			task.setProject_id(0);
		if(request.getParameter("task_parent_task_id") != null)
			task.setParent_task_id(Integer.parseInt(request.getParameter("task_parent_task_id")));
		else
			task.setParent_task_id(0);
/*		// ***测试用数据***
		task.setId(1);
		task.setName("足球");
		task.setOverview("2至2页");
		task.setDescription("4至6页");
		task.setOwner_id(2);
		task.setStart_time("2017-9-13");
		task.setFinish_time("2019-3-15");
		task.setProgress(Float.parseFloat("0.11"));
		task.setRemark("好好学习天天向上！！！");
		task.setStatus("F");
		task.setProject_id(1);
		task.setParent_task_id(2);*/
		System.out.println("task:" + task);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 判断父任务号和任务号是否相同
		if(task.getId() == task.getParent_task_id()){
			System.out.println("父任务不能是任务本身，项目更新失败");
			beanJson.put("status", "0003");
			beanJson.put("message", "父任务不能是任务本身，项目更新失败");

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

		// 判断待更新的任务是否存在
		if (taskService.selectSimTask(task.getId()) == null) {
			System.out.println("待更新的任务不存在");
			beanJson.put("status", "0011");
			beanJson.put("message", "待更新的任务不存在");

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
		// 待更新的任务存在
		System.out.println("待更新的任务存在");

		String username = userService.selectName(task.getOwner_id());
		System.out.println("username:" + username);

		// 判断责任人用户号是否存在
		if (username == null) { // 责任人不存在
			System.out.println("责任人用户号不存在");
			beanJson.put("status", "0002");
			beanJson.put("message", "责任人用户号错误，任务更新失败");

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
		// 责任人存在
		System.out.println("责任人id正确");

		Task updtask = taskService.updateTask(task);
		System.out.println("updtask:" + updtask);

		// 判断更新结果
		if (updtask != null) { // 更新正常
			System.out.println("任务更新成功");
			// JSONObject赋值
			beanJson.put("status", "0000");
			beanJson.put("message", "任务更新成功");
			beanJson.put("task", updtask);
		} else { // 更新异常
			System.out.println("任务更新失败");
			beanJson.put("status", "0001");
			beanJson.put("message", "任务更新失败");
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

	// 删除任务
	@RequestMapping(value = "/deleteTaskByID") // 路径:/deleteTaskByID
	public String deleteTaskByID(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("deleteTaskByID()");

		// 设置请求以及响应的内容类型以及编码方式
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// print前端页面值
		System.out.println("request.task_id:" + request.getParameter("task_id"));
		int id = 0;
		if (request.getParameter("task_id") != null)
			id = Integer.parseInt(request.getParameter("task_id"));
		//id = 2; // ***测试用数据***
		System.out.println("id:" + id);

		// 定义json对象用于向页面传值
		JSONObject beanJson = new JSONObject();

		// 判断待删除的任务是否存在
		if (taskService.selectSimTask(id) == null) {
			System.out.println("待删除的任务不存在");
			beanJson.put("status", "0011");
			beanJson.put("message", "待删除的任务不存在");
		} else {
/*			// 任务id检查
			if (id == 0) {
				beanJson.put("message", "任务id错误");
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

			// 调用taskService.deleteTask，使用用户名和密码查询user信息
			boolean delflag = false;
			delflag = taskService.deleteTask(id);
			System.out.println("delflag:" + delflag);

			// 判断查询结果
			if (delflag) { // 删除正常
				System.out.println("任务删除成功");
				// JSONObject赋值
				beanJson.put("status", "0000");
				beanJson.put("message", "任务删除成功");
				beanJson.put("task_id", id);
			} else { // 删除异常
				beanJson.put("status", "0001");
				System.out.println("任务删除失败");
				beanJson.put("message", "任务删除失败");
			}
		}

		try {
			System.out.println("try:response.getWriter");
			// 将JSONObject传回页面
			System.out.println("beanJson:"+beanJson);
			response.getWriter().print(beanJson);
			
		} catch (IOException e) {
			System.out.println("catch:response.getWriter");
			e.printStackTrace();
		}

		return null;
	}

}