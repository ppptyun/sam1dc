package com.samil.stdadt.util.excelparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.util.Constant;

public abstract class AbstractExcelParser{
	private boolean isError = false;
	
	protected Sheet sheet;
	protected List<DefColumn> columns = new ArrayList<DefColumn>();
	protected Map<String, Object> data;
	protected List<String> errLog = new ArrayList<String>();
	protected Map<String, List<CommCodeVO>> codes;
		 	
	public AbstractExcelParser(Sheet sheet) throws Exception {this.sheet = sheet;}
	public AbstractExcelParser(Sheet sheet, Map<String, List<CommCodeVO>> codes) throws Exception {this.sheet = sheet; this.codes = codes;}
	// 컬럼 정의
	public abstract AbstractExcelParser ready() throws Exception;
	
	// 파싱된 Object 리턴
	public abstract AbstractExcelParser parse() throws Exception;
	
	public Map<String, Object> getData() {
		return data;
	}
	
	public boolean isError() {
		return isError;
	}

	public List<String> getErrLog() {
		return errLog;
	}
	
	protected void addErrorLog(String errStr) {
		this.isError = true;
		errLog.add(errStr);
	}
	
	protected void addErrorLog(int row, String errStr) {
		this.addErrorLog("[" + (row + 1) + "줄] " + errStr);
	}

	protected void addErrorLog(int row, String columnName, String errStr) {
		this.addErrorLog("[" + (row + 1) + "줄][" + columnName + "] " + errStr);
	}
	
	protected List<DefColumn> getColumns() {
		return columns;
	}

	protected CommCodeVO getCodeByName(String grpCd, String codeNm) throws Exception{
		for(CommCodeVO code : codes.get(grpCd)) {
			if(code.getNm().equals(codeNm)) return code;
		}
		
		return null;
	}

	protected Object getValue(int colIdx, Row rowObj) throws Exception{
		try {
			Cell cell = rowObj.getCell(colIdx);
			
			if(cell == null) {return null;}
			
			int type = columns.get(colIdx).getType();
			
			if(type == Constant.COLUMN_TYPE.NUMERIC) {
				try {
					return cell.getNumericCellValue();
				}catch(Exception e) {
					throw new Exception("숫자를 입력하세요");
				}
			}else if(type == Constant.COLUMN_TYPE.CODE_NAME) {
				Object value = null;
				try {
					value =  StringUtils.defaultIfBlank(cell.getStringCellValue(), "").trim();
					if(value.equals("")) return value;
				}catch(Exception e) {
					throw new Exception("문자를 입력하세요.");
				}
				CommCodeVO code = this.getCodeByName(columns.get(colIdx).getGrpCode(), (String) value);
				if(code == null) {
					throw new Exception("등록되지 않은 코드입니다.");
				}else {
					return code.getCd();
				}
			}else {
				String retVal;
				
				if(cell.getCellType() == CellType.NUMERIC) {
					retVal = cell.toString().replace(".0", "").trim();
				}else {
					retVal = cell.toString().trim();
				}
				
				if(retVal.equals("")) {
					return retVal;
				}else {				
					if(columns.get(colIdx).getFormat() == null) {
						return retVal;
					}else {
						return String.format(columns.get(colIdx).getFormat(), Integer.parseInt(retVal));
					}
				}
				
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	protected int getIndexById(String id) {
		for(int i=0; i<columns.size(); i++) {
			if(columns.get(i).getCode().equals(id)) return i;
		}
		return -1;
	}

}
