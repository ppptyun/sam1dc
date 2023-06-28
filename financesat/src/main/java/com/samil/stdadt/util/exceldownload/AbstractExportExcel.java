package com.samil.stdadt.util.exceldownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.samil.stdadt.util.Constant;

public abstract class AbstractExportExcel {
	protected File excel;
	protected Workbook wb;
	protected Sheet sheet;
	protected List<DefExportColumn> columns = new ArrayList<DefExportColumn>();
	protected String templatePath;
	protected Map<String, Object> option;
	
	List<Map<String, Object>> data;
	protected int startRowIdx = 0;
	protected int startColIdx = 0;
	
	protected CellStyle headerStyle, basicStyle, textCenterStyle,
	numberStyle, numberStyle1, numberStyle2, numberStyle3, numberStyle4,
	percentStyle;
	
	// 생성자
	public AbstractExportExcel() throws Exception {
		wb = new XSSFWorkbook();
		sheet = wb.createSheet("Data");		
		this.init(null);
	}
	public AbstractExportExcel(Map<String, Object> option) throws Exception {
		wb = new XSSFWorkbook();
		sheet = wb.createSheet("Data");
		this.init(option);
	}
	public AbstractExportExcel(String templateFilePath, String templateFileName) throws Exception {
		this.excel = new File(templateFilePath, templateFileName);
		if(excel != null) {
			String filePath = excel.getAbsolutePath();
			FileInputStream inputStream = new FileInputStream(filePath);
			if(excel.getName().toLowerCase().endsWith(".xlsx")) {
				wb = new XSSFWorkbook(inputStream);
			}else if(excel.getName().toLowerCase().endsWith(".xls")) {
				wb = new HSSFWorkbook(inputStream);
			}else {
				inputStream.close();
				throw new Exception("허용되지 않는 파일 타입입니다.");
			}
			sheet = wb.getSheetAt(0);
		}else {
			wb = new XSSFWorkbook();
			sheet = wb.createSheet("Data");
		}
		this.init(null);
	}
	
	public AbstractExportExcel(String templateFilePath, String templateFileName, Map<String, Object> option) throws Exception {
		this.excel = new File(templateFilePath, templateFileName);
		if(excel != null) {
			String filePath = excel.getAbsolutePath();
			FileInputStream inputStream = new FileInputStream(filePath);
			if(excel.getName().toLowerCase().endsWith(".xlsx")) {
				wb = new XSSFWorkbook(inputStream);
			}else if(excel.getName().toLowerCase().endsWith(".xls")) {
				wb = new HSSFWorkbook(inputStream);
			}else {
				inputStream.close();
				throw new Exception("허용되지 않는 파일 타입입니다.");
			}
			sheet = wb.getSheetAt(0);
		}else {
			wb = new XSSFWorkbook();
			sheet = wb.createSheet("Data");
		}
		this.init(option);
	}
	
	public abstract void ready() throws Exception;
	public abstract Workbook generate() throws Exception;
	
	
	private void init(Map<String, Object> option) throws Exception{
		this.option = option;
		data = (List<Map<String, Object>>) option.get("data");
		startRowIdx = option.get("startRowIdx") == null?0:(Integer) option.get("startRowIdx");
		startColIdx = option.get("startColIdx") == null?0:(Integer) option.get("startColIdx");
		
		// 눈금선 없애기
		sheet.setDisplayGridlines(false);
		
		// Header Style
		Font headerFont = wb.createFont();
		headerFont.setBold(true);
		headerFont.setFontName("맑은 고딕");
		headerFont.setFontHeightInPoints((short)9);
		headerStyle = createDefaultCellStyle(headerFont);
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setFont(headerFont);
		
		// Data Style
		Font dataFont = wb.createFont();
		dataFont.setFontHeightInPoints((short)9);
		dataFont.setFontName("맑은 고딕");
		basicStyle 		= createDefaultCellStyle(dataFont);
		textCenterStyle	= createDefaultCellStyle(dataFont);
		textCenterStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle 	= createDefaultCellStyle(dataFont, "#,##0");
		numberStyle1 	= createDefaultCellStyle(dataFont, "#,##0.0");
		numberStyle2 	= createDefaultCellStyle(dataFont, "#,##0.00");
		numberStyle3 	= createDefaultCellStyle(dataFont, "#,##0.000");
		numberStyle4 	= createDefaultCellStyle(dataFont, "#,##0.0000");
		percentStyle 	= createDefaultCellStyle(dataFont, "#,##0%");
	}
	
