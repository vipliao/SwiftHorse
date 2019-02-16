package com.firm.swifthorse.business.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.firm.swifthorse.base.entity.SuperEntity;

@Entity
@Table(name = "swift_business_info")
public class BusinessEntity extends SuperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int position;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "publish_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date publishDate;
	@Column(name = "end_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endDate;
	private String title;
	@Column(name = "is_user_outh")
	private int isUserOuth;
	private String tag;
	@Column(name = "company_name")
	private String companyName;
	@Column(name = "company_own")
	private int companyOwn;
	@Column(name = "work_type")
	private int workType;
	@Column(name = "work_place")
	private String workPlace;
	@Column(name = "work_area")
	private int workArea;
	@Column(name = "is_recom")
	private int isRecom;
	@Column(name = "recost_fee")
	private int recostFee;
	@Column(name = "recost_profit")
	private BigDecimal recostProfit;
	@Column(name = "recost_time")
	private Date recostTime;
	@Column(name = "person_num")
	private int personNum;
	@Column(name = "person_age_group")
	private String personAgeGroup;
	@Column(name = "person_sex")
	private int personSex;
	@Column(name = "person_nation")
	private int personNation;
	@Column(name = "person_work_year")
	private int personWorkYear;
	@Column(name = "person_origin_place")
	private String personOriginPlace;
	@Column(name = "person_tatoo")
	private int personTatoo;

	@Column(name = "person_base_pay")
	private BigDecimal personBasePay;
	@Column(name = "person_pay")
	private BigDecimal personPay;
	@Column(name = "post_type")
	private int postType;
	@Column(name = "pay_type")
	private int payType;
	@Column(name = "publish_type")
	private int publishType;
	private String memo;
	@Column(name = "month_guarantee")
	private String monthGuarantee;
	@Column(name = "is_agree_deal")
	private int isAgreeDeal;
	@Column(name = "agency_fee")
	private BigDecimal agencyFee;
	@Column(name = "bag_eats")
	private int  bagEats;
	@Column(name = "bag_bads")
	private int bagBads;
	@Column(name="work_time_type")
	private int workTimeType;
	
	private int education;
	
	@Column(name="is_good")
	private int isGood;
	
	@Column(name="good_tags")
	private String goodTags;
	 

	
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
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
