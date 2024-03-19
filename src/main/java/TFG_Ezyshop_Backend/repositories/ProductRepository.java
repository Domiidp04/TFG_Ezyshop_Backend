package TFG_Ezyshop_Backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import TFG_Ezyshop_Backend.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query("SELECT p FROM Product p JOIN FETCH p.categoryProduct")
	List<Product> findAll();

}
