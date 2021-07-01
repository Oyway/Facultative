package ua.svinkov.model.entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.svinkov.model.entity.enums.Role;

/**
 * User entity
 * 
 * @author R.Svinkov
 *
 */
@Getter
@Setter
@ToString
@Builder
public class User implements Serializable {

	private static final long serialVersionUID = 3029863271984170488L;

	private Long userid;
	private String login;
	private String password;
	private String email;
	private String firstName;
	private String surname;
	private Role role;
	private boolean status;
}
