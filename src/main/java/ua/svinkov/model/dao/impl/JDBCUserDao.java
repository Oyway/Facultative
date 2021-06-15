package ua.svinkov.model.dao.impl;

import ua.svinkov.constants.SqlConstants;
import ua.svinkov.model.dao.UserDao;
import ua.svinkov.model.dao.mapper.UserMapper;
import ua.svinkov.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for user entity
 * 
 * @author R.Svinkov
 *
 */
public class JDBCUserDao implements UserDao {
	private Connection connection;

	public JDBCUserDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(User entity) {
		try (PreparedStatement pstmt = connection.prepareStatement(SqlConstants.INSERT_USER)) {
			pstmt.setString(1, entity.getLogin());
			pstmt.setString(2, entity.getPassword());
			pstmt.setString(3, entity.getEmail());
			pstmt.setString(4, entity.getFirstName());
			pstmt.setString(5, entity.getSurname());
			pstmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User findById(int id) {
		User user = null;
		ResultSet rs = null;
		try (PreparedStatement st = connection.prepareStatement(SqlConstants.FIND_USER_BY_ID)) {
			UserMapper mapper = new UserMapper();
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next())
				user = mapper.extractFromResultSet(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return user;
	}

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery(SqlConstants.FIND_ALL_USERS);
			UserMapper usersMapper = new UserMapper();
			while (rs.next()) {
				users.add(usersMapper.extractFromResultSet(rs));
			}
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return users;
	}

	@Override
	public void update(User entity) {
		try (PreparedStatement st = connection.prepareStatement(SqlConstants.UPDATE_USER)) {
			st.setString(1, entity.getLogin());
			st.setString(2, entity.getPassword());
			st.setString(3, entity.getEmail());
			st.setString(4, entity.getFirstName());
			st.setString(5, entity.getSurname());
			st.setString(6, entity.getRole().toString().toLowerCase());
			st.setBoolean(7, entity.isStatus());
			st.setInt(8, entity.getUserid());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(int id) {

	}

	/**
	 * Returns a user with the given login.
	 *
	 * @param login User login.
	 * @return User entity.
	 */
	public User findByLogin(String login) {
		User user = null;
		ResultSet rs = null;
		try (PreparedStatement st = connection.prepareStatement(SqlConstants.FIND_USER_BY_LOGIN)) {
			UserMapper mapper = new UserMapper();
			st.setString(1, login);
			rs = st.executeQuery();
			if (rs.next())
				user = mapper.extractFromResultSet(rs);
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return user;
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
