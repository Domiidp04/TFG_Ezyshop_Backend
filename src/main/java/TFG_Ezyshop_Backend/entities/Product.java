package TFG_Ezyshop_Backend.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table ( name = "Products" )
public class Product {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	private String title;
	
	private String description;
	
	private Double price;
	
	@Column( name = "discount_price" )
	private Double discountPrice;
	
	private Integer stock;
	
	private Boolean disabled;
	
	@OneToMany( mappedBy = "productImageProduct" )  //Nombre del atributo de @ManyToOne
	private List<ImageProduct>imageProducts;
	
	@OneToMany( mappedBy = "productAssessment" )  //Nombre del atributo de @ManyToOne
	private List<Assessment>assessments;
	
	@OneToMany( mappedBy = "productOrderProduct" )  //Nombre del atributo de @ManyToOne
	private List<OrderProduct>orderProducts;
	
	@ManyToOne(fetch = FetchType.EAGER) //nombre en BD de la FK
	@JoinColumn( name = "category_id" )
	private Category categoryProduct;
	
	
}
