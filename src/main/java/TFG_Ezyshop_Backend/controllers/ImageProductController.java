package TFG_Ezyshop_Backend.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    	this.imageProductService = imageProductService;
    }


    @PostMapping("/upload/{productId}")
    public ResponseEntity<ImageProduct> uploadImage(@PathVariable Long productId, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            ImageProduct imageProduct = imageProductService.saveImage(imageFile, productId);
            return new ResponseEntity<>(imageProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/product/{productId}")
//    public ResponseEntity<List<ImageProduct>> getImagesByProductId(@PathVariable Long productId) {
//        List<ImageProduct> images = imageProductService.getImagesByProductId(productId);
//        return new ResponseEntity<>(images, HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageProductService.deleteImage(imageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
