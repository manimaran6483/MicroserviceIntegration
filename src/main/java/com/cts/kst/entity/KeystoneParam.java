package com.cts.kst.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cts.kst.controller.FlowComponent;
import com.cts.kst.controller.RouterComponent;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document("KeystoneParam")
public class KeystoneParam {

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Transient
	public static final String SEQUENCE_NAME = "KeystoneParam_sequence";

	@Id
	private String key;
	
	private Date createdDate;

	private List<FlowComponent> flows;

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

	@Override
	public String toString() {
		return "KeystoneParam [key=" + key + ", createdDate=" + createdDate + ", flows=" + flows + "]";
	}



}
