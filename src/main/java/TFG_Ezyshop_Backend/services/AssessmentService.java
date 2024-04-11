package TFG_Ezyshop_Backend.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.AdminAssessmentDto;
import TFG_Ezyshop_Backend.dto.AssessmentDto;
import TFG_Ezyshop_Backend.entities.Assessment;
import TFG_Ezyshop_Backend.entities.Product;
import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.exceptions.AssessmentNotFoundException;
import TFG_Ezyshop_Backend.exceptions.ResourceNotFoundException;
import TFG_Ezyshop_Backend.exceptions.UsernameNotFoundException;
import TFG_Ezyshop_Backend.repositories.AssessmentRepository;
import TFG_Ezyshop_Backend.repositories.ProductRepository;
import TFG_Ezyshop_Backend.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AssessmentService {

	private final AssessmentRepository assessmentRepository;
	
	private final ProductRepository productRepository;
	
	private final UserRepository userRepository;

	public AssessmentService(AssessmentRepository assessmentRepository,
							 ProductRepository productRepository,
							 UserRepository userRepository) {
		this.assessmentRepository = assessmentRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	// Metodo obtencion del rol, para no repetir
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities();
	}

	//Todas las valoraciones para el ADMIN
	public List<AdminAssessmentDto> getAll() {
		return assessmentRepository.findAll()
				.stream()
				.map(AdminAssessmentDto::new)
				.collect(Collectors.toList());
	}
	
	public Optional<?> getAssessment(Long assessmentId) {
	    Optional<Assessment> optionalAssessment = assessmentRepository.findById(assessmentId);

	    if (!optionalAssessment.isPresent()) {
	        throw new ResourceNotFoundException("Assessment not found");
	    }

	    Assessment assessment = optionalAssessment.get();

	    // Obtén la autenticación y las autoridades del usuario actual
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Collection<? extends GrantedAuthority> authorities = getAuthorities();
	    // Comprueba si el usuario autenticado tiene el rol de administrador
	    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	        AdminAssessmentDto adminAssessmentDto = new AdminAssessmentDto(assessment);
	        return Optional.of(adminAssessmentDto);
	    } else {
	        AssessmentDto assessmentDto = new AssessmentDto(assessment);
	        return Optional.of(assessmentDto);
	    }
	}




	public Assessment createAssessment(Long productId, Assessment assessment) {
	    // Obtén la autenticación del usuario actual
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String authenticatedUsername = authentication.getName(); // Asegúrate de que esto obtiene el username

	    // Comprueba si el usuario está autenticado
	    if (authenticatedUsername == null) {
	        throw new AccessDeniedException("Debe estar autenticado para crear una valoración.");
	    }

	    // Busca el usuario en la base de datos
	    UserEntity user = userRepository.getByUsername(authenticatedUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    // Obtén el ID del usuario autenticado
	    Long authenticatedUserId = user.getId();

	    // Obtén el producto al que se le está haciendo la valoración
	    Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

	    // Asigna el producto y el usuario a la valoración
	    assessment.setProductId(product.getId());
	    assessment.setUserId(authenticatedUserId);
	    assessment.setDisabled(true);
	    assessment.setDate(new Date());
	    

	    // Guarda la valoración en la base de datos
	    return assessmentRepository.save(assessment);
	}



	public Boolean delete(Long assessmentId) {
	    // Obtiene el nombre de usuario del usuario autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Collection<? extends GrantedAuthority> authorities = getAuthorities();
	    String authenticatedUsername = authentication.getName();

	 // Obtiene el ID del usuario autenticado
	    Optional<UserEntity> optionalAuthenticatedUser = userRepository.getByUsername(authenticatedUsername);
	    if (!optionalAuthenticatedUser.isPresent()) {
	        throw new UsernameNotFoundException("Usuario autenticado no encontrado");
	    }
	    UserEntity authenticatedUser = optionalAuthenticatedUser.get();
	    Long authenticatedUserId = authenticatedUser.getId();
	    // Obtiene el assessment que se está intentando desactivar
	    Optional<Assessment> optionalAssessment = assessmentRepository.findById(assessmentId);
	    if (!optionalAssessment.isPresent()) {
	        throw new AssessmentNotFoundException("Assessment no encontrado");
	    }
	    Assessment assessment = optionalAssessment.get();

	    // Comprueba si el usuario autenticado es el mismo que el usuario del assessment que se está intentando desactivar o si es un administrador
	    if (!assessment.getUserId().equals(authenticatedUserId) && !authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	        throw new AccessDeniedException("No tienes autorización para desactivar el assessment de otros usuarios.");
	    }

	    // Marca el atributo disabled como true
	    assessment.setDisabled(true);

	    // Guarda el assessment actualizado en la base de datos
	    assessmentRepository.save(assessment);

	    return true;
	}




	
}
