package TFG_Ezyshop_Backend.Dto;

import TFG_Ezyshop_Backend.Entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUserDto extends UserDto{

	private Long id;

	private Boolean locked;
	
	private Boolean disabled;
	
	private String role;
	

	public AdminUserDto(User user) {
		super(user);
		this.id = user.getId();
        this.locked = user.getLocked();
        this.disabled = user.getDisabled();
        this.role = user.getRoleUser().getRole();
	}
}
