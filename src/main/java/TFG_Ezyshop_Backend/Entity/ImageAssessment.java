package TFG_Ezyshop_Backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table( name = "Image_Assessment" )
public class ImageAssessment {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	@Lob
	private byte[] image;
	
	@ManyToOne
	@JoinColumn( name = "assessment_id", insertable = false, updatable = false )
	private Assessment assessment;

}
