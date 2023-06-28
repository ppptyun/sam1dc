package com.samil.stdadt.util.excelparser.v3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.util.excelparser.AbstractExcelParser;
import com.samil.stdadt.util.excelparser.DefColumn;
import com.samil.stdadt.util.excelparser.Validator;
import com.samil.stdadt.util.excelparser.ValidatorInterface;
import com.samil.stdadt.vo.WeekVO;

public class BudgetHourExcelParser extends AbstractExcelParser{
	
	private final int titleRow = 1;	// Heaer Text 시작 Row Index
	private final int startRow = 4;	// Data 시작 Row Index;
	
	private List<WeekVO> weeks;
	
	Validator validator = new Validator();
	
	public BudgetHourExcelParser(Map<String, Object> param, Sheet sheet, Map<String, List<CommCodeVO>> codes, List<WeekVO> weeks) throws Exception {
		super(sheet, codes);
		this.weeks = weeks;
		this.data = new HashMap<String, Object>(param);
	}
	
	@Override
	public AbstractExcelParser ready() throws Exception{
		columns.add(new DefColumn("prjtcd", new ValidatorInterface[] {validator.isNotNull})); 
		columns.add(new DefColumn("prjtnm"));
		columns.add(new DefColumn("tbd", new ValidatorInterface[] {validator.isNotNull}));
		columns.add(new DefColumn("actvCd", Constant.COLUMN_TYPE.CODE_NAME, "ACTV_V3", new ValidatorInterface[] {validator.isNotNull}));
		columns.add(new DefColumn("locaCd", Constant.COLUMN_TYPE.CODE_NAME, "LOCA", new ValidatorInterface[] {validator.isNotNull}));
		columns.add(new DefColumn("Name"));
		columns.add((new DefColumn("membEmplNo", new ValidatorInterface[] {validator.isNotNull, validator.isEmpNo})).setFormat("%06d"));
//		columns.add(new DefColumn("membEmplNo"));
		columns.add(new DefColumn("gradnm"));
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
		String title = "";
		for(int col = 0; col < columns.size(); col++) {
			Cell cell = sheet.getRow(titleRow).getCell(col);
			try {
				if(cell != null) {
					if(cell.getCellType() == CellType.NUMERIC) {	// 날짜 타입으로 입력되어있을 경우, cell type은 Numeric
						title = new SimpleDateFormat("yyyy/MM/dd").format(cell.getDateCellValue());
					} else {						
						title = StringUtils.defaultIfBlank(sheet.getRow(titleRow).getCell(col).toString(), "").trim();
					}
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
			
			String prjtcd="", actvCd = "", locaCd = "", membEmplNo = "", tbd = "",  gradnm= "";
			Double wkmnsp = 0.0;
			try {
				prjtcd = StringUtils.defaultIfBlank((String) this.getValue(this.getIndexById("prjtcd"), rowObj), "").trim();
				actvCd = StringUtils.defaultIfBlank((String) this.getValue(this.getIndexById("actvCd"), rowObj), "").trim();
				locaCd = StringUtils.defaultIfBlank((String) this.getValue(this.getIndexById("locaCd"), rowObj), "").trim();
				tbd = StringUtils.defaultIfBlank((String) this.getValue(this.getIndexById("tbd"), rowObj), "").trim();
				membEmplNo = "Y".equals(tbd) && Constant.BUDGET_ROLE_CD.contains(actvCd) ? "999999" : StringUtils.defaultIfBlank((String) this.getValue(this.getIndexById("membEmplNo"), rowObj), "").trim();
				gradnm = StringUtils.defaultIfBlank((String) this.getValue(this.getIndexById("gradnm"), rowObj), "").trim();
				wkmnsp = (Double) this.getValue(this.getIndexById("wkmnsp"), rowObj);
				
				if( (prjtcd + actvCd + locaCd + membEmplNo).equals("")) break;
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			// Member Data 생성
			Map<String, Object> member = new HashMap<String, Object>(this.data);
			boolean isValid = true;
			
			// ----------------------------------------------------------------------------------------------------------
			// 알 수 없는 오류로 인해서 숙련도 고려 투입시간 cellGetType() 이 동작하지 않는다. (getValue 에서 내부적으로 cellGetType()를 호출한다.) 
			// 우선. 숙련도 고려 투입시간 과 Total Time 은 읽어들이지 않는다. V2 에서는 잘되는데... 나중에 다시 보자.
			// 숙련도 고려 투입시간 은 DB 직접 저장이 되지 않는것 같고,
			// Total Time 은 아래 부분에서 다시 계산해서 넣는 로직이 있으므로 관계 없을듯...
			// ----------------------------------------------------------------------------------------------------------			
			//2020-05-17  추가
			// validation check
			for(int i=0; i<(startWeekIdx-2); i++) {
				try {
					// 사번의 경우, tbd, default값이 있는 경우 패스 한다.
					if(columns.get(i).getCode().equals("membEmplNo") && "Y".equals(tbd) && Constant.BUDGET_ROLE_CD.contains(actvCd)) {
						continue;
					}
					
					Object value = this.getValue(i, rowObj);
					// 기본 validation check (Column 정의시 등록된 validation)
					if(columns.get(i).checkValidation(value).equals("")) {
						// wkmnsp의 경우, 추가적으로 더 확인..
						if(columns.get(i).getCode().equals("wkmnsp")) {
							if(! (wkmnsp.compareTo(0.0) == 0 || (wkmnsp.compareTo(minWkmnsp) >= 0 &&  wkmnsp.compareTo(maxWkmnsp) <= 0)) ) {
								addErrorLog(row, columns.get(i).getTitle(), "숙련도 입력 범위에 포함 되지 않습니다.(범위: " + minWkmnsp + " ~ " + maxWkmnsp + ")");
								isValid = false; 
								break;		
							}
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
			
			// 유효성 확인에 통과하지 못했으면 다음 Row를 읽어 들인다.
			if(!isValid) continue;
			
			member.put("prjtcd", prjtcd);
			member.put("tbd", tbd);
			member.put("actvCd", actvCd);
			member.put("locaCd", locaCd);
			member.put("membEmplNo", membEmplNo);
			member.put("wkmnsp", wkmnsp);
			member.put("gradnm", gradnm);
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
				
				//-------------------------------------------
				//TBD = 'Y' 이면 숫자범위제한 금지
				//-------------------------------------------
				if("N".equals(tbd)) {
					if(value.compareTo(maxWorkTm) > 0) {
						addErrorLog(row, columns.get(col).getTitle(), "해당 주에는 최대 " + maxWorkTm + "시간까지만 입력 가능합니다.");
					}else if(value.compareTo(0.0) > 0) {
						bdgt = new HashMap<String, Object>(member);
						bdgts.add(bdgt);
						WeekVO week = (WeekVO) columns.get(col).getRefData();
						bdgt.put("weekFrdt", week.getStartDate());
						bdgt.put(Constant.PARAM_NAME.SESSION, this.data.get(Constant.PARAM_NAME.SESSION));
						bdgt.put("asgnTm", value);
						totAsgnTm += value;
					}					
				}else {
					if(value.compareTo(0.0) > 0) {
						bdgt = new HashMap<String, Object>(member);
						bdgts.add(bdgt);
						WeekVO week = (WeekVO) columns.get(col).getRefData();
						bdgt.put("weekFrdt", week.getStartDate());
						bdgt.put(Constant.PARAM_NAME.SESSION, this.data.get(Constant.PARAM_NAME.SESSION));
						bdgt.put("asgnTm", value);
						totAsgnTm += value;
					}								
				}
			}
			member.put("totAsgnTm", totAsgnTm);
		}

		this.data.put("members", members);
		this.data.put("bdgts", bdgts);
		
		return this;
	}
	
	
}
