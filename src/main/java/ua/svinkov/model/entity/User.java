package ua.svinkov.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.svinkov.model.entity.enums.Role;

@Getter
@Setter
@ToString
@Builder
public class User {
	private int userid;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String surname;
    private Role role;
    private boolean status;
}
