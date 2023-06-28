package com.samil.stdadt.util.exceldownload;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.samil.stdadt.util.Constant;

public class ExportExcelSatBdgtInfoV3 extends AbstractExportExcel {
	
	public ExportExcelSatBdgtInfoV3(Map<String, Object> option) throws Exception {
		super(option);
	}
	
	@Override
	public void ready() throws Exception {
		// 컬럼 정의
		columns.add(new DefExportColumn("prjtnm", "Project Name", basicStyle));
		columns.add(new DefExportColumn("prjtcd", "Project Code", basicStyle));
		columns.add(new DefExportColumn("chargptrNm", "EL", basicStyle));
		columns.add(new DefExportColumn("chargmgrNm", "PM", basicStyle));
		columns.add(new DefExportColumn("prjtFrdt", "프로젝트 시작일", basicStyle));
		columns.add(new DefExportColumn("prjtTodt", "프로젝트 시작일", basicStyle));
		columns.add(new DefExportColumn("bizTodt", "결산 년월", basicStyle));
		columns.add(new DefExportColumn("rprtScdlDt", "예상 보고서일", basicStyle));

		columns.add(new DefExportColumn("elTm", "담당이사", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("pmTm", "PM", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("teamTm", "감사팀", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("newstaffTm", "New Staff(TBD)", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("qrpTm", "QRP", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("qcTm", "QC-(RM, ACS, M&T 등)", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("raTm", "SPA", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("fulcrumTm", "Fulcrum", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("totalTm", "Budget Total", Constant.COLUMN_TYPE.NUMERIC, numberStyle));

		if(this.option.get("satTrgtYn").equals("Y")) {			
			columns.add(new DefExportColumn("satTrgtYn", "표감대상여부", basicStyle));
			columns.add(new DefExportColumn("satgrpNm", "그룹", basicStyle));
			columns.add(new DefExportColumn("etDfnSat", "한공회표준감사시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
			columns.add(new DefExportColumn("baseWkmnsp", "기준 숙련도", Constant.COLUMN_TYPE.NUMERIC, numberStyle3));
			columns.add(new DefExportColumn("satExpWkmnsp", "예상 팀 숙련도", Constant.COLUMN_TYPE.NUMERIC, numberStyle3));
			columns.add(new DefExportColumn("satPlan", "계획단계표준감사시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
			columns.add(new DefExportColumn("satCntrtAdtTm", "감사계약시합의시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
			columns.add(new DefExportColumn("satBdgtTm", "Budget Total(외감)", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
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
}

