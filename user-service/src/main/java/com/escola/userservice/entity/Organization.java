package com.escola.userservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Organization")
public class Organization extends AuditEntity implements Serializable, BaseEntity {

	private static final long serialVersionUID = 491247714130613174L;
	
	public Organization() {
		super.setRecordStatus(1);
	}
	
	@Id
	@GeneratedValue(generator = "organization")
	@GenericGenerator(name = "organization", strategy = "uuid2")
	@Column(name = "Org_Id",updatable = false, nullable = true)//, columnDefinition = "BINARY(16)")
	private String orgId;

	@Column(name = "Org_Code", nullable = false, length = 10)
	private String orgCode;

	@Column(name = "Org_Name", nullable = false)
	private String orgName;

	@Column(name = "Active", nullable = false, length = 1)
	private Integer active;
	
	@OneToMany(mappedBy = "organization")
	private Set<Modules> modules;
	
	@OneToMany(mappedBy = "organization")
	private Set<Privileges> privileges;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Set<Modules> getModules() {
		return modules;
	}

	public void setModules(Set<Modules> modules) {
		this.modules = modules;
	}

	public Set<Privileges> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privileges> privileges) {
		this.privileges = privileges;
	}
}
