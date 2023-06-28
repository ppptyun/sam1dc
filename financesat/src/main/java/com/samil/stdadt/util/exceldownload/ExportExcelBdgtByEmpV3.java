package com.samil.stdadt.util.exceldownload;

import java.io.FileInputStream;
import java.math.BigDecimal;
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
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.WeekVO;

public class ExportExcelBdgtByEmpV3 extends AbstractExportExcel {
	
	int firstWeekIdx = 0;
	List<WeekVO> weeks;
	
	public ExportExcelBdgtByEmpV3(Map<String, Object> option) throws Exception {
		super(option);
	}
	public ExportExcelBdgtByEmpV3(String templateFilePath, String templateFileName, Map<String, Object> option) throws Exception {
		super(templateFilePath, templateFileName, option);
	}
	
	@Override
	public void ready() throws Exception {
		weeks = (List<WeekVO>) option.get("weeks");
		sheet.setDefaultColumnWidth(7);
		
		// 컬럼 정의
		columns.add(new DefExportColumn("membKorNm", "성명", basicStyle));
		columns.add(new DefExportColumn("membEmplno", "사번", basicStyle));
		columns.add(new DefExportColumn("membTeamNm", "소속", basicStyle));
		columns.add(new DefExportColumn("membGradNm", "직급", basicStyle));
		
		columns.add(new DefExportColumn("mainPrjtNm", "Main Project Name", basicStyle));
		columns.add(new DefExportColumn("mainPrjtCd", "Main Project Code", basicStyle));
		columns.add(new DefExportColumn("el", "Main Project EL", basicStyle));
		columns.add(new DefExportColumn("pm", "Main Project PM", basicStyle));
		
		columns.add(new DefExportColumn("subPrjtNm", "Project Name", basicStyle));
		columns.add(new DefExportColumn("subPrjtCd", "Project Code", basicStyle));
		
		columns.add(new DefExportColumn("tbd", "TBD", basicStyle));
		columns.add(new DefExportColumn("locaNm", "Location", basicStyle));
		columns.add(new DefExportColumn("totAsgnTm", "Total", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		
		firstWeekIdx = columns.size();
		
		for(int i=0; i<weeks.size(); i++) {
			columns.add(new DefExportColumn("week" + (i+1), weeks.get(i).getStartDate() + "(" + weeks.get(i).getWorkDay() + "days)" , Constant.COLUMN_TYPE.NUMERIC, numberStyle));	
		}
	}

	@Override
	public Workbook generate() throws Exception {
		this.headerGenerate();
		this.dataGenerate();
		
		// 전체 컬럼 너비 조정 
		for(int i=0; i<columns.size(); i++) {
			sheet.autoSizeColumn(i + startColIdx);
			sheet.setColumnWidth(i + startColIdx, (sheet.getColumnWidth(i))+512 );
		}
		return wb;
	}
	
	private void setRegionBorderWithMedium(CellRangeAddress region, Sheet sheet) {
		RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
    }
}

