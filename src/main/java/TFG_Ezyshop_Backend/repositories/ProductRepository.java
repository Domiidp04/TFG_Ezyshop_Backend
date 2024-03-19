package TFG_Ezyshop_Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
