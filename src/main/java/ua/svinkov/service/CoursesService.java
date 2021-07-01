package ua.svinkov.service;

import java.util.List;

import ua.svinkov.model.dao.DaoFactory;
import ua.svinkov.model.dao.impl.JDBCCourseDao;
import ua.svinkov.model.dao.impl.JDBCDaoFactory;
import ua.svinkov.model.dao.impl.JDBCTopicDao;
import ua.svinkov.model.dao.impl.JDBCUserCoursesDao;
import ua.svinkov.model.entity.Course;
import ua.svinkov.model.entity.Topic;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.UserCourses;

/**
 * Course service
 * 
 * @author R.Svinkov
 *
 */
public class CoursesService {
	DaoFactory factory = JDBCDaoFactory.getInstance();

	/**
	 * Find all courses in table
	 * 
	 * @return list of courses
	 */
	public List<Course> findAll() {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			return dao.findAll();
		}
	}

	/**
	 * Find all courses in table with pagination
	 * 
	 * @return list of courses
	 */
	public List<Course> findAllLimit(int offset, int limit) {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			return dao.findAll(offset, limit);
		}
	}

	/**
	 * Find all courses count
	 * 
	 * @return list of courses
	 */
	public Integer findAllCount() {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			return dao.findRowsCount();
		}
	}

	/**
	 * Set new mark for student on defined course
	 * 
	 * @param studentId student id
	 * @param courseId  course id
	 * @param mark      mark that teacher set in journal
	 */
	public void updateMark(Long studentId, Long courseId, int mark) {
		UserCourses userCourses = new UserCourses();
		userCourses.setCourse(Course.builder().courseid(courseId).build());
		userCourses.setUser(User.builder().userid(studentId).build());
		userCourses.setMark(mark);
		try (JDBCUserCoursesDao dao = (JDBCUserCoursesDao) factory.createUserCoursesDao()) {
			dao.update(userCourses);
		}
	}

	/**
	 * Find all courses for defined user
	 * 
	 * @param user User entity that needs courses
	 * @return list of courses for user
	 */
	public List<UserCourses> findUserCourses(User user) {
		try (JDBCUserCoursesDao dao = (JDBCUserCoursesDao) factory.createUserCoursesDao()) {
			return dao.findAllById(user.getUserid());
		}
	}

	/**
	 * Find all topic entitys from db
	 * 
	 * @return list of topics
	 */
	public List<Topic> findAllTopics() {
		try (JDBCTopicDao dao = (JDBCTopicDao) factory.createTopicDao()) {
			return dao.findAll();
		}
	}

	/**
	 * Add new course to course table in db
	 * 
	 * @param course Course entity that admin add to db
	 */
	public void createCourse(Course course) {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			dao.create(course);
		}
	}

	/**
	 * Delete course by its id
	 * 
	 * @param id id of course entity that will be deleted
	 */
	public void deleteCourse(Long id) {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			dao.delete(id);
		}
	}

	/**
	 * Found course entity by its id
	 * 
	 * @param id id of course entity
	 * @return course entity
	 */
	public Course findCourseById(Long id) {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			return dao.findById(id);
		}
	}

	/**
	 * Update course entity in db with the same id as given entity
	 * 
	 * @param course updated course entity
	 */
	public void updateCourse(Course course) {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			dao.update(course);
		}
	}

	/**
	 * Find courses of teacher sorted by student count
	 * 
	 * @param teacherid teacher id in course entity
	 * @return list of courses id
	 */
	public List<Long> findCoursesSortedByCountSt(Long teacherid) {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			return dao.findCoursesIdSortBySt(teacherid);
		}
	}

	/**
	 * Find all UserCourses entity for given teacher
	 * 
	 * @param id teacher id for courses
	 * @return list of UserCourses entity
	 */
	public List<UserCourses> findAllByTeacherId(Long id) {
		try (JDBCUserCoursesDao dao = (JDBCUserCoursesDao) factory.createUserCoursesDao()) {
			return dao.findAllByTeacherId(id);
		}
	}

}
