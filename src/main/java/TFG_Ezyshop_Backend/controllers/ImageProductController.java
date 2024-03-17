package TFG_Ezyshop_Backend.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import TFG_Ezyshop_Backend.entities.ImageProduct;
import TFG_Ezyshop_Backend.services.ImageProductService;

@RestController
@RequestMapping("/imageProduct")
public class ImageProductController {
	
    private ImageProductService imageProductService;
    
    public ImageProductController(ImageProductService imageProductService) {
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageProduct> uploadImage(@RequestParam("productId") Long productId, @RequestParam("image") MultipartFile file) {
    	try {
    	    ImageProduct imageProduct = imageProductService.saveImage(productId, file);
            return new ResponseEntity<>(imageProduct, HttpStatus.OK);
    	} catch (IOException e) {
    	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }

}
