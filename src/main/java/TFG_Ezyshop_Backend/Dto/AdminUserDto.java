package TFG_Ezyshop_Backend.Dto;

import java.util.Date;

import TFG_Ezyshop_Backend.Entity.User;
import lombok.Data;

@Data
public class AdminUserDto {

	private Long id;

	private String username;

	private String email;
	
	private Boolean locked;
	
	private Boolean disabled;

	private String name;

	private String lastname;

	private String zip;

	private String street;

	private String number;

	private String description;
	
	private Date date;
	
	private String role;
	

	public AdminUserDto(User user) {
		this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.locked = user.getLocked();
        this.disabled = user.getDisabled();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.zip = user.getZip();
        this.street = user.getStreet();
        this.number = user.getNumber();
        this.description = user.getDescription();
        this.role = user.getRoleUser().getRole();
        this.date = user.getDate();
	}
}
