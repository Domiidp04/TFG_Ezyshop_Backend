package TFG_Ezyshop_Backend.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table( name = "Roles" )
public class Role {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	private String role;
	
	private Date granted_date;
	
	@JsonIgnore
	@OneToMany( mappedBy = "roleUser" ) //Nombre del atributo de @ManyToOne
	private List<UserEntity> users;
	
}
