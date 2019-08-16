package com.github.abhinavrohatgi30.utils;

import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.github.abhinavrohatgi30.models.UrlPatternTreeNode;

public class UrlPatternTreeNodeUtils {

	private UrlPatternTreeNodeUtils() {
		
	}

	public static UrlPatternTreeNode getUrlNode(String url, UrlPatternTreeNode currentNode){
		UrlPatternTreeNode urlNodeCandidate = getNode(url,currentNode);
		if(urlNodeCandidate!=null && urlNodeCandidate.isUrlNode()) {
			return urlNodeCandidate;
		}
		return null;
	}
	
	
	public static UrlPatternTreeNode getNode(String url, UrlPatternTreeNode currentNode){
		String[] urlParts = url.split("/");
		for(int i=1;i<urlParts.length;i++) {
			String urlPart = urlParts[i];
			currentNode = getNextNode(urlPart,currentNode);
			if(currentNode == null)
				return currentNode;
		}
		return currentNode;
	}
	
	private static UrlPatternTreeNode getNextNode(String urlPart, UrlPatternTreeNode currentNode) {
		UrlPatternTreeNode nextNode = currentNode.getPaths().get(urlPart);
		if(nextNode == null){
			for(Entry<String,UrlPatternTreeNode> pattern : currentNode.getPatterns().entrySet()) {
				if(Pattern.matches(pattern.getKey(), urlPart) || pattern.getKey().equals(urlPart)) {
					nextNode = pattern.getValue();
					break;
				}
			}
		}
		return nextNode;
	}
	

	public static void addUrlNode(String url, List<String> userRoleClaim, String urlClaim, UrlPatternTreeNode currentNode){
		UrlPatternTreeNode childNode = addNode(url, currentNode);
		childNode.setUrlNode(true);
		childNode.setUrlClaimPattern(urlClaim);
		childNode.setUserRoleClaim(userRoleClaim);
	}
	
	public static UrlPatternTreeNode addNode(String url, UrlPatternTreeNode currentNode){
		String[] urlParts = url.split("/");
		for(int i=1; i<urlParts.length;i++) {
			String urlPart = urlParts[i];
			UrlPatternTreeNode nextNode = getNextNode(urlPart, currentNode);
			if(nextNode == null) {
				nextNode = new UrlPatternTreeNode(currentNode, urlPart);
				if(!isRegex(urlPart))
					currentNode.getPaths().put(urlPart, nextNode);
				else
					currentNode.getPatterns().put(urlPart, nextNode);
			}
			currentNode = nextNode;
		}
		return currentNode;
	}
	
	
	
	private static boolean isRegex(String urlPart) {
		return (urlPart.contains("[") && urlPart.contains("]"));
	}


	public static void printUrlClaimTree(UrlPatternTreeNode claimNode) {
		claimNode.toString();
	}
}
