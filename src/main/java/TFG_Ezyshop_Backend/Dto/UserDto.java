package TFG_Ezyshop_Backend.Dto;

import TFG_Ezyshop_Backend.Entity.User;
import lombok.Data;

@Data
public class UserDto {

	private Long id;

	private String username;

	private String password;

	private String email;

	private Boolean locked;

	private Boolean disabled;

	private String name;

	private String lastname;

	private String zip;

	private String street;

	private String number;

	private String description;
	
	

	public UserDto(User user) {
		this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.locked = user.getLocked();
        this.disabled = user.getDisabled();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.zip = user.getZip();
        this.street = user.getStreet();
        this.number = user.getNumber();
        this.description = user.getDescription();
	}
	
	

}
