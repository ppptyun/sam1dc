package com.samil.dc.service.excel.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.access.OracleConnection;
import com.samil.dc.dao.DcExcelImportDAO;
import com.samil.dc.service.excel.AbstractSheetProcessor;
import com.samil.dc.service.excel.DefColumn;
import com.samil.dc.service.excel.ExcelHelper;
import com.samil.dc.service.excel.ExcelProcessException;
import com.samil.dc.service.excel.bean.ImportBizBean;
import com.samil.dc.service.excel.bean.ImportBuyBean;
import com.samil.dc.service.worker.ServiceHelper;
import com.samil.dc.util.Constants;

import jxl.Cell;
import jxl.Sheet;

/**
 * 마이그레이션용_인수매각자문 Sheet 처리 클래스
 *
 */
public class ImportSheetBuyProcessor extends AbstractSheetProcessor {
	
	private List<ImportBuyBean> list = new ArrayList<ImportBuyBean>();

	public ImportSheetBuyProcessor(DBConnection connection, Sheet sheet) throws ExcelProcessException {
		super(connection, sheet);
		title = "마이그레이션용_인수매각자문";
	}

	@Override
	public AbstractSheetProcessor ready() throws ExcelProcessException {
		// 컬럼 정의
		columns.add(new DefColumn("yearly", "프로젝트 정보 > 대상년도"));
		columns.add(new DefColumn("prjtcd", "프로젝트 정보 > 프로젝트 > Code", REQUIRED));
		columns.add(new DefColumn("prjtnm", "프로젝트 정보 > 프로젝트 > 용역명"));
		columns.add(new DefColumn("hqcd", "프로젝트 정보 > 담당본부 > Code"));
		columns.add(new DefColumn("hqnm", "프로젝트 정보 > 담당본부 > 본부명"));
		columns.add(new DefColumn("ptrempno", "프로젝트 정보 > EL", ExcelHelper.COLUMN_TYPE_EMP));
		columns.add(new DefColumn("mgrempno", "프로젝트 정보 > TM", ExcelHelper.COLUMN_TYPE_EMP));
		columns.add(new DefColumn("processnm", "프로젝트 정보 > 진행상태"));
		columns.add(new DefColumn("contdt", "프로젝트 정보 > 용역기간 > 개시일", ExcelHelper.COLUMN_TYPE_DATE));
		columns.add(new DefColumn("termidt", "프로젝트 정보 > 용역기간 > 종료일", ExcelHelper.COLUMN_TYPE_DATE));
		columns.add(new DefColumn("pdtcd", "Product > Produce > Code"));
		columns.add(new DefColumn("pdtnm", "Product > Produce > Name"));
		// Client
		columns.add(new DefColumn("client.compcd", "Client 정보 > Client > 코드"));
		columns.add(new DefColumn("client.comphannm", "Client 정보 > Client > 한글 기업명"));
		columns.add(new DefColumn("client.compengnm", "Client 정보 > Client > 영문 기업명"));
		columns.add(new DefColumn("client.inducd", "Client 정보 > Industry > Code"));
		columns.add(new DefColumn("client.indunm", "Client 정보 > Industry > Name"));
		// 기타
		columns.add(new DefColumn("buyoutcd", "Buyout/Non-Buyout > 코드", new String[]{"100901", "100902"}));
		columns.add(new DefColumn(false));
		columns.add(new DefColumn("comnm", "내부 Comment"));
		columns.add(new DefColumn("statuscd", "Status > 코드", new String[]{"100601", "100602", "100603"}));
		columns.add(new DefColumn(false));
		// Target
		columns.add(new DefColumn("target.compcd", "Target > 기업명 > Code"));
		columns.add(new DefColumn("target.comphannm", "Target > 기업명 > 한글"));
		columns.add(new DefColumn("target.compengnm", "Target > 기업명 > 영문"));
		columns.add(new DefColumn("target.natcd", "Target > 국적 > 코드"));
		columns.add(new DefColumn("target.natnm", "Target > 국적 > 명칭"));
		columns.add(new DefColumn("target.inducd", "Target > Industry > 코드"));
		columns.add(new DefColumn("target.indunm", "Target > Industry > 명칭"));
		columns.add(new DefColumn("target.dealnm", "Target > 거래물"));
		// 기타
		columns.add(new DefColumn("trgtetc", "Target > 비고"));
		// Seller-Main
		columns.add(new DefColumn("seller.compcd", "Seller > 기업명 > 코드"));
		columns.add(new DefColumn("seller.comphannm", "Seller > 기업명 > 한글"));
		columns.add(new DefColumn("seller.compengnm", "Seller > 기업명 > 영문"));
		columns.add(new DefColumn("seller.natcd", "Seller > 국적 > 코드"));
		columns.add(new DefColumn("seller.natnm", "Seller > 국적 > 명칭"));
		columns.add(new DefColumn("seller.inducd", "Seller > Industry > 코드"));
		columns.add(new DefColumn("seller.indunm", "Seller > Industry > 명칭"));
		// Seller-Finance
		columns.add(new DefColumn("sellfinance.compcd", "Seller > 재무전략 자문사 > 코드"));
		columns.add(new DefColumn("sellfinance.comphannm", "Seller > 재무전략 자문사 > 자문사 명"));
		columns.add(new DefColumn("sellfinance.chrgempnm1", "Seller > 재무전략 자문사 > 담당자"));
		// Seller-Audit
		columns.add(new DefColumn("sellaudit.compcd", "Seller > 회계 자문사 > 코드"));
		columns.add(new DefColumn("sellaudit.comphannm", "Seller > 회계 자문사 > 자문사 명"));
		columns.add(new DefColumn("sellaudit.chrgempnm1", "Seller > 회계 자문사 > 담당자"));
		// Seller-Law
		columns.add(new DefColumn("selllaw.compcd", "Seller > 법률 자문사 > 코드"));
		columns.add(new DefColumn("selllaw.comphannm", "Seller > 법률 자문사 > 자문사 명"));
		columns.add(new DefColumn("selllaw.chrgempnm1", "Seller > 법률 자문사 > 담당자"));
		// Buyer-Main
		columns.add(new DefColumn("buyer.compcd", "Buyer > 기업명 > 코드"));
		columns.add(new DefColumn("buyer.comphannm", "Buyer > 기업명 > 한글"));
		columns.add(new DefColumn("buyer.compengnm", "Buyer > 기업명 > 영문"));
		columns.add(new DefColumn("buyer.natcd", "Buyer > 국적 > 코드"));
		columns.add(new DefColumn("buyer.natnm", "Buyer > 국적 > 명칭"));
		columns.add(new DefColumn("buyer.inducd", "Buyer > Industry > 코드"));
		columns.add(new DefColumn("buyer.indunm", "Buyer > Industry > 명칭"));
		// Buyer-Finance
		columns.add(new DefColumn("buyfinance.compcd", "Buyer > 재무전략 자문사 > 코드"));
		columns.add(new DefColumn("buyfinance.comphannm", "Buyer > 재무전략 자문사 > 자문사 명"));
		columns.add(new DefColumn("buyfinance.chrgempnm1", "Buyer > 재무전략 자문사 > 담당자"));
		// Buyer-Audit
		columns.add(new DefColumn("buyaudit.compcd", "Buyer > 회계 자문사 > 코드"));
		columns.add(new DefColumn("buyaudit.comphannm", "Buyer > 회계 자문사 > 자문사 명"));
		columns.add(new DefColumn("buyaudit.chrgempnm1", "Buyer > 회계 자문사 > 담당자"));
		// Buyer-Law
		columns.add(new DefColumn("buylaw.compcd", "Buyer > 법률 자문사 > 코드"));
		columns.add(new DefColumn("buylaw.comphannm", "Buyer > 법률 자문사 > 자문사 명"));
		columns.add(new DefColumn("buylaw.chrgempnm1", "Buyer > 법률 자문사 > 담당자"));
		// 일자
		columns.add(new DefColumn("consdt", "일자 > 자문계약", ExcelHelper.COLUMN_TYPE_DATE));
		columns.add(new DefColumn("spadt", "일자 > 매매계약(SPA)", ExcelHelper.COLUMN_TYPE_DATE));
		columns.add(new DefColumn("moudt", "일자 > MOU체결", ExcelHelper.COLUMN_TYPE_DATE));
		columns.add(new DefColumn("closdt", "일자 > 잔금납입(Closing)", ExcelHelper.COLUMN_TYPE_DATE));
		// 금액 및 성격
		columns.add(new DefColumn("currcd", "금액 및 성격 > 통화 > 코드"));
		columns.add(new DefColumn("currnm", "금액 및 성격 > 통화 > 통화명"));
		columns.add(new DefColumn("rate", "금액 및 성격 > 환율(1통화당 원화)", ExcelHelper.COLUMN_TYPE_NUMERIC));
		columns.add(new DefColumn("amt", "금액 및 성격 > 금액", ExcelHelper.COLUMN_TYPE_NUMERIC));
		columns.add(new DefColumn("borddealcd", "금액 및 성격 > 국경간거래 > 코드", new String[]{"101101", "101102"}));
		columns.add(new DefColumn(false));
		columns.add(new DefColumn("deal1.dealitemcd", "금액 및 성격 > 거래형태1 > 코드", new String[]{"101201", "101202", "101203", "101204", "101205", "101206"}));
		columns.add(new DefColumn(false));
		columns.add(new DefColumn("dealcd2", "금액 및 성격 > 거래형태2 > 코드", new String[]{"101301", "101302", "101303"}));
		columns.add(new DefColumn(false));
		columns.add(new DefColumn("dealcd3", "금액 및 성격 > 거래형태3 > 코드", new String[]{"101401", "101402"}));
		columns.add(new DefColumn(false));
		
		return this;
	}
	
