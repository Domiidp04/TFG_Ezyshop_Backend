package TFG_Ezyshop_Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

//	public List<Product> getAll();
//	public Optional<Product> getProduct(Long productId);
//	public Product create(Product product); 
//	public void delete(Long productId);
}
