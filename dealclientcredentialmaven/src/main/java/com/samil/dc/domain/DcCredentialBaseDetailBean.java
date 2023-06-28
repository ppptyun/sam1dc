package com.samil.dc.domain;

import java.io.Serializable;

public class DcCredentialBaseDetailBean implements Serializable {

	private static final long serialVersionUID = 5797355215148042659L;

	private String prjtcd = "";
	private String prjtnm = "";
	private String refyearly = "";
	private String ctgcd = "";
	private String pdtcd = "";
	private String pdtnm = "";
	private String refyearly_orig = "";
	private String pdtcd_orig = "";
	private String pdtnm_orig = "";
	private String prjtdesc = "";
	private String trgtetc = "";
	private String brssalecd = "";
	private String brsopb = "";
	private String rcftypecd = "";
	private String rcftypea = "";
	private String rcftypeb = "";
	private String rcftypeetc = "";
	private String rcfarea = "";
	private String rcfland = "";
	
	private String credtgtcd = "";
	private String credtgtnm = "";

	public DcCredentialBaseDetailBean() {
		
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

	public String getPrjtdesc() {
		return prjtdesc;
	}

	public void setPrjtdesc(String prjtdesc) {
		this.prjtdesc = prjtdesc;
	}

	public String getTrgtetc() {
		return trgtetc;
	}

	public void setTrgtetc(String trgtetc) {
		this.trgtetc = trgtetc;
	}

	public String getBrssalecd() {
		return brssalecd;
	}

	public void setBrssalecd(String brssalecd) {
		this.brssalecd = brssalecd;
	}

	public String getBrsopb() {
		return brsopb;
	}

	public void setBrsopb(String brsopb) {
		this.brsopb = brsopb;
	}

	public String getRcftypecd() {
		return rcftypecd;
	}

	public void setRcftypecd(String rcftypecd) {
		this.rcftypecd = rcftypecd;
	}

	public String getRcftypea() {
		return rcftypea;
	}

	public void setRcftypea(String rcftypea) {
		this.rcftypea = rcftypea;
	}

	public String getRcftypeb() {
		return rcftypeb;
	}

	public void setRcftypeb(String rcftypeb) {
		this.rcftypeb = rcftypeb;
	}

	public String getRcftypeetc() {
		return rcftypeetc;
	}

	public void setRcftypeetc(String rcftypeetc) {
		this.rcftypeetc = rcftypeetc;
	}

	public String getRcfarea() {
		return rcfarea;
	}

	public void setRcfarea(String rcfarea) {
		this.rcfarea = rcfarea;
	}

	public String getRcfland() {
		return rcfland;
	}

	public void setRcfland(String rcfland) {
		this.rcfland = rcfland;
	}

	public String getCredtgtcd() {
		return credtgtcd;
	}

	public void setCredtgtcd(String credtgtcd) {
		this.credtgtcd = credtgtcd;
	}

	public String getCredtgtnm() {
		return credtgtnm;
	}

	public void setCredtgtnm(String credtgtnm) {
		this.credtgtnm = credtgtnm;
	}
	public String getRefyearly() {
		return refyearly;
	}

	public void setRefyearly(String refyearly) {
		this.refyearly = refyearly;
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

	public String getCtgcd() {
		return ctgcd;
	}

	public void setCtgcd(String ctgcd) {
		this.ctgcd = ctgcd;
	}
	
	public String getRefyearly_orig() {
		return refyearly_orig;
	}

	public void setRefyearly_orig(String refyearly_orig) {
		this.refyearly_orig = refyearly_orig;
	}

	public String getPdtcd_orig() {
		return pdtcd_orig;
	}

	public void setPdtcd_orig(String pdtcd_orig) {
		this.pdtcd_orig = pdtcd_orig;
	}
	
	public String getPdtnm_orig() {
		return pdtnm_orig;
	}

	public void setPdtnm_orig(String pdtnm_orig) {
		this.pdtnm_orig = pdtnm_orig;
	}

	@Override
	public String toString() {
		return "["
				+   "prjtcd=" + prjtcd 
				+ ", prjtnm=" + prjtnm 
				+ ", refyearly=" + refyearly 
				+ ", ctgcd=" + ctgcd 
				+ ", pdtcd=" + pdtcd
				+ ", pdtnm=" + pdtnm
				+ ", refyearly_orig=" + refyearly_orig 
				+ ", pdtcd_orig=" + pdtcd_orig 
				+ ", pdtnm_orig=" + pdtnm_orig
				+ ", prjtdesc=" + prjtdesc 
				+ ", trgtetc=" + trgtetc 
				+ ", brssalecd=" + brssalecd 
				+ ", brsopb=" + brsopb 
				+ ", rcftypecd=" + rcftypecd 
				+ ", rcftypea=" + rcftypea 
				+ ", rcftypeb=" + rcftypeb 
				+ ", rcftypeetc=" + rcftypeetc 
				+ ", rcfarea=" + rcfarea 
				+ ", rcfland=" + rcfland 
		+ "]";
	}
}
