package TFG_Ezyshop_Backend.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import TFG_Ezyshop_Backend.entities.ImageProduct;
import TFG_Ezyshop_Backend.exceptions.ResourceNotFoundException;
import TFG_Ezyshop_Backend.repositories.ImageProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImageProductService {
    
    private final ImageProductRepository imageProductRepository;
    private final CloudinaryService cloudinaryService;

    public ImageProductService(ImageProductRepository imageProductRepository, 
                               CloudinaryService cloudinaryService) {
        this.imageProductRepository = imageProductRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public ImageProduct saveImage(MultipartFile imageFile, Long productId) throws IOException {
        BufferedImage bi = ImageIO.read(imageFile.getInputStream());
        if (bi == null) {
            throw new IllegalArgumentException("Imagen no válida!");
        }
        Map result = cloudinaryService.upload(imageFile);
        ImageProduct imageProduct = new ImageProduct();
        imageProduct.setImageUrl((String) result.get("url"));
        imageProduct.setImageId((String) result.get("public_id")); // Guarda el imageId
        imageProduct.setProductId(productId);
        return imageProductRepository.save(imageProduct);
    }

    
//    public List<ImageProduct> getImagesByProductId(Long productId) {
//        return imageProductRepository.findByProductId(productId);
//    }

    public void deleteImage(Long imageId) {
        // Obtén la imagen de la base de datos
        ImageProduct imageProduct = imageProductRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image not found"));

        // Obtén el ID público de la imagen de Cloudinary
        String publicId = imageProduct.getImageId();

        // Elimina la imagen de Cloudinary
        try {
            cloudinaryService.delete(publicId);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting image from Cloudinary", e);
        }

        // Elimina la imagen de la base de datos
        imageProductRepository.deleteById(imageId);
    }

}

