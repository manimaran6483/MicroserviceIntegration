package com.cts.kst.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cts.kst.controller.FlowComponent;
import com.cts.kst.controller.RouterComponent;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document("KeystoneParam")
public class KeystoneParam {

	@Transient
	public static final String SEQUENCE_NAME = "KeystoneParam_sequence";

	@JsonIgnore
	@Id
	private String id;

	private List<FlowComponent> flows;

	private List<RouterComponent> routers;

	private String key;

	public List<FlowComponent> getFlows() {
		return flows;
	}

	public void setFlows(List<FlowComponent> flows) {
		this.flows = flows;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<RouterComponent> getRouters() {
		return routers;
	}

	public void setRouters(List<RouterComponent> routers) {
		this.routers = routers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "KeystoneParam [flows=" + flows + "]";
	}

}
