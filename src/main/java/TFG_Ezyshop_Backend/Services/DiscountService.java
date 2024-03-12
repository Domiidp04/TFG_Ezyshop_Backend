package TFG_Ezyshop_Backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.Entity.Discount;
import TFG_Ezyshop_Backend.Repository.DiscountRepository;

@Service
public class DiscountService {

	private final DiscountRepository discountRepository;

	public DiscountService(DiscountRepository discountRepository) {
		this.discountRepository = discountRepository;
	}
	
	
	public List<Discount> getAll(){
		return discountRepository.getAll();
	}
	
	public Discount save(Discount discount) {
		return discountRepository.create(discount);
	}
	
	public Boolean delete(Long discountId) {
		return getDiscount(discountId).map(discount -> {
			discountRepository.delete(discountId);
		return true;
		}).orElse(false);
	}
	
	public Optional<Discount> getDiscount(Long discountId){
		return discountRepository.getDiscount(discountId);
	}
	
}
