package com.github.abhinavrohatgi30.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlPatternTreeNode {

	private UrlPatternTreeNode parent;
	private String key;
	private Map<String, UrlPatternTreeNode> paths = new HashMap<>();
	private Map<String, UrlPatternTreeNode> patterns = new HashMap<>();
	private boolean isUrlNode = false;
	private String urlClaimPattern;
	private List<String> userRoleClaim = new ArrayList<>();
	
	public UrlPatternTreeNode(UrlPatternTreeNode parent, String key) {
		this.parent = parent;
		this.key = key;
	}
	
	public UrlPatternTreeNode(UrlPatternTreeNode parent, String key, Map<String, UrlPatternTreeNode> paths, Map<String, UrlPatternTreeNode> patterns, boolean isLeafNode, String urlClaimPattern, List<String> userRoleClaim) {
		this(parent, key);
		this.paths = paths;
		this.patterns = patterns;
		this.isUrlNode = isLeafNode;
		this.urlClaimPattern = urlClaimPattern;
		this.userRoleClaim = userRoleClaim;
	}
	

	public UrlPatternTreeNode getParent() {
		return parent;
	}

	public void setParent(UrlPatternTreeNode parent) {
		this.parent = parent;
	}

	public Map<String, UrlPatternTreeNode> getPaths() {
		return paths;
	}

	public void setPaths(Map<String, UrlPatternTreeNode> paths) {
		this.paths = paths;
	}
	

	public boolean isUrlNode() {
		return isUrlNode;
	}

	public void setUrlNode(boolean isUrlNode) {
		this.isUrlNode = isUrlNode;
	}

	public String getUrlClaimPattern() {
		return urlClaimPattern;
	}

	public void setUrlClaimPattern(String urlClaimPattern) {
		this.urlClaimPattern = urlClaimPattern;
	}
	
	
	public Map<String, UrlPatternTreeNode> getPatterns() {
		return patterns;
	}

	public void setPatterns(Map<String, UrlPatternTreeNode> patterns) {
		this.patterns = patterns;
	}

	public List<String> getUserRoleClaim() {
		return userRoleClaim;
	}

	public void setUserRoleClaim(List<String> userRoleClaim) {
		this.userRoleClaim = userRoleClaim;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}

	