package TFG_Ezyshop_Backend.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import TFG_Ezyshop_Backend.entities.ImageProduct;
import TFG_Ezyshop_Backend.services.ImageProductService;

@RestController
@RequestMapping("/imageProducts")
public class ImageProductController {
	
    private ImageProductService imageProductService;
    
    public ImageProductController(ImageProductService imageProductService) {
    	this.imageProductService = imageProductService;
    }


    @PostMapping("/{productId}")
    public ResponseEntity<List<ImageProduct>> uploadImages(@PathVariable Long productId, @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        try {
            List<ImageProduct> imageProducts = imageProductService.saveImages(imageFiles, productId);
            return new ResponseEntity<>(imageProducts, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @GetMapping("//{productId}")
//    public ResponseEntity<List<ImageProduct>> getImagesByProductId(@PathVariable Long productId) {
//        List<ImageProduct> images = imageProductService.getImagesByProductId(productId);
//        return new ResponseEntity<>(images, HttpStatus.OK);
//    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageProductService.deleteImage(imageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
