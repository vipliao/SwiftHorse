package com.firm.swifthorse.deliver.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.firm.swifthorse.base.entity.SuperEntity;


@Entity
@Table(name="swift_deliver")
public class DeliverEntity extends SuperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="user_id")
	private String userId;
	@Column(name="business_id")
	private String businessId;
	@Column(name="person_num")
	private int personNum;
	@Column(name="provide_cash")
	private BigDecimal provideCash;
	@Column(name="recruit_cash")
	private BigDecimal recruitCash;
	@Column(name="is_read")
	private int isRead;
	private int state;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	public BigDecimal getProvideCash() {
		return provideCash;
	}
	public void setProvideCash(BigDecimal provideCash) {
		this.provideCash = provideCash;
	}
	public BigDecimal getRecruitCash() {
		return recruitCash;
	}
	public void setRecruitCash(BigDecimal recruitCash) {
		this.recruitCash = recruitCash;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
	

}
