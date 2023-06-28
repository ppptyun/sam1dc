package com.samil.stdadt.util.exceldownload;

import java.math.BigDecimal;
import java.util.Arrays;
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

public class ExportExcelSatPrjtInfo extends AbstractExportExcel {
	
	public ExportExcelSatPrjtInfo(Map<String, Object> option) throws Exception {
		super(option);
	}
	
	@Override
	public void ready() throws Exception {
		// 컬럼 정의
		
		// 프로젝트 정보
		columns.add(new DefExportColumn("prjtnm", "Project Name", basicStyle));
		columns.add(new DefExportColumn("prjtcd", "Project Code", basicStyle));
		columns.add(new DefExportColumn("chargptrNm", "EL 이름", basicStyle));
		columns.add(new DefExportColumn("chargptr", "EL 사번", basicStyle));
		columns.add(new DefExportColumn("chargmgrNm", "PM 이름", basicStyle));
		columns.add(new DefExportColumn("chargmgr", "PM 사번", basicStyle));
		columns.add(new DefExportColumn("prjtFrdt", "Project 시작일", basicStyle));
		columns.add(new DefExportColumn("prjtTodt", "Project 종료일", basicStyle));
		columns.add(new DefExportColumn("desigAdtYnNm", "지정감사 여부", basicStyle));
		columns.add(new DefExportColumn("satTrgtYnNm", "표준감사시간 대상 여부 ", basicStyle));
		columns.add(new DefExportColumn("cntrtFee", "계약보수", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		
		// 합산대상 프로젝트(최대 6개까지 입력 가능함.)
		for(int i=1 ; i<=6; i++) {
			columns.add(new DefExportColumn("s" + i + "Prjtnm", 	"합산" + i + " - Prject Name", basicStyle));
			columns.add(new DefExportColumn("s" + i + "Prjtcd", 	"합산" + i + " - Prject Code", basicStyle));
			columns.add(new DefExportColumn("s" + i + "Chargptr", 	"합산" + i + " - EL 사번", basicStyle));
			columns.add(new DefExportColumn("s" + i + "ChargptrNm", "합산" + i + " - EL 이름", basicStyle));
			columns.add(new DefExportColumn("s" + i + "Chargmgr", 	"합산" + i + " - PM 사번", basicStyle));
			columns.add(new DefExportColumn("s" + i + "ChargmgrNm", "합산" + i + " - PM 이름", basicStyle));
			columns.add(new DefExportColumn("s" + i + "CntrtFee", 	"합산" + i + " - 보수", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		}
		
		// 총보수
		columns.add(new DefExportColumn("totCntrtFee", "총 보수", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		
		// 표준감사시간
		columns.add(new DefExportColumn("indusNm", "표준산업 분류", basicStyle));
		columns.add(new DefExportColumn("listDvNm", "상장 구분", basicStyle));
		columns.add(new DefExportColumn("indivAsset", "개별 자산", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("consoSales", "연결 매출", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("consoAsset", "연결 자산", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("compSize", "기업 규모", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("bizRprtYnNm", "사업보고서 제출 대상", basicStyle));
		columns.add(new DefExportColumn("satgrp1ExptYnNm", "그룹1의 예외사항 여부", basicStyle));
		columns.add(new DefExportColumn("priorAdtTm", "전기 감사 시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("satgrpNm", "표준 감사 시간 그룹 정보", basicStyle));
		columns.add(new DefExportColumn("usaListYnNm", "미국 상장 여부", basicStyle));
		columns.add(new DefExportColumn("holdingsDivNm", "지주사 분류", basicStyle));
		columns.add(new DefExportColumn("consoFinstatYnNm", "연결 작성 여부", basicStyle));
		columns.add(new DefExportColumn("sbsidCnt", "자회사 수", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("rprtScdlDt", "보고서 발행 예정일", basicStyle));
		columns.add(new DefExportColumn("firstAdtYnNm", "초도 감사 여부", basicStyle));
		columns.add(new DefExportColumn("firstAdtFctr", "초도 감사 가산", Constant.COLUMN_TYPE.NUMERIC, basicStyle));
		columns.add(new DefExportColumn("ifrsYnNm", "IFRS 여부", basicStyle));
		columns.add(new DefExportColumn("consoAccntReceiv", "연결 매출채권", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("consoInvnt", "연결 재고자산", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("currConsoLossYnNm", "당기 연결 당기순손실 예상 여부", basicStyle));
		columns.add(new DefExportColumn("currAdtopinNm", "당기 예상 감사의견  비적정 여부", basicStyle));
		columns.add(new DefExportColumn("intrTranAssetSales", "연결 조정시 제거된 내부거래(매출+자산)", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("relatCompAsset", "관계회사의 자산금액", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("sbsidAssetWithIntrTran", "내부거래전 자회사 자산", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("sbsidSalesWithIntrTran", "내부거래전 자회사 매출", Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		
		columns.add(new DefExportColumn("factorVal", "가감률", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("calAdtTm", "산출된 감사시간(a)", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("intrAdtYnNm", "내부회계감사(2조 이상) 여부", basicStyle));
		columns.add(new DefExportColumn("intrAdtTm", "내부 감사시간(b)", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("calSat", "숙련도 반영전 표준감사시간(a+b)", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("etDfnSat", "한공회 표준감사시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("baseWkmnsp", "기준 숙련도", Constant.COLUMN_TYPE.NUMERIC, numberStyle3));
		columns.add(new DefExportColumn("etTrgtAdtTm", "감사계약시 합의 시간 (표준감사시간 대상)", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		
		columns.add(new DefExportColumn("etcBdgtTm", "표준감사시간외 감사등 예산 시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		
		
		/*columns.add(new DefExportColumn("totPrjtBdgt", "감사계약시 합의 시간", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("raBdgtTm", "RA 배부 예산", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("flcmBdgtTm", "Fulcrum 배부 예산", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("remainBdgt", "잔여 예산", Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		*/
	}

	@Override
	public Workbook generate() throws Exception {
		this.headerGenerate();
		this.dataGenerate();
		
		// 전체 컬럼 너비 조정 
		for(int i=0; i<columns.size(); i++) {
			sheet.autoSizeColumn(i + startColIdx);
			sheet.setColumnWidth(i + startColIdx, (sheet.getColumnWidth(i)) + 100 );
		}
		return wb;
	}
}

