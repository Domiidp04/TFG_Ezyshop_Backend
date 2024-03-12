package TFG_Ezyshop_Backend.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.Dto.UserDto;
import TFG_Ezyshop_Backend.Entity.User;
import TFG_Ezyshop_Backend.Repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	public List<UserDto> getAll(){
		List<User>users = userRepository.findAll();
		return users.stream()
				.map(UserDto::new)
				.collect(Collectors.toList());
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public Boolean delete(Long userId) {
		return getUser(userId).map(user -> {
			userRepository.deleteById(userId);
		return true;
		}).orElse(false);
	}
	
	public Optional<User> getUser(Long userId){
		return userRepository.findById(userId);
	}
}
