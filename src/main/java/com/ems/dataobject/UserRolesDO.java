package com.ems.dataobject;

import java.util.List;


public class UserRolesDO {
	
	Long id;
	String roleName;
	List<PrivilagesDO> privilages;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<PrivilagesDO> getPrivilages() {
		return privilages;
	}
	public void setPrivilages(List<PrivilagesDO> privilages) {
		this.privilages = privilages;
	}
	

}
