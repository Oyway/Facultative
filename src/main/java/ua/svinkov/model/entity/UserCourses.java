package ua.svinkov.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserCourses {
	private User user;
	private Course course;
	private int mark;
}
