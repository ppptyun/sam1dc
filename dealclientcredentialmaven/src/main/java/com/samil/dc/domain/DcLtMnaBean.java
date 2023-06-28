package com.samil.dc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DcLtMnaBean implements Serializable {

	private static final long serialVersionUID = 6700682854658565590L;

	private String prjtcd = "";
	private String prjtnm = "";
	private String hqcd = "";
	private String hqnm = "";
	private String ptrempno = "";
	private String ptrempnm = "";
	private String ptrhqcd = "";
	private String mgrempno = "";
	private String mgrempnm = "";
	private String ctgcd = "";
	private String ctgnm = "";
	private String pdtcd = "";
	private String pdtnm = "";
	private String conscd = "";
	private String consnm = "";
	private String statuscd = "";
	private String statusnm = "";
	private String confcd = "";
	private String confnm = "";
	private String chrgconfcd = "";
	private String chrgconfnm = "";
	private String chrgconfempno = "";
	private String chrgconfempnm = "";
	private String chrgconfdt = "";
	private String comnm = "";
	private String consdt = "";
	private String dirtdt = "";
	private String stkhdt = "";
	private String mnadt = "";
	private String currcd = "";
	private String currnm = "";
	private String rate = "";
	private String amt = "";
	private String rateamt = "";
	private String borddealcd = "";
	private String borddealnm = "";
	private String dealcd1 = "";
	private String dealnm1 = "";
	private String dealcd2 = "";
	private String dealnm2 = "";
	private String dealcd3 = "";
	private String dealnm3 = "";
	private List<DcLtBizBean> merging = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> mergingfinance = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> mergingaudit = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> merginglaw = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> merged = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> mergedfinance = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> mergedaudit = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> mergedlaw = new ArrayList<DcLtBizBean>();
	private String upddt = "";
	
	public DcLtMnaBean() {
		
	}

	public String getPrjtcd() {
		return prjtcd;
	}

	public void setPrjtcd(String prjtcd) {
		this.prjtcd = prjtcd;
	}

	public String getPrjtnm() {
		return prjtnm;
	}

	public void setPrjtnm(String prjtnm) {
		this.prjtnm = prjtnm;
	}

	public String getHqcd() {
		return hqcd;
	}

	public void setHqcd(String hqcd) {
		this.hqcd = hqcd;
	}

	public String getHqnm() {
		return hqnm;
	}

	public void setHqnm(String hqnm) {
		this.hqnm = hqnm;
	}

	public String getPtrempno() {
		return ptrempno;
	}

	public void setPtrempno(String ptrempno) {
		this.ptrempno = ptrempno;
	}

	public String getPtrempnm() {
		return ptrempnm;
	}

	public void setPtrempnm(String ptrempnm) {
		this.ptrempnm = ptrempnm;
	}

	public String getMgrempno() {
		return mgrempno;
	}

	public void setMgrempno(String mgrempno) {
		this.mgrempno = mgrempno;
	}

	public String getMgrempnm() {
		return mgrempnm;
	}

	public void setMgrempnm(String mgrempnm) {
		this.mgrempnm = mgrempnm;
	}

	public String getCtgcd() {
		return ctgcd;
	}

	public void setCtgcd(String ctgcd) {
		this.ctgcd = ctgcd;
	}

	public String getCtgnm() {
		return ctgnm;
	}

	public void setCtgnm(String ctgnm) {
		this.ctgnm = ctgnm;
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

	public String getConscd() {
		return conscd;
	}

	public void setConscd(String conscd) {
		this.conscd = conscd;
	}

	public String getConsnm() {
		return consnm;
	}

	public void setConsnm(String consnm) {
		this.consnm = consnm;
	}

	public String getStatuscd() {
		return statuscd;
	}

	public void setStatuscd(String statuscd) {
		this.statuscd = statuscd;
	}

	public String getStatusnm() {
		return statusnm;
	}

	public void setStatusnm(String statusnm) {
		this.statusnm = statusnm;
	}

	public String getConfcd() {
		return confcd;
	}

	public void setConfcd(String confcd) {
		this.confcd = confcd;
	}

	public String getConfnm() {
		return confnm;
	}

	public void setConfnm(String confnm) {
		this.confnm = confnm;
	}

	public String getChrgconfcd() {
		return chrgconfcd;
	}

	public void setChrgconfcd(String chrgconfcd) {
		this.chrgconfcd = chrgconfcd;
	}

	public String getChrgconfnm() {
		return chrgconfnm;
	}

	public void setChrgconfnm(String chrgconfnm) {
		this.chrgconfnm = chrgconfnm;
	}

	public String getChrgconfempno() {
		return chrgconfempno;
	}

	public void setChrgconfempno(String chrgconfempno) {
		this.chrgconfempno = chrgconfempno;
	}

	public String getChrgconfempnm() {
		return chrgconfempnm;
	}

	public void setChrgconfempnm(String chrgconfempnm) {
		this.chrgconfempnm = chrgconfempnm;
	}

	public String getChrgconfdt() {
		return chrgconfdt;
	}

	public void setChrgconfdt(String chrgconfdt) {
		this.chrgconfdt = chrgconfdt;
	}

	public String getComnm() {
		return comnm;
	}

	public void setComnm(String comnm) {
		this.comnm = comnm;
	}

	public String getConsdt() {
		return consdt;
	}

	public void setConsdt(String consdt) {
		this.consdt = consdt;
	}

	public String getDirtdt() {
		return dirtdt;
	}

	public void setDirtdt(String dirtdt) {
		this.dirtdt = dirtdt;
	}

	public String getStkhdt() {
		return stkhdt;
	}

	public void setStkhdt(String stkhdt) {
		this.stkhdt = stkhdt;
	}

	public String getMnadt() {
		return mnadt;
	}

	public void setMnadt(String mnadt) {
		this.mnadt = mnadt;
	}

	public String getCurrcd() {
		return currcd;
	}

	public void setCurrcd(String currcd) {
		this.currcd = currcd;
	}

	public String getCurrnm() {
		return currnm;
	}

	public void setCurrnm(String currnm) {
		this.currnm = currnm;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getRateamt() {
		return rateamt;
	}

	public void setRateamt(String rateamt) {
		this.rateamt = rateamt;
	}

	public String getBorddealcd() {
		return borddealcd;
	}

	public void setBorddealcd(String borddealcd) {
		this.borddealcd = borddealcd;
	}

	public String getBorddealnm() {
		return borddealnm;
	}

	public void setBorddealnm(String borddealnm) {
		this.borddealnm = borddealnm;
	}

	public String getDealcd1() {
		return dealcd1;
	}

	public void setDealcd1(String dealcd1) {
		this.dealcd1 = dealcd1;
	}

	public String getDealcd2() {
		return dealcd2;
	}

	public void setDealcd2(String dealcd2) {
		this.dealcd2 = dealcd2;
	}

	public String getDealnm2() {
		return dealnm2;
	}

	public void setDealnm2(String dealnm2) {
		this.dealnm2 = dealnm2;
	}

	public String getDealcd3() {
		return dealcd3;
	}

	public void setDealcd3(String dealcd3) {
		this.dealcd3 = dealcd3;
	}

	public String getDealnm3() {
		return dealnm3;
	}

	public void setDealnm3(String dealnm3) {
		this.dealnm3 = dealnm3;
	}

	public List<DcLtBizBean> getMerging() {
		return merging;
	}

	public void setMerging(List<DcLtBizBean> merging) {
		this.merging = merging;
	}

	public List<DcLtBizBean> getMergingfinance() {
		return mergingfinance;
	}

	public void setMergingfinance(List<DcLtBizBean> mergingfinance) {
		this.mergingfinance = mergingfinance;
	}

	public List<DcLtBizBean> getMergingaudit() {
		return mergingaudit;
	}

	public void setMergingaudit(List<DcLtBizBean> mergingaudit) {
		this.mergingaudit = mergingaudit;
	}

	public List<DcLtBizBean> getMerginglaw() {
		return merginglaw;
	}

	public void setMerginglaw(List<DcLtBizBean> merginglaw) {
		this.merginglaw = merginglaw;
	}

	public List<DcLtBizBean> getMerged() {
		return merged;
	}

	public void setMerged(List<DcLtBizBean> merged) {
		this.merged = merged;
	}

	public List<DcLtBizBean> getMergedfinance() {
		return mergedfinance;
	}

	public void setMergedfinance(List<DcLtBizBean> mergedfinance) {
		this.mergedfinance = mergedfinance;
	}

	public List<DcLtBizBean> getMergedaudit() {
		return mergedaudit;
	}

	public void setMergedaudit(List<DcLtBizBean> mergedaudit) {
		this.mergedaudit = mergedaudit;
	}

	public List<DcLtBizBean> getMergedlaw() {
		return mergedlaw;
	}

	public void setMergedlaw(List<DcLtBizBean> mergedlaw) {
		this.mergedlaw = mergedlaw;
	}

	public String getUpddt() {
		return upddt;
	}

	public void setUpddt(String upddt) {
		this.upddt = upddt;
	}

	public String getDealnm1() {
		return dealnm1;
	}

	public void setDealnm1(String dealnm1) {
		this.dealnm1 = dealnm1;
	}

	public String getPtrhqcd() {
		return ptrhqcd;
	}

	public void setPtrhqcd(String ptrhqcd) {
		this.ptrhqcd = ptrhqcd;
	}
}
