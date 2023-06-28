package com.samil.dc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DcLtRealBean implements Serializable {

	private static final long serialVersionUID = -43089056086362394L;

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
	private String spadt = "";
	private String moudt = "";
	private String closdt = "";
	private String currcd = "";
	private String currnm = "";
	private String rate = "";
	private String amt = "";
	private String rateamt = "";
	private String borddealcd = "";
	private String borddealnm = "";
	private List<DcLtBizBean> target = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> seller = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> sellfinance = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> sellaudit = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> selllaw = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> buyer = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> buyfinance = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> buyaudit = new ArrayList<DcLtBizBean>();
	private List<DcLtBizBean> buylaw = new ArrayList<DcLtBizBean>();
	private String upddt = "";
	
	public DcLtRealBean() {
		
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

	public String getSpadt() {
		return spadt;
	}

	public void setSpadt(String spadt) {
		this.spadt = spadt;
	}

	public String getMoudt() {
		return moudt;
	}

	public void setMoudt(String moudt) {
		this.moudt = moudt;
	}

	public String getClosdt() {
		return closdt;
	}

	public void setClosdt(String closdt) {
		this.closdt = closdt;
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

	public List<DcLtBizBean> getTarget() {
		return target;
	}

	public void setTarget(List<DcLtBizBean> target) {
		this.target = target;
	}

	public List<DcLtBizBean> getSeller() {
		return seller;
	}

	public void setSeller(List<DcLtBizBean> seller) {
		this.seller = seller;
	}

	public List<DcLtBizBean> getSellfinance() {
		return sellfinance;
	}

	public void setSellfinance(List<DcLtBizBean> sellfinance) {
		this.sellfinance = sellfinance;
	}

	public List<DcLtBizBean> getSellaudit() {
		return sellaudit;
	}

	public void setSellaudit(List<DcLtBizBean> sellaudit) {
		this.sellaudit = sellaudit;
	}

	public List<DcLtBizBean> getSelllaw() {
		return selllaw;
	}

	public void setSelllaw(List<DcLtBizBean> selllaw) {
		this.selllaw = selllaw;
	}

	public List<DcLtBizBean> getBuyer() {
		return buyer;
	}

	public void setBuyer(List<DcLtBizBean> buyer) {
		this.buyer = buyer;
	}

	public List<DcLtBizBean> getBuyfinance() {
		return buyfinance;
	}

	public void setBuyfinance(List<DcLtBizBean> buyfinance) {
		this.buyfinance = buyfinance;
	}

	public List<DcLtBizBean> getBuyaudit() {
		return buyaudit;
	}

	public void setBuyaudit(List<DcLtBizBean> buyaudit) {
		this.buyaudit = buyaudit;
	}

	public List<DcLtBizBean> getBuylaw() {
		return buylaw;
	}

	public void setBuylaw(List<DcLtBizBean> buylaw) {
		this.buylaw = buylaw;
	}

	public String getUpddt() {
		return upddt;
	}

	public void setUpddt(String upddt) {
		this.upddt = upddt;
	}

	public String getPtrhqcd() {
		return ptrhqcd;
	}

	public void setPtrhqcd(String ptrhqcd) {
		this.ptrhqcd = ptrhqcd;
	}
}
