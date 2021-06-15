package ua.svinkov.model.dao.impl;

import ua.svinkov.model.dao.CourseDao;
import ua.svinkov.model.dao.DaoFactory;
import ua.svinkov.model.dao.TopicDao;
import ua.svinkov.model.dao.UserCoursesDao;
import ua.svinkov.model.dao.UserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Realization of DaoFactory with database connection
 * 
 * @author R.Svinkov
 *
 */
public class JDBCDaoFactory extends DaoFactory {

	private DataSource dataSource = ConnectionPoolHolder.getDataSource();

	@Override
	public TopicDao createTopicDao() {
		return new JDBCTopicDao(getConnection());
	}

	@Override
	public UserDao createUserDao() {
		return new JDBCUserDao(getConnection());
	}

	@Override
	public CourseDao createCourseDao() {
		return new JDBCCourseDao(getConnection());
	}

	@Override
	public UserCoursesDao createUserCoursesDao() {
		return new JDBCUserCoursesDao(getConnection());
	}

	/**
	 * Returns a DB connection from the Pool Connections.
	 * 
	 * @return DB connection
	 */
	private Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
