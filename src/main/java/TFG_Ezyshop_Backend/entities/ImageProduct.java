package TFG_Ezyshop_Backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table( name = "Images_Products" )
public class ImageProduct {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Lob
	private byte[] image;
	
	@Column( name = "product_id" )
	private Long productId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn( name = "product_id", insertable = false, updatable = false )
	private Product productImageProduct;

}
