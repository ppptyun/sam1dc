package com.samil.dc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DcCredentialBean implements Serializable {

	private static final long serialVersionUID = -5544152901010363941L;

	private DcCredentialBaseDetailBean info = new DcCredentialBaseDetailBean();
	private DcCredentialBizBean client = new DcCredentialBizBean();
	private List<DcCredentialBizBean> target = new ArrayList<DcCredentialBizBean>();
	// 이해관계자
	private DcCredentialBizBean interest = new DcCredentialBizBean();
	// 담보 채권
	private List<DcCredentialAddBean> bondsecure = new ArrayList<DcCredentialAddBean>();
	// 무담보 채권
	private List<DcCredentialAddBean> bondsecurenone = new ArrayList<DcCredentialAddBean>();
	// BRS 매수처
	private DcCredentialBizBean brsbuyer = new DcCredentialBizBean();
	// RCF
	private DcCredentialBizBean rcf = new DcCredentialBizBean();
	
	public DcCredentialBean() {
		
	}

	public DcCredentialBaseDetailBean getInfo() {
		return info;
	}

	public void setInfo(DcCredentialBaseDetailBean info) {
		this.info = info;
	}

	public DcCredentialBizBean getClient() {
		return client;
	}

	public void setClient(DcCredentialBizBean client) {
		this.client = client;
	}

	public List<DcCredentialBizBean> getTarget() {
		return target;
	}

	public void setTarget(List<DcCredentialBizBean> target) {
		this.target = target;
	}

	public List<DcCredentialAddBean> getBondsecure() {
		return bondsecure;
	}

	public void setBondsecure(List<DcCredentialAddBean> bondsecure) {
		this.bondsecure = bondsecure;
	}

	public List<DcCredentialAddBean> getBondsecurenone() {
		return bondsecurenone;
	}

	public void setBondsecurenone(List<DcCredentialAddBean> bondsecurenone) {
		this.bondsecurenone = bondsecurenone;
	}

	public DcCredentialBizBean getBrsbuyer() {
		return brsbuyer;
	}

	public void setBrsbuyer(DcCredentialBizBean brsbuyer) {
		this.brsbuyer = brsbuyer;
	}

	public DcCredentialBizBean getRcf() {
		return rcf;
	}

	public void setRcf(DcCredentialBizBean rcf) {
		this.rcf = rcf;
	}

	public DcCredentialBizBean getInterest() {
		return interest;
	}

	public void setInterest(DcCredentialBizBean interest) {
		this.interest = interest;
	}
}
