package javasrc;

import java.util.List;

import com.bean.Project;
import com.bean.ProjectSummary;

//定义ProjectService接口
public interface ProjectService {
	// 查询多条项目
	// 输入：无
	// 输出：多条项目信息（size为0表示无数据）
	public List<ProjectSummary> selectMulProject();

	// 查询单条项目
	// 输入：项目号
	// 输出：单条项目信息（返回null表示无数据）
	public Project selectSimProject(int id);

	// 录入项目
	// 输入：项目号
	// 输出：单条项目信息（返回null表示无数据）
	public Project insertProject(Project Project);

	// 更新项目
	// 输入：项目号
	// 输出：单条项目信息（返回null表示无数据）
	public Project updateProject(Project Project);

	// 删除项目（更新状态）
	// 输入：项目号
	// 输出：true-删除成功，false-删除失败
	public boolean deleteProject(int id);

}