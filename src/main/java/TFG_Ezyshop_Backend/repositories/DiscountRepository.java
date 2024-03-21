package TFG_Ezyshop_Backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.entities.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long>{
	
	Optional<Discount>findByCode(String discountCode);
	
//	public List<Discount> getAll();
//	public Optional<Discount> getDiscount(Long discountId);
//	public Discount create(Discount discount); 
//	public void delete(Long discountId);

}
