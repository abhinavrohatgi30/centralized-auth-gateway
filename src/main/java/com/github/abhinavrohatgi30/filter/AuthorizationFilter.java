package com.github.abhinavrohatgi30.filter;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.github.abhinavrohatgi30.models.UrlPatternTree;
import com.github.abhinavrohatgi30.models.UrlPatternTreeNode;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import static com.github.abhinavrohatgi30.constants.FilterConstants.*;

@Component
public class AuthorizationFilter extends ZuulFilter{


	@Autowired
	private UrlPatternTree urlPatternTree;

	@Override
	public boolean shouldFilter() {
		final RequestContext requestContext = RequestContext.getCurrentContext();
		return requestContext.getBoolean(IS_CLAIM_VERIFICATION_REQUIRED);
	}

	@Override
	public Object run() throws ZuulException {
		final RequestContext requestContext = RequestContext.getCurrentContext();
		String requestURI = requestContext.getRequest().getRequestURI();
		Optional<UrlPatternTreeNode> claimNode = Optional
				.ofNullable(urlPatternTree.verifyIfRequestIsAuthorized(requestURI));
		if (claimNode.isPresent()) {
			String userRoleInToken = requestContext.get("userRole").toString();
			validateUserRoleClaim(userRoleInToken, claimNode.get());
			validateUrlClaim(requestURI, claimNode.get().getUrlClaimPattern(), requestContext);
			addClaimsToRequestAsHeaders(requestContext);
		}else {
			throw new ZuulException("Invalid Token", HttpStatus.FORBIDDEN.value(), "Invalid Route");
		}
		requestContext.put(IS_SESSION_UPDATE_REQUIRED, true);
		return null;
	}

	private void addClaimsToRequestAsHeaders(RequestContext requestContext) {
		requestContext.addZuulRequestHeader(USER_ID_CLAIM,requestContext.get(USER_ID_CLAIM).toString());
		requestContext.addZuulRequestHeader(USER_ROLE_CLAIM,requestContext.get(USER_ROLE_CLAIM).toString());
		requestContext.addZuulRequestHeader(USER_TYPE_CLAIM,requestContext.get(USER_TYPE_CLAIM).toString());
	}

	private void validateUrlClaim(String requestURI, String urlClaimPattern, RequestContext requestContext) throws ZuulException {
		String[] requestURIParts = requestURI.split("/");
		String[] urlClaimPatternParts = urlClaimPattern.split("/");
		if(requestURIParts.length == urlClaimPatternParts.length) {
			for(int i=1; i<urlClaimPatternParts.length; i++){
				String urlClaimPatternPart = urlClaimPatternParts[i];
				if(isClaim(urlClaimPatternPart)) {
					String correspondingPartInRequestURL = requestURIParts[i];
					String claimKey = urlClaimPatternPart.substring(1);
					if(!correspondingPartInRequestURL.equals(requestContext.get(claimKey).toString())) {
						throw new ZuulException("Invalid Token", HttpStatus.FORBIDDEN.value(), "Invalid User");
					}
				}
			}
		}
	}

	private boolean isClaim(String urlClaimPatternPart) {
		return urlClaimPatternPart.startsWith(":");
	}

	private void validateUserRoleClaim(String userRoleInToken, UrlPatternTreeNode claimNode) throws ZuulException {
		List<String> userRoleClaim = claimNode.getUserRoleClaim();
		if (userRoleClaim!=null && !userRoleClaim.contains(userRoleInToken)) {
			throw new ZuulException("Invalid Token", HttpStatus.FORBIDDEN.value(), "User Role is Invalid");
		}
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER - 2;
	}

}
