package TFG_Ezyshop_Backend.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table( name = "Users" )
public class UserEntity {

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
	
	@Column( name = "role_id" )
	private Long roleId;
	
	@ManyToOne //nombre en BD de la FK
	@JoinColumn( name = "role_id", insertable = false, updatable = false )
	private Role roleUser;
	
	@JsonIgnore
	@OneToMany( mappedBy = "userOrder" )  //Nombre del atributo de @ManyToOne
	private List<Order> orders;
	
	@OneToMany( mappedBy = "userAssessment" )  //Nombre del atributo de @ManyToOne
	private List<Assessment> assessments;

}
