package com.github.abhinavrohatgi30.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.abhinavrohatgi30.config.AuthorizationConfig;
import com.github.abhinavrohatgi30.utils.UrlPatternTreeNodeUtils;

@Component
public class UrlPatternTree {

	private UrlPatternTreeNode rootNode;
	
	@Autowired
	public UrlPatternTree(AuthorizationConfig authorizationConfig) {
		rootNode = new UrlPatternTreeNode(null, "root");
		List<AuthorizationPolicy> claimRoutes = authorizationConfig.getPolicies();
		for(AuthorizationPolicy claimRoute : claimRoutes) {
			String prefix = claimRoute.getRoute();
			UrlPatternTreeNode routeNode = UrlPatternTreeNodeUtils.addNode(prefix,rootNode);
			List<AuthorizationPolicyClaim> claimPatterns = claimRoute.getClaims();
			for(AuthorizationPolicyClaim claimPattern : claimPatterns) {
				String urlPattern = claimPattern.getUrlPattern();
				String urlClaim = claimPattern.getUrlClaim();
				List<String> userRoleClaim = claimPattern.getUserRoleClaim();
				UrlPatternTreeNodeUtils.addUrlNode(urlPattern, userRoleClaim, prefix + urlClaim, routeNode);
			}
		}
	}	
	
	
	public UrlPatternTreeNode verifyIfRequestIsAuthorized(String requestURI) {
		return UrlPatternTreeNodeUtils.getUrlNode(requestURI, rootNode);
	}
}
