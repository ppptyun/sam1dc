package com.samil.dc.domain;

import java.io.Serializable;

public class AdminMailProfileBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1423368673808661624L;
	private String syscd 		= null;	// 시스템 코드(필수)
	private String mailid 		= null;	// 메일 ID(필수)
	private String subject 		= null;	// 메일 제목
	private String contents 	= null; // 메일 본문	
	private String des 			= null; // 메일 프로파일 설명
	private String useyn 		= null;	// 사용 여부(필수)
	private String created 		= null; // 작성일(YYYY-MM-DD HH24:Mi:SS)(필수)
	private String createdby 	= null;	// 작성자(필수)
	private String modified 	= null; // 수정일(YYYY-MM-DD HH24:Mi:SS)(필수)
	private String modefiedby	= null; // 수정자(필수)
	
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
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
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
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getModefiedby() {
		return modefiedby;
	}
	public void setModefiedby(String modefiedby) {
		this.modefiedby = modefiedby;
	}
	public boolean validate(){
		// 필수값 체크
		if( syscd == null || mailid == null || useyn==null){
			return false;
		}
		
		// 컬럼 사이즈 체크
		if( syscd.getBytes().length > 50 || 
			mailid.getBytes().length > 20 || 
			subject.getBytes().length > 100 ||
			des.getBytes().length > 200 ||
			useyn.getBytes().length > 1 
				){
			return false;
		}
		return true;
	}
}
