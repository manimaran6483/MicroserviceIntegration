package com.cts.kst.service;

import java.util.List;

import com.cts.kst.entity.KeystoneParam;

public interface KeystoneService {

	public KeystoneParam saveKeystoneParam(KeystoneParam param);
	
	public List<KeystoneParam> getAllKeystoneParams();
	
}
