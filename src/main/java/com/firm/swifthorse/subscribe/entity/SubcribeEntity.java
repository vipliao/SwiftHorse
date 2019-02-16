package com.firm.swifthorse.subscribe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.firm.swifthorse.base.entity.SuperEntity;

@Entity
@Table(name="swift_subscribe")
public class SubcribeEntity extends SuperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="business_id")
	private String businessId;
	@Column(name="business_type")
	private int businessType;
	@Column(name="user_id")
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
