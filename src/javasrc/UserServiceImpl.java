package javasrc;

import com.bean.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.*;
import org.springframework.util.Assert;

public class UserServiceImpl implements UserService{

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

	@Override
	// 查询用户
	public User selectUser(String name) {
		System.out.println("selectUser()");
		System.out.println("name:" + name);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// SQL句
		final String selectSQL = "select * from tb_user where name=?";
		System.out.println("selectSQL:" + selectSQL);

		// spring提供了一个便利的RowMapper实现-----BeanPropertyRowMapper
		// 它可自动将一行数据映射到指定类的实例中，它首先将这个类实例化，然后通过名称匹配的方式，映射到属性中去。
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = new User();
		try {
			System.out.println("try:jdbcTemplate.queryForObject");
			user = jdbcTemplate.queryForObject(selectSQL, new Object[] { name }, rowMapper);
			System.out.println("user:" + user.toString());
			//PrintSomething.printUser(user);
		} catch (DataAccessException ex) {
			System.out.println("catch:jdbcTemplate.queryForObject");
			System.out.println("ex.getMessage:" + ex.getMessage());

			// 通过DataAccessException的类型判断sql产生的错误
			System.out.println("DataAccessException:" + ex.getClass());

			// EmptyResultDataAccessException：0数据
			if (ex instanceof EmptyResultDataAccessException) {
				System.out.println("EmptyResultDataAccessException");
				return null;
				// IncorrectResultSizeDataAccessException：数据数量大于1
			} else if (ex instanceof IncorrectResultSizeDataAccessException) {
				System.out.println("IncorrectResultSizeDataAccessException");
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
				throw ex;
			}
		} finally {
			System.out.println("finally:jdbcTemplate.queryForObject");
			
			transactionManager.commit(status);
		}
		return user;
	}

	@Override
	// 插入用户
	public void insertUser(User user) {
		System.out.println("insertUser()");
		System.out.println("user:" + user.toString());
		//PrintSomething.printUser(user);

		Assert.notNull(user, "The user must not be null");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		final String insertSQL = "INSERT INTO tb_user (id,name,password) VALUES (?,?,?)";
		System.out.println("insertSQL:" + insertSQL);

		try {
			System.out.println("try:jdbcTemplate.update");
			jdbcTemplate.update(insertSQL, new Object[] { user.getId(), user.getName(), user.getPassword() });

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
	}
	
	@Override
	// 验证用户id是否存在
	public boolean selectId(int id) {
		System.out.println("selectId()");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// SQL句
		final String selectSQL = "select count(id) from tb_user where id=?";
		System.out.println("selectSQL:" + selectSQL);

		// 查询用户id是否存在
		int count = 0;
		count = jdbcTemplate.queryForObject(selectSQL, new Object[] { id }, java.lang.Integer.class);
		
		transactionManager.commit(status);
		System.out.println("count:" + count);
		/*
		try{
			System.out.println("try:jdbcTemplate.queryForObject");
			count = jdbcTemplate.queryForObject(selectSQL,new Object[] { id },java.lang.Long.class);
		}catch(DataAccessException ex){
			System.out.println("catch:jdbcTemplate.queryForObject");
			System.out.println("ex.getMessage:"+ex.getMessage());
			throw ex;
		}finally{
			System.out.println("finally:jdbcTemplate.queryForObject");
			System.out.println("status:"+status);
			transactionManager.commit(status);
		}
		*/
		// 判断用户id是否存在
		if (count == 1)
			return true;
		else
			return false;
	}
	
	@Override
	// 验证用户id是否存在
	public String selectName(int id) {
		System.out.println("selectName()");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// SQL句
		final String selectSQL = "select name from tb_user where id=?";
		System.out.println("selectSQL:" + selectSQL);

		// spring提供了一个便利的RowMapper实现-----BeanPropertyRowMapper
		// 它可自动将一行数据映射到指定类的实例中，它首先将这个类实例化，然后通过名称匹配的方式，映射到属性中去。
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = new User();
		try {
			System.out.println("try:jdbcTemplate.queryForObject");
			user = jdbcTemplate.queryForObject(selectSQL, new Object[] { id }, rowMapper);
			System.out.println("user:" + user.toString());
			//PrintSomething.printUser(user);
		} catch (DataAccessException ex) {
			System.out.println("catch:jdbcTemplate.queryForObject");
			System.out.println("ex.getMessage:" + ex.getMessage());

			// 通过DataAccessException的类型判断sql产生的错误
			System.out.println("DataAccessException:" + ex.getClass());

			// EmptyResultDataAccessException：0数据
			if (ex instanceof EmptyResultDataAccessException) {
				System.out.println("EmptyResultDataAccessException");
				return null;
				// IncorrectResultSizeDataAccessException：数据数量大于1
			} else if (ex instanceof IncorrectResultSizeDataAccessException) {
				System.out.println("IncorrectResultSizeDataAccessException");
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
				throw ex;
			}
		} finally {
			System.out.println("finally:jdbcTemplate.queryForObject");
			
			transactionManager.commit(status);
		}
		return user.getName();
	}

	//查询多条
	@Override
	public List<User> selectMulUser() {
		System.out.println("selectMulUser()");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		// 定义查询多条的SQL句
		// String selectSQL = "select * from tb_task";
		final String selectSQL = "select id,name from tb_user order by id asc";
		System.out.println("selectSQL:" + selectSQL);

		// 定义返回值,类型List<User>
		List<User> listusers = new ArrayList<User>();

		try {
			System.out.println("try:jdbcTemplate.query");
			RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
			listusers = jdbcTemplate.query(selectSQL, rowMapper);
			for (User simlistuser : listusers) {
				System.out.println("id:" + simlistuser.getId());
				System.out.println("name:" + simlistuser.getName());
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
			
			transactionManager.commit(status);
		}
		return listusers;
	}
}