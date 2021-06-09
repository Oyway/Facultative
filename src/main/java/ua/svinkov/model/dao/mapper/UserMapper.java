package ua.svinkov.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.enums.Role;

public class UserMapper implements ObjectMapper<User> {

	@Override
	public User extractFromResultSet(ResultSet rs) throws SQLException {
		User user = User.builder().userid(rs.getInt("userid")).login(rs.getString("login"))
					.password(rs.getString("pass")).email(rs.getString("email")).firstname(rs.getString("firstname"))
					.surname(rs.getString("surname")).role(Role.valueOf(rs.getString("role").toUpperCase())).status(rs.getBoolean("status")).build();
		return user;
	}

	@Override
	public User makeUnique(Map<Integer, User> cache, User user) {
		cache.putIfAbsent(user.getUserid(), user);
		return cache.get(user.getUserid());
	}

}
