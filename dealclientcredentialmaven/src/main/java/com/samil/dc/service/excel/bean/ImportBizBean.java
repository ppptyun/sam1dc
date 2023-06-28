package com.samil.dc.service.excel.bean;

import java.io.Serializable;

public class ImportBizBean implements Serializable {

	private static final long serialVersionUID = 2211973885315373989L;

	private String prjtcd = "";
	private String actcd = "";
	private String bizcd = "";
	private String seq = "1";
	private String bizdivcd = "";
	private String compcd = "";
	private String comphannm = "";
	private String compengnm = "";
	private String natcd = "";
	private String natnm = "";
	private String inducd = "";
	private String indunm = "";
	private String chrgempno1 = "";
	private String chrgempnm1 = "";
	private String dealnm = "";
	// 인수/매각자문-거래형태1
	private String dealitemcd = "";
	private String dealitemnm = "";
	
	public ImportBizBean() {
		
	}
	
	public ImportBizBean(String prjtcd) {
		this.prjtcd = prjtcd;
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

	public String getDealnm() {
		return dealnm;
	}

	public void setDealnm(String dealnm) {
		this.dealnm = dealnm;
	}

	public String getDealitemcd() {
		return dealitemcd;
	}

	public void setDealitemcd(String dealitemcd) {
		this.dealitemcd = dealitemcd;
	}

	public String getDealitemnm() {
		return dealitemnm;
	}

	public void setDealitemnm(String dealitemnm) {
		this.dealitemnm = dealitemnm;
	}

	@Override
	public String toString() {
		return "ImportBizBean [prjtcd=" + prjtcd + ", actcd=" + actcd + ", bizcd=" + bizcd + ", seq=" + seq + ", bizdivcd=" + bizdivcd + ", compcd=" + compcd + ", comphannm=" + comphannm + ", compengnm=" + compengnm + ", natcd=" + natcd + ", natnm=" + natnm + ", inducd=" + inducd + ", indunm=" + indunm + ", chrgempno1=" + chrgempno1 + ", chrgempnm1=" + chrgempnm1 + ", dealnm=" + dealnm + ", dealitemcd=" + dealitemcd + ", dealitemnm=" + dealitemnm + "]";
	}
}
