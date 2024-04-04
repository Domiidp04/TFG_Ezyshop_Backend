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
@Table( name = "Images_Assessments" )
public class ImageAssessment {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column( name = "image_url" )
	private String imageUrl;
	
	@Column( name = "assessment_id" )
	private Long assessmentId;
	
	@Column( name = "image_id" )
	private String imageId;
	
	@ManyToOne
	@JoinColumn( name = "assessment_id", insertable = false, updatable = false )
	private Assessment assessmentImageAssessment;

}
