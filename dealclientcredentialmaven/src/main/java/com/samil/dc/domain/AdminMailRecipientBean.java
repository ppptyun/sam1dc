package com.samil.dc.domain;

import java.io.Serializable;

public class AdminMailRecipientBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 763615457041957344L;
	private String syscd 		= null;	// 시스템 코드(필수)
	private String mailid 		= null;	// 메일 ID(필수)
	private String rid			= null; // 사용자 ID
	private String rname		= null; // 사용자 이름
	private String rmail		= null; // 사용자 메일(필수)
	private String created		= null; // 작성일
	private String createdby	= null; // 작성자
	
	public String getSyscd() {
		return syscd;
	}
	public void setSyscd(String syscd) {
		this.syscd = syscd;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getRmail() {
		return rmail;
	}
	public void setRmail(String rmail) {
		this.rmail = rmail;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	
	
	
}
