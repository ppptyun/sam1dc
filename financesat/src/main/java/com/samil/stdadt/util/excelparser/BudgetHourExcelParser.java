package com.samil.stdadt.util.excelparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.WeekVO;


public class BudgetHourExcelParser extends AbstractExcelParser{
	
	private final int titleRow = 1;	// Heaer Text 시작 Row Index
	private final int startRow = 4;	// Data 시작 Row Index;
	private final int maxWeekNum = 61;
	
	private List<WeekVO> weeks;
	
	Validator validator = new Validator();
	
	public BudgetHourExcelParser(Map<String, Object> param, Sheet sheet, Map<String, List<CommCodeVO>> codes, List<WeekVO> weeks) throws Exception {
		super(sheet, codes);
		this.weeks = weeks;
		this.data = new HashMap<String, Object>(param);
	}

	@Override
	public AbstractExcelParser ready() throws Exception{
		columns.add(new DefColumn("tbd", new ValidatorInterface[] {validator.isNotNull}));
		columns.add(new DefColumn("actvCd", Constant.COLUMN_TYPE.CODE_NAME, "ACTV", new ValidatorInterface[] {validator.isNotNull}));
		columns.add(new DefColumn("locaCd", Constant.COLUMN_TYPE.CODE_NAME, "LOCA", new ValidatorInterface[] {validator.isNotNull}));
		columns.add(new DefColumn("Name"));
		columns.add((new DefColumn("membEmplNo", new ValidatorInterface[] {validator.isNotNull, validator.isEmpNo})).setFormat("%06d"));
		columns.add(new DefColumn("Grade"));
		columns.add(new DefColumn("wkmnsp", Constant.COLUMN_TYPE.NUMERIC));
		columns.add(new DefColumn("wkmnspAsgnTm", Constant.COLUMN_TYPE.NUMERIC));
		columns.add(new DefColumn("totAsgnTm", Constant.COLUMN_TYPE.NUMERIC));
		
		for(int i=1; i<=Constant.maxWeekNum; i++) columns.add(new DefColumn("week" + i,  Constant.COLUMN_TYPE.NUMERIC));
		return this;
	}

