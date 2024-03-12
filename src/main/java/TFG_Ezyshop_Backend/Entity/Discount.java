package TFG_Ezyshop_Backend.Entity;


import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table( name = "Discounts" )
public class Discount {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	private String title;
	
	private String code;
	
	@Column( name = "start_date" )
	private Date startDate;
	
	@Column( name = "final_date" )
	private Date finalDate;
	
	private boolean expired;
	
	private int use;
	
	@OneToMany( mappedBy = "discountOrder" )
	private List<Order> orders;
	
}
