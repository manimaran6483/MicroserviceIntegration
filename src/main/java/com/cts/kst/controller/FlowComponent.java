package com.cts.kst.controller;

import java.util.List;

public class FlowComponent {

	private String flowId;
	private String type;
	private String endpoint;
	private String name;
	private List<RouterComponent> routers;
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "FlowComponent [flowId=" + flowId + ", type=" + type + ", endpoint=" + endpoint + ", name=" + name + "]";
	}
	public List<RouterComponent> getRouters() {
		return routers;
	}
	public void setRouters(List<RouterComponent> routers) {
		this.routers = routers;
	}
	
	
}
