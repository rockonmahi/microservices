package com.escola.userservice.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="Modules")
public class Modules extends AuditEntity implements Serializable , BaseEntity{

	private static final long serialVersionUID = -3616038677399721684L;
	
	public Modules() {
		super.setRecordStatus(1);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Module_ID")
	private Integer moduleId;

	@Column(name = "Module_Desc")
	private String moduleDesc;

	@Column(name = "Module_Name")
	private String moduleName;
	
	@ManyToOne
	@JoinColumn(name = "Org_Id")
	private Organization organization;

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}
