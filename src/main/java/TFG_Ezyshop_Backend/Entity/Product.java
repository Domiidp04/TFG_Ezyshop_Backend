package TFG_Ezyshop_Backend.Entity;

import java.util.List;
import java.util.Locale.Category;

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
@Table( name = "Products" )
public class Product {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	private String title;
	
	private String description;
	
	private Double price;
	
	private Double discount_price;
	
	private Integer stock;
	
	private Boolean disabled;
	
	@ManyToOne
	@JoinColumn( name = "category_id", insertable = false, updatable = false )
	private Category categoryProduct;
	
	@OneToMany( mappedBy = "productImageProduct" )
	private List<ImageProduct> images;
	
	@OneToMany( mappedBy = "productOrderProduct" )
	private List<OrderProduct> ordersProducts;
	
	@OneToMany(mappedBy = "productAssessment")
	private List<Assessment> assessments;
	
}
