package TFG_Ezyshop_Backend.Dto;

import TFG_Ezyshop_Backend.Entity.Role;
import lombok.Data;

@Data
public class AdminRoleDto {

	private String role;
	
	public AdminRoleDto(Role role) {
		this.role = role.getRole();
	}
}
