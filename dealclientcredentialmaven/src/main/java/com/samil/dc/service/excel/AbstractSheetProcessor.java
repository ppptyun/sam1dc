package com.samil.dc.service.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;

import jxl.Sheet;

public abstract class AbstractSheetProcessor {

	protected static final Logger logger = Logger.getRootLogger();
	protected DBConnection connection;
	protected Sheet sheet;
	protected String empno = "";
	protected String title = "";
	protected List<DefColumn> columns = new ArrayList<DefColumn>();
	protected List<String> errorLogList = new ArrayList<String>();
	protected boolean failed = false;
	protected int successCount = 0;
	protected final static boolean REQUIRED = true;
	protected final static String TERMINATED = "종료";
	protected final static String MANUALLY = "-";

	public AbstractSheetProcessor(DBConnection connection, Sheet sheet) throws ExcelProcessException {
		if (sheet == null) {
			throw new ExcelProcessException("처리할 워크시트가 없습니다.");
		}
		this.connection = connection;
		this.sheet = sheet;
	}

	// 1. 파싱 준비 절차[컬럼 정의 만들기]
	public abstract AbstractSheetProcessor ready() throws ExcelProcessException;
	
	// 2. 엑셀 데이터 읽어 들이기
	public abstract AbstractSheetProcessor parse() throws ExcelProcessException;
	
	// 3. 조합된 데이터를 데이터베이스에 저장하기
	public abstract AbstractSheetProcessor save() throws ExcelProcessException;
	
	// 읽어들인 데이터 로우 갯수
	public abstract int getReadRowCount();
	
	public String getEmpno() {
		return empno;
	}

	public AbstractSheetProcessor setEmpno(String empno) {
		this.empno = empno;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public AbstractSheetProcessor setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public int getSuccessCount() {
		return this.successCount;
	}
	
	public List<String> getErrorLogList() {
		return this.errorLogList;
	}
	
	// 오류 항목 쌓아두기
	protected void pileUpErrorLog(String log) {
		failed = true;
		errorLogList.add(log);
	}
}
