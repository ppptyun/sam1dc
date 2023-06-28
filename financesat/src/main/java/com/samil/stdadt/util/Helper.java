package com.samil.stdadt.util;

import java.io.UnsupportedEncodingException;

public class Helper {
	public static String getMailSubject(String prefix, String prjtpnm, String shrtnm, String postfix) throws UnsupportedEncodingException {
		int maxSize = 90; // 메일 제목의 최대 길이
		String charsets = "EUC-KR"; 
		
		// 잔여 사이즈 구하기
		int remainSize = maxSize - shrtnm.getBytes(charsets).length - prefix.getBytes(charsets).length - postfix.getBytes(charsets).length + 1; // 1은 프로젝트명/용역명 구분하는 구분자 길이
		int test = remainSize - prjtpnm.getBytes(charsets).length;
		int maxIdx = prjtpnm.length() - 1;
		if(test<0) {
			String replaceText = "...";
			test -= replaceText.getBytes(charsets).length;
			
			// 허용 범위 초과 시 뒤에서부터 자름.
			for(int i= maxIdx; i >= 0 ; i-- ) {
				if(test >= 0) {
					maxIdx = i;
					break;
				}
				int ch = prjtpnm.charAt(i);
				test += ((ch > 127 || ch < 0) ? 2 : 1);
			}
			
			prjtpnm = prjtpnm.substring(0, maxIdx) + replaceText;
		}
		
		// Subject 구성하여 Return
		return prefix + prjtpnm + "/" + shrtnm + postfix;
	}
}
