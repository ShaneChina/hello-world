package javasrc;

import com.bean.SubtaskId;
import com.bean.Task;
import com.bean.TaskSummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.*;
import org.springframework.util.Assert;

public class TaskServiceImpl implements TaskService{

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
	public List<TaskSummary> selectMulTask(int project_id) {
		System.out.println("selectMulTask(int project_id)");
		System.out.println("project_id:" + project_id);

		// 定义返回值,类型List<TaskSummary>
		List<TaskSummary> listtasks = new ArrayList<TaskSummary>();

		// 主任务转换成List<TaskSummary>类型（parenttasksummarylist）
		TaskSummary parenttasksummary = new TaskSummary();
		parenttasksummary.setId(0);
		List<TaskSummary> parenttasksummarylist = new ArrayList<TaskSummary>();
		parenttasksummarylist.add(parenttasksummary);
		System.out.println("parenttasksummarylist:" + parenttasksummarylist);

		// 使用循环的方式查询所有任务及子任务
		// 定义returntasksummarylist运用于存储返回的子任务，并作为下次查询的父任务
		List<TaskSummary> returntasksummarylist = new ArrayList<TaskSummary>();
		// 定义returntasksummaryquantity表示子任务数量
		int returntasksummaryquantity = 0;
		System.out.println("returntasksummaryquantity:" + returntasksummaryquantity);

		// 层级
		long level=1;

		do{
			// 获取parenttasksummarylist的子任务，存入returntasksummarylist
			returntasksummarylist = selectSubtaskAll(parenttasksummarylist,project_id);
			for(TaskSummary simreturntasksummarylist:returntasksummarylist)
				simreturntasksummarylist.setSubtasksize(level);
			System.out.println("returntasksummarylist:" + returntasksummarylist);
			// 将子任务累加到listtasks中
			listtasks.addAll(returntasksummarylist);
			// 获取子任务returntasksummarylist的数量returntasksummaryquantity
			returntasksummaryquantity = returntasksummarylist.size();
			System.out.println("returntasksummaryquantity:" + returntasksummaryquantity);
			// 将returntasksummarylist作为父任务parenttasksummarylist
			parenttasksummarylist = returntasksummarylist;
			System.out.println("parenttasksummarylist:" + parenttasksummarylist);
			level++;
		}while(returntasksummaryquantity != 0); // 没有子任务则停止循环

		return listtasks;
	}

