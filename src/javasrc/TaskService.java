package javasrc;

import java.util.List;

import com.bean.Task;
import com.bean.TaskSummary;

//定义TaskService接口
public interface TaskService {
	// 查询多条任务（排层序）
	// 输入：项目号
	// 输出：多条任务信息（size为0表示无数据）
	public List<TaskSummary> selectMulTask(int project_id);

	// 查询多条任务
	// 输入：任务号
	// 输出：多条任务信息（size为0表示无数据）
	public List<TaskSummary> selectMulTaskById(int id);

	// 查询单条任务
	// 输入：任务号
	// 输出：单条任务信息（返回null表示无数据）
	public Task selectSimTask(int id);

	// 录入任务
	// 输入：任务
	// 输出：单条任务信息
	public Task insertTask(Task task);

	// 更新任务
	// 输入：任务
	// 输出：单条任务信息（返回null表示无数据）
	public Task updateTask(Task task);

	// 删除任务
	// 输入：任务号
	// 输出：true-删除成功，false-删除失败
	public boolean deleteTask(int id);

	// 查询多条任务（按遍历排序）
	// 输入：项目号
	// 输出：多条任务信息（size为0表示无数据）
	public List<TaskSummary> selectMulTaskSort(int project_id);
}