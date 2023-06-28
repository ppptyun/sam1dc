package com.samil.dc.service.excel.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcExcelImportDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.domain.DcLogBean;
import com.samil.dc.service.excel.AbstractSheetProcessor;
import com.samil.dc.service.excel.DefColumn;
import com.samil.dc.service.excel.ExcelHelper;
import com.samil.dc.service.excel.ExcelProcessException;
import com.samil.dc.service.excel.bean.ImportExceptCredentialBean;
import com.samil.dc.service.worker.ServiceHelper;
import com.samil.dc.util.Constants;
import com.samil.dc.util.Constants.CredentialTargetYN;

import jxl.Cell;
import jxl.Sheet;

/**
 * 마이그레이션용_인수매각자문 Sheet 처리 클래스
 *
 */
public class ImportSheetExceptCredentialProcessor extends AbstractSheetProcessor {
	
	private List<ImportExceptCredentialBean> list = new ArrayList<ImportExceptCredentialBean>();
	private static final int COLUMN_INDEX_PROJECTCODE	= 0;
	private static final int ROW_INDEX_START			= 1;

	public ImportSheetExceptCredentialProcessor(DBConnection connection, Sheet sheet) throws ExcelProcessException {
		super(connection, sheet);
		title = "Credential 대상여부 변경";
	}

	@Override
	public AbstractSheetProcessor ready() throws ExcelProcessException {
		// 컬럼 정의
		columns.add(new DefColumn("prjtcd", "프로젝트코드(00000-00-000)", REQUIRED));
		columns.add(new DefColumn("credtgtcd", "Credential 대상여부", REQUIRED));
		return this;
	}
	
	@Override
	public AbstractSheetProcessor parse() throws ExcelProcessException {
		for (int r = ROW_INDEX_START; r < sheet.getRows(); r++) {
			int rownum = r + 1;
			// Project Code 읽기
			String prjtcd = StringUtils.defaultIfBlank(sheet.getCell(COLUMN_INDEX_PROJECTCODE, r).getContents(), "").trim();
			if (StringUtils.isBlank(prjtcd)) {
				continue;
			}
			if (!ServiceHelper.isValidProjectCode(prjtcd)) {
				pileUpErrorLog(rownum + "행 프로젝트 코드[" + prjtcd + "] 유효하지 않습니다.");
				continue;
			}
			
			// row-column 단위로 읽어서 저장하기
			ImportExceptCredentialBean project = new ImportExceptCredentialBean(r, prjtcd);
			boolean success = true;
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
						success = false;
						break;
					}
					// 데이터 타입 처리
					switch (column.getType()) {
					case ExcelHelper.COLUMN_TYPE_EMP:
						data = ServiceHelper.doLPadEmpno(data);
						break;
					case ExcelHelper.COLUMN_TYPE_DATE:
						data = ServiceHelper.stripForDateFormat(ExcelHelper.convertDateFormat(data));
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
			if (success) {
				list.add(project);
				if (logger.isDebugEnabled()) {
					logger.debug(project.toString());
				}
			}
		}
		
		return this;
	}

	@Override
	public AbstractSheetProcessor save() throws ExcelProcessException {
		
		if (failed) {
			return this;
		}
		if (list == null || list.size() == 0) {
			return this;
		}
		DcExcelImportDAO dao = new DcExcelImportDAO(connection);
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		try {
			for (ImportExceptCredentialBean project : list) {
				
				// 현재 DB에 저장된 값을 가져오기
				ImportExceptCredentialBean currData = dao.sqlSelectCredentialCREDTGTCD(project);
				
				if(currData == null){
					pileUpErrorLog(project.getPrjtcd() + " : 프로젝트가 존재하지 않습니다.");
					continue;
				}
				
				// 코드값으로 변환
				if("Yes".equals(project.getCredtgtcd())){
					project.setCredtgtcd(CredentialTargetYN.YES);
				}else if("No".equals(project.getCredtgtcd())){
					project.setCredtgtcd(CredentialTargetYN.NO);
				}else{
					pileUpErrorLog(project.getPrjtcd() + " : 대상 여부 입력 값이 유효하지 않습니다.");
					continue;
				}
				
				// 이전값과 다르다면 업데이트 진행
				if(!currData.getCredtgtcd().equals(project.getCredtgtcd())){
					dao.sqlUpdateCredentialCREDTGTCD(project);
					successCount++;
					
					// 로그 리스트 형태로 담기
					DcLogBean log = new DcLogBean(Constants.LogType.IMPORT_EXCEL);
					log.setAltcd(Constants.LogMethod.UPDATE);
					log.setFildnm("Credential 대상여부");
					log.setFildcd("credtgtcd");
					log.setBefval(currData.getCredtgtcd());
					log.setAltval(project.getCredtgtcd());
					log.setPrjtcd(project.getPrjtcd());
					log.setBigo("[" + currData.getCredtgtcd() + " -> " + project.getCredtgtcd() + "] 변경");
					log.setAltempno(empno);
					logList.add(log);
				}
			}
			
			// 로그 리스트 저장하기
			DcLogDAO logDAO = new DcLogDAO(connection);
			logDAO.sqlLogCommon(logList);
			
		} catch (Exception e) {
			throw new ExcelProcessException(Constants.ErrorMessage.FAIL_SQL_UPDATE);
		}
		
		return this;
	}
	
	@Override
	public int getReadRowCount() {
		return list.size();
	}
}
