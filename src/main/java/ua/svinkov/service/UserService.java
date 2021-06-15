package ua.svinkov.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ua.svinkov.model.dao.DaoFactory;
import ua.svinkov.model.dao.impl.JDBCDaoFactory;
import ua.svinkov.model.dao.impl.JDBCUserDao;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.enums.Role;

/**
 * User service for work with db
 * 
 * @author R.Svinkov
 *
 */
public class UserService {

	private DaoFactory factory = JDBCDaoFactory.getInstance();

	/**
	 * Find user entity by login
	 * 
	 * @param login login of user
	 * @return user entity
	 */
	public User findByLogin(String login) {
		User user = null;
		try (JDBCUserDao dao = (JDBCUserDao) factory.createUserDao()) {
			user = dao.findByLogin(login);
		}
		return user;
	}

	/**
	 * Find user entity by given id
	 * 
	 * @param id id of user
	 * @return user entity
	 */
	public User findById(int id) {
		User user = null;
		try (JDBCUserDao dao = (JDBCUserDao) factory.createUserDao()) {
			user = dao.findById(id);
		}
		return user;
	}

	/**
	 * Create new user raw in db
	 * 
	 * @param user user entity
	 */
	public void createUser(User user) {
		factory.createUserDao().create(user);
	}

	/**
	 * Update user data
	 * 
	 * @param user user entity
	 */
	public void updateUser(User user) {
		try (JDBCUserDao dao = (JDBCUserDao) factory.createUserDao()) {
			dao.update(user);
		}
	}

	/**
	 * Find all teachers in users table
	 * 
	 * @return list of users with teacher role
	 */
	public List<User> findAllTeachers() {
		List<User> teachers = new ArrayList<>();
		try (JDBCUserDao dao = (JDBCUserDao) factory.createUserDao()) {
			teachers = dao.findAll().stream().filter(t -> t.getRole().equals(Role.TEACHER))
					.collect(Collectors.toList());
		}
		return teachers;
	}

	/**
	 * Find all existed users
	 * 
	 * @return list of users
	 */
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		try (JDBCUserDao dao = (JDBCUserDao) factory.createUserDao()) {
			users = dao.findAll();
		}
		return users;
	}

}
