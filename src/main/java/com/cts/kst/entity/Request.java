package com.cts.kst.entity;

public class Request {

	private String id;
	private String name;
	private String transactionId;
	private String createdDate;
	private String requestType;

	
	
	@Override
	public String toString() {
		return "Request [id=" + id + ", name=" + name + ", transactionId=" + transactionId + ", createdDate="
				+ createdDate + ", requestType=" + requestType + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	
}
