package com.github.abhinavrohatgi30.models;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationPolicy {

	private String route;
	private List<AuthorizationPolicyClaim> claims = new ArrayList<>();
	
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public List<AuthorizationPolicyClaim> getClaims() {
		return claims;
	}
	public void setClaims(List<AuthorizationPolicyClaim> claims) {
		this.claims = claims;
	}
}
