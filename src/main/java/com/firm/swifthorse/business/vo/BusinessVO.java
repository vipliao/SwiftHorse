package com.firm.swifthorse.business.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.firm.swifthorse.assessory.vo.AssessoryVO;
import com.firm.swifthorse.base.vo.SuperVO;

public class BusinessVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String position;
	private String userId;
	private String userName;
	private Date publishDate;
	private Date endDate;
	private String title;
	private int isUserOuth;
	private String tag;
	private String companyName;
	private int companyOwn;
	private int workType;
	private String workPlace;
	private int workArea;
	private int isRecom;
	private int recostFee;
	private BigDecimal recostProfit;
	private Date recostTime;
	private int personNum;
	private String personAgeGroup;
	private int personSex;
	private int personNation;
	private int personWorkYear;
	private String personOriginPlace;
	private int personTatoo;
	private BigDecimal personBasePay;
	private BigDecimal personPay;
	private int postType;
	private int payType;
	private int publishType;
	private String memo;
	private String monthGuarantee;
	private int isAgreeDeal;
	private BigDecimal agencyFee;
	private int education;
	private int  bagEats;
	private int bagBads;
	private int workTimeType;
	private int isGood;
	private String goodTags;
	
	private List<AssessoryVO> assessorys;

	
	
	public int getIsGood() {
		return isGood;
	}

	public void setIsGood(int isGood) {
		this.isGood = isGood;
	}

	public String getGoodTags() {
		return goodTags;
	}

	public void setGoodTags(String goodTags) {
		this.goodTags = goodTags;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIsUserOuth() {
		return isUserOuth;
	}

	public void setIsUserOuth(int isUserOuth) {
		this.isUserOuth = isUserOuth;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanyOwn() {
		return companyOwn;
	}

	public void setCompanyOwn(int companyOwn) {
		this.companyOwn = companyOwn;
	}

	public int getWorkType() {
		return workType;
	}

	public void setWorkType(int workType) {
		this.workType = workType;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public int getWorkArea() {
		return workArea;
	}

	public void setWorkArea(int workArea) {
		this.workArea = workArea;
	}

	public int getIsRecom() {
		return isRecom;
	}

	public void setIsRecom(int isRecom) {
		this.isRecom = isRecom;
	}

	public int getRecostFee() {
		return recostFee;
	}

	public void setRecostFee(int recostFee) {
		this.recostFee = recostFee;
	}

	public BigDecimal getRecostProfit() {
		return recostProfit;
	}

	public void setRecostProfit(BigDecimal recostProfit) {
		this.recostProfit = recostProfit;
	}

	public Date getRecostTime() {
		return recostTime;
	}

	public void setRecostTime(Date recostTime) {
		this.recostTime = recostTime;
	}

	public int getPersonNum() {
		return personNum;
	}

	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}

	public String getPersonAgeGroup() {
		return personAgeGroup;
	}

	public void setPersonAgeGroup(String personAgeGroup) {
		this.personAgeGroup = personAgeGroup;
	}

	public int getPersonSex() {
		return personSex;
	}

	public void setPersonSex(int personSex) {
		this.personSex = personSex;
	}

	public int getPersonNation() {
		return personNation;
	}

	public void setPersonNation(int personNation) {
		this.personNation = personNation;
	}

	public int getPersonWorkYear() {
		return personWorkYear;
	}

	public void setPersonWorkYear(int personWorkYear) {
		this.personWorkYear = personWorkYear;
	}

	public String getPersonOriginPlace() {
		return personOriginPlace;
	}

	public void setPersonOriginPlace(String personOriginPlace) {
		this.personOriginPlace = personOriginPlace;
	}

	public int getPersonTatoo() {
		return personTatoo;
	}

	public void setPersonTatoo(int personTatoo) {
		this.personTatoo = personTatoo;
	}

	
	public BigDecimal getPersonBasePay() {
		return personBasePay;
	}

	public void setPersonBasePay(BigDecimal personBasePay) {
		this.personBasePay = personBasePay;
	}

	public BigDecimal getPersonPay() {
		return personPay;
	}

	public void setPersonPay(BigDecimal personPay) {
		this.personPay = personPay;
	}

	public int getPostType() {
		return postType;
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getPublishType() {
		return publishType;
	}

	public void setPublishType(int publishType) {
		this.publishType = publishType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMonthGuarantee() {
		return monthGuarantee;
	}

	public void setMonthGuarantee(String monthGuarantee) {
		this.monthGuarantee = monthGuarantee;
	}

	public int getIsAgreeDeal() {
		return isAgreeDeal;
	}

	public void setIsAgreeDeal(int isAgreeDeal) {
		this.isAgreeDeal = isAgreeDeal;
	}

	public BigDecimal getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {
		this.agencyFee = agencyFee;
	}

	public List<AssessoryVO> getAssessorys() {
		return assessorys;
	}

	public void setAssessorys(List<AssessoryVO> assessorys) {
		this.assessorys = assessorys;
	}

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public int getBagEats() {
		return bagEats;
	}

	public void setBagEats(int bagEats) {
		this.bagEats = bagEats;
	}

	public int getBagBads() {
		return bagBads;
	}

	public void setBagBads(int bagBads) {
		this.bagBads = bagBads;
	}

	public int getWorkTimeType() {
		return workTimeType;
	}

	public void setWorkTimeType(int workTimeType) {
		this.workTimeType = workTimeType;
	}
	
	

}
