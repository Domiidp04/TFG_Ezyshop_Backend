package TFG_Ezyshop_Backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table( name = "Users" )
public class User {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private boolean locked;
	
	private boolean disabled;
	
	private String name;
	
	private String lastname;
	
	private String zip;
	
	private String street;
	
	private String number;
	
	private String description;
	
	@ManyToOne
	@JoinColumn( name = "role_id", insertable = false, updatable = false )
	private Role role;

}
