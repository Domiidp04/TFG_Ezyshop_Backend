package TFG_Ezyshop_Backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import TFG_Ezyshop_Backend.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query("SELECT p FROM Product p JOIN FETCH p.categoryProduct")
	List<Product> findAll();
	
	@Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE LOWER(CONCAT(:name, '%'))")
	List<Product> findByTitle(@Param("name") String name);

    @Query(value = "SELECT p.* FROM Products p JOIN Orders_Products op ON p.id = op.product_id GROUP BY p.id ORDER BY SUM(op.amount) DESC", nativeQuery = true)
    List<Product> findAllWithTotalSold();
    
    List<Product> findByCategoryId(Long categoryId);


}
