package TFG_Ezyshop_Backend.entities;


import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table( name = "Discounts" )
public class Discount {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	private String title;
	
	private String code;
	
	@Column( name = "start_date" )
	private Date startDate;
	
	@Column( name = "final_date" )
	private Date finalDate;
	
	private Boolean expired;
	
	private Integer use;
	
	private Double amount;
	
	@OneToMany( mappedBy = "discountOrder" )
	private List<Order> orders;
	
	
	
}
