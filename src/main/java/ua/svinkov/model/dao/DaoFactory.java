package ua.svinkov.model.dao;

import ua.svinkov.model.dao.impl.JDBCDaoFactory;

/**
 * Singleton for creating dao entities 
 * 
 * @author R.Svinkov
 *
 */
public abstract class DaoFactory {
	private static DaoFactory daoFactory;

	public abstract TopicDao createTopicDao();

	public abstract UserDao createUserDao();

	public abstract CourseDao createCourseDao();

	public abstract UserCoursesDao createUserCoursesDao();

	public static DaoFactory getInstance() {
		if (daoFactory == null) {
			synchronized (DaoFactory.class) {
				if (daoFactory == null) {
					DaoFactory temp = new JDBCDaoFactory();
					daoFactory = temp;
				}
			}
		}
		return daoFactory;
	}
}
