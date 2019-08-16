package com.github.abhinavrohatgi30.models;

import java.util.List;

public class AuthorizationPolicyClaim {

	private String urlPattern;
	private String urlClaim;
	private List<String> userRoleClaim;

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	public String getUrlClaim() {
		return urlClaim;
	}

	public void setUrlClaim(String urlClaim) {
		this.urlClaim = urlClaim;
	}

	public List<String> getUserRoleClaim() {
		return userRoleClaim;
	}

	public void setUserRoleClaim(List<String> userRoleClaim) {
		this.userRoleClaim = userRoleClaim;
	}

}
