package com.samil.stdadt.util.exceldownload;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.samil.stdadt.util.Constant;

public class ExportExcelSatPrjtInfoV2 extends AbstractExportExcel {
	
	public ExportExcelSatPrjtInfoV2(Map<String, Object> option) throws Exception {
		super(option);
	}
	
	@Override
	public void ready() throws Exception {
		
		//======================
		// 프로젝트 정보
		//======================
		columns.add(new DefExportColumn("prjtnm", Arrays.asList("프로젝트 정보", "Project Name", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("prjtcd", Arrays.asList("#cspan", "Project Code", "#rspan"), textCenterStyle));
		columns.add(new DefExportColumn("chargptrNm", Arrays.asList("#cspan", "EL 이름", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("chargptr", Arrays.asList("#cspan", "EL 사번", "#rspan"), textCenterStyle));
		columns.add(new DefExportColumn("chargmgrNm", Arrays.asList("#cspan", "PM 이름", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("chargmgr", Arrays.asList("#cspan", "PM 사번", "#rspan"), textCenterStyle));
		columns.add(new DefExportColumn("prjtFrdt", Arrays.asList("#cspan", "Project 시작일", "#rspan"), textCenterStyle));
		columns.add(new DefExportColumn("prjtTodt", Arrays.asList("#cspan", "Project 종료일", "#rspan"), textCenterStyle));
		columns.add(new DefExportColumn("desigAdtYnNm", Arrays.asList("#cspan", "지정감사 여부", "#rspan"), textCenterStyle));
		columns.add(new DefExportColumn("satTrgtYnNm", Arrays.asList("#cspan", "표준감사시간 대상 여부 ", "#rspan"), textCenterStyle));
		columns.add(new DefExportColumn("bizFrdt", Arrays.asList("#cspan", "사업연도 시작", "#rspan"), textCenterStyle));
		columns.add(new DefExportColumn("bizTodt", Arrays.asList("#cspan", "사업연도 종료", "#rspan"), textCenterStyle));
		columns.add(new DefExportColumn("cntrtFee", Arrays.asList("#cspan", "계약보수", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		for(int i=1 ; i<=6; i++) { // 합산대상 프로젝트(최대 6개까지 입력 가능함.)
			columns.add(new DefExportColumn("s" + i + "Prjtnm", 	Arrays.asList("#cspan", "합산 대상 프로젝트 " + i, "Prject Name"), basicStyle));
			columns.add(new DefExportColumn("s" + i + "Prjtcd", 	Arrays.asList("#cspan", "#cspan", "Prject Code"), textCenterStyle));
			columns.add(new DefExportColumn("s" + i + "Chargptr", 	Arrays.asList("#cspan", "#cspan", "EL 사번"), textCenterStyle));
			columns.add(new DefExportColumn("s" + i + "ChargptrNm", Arrays.asList("#cspan", "#cspan", "EL 이름"), basicStyle));
			columns.add(new DefExportColumn("s" + i + "Chargmgr", 	Arrays.asList("#cspan", "#cspan", "PM 사번"), textCenterStyle));
			columns.add(new DefExportColumn("s" + i + "ChargmgrNm", Arrays.asList("#cspan", "#cspan", "PM 이름"), basicStyle));
			columns.add(new DefExportColumn("s" + i + "CntrtFee", 	Arrays.asList("#cspan", "#cspan", "보수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		}
		columns.add(new DefExportColumn("totCntrtFee", Arrays.asList("#cspan", "총 보수", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		
		//======================
		// 그룹 정보
		//======================
		columns.add(new DefExportColumn("indusNm", Arrays.asList("그룹 정보", "표준산업 분류", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("listDvNm", Arrays.asList("#cspan", "상장 구분", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("indivAsset", Arrays.asList("#cspan", "개별 자산", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("bizRprtYnNm", Arrays.asList("#cspan", "사업보고서 제출 대상", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("consoAsset", Arrays.asList("#cspan", "연결 자산", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("satgrp1ExptYnNm", Arrays.asList("#cspan", "그룹1의 예외사항 여부", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("consoSales", Arrays.asList("#cspan", "연결 매출", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("compSize", Arrays.asList("#cspan", "기업 규모", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("consoAccntReceiv", Arrays.asList("#cspan", "연결 매출채권", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("rprtScdlDt", Arrays.asList("#cspan", "보고서 발행 예정일", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("consoInvnt", Arrays.asList("#cspan", "연결 재고자산", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("satgrpNm", Arrays.asList("#cspan", "표준 감사 시간 그룹 정보", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("firstBizDt", Arrays.asList("#cspan", "최초적용 사업연도", "#rspan"), textCenterStyle));
		columns.add(new DefExportColumn("contiAdtCntNm", Arrays.asList("#cspan", "삼일회계법인 연속감사 횟수", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("intrAdtYcntNm", Arrays.asList("#cspan", "내부회계감사 시행연차", "#rspan"), basicStyle));
		columns.add(new DefExportColumn("revwCnt", Arrays.asList("#cspan", "검토 횟수", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("intrTranAssetSales", Arrays.asList("#cspan", "연결 조정시 제거된 내부거래(매출+자산)", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("sbsidAssetWithIntrTran", Arrays.asList("#cspan", "내부거래전 자회사 자산", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("relatCompAsset", Arrays.asList("#cspan", "관계회사의 자산금액", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("sbsidSalesWithIntrTran", Arrays.asList("#cspan", "내부거래전 자회사 매출", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));

		//======================
		// 가감계수
		//======================
		columns.add(new DefExportColumn("ifrsYnNm", Arrays.asList("가감계수", "IFRS 여부", "O/X"), textCenterStyle));
		columns.add(new DefExportColumn("ifrsFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("holdingsDivNm", Arrays.asList("#cspan", "지주사 분류", "금융/비금융/일반"), basicStyle));
		columns.add(new DefExportColumn("holdingsFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("firstAdtYnNm", Arrays.asList("#cspan", "초도 감사 여부", "O/X"), textCenterStyle));
		columns.add(new DefExportColumn("firstAdtFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("usaListYnNm", Arrays.asList("#cspan", "미국 상장 여부", "O/X"), textCenterStyle));
		columns.add(new DefExportColumn("usaListFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("riskBase", Arrays.asList("#cspan", "위험계정비중", ""), Constant.COLUMN_TYPE.NUMERIC, numberStyle4));
		columns.add(new DefExportColumn("riskFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("kamYnNm", Arrays.asList("#cspan", "KAM", "O/X"), textCenterStyle));
		columns.add(new DefExportColumn("kamFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("consoFinstatYnNm", Arrays.asList("#cspan", "연결 작성 여부", "O/X"), textCenterStyle));
		columns.add(new DefExportColumn("consoFinstatFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("sbsidCnt", Arrays.asList("#cspan", "자회사 수", "갯수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("sbsidCntFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("currAdtopinNm", Arrays.asList("#cspan", "당기 예상 감사의견  비적정 여부", "O/알수없음"), textCenterStyle));
		columns.add(new DefExportColumn("currAdtopinFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("currConsoLossYnNm", Arrays.asList("#cspan", "당기 연결 당기순손실 예상 여부", "O/알수없음"), textCenterStyle));
		columns.add(new DefExportColumn("currConsoLossFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("priorAdtopinChgYnNm", Arrays.asList("#cspan", "전기 의견변형 여부", "O/알수없음"), textCenterStyle));
		columns.add(new DefExportColumn("priorAdtopinChgFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("priorLossYnNm", Arrays.asList("#cspan", "전기손실여부 여부", "O/알수없음"), textCenterStyle));
		columns.add(new DefExportColumn("priorLossFctr", Arrays.asList("#cspan", "#cspan", "계수"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("etcFctr", Arrays.asList("#cspan", "분기검토조정계수 등 기타 계수", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("factorVal", Arrays.asList("#cspan", "가감요인계", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		
		//======================
		// 상하한(기준숙련도 기준)
		//======================
		columns.add(new DefExportColumn("minMaxYnNm", Arrays.asList("상하한(기준숙련도 기준)", "상하한 적용 여부", "O/X"), textCenterStyle));
		columns.add(new DefExportColumn("fstBizDt", Arrays.asList("#cspan", "최초 적용 상한", "최초 직전 사업연도"), textCenterStyle));
		columns.add(new DefExportColumn("fstMaxTm", Arrays.asList("#cspan", "#cspan", "최초 3년 상한"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("fstAdtTm", Arrays.asList("#cspan", "#cspan", "최초 직전 감사 시간"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("fstAdtTmReasonNm", Arrays.asList("#cspan", "#cspan", "감사 시간 근거"), basicStyle));
		columns.add(new DefExportColumn("fstAdtTmReasonDscrt", Arrays.asList("#cspan", "#cspan", "감사 시간 근거 - 직접 서술"), basicStyle));
		columns.add(new DefExportColumn("fstAdtrWkmnsp", Arrays.asList("#cspan", "#cspan", "최초 직전 감사팀 숙련도"), Constant.COLUMN_TYPE.NUMERIC, numberStyle3));
		columns.add(new DefExportColumn("fstWkmnspReasonNm", Arrays.asList("#cspan", "#cspan", "감사팀 숙련도 근거"), basicStyle));
		columns.add(new DefExportColumn("fstWkmnspReasonDscrt", Arrays.asList("#cspan", "#cspan", "감사팀 숙련도 근거 - 직접 서술"), basicStyle));
		columns.add(new DefExportColumn("priorAdtrNm", Arrays.asList("#cspan", "전기기준 상하한", "전기 감사인"), basicStyle));
		columns.add(new DefExportColumn("priorBaseWkmnsp", Arrays.asList("#cspan", "#cspan", "전기 그룹 기준 숙련도"), Constant.COLUMN_TYPE.NUMERIC, numberStyle3));
		columns.add(new DefExportColumn("priorAdtTm", Arrays.asList("#cspan", "#cspan", "전기 감사 시간(내부회계감사시간 포함)"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("priorAdtTmReasonNm", Arrays.asList("#cspan", "#cspan", "전기 감사 시간 근거"), basicStyle));
		columns.add(new DefExportColumn("priorAdtTmReasonDscrt", Arrays.asList("#cspan", "#cspan", "전기 감사 시간 근거 - 직접 서술"), basicStyle));
		columns.add(new DefExportColumn("priorAdtrWkmnsp", Arrays.asList("#cspan", "#cspan", "전기 감사팀 숙련도"), Constant.COLUMN_TYPE.NUMERIC, numberStyle3));
		columns.add(new DefExportColumn("priorWkmnspReasonNm", Arrays.asList("#cspan", "#cspan", "전기 감사팀 숙련도 근거"), basicStyle));
		columns.add(new DefExportColumn("priorWkmnspReasonDscrt", Arrays.asList("#cspan", "#cspan", "전기 감사팀 숙련도 근거 - 직접 서술"), basicStyle));
		columns.add(new DefExportColumn("intrAdtYnNm", Arrays.asList("#cspan", "내부 회계 감사", "전기 내부회계감사시간 포함 여부"), textCenterStyle));
		columns.add(new DefExportColumn("intrAdtTm", Arrays.asList("#cspan", "#cspan", "전기 내부회계감사시간"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("minTm", Arrays.asList("#cspan", "상하한", "상한"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("maxTm", Arrays.asList("#cspan", "#cspan", "하한"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("minMaxReasonNm", Arrays.asList("#cspan", "상하한 미적용시 근거", "선택"), basicStyle));
		columns.add(new DefExportColumn("minMaxReasonDscrt", Arrays.asList("#cspan", "#cspan", "직접서술"), basicStyle));
		
		//======================
		// 표준감사시간 산출
		//======================
		columns.add(new DefExportColumn("intplSat", Arrays.asList("표준감사시간 산출", "표준감사시간표(보간법)", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("factorVal", Arrays.asList("#cspan", "가감률", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("calResult", Arrays.asList("#cspan", "산식결과", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("yearRate", Arrays.asList("#cspan", "적용요율(%)", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, percentStyle));		
		columns.add(new DefExportColumn("calRateSat", Arrays.asList("#cspan", "산식결과 * 적용요율", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle));
		columns.add(new DefExportColumn("calAdtTm", Arrays.asList("#cspan", "산출된 감사시간(상하한 적용 후)(a)", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("intrAdtFctr", Arrays.asList("#cspan", "내부회계감사시간 계수(b)", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle3));
		columns.add(new DefExportColumn("calSat", Arrays.asList("#cspan", "숙련도 반영전 표준감사시간(a*b)", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("baseWkmnsp", Arrays.asList("#cspan", "기준 숙련도", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle3));
		columns.add(new DefExportColumn("etDfnSat", Arrays.asList("#cspan", "한공회 표준감사시간(기본산식 외 요소를 고려한 최종 표준감사시간)", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
		columns.add(new DefExportColumn("etTrgtAdtTm", Arrays.asList("#cspan", "감사계약시 합의 시간 (표준감사시간 대상)", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));

		//======================
		// 표준감사시간외 예산입력(작성된 시간은 계획단계 표준감사시간 계산시에 포함되지 않습니다)
		//======================
		columns.add(new DefExportColumn("etcBdgtTm", Arrays.asList("표준감사시간외 감사등 예산 시간", "#rspan", "#rspan"), Constant.COLUMN_TYPE.NUMERIC, numberStyle2));
	}

	@Override
	public Workbook generate() throws Exception {
		// 헤더 출력
		this.headerGenerate();
		
		// 데이터 출력
		this.dataGenerate();
		
		// 전체 컬럼 자동 너비 조정 
		for(int i=0; i<columns.size(); i++) {
			sheet.autoSizeColumn(i + startColIdx, true);
		}
		
		// 틀고정(column index, row index)
		sheet.createFreezePane(1, 3);
		return wb;
	}
}

