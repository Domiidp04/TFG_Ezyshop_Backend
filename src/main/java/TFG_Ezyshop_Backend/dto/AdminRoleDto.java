package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.Role;
import lombok.Data;

@Data
public class AdminRoleDto {

	private String role;
	
	public AdminRoleDto(Role role) {
		this.role = role.getRole();
	}
}
