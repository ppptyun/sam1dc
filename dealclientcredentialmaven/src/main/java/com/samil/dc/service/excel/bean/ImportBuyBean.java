package com.samil.dc.service.excel.bean;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.util.Constants;

public class ImportBuyBean implements Serializable {
	
	private static final long serialVersionUID = -7346936279187232859L;

	private int row = 0;
	private boolean valid = true;
	private String conscd = Constants.Consult.BUY;
	private String refyearly = "";
	private String yearly = "";
	private String prjtcd = "";
	
	private String prjtcd1 = "";
	private String prjtcd2 = "";
	private String prjtcd3 = "";
	private String credtgtcd = "103201";//Credential 대상여부
	
	private String prjtnm = "";
	private String hqcd = "";
	private String hqnm = "";
	private String ptrempno = "";
	private String mgrempno = "";
	private String processnm = "";
	private String contdt = "";
	private String termidt = "";
	private String pdtcd = "";
	private String pdtnm = "";
	private String buyoutcd = "";
	private String comnm = "";
	private String statuscd = "";
	private String trgtetc = "";
	
	private String consdt = "";
	private String spadt = "";
	private String moudt = "";
	private String closdt = "";
	
	private String currcd = "";
	private String currnm = "";
	private String rate = "";
	private String amt = "";
	private String borddealcd = "";
	private String borddealnm = "";
	private String dealcd2 = "";
	private String dealnm2 = "";
	private String dealcd3 = "";
	private String dealnm3 = "";
	
	private ImportBizBean client = new ImportBizBean();
	private ImportBizBean target = new ImportBizBean();
	private ImportBizBean seller = new ImportBizBean();
	private ImportBizBean sellfinance = new ImportBizBean();
	private ImportBizBean sellaudit = new ImportBizBean();
	private ImportBizBean selllaw = new ImportBizBean();
	private ImportBizBean buyer = new ImportBizBean();
	private ImportBizBean buyfinance = new ImportBizBean();
	private ImportBizBean buyaudit = new ImportBizBean();
	private ImportBizBean buylaw = new ImportBizBean();
	private ImportBizBean deal1 = new ImportBizBean();
	
	private String paycd = "";
	private String chamtw = "";
	private String cisno = "";
	private String cisno1 = "";
	private String cisno2 = "";
	private String cisno3 = "";

