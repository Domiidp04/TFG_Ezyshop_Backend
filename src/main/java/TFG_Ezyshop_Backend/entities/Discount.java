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
	
	@OneToMany( mappedBy = "discountOrder" )
	private List<Order> orders;
	
	@PostLoad
    @PostUpdate
    public void checkDiscount() {
        Date now = new Date();
        if ((this.startDate == null || !now.before(this.startDate)) &&
            (this.finalDate == null || !now.after(this.finalDate)) &&
            (this.use == null || this.use > 0)) {
            this.expired = false;
        } else {
            this.expired = true;
        }
    }

    public void use() {
        if (this.use != null && this.use > 0) {
            this.use--;
            checkDiscount();
        }
    }
	
}
