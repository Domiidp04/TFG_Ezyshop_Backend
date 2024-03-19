package TFG_Ezyshop_Backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

//	public List<User> getAll();
//	public Optional<User> getUser(Long userId);
//	public User create(User user); 
//	public void delete(Long userId);
	
	public Optional<UserEntity> getByUsername(String username);
	
}