	@Override
	public AbstractSheetProcessor parse() throws ExcelProcessException {
		for (int r = ExcelHelper.ROW_INDEX_START; r < sheet.getRows(); r++) {
			int rownum = r + 1;
			// Project Code 읽기
			String prjtcd = StringUtils.defaultIfBlank(sheet.getCell(ExcelHelper.COLUMN_INDEX_PROJECTCODE, r).getContents(), "").trim();
			if (StringUtils.isBlank(prjtcd)) {
				// 프로젝트 코드 값이 없으면 뛰어넘는다.
				continue;
			}
			
			// row-column 단위로 읽어서 저장하기
			ImportBuyBean project = new ImportBuyBean(r, ExcelHelper.PROJECT_REFYEARLY, prjtcd);
			if (!ServiceHelper.isValidProjectCode(prjtcd)) {
				pileUpErrorLog(rownum + "행 프로젝트 코드[" + prjtcd + "] 유효하지 않습니다.");
				project.setValid(false);
				// 프로젝트 코드가 유효하기 않으면 해당 로우는 읽어들이지 않는다.
				continue;
			}
			for (int c = 0; c < columns.size(); c++) {
				DefColumn column = columns.get(c);
				String property = column.getCode();
				int columnnum = c + 1;
				// 사용 여부 체크
				if (column.isUsed()) {
					Cell cell = sheet.getCell(c, r);
					String data = StringUtils.defaultIfBlank(cell.getContents(), "").trim();
					// 필수 입력값 여부 체크
					if (column.isRequired() && StringUtils.isBlank(data)) {
						pileUpErrorLog("[" + rownum + "|" + columnnum + "] [" + column.getTitle() + "] 필수 입력 항목입니다.");
						project.setValid(false);
					}
					// 데이터 타입 처리
					switch (column.getType()) {
					case ExcelHelper.COLUMN_TYPE_EMP:
						data = ServiceHelper.doLPadEmpno(data);
						break;
					case ExcelHelper.COLUMN_TYPE_DATE:
						data = ServiceHelper.stripForDateFormat(ExcelHelper.convertDateFormat(data));
						break;
					case ExcelHelper.COLUMN_TYPE_NUMERIC:
						if (!StringUtils.isBlank(data)) {
							data = ExcelHelper.stripForNumberFormat(data);
							if (!NumberUtils.isNumber(data)) {
								pileUpErrorLog("[" + rownum + "|" + columnnum + "] [" + column.getTitle() + "] 숫자형 입력 항목입니다.");
								project.setValid(false);
							}
						}
						break;
					case ExcelHelper.COLUMN_TYPE_COMCD:
						if (!StringUtils.isBlank(data)) {
							if (!ExcelHelper.isContain(data, column.getItems())) {
								pileUpErrorLog("[" + rownum + "|" + columnnum + "] [" + column.getTitle() + "] 적합한 코드값이 아닙니다.");
								project.setValid(false);
							}
						}
						break;
					}
					if (logger.isDebugEnabled()) {
						logger.debug("[" + rownum + "|" + columnnum + "] [" + property + "=" + data + "]");
					}
					// 데이터 필드에 저장
					try {
						if (property.contains(".")) {
							PropertyUtils.setNestedProperty(project, property, data);
						} else {
							PropertyUtils.setProperty(project, property, data);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			list.add(project);
			if (logger.isDebugEnabled()) {
				logger.debug(project.toString());
			}
		}
		
		return this;
	}

	@Override
	public AbstractSheetProcessor save() throws ExcelProcessException {
		if (list == null || list.size() == 0) {
			return this;
		}
		
		
		//DcExcelImportDAO dao = new DcExcelImportDAO(connection);
		DBConnection dbcon = null;
		DcExcelImportDAO dao = null;
		
		try {
			for (ImportBuyBean project : list) {
				if (!project.isValid()) {
					continue;
				}
				
				if (dbcon != null) {
					dbcon.close();
					dbcon = null;
				}
				dbcon = new OracleConnection();
				dao = new DcExcelImportDAO(dbcon);
				
				String prjtcd = project.getPrjtcd();
				boolean exist = false;
				
				// 가상 프로젝트인지 판별
				boolean virtual = ExcelHelper.isVirtualProjectCode(prjtcd);
				if (!virtual) {
					// 존재하는 프로젝트이면 기존 정보 조회
					project = dao.sqlSelectProjectInfo(project);
				}
				
				// 종료여부 체크한 후
				if (TERMINATED.equals(project.getProcessnm())) {
					// 프로젝트 기존 존재 여부
					exist = dao.sqlSelectExistCredentialProject(prjtcd);
					if (!exist) {
						// Credential 정보 저장
						dao.sqlInsertCredential(project);
						
						// Credential Client 정보 저장
						ImportBizBean client = project.getClient();
						if (!StringUtils.isBlank(client.getComphannm())) {
							client.setBizcd(Constants.CredBiz.CLIENT);
							client.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(client.getCompcd()));
							dao.sqlInsertCredentialBiz(client);
						}
					}
				}
				
				// 프로젝트 기존 존재 여부
				exist = dao.sqlSelectExistLeagueProject(prjtcd);
				if (!exist) {
					// 기타 정보 삭제 (혹시 모를 찌꺼기 제거)
					dao.sqlDeleteLeagueForUpdate(project);
					// League Table 정보 저장
					dao.sqlInsertLeagueTable(project);
					
				} else {
					// League Table 정보 수정
					dao.sqlUpdateLeagueBase(project);

					// 기타 정보 삭제 (이후 로직에서 새롭게 등록)
					dao.sqlDeleteLeagueForUpdate(project);

					// League Table Buy 정보 등록
					dao.sqlInsertLeagueTableSub(project);
				}
				
				// League Table Target 정보 저장
				ImportBizBean target = project.getTarget();
				if (!StringUtils.isBlank(target.getComphannm())) {
					target.setActcd(Constants.Actor.BUY_TARGET);
					target.setBizcd(Constants.LtBiz.MAIN);
					target.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(target.getCompcd()));
					dao.sqlInsertLeagueBizCommon(target);
				}
				
				// League Table Seller 정보 저장
				ImportBizBean seller = project.getSeller();
				if (!StringUtils.isBlank(seller.getComphannm())) {
					seller.setActcd(Constants.Actor.BUY_SELLER);
					seller.setBizcd(Constants.LtBiz.MAIN);
					seller.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(seller.getCompcd()));
					dao.sqlInsertLeagueBizCommon(seller);
				}
				
				// League Table Seller-Finance 정보 저장
				ImportBizBean sellfinance = project.getSellfinance();
				if (!StringUtils.isBlank(sellfinance.getComphannm())) {
					sellfinance.setActcd(Constants.Actor.BUY_SELLER);
					sellfinance.setBizcd(Constants.LtBiz.FINANCE);
					sellfinance.setBizdivcd(ServiceHelper.getBizDivCdConsult(sellfinance.getCompcd()));
					if (!StringUtils.isBlank(sellfinance.getChrgempnm1())) {
						sellfinance.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(sellfinance);
				}
				
				// League Table Seller-Audit 정보 저장
				ImportBizBean sellaudit = project.getSellaudit();
				if (!StringUtils.isBlank(sellaudit.getComphannm())) {
					sellaudit.setActcd(Constants.Actor.BUY_SELLER);
					sellaudit.setBizcd(Constants.LtBiz.AUDIT);
					sellaudit.setBizdivcd(ServiceHelper.getBizDivCdConsult(sellaudit.getCompcd()));
					if (!StringUtils.isBlank(sellaudit.getChrgempnm1())) {
						sellaudit.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(sellaudit);
				}
				
				// League Table Seller-Law 정보 저장
				ImportBizBean selllaw = project.getSelllaw();
				if (!StringUtils.isBlank(selllaw.getComphannm())) {
					selllaw.setActcd(Constants.Actor.BUY_SELLER);
					selllaw.setBizcd(Constants.LtBiz.LAW);
					selllaw.setBizdivcd(ServiceHelper.getBizDivCdConsult(selllaw.getCompcd()));
					if (!StringUtils.isBlank(selllaw.getChrgempnm1())) {
						selllaw.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(selllaw);
				}
				
				// League Table Buyer 정보 저장
				ImportBizBean buyer = project.getBuyer();
				if (!StringUtils.isBlank(buyer.getComphannm())) {
					buyer.setActcd(Constants.Actor.BUY_BUYER);
					buyer.setBizcd(Constants.LtBiz.MAIN);
					buyer.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(buyer.getCompcd()));
					dao.sqlInsertLeagueBizCommon(buyer);
				}
				
				// League Table Buyer-Finance 정보 저장
				ImportBizBean buyfinance = project.getBuyfinance();
				if (!StringUtils.isBlank(buyfinance.getComphannm())) {
					buyfinance.setActcd(Constants.Actor.BUY_BUYER);
					buyfinance.setBizcd(Constants.LtBiz.FINANCE);
					buyfinance.setBizdivcd(ServiceHelper.getBizDivCdConsult(buyfinance.getCompcd()));
					if (!StringUtils.isBlank(buyfinance.getChrgempnm1())) {
						buyfinance.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(buyfinance);
				}
				
				// League Table Buyer-Audit 정보 저장
				ImportBizBean buyaudit = project.getBuyaudit();
				if (!StringUtils.isBlank(buyaudit.getComphannm())) {
					buyaudit.setActcd(Constants.Actor.BUY_BUYER);
					buyaudit.setBizcd(Constants.LtBiz.AUDIT);
					buyaudit.setBizdivcd(ServiceHelper.getBizDivCdConsult(buyaudit.getCompcd()));
					if (!StringUtils.isBlank(buyaudit.getChrgempnm1())) {
						buyaudit.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(buyaudit);
				}
				
				// League Table Buyer-Law 정보 저장
				ImportBizBean buylaw = project.getBuylaw();
				if (!StringUtils.isBlank(buylaw.getComphannm())) {
					buylaw.setActcd(Constants.Actor.BUY_BUYER);
					buylaw.setBizcd(Constants.LtBiz.LAW);
					buylaw.setBizdivcd(ServiceHelper.getBizDivCdConsult(buylaw.getCompcd()));
					if (!StringUtils.isBlank(buylaw.getChrgempnm1())) {
						buylaw.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(buylaw);
				}
				
				// League Table 거래유형1 정보 저장
				ImportBizBean deal1 = project.getDeal1();
				if (!StringUtils.isBlank(deal1.getDealitemcd())) {
					dao.sqlInsertLeagueDeal1(deal1);
				}
				
				successCount++;
			}
		} catch (Exception e) {
			throw new ExcelProcessException(Constants.ErrorMessage.FAIL_SQL_INSERT);
		} finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		
		return this;
	}
	
	@Override
	public int getReadRowCount() {
		return list.size();
	}
}
