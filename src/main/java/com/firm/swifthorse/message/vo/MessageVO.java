package com.firm.swifthorse.message.vo;

import com.firm.swifthorse.base.vo.SuperVO;

public class MessageVO extends SuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String receiverId;
	private String senderId;
	private int isRead;
	private String msgTitle;
	private String msgBody;
	private String msgDealurl;
	private int msgType;
	private String msgParams;
	private int sendAll;

	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	public String getMsgDealurl() {
		return msgDealurl;
	}
	public void setMsgDealurl(String msgDealurl) {
		this.msgDealurl = msgDealurl;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getMsgParams() {
		return msgParams;
	}
	public void setMsgParams(String msgParams) {
		this.msgParams = msgParams;
	}
	public int getSendAll() {
		return sendAll;
	}
	public void setSendAll(int sendAll) {
		this.sendAll = sendAll;
	}

}
