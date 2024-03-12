package TFG_Ezyshop_Backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.Entity.User;
import TFG_Ezyshop_Backend.Repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	public List<User> getAll(){
		return userRepository.getAll();
	}
	
	public User save(User user) {
		return userRepository.create(user);
	}
	
	public Boolean delete(Long userId) {
		return getUser(userId).map(user -> {
			userRepository.delete(userId);
		return true;
		}).orElse(false);
	}
	
	public Optional<User> getUser(Long userId){
		return userRepository.getUser(userId);
	}
}
