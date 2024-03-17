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
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostUpdate;
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
	
	@Column( name = "discount_price" )
	private Double discountPrice;
	
	private Integer stock;
	
	private Boolean disabled;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "category_id", insertable = false, updatable = false )
	private Category categoryProduct;
	
	@OneToMany( mappedBy = "productImageProduct" )
	private List<ImageProduct> images;
	
	@OneToMany( mappedBy = "productOrderProduct" )
	private List<OrderProduct> ordersProducts;
	
	@OneToMany(mappedBy = "productAssessment")
	private List<Assessment> assessments;
	
	//Metodo de control de stock
	@PostLoad
    @PostUpdate
    public void checkStock() {
        if (this.stock == 0) {
            this.disabled = true;
        }
    }
}
