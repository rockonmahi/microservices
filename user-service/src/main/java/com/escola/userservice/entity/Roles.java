package com.escola.userservice.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="Roles")
public class Roles extends AuditEntity implements Serializable , BaseEntity{

	private static final long serialVersionUID = -3616038677399721684L;
	
	public Roles() {
		super.setRecordStatus(1);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Role_ID")
	private Integer roleId;

	@Column(name = "Role_Desc")
	private String roleDesc;

	@Column(name = "Role_Name")
	private String roleName;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
