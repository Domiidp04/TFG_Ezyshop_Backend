package TFG_Ezyshop_Backend.Dto;

import java.util.Date;

import TFG_Ezyshop_Backend.Entity.User;
import lombok.Data;

@Data
public class UserDto {

	private String username;

	private String email;

	private String name;

	private String lastname;

	private String zip;

	private String street;

	private String number;

	private String description;
	
	private Date date;
	

	public UserDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.zip = user.getZip();
        this.street = user.getStreet();
        this.number = user.getNumber();
        this.description = user.getDescription();
        this.date = user.getDate();
	}
	
	

}