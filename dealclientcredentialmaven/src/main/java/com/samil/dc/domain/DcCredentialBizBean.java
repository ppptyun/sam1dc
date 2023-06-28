package com.samil.dc.domain;

import java.io.Serializable;

public class DcCredentialBizBean implements Serializable {
	
	private static final long serialVersionUID = 650059349335919298L;

	private String prjtcd = "";
	private String bizcd = "";
	private String seq = "";
	private String bizdivcd = "";
	private String compcd = "";
	private String comphannm = "";
	private String compengnm = "";
	private String inducd = "";
	private String indunm = "";
	private String natcd = "";
	private String natnm = "";
	private String city = "";
	private String addr = "";

	public DcCredentialBizBean() {
		
	}

	public String getPrjtcd() {
		return prjtcd;
	}

	public void setPrjtcd(String prjtcd) {
		this.prjtcd = prjtcd;
	}

	public String getBizcd() {
		return bizcd;
	}

	public void setBizcd(String bizcd) {
		this.bizcd = bizcd;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getBizdivcd() {
		return bizdivcd;
	}

	public void setBizdivcd(String bizdivcd) {
		this.bizdivcd = bizdivcd;
	}

	public String getCompcd() {
		return compcd;
	}

	public void setCompcd(String compcd) {
		this.compcd = compcd;
	}

	public String getComphannm() {
		return comphannm;
	}

	public void setComphannm(String comphannm) {
		this.comphannm = comphannm;
	}

	public String getCompengnm() {
		return compengnm;
	}

	public void setCompengnm(String compengnm) {
		this.compengnm = compengnm;
	}

	public String getInducd() {
		return inducd;
	}

	public void setInducd(String inducd) {
		this.inducd = inducd;
	}

	public String getIndunm() {
		return indunm;
	}

	public void setIndunm(String indunm) {
		this.indunm = indunm;
	}

	public String getNatcd() {
		return natcd;
	}

	public void setNatcd(String natcd) {
		this.natcd = natcd;
	}

	public String getNatnm() {
		return natnm;
	}

	public void setNatnm(String natnm) {
		this.natnm = natnm;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "[prjtcd=" + prjtcd + ", bizcd=" + bizcd + ", seq=" + seq + ", bizdivcd=" + bizdivcd + ", compcd=" + compcd + ", comphannm=" + comphannm + ", compengnm=" + compengnm + ", inducd=" + inducd + ", indunm=" + indunm + ", natcd=" + natcd + ", natnm=" + natnm + ", city=" + city + ", addr=" + addr + "]";
	}
}
