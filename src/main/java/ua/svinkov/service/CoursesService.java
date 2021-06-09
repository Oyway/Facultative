package ua.svinkov.service;

import java.util.ArrayList;
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

public class CoursesService {
	DaoFactory factory = JDBCDaoFactory.getInstance();

	public List<Course> findAll() {
		List<Course> courses = new ArrayList<>();
		try(JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()){
			courses = dao.findAll();
		}
		return courses;
	}

	public void updateMark(int studentId, int courseId, int mark) {
		try (JDBCUserCoursesDao dao = (JDBCUserCoursesDao) factory.createUserCoursesDao()) {
			UserCourses userCourses = new UserCourses();
			userCourses.setCourse(Course.builder().courseid(courseId).build());
			userCourses.setUser(User.builder().userid(studentId).build());
			userCourses.setMark(mark);
			dao.update(userCourses);
		}
	}
	//TODO: check 
	public List<UserCourses> findUserCourses(User user) {
		List<UserCourses> courses = new ArrayList<>();
		try (JDBCUserCoursesDao dao = (JDBCUserCoursesDao) factory.createUserCoursesDao()) {
			courses = dao.findAllById(user.getUserid());
		}
		return courses;
	}

	public List<Topic> findAllTopics() {
		List<Topic> topics = new ArrayList<>();
		try (JDBCTopicDao dao = (JDBCTopicDao) factory.createTopicDao()) {
			topics = dao.findAll();
		}
		return topics;
	}

	public void createCourse(Course course) {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			dao.create(course);
		}
	}
	
	public void deleteCourse(int id) {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			dao.delete(id);
		}
	}
	
	public Course findCourseById(int id) {
		Course courses = null;
		try(JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()){
			courses = dao.findById(id);
		}
		return courses;
	}
	
	public void updateCourse(Course course) {
		try (JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()) {
			dao.update(course);
		}
	}
	
	public List<Integer> findCoursesSortedByCountSt(){
		List<Integer> ids = new ArrayList<>();
		try(JDBCCourseDao dao = (JDBCCourseDao) factory.createCourseDao()){
			ids = dao.findCoursesIdSortBySt();
		}
		return ids;
	}

}
