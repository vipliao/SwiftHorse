package com.firm.swifthorse.user.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.firm.swifthorse.assessory.vo.AssessoryVO;
import com.firm.swifthorse.base.vo.SuperVO;

public class UserVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;
	private String picSrc;
	private int signNum;
	private String recomCode;
	private String openId;
	private String phone;
	private String password;
	private int points;
	private int isAuth;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginTime;
	private int dailyActivity;// 日活量
	private int userType;
	private int level;
	private int dr;

	private int age;

	private int personOriginPlace;

	private int personNation;

	private int personWorkYear;
	private int education;
	private int workType;

	private int jobState;

	private int personTatoo;

	private int personSex;

	private int postType;

	private List<AssessoryVO> assessorys;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPicSrc() {
		return picSrc;
	}

	public void setPicSrc(String picSrc) {
		this.picSrc = picSrc;
	}

	public String getRecomCode() {
		return recomCode;
	}

	public void setRecomCode(String recomCode) {
		this.recomCode = recomCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(int isAuth) {
		this.isAuth = isAuth;
	}

	public int getSignNum() {
		return signNum;
	}

	public void setSignNum(int signNum) {
		this.signNum = signNum;
	}

	public List<AssessoryVO> getAssessorys() {
		return assessorys;
	}

	public void setAssessorys(List<AssessoryVO> assessorys) {
		this.assessorys = assessorys;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getDailyActivity() {
		return dailyActivity;
	}

	public void setDailyActivity(int dailyActivity) {
		this.dailyActivity = dailyActivity;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDr() {
		return dr;
	}

	public void setDr(int dr) {
		this.dr = dr;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPersonOriginPlace() {
		return personOriginPlace;
	}

	public void setPersonOriginPlace(int personOriginPlace) {
		this.personOriginPlace = personOriginPlace;
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

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public int getWorkType() {
		return workType;
	}

	public void setWorkType(int workType) {
		this.workType = workType;
	}

	public int getJobState() {
		return jobState;
	}

	public void setJobState(int jobState) {
		this.jobState = jobState;
	}

	public int getPersonTatoo() {
		return personTatoo;
	}

	public void setPersonTatoo(int personTatoo) {
		this.personTatoo = personTatoo;
	}

	public int getPersonSex() {
		return personSex;
	}

	public void setPersonSex(int personSex) {
		this.personSex = personSex;
	}

	public int getPostType() {
		return postType;
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}
	
	

}
