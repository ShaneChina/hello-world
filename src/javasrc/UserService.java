package javasrc;

import java.util.List;

import com.bean.User;

//定义UserService接口
public interface UserService {
	// 录入用户
	// 输入：用户信息
	// 输出：无
	public void insertUser(User user);

	// 查询用户信息
	// 输入：用户名
	// 输出：用户信息（用户号，用户名，用户密码）
	public User selectUser(String name);

	// 查询用户号是否存在
	// 输入：用户号
	// 输出：true-用户存在，false-用户不存在
	public boolean selectId(int id);

	// 查询用户号是否存在
	// 输入：用户号
	// 输出：用户名
	public String selectName(int id);
	
	// 查询多条任务
	// 输入：无
	// 输出：多条任务信息（size为0表示无数据）
	public List<User> selectMulUser();
}