package ua.svinkov.model.dao.mapper;

import ua.svinkov.model.entity.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CourseMapper implements ObjectMapper<Course> {

	@Override
	public Course extractFromResultSet(ResultSet rs) throws SQLException {
		Course course = Course.builder().courseid(rs.getLong("courseid")).course(rs.getString("course"))
				.topic(new TopicMapper().extractFromResultSet(rs)).teacher(new UserMapper().extractFromResultSet(rs))
				.dateStart(rs.getDate("date_start").toLocalDate()).dateEnd(rs.getDate("date_stop").toLocalDate())
				.description(rs.getString("descr")).build();
		return course;
	}

	@Override
	public Course makeUnique(Map<Long, Course> cache, Course course) {
		cache.putIfAbsent(course.getCourseid(), course);
		return cache.get(course.getCourseid());
	}
}
