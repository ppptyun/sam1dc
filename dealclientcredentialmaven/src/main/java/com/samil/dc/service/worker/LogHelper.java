package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.domain.DcCredentialAddBean;
import com.samil.dc.domain.DcCredentialBaseDetailBean;
import com.samil.dc.domain.DcCredentialBean;
import com.samil.dc.domain.DcCredentialBizBean;
import com.samil.dc.domain.DcLogBean;
import com.samil.dc.domain.DcLtBizBean;
import com.samil.dc.domain.DcLtBuyBean;
import com.samil.dc.domain.DcLtDealListBean;
import com.samil.dc.domain.DcLtMnaBean;
import com.samil.dc.domain.DcLtRealBean;
import com.samil.dc.util.Constants;

public class LogHelper {
	
	protected DBConnection connection = null;

	public LogHelper(DBConnection connection) {
		this.connection = connection;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 변경 로그 남기기 수행
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param befProject
	 * @param altProject
	 */
	public void doLogCredential(DcCredentialBean befProject, DcCredentialBean altProject, String empno) {
		final String INSERT = Constants.LogMethod.INSERT;
		final String UPDATE = Constants.LogMethod.UPDATE;
		final String DELETE = Constants.LogMethod.DELETE;
		
		String prjtcd = altProject.getInfo().getPrjtcd();
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		
		// 기본 정보 로그
		DcCredentialBaseDetailBean befInfo = befProject.getInfo();
		DcCredentialBaseDetailBean altInfo = altProject.getInfo();
		if (!befInfo.getPrjtdesc().equals(altInfo.getPrjtdesc())) {
			logList.add(doLogBuildCredential(UPDATE, "상세업무내용", "prjtdesc", befInfo.getPrjtdesc(), altInfo.getPrjtdesc(), prjtcd, "", empno));
		}
		if (!befInfo.getTrgtetc().equals(altInfo.getTrgtetc())) {
			logList.add(doLogBuildCredential(UPDATE, "기타대상", "trgtetc", befInfo.getTrgtetc(), altInfo.getTrgtetc(), prjtcd, "", empno));
		}
		if (!befInfo.getBrssalecd().equals(altInfo.getBrssalecd())) {
			logList.add(doLogBuildCredential(UPDATE, "BRS 매각방식", "brssalecd", befInfo.getBrssalecd(), altInfo.getBrssalecd(), prjtcd, "", empno));
		}
		if (!befInfo.getBrsopb().equals(altInfo.getBrsopb())) {
			logList.add(doLogBuildCredential(UPDATE, "BRS OPB", "brsopb", befInfo.getBrsopb(), altInfo.getBrsopb(), prjtcd, "", empno));
		}
		if (!befInfo.getRcftypecd().equals(altInfo.getRcftypecd())) {
			logList.add(doLogBuildCredential(UPDATE, "RCF 구분", "rcftypecd", befInfo.getRcftypecd(), altInfo.getRcftypecd(), prjtcd, "", empno));
		}
		if (!befInfo.getRcftypea().equals(altInfo.getRcftypea())) {
			logList.add(doLogBuildCredential(UPDATE, "RCF 레저상세", "rcftypea", befInfo.getRcftypea(), altInfo.getRcftypea(), prjtcd, "", empno));
		}
		if (!befInfo.getRcftypeb().equals(altInfo.getRcftypeb())) {
			logList.add(doLogBuildCredential(UPDATE, "RCF 인프라상세", "rcftypeb", befInfo.getRcftypeb(), altInfo.getRcftypeb(), prjtcd, "", empno));
		}
		if (!befInfo.getRcftypeetc().equals(altInfo.getRcftypeetc())) {
			logList.add(doLogBuildCredential(UPDATE, "RCF 기타", "rcftypeetc", befInfo.getRcftypeetc(), altInfo.getRcftypeetc(), prjtcd, "", empno));
		}
		if (!befInfo.getRcfarea().equals(altInfo.getRcfarea())) {
			logList.add(doLogBuildCredential(UPDATE, "RCF 연면적", "rcfarea", befInfo.getRcfarea(), altInfo.getRcfarea(), prjtcd, "", empno));
		}
		if (!befInfo.getRcfland().equals(altInfo.getRcfland())) {
			logList.add(doLogBuildCredential(UPDATE, "RCF 토지면적", "rcfland", befInfo.getRcfland(), altInfo.getRcfland(), prjtcd, "", empno));
		}
		
		// 클라이언트 국가 로그
		DcCredentialBizBean befClient = befProject.getClient();
		DcCredentialBizBean altClient = altProject.getClient();
		if (!befClient.getNatcd().equals(altClient.getNatcd())) {
			logList.add(doLogBuildCredential(UPDATE, "클라이언트 국가", "rcfland", befClient.getNatcd(), altClient.getNatcd(), prjtcd, "국가변경[" + befClient.getNatnm() + " -> " + altClient.getNatnm() + "]", empno));
		}
		
		// 타겟 기업 로그
		List<DcCredentialBizBean> altTargetList = altProject.getTarget();
		for (DcCredentialBizBean altTarget : altTargetList) {
			DcCredentialBizBean befTarget = findMatchBizBean(altTarget, befProject.getTarget());
			if (befTarget == null) {
				if (!StringUtils.isBlank(altTarget.getCompcd())) {
					logList.add(doLogBuildCredential(INSERT, "", "", "", altTarget.toString(), prjtcd, "대상(Target) 신규", empno));
				}
			} else {
				logList.add(doLogBuildCredential(UPDATE, "", "", befTarget.toString(), altTarget.toString(), prjtcd, "대상(Target) 수정", empno));
			}
		}
		
		// 이해관계자
		DcCredentialBizBean altInterest = altProject.getInterest();
		if (StringUtils.isBlank(altInterest.getSeq())) {
			if (!StringUtils.isBlank(altInterest.getCompcd())) {
				logList.add(doLogBuildCredential(INSERT, "", "", "", altInterest.toString(), prjtcd, "이해관계자 신규", empno));
			}
		} else {
			DcCredentialBizBean befInterest = befProject.getInterest();
			logList.add(doLogBuildCredential(UPDATE, "", "", befInterest.toString(), altInterest.toString(), prjtcd, "BRS 매수처 수정", empno));
		}
		
		// 채권 변경 로그
		// 담보
		for (DcCredentialAddBean altItem : altProject.getBondsecure()) {
			DcCredentialAddBean befItem = findMatchAddBean(altItem, befProject.getBondsecure());
			if (befItem == null) {
				logList.add(doLogBuildCredential(INSERT, "항목코드", "itemcd", "", altItem.getItemcd(), prjtcd, "BRS 채권 담보 신규", empno));
			}
		}
		for (DcCredentialAddBean befItem : befProject.getBondsecure()) {
			DcCredentialAddBean altItem = findMatchAddBean(befItem, altProject.getBondsecure());
			if (altItem == null) {
				logList.add(doLogBuildCredential(DELETE, "항목코드", "itemcd", befItem.getItemcd(), "", prjtcd, "BRS 채권 담보 삭제", empno));
			}
		}
		// 무담보
		for (DcCredentialAddBean altItem : altProject.getBondsecurenone()) {
			DcCredentialAddBean befItem = findMatchAddBean(altItem, befProject.getBondsecurenone());
			if (befItem == null) {
				logList.add(doLogBuildCredential(INSERT, "항목코드", "itemcd", "", altItem.getItemcd(), prjtcd, "BRS 채권 무담보 신규", empno));
			}
		}
		for (DcCredentialAddBean befItem : befProject.getBondsecurenone()) {
			DcCredentialAddBean altItem = findMatchAddBean(befItem, altProject.getBondsecurenone());
			if (altItem == null) {
				logList.add(doLogBuildCredential(DELETE, "항목코드", "itemcd", befItem.getItemcd(), "", prjtcd, "BRS 채권 무담보 삭제", empno));
			}
		}
		
		// BRS 매수처
		DcCredentialBizBean altBrsbuyer = altProject.getBrsbuyer();
		if (StringUtils.isBlank(altBrsbuyer.getSeq())) {
			if (!StringUtils.isBlank(altBrsbuyer.getCompcd())) {
				logList.add(doLogBuildCredential(INSERT, "", "", "", altBrsbuyer.toString(), prjtcd, "BRS 매수처 신규", empno));
			}
		} else {
			DcCredentialBizBean befBrsbuyer = befProject.getBrsbuyer();
			logList.add(doLogBuildCredential(UPDATE, "", "", befBrsbuyer.toString(), altBrsbuyer.toString(), prjtcd, "BRS 매수처 수정", empno));
		}
		
		// RCF
		DcCredentialBizBean altRcf = altProject.getRcf();
		if (StringUtils.isBlank(altRcf.getSeq())) {
			if (!StringUtils.isBlank(altRcf.getNatcd()) || !StringUtils.isBlank(altRcf.getCity())) {
				logList.add(doLogBuildCredential(INSERT, "", "", "", altRcf.toString(), prjtcd, "RCF 신규", empno));
			}
		} else {
			DcCredentialBizBean befRcf = befProject.getRcf();
			logList.add(doLogBuildCredential(UPDATE, "", "", befRcf.toString(), altBrsbuyer.toString(), prjtcd, "RCF 수정", empno));
		}
		
		// 로그 저장
		DcLogDAO logDAO = new DcLogDAO(connection);
		logDAO.sqlLogCommon(logList);
	}
	
	public DcLogBean doLogBuildCredential(String altcd, String fildnm, String fildcd, String befval, String altval, String prjtcd, String bigo, String empno) {
		DcLogBean log = new DcLogBean(Constants.LogType.CREDENTIAL);
		log.setAltcd(altcd);
		log.setFildnm(fildnm);
		log.setFildcd(fildcd);
		log.setBefval(befval);
		log.setAltval(altval);
		log.setPrjtcd(prjtcd);
		log.setBigo(bigo);
		log.setAltempno(empno);
		return log;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 변경 로그 남기기 수행
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param befProject
	 * @param altProject
	 */
	public void doLogLeagueTableBuy(DcLtBuyBean befProject, DcLtBuyBean altProject, List<DcLtBizBean> delBizList, String empno) {
		String prjtcd = altProject.getPrjtcd();
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		DcLogBean log = null;
		
		// 정보 변경에 의한 담당자 확인 자동 철회 로그
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "담당자확인여부", "chrgconfcd", befProject.getChrgconfcd(), Constants.ChargeConfirm.NO);
		if (log != null) {
			log.setBigo("정보 변경에 의한 담당자 확인 자동 철회");
			logList.add(log);
		}
		
		// 프로젝트 기본정보 변경 로그
		List<DcLogBean> logBaseList = getLeagueTableBaseLogList(prjtcd, empno, befProject, altProject);
		logList.addAll(logBaseList);
		
		// 인수/매각 정보 변경 로그
		// TRGTETC
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "비고(Target 기타)", "trgtetc", befProject.getTrgtetc(), altProject.getTrgtetc());
		if (log != null) {
			logList.add(log);
		}
		// BUYOUTCD
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "Buyout/Non-Buyout", "buyoutcd", befProject.getBuyoutcd(), altProject.getBuyoutcd());
		if (log != null) {
			logList.add(log);
		}
		// CONSDT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "자문계약일", "consdt", befProject.getConsdt(), altProject.getConsdt());
		if (log != null) {
			logList.add(log);
		}
		// SPADT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "매매계약일(SPA)", "spadt", befProject.getSpadt(), altProject.getSpadt());
		if (log != null) {
			logList.add(log);
		}
		// MOUDT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "MOU체결일", "moudt", befProject.getMoudt(), altProject.getMoudt());
		if (log != null) {
			logList.add(log);
		}
		// CLOSDT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "잔금납입일(Closing)", "closdt", befProject.getClosdt(), altProject.getClosdt());
		if (log != null) {
			logList.add(log);
		}
		// CURRCD
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "통화 코드", "currcd", befProject.getCurrcd(), altProject.getCurrcd());
		if (log != null) {
			logList.add(log);
		}
		// AMT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "금액", "amt", befProject.getAmt(), altProject.getAmt());
		if (log != null) {
			logList.add(log);
		}
		// RATE
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "환율(1통화당원화)", "rate", befProject.getRate(), altProject.getRate());
		if (log != null) {
			logList.add(log);
		}
		// BORDDEALCD
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "국경간거래", "borddealcd", befProject.getBorddealcd(), altProject.getBorddealcd());
		if (log != null) {
			logList.add(log);
		}
		// DEALCD1
		List<DcLogBean> logDeal1List = getLeagueTableBuyDeal1LogList(prjtcd, empno, befProject.getDeal1(), altProject.getDeal1());
		logList.addAll(logDeal1List);
		// DEALCD2
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "거래유형2", "dealcd2", befProject.getDealcd2(), altProject.getDealcd2());
		if (log != null) {
			logList.add(log);
		}
		// DEALCD3
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "거래유형3", "dealcd3", befProject.getDealcd3(), altProject.getDealcd3());
		if (log != null) {
			logList.add(log);
		}
		// DEALCD4
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "거래유형4", "dealcd4", befProject.getDealcd4(), altProject.getDealcd4());
		if (log != null) {
			logList.add(log);
		}
		
		// 이전 프로젝트 기업정보
		List<DcLtBizBean> befBizList = new ArrayList<DcLtBizBean>();
		befBizList.addAll(befProject.getTarget());
		befBizList.addAll(befProject.getSeller());
		befBizList.addAll(befProject.getBuyer());
		befBizList.addAll(befProject.getSellfinance());
		befBizList.addAll(befProject.getSellaudit());
		befBizList.addAll(befProject.getSelllaw());
		befBizList.addAll(befProject.getBuyfinance());
		befBizList.addAll(befProject.getBuyaudit());
		befBizList.addAll(befProject.getBuylaw());
		// 입력 프로젝트 기업정보
		List<DcLtBizBean> altBizList = new ArrayList<DcLtBizBean>();
		altBizList.addAll(altProject.getTarget());
		altBizList.addAll(altProject.getSeller());
		altBizList.addAll(altProject.getBuyer());
		altBizList.addAll(altProject.getSellfinance());
		altBizList.addAll(altProject.getSellaudit());
		altBizList.addAll(altProject.getSelllaw());
		altBizList.addAll(altProject.getBuyfinance());
		altBizList.addAll(altProject.getBuyaudit());
		altBizList.addAll(altProject.getBuylaw());
		// 기업정보 변경 로그 
		List<DcLogBean> logBizList = getLeagueTableBizLogList(prjtcd, empno, befBizList, altBizList, delBizList);
		logList.addAll(logBizList);
		
		// 로그 저장
		DcLogDAO logDAO = new DcLogDAO(connection);
		logDAO.sqlLogCommon(logList);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 변경 로그 남기기 수행
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param befProject
	 * @param altProject
	 */
	public void doLogLeagueTableMna(DcLtMnaBean befProject, DcLtMnaBean altProject, List<DcLtBizBean> delBizList, String empno) {
		String prjtcd = altProject.getPrjtcd();
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		DcLogBean log = null;
		
		// 정보 변경에 의한 담당자 확인 자동 철회 로그
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "담당자확인여부", "chrgconfcd", befProject.getChrgconfcd(), Constants.ChargeConfirm.NO);
		if (log != null) {
			log.setBigo("정보 변경에 의한 담당자 확인 자동 철회");
			logList.add(log);
		}
		
		// 프로젝트 기본정보 변경 로그
		List<DcLogBean> logBaseList = getLeagueTableBaseLogList(prjtcd, empno, befProject, altProject);
		logList.addAll(logBaseList);
		
		// 합병 정보 변경 로그
		// CONSDT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "자문계약일", "consdt", befProject.getConsdt(), altProject.getConsdt());
		if (log != null) {
			logList.add(log);
		}
		// DIRTDT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "이사회의결일", "dirtdt", befProject.getDirtdt(), altProject.getDirtdt());
		if (log != null) {
			logList.add(log);
		}
		// STKHDT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "주주총회의결일", "stkhdt", befProject.getStkhdt(), altProject.getStkhdt());
		if (log != null) {
			logList.add(log);
		}
		// MNADT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "합병기일", "mnadt", befProject.getMnadt(), altProject.getMnadt());
		if (log != null) {
			logList.add(log);
		}
		// CURRCD
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "통화 코드", "currcd", befProject.getCurrcd(), altProject.getCurrcd());
		if (log != null) {
			logList.add(log);
		}
		// AMT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "금액", "amt", befProject.getAmt(), altProject.getAmt());
		if (log != null) {
			logList.add(log);
		}
		// RATE
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "환율(1통화당원화)", "rate", befProject.getRate(), altProject.getRate());
		if (log != null) {
			logList.add(log);
		}
		// BORDDEALCD
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "국경간거래", "borddealcd", befProject.getBorddealcd(), altProject.getBorddealcd());
		if (log != null) {
			logList.add(log);
		}
		// DEALCD1
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "거래유형1", "dealcd1", befProject.getDealcd1(), altProject.getDealcd1());
		if (log != null) {
			logList.add(log);
		}
		// DEALCD2
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "거래유형2", "dealcd2", befProject.getDealcd2(), altProject.getDealcd2());
		if (log != null) {
			logList.add(log);
		}
		// DEALCD3
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "거래유형3", "dealcd3", befProject.getDealcd3(), altProject.getDealcd3());
		if (log != null) {
			logList.add(log);
		}
		
		// 이전 프로젝트 기업정보
		List<DcLtBizBean> befBizList = new ArrayList<DcLtBizBean>();
		befBizList.addAll(befProject.getMerging());
		befBizList.addAll(befProject.getMergingfinance());
		befBizList.addAll(befProject.getMergingaudit());
		befBizList.addAll(befProject.getMerginglaw());
		befBizList.addAll(befProject.getMerged());
		befBizList.addAll(befProject.getMergedfinance());
		befBizList.addAll(befProject.getMergedaudit());
		befBizList.addAll(befProject.getMergedlaw());
		// 입력 프로젝트 기업정보
		List<DcLtBizBean> altBizList = new ArrayList<DcLtBizBean>();
		altBizList.addAll(altProject.getMerging());
		altBizList.addAll(altProject.getMergingfinance());
		altBizList.addAll(altProject.getMergingaudit());
		altBizList.addAll(altProject.getMerginglaw());
		altBizList.addAll(altProject.getMerged());
		altBizList.addAll(altProject.getMergedfinance());
		altBizList.addAll(altProject.getMergedaudit());
		altBizList.addAll(altProject.getMergedlaw());
		// 기업정보 변경 로그 
		List<DcLogBean> logBizList = getLeagueTableBizLogList(prjtcd, empno, befBizList, altBizList, delBizList);
		logList.addAll(logBizList);
		
		// 로그 저장
		DcLogDAO logDAO = new DcLogDAO(connection);
		logDAO.sqlLogCommon(logList);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 변경 로그 남기기 수행
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param befProject
	 * @param altProject
	 */
	public void doLogLeagueTableReal(DcLtRealBean befProject, DcLtRealBean altProject, List<DcLtBizBean> delBizList, String empno) {
		String prjtcd = altProject.getPrjtcd();
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		DcLogBean log = null;
		
		// 정보 변경에 의한 담당자 확인 자동 철회 로그
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "담당자확인여부", "chrgconfcd", befProject.getChrgconfcd(), Constants.ChargeConfirm.NO);
		if (log != null) {
			log.setBigo("정보 변경에 의한 담당자 확인 자동 철회");
			logList.add(log);
		}
		
		// 프로젝트 기본정보 변경 로그
		List<DcLogBean> logBaseList = getLeagueTableBaseLogList(prjtcd, empno, befProject, altProject);
		logList.addAll(logBaseList);
		
		// 부동산 정보 변경 로그
		// CONSDT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "자문계약일", "consdt", befProject.getConsdt(), altProject.getConsdt());
		if (log != null) {
			logList.add(log);
		}
		// SPADT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "매매계약일(SPA)", "spadt", befProject.getSpadt(), altProject.getSpadt());
		if (log != null) {
			logList.add(log);
		}
		// MOUDT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "MOU체결일", "moudt", befProject.getMoudt(), altProject.getMoudt());
		if (log != null) {
			logList.add(log);
		}
		// CLOSDT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "잔금납입일(Closing)", "closdt", befProject.getClosdt(), altProject.getClosdt());
		if (log != null) {
			logList.add(log);
		}
		// CURRCD
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "통화 코드", "currcd", befProject.getCurrcd(), altProject.getCurrcd());
		if (log != null) {
			logList.add(log);
		}
		// AMT
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "금액", "amt", befProject.getAmt(), altProject.getAmt());
		if (log != null) {
			logList.add(log);
		}
		// RATE
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "환율(1통화당원화)", "rate", befProject.getRate(), altProject.getRate());
		if (log != null) {
			logList.add(log);
		}
		// BORDDEALCD
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "국경간거래", "borddealcd", befProject.getBorddealcd(), altProject.getBorddealcd());
		if (log != null) {
			logList.add(log);
		}
		
		// 이전 프로젝트 기업정보
		List<DcLtBizBean> befBizList = new ArrayList<DcLtBizBean>();
		befBizList.addAll(befProject.getTarget());
		befBizList.addAll(befProject.getSeller());
		befBizList.addAll(befProject.getBuyer());
		befBizList.addAll(befProject.getSellfinance());
		befBizList.addAll(befProject.getSellaudit());
		befBizList.addAll(befProject.getSelllaw());
		befBizList.addAll(befProject.getBuyfinance());
		befBizList.addAll(befProject.getBuyaudit());
		befBizList.addAll(befProject.getBuylaw());
		// 입력 프로젝트 기업정보
		List<DcLtBizBean> altBizList = new ArrayList<DcLtBizBean>();
		altBizList.addAll(altProject.getTarget());
		altBizList.addAll(altProject.getSeller());
		altBizList.addAll(altProject.getBuyer());
		altBizList.addAll(altProject.getSellfinance());
		altBizList.addAll(altProject.getSellaudit());
		altBizList.addAll(altProject.getSelllaw());
		altBizList.addAll(altProject.getBuyfinance());
		altBizList.addAll(altProject.getBuyaudit());
		altBizList.addAll(altProject.getBuylaw());
		// 기업정보 변경 로그 
		List<DcLogBean> logBizList = getLeagueTableBizLogList(prjtcd, empno, befBizList, altBizList, delBizList);
		logList.addAll(logBizList);
		
		// 로그 저장
		DcLogDAO logDAO = new DcLogDAO(connection);
		logDAO.sqlLogCommon(logList);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기본정보 변경 로그 목록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param empno
	 * @param befProject
	 * @param altProject
	 * @return
	 */
	private List<DcLogBean> getLeagueTableBaseLogList(String prjtcd, String empno, DcLtBuyBean befProject, DcLtBuyBean altProject) {
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		// STATUSCD
		DcLogBean log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "진행 상태", "statuscd", befProject.getStatuscd(), altProject.getStatuscd());
		if (log != null) {
			logList.add(log);
		}
		// CONFCD
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "Confidential 여부", "confcd", befProject.getConfcd(), altProject.getConfcd());
		if (log != null) {
			logList.add(log);
		}
		// COMNM
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "내부 Comment", "comnm", befProject.getComnm(), altProject.getComnm());
		if (log != null) {
			logList.add(log);
		}
		return logList;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기본정보 변경 로그 목록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param empno
	 * @param befProject
	 * @param altProject
	 * @return
	 */
	private List<DcLogBean> getLeagueTableBaseLogList(String prjtcd, String empno, DcLtMnaBean befProject, DcLtMnaBean altProject) {
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		// STATUSCD
		DcLogBean log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "진행 상태", "statuscd", befProject.getStatuscd(), altProject.getStatuscd());
		if (log != null) {
			logList.add(log);
		}
		// CONFCD
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "Confidential 여부", "confcd", befProject.getConfcd(), altProject.getConfcd());
		if (log != null) {
			logList.add(log);
		}
		// COMNM
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "내부 Comment", "comnm", befProject.getComnm(), altProject.getComnm());
		if (log != null) {
			logList.add(log);
		}
		return logList;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기본정보 변경 로그 목록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param empno
	 * @param befProject
	 * @param altProject
	 * @return
	 */
	private List<DcLogBean> getLeagueTableBaseLogList(String prjtcd, String empno, DcLtRealBean befProject, DcLtRealBean altProject) {
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		// STATUSCD
		DcLogBean log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "진행 상태", "statuscd", befProject.getStatuscd(), altProject.getStatuscd());
		if (log != null) {
			logList.add(log);
		}
		// CONFCD
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "Confidential 여부", "confcd", befProject.getConfcd(), altProject.getConfcd());
		if (log != null) {
			logList.add(log);
		}
		// COMNM
		log = getLeagueUpdateDifferentItemLogBean(prjtcd, empno, "내부 Comment", "comnm", befProject.getComnm(), altProject.getComnm());
		if (log != null) {
			logList.add(log);
		}
		return logList;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 정보 변경 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param empno
	 * @param fildnm
	 * @param fildcd
	 * @param befVal
	 * @param altVal
	 * @return
	 */
	private DcLogBean getLeagueUpdateDifferentItemLogBean(String prjtcd, String empno, String fildnm, String fildcd, String befVal, String altVal) {
		if (befVal.equals(altVal)) {
			return null;
		}
		DcLogBean log = new DcLogBean(Constants.LogType.LEAGUE_TABLE);
		log.setAltcd(Constants.LogMethod.UPDATE);
		log.setFildnm(fildnm);
		log.setFildcd(fildcd);
		log.setBefval(befVal);
		log.setAltval(altVal);
		log.setPrjtcd(prjtcd);
		log.setBigo("");
		log.setAltempno(empno);
		return log;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 인수/매각 거래유형1 정보 변경 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param empno
	 * @param befList
	 * @param altList
	 * @return
	 */
	private List<DcLogBean> getLeagueTableBuyDeal1LogList(String prjtcd, String empno, List<DcLtDealListBean> befList, List<DcLtDealListBean> altList) {
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		// 신규 목록
		for (DcLtDealListBean altDeal : altList) {
			DcLtDealListBean befDeal = findMatchDealBean(altDeal, befList);
			if (befDeal == null) {
				DcLogBean log = new DcLogBean(Constants.LogType.LEAGUE_TABLE);
				log.setAltcd(Constants.LogMethod.INSERT);
				log.setFildnm("거래유형1");
				log.setFildcd("dealitemcd");
				log.setBefval("");
				log.setAltval(altDeal.getDealitemcd());
				log.setPrjtcd(prjtcd);
				log.setBigo("");
				log.setAltempno(empno);
				logList.add(log);
			}
		}
		// 삭제 목록
		for (DcLtDealListBean befDeal : befList) {
			DcLtDealListBean altDeal = findMatchDealBean(befDeal, altList);
			if (altDeal == null) {
				DcLogBean log = new DcLogBean(Constants.LogType.LEAGUE_TABLE);
				log.setAltcd(Constants.LogMethod.DELETE);
				log.setFildnm("거래유형1");
				log.setFildcd("dealitemcd");
				log.setBefval(befDeal.getDealitemcd());
				log.setAltval("");
				log.setPrjtcd(prjtcd);
				log.setBigo("");
				log.setAltempno(empno);
				logList.add(log);
			}
		}
		return logList;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 변경 로그 목록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param empno
	 * @param befBizList
	 * @param altBizList
	 * @param delBizList
	 */
	private List<DcLogBean> getLeagueTableBizLogList(String prjtcd, String empno, List<DcLtBizBean> befBizList, List<DcLtBizBean> altBizList, List<DcLtBizBean> delBizList) {
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		// 신규/수정 목록
		for (DcLtBizBean altBiz : altBizList) {
			DcLtBizBean befBiz = findMatchBizBean(altBiz, befBizList);
			// 신규
			if (befBiz == null) {
				DcLogBean log = buildLeagueTableBizLogBean(prjtcd, empno, befBiz, altBiz);
				logList.add(log);
				continue;
			}
			// 수정
			if (!altBiz.getCompcd().equals(befBiz.getCompcd()) || !altBiz.getComphannm().equals(befBiz.getComphannm()) || !altBiz.getCompengnm().equals(befBiz.getCompengnm()) ||
				!altBiz.getNatcd().equals(befBiz.getNatcd()) || !altBiz.getNatnm().equals(befBiz.getNatnm()) || !altBiz.getInducd().equals(befBiz.getInducd()) || 
				!altBiz.getChrgempnm1().equals(befBiz.getChrgempnm1()) || !altBiz.getDealnm().equals(befBiz.getDealnm())) {
				DcLogBean log = buildLeagueTableBizLogBean(prjtcd, empno, befBiz, altBiz);
				logList.add(log);
			}
		}
		// 삭제 목록
		for (DcLtBizBean delBiz : delBizList) {
			DcLtBizBean befBiz = findMatchBizBean(delBiz, befBizList);
			DcLogBean log = new DcLogBean(Constants.LogType.LEAGUE_TABLE);
			log.setAltcd(Constants.LogMethod.DELETE);
			log.setPrjtcd(prjtcd);
			log.setAltempno(empno);
			log.setBefval(befBiz.toString());
			log.setBigo(getBigoText(delBiz, Constants.LogMethod.DELETE));
			logList.add(log);
		}
		return logList;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 변경 로그 만들기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param empno
	 * @param befBiz
	 * @param altBiz
	 * @return
	 */
	private DcLogBean buildLeagueTableBizLogBean(String prjtcd, String empno, DcLtBizBean befBiz, DcLtBizBean altBiz) {
		DcLogBean log = new DcLogBean(Constants.LogType.LEAGUE_TABLE);
		log.setPrjtcd(prjtcd);
		log.setAltempno(empno);
		if (befBiz == null) {
			// 신규
			log.setAltcd(Constants.LogMethod.INSERT);
			log.setBigo(getBigoText(altBiz, Constants.LogMethod.INSERT));
		} else {
			// 수정
			log.setAltcd(Constants.LogMethod.UPDATE);
			log.setBigo(getBigoText(altBiz, Constants.LogMethod.UPDATE));
			log.setBefval(befBiz.toString());
		}
		log.setAltval(altBiz.toString());
		return log;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 기업정보 로그를 위한 비고 문구 만들기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param biz
	 * @return
	 */
	private String getBigoText(DcLtBizBean biz, String logMethod) {
		StringBuffer sbBigo = new StringBuffer();
		if (Constants.Actor.BUY_TARGET.equals(biz.getActcd())) {
			sbBigo.append("인수/매각 자문-대상 (Target)");
		} else if (Constants.Actor.BUY_SELLER.equals(biz.getActcd())) {
			sbBigo.append("인수/매각 자문-매각자 (Seller)");
		} else if (Constants.Actor.BUY_BUYER.equals(biz.getActcd())) {
			sbBigo.append("인수/매각 자문-인수자 (Buyer)");
		} else if (Constants.Actor.MNA_MERGING.equals(biz.getActcd())) {
			sbBigo.append("합병 자문-합병(존속/신설) 법인");
		} else if (Constants.Actor.MNA_MERGED.equals(biz.getActcd())) {
			sbBigo.append("합병 자문-피합병(소멸) 법인");
		} else if (Constants.Actor.REAL_TARGET.equals(biz.getActcd())) {
			sbBigo.append("부동산 자문-대상 (Target)");
		} else if (Constants.Actor.REAL_SELLER.equals(biz.getActcd())) {
			sbBigo.append("부동산 자문-매각자 (Seller)");
		} else if (Constants.Actor.REAL_BUYER.equals(biz.getActcd())) {
			sbBigo.append("부동산 자문-인수자 (Buyer)");
		}
		sbBigo.append(" | ");
		if (Constants.LtBiz.MAIN.equals(biz.getBizcd())) {
			sbBigo.append("메인");
		} else if (Constants.LtBiz.FINANCE.equals(biz.getBizcd())) {
			sbBigo.append("재무/전략자문");
		} else if (Constants.LtBiz.AUDIT.equals(biz.getBizcd())) {
			sbBigo.append("회계자문");
		} else if (Constants.LtBiz.LAW.equals(biz.getBizcd())) {
			sbBigo.append("법률자문");
		}
		sbBigo.append(" | ");
		if (Constants.LogMethod.INSERT.equals(logMethod)) {
			sbBigo.append("신규");
		} else if (Constants.LogMethod.UPDATE.equals(logMethod)) {
			sbBigo.append("수정");
		} else if (Constants.LogMethod.DELETE.equals(logMethod)) {
			sbBigo.append("삭제");
		}
		return sbBigo.toString().trim();
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 동일한 기업정보 찾기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param search
	 * @param pool
	 * @return
	 */
	private DcCredentialBizBean findMatchBizBean(DcCredentialBizBean search, List<DcCredentialBizBean> pool) {
		for (DcCredentialBizBean biz : pool) {
			if (search.getPrjtcd().equals(biz.getPrjtcd()) && search.getBizcd().equals(biz.getBizcd()) && search.getSeq().equals(biz.getSeq())) {
				return biz;
			}
		}
		return null;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 동일한 추가정보 찾기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param search
	 * @param pool
	 * @return
	 */
	public DcCredentialAddBean findMatchAddBean(DcCredentialAddBean search, List<DcCredentialAddBean> pool) {
		for (DcCredentialAddBean item : pool) {
			if (search.getPrjtcd().equals(item.getPrjtcd()) && search.getItemcd().equals(item.getItemcd())) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 동일한 기업정보 찾기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param search
	 * @param pool
	 * @return
	 */
	private DcLtBizBean findMatchBizBean(DcLtBizBean search, List<DcLtBizBean> pool) {
		for (DcLtBizBean biz : pool) {
			if (search.getPrjtcd().equals(biz.getPrjtcd()) && search.getActcd().equals(biz.getActcd()) && search.getBizcd().equals(biz.getBizcd()) && search.getSeq().equals(biz.getSeq())) {
				return biz;
			}
		}
		return null;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 동일한 거래유형정보 찾기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param search
	 * @param pool
	 * @return
	 */
	private DcLtDealListBean findMatchDealBean(DcLtDealListBean search, List<DcLtDealListBean> pool) {
		for (DcLtDealListBean deal : pool) {
			if (search.getPrjtcd().equals(deal.getPrjtcd()) && search.getDealitemcd().equals(deal.getDealitemcd())) {
				return deal;
			}
		}
		return null;
	}
}
