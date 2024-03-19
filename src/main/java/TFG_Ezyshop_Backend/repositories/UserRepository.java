package TFG_Ezyshop_Backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

//	public List<User> getAll();
//	public Optional<User> getUser(Long userId);
//	public User create(User user); 
//	public void delete(Long userId);
	
}
