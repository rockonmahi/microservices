package com.escola.userservice.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="User_Login_History")
public class UserLoginHistory extends AuditEntity implements Serializable , BaseEntity{

	private static final long serialVersionUID = -1697962340729576327L;
	
	public UserLoginHistory() {
		super.setRecordStatus(1);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="History_Id", nullable = false, length = 10)
	private Integer historyId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "User_Id")
	private User user;
	
	@Column(name="System_Ip", nullable = false, length = 10)
	private String systemIp;

	public User getUser() {
		return user;
	}

	public void setUser(User loginUser) {
		this.user = user;
	}

	public String getSystemIp() {
		return systemIp;
	}

	public void setSystemIp(String systemIp) {
		this.systemIp = systemIp;
	}

	public Integer getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
}
