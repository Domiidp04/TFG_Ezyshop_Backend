package TFG_Ezyshop_Backend.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table( name = "Categories" )
public class Category {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	private String title;
	
	private String description;
	
	private boolean disabled;
	
	@OneToMany( mappedBy = "categoryProduct" )
	private List<Product> products;
}
