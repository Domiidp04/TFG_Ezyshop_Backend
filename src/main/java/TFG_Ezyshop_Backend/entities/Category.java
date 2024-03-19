package TFG_Ezyshop_Backend.entities;

import java.util.List;

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
@Table( name = "Categories" )
public class Category {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	private String title;
	
	private String description;
	
	private Boolean disabled;
	
	@OneToMany( mappedBy = "categoryProduct" ) //Nombre del atributo de @ManyToOne
	private List<Product> products;
	
	/*Metodo de control de categorias.
	 * Si una categoria no tiene productos, esta desactivada
	 * Si un producto esta desactivado de esa categoria, 
	 * se desactiva hasta que haya stock
	 */
	@PostLoad
    @PostUpdate
    public void checkProducts() {
        if (this.products.stream().allMatch(Product::getDisabled)) {
            this.disabled = true;
        } else {
            this.disabled = false;
        }
    }
}