	// 헤더 그리기
	protected void headerGenerate() {
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		Font headerFont = wb.createFont();
		headerFont.setBold(true);
		headerFont.setFontName("맑은 고딕");
		headerStyle.setFont(headerFont);
		
		byte[] byteColor = new byte[]{(byte)234,(byte)234,(byte)234};
		XSSFColor color = new XSSFColor(byteColor, null);
		headerStyle.setFillBackgroundColor(color.getIndex());
		
		// 헤더 출력
		int rowCnt = columns.get(0).getHeaderRowCount();
		
		for(int i=0; i<rowCnt; i++) {
			Row row = sheet.getRow(i + startRowIdx);
			if(row == null) row = sheet.createRow(i + startRowIdx);
			
			for(int j=0; j<columns.size(); j++) {
				String text = columns.get(j).getTitle(i);
				Cell cell = row.getCell(j+startColIdx);
				if(cell == null) cell = row.createCell(j+startColIdx);
				
				if("#cspan".equals(text) || "#rspan".equals(text)) {
					cell.setCellStyle(headerStyle);
				}else {
					cell.setCellType(CellType.STRING);
					cell.setCellStyle(headerStyle);
					cell.setCellValue(text);
				}
			}
		}
		
		
		for(int i=0; i<columns.size(); i++) {
			for(int j=0; j<columns.get(0).getHeaderRowCount(); j++) {
				CellRangeAddress region = getMergeCellRangeAddress(i, j);
				if(region != null) {
					sheet.addMergedRegion(region);
				}
			}
		}
		
		startRowIdx += rowCnt;
	}
	
	// 데이터 그리기
	protected void dataGenerate() {
		for(int i=0; i<columns.size(); i++) {
			int colIdx = i + startColIdx;
			int colType = columns.get(i).getType();
			
			for(int j=0; j<this.data.size(); j++) {
				int rowIdx = j + startRowIdx;
				Object plainData = data.get(j).get(columns.get(i).getCode());
				
				Row row = sheet.getRow(rowIdx)==null?sheet.createRow(rowIdx):sheet.getRow(rowIdx);
				
				Cell cell = row.getCell(colIdx)==null?row.createCell(colIdx):row.getCell(colIdx);
				
				if(colType == Constant.COLUMN_TYPE.NUMERIC) {
					cell.setCellType(CellType.NUMERIC);
					if(plainData != null) cell.setCellValue(((BigDecimal) plainData).doubleValue());
				}else {
					cell.setCellType(CellType.STRING);
					cell.setCellValue((String) plainData);
				}
				
				cell.setCellStyle(columns.get(i).getStyle());
			}
		}
	}
	
	private CellStyle createDefaultCellStyle(Font font) {
		CellStyle defaultStyle = createDefaultCellStyle();
		defaultStyle.setFont(font);
		return defaultStyle;
	}
	private CellStyle createDefaultCellStyle(Font font, String formatString) {
		DataFormat format = wb.createDataFormat();
		CellStyle defaultStyle = createDefaultCellStyle();
		defaultStyle.setFont(font);
		defaultStyle.setDataFormat(format.getFormat(formatString));
		return defaultStyle;
	}
	private CellStyle createDefaultCellStyle() {
		CellStyle defaultStyle = wb.createCellStyle();
		defaultStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		defaultStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		defaultStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		defaultStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		defaultStyle.setBorderTop(BorderStyle.THIN);
		defaultStyle.setBorderBottom(BorderStyle.THIN);
		defaultStyle.setBorderLeft(BorderStyle.THIN);
		defaultStyle.setBorderRight(BorderStyle.THIN);
		return defaultStyle;
	}
	
	private CellRangeAddress getMergeCellRangeAddress(int col, int row) {
		int sRow=row, eRow=row, sCol=col, eCol=col;
		String text = columns.get(col).getTitle(row);
		
		if("#cspan".equals(text)){
			for(int i=(row+1); i<columns.get(col).getHeaderRowCount(); i++) {
				if("#rspan".equals(columns.get(col).getTitle(i))) {
					eRow++;
				}else {
					break;
				}
			}
		}else if("#rspan".equals(text)){
			for(int i=(col+1); i<columns.size(); i++) {
				if("#cspan".equals(columns.get(i).getTitle(row))) {
					eCol++;
				}else {
					break;
				}
			}
		}else {
			for(int i=(row+1); i<columns.get(col).getHeaderRowCount(); i++) {
				if("#rspan".equals(columns.get(col).getTitle(i))) {
					eRow++;
				}else {
					break;
				}
			}
			for(int i=(col+1); i<columns.size(); i++) {
				if("#cspan".equals(columns.get(i).getTitle(row))) {
					eCol++;
				}else {
					break;
				}
			}
		}
		
		if(sCol == eCol && sRow==eRow) {
			return null;
		}else {			
			return new CellRangeAddress(sRow+startRowIdx, eRow+startRowIdx, sCol+startColIdx, eCol+startColIdx);
		}
	}
	
	protected int getColIndex(String key) {
		for(int i=0; i<columns.size(); i++) {
			if(columns.get(i).getCode().equals(key)) {
				return i;
			}
		}
		return -1;
	}
}
