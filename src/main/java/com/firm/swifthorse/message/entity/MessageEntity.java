package com.firm.swifthorse.message.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.firm.swifthorse.base.entity.SuperEntity;

@Entity
@Table(name = "swift_message_info")
public class MessageEntity extends SuperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "receiver_id")
	private String receiverId;

	@Column(name = "sender_id")
	private String senderId;
	
	@Column(name="is_read")
	private int isRead;
	
	@Column(name="msg_title")
	private String msgTitle;
	
	@Column(name="msg_body")
	private String msgBody;
	
	@Column(name="msg_dealurl")
	private String msgDealurl;
	
	@Column(name="msg_type")
	private int msgType;
	
	@Column(name="msg_params")
	private String msgParams;
	
	@Column(name="send_all")
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
