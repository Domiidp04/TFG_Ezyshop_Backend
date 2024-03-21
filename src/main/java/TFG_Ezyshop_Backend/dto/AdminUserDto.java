package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class AdminUserDto extends UserDto{

	private Long id;

	private Boolean locked;
	
	private Boolean disabled;
	
	private String role;
	

	public AdminUserDto(UserEntity user) {
		super(user);
		this.id = user.getId();
        this.locked = user.getLocked();
        this.disabled = user.getDisabled();
        this.role = user.getRoleUser().getRole();
	}
}
