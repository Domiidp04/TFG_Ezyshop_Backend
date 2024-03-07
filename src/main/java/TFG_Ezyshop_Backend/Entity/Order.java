package TFG_Ezyshop_Backend.Entity;

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
	private long id;
	
	@Column( name = "order_date" )
	private Date orderDate;
	
	@Column( name = "shipping_date" )
	private Date shippingDate;
	
	@Column( name = "total_amount" )
	private double totalAmount;
	
	@Column( name = "shipping_amount" )
	private double shippingAmount;
	
	@Column( name = "saved_amount" )
	private double savedAmount;
	
	@Column( name = "payment_amount" )
	private double paymentAmount;
	
	@ManyToOne
	@JoinColumn( name = "discount_id", updatable = false, insertable = false )
	private Discount discountOrder;
	
	@ManyToOne
	@JoinColumn( name = "user_id", updatable = false, insertable = false )
	private User userOrder;
	
	@OneToMany( mappedBy = "order" )
	private List<OrderProduct> orders;
	
	
}
