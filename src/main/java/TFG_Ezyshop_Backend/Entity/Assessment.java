package TFG_Ezyshop_Backend.Entity;

import java.util.Date;
import java.util.List;

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
@Table( name = "Assessments" )
public class Assessment {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	private String title;
	
	private String description;
	
	private Date date;
	
	private boolean disabled;
	
	@ManyToOne
	@JoinColumn( name = "user_id", insertable = false, updatable = false )
	private User userAssessment;
	
	@ManyToOne
	@JoinColumn( name = "product_id", insertable = false, updatable = false )
	private Product productAssessment;
	
	@OneToMany( mappedBy = "assessmentImageAssessment" )
	private List<ImageAssessment> images;
	
}
