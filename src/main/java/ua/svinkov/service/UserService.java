package ua.svinkov.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ua.svinkov.model.dao.DaoFactory;
import ua.svinkov.model.dao.impl.JDBCDaoFactory;
import ua.svinkov.model.dao.impl.JDBCUserDao;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.enums.Role;

public class UserService {
	
	private DaoFactory factory = JDBCDaoFactory.getInstance();
	
	public User findByLogin(String login) {
		User user = null;
		try(JDBCUserDao dao = (JDBCUserDao) factory.createUserDao()){
			user = dao.findByLogin(login);
		}
		return user;
	}
	
	public User findById(int id) {
		User user = null;
		try(JDBCUserDao dao = (JDBCUserDao) factory.createUserDao()){
			user = dao.findById(id);
		}
		return user;
	}
	
	public void createUser(User user) {
		factory.createUserDao().create(user);
	}
	
	public void updateUser(User user) {
		try(JDBCUserDao dao = (JDBCUserDao) factory.createUserDao()){
			dao.update(user);
		}
	}
	
	public List<User> findAllTeachers(){
		List<User> teachers = new ArrayList<>();
		try(JDBCUserDao dao = (JDBCUserDao) factory.createUserDao()){
			teachers = dao.findAll().stream().filter(t -> t.getRole().equals(Role.TEACHER)).collect(Collectors.toList());
		}
		return teachers;
	}
	
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		try(JDBCUserDao dao = (JDBCUserDao) factory.createUserDao()){
			users = dao.findAll();
		}
		return users;
	}

}
