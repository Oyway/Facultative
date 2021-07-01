package ua.svinkov.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.svinkov.model.dao.impl.ConnectionPoolHolder;
import ua.svinkov.model.entity.Course;
import ua.svinkov.model.entity.Topic;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.UserCourses;

class CoursesServiceTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/facultative_test?useSSL=false");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
		ConnectionPoolHolder.setDataSource(ds);
	}

	@Test
	void testFindAll() {
		CoursesService us = new CoursesService();
		List<Course> curses = us.findAll();
		int expected = 2;
		assertEquals(expected, curses.size());
	}

	@Test
	void testUpdateMark() {
		User user = new UserService().findById(1L);
		CoursesService us = new CoursesService();
		us.updateMark(1L, 3L, 20);
		List<UserCourses> userCourses = us.findUserCourses(user);
		userCourses.stream().filter(t -> t.getCourse().getCourseid() == 3);
		int expected = 20;
		assertEquals(expected, userCourses.get(0).getMark());
		us.updateMark(1L, 3L, 100);
	}

	@Test
	void testFindUserCourses() {
		User user = new UserService().findById(1L);
		CoursesService us = new CoursesService();
		List<UserCourses> userCourses = us.findUserCourses(user);
		int expected = 2;
		assertEquals(expected, userCourses.size());
	}

	@Test
	void testFindAllTopics() {
		CoursesService us = new CoursesService();
		List<Topic> topics = us.findAllTopics();
		int expected = 6;
		assertEquals(expected, topics.size());
	}

	@Test
	void testCreateCourse() {
		CoursesService us = new CoursesService();
		int countBefore = us.findAll().size();
		Topic topic = Topic.builder().topicId(7L).build();
		User teacher = new UserService().findById(2L);
		Course course = Course.builder().course("Test").topic(topic).teacher(teacher)
				.dateStart(LocalDate.now()).dateEnd(LocalDate.now().plusDays(10)).description("Test").build();
		us.createCourse(course);
		List<Course> courses = us.findAll();
		int countAfter = courses.size();
		assertEquals(countBefore+1, countAfter);
		us.deleteCourse(courses.get(courses.size()-1).getCourseid());
	}

	@Test
	void testFindCourseById() {
		CoursesService us = new CoursesService();
		Course course = us.findCourseById(3L);
		String expected = "Java Core";
		assertEquals(expected, course.getCourse());
	}

	@Test
	void testUpdateCourse() {
		CoursesService us = new CoursesService();
		Course course = us.findCourseById(3L);
		String descrBefore = course.getDescription();
		assertEquals("Java", descrBefore);
		course.setDescription("None");
		us.updateCourse(course);
		course = us.findCourseById(3L);
		assertEquals("None", course.getDescription());
		course.setDescription(descrBefore);
		us.updateCourse(course);
	}

	@Test
	void testFindAllByTeacherId() {
		CoursesService us = new CoursesService();
		List<UserCourses> course = us.findAllByTeacherId(2L);
		int expected = 1;
		assertEquals(expected, course.size());
	}

}
