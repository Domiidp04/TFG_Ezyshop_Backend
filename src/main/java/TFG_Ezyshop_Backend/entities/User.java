package TFG_Ezyshop_Backend.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table( name = "Users" )
public class User {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
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
	
	private Date date;
	
	@ManyToOne //nombre en BD de la FK
	@JoinColumn( name = "role_id", insertable = false, updatable = false )
	private Role roleUser;
	
	@OneToMany( mappedBy = "userOrder" )  //Nombre del atributo de @ManyToOne
	private List<Order> orders;
	
	@OneToMany( mappedBy = "userAssessment" )  //Nombre del atributo de @ManyToOne
	private List<Assessment> assessments;

}