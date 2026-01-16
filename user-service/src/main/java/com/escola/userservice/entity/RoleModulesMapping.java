package com.escola.userservice.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name="Role_Modules_Mapping")
public class RoleModulesMapping extends AuditEntity implements Serializable , BaseEntity{

	private static final long serialVersionUID = -2815537346238976829L;
	
	public RoleModulesMapping() {
		super.setRecordStatus(1);
	}
	
	@EmbeddedId
	private RoleModulesPK roleModulesPK;
	
	public RoleModulesMapping(Roles role,Modules module) {
		super.setRecordStatus(1);
		roleModulesPK=new RoleModulesPK(role,module);
	}

	public RoleModulesPK getRoleModulesPK() {
		return roleModulesPK;
	}

	public void setRoleModulesPK(RoleModulesPK roleModulesPK) {
		this.roleModulesPK = roleModulesPK;
	}
}
