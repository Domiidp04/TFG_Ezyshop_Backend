package TFG_Ezyshop_Backend.services;

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
	
	public Discount save(Discount discount) {
		return discountRepository.save(discount);
	}
	
	public Boolean delete(Long discountId) {
		return getDiscount(discountId).map(discount -> {
			discountRepository.deleteById(discountId);
		return true;
		}).orElse(false);
	}
	
	public Optional<Discount> getDiscount(Long discountId){
		return discountRepository.findById(discountId);
	}
	
}
