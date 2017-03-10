package javasrc;

import com.bean.Project;
import com.bean.ProjectSummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.*;
import org.springframework.util.Assert;

public class ProjectServiceImpl implements ProjectService {

	// 注入对象jdbcTemplate
	private JdbcTemplate jdbcTemplate;

	// 被注入对象的set方法
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// 注入对象transactionManager
	private PlatformTransactionManager transactionManager;

	// 被注入对象的set方法
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	// 查询多条
	@Override
	public List<ProjectSummary> selectMulProject() {
		System.out.println("selectMulProject()");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 定义查询多条的SQL句
		final String selectSQL = "(select p.id,p.name,p.overview,p.owner_id,p.status,count(p.id) as tasksize"
				+ " from tb_project p,tb_task t"
				//+ " where p.id=t.project_id and p.status<>'D' and t.status<>'D' and t.parent_task_id=0"
				+ " where p.id=t.project_id and p.status<>'D' and t.status<>'D'"
				//+ " group by p.id order by p.id asc)"
				+ " group by p.id)"
				+ " union all"
				+ " (select p.id,p.name,p.overview,p.owner_id,p.status,0 as tasksize"
				+ " from tb_project p"
				+ " where p.id not in(select distinct project_id from tb_task where status<>'D') and p.status<>'D'"
				//+ " group by p.id order by p.id asc)";
				+ " group by p.id)"
				+ " order by id asc";
		System.out.println("selectSQL:" + selectSQL);

		// 定义返回值,类型List<ProjectSummary>
		List<ProjectSummary> listProjects = new ArrayList<ProjectSummary>();

		try {
			System.out.println("try:jdbcTemplate.query");
			RowMapper<ProjectSummary> rowMapper = new BeanPropertyRowMapper<ProjectSummary>(ProjectSummary.class);
			listProjects = jdbcTemplate.query(selectSQL, rowMapper);
			System.out.println("listProjects:" + listProjects);
			System.out.println("commit");
			transactionManager.commit(status);
		} catch (DataAccessException ex) {
			System.out.println("catch:jdbcTemplate.query");
			System.out.println("ex.getMessage:" + ex.getMessage());

			// 通过DataAccessException的类型判断sql产生的错误
			System.out.println("DataAccessException:" + ex.getClass());

			// 表不存在
			if (ex instanceof BadSqlGrammarException) {
				System.out.println("BadSqlGrammarException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
				// 其他错误
			} else {
				System.out.println("DataAccessException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
			}
		} finally {
			System.out.println("finally:jdbcTemplate.query");
		}
		return listProjects;
	}

	// 查询一条项目
	@Override
	public Project selectSimProject(int id) {
		System.out.println("selectSimProject(int id)");
		System.out.println("id:" + id);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 查询语句
		final String selectSQL = "select * from tb_project where id=?";
		System.out.println("selectSQL:" + selectSQL);

		// 定义返回值类型Project
		Project project = new Project();

		// spring提供了一个便利的RowMapper实现-----BeanPropertyRowMapper
		// 它可自动将一行数据映射到指定类的实例中，它首先将这个类实例化，然后通过名称匹配的方式，映射到属性中去。
		RowMapper<Project> rowMapper = new BeanPropertyRowMapper<Project>(Project.class);
		try {
			System.out.println("try:jdbcTemplate.queryForObject");
			// 调用queryForObject
			project = jdbcTemplate.queryForObject(selectSQL, new Object[] { id }, rowMapper);
			System.out.println("commit;");
			transactionManager.commit(status);
		} catch (DataAccessException ex) {
			System.out.println("catch:jdbcTemplate.queryForObject");
			System.out.println("ex.getMessage:" + ex.getMessage());

			// 通过DataAccessException的类型判断sql产生的错误
			System.out.println("DataAccessException:" + ex.getClass());

			// EmptyResultDataAccessException：0数据
			if (ex instanceof EmptyResultDataAccessException) {
				System.out.println("EmptyResultDataAccessException");
				System.out.println("commit;");
				transactionManager.commit(status);
				// throw ex;
				return null;
				// IncorrectResultSizeDataAccessException：数据数量大于1
			} else if (ex instanceof IncorrectResultSizeDataAccessException) {
				System.out.println("IncorrectResultSizeDataAccessException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
				// 表不存在
			} else if (ex instanceof BadSqlGrammarException) {
				System.out.println("BadSqlGrammarException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
				// DataAccessException：其他错误
			} else {
				System.out.println("DataAccessException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
			}
		} finally {
			System.out.println("finally:jdbcTemplate.queryForObject");
		}
		System.out.println("project:" + project);
		return project;
	}

	// 插入一条项目
	@Override
	public Project insertProject(Project project) {
		System.out.println("insertProject(Project project)");
		System.out.println("Project:" + project);

		Assert.notNull(project, "The Project must not be null");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		final String insertSQL = "INSERT INTO tb_project (id,name,overview,description,owner_id,start_time,finish_time,create_id,create_time,remark,status) VALUES (0,?,?,?,?,?,?,?,?,?,?)";
		System.out.println("insertSQL:" + insertSQL);

		// 获得当前日期并格式化
		Date dateNow = new Date();  
        System.out.println("当前日期:" + dateNow);  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String dateNowStr = sdf.format(dateNow);  
        System.out.println("格式化后的当前日期:" + dateNowStr);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(insertSQL, new String[] { "id" });
					ps.setString(1, project.getName());
					ps.setString(2, project.getOverview());
					ps.setString(3, project.getDescription());
					ps.setInt(4, project.getOwner_id());
					ps.setString(5, project.getStart_time());
					ps.setString(6, project.getFinish_time());
					ps.setInt(7, project.getCreate_id());
					ps.setString(8, dateNowStr);
					ps.setString(9, project.getRemark());
					ps.setString(10, project.getStatus());
					return ps;
				}
			}, keyHolder);
			System.out.println("commit");
			transactionManager.commit(status);
		} catch (DataAccessException ex) {
			System.out.println("catch:jdbcTemplate.update");
			System.out.println("ex.getMessage:" + ex.getMessage());

			// 通过DataAccessException的类型判断sql产生的错误
			System.out.println("DataAccessException:" + ex.getClass());

			// 待插入的数据已经存在
			if (ex instanceof DuplicateKeyException) {
				System.out.println("DuplicateKeyException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
				// 表不存在
			} else if (ex instanceof BadSqlGrammarException) {
				System.out.println("BadSqlGrammarException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
				// 其他错误
			} else {
				System.out.println("DataAccessException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
			}
		} finally {
			System.out.println("finally:jdbcTemplate.update");
		}

		// 录入成功的同时，获取自增主键（项目id）的值
		keyHolder.getKey().intValue();
		System.out.println("keyHolder.getKey().intValue():" + keyHolder.getKey().intValue());
		// 返回项目号
		project.setId(keyHolder.getKey().intValue());
		// 返回创建日期
		project.setCreate_time(dateNowStr);
		// 返回项目状态
		project.setStatus("P");

		System.out.println("Project:" + project);
		return project;
	}

	// 更新一条项目
	@Override
	public Project updateProject(Project project) {
		System.out.println("updateProject(Project project)");
		System.out.println("Project:" + project);

		Assert.notNull(project, "The Project must not be null");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 更新语句
		final String updateSQL = "update tb_project set name=?,overview=?,description=?,owner_id=?,start_time=?,finish_time=?,remark=?,status=? where id = ?";
		System.out.println("updateSQL:" + updateSQL);

		// 调用update
		try {
			System.out.println("try:jdbcTemplate.update");
			jdbcTemplate.update(updateSQL,
					new Object[] { project.getName(), 
							       project.getOverview(), 
								   project.getDescription(),
								   project.getOwner_id(),
								   project.getStart_time(),
								   project.getFinish_time(),
								   project.getRemark(), 
								   project.getStatus(),
								   project.getId() });
			System.out.println("commit");
			transactionManager.commit(status);
		} catch (DataAccessException ex) {
			System.out.println("catch:jdbcTemplate.update");
			System.out.println("ex.getMessage:" + ex.getMessage());

			// 通过DataAccessException的类型判断sql产生的错误
			System.out.println("DataAccessException:" + ex.getClass());

			// 表不存在
			if (ex instanceof BadSqlGrammarException) {
				System.out.println("BadSqlGrammarException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
				// 其他错误
			} else {
				System.out.println("DataAccessException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
			}
		} finally {
			System.out.println("finally:jdbcTemplate.update");
		}

		return project;
	}

	// 删除项目及旗下所有任务（修改状态为“P-已删除”）
	@Override
	public boolean deleteProject(int id) {
		System.out.println("deleteProject(int id)");
		System.out.println("id:" + id);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 更新tb_task表
		final String updateSQLT = "update tb_task set status='D' where project_id = ?";
		System.out.println("updateSQLT:" + updateSQLT);

		// 调用update
		try {
			System.out.println("try:jdbcTemplate.update");
			jdbcTemplate.update(updateSQLT, new Object[] { id });
			//System.out.println("commit");
			//transactionManager.commit(status);
		} catch (DataAccessException ex) {
			System.out.println("catch:jdbcTemplate.update");
			System.out.println("ex.getMessage:" + ex.getMessage());

			// 通过DataAccessException的类型判断sql产生的错误
			System.out.println("DataAccessException:" + ex.getClass());

			// 表不存在
			if (ex instanceof BadSqlGrammarException) {
				System.out.println("BadSqlGrammarException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
				// 其他错误
			} else {
				System.out.println("DataAccessException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
			}
		} finally {
			System.out.println("finally:jdbcTemplate.update");
		}

		// 更新tb_project表
		final String updateSQLP = "update tb_project set status='D' where id = ?";
		System.out.println("updateSQLP:" + updateSQLP);

		// 调用update
		try {
			System.out.println("try:jdbcTemplate.update");
			jdbcTemplate.update(updateSQLP, new Object[] { id });
			System.out.println("commit");
			transactionManager.commit(status);
		} catch (DataAccessException ex) {
			System.out.println("catch:jdbcTemplate.update");
			System.out.println("ex.getMessage:" + ex.getMessage());

			// 通过DataAccessException的类型判断sql产生的错误
			System.out.println("DataAccessException:" + ex.getClass());

			// 表不存在
			if (ex instanceof BadSqlGrammarException) {
				System.out.println("BadSqlGrammarException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
				// 其他错误
			} else {
				System.out.println("DataAccessException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
			}
		} finally {
			System.out.println("finally:jdbcTemplate.update");
		}

		return true;
	}

/*	// 删除一个项目
	@Override
	public boolean deleteProject(int pid) {
		System.out.println("deleteProject(int pid)");
		System.out.println("pid:" + pid);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 删除语句
		final String selectSQL = "delete from tb_Project where pid = ?";
		System.out.println("selectSQL:" + selectSQL);

		try {
			System.out.println("try:jdbcTemplate.update");
			jdbcTemplate.update(selectSQL, new Object[] { pid }, new int[] { java.sql.Types.INTEGER });
			System.out.println("commit");
			transactionManager.commit(status);
		} catch (DataAccessException ex) {
			System.out.println("catch:jdbcTemplate.update");
			System.out.println("ex.getMessage:" + ex.getMessage());

			// 通过DataAccessException的类型判断sql产生的错误
			System.out.println("DataAccessException:" + ex.getClass());

			// 表不存在
			if (ex instanceof BadSqlGrammarException) {
				System.out.println("BadSqlGrammarException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
				// 其他错误
			} else {
				System.out.println("DataAccessException");
				System.out.println("rollback");
				transactionManager.rollback(status);
				throw ex;
			}
		} finally {
			System.out.println("finally:jdbcTemplate.queryForList");
		}

		return true;
	}*/

}