	// 查询所有子任务
	private List<TaskSummary> selectSubtaskAll(List<TaskSummary> parenttasksummarylist, int project_id) {
		System.out.println("selectSubtaskAll(List<TaskSummary> parenttasksummarylist, int project_id)");
		System.out.println("parenttasksummarylist:" + parenttasksummarylist);
		System.out.println("project_id:" + project_id);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

/*		// 方法1：拼接SQL句
		// 定义查询多条的SQL句
		StringBuffer selectSQLbuf = new StringBuffer("select id,name,overview,owner_id,progress,status,parent_task_id"
				+" from tb_task where project_id=? and status<>'D' and parent_task_id in ("); 
		for (int i=0; i< parenttasksummarylist.size(); i++) { 
		  if (i!=0) selectSQLbuf.append(", "); 
		  selectSQLbuf.append("?"); 
		}
		selectSQLbuf.append(")  order by parent_task_id asc"); 
		System.out.println("selectSQLbuf:" + selectSQLbuf.toString());*/

		// 方法2：使用NamedParameterJdbcTemplate
		// 定义NamedParameterJdbcTemplate
	    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	    // SQL句，使用变量:pid和:ptid
		String selectSQL = "select id,name,overview,owner_id,progress,status,parent_task_id from tb_task"
				+" where project_id=:pid and status<>'D' and parent_task_id in (:ptid)  order by parent_task_id asc";
		System.out.println("selectSQL:" + selectSQL);
		// parameters：添加name：pid和ptid及对应的value
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("pid", project_id);
	    List<Integer> ptidlist = new ArrayList<Integer>();
	    for(TaskSummary simparenttasksummarylist:parenttasksummarylist){
	    	ptidlist.add(simparenttasksummarylist.getId());
	    }
	    parameters.addValue("ptid", ptidlist);

		// 定义返回值,类型List<TaskSummary>
		List<TaskSummary> tasksummarylist = new ArrayList<TaskSummary>();

/* 		// 方法1：拼接SQL句
		try {
			System.out.println("try:jdbcTemplate.query");
			RowMapper<TaskSummary> rowMapper = new BeanPropertyRowMapper<TaskSummary>(TaskSummary.class);

			tasksummarylist = jdbcTemplate.query(selectSQLbuf.toString(), new PreparedStatementSetter() {
				public void setValues(PreparedStatement ps) throws SQLException {
					for(int n=0,m=parenttasksummarylist.size();n<m;n++){
						ps.setInt(1, project_id);
						ps.setInt(n+2, parenttasksummarylist.get(n).getId());
					}
				}
			}, rowMapper);*/

		// 方法2：使用NamedParameterJdbcTemplate.query(selectSQL, parameters, rowMapper)
		try {
			System.out.println("try:jdbcTemplate.query");
			RowMapper<TaskSummary> rowMapper = new BeanPropertyRowMapper<TaskSummary>(TaskSummary.class);
			tasksummarylist = namedParameterJdbcTemplate.query(selectSQL, parameters, rowMapper);

			System.out.println("tasksummarylist:" + tasksummarylist);
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
		return tasksummarylist;
	}

	// 查询多条任务，单层
	@Override
	public List<TaskSummary> selectMulTaskById(int id) {
		System.out.println("selectMulTaskById(int id)");
		System.out.println("id:" + id);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 定义查询多条的SQL句
		final String selectSQL = "select id,name,overview,owner_id,progress,status,parent_task_id"
				+ " from tb_task where parent_task_id=? and status<>'D' order by id asc";
		System.out.println("selectSQL:" + selectSQL);

		// 定义返回值,类型List<TaskSummary>
		List<TaskSummary> listtasks = new ArrayList<TaskSummary>();

		try {
			System.out.println("try:jdbcTemplate.query");
			RowMapper<TaskSummary> rowMapper = new BeanPropertyRowMapper<TaskSummary>(TaskSummary.class);
			listtasks = jdbcTemplate.query(selectSQL, new Object[] { id }, rowMapper);
			System.out.println("listtasks:" + listtasks);
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

		System.out.println("listtasks.size():" + listtasks.size());
		// 判断是否存在子任务，存在则补充每个子任务的子任务数 
		if (listtasks.size() != 0) {
			// 定义查询子任务数的SQL句
			final String selectSQLcount = "select count(id) as subtasksize"
					+ " from tb_task where parent_task_id=? and status<>'D'";
			System.out.println("selectSQLcount:" + selectSQLcount);

			for (TaskSummary simlisttasks : listtasks) {
				System.out.println("simlisttask:" + simlisttasks);
				try {
					System.out.println("try:jdbcTemplate.query");
					simlisttasks.setSubtasksize(jdbcTemplate.queryForObject(selectSQLcount, new Object[] { simlisttasks.getId() }, java.lang.Long.class));
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
			}
		}

		transactionManager.commit(status);
		return listtasks;
	}

	// 查询一条任务
	@Override
	public Task selectSimTask(int id) {
		System.out.println("selectSimTask(int id)");
		System.out.println("id:" + id);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 查询语句
		final String selectSQL = "select * from tb_task where id=?";
		System.out.println("selectSQL:" + selectSQL);

		// 定义返回值,类型Task
		Task task = new Task();

		// spring提供了一个便利的RowMapper实现-----BeanPropertyRowMapper
		// 它可自动将一行数据映射到指定类的实例中，它首先将这个类实例化，然后通过名称匹配的方式，映射到属性中去。
		RowMapper<Task> rowMapper = new BeanPropertyRowMapper<Task>(Task.class);
		try {
			System.out.println("try:jdbcTemplate.queryForObject");
			// 调用queryForObject
			task = jdbcTemplate.queryForObject(selectSQL, new Object[] { id }, rowMapper);
			System.out.println("commit");
			transactionManager.commit(status);
		} catch (DataAccessException ex) {
			System.out.println("catch:jdbcTemplate.queryForObject");
			System.out.println("ex.getMessage:" + ex.getMessage());

			// 通过DataAccessException的类型判断sql产生的错误
			System.out.println("DataAccessException:" + ex.getClass());

			// EmptyResultDataAccessException：0数据
			if (ex instanceof EmptyResultDataAccessException) {
				System.out.println("EmptyResultDataAccessException");
				// throw ex;
				System.out.println("commit");
				transactionManager.commit(status);
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
		System.out.println("task:" + task);
		return task;
	}

	//插入一条任务
	@Override
	public Task insertTask(Task task) {
		System.out.println("insertTask(Task task)");
		System.out.println("task:" + task);

		Assert.notNull(task, "The task must not be null");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		final String insertSQL = "INSERT INTO tb_task (id,name,overview,description,owner_id,start_time,finish_time,"
				+ "progress,remark,status,project_id,parent_task_id) VALUES (0,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println("insertSQL:" + insertSQL);

		// KeyHolder:public abstract interface org.springframework.jdbc.support.KeyHolder
		// GeneratedKeyHolder:public class org.springframework.jdbc.support.GeneratedKeyHolder implements org.springframework.jdbc.support.KeyHolder
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			// jdbcTemplate.update:
			//     public int update(org.springframework.jdbc.core.PreparedStatementCreator psc,
			//                      org.springframework.jdbc.support.KeyHolder generatedKeyHolder)
            //                      throws org.springframework.dao.DataAccessException;
				// PreparedStatementCreator:public abstract interface org.springframework.jdbc.core.PreparedStatementCreator
			jdbcTemplate.update(new PreparedStatementCreator() {
				// createPreparedStatement:public abstract java.sql.PreparedStatement createPreparedStatement(java.sql.Connection arg0) throws java.sql.SQLException;
					// Connection:public abstract interface java.sql.Connection extends java.sql.Wrapper, java.lang.AutoCloseable
					// PreparedStatement:public abstract interface java.sql.PreparedStatement extends java.sql.Statement
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(insertSQL, new String[] { "id" });
					ps.setString(1, task.getName()); 
					ps.setString(2, task.getOverview());
					ps.setString(3, task.getDescription());
					ps.setInt(4, task.getOwner_id());
					ps.setString(5, task.getStart_time());
					ps.setString(6, task.getFinish_time());
					ps.setFloat(7, task.getProgress());
					ps.setString(8, task.getRemark());
					ps.setString(9, task.getStatus());
					ps.setInt(10, task.getProject_id());
					ps.setInt(11, task.getParent_task_id());
					return ps;
				}
			}, keyHolder);
			// commit
			System.out.println("commit");
			transactionManager.commit(status);

			// 录入成功的同时，获取自增主键（任务id）的值
			keyHolder.getKey().intValue();
			System.out.println("keyHolder.getKey().intValue():" + keyHolder.getKey().intValue());
			task.setId(keyHolder.getKey().intValue());
			task.setStatus("P");

			System.out.println("task:" + task);
			return task;
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
		
	}

	// 更新一条任务
	@Override
	public Task updateTask(Task task) {
		System.out.println("updateTask(Task task)");
		System.out.println("task:" + task);

		Assert.notNull(task, "The task must not be null");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 更新语句
		final String updateSQL = "update tb_task set name=?,overview=?,description=?,owner_id=?,start_time=?,"
				+ "finish_time=?,progress=?,remark=?,status=?,parent_task_id=? where id = ?";
		System.out.println("updateSQL:"+updateSQL);

		// 调用update
		try {
			System.out.println("try:jdbcTemplate.update");
			jdbcTemplate.update(updateSQL,
	                new Object[]{task.getName(),
               		     		 task.getOverview(),
	                		     task.getDescription(),
	                		     task.getOwner_id(),
	                		     task.getStart_time(),
	                		     task.getFinish_time(),
	                		     task.getProgress(),
	                		     task.getRemark(),
	                		     task.getStatus(),
	                		     task.getParent_task_id(),
	                		     task.getId()});
			System.out.println("commit");
			transactionManager.commit(status);
			return task;
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
	}

// 方法1：以下是使用namedParameterJdbcTemplate解决where条件中的in查询方式
	// 删除任务（任务及子任务的状态改为D-删除）
	@Override
	public boolean deleteTask(int id) {
		System.out.println("deleteTask(int id)");
		System.out.println("id:" + id);

		// 待删除任务id转换成List<SubtaskId>类型parenttaskidlist
		SubtaskId parenttaskid = new SubtaskId();
		parenttaskid.setId(id);
		List<SubtaskId> parenttaskidlist = new ArrayList<SubtaskId>();
		parenttaskidlist.add(parenttaskid);
		System.out.println("parenttaskidlist:" + parenttaskidlist);

/*		// 方法a：使用循环的方式查询子任务并修改状态
		// 定义returntaskidlist运用于存储返回的子任务id，并作为下次查询的父任务id
		List<SubtaskId> returntaskidlist = new ArrayList<SubtaskId>();
		int returntaskidquantity = 0;
		System.out.println("returntaskidquantity:" + returntaskidquantity);

		do{
			// 更新任务id是parenttaskidlist的任务状态为D
			updateTaskStatusToDelete(parenttaskidlist);
			// 获取parenttaskidlist的子任务号，存入returntaskidlist
			returntaskidlist = selectSubtaskId(parenttaskidlist);
			System.out.println("returntaskidlist:" + returntaskidlist);
			// 获取子任务号returntaskidlist的数量
			returntaskidquantity = returntaskidlist.size();
			System.out.println("returntaskidquantity:" + returntaskidquantity);
			// 将returntaskidlist作为父任务号parenttaskidlist
			parenttaskidlist = returntaskidlist;
			System.out.println("parenttaskidlist:" + parenttaskidlist);
		}while(returntaskidquantity != 0); // 没有子任务则停止循环*/

		// 方法b：使用递归的方式查询子任务并修改状态
		selectSubtaskIdRecursive(parenttaskidlist);

		return true;

/*		// 删除语句
		final String deletetSQL = "delete from tb_task where id = ?";
		System.out.println("deletetSQL:" + deletetSQL);

		try {
			System.out.println("try:jdbcTemplate.update");
			jdbcTemplate.update(deletetSQL, new Object[] { id }, new int[] { java.sql.Types.INTEGER });
			System.out.println("commit");
			transactionManager.commit(status);
			return true;
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
		}*/

	}

	// 更新任务状态为D-已删除
	private void updateTaskStatusToDelete(List<SubtaskId> taskid) {
		System.out.println("\nselectSubtaskId(List<SubtaskId> taskid)");
		System.out.println("taskid:" + taskid);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

	    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	    // SQL句，使用变量tid
		String selectSQL = "update tb_task set status='D' where id in (:tid)";
		System.out.println("selectSQL:" + selectSQL);
		// parameters：添加name：tid及对应的value
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    List<Integer> tidlist = new ArrayList<Integer>();
	    for(SubtaskId simtaskid:taskid){
	    	tidlist.add(simtaskid.getId());
	    }
	    parameters.addValue("tid", tidlist);

		try {
			System.out.println("try:jdbcTemplate.query");

			namedParameterJdbcTemplate.update(selectSQL, parameters);

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
	}

	// 查询子任务id
	private List<SubtaskId> selectSubtaskId(List<SubtaskId> parenttaskid) {
		System.out.println("selectSubtaskId(List<SubtaskId> parenttaskid)");
		System.out.println("parenttaskid:" + parenttaskid);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	    // SQL句，使用变量:ptid
		String selectSQL = "select id from tb_task where status<>'D' and parent_task_id in (:ptid)";
		System.out.println("selectSQL:" + selectSQL);
		// parameters：添加name：ptid及对应的value
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    List<Integer> ptidlist = new ArrayList<Integer>();
	    for(SubtaskId simparenttaskid:parenttaskid){
	    	ptidlist.add(simparenttaskid.getId());
	    }
	    parameters.addValue("ptid", ptidlist);

		// 子任务list
		List<SubtaskId> subtaskidlist = new ArrayList<SubtaskId>();

		try {
			System.out.println("try:jdbcTemplate.query");
			RowMapper<SubtaskId> rowMapper = new BeanPropertyRowMapper<SubtaskId>(SubtaskId.class);
			subtaskidlist = namedParameterJdbcTemplate.query(selectSQL, parameters, rowMapper);
			System.out.println("subtaskidlist:" + subtaskidlist);
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

		return subtaskidlist;
	}

	// 修改任务状态并查询子任务id（递归）
	private List<SubtaskId> selectSubtaskIdRecursive(List<SubtaskId> parenttaskid) {
		System.out.println("\nselectSubtaskIdRecursive(List<SubtaskId> parenttaskid)");
		System.out.println("parenttaskid:" + parenttaskid);

		// 修改任务状态为D-删除
		updateTaskStatusToDelete(parenttaskid);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

	    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	    // SQL句，使用变量:ptid
		String selectSQL = "select id from tb_task where status<>'D' and parent_task_id in (:ptid)";
		System.out.println("selectSQL:" + selectSQL);
		// parameters：添加name：ptid及对应的value
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    List<Integer> ptidlist = new ArrayList<Integer>();
	    for(SubtaskId simparenttaskid:parenttaskid){
	    	ptidlist.add(simparenttaskid.getId());
	    }
	    parameters.addValue("ptid", ptidlist);

		// 子任务list
		List<SubtaskId> subtaskidlist = new ArrayList<SubtaskId>();

		try {
			System.out.println("try:jdbcTemplate.query");
			RowMapper<SubtaskId> rowMapper = new BeanPropertyRowMapper<SubtaskId>(SubtaskId.class);
			subtaskidlist = namedParameterJdbcTemplate.query(selectSQL, parameters, rowMapper);
			System.out.println("subtaskidlist:" + subtaskidlist);
			System.out.println("commit");
			transactionManager.commit(status);

			System.out.println("subtaskidlist.size():" + subtaskidlist.size());
			// 递归条件
			if(subtaskidlist.size() != 0){
				// 递归
				selectSubtaskIdRecursive(subtaskidlist);
			}
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

		return subtaskidlist;
	}

// 方法2：以下是使用字符串拼接查询条件的方式实现where条件中的in查询方式
/*	// 删除任务（任务及子任务的状态改为D-删除）
	@Override
	public boolean deleteTask(int id) {
		System.out.println("deleteTask(int id)");
		System.out.println("id:" + id);

		// 待删除任务id转换成List<SubtaskId>类型parenttaskidlist
		SubtaskId parenttaskid = new SubtaskId();
		parenttaskid.setId(id);
		List<SubtaskId> parenttaskidlist = new ArrayList<SubtaskId>();
		parenttaskidlist.add(parenttaskid);
		System.out.println("parenttaskidlist:" + parenttaskidlist);

		// 方法a：使用循环的方式查询子任务并修改状态
		// 定义returntaskidlist运用于存储返回的子任务id，并作为下次查询的父任务id
		List<SubtaskId> returntaskidlist = new ArrayList<SubtaskId>();
		int returntaskidquantity = 0;
		System.out.println("returntaskidquantity:" + returntaskidquantity);

		do{
			// 更新任务id是parenttaskidlist的任务状态为D
			updateTaskStatusToDelete(parenttaskidlist);
			// 获取parenttaskidlist的子任务号，存入returntaskidlist
			returntaskidlist = selectSubtaskId(parenttaskidlist);
			System.out.println("returntaskidlist:" + returntaskidlist);
			// 获取子任务号returntaskidlist的数量
			returntaskidquantity = returntaskidlist.size();
			System.out.println("returntaskidquantity:" + returntaskidquantity);
			// 将returntaskidlist作为父任务号parenttaskidlist
			parenttaskidlist = returntaskidlist;
			System.out.println("parenttaskidlist:" + parenttaskidlist);
		}while(returntaskidquantity != 0); // 没有子任务则停止循环

		// 方法b：使用递归的方式查询子任务并修改状态
		selectSubtaskIdRecursive(parenttaskidlist);

		return true;

		// 删除语句
		final String deletetSQL = "delete from tb_task where id = ?";
		System.out.println("deletetSQL:" + deletetSQL);

		try {
			System.out.println("try:jdbcTemplate.update");
			jdbcTemplate.update(deletetSQL, new Object[] { id }, new int[] { java.sql.Types.INTEGER });
			System.out.println("commit");
			transactionManager.commit(status);
			return true;
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

	}

	// 更新任务状态为D-已删除
	private void updateTaskStatusToDelete(List<SubtaskId> taskid) {
		System.out.println("\nselectSubtaskId(List<SubtaskId> taskid)");
		System.out.println("taskid:" + taskid);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 拼接SQL句
		StringBuffer selectSQLbuf= new StringBuffer("update tb_task set status='D' where id in ("); 
		for (int i=0; i< taskid.size(); i++) { 
		  if (i!=0) selectSQLbuf.append(", "); 
		  selectSQLbuf.append("?"); 
		} 
		selectSQLbuf.append(")"); 
		System.out.println("selectSQLbuf:" + selectSQLbuf.toString());

		try {
			System.out.println("try:jdbcTemplate.query");
			jdbcTemplate.update(selectSQLbuf.toString(), new PreparedStatementSetter() {
				public void setValues(PreparedStatement ps) throws SQLException {
					for(int n=0,m=taskid.size();n<m;n++){
						ps.setInt(n+1, taskid.get(n).getId());
					}
				}
			});
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
	}

	// 查询子任务id
	private List<SubtaskId> selectSubtaskId(List<SubtaskId> parenttaskid) {
		System.out.println("selectSubtaskId(List<SubtaskId> parenttaskid)");
		System.out.println("parenttaskid:" + parenttaskid);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 拼接SQL句
		StringBuffer selectSQLbuf= new StringBuffer("select id from tb_task where status<>'D' and parent_task_id in ("); 
		for (int i=0; i< parenttaskid.size(); i++) { 
		  if (i!=0) selectSQLbuf.append(", "); 
		  selectSQLbuf.append("?"); 
		} 
		selectSQLbuf.append(")"); 
		System.out.println("selectSQLbuf:" + selectSQLbuf.toString());

		// 子任务list
		List<SubtaskId> subtaskidlist = new ArrayList<SubtaskId>();

		try {
			System.out.println("try:jdbcTemplate.query");
			RowMapper<SubtaskId> rowMapper = new BeanPropertyRowMapper<SubtaskId>(SubtaskId.class);
			subtaskidlist = jdbcTemplate.query(selectSQLbuf.toString(), new PreparedStatementSetter() {
				public void setValues(PreparedStatement ps) throws SQLException {
					for(int n=0,m=parenttaskid.size();n<m;n++){
						ps.setInt(n+1, parenttaskid.get(n).getId());
					}
				}
			}, rowMapper);
			System.out.println("subtaskidlist:" + subtaskidlist);
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

		return subtaskidlist;
	}

	// 修改任务状态并查询子任务id（递归）
	private List<SubtaskId> selectSubtaskIdRecursive(List<SubtaskId> parenttaskid) {
		System.out.println("\nselectSubtaskIdRecursive(List<SubtaskId> parenttaskid)");
		System.out.println("parenttaskid:" + parenttaskid);

		// 修改任务状态为D-删除
		updateTaskStatusToDelete(parenttaskid);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 拼接SQL句
		StringBuffer selectSQLbuf= new StringBuffer("select id from tb_task where status<>'D' and parent_task_id in ("); 
		for (int i=0; i< parenttaskid.size(); i++) { 
		  if (i!=0) selectSQLbuf.append(", "); 
		  selectSQLbuf.append("?"); 
		} 
		selectSQLbuf.append(")"); 
		System.out.println("selectSQLbuf:" + selectSQLbuf.toString());

		// 子任务list
		List<SubtaskId> subtaskidlist = new ArrayList<SubtaskId>();

		try {
			System.out.println("try:jdbcTemplate.query");
			RowMapper<SubtaskId> rowMapper = new BeanPropertyRowMapper<SubtaskId>(SubtaskId.class);
			subtaskidlist = jdbcTemplate.query(selectSQLbuf.toString(), new PreparedStatementSetter() {
				public void setValues(PreparedStatement ps) throws SQLException {
					for(int n=0,m=parenttaskid.size();n<m;n++){
						ps.setInt(n+1, parenttaskid.get(n).getId());
					}
				}
			}, rowMapper);
			System.out.println("subtaskidlist:" + subtaskidlist);
			System.out.println("commit");
			transactionManager.commit(status);

			System.out.println("subtaskidlist.size():" + subtaskidlist.size());
			// 递归条件
			if(subtaskidlist.size() != 0){
				// 递归
				selectSubtaskIdRecursive(subtaskidlist);
			}
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

		return subtaskidlist;
	}*/

	// 查询多条（按遍历排序）
	@Override
	public List<TaskSummary> selectMulTaskSort(int project_id) {
		System.out.println("selectMulTaskSort(int project_id)");
		System.out.println("project_id:" + project_id);

		// 定义返回值,类型List<TaskSummary>
		List<TaskSummary> listtasks = new ArrayList<TaskSummary>();

		long level = 0;
		List<TaskSummary> maintasklist = new ArrayList<TaskSummary>();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		final String selectSQL = "select id,name,overview,owner_id,progress,status,parent_task_id from tb_task"
				+" where project_id=? and status<>'D' and parent_task_id=0  order by parent_task_id asc";
		System.out.println("selectSQL:" + selectSQL);

		RowMapper<TaskSummary> rowMapper = new BeanPropertyRowMapper<TaskSummary>(TaskSummary.class);
		try {
			System.out.println("try:jdbcTemplate.queryForObject");
			// 调用queryForObject
			maintasklist = jdbcTemplate.query(selectSQL, new Object[] { project_id }, rowMapper);
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
			System.out.println("finally:jdbcTemplate.queryForObject");
		}
		for(TaskSummary simmaintasklist:maintasklist)
			simmaintasklist.setSubtasksize(level);

		listtasks = selectSubtaskAllSort(maintasklist, project_id, listtasks, level);

		return listtasks;
	}

	// 查询所有子任务
	private List<TaskSummary> selectSubtaskAllSort(List<TaskSummary> parenttasksummarylist, int project_id, List<TaskSummary> retasklist, long level) {
		System.out.println("selectSubtaskAllSort(List<TaskSummary> parenttasksummarylist, int project_id)");
		System.out.println("parenttasksummarylist:" + parenttasksummarylist);
		System.out.println("project_id:" + project_id);
		System.out.println("retasklist:" + retasklist);

		if(parenttasksummarylist.size() == 0){
			return retasklist;
		} else {
			level++;
			for(TaskSummary simparenttasksummarylist : parenttasksummarylist){
				simparenttasksummarylist.setSubtasksize(level);
				retasklist.add(simparenttasksummarylist);

				List<TaskSummary> simtasklist = new ArrayList<TaskSummary>();

				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				TransactionStatus status = transactionManager.getTransaction(def);

				final String selectSQL = "select id,name,overview,owner_id,progress,status,parent_task_id from tb_task"
						+" where project_id=? and status<>'D' and parent_task_id=?  order by parent_task_id asc";
				System.out.println("selectSQL:" + selectSQL);

				RowMapper<TaskSummary> rowMapper = new BeanPropertyRowMapper<TaskSummary>(TaskSummary.class);
				try {
					System.out.println("try:jdbcTemplate.queryForObject");
					// 调用queryForObject
					simtasklist = jdbcTemplate.query(selectSQL, new Object[] { project_id,simparenttasksummarylist.getId() }, rowMapper);
					System.out.println("commit");
					transactionManager.commit(status);
					retasklist = selectSubtaskAllSort(simtasklist, project_id, retasklist, level);
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
					System.out.println("finally:jdbcTemplate.queryForObject");
				}
			}
			return retasklist;
		}
	}

}