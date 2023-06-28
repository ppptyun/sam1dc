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
import com.samil.dc.service.excel.bean.ImportMnaBean;
import com.samil.dc.service.worker.ServiceHelper;
import com.samil.dc.util.Constants;

import jxl.Cell;
import jxl.Sheet;

/**
 * 마이그레이션용_합병자문 Sheet 처리 클래스
 *
 */
public class ImportSheetMnaProcessor extends AbstractSheetProcessor {
	
	private List<ImportMnaBean> list = new ArrayList<ImportMnaBean>();

	public ImportSheetMnaProcessor(DBConnection connection, Sheet sheet) throws ExcelProcessException {
		super(connection, sheet);
		title = "마이그레이션용_합병자문";
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
		columns.add(new DefColumn("comnm", "내부 Comment"));
		columns.add(new DefColumn("statuscd", "Status > 코드", new String[]{"100601", "100602", "100603"}));
		columns.add(new DefColumn(false));
		// Merging-Main
		columns.add(new DefColumn("merging.compcd", "합병(존속/신설) 법인 > 기업명 > 코드"));
		columns.add(new DefColumn("merging.comphannm", "합병(존속/신설) 법인 > 기업명 > 한글"));
		columns.add(new DefColumn("merging.compengnm", "합병(존속/신설) 법인 > 기업명 > 영문"));
		columns.add(new DefColumn("merging.natcd", "합병(존속/신설) 법인 > 국적 > 코드"));
		columns.add(new DefColumn("merging.natnm", "합병(존속/신설) 법인 > 국적 > 명칭"));
		columns.add(new DefColumn("merging.inducd", "합병(존속/신설) 법인 > Industry > 코드"));
		columns.add(new DefColumn("merging.indunm", "합병(존속/신설) 법인 > Industry > 명칭"));
		// Merging-Finance
		columns.add(new DefColumn("mergingfinance.compcd", "합병(존속/신설) 법인 > 재무전략 자문사 > 코드"));
		columns.add(new DefColumn("mergingfinance.comphannm", "합병(존속/신설) 법인 > 재무전략 자문사 > 자문사 명"));
		columns.add(new DefColumn("mergingfinance.chrgempnm1", "합병(존속/신설) 법인 > 재무전략 자문사 > 담당자"));
		// Merging-Audit
		columns.add(new DefColumn("mergingaudit.compcd", "합병(존속/신설) 법인 > 회계 자문사 > 코드"));
		columns.add(new DefColumn("mergingaudit.comphannm", "합병(존속/신설) 법인 > 회계 자문사 > 자문사 명"));
		columns.add(new DefColumn("mergingaudit.chrgempnm1", "합병(존속/신설) 법인 > 회계 자문사 > 담당자"));
		// Merging-Law
		columns.add(new DefColumn("merginglaw.compcd", "합병(존속/신설) 법인 > 법률 자문사 > 코드"));
		columns.add(new DefColumn("merginglaw.comphannm", "합병(존속/신설) 법인 > 법률 자문사 > 자문사 명"));
		columns.add(new DefColumn("merginglaw.chrgempnm1", "합병(존속/신설) 법인 > 법률 자문사 > 담당자"));
		// Merged-Main
		columns.add(new DefColumn("merged.compcd", "피합병(소멸)법인 > 기업명 > 코드"));
		columns.add(new DefColumn("merged.comphannm", "피합병(소멸)법인 > 기업명 > 한글"));
		columns.add(new DefColumn("merged.compengnm", "피합병(소멸)법인 > 기업명 > 영문"));
		columns.add(new DefColumn("merged.natcd", "피합병(소멸)법인 > 국적 > 코드"));
		columns.add(new DefColumn("merged.natnm", "피합병(소멸)법인 > 국적 > 명칭"));
		columns.add(new DefColumn("merged.inducd", "피합병(소멸)법인 > Industry > 코드"));
		columns.add(new DefColumn("merged.indunm", "피합병(소멸)법인 > Industry > 명칭"));
		// Merged-Finance
		columns.add(new DefColumn("mergedfinance.compcd", "피합병(소멸)법인 > 재무전략 자문사 > 코드"));
		columns.add(new DefColumn("mergedfinance.comphannm", "피합병(소멸)법인 > 재무전략 자문사 > 자문사 명"));
		columns.add(new DefColumn("mergedfinance.chrgempnm1", "피합병(소멸)법인 > 재무전략 자문사 > 담당자"));
		// Merged-Audit
		columns.add(new DefColumn("mergedaudit.compcd", "피합병(소멸)법인 > 회계 자문사 > 코드"));
		columns.add(new DefColumn("mergedaudit.comphannm", "피합병(소멸)법인 > 회계 자문사 > 자문사 명"));
		columns.add(new DefColumn("mergedaudit.chrgempnm1", "피합병(소멸)법인 > 회계 자문사 > 담당자"));
		// Merged-Law
		columns.add(new DefColumn("mergedlaw.compcd", "피합병(소멸)법인 > 법률 자문사 > 코드"));
		columns.add(new DefColumn("mergedlaw.comphannm", "피합병(소멸)법인 > 법률 자문사 > 자문사 명"));
		columns.add(new DefColumn("mergedlaw.chrgempnm1", "피합병(소멸)법인 > 법률 자문사 > 담당자"));
		// 일자
		columns.add(new DefColumn("consdt", "일자 > 자문계약", ExcelHelper.COLUMN_TYPE_DATE));
		columns.add(new DefColumn("dirtdt", "일자 > 이사회 의결일", ExcelHelper.COLUMN_TYPE_DATE));
		columns.add(new DefColumn("stkhdt", "일자 > 주주총회 의결일", ExcelHelper.COLUMN_TYPE_DATE));
		columns.add(new DefColumn("mnadt", "일자 > 합병기일", ExcelHelper.COLUMN_TYPE_DATE));
		// 금액 및 성격
		columns.add(new DefColumn("currcd", "금액 및 성격 > 통화 > 코드"));
		columns.add(new DefColumn("currnm", "금액 및 성격 > 통화 > 통화명"));
		columns.add(new DefColumn("rate", "금액 및 성격 > 환율(1통화당 원화)", ExcelHelper.COLUMN_TYPE_NUMERIC));
		columns.add(new DefColumn("amt", "금액 및 성격 > 금액", ExcelHelper.COLUMN_TYPE_NUMERIC));
		columns.add(new DefColumn("borddealcd", "금액 및 성격 > 국경간거래 > 코드", new String[]{"101101", "101102"}));
		columns.add(new DefColumn(false));
		columns.add(new DefColumn("dealcd1", "금액 및 성격 > 거래형태1 > 코드", new String[]{"101601", "101602"}));
		columns.add(new DefColumn(false));
		columns.add(new DefColumn("dealcd2", "금액 및 성격 > 거래형태2 > 코드", new String[]{"101701", "101702"}));
		columns.add(new DefColumn(false));
		columns.add(new DefColumn("dealcd3", "금액 및 성격 > 거래형태3 > 코드", new String[]{"101801", "101802", "101803", "101804", "101805", "101806", "101807", "101808"}));
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
			ImportMnaBean project = new ImportMnaBean(r, ExcelHelper.PROJECT_REFYEARLY, prjtcd);
			if (!ServiceHelper.isValidProjectCode(prjtcd)) {
				pileUpErrorLog(rownum + "행 프로젝트 코드[" + prjtcd + "] 유효하지 않습니다.");
				project.setValid(false);
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
			for (ImportMnaBean project : list) {
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

					// League Table M&A 정보 등록
					dao.sqlInsertLeagueTableSub(project);
				}
				
				// League Table Merging 정보 저장
				ImportBizBean merging = project.getMerging();
				if (!StringUtils.isBlank(merging.getComphannm())) {
					merging.setActcd(Constants.Actor.MNA_MERGING);
					merging.setBizcd(Constants.LtBiz.MAIN);
					merging.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(merging.getCompcd()));
					dao.sqlInsertLeagueBizCommon(merging);
				}
				
				// League Table Merging-Finance 정보 저장
				ImportBizBean mergingfinance = project.getMergingfinance();
				if (!StringUtils.isBlank(mergingfinance.getComphannm())) {
					mergingfinance.setActcd(Constants.Actor.MNA_MERGING);
					mergingfinance.setBizcd(Constants.LtBiz.FINANCE);
					mergingfinance.setBizdivcd(ServiceHelper.getBizDivCdConsult(mergingfinance.getCompcd()));
					if (!StringUtils.isBlank(mergingfinance.getChrgempnm1())) {
						mergingfinance.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(mergingfinance);
				}
				
				// League Table Merging-Audit 정보 저장
				ImportBizBean mergingaudit = project.getMergingaudit();
				if (!StringUtils.isBlank(mergingaudit.getComphannm())) {
					mergingaudit.setActcd(Constants.Actor.MNA_MERGING);
					mergingaudit.setBizcd(Constants.LtBiz.AUDIT);
					mergingaudit.setBizdivcd(ServiceHelper.getBizDivCdConsult(mergingaudit.getCompcd()));
					if (!StringUtils.isBlank(mergingaudit.getChrgempnm1())) {
						mergingaudit.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(mergingaudit);
				}
				
				// League Table Merging-Law 정보 저장
				ImportBizBean merginglaw = project.getMerginglaw();
				if (!StringUtils.isBlank(merginglaw.getComphannm())) {
					merginglaw.setActcd(Constants.Actor.MNA_MERGING);
					merginglaw.setBizcd(Constants.LtBiz.LAW);
					merginglaw.setBizdivcd(ServiceHelper.getBizDivCdConsult(merginglaw.getCompcd()));
					if (!StringUtils.isBlank(merginglaw.getChrgempnm1())) {
						merginglaw.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(merginglaw);
				}
				
				// League Table Merged 정보 저장
				ImportBizBean merged = project.getMerged();
				if (!StringUtils.isBlank(merged.getComphannm())) {
					merged.setActcd(Constants.Actor.MNA_MERGED);
					merged.setBizcd(Constants.LtBiz.MAIN);
					merged.setBizdivcd(ServiceHelper.getBizDivCdNonConsult(merged.getCompcd()));
					dao.sqlInsertLeagueBizCommon(merged);
				}
				
				// League Table Merged-Finance 정보 저장
				ImportBizBean mergedfinance = project.getMergedfinance();
				if (!StringUtils.isBlank(mergedfinance.getComphannm())) {
					mergedfinance.setActcd(Constants.Actor.MNA_MERGED);
					mergedfinance.setBizcd(Constants.LtBiz.FINANCE);
					mergedfinance.setBizdivcd(ServiceHelper.getBizDivCdConsult(mergedfinance.getCompcd()));
					if (!StringUtils.isBlank(mergedfinance.getChrgempnm1())) {
						mergedfinance.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(mergedfinance);
				}
				
				// League Table Merged-Audit 정보 저장
				ImportBizBean mergedaudit = project.getMergedaudit();
				if (!StringUtils.isBlank(mergedaudit.getComphannm())) {
					mergedaudit.setActcd(Constants.Actor.MNA_MERGED);
					mergedaudit.setBizcd(Constants.LtBiz.AUDIT);
					mergedaudit.setBizdivcd(ServiceHelper.getBizDivCdConsult(mergedaudit.getCompcd()));
					if (!StringUtils.isBlank(mergedaudit.getChrgempnm1())) {
						mergedaudit.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(mergedaudit);
				}
				
				// League Table Merged-Law 정보 저장
				ImportBizBean mergedlaw = project.getMergedlaw();
				if (!StringUtils.isBlank(mergedlaw.getComphannm())) {
					mergedlaw.setActcd(Constants.Actor.MNA_MERGED);
					mergedlaw.setBizcd(Constants.LtBiz.LAW);
					mergedlaw.setBizdivcd(ServiceHelper.getBizDivCdConsult(mergedlaw.getCompcd()));
					if (!StringUtils.isBlank(mergedlaw.getChrgempnm1())) {
						mergedlaw.setChrgempno1(MANUALLY);
					}
					dao.sqlInsertLeagueBizConsult(mergedlaw);
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
