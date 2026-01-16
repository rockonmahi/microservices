package com.escola.userservice.entity;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name="User_Details")
public class UserDetails extends AuditEntity implements Serializable , BaseEntity{

	private static final long serialVersionUID = 1703035331823216982L;
	
	public UserDetails() {
		super.setRecordStatus(1);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Seq_Id")
	private Integer seqId;

	@Column(name="First_Name", nullable = false, length = 10)
	private String firstName;
	
	@Column(name="Middle_Name", nullable = false, length = 10)
	private String middleName;
	
	@Column(name="Last_Name", nullable = false, length = 10)
	private String lastName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="User_Id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


}