package com.samil.dc.domain;

import java.io.Serializable;

public class DcCredentialProductBean implements Serializable {

	private static final long serialVersionUID = 4798758034098142432L;
	
	private String pdtcd = "";
	private String pdtnm = "";

	public DcCredentialProductBean() {
		
	}

	public String getPdtcd() {
		return pdtcd;
	}

	public void setPdtcd(String pdtcd) {
		this.pdtcd = pdtcd;
	}

	public String getPdtnm() {
		return pdtnm;
	}

	public void setPdtnm(String pdtnm) {
		this.pdtnm = pdtnm;
	}
}
