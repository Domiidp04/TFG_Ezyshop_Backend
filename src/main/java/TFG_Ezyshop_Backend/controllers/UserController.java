package TFG_Ezyshop_Backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.exceptions.EmailAlreadyExistsException;
import TFG_Ezyshop_Backend.exceptions.UserNotFoundException;
import TFG_Ezyshop_Backend.exceptions.UsernameAlreadyExistsException;
import TFG_Ezyshop_Backend.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	

	 @GetMapping
	    public ResponseEntity<?> getAllUsers() {
	        List<?> users = userService.getAll();
	        return ResponseEntity.ok(users);
	    }
	
	 @GetMapping("/{userId}")
	 public ResponseEntity<?> getById(@PathVariable("userId") long userId) {
	     Optional<?> user = userService.getUser(userId);

	     if (user.isPresent()) {
	         return new ResponseEntity<>(user.get(), HttpStatus.OK);
	     } else {
	         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     }
	 }

	
	 @PostMapping
	 public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity) {
	     try {
	         UserEntity createdUser = userService.save(userEntity);
	         System.out.println(createdUser.toString());
	         return new ResponseEntity<>("Usuario creado con éxito", HttpStatus.CREATED);
	     } catch (UsernameAlreadyExistsException e) {
	         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	     } catch (EmailAlreadyExistsException e) {
	         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	     }
	 }
	 
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<UserEntity> update(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
	        try {
	            UserEntity user = userService.update(id, updatedUser);
	            return ResponseEntity.ok(user);
	        } catch (UserNotFoundException e) {
	            return ResponseEntity.notFound().build();
	        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
	            return ResponseEntity.badRequest().body(null);
	        } catch (AccessDeniedException e) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	        }
	    }



	
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
	        try {
	            Boolean deleted = userService.delete(id);
	            if (deleted) {
	                return ResponseEntity.ok("Usuario eliminado correctamente");
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
	            }
	        } catch (AccessDeniedException e) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes autorización para eliminar este usuario");
	        }
	    }

}
