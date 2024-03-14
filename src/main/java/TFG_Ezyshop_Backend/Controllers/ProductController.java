package TFG_Ezyshop_Backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.Dto.ProductDto;
import TFG_Ezyshop_Backend.Entity.Product;
import TFG_Ezyshop_Backend.Services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAll(){
		System.out.println(productService.getAll());
		System.out.println("hola");
		return new ResponseEntity<List<ProductDto>>(productService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") long productId) {
    	return productService.getById(productId)
				.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	
}
