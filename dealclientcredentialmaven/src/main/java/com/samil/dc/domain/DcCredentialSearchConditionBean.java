package com.samil.dc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Client Credential 목록 검색 조건
 *
 */
public class DcCredentialSearchConditionBean implements Serializable {

	private static final long serialVersionUID = 2531479019991550465L;

	private String ctgcd = "";
	private String pdtcd = "";
	private String frdt = "";
	private String todt = "";
	private String prjtnm = "";
	private String chkpe = "";
	private String chkcross = "";
	private String srchtypcomp = "";
	private String clicomnm = "";
	private String cliindcd = "";
	private String cligindcd = "";
	private String cligindnm = "";
	private String clinatcd = "";
	private String clinatnm = "";
	private String tarcomnm = "";
	private String tarindcd = "";
	private String targindcd = "";
	private String targindnm = "";
	private String tarnatcd = "";
	private String tarnatnm = "";
	private String concomnm = "";
	private String conindcd = "";
	private String congindcd = "";
	private String congindnm = "";
	private String connatcd = "";
	private String connatnm = "";
	private String amtrange = "";
	private List<CommonCodeListBean> brssecure = new ArrayList<CommonCodeListBean>();
	private List<CommonCodeListBean> brsunsecure = new ArrayList<CommonCodeListBean>();
	private String rcflocation = "";
	private String rcftype = "";
	private String rcfdetailtype = "";
	private String credtgtcd = "";
	

	public DcCredentialSearchConditionBean() {

	}

	public String getPdtcd() {
		return pdtcd;
	}

	public void setPdtcd(String pdtcd) {
		this.pdtcd = pdtcd;
	}

	public String getFrdt() {
		return frdt;
	}

	public void setFrdt(String frdt) {
		this.frdt = frdt;
	}

	public String getTodt() {
		return todt;
	}

	public void setTodt(String todt) {
		this.todt = todt;
	}

	public String getClicomnm() {
		return clicomnm;
	}

	public void setClicomnm(String clicomnm) {
		this.clicomnm = clicomnm;
	}

	public String getCliindcd() {
		return cliindcd;
	}

	public void setCliindcd(String cliindcd) {
		this.cliindcd = cliindcd;
	}

	public String getClinatcd() {
		return clinatcd;
	}

	public void setClinatcd(String clinatcd) {
		this.clinatcd = clinatcd;
	}

	public String getClinatnm() {
		return clinatnm;
	}

	public void setClinatnm(String clinatnm) {
		this.clinatnm = clinatnm;
	}

	public String getTarcomnm() {
		return tarcomnm;
	}

	public void setTarcomnm(String tarcomnm) {
		this.tarcomnm = tarcomnm;
	}

	public String getTarindcd() {
		return tarindcd;
	}

	public void setTarindcd(String tarindcd) {
		this.tarindcd = tarindcd;
	}

	public String getTarnatcd() {
		return tarnatcd;
	}

	public void setTarnatcd(String tarnatcd) {
		this.tarnatcd = tarnatcd;
	}

	public String getTarnatnm() {
		return tarnatnm;
	}

	public void setTarnatnm(String tarnatnm) {
		this.tarnatnm = tarnatnm;
	}

	public String getChkpe() {
		return chkpe;
	}

	public void setChkpe(String chkpe) {
		this.chkpe = chkpe;
	}

	public String getChkcross() {
		return chkcross;
	}

	public void setChkcross(String chkcross) {
		this.chkcross = chkcross;
	}

	public String getConcomnm() {
		return concomnm;
	}

	public void setConcomnm(String concomnm) {
		this.concomnm = concomnm;
	}

	public String getConindcd() {
		return conindcd;
	}

	public void setConindcd(String conindcd) {
		this.conindcd = conindcd;
	}

	public String getConnatcd() {
		return connatcd;
	}

	public void setConnatcd(String connatcd) {
		this.connatcd = connatcd;
	}

	public String getConnatnm() {
		return connatnm;
	}

	public void setConnatnm(String connatnm) {
		this.connatnm = connatnm;
	}

	public String getCtgcd() {
		return ctgcd;
	}

	public void setCtgcd(String ctgcd) {
		this.ctgcd = ctgcd;
	}

	
	public List<CommonCodeListBean> getBrssecure() {
		return brssecure;
	}

	public void setBrssecure(List<CommonCodeListBean> brssecure) {
		this.brssecure = brssecure;
	}

	public List<CommonCodeListBean> getBrsunsecure() {
		return brsunsecure;
	}

	public void setBrsunsecure(List<CommonCodeListBean> brsunsecure) {
		this.brsunsecure = brsunsecure;
	}

	public String getRcflocation() {
		return rcflocation;
	}

	public void setRcflocation(String rcflocation) {
		this.rcflocation = rcflocation;
	}

	public String getRcftype() {
		return rcftype;
	}

	public void setRcftype(String rcftype) {
		this.rcftype = rcftype;
	}

	public String getRcfdetailtype() {
		return rcfdetailtype;
	}

	public void setRcfdetailtype(String rcfdetailtype) {
		this.rcfdetailtype = rcfdetailtype;
	}

	public String getCredtgtcd() {
		return credtgtcd;
	}

	public void setCredtgtcd(String credtgtcd) {
		this.credtgtcd = credtgtcd;
	}

	public String getPrjtnm() {
		return prjtnm;
	}

	public void setPrjtnm(String prjtnm) {
		this.prjtnm = prjtnm;
	}

	public String getSrchtypcomp() {
		return srchtypcomp;
	}

	public void setSrchtypcomp(String srchtypcomp) {
		this.srchtypcomp = srchtypcomp;
	}

	public String getCligindcd() {
		return cligindcd;
	}

	public void setCligindcd(String cligindcd) {
		this.cligindcd = cligindcd;
	}

	public String getCligindnm() {
		return cligindnm;
	}

	public void setCligindnm(String cligindnm) {
		this.cligindnm = cligindnm;
	}

	public String getTargindcd() {
		return targindcd;
	}

	public void setTargindcd(String targindcd) {
		this.targindcd = targindcd;
	}

	public String getTargindnm() {
		return targindnm;
	}

	public void setTargindnm(String targindnm) {
		this.targindnm = targindnm;
	}

	public String getCongindcd() {
		return congindcd;
	}

	public void setCongindcd(String congindcd) {
		this.congindcd = congindcd;
	}

	public String getCongindnm() {
		return congindnm;
	}

	public void setCongindnm(String congindnm) {
		this.congindnm = congindnm;
	}

	public String getAmtrange() {
		return amtrange;
	}

	public void setAmtrange(String amtrange) {
		this.amtrange = amtrange;
	}
}
