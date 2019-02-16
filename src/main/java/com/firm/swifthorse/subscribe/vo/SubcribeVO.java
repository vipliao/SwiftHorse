package com.firm.swifthorse.subscribe.vo;

import com.firm.swifthorse.base.vo.SuperVO;

public class SubcribeVO extends SuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String businessId;
	private int businessType;
	private String userId;
	private int type;
	
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public int getBusinessType() {
		return businessType;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	
}
