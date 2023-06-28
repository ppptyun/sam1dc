package com.samil.dc.domain;

import java.io.Serializable;

public class NationSearchListBean implements Serializable {

	private static final long serialVersionUID = 5749434823245767277L;
	
	private String etcocd = "";
	private String etcdnm = "";
	
	public NationSearchListBean() {
	}

	public String getEtcocd() {
		return etcocd;
	}

	public void setEtcocd(String etcocd) {
		this.etcocd = etcocd;
	}

	public String getEtcdnm() {
		return etcdnm;
	}

	public void setEtcdnm(String etcdnm) {
		this.etcdnm = etcdnm;
	}

}
