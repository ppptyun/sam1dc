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
import com.samil.dc.service.excel.impl.ImportSheetExceptCredentialProcessor;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

import jxl.Workbook;

public class DcExcelImportForExceptCredentialServiceWorker extends AbstractServiceWorker  {

	private static final Logger logger = Logger.getRootLogger();
	private static final String UPLOADED_FILE_NAME = "uploadedFileName";
	private static final String MESSAGE_SHEET_DIVIDER = "=================================================";
	
	public DcExcelImportForExceptCredentialServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		int rowCount = 0;
		int successCount = 0;
		StringBuffer message = new StringBuffer();
		List<String> errorLogs = new ArrayList<String>();
		
		
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

		File excel = null;
		try {
			excel = new File(baseDirPath, fileName);
			if (excel != null && excel.exists()) {
				Workbook workbook = Workbook.getWorkbook(excel);
				
				// 처리 클래스 얻기
				AbstractSheetProcessor exceptCredentialProcessor = new ImportSheetExceptCredentialProcessor(connection, workbook.getSheet(0));
				
				// 임포트 처리
				exceptCredentialProcessor.setEmpno(empNo).ready().parse().save();
				
				// 결과 처리
				rowCount 		= exceptCredentialProcessor.getReadRowCount();
				successCount 	= exceptCredentialProcessor.getSuccessCount();
				errorLogs 		= exceptCredentialProcessor.getErrorLogList();
				
				if (exceptCredentialProcessor != null) {
					message.append(MESSAGE_SHEET_DIVIDER).append("<br>");
					message.append("Sheet: ").append(exceptCredentialProcessor.getTitle()).append("<br>");
					message.append(MESSAGE_SHEET_DIVIDER).append("<br>");
					if (errorLogs != null && errorLogs.size() > 0) {
						message.append(StringUtils.join(errorLogs, "<br>"));
						message.append("<br>");
					} else {
						message.append("업로드[" + rowCount + "건] 수정처리[" + successCount + "건] 완료하였습니다.").append("<br>");
					}
				}
			}else{
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
