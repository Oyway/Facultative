package ua.svinkov.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import ua.svinkov.model.entity.Course;
import ua.svinkov.model.entity.User;
import ua.svinkov.model.entity.UserCourses;

public class UserCoursesMapper implements ObjectMapper<UserCourses> {

	@Override
	public UserCourses extractFromResultSet(ResultSet rs) throws SQLException {
		UserCourses userCourses = new UserCourses();
		UserMapper mapper = new UserMapper();
		Course course = Course.builder()
				.courseid(rs.getInt("courseid"))
				.course(rs.getString("course"))
				.topic(new TopicMapper().extractFromResultSet(rs))
				.teacher(User.builder().userid(rs.getInt("teacherid")).build())
				.dateStart(rs.getDate("date_start").toLocalDate())
				.dateEnd(rs.getDate("date_stop").toLocalDate())
				.description(rs.getString("descr"))
				.build();
		userCourses.setUser(mapper.extractFromResultSet(rs));
		userCourses.setCourse(course);
		userCourses.setMark(rs.getInt("mark"));
		return userCourses;
	}

	@Override
	public UserCourses makeUnique(Map<Integer, UserCourses> cache, UserCourses userCourses) {
		cache.putIfAbsent(userCourses.getUser().getUserid(), userCourses);
		return cache.get(userCourses.getUser().getUserid());
	}
}
