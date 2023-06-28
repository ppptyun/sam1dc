package com.samil.stdadt.vo;

import java.util.ArrayList;
import java.util.List;

import com.samil.stdadt.comm.vo.CommCodeVO;

public class DivisionProfileVO {
	
	String lockProfYymm;
	Integer lockProfMonths;
	String lockTrgtStat;
	List<ProfAuth> profAuthByBonb;
	List<CommCodeVO> statList;
	
	
	public List<CommCodeVO> convertCodeList () {
		List<CommCodeVO> retValue = new ArrayList<CommCodeVO>();
		
		if(this.lockProfYymm != null && !"".equals(this.lockProfYymm)) {			
			CommCodeVO lockProfYymm = new CommCodeVO();
			lockProfYymm.setGrpCd("PROF_LOCK");
			lockProfYymm.setCd("LOCK_PROF_YYMM");
			lockProfYymm.setpCd("root");
			lockProfYymm.setNm("Lock 대상 기준 연월 (프로젝트 사업연도 종료일 기준)");
			lockProfYymm.setVal1(this.lockProfYymm);
			lockProfYymm.setOrdby(1);
			retValue.add(lockProfYymm);
			
		}
		
		if(this.lockProfMonths != null) {			
			CommCodeVO lockProfMonths = new CommCodeVO();
			lockProfMonths.setGrpCd("PROF_LOCK");
			lockProfMonths.setCd("LOCK_PROF_MONTHS");
			lockProfMonths.setpCd("root");
			lockProfMonths.setNm("본부별 Lock 프로파일 적용을 위한 기준 개월 수");
			lockProfMonths.setVal1(this.lockProfMonths.toString());
			lockProfMonths.setOrdby(2);
			retValue.add(lockProfMonths);
		}
		
		if(this.lockTrgtStat != null) {			
			CommCodeVO lockProfMonths = new CommCodeVO();
			lockProfMonths.setGrpCd("PROF_LOCK");
			lockProfMonths.setCd("LOCK_TRGT_STAT");
			lockProfMonths.setpCd("root");
			lockProfMonths.setNm("본부별 LOCK 프로파일 적용 시 적용대상 Status");
			lockProfMonths.setVal1(this.lockTrgtStat);
			lockProfMonths.setOrdby(3);
			retValue.add(lockProfMonths);
		}
		
		if(this.profAuthByBonb != null) {			
			int i=0;
			for(ProfAuth item : profAuthByBonb) {
				CommCodeVO division = new CommCodeVO();	// 본부
				division.setGrpCd("POLICY_BY_BONB");
				division.setCd(item.cd);
				division.setpCd("root");
				division.setNm(item.nm);
				division.setOrdby(++i);
				
				CommCodeVO retain = new CommCodeVO();
				retain.setGrpCd("POLICY_BY_BONB");
				retain.setCd("RETAIN_YN_" + item.cd);
				retain.setpCd(item.cd);
				retain.setNm("리테인 전송 허용 여부");
				retain.setVal1(item.retainYn);
				retain.setVal2(item.retainAdmYn);
				retain.setOrdby(1);
				
				CommCodeVO sat = new CommCodeVO();
				sat.setGrpCd("POLICY_BY_BONB");
				sat.setCd("SAT_EDIT_YN_" + item.cd);
				sat.setpCd(item.cd);
				sat.setNm("표준감사시간 수정 허용 여부");
				sat.setVal1(item.satYn);
				sat.setVal2(item.satAdmYn);
				sat.setOrdby(2);
				
				CommCodeVO bdgt = new CommCodeVO();
				bdgt.setGrpCd("POLICY_BY_BONB");
				bdgt.setCd("BDGT_EDIT_YN_" + item.cd);
				bdgt.setpCd(item.cd);
				bdgt.setNm("Budget 수정 허용 여부");
				bdgt.setVal1(item.bdgtYn);
				bdgt.setVal2(item.bdgtAdmYn);
				bdgt.setOrdby(3);
				
				retValue.add(retain);
				retValue.add(sat);
				retValue.add(bdgt);
			}
		}
		
		return retValue;
	}
	
	
	public static class ProfAuth {
		String cd;
		String nm;
		String teamList;
		String retainYn;
		String retainAdmYn;
		String satYn;
		String satAdmYn;
		String bdgtYn;
		String bdgtAdmYn;
		Integer ordby; 
		public String getCd() {
			return cd;
		}
		public String getNm() {
			return nm;
		}
		public String getTeamList() {
			return teamList;
		}
		public String getRetainYn() {
			return retainYn;
		}
		public String getRetainAdmYn() {
			return retainAdmYn;
		}
		public String getSatYn() {
			return satYn;
		}
		public String getSatAdmYn() {
			return satAdmYn;
		}
		public String getBdgtYn() {
			return bdgtYn;
		}
		public String getBdgtAdmYn() {
			return bdgtAdmYn;
		}
		public void setCd(String cd) {
			this.cd = cd;
		}
		public void setNm(String nm) {
			this.nm = nm;
		}
		public void setTeamList(String teamList) {
			this.teamList = teamList;
		}
		public void setRetainYn(String retainYn) {
			this.retainYn = retainYn;
		}
		public void setRetainAdmYn(String retainAdmYn) {
			this.retainAdmYn = retainAdmYn;
		}
		public void setSatYn(String satYn) {
			this.satYn = satYn;
		}
		public void setSatAdmYn(String satAdmYn) {
			this.satAdmYn = satAdmYn;
		}
		public void setBdgtYn(String bdgtYn) {
			this.bdgtYn = bdgtYn;
		}
		public void setBdgtAdmYn(String bdgtAdmYn) {
			this.bdgtAdmYn = bdgtAdmYn;
		}
		public Integer getOrdby() {
			return ordby;
		}
		public void setOrdby(Integer ordby) {
			this.ordby = ordby;
		}
	}

	public String getLockProfYymm() {
		return lockProfYymm;
	}

	public Integer getLockProfMonths() {
		return lockProfMonths;
	}

	public List<ProfAuth> getProfAuthByBonb() {
		return profAuthByBonb;
	}

	public void setLockProfYymm(String lockProfYymm) {
		this.lockProfYymm = lockProfYymm;
	}

	public void setLockProfMonths(Integer lockProfMonths) {
		this.lockProfMonths = lockProfMonths;
	}

	public void setProfAuthByBonb(List<ProfAuth> profAuthByBonb) {
		this.profAuthByBonb = profAuthByBonb;
	}

	public String getLockTrgtStat() {
		return lockTrgtStat;
	}

	public void setLockTrgtStat(String lockTrgtStat) {
		this.lockTrgtStat = lockTrgtStat;
	}

	public List<CommCodeVO> getStatList() {
		return statList;
	}

	public void setStatList(List<CommCodeVO> statList) {
		this.statList = statList;
	}
}
