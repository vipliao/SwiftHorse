package com.firm.swifthorse.deliver.vo;

import java.math.BigDecimal;

import com.firm.swifthorse.base.vo.SuperVO;
import com.firm.swifthorse.business.vo.BusinessVO;
import com.firm.swifthorse.user.vo.UserVO;

public class DeliverVO extends SuperVO{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private String businessId;

	private int personNum;
	
	private BigDecimal provideCash;
	
	private BigDecimal recruitCash;
	
	private int isRead;
	private int state;
	private UserVO user;
	private BusinessVO business;

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

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public BusinessVO getBusiness() {
		return business;
	}

	public void setBusiness(BusinessVO business) {
		this.business = business;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
}
