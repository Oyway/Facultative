package ua.svinkov.model.entity;

import java.io.Serializable;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Course entity
 * 
 * @author R.Svinkov
 *
 */
@Getter
@Setter
@ToString
@Builder
public class Course implements Serializable {

	private static final long serialVersionUID = -1334870106776022108L;

	private Long courseid;
	private String course;
	private Topic topic;
	private User teacher;
	private LocalDate dateStart;
	private LocalDate dateEnd;
	private String description;
}
