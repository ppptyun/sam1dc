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

public class ExportExcelSatBdgtInfo extends AbstractExportExcel {
	
	public ExportExcelSatBdgtInfo(Map<String, Object> option) throws Exception {
		super(option);
	}
	
	@Override
	public void ready() throws Exception {
		// 컬럼 정의
		columns.add(new DefExportColumn("prjtnm", "Project Name", basicStyle));
		columns.add(new DefExportColumn("prjtcd", "Project Code", basicStyle));
		columns.add(new DefExportColumn("satTrgtYnNm", "표준감사시간 대상 여부", basicStyle));
		columns.add(new DefExportColumn("el", "EL", basicStyle));
		columns.add(new DefExportColumn("pm", "PM", basicStyle));
		columns.add(new DefExportColumn("team", "소속", basicStyle));
		columns.add(new DefExportColumn("cntrtFee", "계약 금액", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("etTrgtAdtTm", "감사계약시 합의 시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("totBdgt", "Total Budget Hour", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("etDfnSat", "한공회표준감사시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("baseWkmnsp", "기준 숙련도", Constant.COLUMN_TYPE.NUMERIC, numberStyle3));
		columns.add(new DefExportColumn("expTeamWkmnsp", "예상 팀 숙련도", Constant.COLUMN_TYPE.NUMERIC, numberStyle3));
		columns.add(new DefExportColumn("planSat", "계획단계 표준감사시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("expCm", "예상 CM(백만원)", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("elBdgtTm", "EL 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("pTotAsgnTm", "Partner 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("dTotAsgnTm", "Director 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("smTotAsgnTm", "Senior Manager 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("mTotAsgnTm", "Manager 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("saTotAsgnTm", "Senior Associate 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("aTotAsgnTm", "Associate 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("newStfBdgtTm", "New Staff 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("otherBdgtTm", "Other 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("raBdgtTm", "RA 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("flcmBdgtTm", "Fulcrum 투입시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
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

