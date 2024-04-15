package TFG_Ezyshop_Backend.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import TFG_Ezyshop_Backend.entities.Assessment;
import TFG_Ezyshop_Backend.entities.ImageAssessment;
import TFG_Ezyshop_Backend.exceptions.ResourceNotFoundException;
import TFG_Ezyshop_Backend.repositories.AssessmentRepository;
import TFG_Ezyshop_Backend.repositories.ImageAssessmentRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImageAssessmentService {
    
    private final ImageAssessmentRepository imageAssessmentRepository;
    
    private final CloudinaryService cloudinaryService;

    private final AssessmentRepository assessmentRepository;
    
    public ImageAssessmentService(ImageAssessmentRepository imageAssessmentRepository, 
                               CloudinaryService cloudinaryService,
                               AssessmentRepository assessmentRepository) {
        this.imageAssessmentRepository = imageAssessmentRepository;
        this.cloudinaryService = cloudinaryService;
        this.assessmentRepository = assessmentRepository;
    }
    
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities();
	}

	public List<ImageAssessment> saveImages(List<MultipartFile> imageFiles, Long assessmentId) throws IOException {
	    List<ImageAssessment> imageAssessments = new ArrayList<>();

	    for (MultipartFile imageFile : imageFiles) {
	        BufferedImage bi = ImageIO.read(imageFile.getInputStream());
	        if (bi == null) {
	            throw new IllegalArgumentException("Imagen no válida!");
	        }
	        Map result = cloudinaryService.upload(imageFile);
	        ImageAssessment imageAssessment = new ImageAssessment();
	        imageAssessment.setImageUrl((String) result.get("url"));
	        imageAssessment.setImageId((String) result.get("public_id")); // Guarda el imageId
	        imageAssessment.setAssessmentId(assessmentId);
	        imageAssessments.add(imageAssessmentRepository.save(imageAssessment));
	    }

	    return imageAssessments;
	}


    public void deleteImage(Long imageId) {
        // Obtén la imagen de la base de datos
        ImageAssessment imageAssessment = imageAssessmentRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image not found"));

        // Obtén la valoración asociada a la imagen
        Assessment assessment = assessmentRepository.findById(imageAssessment.getAssessmentId()).orElseThrow(() -> new ResourceNotFoundException("Assessment not found"));

        // Obtén la autenticación y las autoridades del usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = getAuthorities();
        String authenticatedUsername = authentication.getName();

        // Comprueba si el usuario autenticado es el mismo que el usuario que creó la valoración
        if (!assessment.getUserAssessment().getUsername().equals(authenticatedUsername) && !authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("No tienes autorización para eliminar valoraciones de otros usuarios.");
        }

        // Obtén el ID público de la imagen de Cloudinary
        String publicId = imageAssessment.getImageId();

        // Elimina la imagen de Cloudinary
        try {
            cloudinaryService.delete(publicId);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting image from Cloudinary", e);
        }

        // Elimina la imagen de la base de datos
        imageAssessmentRepository.deleteById(imageId);
    }



}

