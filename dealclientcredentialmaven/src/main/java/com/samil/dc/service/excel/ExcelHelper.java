package com.samil.dc.service.excel;

import org.apache.commons.lang3.StringUtils;

public final class ExcelHelper {

	public static final Integer ROW_INDEX_START = 3;
	public static final Integer COLUMN_INDEX_PROJECTCODE = 1;

	public static final int COLUMN_TYPE_TEXT = 0;
	public static final int COLUMN_TYPE_EMP = 1;
	public static final int COLUMN_TYPE_DATE = 2;
	public static final int COLUMN_TYPE_NUMERIC = 3;
	public static final int COLUMN_TYPE_COMCD = 4;
	
	public static final String PROJECT_REFYEARLY = "2016";
	public static final String VIRTUAL_PROJECT_STARTWITH = "M";

	/**
	 * 날짜 포맷 변경
	 * 
	 * @param cellDateString
	 * @return
	 */
	public static String convertDateFormat(String cellDateString) {
		if (StringUtils.isBlank(cellDateString)) {
			return "";
		}
		
		cellDateString = StringUtils.trim(cellDateString);
		String[] dataInfos = null;
		if (StringUtils.contains(cellDateString, "-")) {
			dataInfos = StringUtils.split(cellDateString.trim(), "-");
		} else if (StringUtils.contains(cellDateString, ".")) {
			dataInfos = StringUtils.split(cellDateString.trim(), ".");			
		}
		if (dataInfos != null && dataInfos.length == 3) {
			String year = StringUtils.trim(dataInfos[0]);
			String month = StringUtils.leftPad(StringUtils.trim(dataInfos[1]), 2, "0");
			String day = StringUtils.leftPad(StringUtils.trim(dataInfos[2]), 2, "0");
			
			StringBuffer sb = new StringBuffer();
			if (StringUtils.length(year) == 2) {
				// 현대적인 증권거래 시작 년도(1956년)
				int yearLimit = 56;
				if (yearLimit >= Integer.valueOf(year)) {
					sb.append("20").append(year).append("-");
				} else {
					sb.append("19").append(year).append("-");
				}
			} else if (StringUtils.length(year) == 4) {
				sb.append(year).append("-");
			} else {
				return "";
			}
			sb.append(month).append("-").append(day);
			
			return sb.toString();			
		} else {
			return cellDateString;
		}
	}
	
	/**
	 * 가상 프로젝트 코드인지 판별
	 * 
	 * @param prjtcd
	 * @return
	 */
	public static boolean isVirtualProjectCode(String prjtcd) {
		return prjtcd.startsWith(VIRTUAL_PROJECT_STARTWITH);
	}
	
	/**
	 * 배열 안에 찾고자 하는 항목이 포함되었는지 여부
	 * 
	 * @param search
	 * @param pool
	 * @return
	 */
	public static boolean isContain(String search, String[] pool) {
		for (String item : pool) {
			if (item.equals(search)) {
				return true;
			}
		}
		return false;
	}
	
	public static String stripForNumberFormat(String strNumber) {
		if (StringUtils.isBlank(strNumber)) {
			return "";
		}
		String newStrNumber = String.valueOf(strNumber);
		newStrNumber = StringUtils.replace(newStrNumber, ",", "");
		return newStrNumber.trim();
	}
}
