package TFG_Ezyshop_Backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.dto.ProductDto;
import TFG_Ezyshop_Backend.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAll(){
		return new ResponseEntity<List<ProductDto>>(productService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	 public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
		try {
            ProductDto product = productService.getById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            // Manejar la situación donde el producto no existe.
            // Podrías devolver un estado HTTP 404 (Not Found), por ejemplo.
            return ResponseEntity.notFound().build();
        }
	    }
}
