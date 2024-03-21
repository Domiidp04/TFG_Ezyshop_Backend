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
	        discount.setExpired(true);
	    }else {
	    	discount.setExpired(false);
	    }

	    discount = discountRepository.save(discount);
	    return new AdminDiscountDto(discount);
	}
	
	public AdminDiscountDto updateDiscount(Long id, AdminDiscountDto adminDiscountDto) {
	    // Comprobar si el ID en la ruta coincide con el ID en el DTO
	    if (!id.equals(adminDiscountDto.getId())) {
	        throw new IllegalArgumentException("Mismatched discount ID");
	    }

	    Optional<Discount> optionalDiscount = discountRepository.findById(id);
	    if (!optionalDiscount.isPresent()) {
	        throw new IllegalArgumentException("Invalid discount ID");
	    }
	    Discount discount = optionalDiscount.get();
	    
	    

	    // Si los usos son 0, establecer expired a true
	    if (discount.getUse() <= 0) {
	        discount.setExpired(true);
	    }else {
	        discount.setExpired(false);
	    }

	    discount = discountRepository.save(discount);
	    return new AdminDiscountDto(discount);
	}


	
}
