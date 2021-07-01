package ua.svinkov.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.svinkov.model.dao.impl.ConnectionPoolHolder;
import ua.svinkov.model.entity.User;

class UserServiceTest {

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
	void testFindByLogin() {
		UserService us = new UserService();
		User user = us.findByLogin("user");
		String expectedEmail = "user@gmail.com";
		assertEquals(expectedEmail, user.getEmail());
	}

	@Test
	void testFindById() {
		UserService us = new UserService();
		User user = us.findById(1L);
		String expected = "user";
		assertEquals(expected, user.getLogin());
	}

	@Test
	void testUpdateUser() {
		UserService us = new UserService();
		User userBefore = us.findByLogin("user");
		userBefore.setStatus(false);
		us.updateUser(userBefore);
		User userAfter = us.findByLogin("user");
		assertEquals(userBefore.isStatus(), userAfter.isStatus());
		userAfter.setStatus(true);
		us.updateUser(userAfter);
	}

	@Test
	void testFindAllTeachers() {
		UserService us = new UserService();
		List<User> teachers = us.findAllTeachers();
		int expected = 2;
		assertEquals(expected, teachers.size());
	}

	@Test
	void testFindAll() {
		UserService us = new UserService();
		List<User> users = us.findAll();
		int expected = 6;
		assertEquals(expected, users.size());
	}

}
