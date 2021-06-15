package ua.svinkov.model.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * UserCourses entity
 * 
 * @author R.Svinkov
 *
 */
@Getter
@Setter
@ToString
public class UserCourses implements Serializable {

	private static final long serialVersionUID = 6299005808254740364L;

	private User user;
	private Course course;
	private int mark;
}
