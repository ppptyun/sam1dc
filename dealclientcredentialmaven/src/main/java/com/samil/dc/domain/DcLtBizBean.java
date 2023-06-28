package com.samil.dc.domain;

import java.io.Serializable;

public class DcLtBizBean implements Serializable {

	private static final long serialVersionUID = 6561952762306044808L;

	private String prjtcd = "";
	private String actcd = "";
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
	private String chrgempno1 = "";
	private String chrgempnm1 = "";
	private String chrgempno2 = "";
	private String chrgempnm2 = "";
	private String dealnm = "";
	
	public DcLtBizBean() {
		
	}

	public String getPrjtcd() {
		return prjtcd;
	}

	public void setPrjtcd(String prjtcd) {
		this.prjtcd = prjtcd;
	}

	public String getActcd() {
		return actcd;
	}

	public void setActcd(String actcd) {
		this.actcd = actcd;
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

	public String getChrgempno1() {
		return chrgempno1;
	}

	public void setChrgempno1(String chrgempno1) {
		this.chrgempno1 = chrgempno1;
	}

	public String getChrgempnm1() {
		return chrgempnm1;
	}

	public void setChrgempnm1(String chrgempnm1) {
		this.chrgempnm1 = chrgempnm1;
	}

	public String getChrgempno2() {
		return chrgempno2;
	}

	public void setChrgempno2(String chrgempno2) {
		this.chrgempno2 = chrgempno2;
	}

	public String getChrgempnm2() {
		return chrgempnm2;
	}

	public void setChrgempnm2(String chrgempnm2) {
		this.chrgempnm2 = chrgempnm2;
	}

	public String getDealnm() {
		return dealnm;
	}

	public void setDealnm(String dealnm) {
		this.dealnm = dealnm;
	}

	@Override
	public String toString() {
		return "[prjtcd=" + prjtcd + ", actcd=" + actcd + ", bizcd=" + bizcd + ", seq=" + seq + ", bizdivcd=" + bizdivcd + ", compcd=" + compcd + ", comphannm=" + comphannm + ", compengnm=" + compengnm + ", inducd=" + inducd + ", indunm=" + indunm + ", natcd=" + natcd + ", natnm=" + natnm + ", city=" + city + ", addr=" + addr + ", chrgempno1=" + chrgempno1 + ", chrgempnm1=" + chrgempnm1 + ", chrgempno2=" + chrgempno2 + ", chrgempnm2=" + chrgempnm2 + ", dealnm=" + dealnm + "]";
	}
}
