package TFG_Ezyshop_Backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.AdminDiscountDto;
import TFG_Ezyshop_Backend.entities.Discount;
import TFG_Ezyshop_Backend.repositories.DiscountRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DiscountService {

	private final DiscountRepository discountRepository;

	public DiscountService(DiscountRepository discountRepository) {
		this.discountRepository = discountRepository;
	}
	
	
	public List<AdminDiscountDto> getAll(){
		List<Discount> discounts = discountRepository.findAll();
		return discounts.stream()
				.map(AdminDiscountDto::new)
				.collect(Collectors.toList());
	}

	
	public AdminDiscountDto getById(Long id) {
	    Optional<Discount> optionalDiscount = discountRepository.findById(id);
	    if (!optionalDiscount.isPresent()) {
	        throw new IllegalArgumentException("Invalid discount ID");
	    }
	    return new AdminDiscountDto(optionalDiscount.get());
	}


	public AdminDiscountDto createDiscount(AdminDiscountDto adminDiscountDto) {
	    Discount discount = new Discount();
	    // Aquí debes establecer las propiedades del descuento a partir del DTO
	    discount.setTitle(adminDiscountDto.getTitle());
	    discount.setCode(adminDiscountDto.getCode());
	    discount.setStartDate(new Date());
	    discount.setFinalDate(adminDiscountDto.getFinalDate());
	    discount.setUse(adminDiscountDto.getUse() );
	    discount.setAmount(adminDiscountDto.getAmount());

	    // Si los usos son 0, establecer expired a true
	    if (discount.getUse() <= 0) {
	    	System.out.println("USOS : " + discount.getUse());
	        discount.setExpired(true);
	    }else {
	    	System.out.println("USOS : " + discount.getUse());
	    	discount.setExpired(false);
	    }

	    discount = discountRepository.save(discount);
	    discountRepository.flush();
	    return new AdminDiscountDto(discount);
	}
	
	public AdminDiscountDto updateDiscount(Long id, AdminDiscountDto adminDiscountDto) {
	    if (!id.equals(adminDiscountDto.getId())) {
	        throw new IllegalArgumentException("Mismatched discount ID"); // Lanza una excepción si los IDs no coinciden
	    }

	    return discountRepository.findById(id)
	            .map(discount -> {
	                // Actualizar el objeto Discount con los datos del DTO
	                discount.setId(adminDiscountDto.getId());
	                discount.setTitle(adminDiscountDto.getTitle());
	                discount.setCode(adminDiscountDto.getCode());
	                discount.setStartDate(adminDiscountDto.getStartDate());
	                discount.setFinalDate(adminDiscountDto.getFinalDate());
	                discount.setExpired(adminDiscountDto.getExpired());
	                discount.setUse(adminDiscountDto.getUse());
	                discount.setAmount(adminDiscountDto.getAmount());

	                // Si los usos son 0, establecer expired a true
	        	    if (discount.getUse() <= 0) {
	        	    	System.out.println("USOS : " + discount.getUse());
	        	        discount.setExpired(true);
	        	    }else {
	        	    	System.out.println("USOS : " + discount.getUse());
	        	    	discount.setExpired(false);
	        	    }
	        	    
	                discount = discountRepository.save(discount);

	                // Convertir el Discount a AdminDiscountDto antes de devolverlo
	                return new AdminDiscountDto(discount);
	            })
	            .orElseThrow(() -> new IllegalArgumentException("Invalid discount ID")); // Lanza una excepción si el ID no es válido
	}

	public Boolean deleteDiscount(Long id) {
	    return discountRepository.findById(id)
	            .map(discount -> {
	                discount.setExpired(true);
	                discountRepository.save(discount);
	                return true; // Devuelve true si el descuento se encuentra y se actualiza
	            })
	            .orElse(false); // Devuelve false si el descuento no se encuentra
	}


	
	
	
	


	
}