	public ImportBuyBean(int row, String refyearly, String prjtcd) {
		this.row = row;
		this.refyearly = refyearly;
		this.prjtcd = prjtcd;
		
		String[] arrPrjtcd = prjtcd.split("-");
		if (!StringUtils.isBlank(prjtcd) && arrPrjtcd.length == 3) {
			this.prjtcd1 = arrPrjtcd[0];
			this.prjtcd2 = arrPrjtcd[1];
			this.prjtcd3 = arrPrjtcd[2];
		}
		
		client = new ImportBizBean(prjtcd);
		target = new ImportBizBean(prjtcd);
		seller = new ImportBizBean(prjtcd);
		sellfinance = new ImportBizBean(prjtcd);
		sellaudit = new ImportBizBean(prjtcd);
		selllaw = new ImportBizBean(prjtcd);
		buyer = new ImportBizBean(prjtcd);
		buyfinance = new ImportBizBean(prjtcd);
		buyaudit = new ImportBizBean(prjtcd);
		buylaw = new ImportBizBean(prjtcd);
		deal1 = new ImportBizBean(prjtcd);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getConscd() {
		return conscd;
	}

	public void setConscd(String conscd) {
		this.conscd = conscd;
	}

	public String getRefyearly() {
		return refyearly;
	}

	public void setRefyearly(String refyearly) {
		this.refyearly = refyearly;
	}

	public String getYearly() {
		return yearly;
	}

	public void setYearly(String yearly) {
		this.yearly = yearly;
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

	public String getMgrempno() {
		return mgrempno;
	}

	public void setMgrempno(String mgrempno) {
		this.mgrempno = mgrempno;
	}

	public String getContdt() {
		return contdt;
	}

	public void setContdt(String contdt) {
		this.contdt = contdt;
	}

	public String getTermidt() {
		return termidt;
	}

	public void setTermidt(String termidt) {
		this.termidt = termidt;
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

	public String getBuyoutcd() {
		return buyoutcd;
	}

	public void setBuyoutcd(String buyoutcd) {
		this.buyoutcd = buyoutcd;
	}

	public String getComnm() {
		return comnm;
	}

	public void setComnm(String comnm) {
		this.comnm = comnm;
	}

	public String getStatuscd() {
		return statuscd;
	}

	public void setStatuscd(String statuscd) {
		this.statuscd = statuscd;
	}

	public String getTrgtetc() {
		return trgtetc;
	}

	public void setTrgtetc(String trgtetc) {
		this.trgtetc = trgtetc;
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

	public ImportBizBean getClient() {
		return client;
	}

	public void setClient(ImportBizBean client) {
		this.client = client;
	}

	public ImportBizBean getTarget() {
		return target;
	}

	public void setTarget(ImportBizBean target) {
		this.target = target;
	}

	public ImportBizBean getSeller() {
		return seller;
	}

	public void setSeller(ImportBizBean seller) {
		this.seller = seller;
	}

	public ImportBizBean getSellfinance() {
		return sellfinance;
	}

	public void setSellfinance(ImportBizBean sellfinance) {
		this.sellfinance = sellfinance;
	}

	public ImportBizBean getSellaudit() {
		return sellaudit;
	}

	public void setSellaudit(ImportBizBean sellaudit) {
		this.sellaudit = sellaudit;
	}

	public ImportBizBean getSelllaw() {
		return selllaw;
	}

	public void setSelllaw(ImportBizBean selllaw) {
		this.selllaw = selllaw;
	}

	public ImportBizBean getBuyer() {
		return buyer;
	}

	public void setBuyer(ImportBizBean buyer) {
		this.buyer = buyer;
	}

	public ImportBizBean getBuyfinance() {
		return buyfinance;
	}

	public void setBuyfinance(ImportBizBean buyfinance) {
		this.buyfinance = buyfinance;
	}

	public ImportBizBean getBuyaudit() {
		return buyaudit;
	}

	public void setBuyaudit(ImportBizBean buyaudit) {
		this.buyaudit = buyaudit;
	}

	public ImportBizBean getBuylaw() {
		return buylaw;
	}

	public void setBuylaw(ImportBizBean buylaw) {
		this.buylaw = buylaw;
	}

	public ImportBizBean getDeal1() {
		return deal1;
	}

	public void setDeal1(ImportBizBean deal1) {
		this.deal1 = deal1;
	}

	public String getPrjtcd1() {
		return prjtcd1;
	}

	public void setPrjtcd1(String prjtcd1) {
		this.prjtcd1 = prjtcd1;
	}

	public String getPrjtcd2() {
		return prjtcd2;
	}

	public void setPrjtcd2(String prjtcd2) {
		this.prjtcd2 = prjtcd2;
	}

	public String getPrjtcd3() {
		return prjtcd3;
	}

	public void setPrjtcd3(String prjtcd3) {
		this.prjtcd3 = prjtcd3;
	}

	public String getCredtgtcd() {
		return credtgtcd;
	}

	public void setCredtgtcd(String credtgtcd) {
		this.credtgtcd = credtgtcd;
	}

	public String getProcessnm() {
		return processnm;
	}

	public void setProcessnm(String processnm) {
		this.processnm = processnm;
	}

	public String getPaycd() {
		return paycd;
	}

	public void setPaycd(String paycd) {
		this.paycd = paycd;
	}

	public String getCisno() {
		return cisno;
	}

	public void setCisno(String cisno) {
		this.cisno = cisno;
	}

	public String getCisno1() {
		return cisno1;
	}

	public void setCisno1(String cisno1) {
		this.cisno1 = cisno1;
	}

	public String getCisno2() {
		return cisno2;
	}

	public void setCisno2(String cisno2) {
		this.cisno2 = cisno2;
	}

	public String getCisno3() {
		return cisno3;
	}

	public void setCisno3(String cisno3) {
		this.cisno3 = cisno3;
	}

	public String getChamtw() {
		return chamtw;
	}

	public void setChamtw(String chamtw) {
		this.chamtw = chamtw;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "ImportBuyBean [row=" + row + ", conscd=" + conscd + ", refyearly=" + refyearly + ", yearly=" + yearly + ", prjtcd=" + prjtcd + ", prjtcd1=" + prjtcd1 + ", prjtcd2=" + prjtcd2 + ", prjtcd3=" + prjtcd3 + ", credtgtcd=" + credtgtcd + ", prjtnm=" + prjtnm + ", hqcd=" + hqcd + ", hqnm=" + hqnm + ", ptrempno=" + ptrempno + ", mgrempno=" + mgrempno + ", processnm=" + processnm + ", contdt=" + contdt + ", termidt=" + termidt + ", pdtcd=" + pdtcd + ", pdtnm=" + pdtnm + ", buyoutcd=" + buyoutcd + ", comnm=" + comnm + ", statuscd=" + statuscd + ", trgtetc=" + trgtetc + ", consdt=" + consdt + ", spadt=" + spadt + ", moudt=" + moudt + ", closdt=" + closdt + ", currcd=" + currcd + ", currnm=" + currnm + ", rate=" + rate + ", amt=" + amt + ", borddealcd=" + borddealcd + ", borddealnm=" + borddealnm + ", dealcd2=" + dealcd2 + ", dealnm2=" + dealnm2 + ", dealcd3=" + dealcd3 + ", dealnm3=" + dealnm3 + ", client=" + client + ", target=" + target + ", seller=" + seller + ", sellfinance=" + sellfinance + ", sellaudit=" + sellaudit + ", selllaw=" + selllaw + ", buyer=" + buyer + ", buyfinance=" + buyfinance + ", buyaudit=" + buyaudit + ", buylaw=" + buylaw + ", deal1=" + deal1
				+ ", paycd=" + paycd + ", chamtw=" + chamtw + ", cisno=" + cisno + ", cisno1=" + cisno1 + ", cisno2=" + cisno2 + ", cisno3=" + cisno3 + "]";
	}
}
