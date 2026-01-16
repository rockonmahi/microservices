package com.escola.userservice.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name="User_Roles_Mapping")
public class UserRolesMapping extends AuditEntity implements Serializable , BaseEntity{

	private static final long serialVersionUID = -2815537346238976829L;
	
	public UserRolesMapping() {
		super.setRecordStatus(1);
	}
	
	@EmbeddedId
	private UserRolesPK userRolesPK;
	
	public UserRolesMapping(User user,Roles roles) {
		super.setRecordStatus(1);
		userRolesPK=new UserRolesPK(user,roles);
	}

	public UserRolesPK getUserRolesPK() {
		return userRolesPK;
	}

	public void setUserRolesPK(UserRolesPK userRolesPK) {
		this.userRolesPK = userRolesPK;
	}
}
