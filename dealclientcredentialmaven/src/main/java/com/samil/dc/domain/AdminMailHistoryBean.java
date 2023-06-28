package com.samil.dc.domain;

import java.io.Serializable;

public class AdminMailHistoryBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5792389445187605378L;
	private String mid	 		= null;	// 메일 ID(필수)
	private String sndemplno	= null;	// 발송인 사번
	private String kornm 		= null;	// 발송인 이름
	private String subject 		= null;	// 메일 제목
	private String contents 	= null; // 메일 본문
	private String snddate		= null; // 발송일
	
	
	public String getSnddate() {
		return snddate;
	}
	public void setSnddate(String snddate) {
		this.snddate = snddate;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getSndemplno() {
		return sndemplno;
	}
	public void setSndemplno(String sndemplno) {
		this.sndemplno = sndemplno;
	}
	public String getKornm() {
		return kornm;
	}
	public void setKornm(String kornm) {
		this.kornm = kornm;
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
	
}
