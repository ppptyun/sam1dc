package com.samil.stdadt.dto;

import com.samil.stdadt.comm.vo.CamelMap;
import com.samil.stdadt.util.SHConverter;
import com.samil.stdadt.vo.SubProjectVO;

/**
 * 합산대상 프로젝트 정보
 * @author shyunchoi
 *
 */
public class SubProjectV2Dto {
	private String prjtCd;			// 프로젝트 코드 00000-00-000
	private String prjtNm;			// Client명/용역명
	private CamelMap chargPtr;		// 프로젝트 담당 Partner
	private CamelMap chargMgr;		// 프로젝트 담당 매니저
	private long cntrtFee;			// 계약 보수 (원)
	
	
	public void init(SubProjectVO subPrjt) throws Exception{
		this.prjtCd = subPrjt.getPrjtCd();
		this.prjtNm = subPrjt.getPrjtNm();
		this.chargPtr = SHConverter.camelMapFactory("emplno", subPrjt.getChargPtr(),"kornm", subPrjt.getChargPtrNm(),"gradnm", subPrjt.getChargPtrGradNm());
		this.chargMgr = SHConverter.camelMapFactory("emplno", subPrjt.getChargMgr(),"kornm", subPrjt.getChargMgrNm(),"gradnm", subPrjt.getChargMgrGradNm());
		this.cntrtFee = subPrjt.getCntrtFee();
	}
	
	public String getPrjtCd() {
		return prjtCd;
	}
	public String getPrjtNm() {
		return prjtNm;
	}
	public CamelMap getChargPtr() {
		return chargPtr;
	}
	public CamelMap getChargMgr() {
		return chargMgr;
	}
	public long getCntrtFee() {
		return cntrtFee;
	}
	public void setPrjtCd(String prjtCd) {
		this.prjtCd = prjtCd;
	}
	public void setPrjtNm(String prjtNm) {
		this.prjtNm = prjtNm;
	}
	public void setChargPtr(CamelMap chargPtr) {
		this.chargPtr = chargPtr;
	}
	public void setChargMgr(CamelMap chargMgr) {
		this.chargMgr = chargMgr;
	}
	public void setCntrtFee(long cntrtFee) {
		this.cntrtFee = cntrtFee;
	}
}
