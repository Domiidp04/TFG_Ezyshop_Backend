package TFG_Ezyshop_Backend.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
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
@Table( name = "Orders" )
public class Order {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column( name = "order_date" )
	private Date orderDate;
	
	@Column( name = "shipping_date" )
	private Date shippingDate;
	
	@Column( name = "total_amount" )
	private Double totalAmount;
	
	@Column( name = "shipping_amount" )
	private Double shippingAmount;
	
	@Column( name = "saved_amount" )
	private Double savedAmount;
	
	@Column( name = "payment_amount" )
	private Double paymentAmount;
	
	@Column( name = "currency", updatable = false, nullable = false )
    private String currency = "EUR";

    @Column( name = "method", updatable = false, nullable = false )
    private String method = "paypal";

    @Column( name = "intent", updatable = false, nullable = false )
    private String intent = "sale";
    
    @Column( name = "payment", updatable = false )
    private Boolean payment;
    
    @Column( name = "payment_id", updatable = false )
    private String paymentId;
	
	@Column( name = "discount_id" )
	private Long discountId;
	
	@Column( name = "user_id" )
	private Long userId;
	
	@ManyToOne
	@JoinColumn( name = "discount_id", updatable = false, insertable = false )
	private Discount discountOrder;
	
	@ManyToOne
	@JoinColumn( name = "user_id", updatable = false, insertable = false )
	private UserEntity userOrder;
	
	@OneToMany( mappedBy = "orderOrderProduct" )
	private List<OrderProduct> orders;
	
	
}
