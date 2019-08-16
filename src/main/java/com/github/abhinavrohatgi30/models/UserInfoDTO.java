package com.github.abhinavrohatgi30.models;

public class UserInfoDTO {
	
	private Long userId;
	private String userRole;
	private String userType;
	
	public UserInfoDTO(Long userId,String userType,String userRole) {
		this.userId = userId;
		this.userRole = userRole;
		this.userType = userType;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public String getUserType() {
		return userType;
	}

	
	
}