	@Override
	public AbstractExcelParser parse() throws Exception{
		
		// ================== 프로젝트 코드 확인 ==================
		String excelPrjtCd = StringUtils.defaultIfBlank(sheet.getRow(0).getCell(0).getStringCellValue(), "").trim();
		if(excelPrjtCd == null) {
			addErrorLog("Excel에 프로젝트 코드가 없습니다.");
			return this;
		}else if(!excelPrjtCd.equals(this.data.get("prjtCd"))) {
			addErrorLog("Import 대상 프로젝트 코드와 Excel에 입력된 프로젝트 코드가 일치하지 않습니다.");
			return this;
		}
		
		
		// ================== Week 확인 ==================
		// Excel Week Data 가져오기
		int startWeekIdx = 0;
		int weeksSize = weeks.size() <= Constant.maxWeekNum ? weeks.size() : Constant.maxWeekNum;
		
		for(int col = 0; col < columns.size(); col++) {
			try {
				if(sheet.getRow(titleRow).getCell(col) != null) {			
					String title = StringUtils.defaultIfBlank(sheet.getRow(titleRow).getCell(col).toString(), "").trim();
					columns.get(col).setTitle(title);
				}
				if(columns.get(col).getCode().equals("week1")) startWeekIdx = col;	
			}catch(Exception e) {
				addErrorLog("프로젝트 기간의 Budget 기준 날짜와 Excel컬럼의 날짜 정보가 일치하지 않습니다.");
				addErrorLog("다시 다운로드 후 시도해 보시기 바랍니다.");
				return this;
			}
		}
		// 프로젝트 기간 기준으로 계산된 Week정보와 Excel Week 정보 비교 
		for(int i=0; i<weeksSize; i++) {
			if(!weeks.get(i).getStartDate().equals(columns.get(i+startWeekIdx).getTitle())) {
				addErrorLog("프로젝트 기간의 Budget 기준 날짜와 Excel컬럼의 날짜 정보가 일치하지 않습니다.");
				addErrorLog("다시 다운로드 후 시도해 보시기 바랍니다.");
				return this;
			}
		}
		// Week 정보 확정
		int columnSize = startWeekIdx + weeksSize;
		for(int i= 0; i < weeksSize; i++) {
			this.weeks.get(i).setStartDate(weeks.get(i).getStartDate().replaceAll("/", "-")); // YYYY/MM/DD --> YYYY-MM-DD로 변경
			columns.get(i+startWeekIdx).setRefData(this.weeks.get(i));
		}
		
		
		// ==================  데이터 생성  ================== 
		long totRows = sheet.getPhysicalNumberOfRows();
		ArrayList<Map<String, Object>> members = new ArrayList<Map<String, Object>>();
		ArrayList<Map<String, Object>> bdgts = new ArrayList<Map<String, Object>>();
		
		Double minWkmnsp = (Double) this.data.get("minWkmnsp");
		Double maxWkmnsp = (Double) this.data.get("maxWkmnsp");
		for(int row = startRow; row < totRows; row++) {
			Row rowObj = sheet.getRow(row);
			
			// 키값이 모두 NULL이면 마지막 Row 끝으로 간주.
			String actv, loca, emplno;
			try {
				actv = StringUtils.defaultIfBlank((String) this.getValue(this.getIndexById("actvCd"), rowObj), "").trim();
				loca = StringUtils.defaultIfBlank((String) this.getValue(this.getIndexById("locaCd"), rowObj), "").trim();
				emplno = StringUtils.defaultIfBlank((String) this.getValue(this.getIndexById("membEmplNo"), rowObj), "").trim();
				if( (actv + loca + emplno).equals("")) break;
			}catch(Exception e) {}
			
			// Member Data 생성
			Map<String, Object> member = new HashMap<String, Object>(this.data);		
			boolean isValid = true;

			for(int i=0; i<startWeekIdx; i++) {
				Object value = null;
				try {
					value = this.getValue(i, rowObj);
					if(columns.get(i).checkValidation(value).equals("")) {
						if(columns.get(i).getCode().equals("wkmnsp")) {
							
							Double wkmnsp = (Double) this.getValue(i, rowObj);
							if( wkmnsp.compareTo(0.0) == 0 || (wkmnsp.compareTo(minWkmnsp) >= 0 &&  wkmnsp.compareTo(maxWkmnsp) <= 0) ) {
								member.put(columns.get(i).getCode(), wkmnsp); 
							}else {
								addErrorLog(row, columns.get(i).getTitle(), "숙련도 입력 범위에 포함 되지 않습니다.(범위: " + minWkmnsp + " ~ " + maxWkmnsp + ")");
								isValid = false; 
								break;		
							}
						}else {							
							member.put(columns.get(i).getCode(), this.getValue(i, rowObj));	
						}
					}else {
						addErrorLog(row, columns.get(i).getTitle(), columns.get(i).checkValidation(value));
						isValid = false; 
						break;
					}
				}catch(Exception e) {
					addErrorLog(row, columns.get(i).getTitle(), e.getMessage());
					isValid = false;
					break;
				}
			}
			if(!isValid) continue;
			members.add(member);
			
			// Member Budget Data 생성
			Map<String, Object> bdgt = null;
			double totAsgnTm = 0.0;
			double maxWorkTm = 0;
			for(int col = startWeekIdx; col < columnSize; col++) {
				Double value = null;
				try {
					value = (Double) this.getValue(col, rowObj);
				} catch (Exception e) {
					this.addErrorLog(row, columns.get(col).getTitle(), e.getMessage());
					continue;
				}
				
				maxWorkTm = this.weeks.get(col - startWeekIdx).getWorkDay() * 16; 
				
				if(value.compareTo(maxWorkTm) > 0) {
					addErrorLog(row, columns.get(col).getTitle(), "해당 주에는 최대 " + maxWorkTm + "시간까지만 입력 가능합니다.");
				}else if(value.compareTo(0.0) != 0) {
					bdgt = new HashMap<String, Object>(member);
					bdgts.add(bdgt);
					WeekVO week = (WeekVO) columns.get(col).getRefData();
					bdgt.put("weekFrdt", week.getStartDate());
					bdgt.put(Constant.PARAM_NAME.SESSION, this.data.get(Constant.PARAM_NAME.SESSION));
					bdgt.put("asgnTm", value);
					totAsgnTm += value;
				}
			}
			member.put("totAsgnTm", totAsgnTm);
		}
		
		this.data.put("members", members);
		this.data.put("bdgts", bdgts);
		
		return this;
	}
	
	
}
