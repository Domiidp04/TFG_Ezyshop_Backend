package TFG_Ezyshop_Backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.entities.Discount;
import TFG_Ezyshop_Backend.repositories.DiscountRepository;

@Service
public class DiscountService {

	private final DiscountRepository discountRepository;

	public DiscountService(DiscountRepository discountRepository) {
		this.discountRepository = discountRepository;
	}
	
	
	public List<Discount> getAll(){
		return discountRepository.findAll();
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
