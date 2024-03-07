package TFG_Ezyshop_Backend.Entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table( name = "Roles" )
public class Role {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	private String role;
	
	private Date granted_date;
	
}
