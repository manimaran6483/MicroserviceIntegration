package com.cts.kst.entity;

import org.springframework.data.annotation.Id;

public class DbSequence {
	
    @Id
    private String  id;
    private int seq;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
}