package com.firm.swifthorse.complaint.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.firm.swifthorse.base.entity.SuperEntity;

@Entity
@Table(name="swift_complaint_info")
public class ComplaintEntity extends SuperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="plaintiff_id")
	private String plaintiffId;
	@Column(name="defendant_id")
	private String defendantId;
	private String title;
	private String content;
	private int dr;
	public int getDr() {
		return dr;
	}
	public void setDr(int dr) {
		this.dr = dr;
	}
	public String getPlaintiffId() {
		return plaintiffId;
	}
	public void setPlaintiffId(String plaintiffId) {
		this.plaintiffId = plaintiffId;
	}
	public String getDefendantId() {
		return defendantId;
	}
	public void setDefendantId(String defendantId) {
		this.defendantId = defendantId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
