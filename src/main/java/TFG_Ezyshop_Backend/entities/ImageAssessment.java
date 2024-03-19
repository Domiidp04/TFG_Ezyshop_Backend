package TFG_Ezyshop_Backend.entities;

import jakarta.persistence.Column;
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
@Table( name = "Images_Assessment" )
public class ImageAssessment {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Lob
	private byte[] image;
	
	@Column( name = "assessment_id" )
	private Long assessmentId;
	
	@ManyToOne
	@JoinColumn( name = "assessment_id", insertable = false, updatable = false )
	private Assessment assessmentImageAssessment;

}
