package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.domain.AdminConsultListBean;
import com.samil.dc.domain.DcCredentialAddBean;
import com.samil.dc.domain.DcCredentialBaseDetailBean;
import com.samil.dc.domain.DcCredentialBean;
import com.samil.dc.domain.DcCredentialBizBean;
import com.samil.dc.domain.DcLtBizBean;
import com.samil.dc.domain.DcLtBuyBean;
import com.samil.dc.domain.DcLtDealListBean;
import com.samil.dc.domain.DcLtMnaBean;
import com.samil.dc.domain.DcLtRealBean;
import com.samil.dc.util.Constants;

public final class ParameterHelper {
	
	public final static String ARRAY_BRACKET_OPEN = "[";
	public final static String ARRAY_BRACKET_CLOSE = "]";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static DcCredentialBean getParameterCredentialBean(HttpServletRequest request) {
		DcCredentialBean project = new DcCredentialBean();
		
		// 단일형 파라미터
		DcCredentialBaseDetailBean info = new DcCredentialBaseDetailBean();
		String prjtcd = StringUtils.defaultString(ServiceHelper.getParameter(request, "prjtcd"), "");
		info.setPrjtcd(prjtcd);
		info.setRefyearly(StringUtils.defaultString(ServiceHelper.getParameter(request, "refyearly"), ""));
		info.setCtgcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "ctgcd"), ""));
		info.setPdtcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "pdtcd"), ""));
		info.setPrjtdesc(StringUtils.defaultString(ServiceHelper.getParameter(request, "prjtdesc"), ""));
		info.setTrgtetc(StringUtils.defaultString(ServiceHelper.getParameter(request, "trgtetc"), ""));
		info.setBrssalecd(StringUtils.defaultString(ServiceHelper.getParameter(request, "brssalecd"), ""));
		info.setBrsopb(StringUtils.defaultString(ServiceHelper.getParameter(request, "brsopb"), ""));
		info.setRcftypecd(StringUtils.defaultString(ServiceHelper.getParameter(request, "rcftypecd"), ""));
		info.setRcftypea(StringUtils.defaultString(ServiceHelper.getParameter(request, "rcftypea"), ""));
		info.setRcftypeb(StringUtils.defaultString(ServiceHelper.getParameter(request, "rcftypeb"), ""));
		info.setRcftypeetc(StringUtils.defaultString(ServiceHelper.getParameter(request, "rcftypeetc"), ""));
		info.setRcfarea(StringUtils.defaultString(ServiceHelper.getParameter(request, "rcfarea"), ""));
		info.setRcfland(StringUtils.defaultString(ServiceHelper.getParameter(request, "rcfland"), ""));
		info.setCredtgtcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "credtgtcd"), ""));
		project.setInfo(info);
		// 클라이언트
		DcCredentialBizBean client = new DcCredentialBizBean();
		client.setPrjtcd(prjtcd);
		client.setNatcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "clientnatcd"), ""));
		client.setNatnm(StringUtils.defaultString(ServiceHelper.getParameter(request, "clientnatnm"), ""));
		project.setClient(client);
		// 대상(Target)
		project.setTarget(getCredTargetBizList(request, prjtcd));
		// 이해관계자
		project.setInterest(getCredInterestBiz(request, prjtcd));
		// BRS 매수처
		project.setBrsbuyer(getCredBRSBuyerBiz(request, prjtcd));
		// RCF
		project.setRcf(getCredRCFBiz(request, prjtcd));
		// 담보 채권
		project.setBondsecure(getCredBondsecureList(request, prjtcd));
		// 무담보 채권
		project.setBondsecurenone(getCredBondsecurenoneList(request, prjtcd));
		
		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 대상(Target) 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcCredentialBizBean> getCredTargetBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "target";
		List<DcCredentialBizBean> list = new ArrayList<DcCredentialBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcCredentialBizBean biz = new DcCredentialBizBean();
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setPrjtcd(prjtcd);
			biz.setBizcd(Constants.CredBiz.TARGET);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setCompcd(compcd);
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			biz.setCity(StringUtils.defaultString(getParameter(request, categoryName, i, "city"), ""));
			biz.setAddr(StringUtils.defaultString(getParameter(request, categoryName, i, "addr"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 이해관계자 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static DcCredentialBizBean getCredInterestBiz(HttpServletRequest request, String prjtcd) {
		final String categoryName = "interest";
		List<DcCredentialBizBean> list = new ArrayList<DcCredentialBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcCredentialBizBean biz = new DcCredentialBizBean();
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setPrjtcd(prjtcd);
			biz.setBizcd(Constants.CredBiz.INTEREST);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setCompcd(compcd);
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			biz.setCity(StringUtils.defaultString(getParameter(request, categoryName, i, "city"), ""));
			biz.setAddr(StringUtils.defaultString(getParameter(request, categoryName, i, "addr"), ""));
			list.add(biz);
		}
		
		return list.get(0);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential BRS 매수처 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static DcCredentialBizBean getCredBRSBuyerBiz(HttpServletRequest request, String prjtcd) {
		final String categoryName = "brsbuyer";
		List<DcCredentialBizBean> list = new ArrayList<DcCredentialBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcCredentialBizBean biz = new DcCredentialBizBean();
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setPrjtcd(prjtcd);
			biz.setBizcd(Constants.CredBiz.BRSBUYER);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setCompcd(compcd);
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			biz.setCity(StringUtils.defaultString(getParameter(request, categoryName, i, "city"), ""));
			biz.setAddr(StringUtils.defaultString(getParameter(request, categoryName, i, "addr"), ""));
			list.add(biz);
		}
		
		return list.get(0);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential RCF 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static DcCredentialBizBean getCredRCFBiz(HttpServletRequest request, String prjtcd) {
		final String categoryName = "rcf";
		List<DcCredentialBizBean> list = new ArrayList<DcCredentialBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcCredentialBizBean biz = new DcCredentialBizBean();
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setPrjtcd(prjtcd);
			biz.setBizcd(Constants.CredBiz.RCF);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setCompcd(compcd);
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			biz.setCity(StringUtils.defaultString(getParameter(request, categoryName, i, "city"), ""));
			biz.setAddr(StringUtils.defaultString(getParameter(request, categoryName, i, "addr"), ""));
			list.add(biz);
		}
		
		return list.get(0);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 담보 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcCredentialAddBean> getCredBondsecureList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "bondsecure";
		List<DcCredentialAddBean> list = new ArrayList<DcCredentialAddBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcCredentialAddBean item = new DcCredentialAddBean();
			item.setPrjtcd(prjtcd);
			item.setItemcd(StringUtils.defaultString(getParameter(request, categoryName, i, "itemcd"), ""));
			item.setCtgcd(Constants.BondType.SECURE);
			list.add(item);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 무담보 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcCredentialAddBean> getCredBondsecurenoneList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "bondsecurenone";
		List<DcCredentialAddBean> list = new ArrayList<DcCredentialAddBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcCredentialAddBean item = new DcCredentialAddBean();
			item.setPrjtcd(prjtcd);
			item.setItemcd(StringUtils.defaultString(getParameter(request, categoryName, i, "itemcd"), ""));
			item.setCtgcd(Constants.BondType.NONE_SECURE);
			list.add(item);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static DcLtBuyBean getParameterBuyBean(HttpServletRequest request) {
		DcLtBuyBean project = new DcLtBuyBean();

		// 단일형 파라미터
		String prjtcd = StringUtils.defaultString(ServiceHelper.getParameter(request, "prjtcd"), "");
		project.setPrjtcd(prjtcd);
		project.setStatuscd(StringUtils.defaultString(ServiceHelper.getParameter(request, "statuscd"), ""));
		project.setConfcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "confcd"), ""));
		project.setComnm(StringUtils.defaultString(ServiceHelper.getParameter(request, "comnm"), ""));
		project.setTrgtetc(StringUtils.defaultString(ServiceHelper.getParameter(request, "trgtetc"), ""));
		project.setBuyoutcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "buyoutcd"), ""));
		project.setConsdt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "consdt"), "")));
		project.setSpadt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "spadt"), "")));
		project.setMoudt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "moudt"), "")));
		project.setClosdt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "closdt"), "")));
		project.setCurrcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "currcd"), ""));
		project.setRate(StringUtils.defaultString(ServiceHelper.getParameter(request, "rate"), ""));
		project.setAmt(StringUtils.defaultString(ServiceHelper.getParameter(request, "amt"), ""));
		project.setBorddealcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "borddealcd"), ""));
		project.setDealcd2(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealcd2"), ""));
		project.setDealnm2(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealnm2"), ""));
		project.setDealcd3(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealcd3"), ""));
		project.setDealnm3(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealnm3"), ""));
		project.setDealcd4(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealcd4"), ""));
		project.setDealnm4(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealnm4"), ""));
		// 목록형 파라미터
		project.setDeal1(getBuyDeal1List(request, prjtcd));
		project.setTarget(getBuyTargetBizList(request, prjtcd));
		project.setSeller(getBuySellerBizList(request, prjtcd));
		project.setSellfinance(getBuySellFinanceBizList(request, prjtcd));
		project.setSellaudit(getBuySellAuditBizList(request, prjtcd));
		project.setSelllaw(getBuySellLawBizList(request, prjtcd));
		project.setBuyer(getBuyBuyerBizList(request, prjtcd));
		project.setBuyfinance(getBuyBuyFinanceBizList(request, prjtcd));
		project.setBuyaudit(getBuyBuyAuditBizList(request, prjtcd));
		project.setBuylaw(getBuyBuyLawBizList(request, prjtcd));

		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static DcLtMnaBean getParameterMnaBean(HttpServletRequest request) {
		DcLtMnaBean project = new DcLtMnaBean();

		// 단일형 파라미터
		String prjtcd = StringUtils.defaultString(ServiceHelper.getParameter(request, "prjtcd"), "");
		project.setPrjtcd(prjtcd);
		project.setStatuscd(StringUtils.defaultString(ServiceHelper.getParameter(request, "statuscd"), ""));
		project.setConfcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "confcd"), ""));
		project.setComnm(StringUtils.defaultString(ServiceHelper.getParameter(request, "comnm"), ""));
		project.setConsdt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "consdt"), "")));
		project.setDirtdt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "dirtdt"), "")));
		project.setStkhdt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "stkhdt"), "")));
		project.setMnadt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "mnadt"), "")));
		project.setCurrcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "currcd"), ""));
		project.setRate(StringUtils.defaultString(ServiceHelper.getParameter(request, "rate"), ""));
		project.setAmt(StringUtils.defaultString(ServiceHelper.getParameter(request, "amt"), ""));
		project.setBorddealcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "borddealcd"), ""));
		project.setDealcd1(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealcd1"), ""));
		project.setDealnm1(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealnm1"), ""));
		project.setDealcd2(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealcd2"), ""));
		project.setDealnm2(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealnm2"), ""));
		project.setDealcd3(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealcd3"), ""));
		project.setDealnm3(StringUtils.defaultString(ServiceHelper.getParameter(request, "dealnm3"), ""));
		// 목록형 파라미터
		project.setMerging(getMnaMergingBizList(request, prjtcd));
		project.setMergingfinance(getMnaMergingFinanceBizList(request, prjtcd));
		project.setMergingaudit(getMnaMergingAuditBizList(request, prjtcd));
		project.setMerginglaw(getMnaMergingLawBizList(request, prjtcd));
		project.setMerged(getMnaMergedBizList(request, prjtcd));
		project.setMergedfinance(getMnaMergedFinanceBizList(request, prjtcd));
		project.setMergedaudit(getMnaMergedAuditBizList(request, prjtcd));
		project.setMergedlaw(getMnaMergedLawBizList(request, prjtcd));

		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static DcLtRealBean getParameterRealBean(HttpServletRequest request) {
		DcLtRealBean project = new DcLtRealBean();

		// 단일형 파라미터
		String prjtcd = StringUtils.defaultString(ServiceHelper.getParameter(request, "prjtcd"), "");
		project.setPrjtcd(prjtcd);
		project.setStatuscd(StringUtils.defaultString(ServiceHelper.getParameter(request, "statuscd"), ""));
		project.setConfcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "confcd"), ""));
		project.setComnm(StringUtils.defaultString(ServiceHelper.getParameter(request, "comnm"), ""));
		project.setConsdt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "consdt"), "")));
		project.setSpadt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "spadt"), "")));
		project.setMoudt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "moudt"), "")));
		project.setClosdt(ServiceHelper.stripForDateFormat(StringUtils.defaultString(ServiceHelper.getParameter(request, "closdt"), "")));
		project.setCurrcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "currcd"), ""));
		project.setRate(StringUtils.defaultString(ServiceHelper.getParameter(request, "rate"), ""));
		project.setAmt(StringUtils.defaultString(ServiceHelper.getParameter(request, "amt"), ""));
		project.setBorddealcd(StringUtils.defaultString(ServiceHelper.getParameter(request, "borddealcd"), ""));
		// 목록형 파라미터
		project.setTarget(getRealTargetBizList(request, prjtcd));
		project.setSeller(getRealSellerBizList(request, prjtcd));
		project.setSellfinance(getRealSellFinanceBizList(request, prjtcd));
		project.setSellaudit(getRealSellAuditBizList(request, prjtcd));
		project.setSelllaw(getRealSellLawBizList(request, prjtcd));
		project.setBuyer(getRealBuyerBizList(request, prjtcd));
		project.setBuyfinance(getRealBuyFinanceBizList(request, prjtcd));
		project.setBuyaudit(getRealBuyAuditBizList(request, prjtcd));
		project.setBuylaw(getRealBuyLawBizList(request, prjtcd));

		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 거래유형1 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtDealListBean> getBuyDeal1List(HttpServletRequest request, String prjtcd) {
		final String dealName = "deal1[]";
		List<DcLtDealListBean> list = new ArrayList<DcLtDealListBean>();
		
		String[] arrDeal1 = request.getParameterValues(dealName);
		if (arrDeal1 != null) {
			for (String dealitemcd : arrDeal1) {
				DcLtDealListBean deal = new DcLtDealListBean();
				deal.setPrjtcd(prjtcd);
				deal.setDealitemcd(ServiceHelper.stripXSS(dealitemcd));
				list.add(deal);
			}
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 대상 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getBuyTargetBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "target";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.BUY_TARGET);
			biz.setBizcd(Constants.LtBiz.MAIN);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			biz.setDealnm(StringUtils.defaultString(getParameter(request, categoryName, i, "dealnm"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 매각자 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getBuySellerBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "seller";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.BUY_SELLER);
			biz.setBizcd(Constants.LtBiz.MAIN);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 매각자 재무/전략 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getBuySellFinanceBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "sellfinance";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.BUY_SELLER);
			biz.setBizcd(Constants.LtBiz.FINANCE);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 매각자 회계 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getBuySellAuditBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "sellaudit";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.BUY_SELLER);
			biz.setBizcd(Constants.LtBiz.AUDIT);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 매각자 법률 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getBuySellLawBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "selllaw";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.BUY_SELLER);
			biz.setBizcd(Constants.LtBiz.LAW);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 인수자 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getBuyBuyerBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "buyer";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.BUY_BUYER);
			biz.setBizcd(Constants.LtBiz.MAIN);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 인수자 재무/전략 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getBuyBuyFinanceBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "buyfinance";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.BUY_BUYER);
			biz.setBizcd(Constants.LtBiz.FINANCE);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 인수자 회계 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getBuyBuyAuditBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "buyaudit";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.BUY_BUYER);
			biz.setBizcd(Constants.LtBiz.AUDIT);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 인수자 법률 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getBuyBuyLawBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "buylaw";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.BUY_BUYER);
			biz.setBizcd(Constants.LtBiz.LAW);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 합병(존속/신설) 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getMnaMergingBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "merging";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.MNA_MERGING);
			biz.setBizcd(Constants.LtBiz.MAIN);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 합병(존속/신설) 재무/전략 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getMnaMergingFinanceBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "mergingfinance";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.MNA_MERGING);
			biz.setBizcd(Constants.LtBiz.FINANCE);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 합병(존속/신설) 회계 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getMnaMergingAuditBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "mergingaudit";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.MNA_MERGING);
			biz.setBizcd(Constants.LtBiz.AUDIT);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 합병(존속/신설) 법률 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getMnaMergingLawBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "merginglaw";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.MNA_MERGING);
			biz.setBizcd(Constants.LtBiz.LAW);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 피합병(소멸) 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getMnaMergedBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "merged";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.MNA_MERGED);
			biz.setBizcd(Constants.LtBiz.MAIN);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 피합병(소멸) 재무/전략 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getMnaMergedFinanceBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "mergedfinance";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.MNA_MERGED);
			biz.setBizcd(Constants.LtBiz.FINANCE);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 피합병(소멸) 회계 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getMnaMergedAuditBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "mergedaudit";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.MNA_MERGED);
			biz.setBizcd(Constants.LtBiz.AUDIT);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 피합병(소멸) 법률 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getMnaMergedLawBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "mergedlaw";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.MNA_MERGED);
			biz.setBizcd(Constants.LtBiz.LAW);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 대상 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getRealTargetBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "target";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.REAL_TARGET);
			biz.setBizcd(Constants.LtBiz.MAIN);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			biz.setDealnm(StringUtils.defaultString(getParameter(request, categoryName, i, "dealnm"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 매각자 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getRealSellerBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "seller";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.REAL_SELLER);
			biz.setBizcd(Constants.LtBiz.MAIN);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 매각자 재무/전략 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getRealSellFinanceBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "sellfinance";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.REAL_SELLER);
			biz.setBizcd(Constants.LtBiz.FINANCE);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 매각자 회계 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getRealSellAuditBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "sellaudit";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.REAL_SELLER);
			biz.setBizcd(Constants.LtBiz.AUDIT);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 매각자 법률 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getRealSellLawBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "selllaw";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.REAL_SELLER);
			biz.setBizcd(Constants.LtBiz.LAW);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 인수자 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getRealBuyerBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "buyer";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.REAL_BUYER);
			biz.setBizcd(Constants.LtBiz.MAIN);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setInducd(StringUtils.defaultString(getParameter(request, categoryName, i, "inducd"), ""));
			biz.setIndunm(StringUtils.defaultString(getParameter(request, categoryName, i, "indunm"), ""));
			biz.setNatcd(StringUtils.defaultString(getParameter(request, categoryName, i, "natcd"), ""));
			biz.setNatnm(StringUtils.defaultString(getParameter(request, categoryName, i, "natnm"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 인수자 재무/전략 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getRealBuyFinanceBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "buyfinance";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.REAL_BUYER);
			biz.setBizcd(Constants.LtBiz.FINANCE);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 인수자 회계 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getRealBuyAuditBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "buyaudit";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.REAL_BUYER);
			biz.setBizcd(Constants.LtBiz.AUDIT);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 인수자 법률 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getRealBuyLawBizList(HttpServletRequest request, String prjtcd) {
		final String categoryName = "buylaw";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(prjtcd);
			biz.setActcd(Constants.Actor.REAL_BUYER);
			biz.setBizcd(Constants.LtBiz.LAW);
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			String compcd = StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), "");
			biz.setCompcd(compcd);
			biz.setBizdivcd(ServiceHelper.getBizDivCdConsult(compcd));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setChrgempno1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempno1"), ""));
			biz.setChrgempnm1(StringUtils.defaultString(getParameter(request, categoryName, i, "chrgempnm1"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문사 관리 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<AdminConsultListBean> getConsultBizList(HttpServletRequest request) {
		final String categoryName = "items";
		List<AdminConsultListBean> list = new ArrayList<AdminConsultListBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			AdminConsultListBean biz = new AdminConsultListBean();
			biz.setCompcd(StringUtils.defaultString(getParameter(request, categoryName, i, "compcd"), ""));
			biz.setComphannm(StringUtils.defaultString(getParameter(request, categoryName, i, "comphannm"), ""));
			biz.setCompengnm(StringUtils.defaultString(getParameter(request, categoryName, i, "compengnm"), ""));
			biz.setFinanceyn(StringUtils.defaultString(getParameter(request, categoryName, i, "financeyn"), ""));
			biz.setAudityn(StringUtils.defaultString(getParameter(request, categoryName, i, "audityn"), ""));
			biz.setLawyn(StringUtils.defaultString(getParameter(request, categoryName, i, "lawyn"), ""));
			biz.setUseyn(StringUtils.defaultString(getParameter(request, categoryName, i, "useyn"), ""));
			
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 삭제된 Credential 기업정보 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcCredentialBizBean> getParameterCredRemoveBiz(HttpServletRequest request) {
		final String categoryName = "remove";
		List<DcCredentialBizBean> list = new ArrayList<DcCredentialBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcCredentialBizBean biz = new DcCredentialBizBean();
			biz.setPrjtcd(StringUtils.defaultString(getParameter(request, categoryName, i, "prjtcd"), ""));
			biz.setBizcd(StringUtils.defaultString(getParameter(request, categoryName, i, "bizcd"), ""));
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 삭제된 League Table 기업정보 파라미터
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static List<DcLtBizBean> getParameterRemoveBiz(HttpServletRequest request) {
		final String categoryName = "remove";
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();
		
		int count = getCategoryCount(request, categoryName);
		for (int i = 0; i < count; i++) {
			DcLtBizBean biz = new DcLtBizBean();
			biz.setPrjtcd(StringUtils.defaultString(getParameter(request, categoryName, i, "prjtcd"), ""));
			biz.setActcd(StringUtils.defaultString(getParameter(request, categoryName, i, "actcd"), ""));
			biz.setBizcd(StringUtils.defaultString(getParameter(request, categoryName, i, "bizcd"), ""));
			biz.setSeq(StringUtils.defaultString(getParameter(request, categoryName, i, "seq"), ""));
			list.add(biz);
		}
		
		return list;
	}
	
	public static String getValue(HttpServletRequest request, String parameterName) {
		return ServiceHelper.getParameter(request, parameterName);
	}
	
	public static int getCategoryCount(HttpServletRequest request, String categoryName) {
		int maxIndex = -1;
		Map<String, String[]> paramMap = request.getParameterMap();
		Iterator<String> iter = paramMap.keySet().iterator();
		while (iter.hasNext()) {
			String parameterName = iter.next();
			if (isCategory(parameterName, categoryName)) {
				int tmpIndex = getCategoryIndex(parameterName, categoryName);
				if (tmpIndex > maxIndex) {
					maxIndex = tmpIndex;
				}
			}
		}
		return ++maxIndex;
	}

	public static String getParameter(HttpServletRequest request, String categoryName, int index, String itemName) {
		StringBuffer paramName = new StringBuffer();
		paramName.append(categoryName);
		paramName.append(ARRAY_BRACKET_OPEN);
		paramName.append(String.valueOf(index));
		paramName.append(ARRAY_BRACKET_CLOSE);
		paramName.append(ARRAY_BRACKET_OPEN);
		paramName.append(itemName);
		paramName.append(ARRAY_BRACKET_CLOSE);
		return getValue(request, paramName.toString());
	}
	
	private static boolean isCategory(String parameterName, String categoryName) {
		return parameterName.startsWith(categoryName + ARRAY_BRACKET_OPEN);
	}
	
	private static int getCategoryIndex(String parameterName, String categoryName) {
		int index = new String(categoryName + ARRAY_BRACKET_OPEN).length();
		int index2 = parameterName.indexOf(ARRAY_BRACKET_CLOSE);
		String tmp = parameterName.substring(index, index2);
		if (StringUtils.isNumeric(tmp)) {
			return Integer.valueOf(tmp);
		} else {
			return 0;
		}
	}
}
