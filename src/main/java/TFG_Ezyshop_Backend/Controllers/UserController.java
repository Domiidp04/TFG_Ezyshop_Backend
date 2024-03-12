package TFG_Ezyshop_Backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.Dto.UserDto;
import TFG_Ezyshop_Backend.Entity.User;
import TFG_Ezyshop_Backend.Services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	

	@GetMapping
	public ResponseEntity<List<UserDto>> getAll(){
		return new ResponseEntity<List<UserDto>>(userService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("{userId}")
	public ResponseEntity<User> getById(@PathVariable("userId") long userId){
		
		return userService.getUser(userId)
				.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		
	}
	
	@PostMapping
	public ResponseEntity<User> create (@RequestBody User user){
		return new ResponseEntity<User>(userService.save(user),HttpStatus.CREATED);
	}
	
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> update(@PathVariable("userId") long userId, @RequestBody User user){
	    if (userId == user.getId()) {
	        return userService.getUser(userId)
	                .map(userDB -> {
	                    userService.save(user); // guarda el usuario en la base de datos
	                    return new ResponseEntity<>(user, HttpStatus.OK);
	                })
	                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    } else {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}

	
	@DeleteMapping("/{userId}")
	public ResponseEntity<User> delete(@PathVariable("userId") long userId){
		if (userService.delete(userId)) {
			return new ResponseEntity<User>(HttpStatus.OK);
		}else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}

}
