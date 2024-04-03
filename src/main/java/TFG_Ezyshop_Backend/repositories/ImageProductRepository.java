package TFG_Ezyshop_Backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.entities.ImageProduct;

public interface ImageProductRepository extends JpaRepository<ImageProduct, Long>{
	
	public List<ImageProduct>findByProductId(Long productId);

}
