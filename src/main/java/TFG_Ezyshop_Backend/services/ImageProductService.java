package TFG_Ezyshop_Backend.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import TFG_Ezyshop_Backend.entities.ImageProduct;
import TFG_Ezyshop_Backend.entities.Product;
import TFG_Ezyshop_Backend.repositories.ImageProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImageProductService {
	
    private final ImageProductRepository imageProductRepository;
    
    private ProductService productService;
    
    public ImageProductService(ImageProductRepository imageProductRepository) {
    	this.imageProductRepository = imageProductRepository;
    }

//    public ImageProduct saveImage(Long productId, MultipartFile file) throws IOException {
//        Optional<Product> product = productService.getById(productId);
//        if (product.isPresent()) {
//            profu.setImage(file.getBytes());
//            return imageProductRepository.save(imageProduct);
//        } else {
//            throw new RuntimeException("Product not found with id " + productId);
//        }
//    }


}
