package com.samil.dc.service.excel.bean;

import java.io.Serializable;

public class ImportExceptCredentialBean implements Serializable {
	
	private static final long serialVersionUID = 8619113973812696945L;
	
	private int row = 0;
	private String prjtcd = "";
	private String credtgtcd = "";
	
	public ImportExceptCredentialBean(int row, String prjtcd) {
		this.row = row;
		this.prjtcd = prjtcd;
	}

	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getPrjtcd() {
		return prjtcd;
	}
	public void setPrjtcd(String prjtcd) {
		this.prjtcd = prjtcd;
	}
	public String getCredtgtcd() {
		return credtgtcd;
	}
	public void setCredtgtcd(String credtgtcd) {
		this.credtgtcd = credtgtcd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ImportBizBean [prjtcd=" + prjtcd + ", credtgtcd=" + credtgtcd  + "]";
	}
}
