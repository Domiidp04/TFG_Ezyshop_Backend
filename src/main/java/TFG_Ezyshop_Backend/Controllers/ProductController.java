package TFG_Ezyshop_Backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.Dto.AdminProductDto;
import TFG_Ezyshop_Backend.Services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<List<AdminProductDto>> getAll(){
		System.out.println(productService.getAll());
		System.out.println("hola");
		return new ResponseEntity<List<AdminProductDto>>(productService.getAll(), HttpStatus.OK);
	}
	
	
}
