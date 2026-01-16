package com.escola.userservice.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="Privileges")
public class Privileges extends AuditEntity implements Serializable , BaseEntity{

	private static final long serialVersionUID = 615592636053903122L;
	
	public Privileges() {
		super.setRecordStatus(1);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Privilege_ID")
	private Integer privilegeId;

	@Column(name = "Privilege_Desc")
	private String privilegeDesc;

	@Column(name = "Privilege_Name")
	private String privilegeName;
	
	@Column(name = "Privilege",length = 10)
	@Enumerated(EnumType.STRING)
	private Privilege privilege;
	
	@ManyToOne
	@JoinColumn(name = "Org_Id")
	private Organization organization;

	public Integer getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegeDesc() {
		return privilegeDesc;
	}

	public void setPrivilegeDesc(String privilegeDesc) {
		this.privilegeDesc = privilegeDesc;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}

enum Privilege {
	LOGIN
}