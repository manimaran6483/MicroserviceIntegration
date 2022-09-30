package com.cts.kst.controller;

public class RouterComponent {

	private String routerFlowId;
	private String attribute;
	private String attributeValue;
	private String attributeName;
	public String getRouterFlowId() {
		return routerFlowId;
	}
	public void setRouterFlowId(String routerFlowId) {
		this.routerFlowId = routerFlowId;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public String getName() {
		return attributeName;
	}
	public void setName(String name) {
		this.attributeName = name;
	}
	@Override
	public String toString() {
		return "RouterComponent [routerFlowId=" + routerFlowId + ", attribute=" + attribute + ", attributeValue="
				+ attributeValue + ", attributeName=" + attributeName + "]";
	}
	
	
	
}
