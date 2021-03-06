package com.cxp.exception;

public class NoFindException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resourceType;
	private String resource;
	public NoFindException(String resourceType, String resource) {
		this.resourceType = resourceType;
		this.resource = resource;
	}
	public NoFindException(String resourceType, int resource) {
		this.resourceType = resourceType;
		this.resource = String.valueOf(resource);
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
}
