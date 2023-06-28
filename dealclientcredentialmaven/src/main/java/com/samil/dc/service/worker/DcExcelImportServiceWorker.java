package com.samil.dc.service.worker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.core.ContextLoader;
import com.samil.dc.service.excel.AbstractSheetProcessor;
import com.samil.dc.service.excel.ExcelProcessException;
import com.samil.dc.service.excel.impl.ImportSheetBuyProcessor;
import com.samil.dc.service.excel.impl.ImportSheetMnaProcessor;
import com.samil.dc.service.excel.impl.ImportSheetRealProcessor;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

import jxl.Workbook;

/**
 * 엑셀 일괄 업로드 처리
 *
 */
public class DcExcelImportServiceWorker extends AbstractServiceWorker {
	
	private static final Logger logger = Logger.getRootLogger();
	private static final String UPLOADED_FILE_NAME = "uploadedFileName";
	private static final String MESSAGE_SHEET_DIVIDER = "=================================================";

	public DcExcelImportServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}
	
	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		// required [uploadedFilePath | 업로드된 엑셀 파일명]
		validationResult = ServiceHelper.validateParameterBlank(request, UPLOADED_FILE_NAME);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}
	
	@Override
	public Object process() throws Exception {
		// 파라미터 조합
		String fileName = StringUtils.trim(ServiceHelper.getParameter(request, UPLOADED_FILE_NAME));
		String empNo = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		String baseDirPath = "";
		if (ContextLoader.getContext("ConnectToOper").equals("Y")) {
			baseDirPath = ContextLoader.getContext("Path", "FileUploadInfo");
		} else {
			baseDirPath = ContextLoader.getContext("Path", "FileUploadInfoDev");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(UPLOADED_FILE_NAME + ": " + fileName);
			logger.debug("사용자사번: " + empNo);
			logger.debug("baseDirPath: " + baseDirPath);
		}

		AbstractSheetProcessor buyProcessor = null;
		AbstractSheetProcessor mnaProcessor = null;
		AbstractSheetProcessor realProcessor = null;
		int buyRowCount = 0;
		int mnaRowCount = 0;
		int realRowCount = 0;
		int buySuccessCount = 0;
		int mnaSuccessCount = 0;
		int realSuccessCount = 0;
		List<String> buyErrorLogs = new ArrayList<String>();
		List<String> mnaErrorLogs = new ArrayList<String>();
		List<String> realErrorLogs = new ArrayList<String>();
		StringBuffer message = new StringBuffer();
		
		File excel = null;
		try {
			excel = new File(baseDirPath, fileName);
			if (excel != null && excel.exists()) {
				Workbook workbook = Workbook.getWorkbook(excel);
				
				// 처리 클래스 얻기
				buyProcessor = new ImportSheetBuyProcessor(connection, workbook.getSheet(0));
				mnaProcessor = new ImportSheetMnaProcessor(connection, workbook.getSheet(1));
				realProcessor = new ImportSheetRealProcessor(connection, workbook.getSheet(2));
				
				// 임포트 처리
				buyProcessor.setEmpno(empNo).ready().parse().save();
				mnaProcessor.setEmpno(empNo).ready().parse().save();
				realProcessor.setEmpno(empNo).ready().parse().save();
				
				// 결과 처리
				buyRowCount = buyProcessor.getReadRowCount();
				mnaRowCount = mnaProcessor.getReadRowCount();
				realRowCount = realProcessor.getReadRowCount();
				buySuccessCount = buyProcessor.getSuccessCount();
				mnaSuccessCount = mnaProcessor.getSuccessCount();
				realSuccessCount = realProcessor.getSuccessCount();
				buyErrorLogs = buyProcessor.getErrorLogList();
				mnaErrorLogs = mnaProcessor.getErrorLogList();
				realErrorLogs = realProcessor.getErrorLogList();
				if (buyProcessor != null) {
					message.append(MESSAGE_SHEET_DIVIDER).append("<br>");
					message.append("Sheet: ").append(buyProcessor.getTitle()).append("<br>");
					message.append("업로드[<b>" + buyRowCount + "</b>건] 처리[<b>" + buySuccessCount + "</b>건]").append("<br>");
					message.append(MESSAGE_SHEET_DIVIDER).append("<br>");
					message.append(StringUtils.join(buyErrorLogs, "<br>"));
					message.append("<br>");
				}
				if (mnaProcessor != null) {
					message.append("<br>");
					message.append(MESSAGE_SHEET_DIVIDER).append("<br>");
					message.append("Sheet: ").append(mnaProcessor.getTitle()).append("<br>");
					message.append("업로드[<b>" + mnaRowCount + "</b>건] 처리[<b>" + mnaSuccessCount + "</b>건]").append("<br>");
					message.append(MESSAGE_SHEET_DIVIDER).append("<br>");
					message.append(StringUtils.join(mnaErrorLogs, "<br>"));
					message.append("<br>");
				}
				if (realProcessor != null) {
					message.append("<br>");
					message.append(MESSAGE_SHEET_DIVIDER).append("<br>");
					message.append("Sheet: ").append(realProcessor.getTitle()).append("<br>");
					message.append("업로드[<b>" + realRowCount + "</b>건] 처리[<b>" + realSuccessCount + "</b>건]").append("<br>");
					message.append(MESSAGE_SHEET_DIVIDER).append("<br>");
					message.append(StringUtils.join(realErrorLogs, "<br>"));
					message.append("<br>");
				}
			
			} else {
				return new ServiceError(Constants.ErrorCode.EXCEL_PROCESS, "엑셀 파일을 찾을 수 없습니다.");
			}

		} catch (ExcelProcessException e) {
			e.printStackTrace();
			removeExcelFile(excel);
			return new ServiceError(Constants.ErrorCode.EXCEL_PROCESS, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			removeExcelFile(excel);
			return new ServiceError(Constants.ErrorCode.INTERNAL, Constants.ErrorMessage.INTERNAL);
			
		} finally {
			removeExcelFile(excel);
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", message.toString());
		return data;
	}
	
	// 파일 삭제
	private void removeExcelFile(File excel) {
		try {
			if (excel != null && excel.exists()) {
				excel.delete();
			}
		} catch (Exception e) {
			
		}
	}
}
