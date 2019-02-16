package com.firm.swifthorse.complaint.vo;

import java.util.List;

import com.firm.swifthorse.assessory.vo.AssessoryVO;
import com.firm.swifthorse.base.vo.SuperVO;

public class ComplaintVO extends SuperVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String plaintiffId;
	private String defendantId;
	private String title;
	private String content;
	private int dr;
	private List<AssessoryVO> assessorys;
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
	public List<AssessoryVO> getAssessorys() {
		return assessorys;
	}
	public void setAssessorys(List<AssessoryVO> assessorys) {
		this.assessorys = assessorys;
	}
	public int getDr() {
		return dr;
	}
	public void setDr(int dr) {
		this.dr = dr;
	}
	
	
}
