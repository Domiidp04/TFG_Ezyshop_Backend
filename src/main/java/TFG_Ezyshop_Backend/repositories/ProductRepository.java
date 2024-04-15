package TFG_Ezyshop_Backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import TFG_Ezyshop_Backend.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query("SELECT p FROM Product p JOIN FETCH p.categoryProduct")
	List<Product> findAll();
	
	@Query("SELECT p FROM Product p WHERE p.title = :title")
    Optional<Product> findByTitle(@Param("title") String title);

}
