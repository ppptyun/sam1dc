package com.samil.dc.domain;

import java.io.Serializable;

public class RTMSMailBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2919685944571138471L;
	private String mid = "";	// 메일 발송 ID
	private RTMSUserBean sender = null;
	private String subject = "";	// 메일 제목
	private String contents = "";	// 메일 본문
	
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public RTMSUserBean getSender() {
		return sender;
	}
	public void setSender(RTMSUserBean sender) {
		this.sender = sender;
	}
}
