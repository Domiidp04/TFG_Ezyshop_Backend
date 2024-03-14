package TFG_Ezyshop_Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.Entity.Product;
import jakarta.transaction.Transactional;
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long>{
//	@Query(value = "SELECT * FROM Products", nativeQuery = true)
//	public List<Product> getAll();

//	public List<Product> getAll();
//	public Optional<Product> getProduct(Long productId);
//	public Product create(Product product); 
//	public void delete(Long productId);
}
