package TFG_Ezyshop_Backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.AdminUserDto;
import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	public List<AdminUserDto> getAll(){
		List<UserEntity>users = userRepository.findAll();
		return users.stream()
				.map(AdminUserDto::new)
				.collect(Collectors.toList());
	}
	
	public UserEntity save(UserEntity user) {
		return userRepository.save(user);
	}
	
	public Boolean delete(Long userId) {
		return getUser(userId).map(user -> {
			userRepository.deleteById(userId);
		return true;
		}).orElse(false);
	}
	
	public Optional<UserEntity> getUser(Long userId){
		return userRepository.findById(userId);
	}
}
