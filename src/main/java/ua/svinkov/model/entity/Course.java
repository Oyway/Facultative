package ua.svinkov.model.entity;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Course {
	
	private int courseid;
	private String course;
	private Topic topic;
	private User teacher;
	private LocalDate dateStart;
	private LocalDate dateEnd;
	private String description;
}
