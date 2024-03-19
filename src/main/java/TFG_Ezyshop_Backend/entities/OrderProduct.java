package TFG_Ezyshop_Backend.entities;


import jakarta.persistence.Column;
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
@Table( name = "Orders_Products" )
public class OrderProduct {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	private Integer amount;
	
	private Double price;
	
	@Column( name = "order_id" )
	private Long orderId;
	
	@Column( name = "product_id" )
	private Long productId;
	
	@ManyToOne
	@JoinColumn( name = "order_id", insertable = false, updatable = false )
	private Order orderOrderProduct;
	
	@ManyToOne
	@JoinColumn( name = "product_id", insertable = false, updatable = false )
	private Product productOrderProduct;

}
