package com.escola.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class UserRolesPK implements Serializable{

	private static final long serialVersionUID = 8642446270721442496L;
		
	@Column(name="User_Id")
	private String userId;
	
	@Column(name="Role_Id")
	private Integer roleId;
		
	public UserRolesPK() {
	}
	
	public UserRolesPK(User user,Roles roles) {
		this.userId=""+user.getUserId();
		this.roleId=roles.getRoleId();
	}
		
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRolesPK)) return false;
        UserRolesPK that = (UserRolesPK) o;
        return Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getRoleId(), that.getRoleId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getRoleId());
    }
}
