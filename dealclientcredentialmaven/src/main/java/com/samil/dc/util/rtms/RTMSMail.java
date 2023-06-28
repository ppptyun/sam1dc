package com.samil.dc.util.rtms;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.core.ContextLoader;
import com.samil.dc.dao.RTMSMailDAO;
import com.samil.dc.domain.RTMSMailBean;
import com.samil.dc.domain.RTMSUserBean;

public class RTMSMail {
	private static final Logger logger = Logger.getRootLogger();
	private static String	_syscd 			= ContextLoader.getContext("ProgramCode");
	public static String 	STATUS_OK 		= "1";
	public static String 	STATUS_FAIL 	= "2";
	public static String 	NON_PROFILE 	= "N/A";

	/*
	 * 메일 프로파일 참조하여 발송할 경우 사용
	 * 	- mailid 	: 메일 프로파일 ID
	 * 	- sender 	: 발신인 정보
	 * 	- rcptList : 수신인 정보
	 * 	- conn 	: DBConnection 객체
	 * 	- emplno 	: 메일 발송 History에 저장할 사번(실제 발신인 사번)
	 */
	public static void sendMail(String mailid, RTMSUserBean sender, List<RTMSUserBean> rcptList, DBConnection conn, String emplno) throws Exception {
		RTMSMailDAO mpdao = new RTMSMailDAO(conn);
		String mid = "";
		RTMSMailBean rm = mpdao.sqlSelectMailProfile(mailid);
		logger.debug("TEST 1");
		// 메일 프로파일 존재 여부 파악
		if(rm != null){	
			// 메일프로파일에 등록된 수신자 리스트 가져오기
			List<RTMSUserBean> prof_rcpt = mpdao.sqlSelectRcptList(mailid);
				
			// 수신 대상자 리스트 + 프로파일에 등록된 수신자 리스트
			rcptList.addAll(prof_rcpt);
				
			logger.debug(rcptList.size());
			// 메일 발송
			if(rcptList != null && rcptList.size()>0){
				// 메일 발신 ID 생성
				mid = makeMid("_" + mailid + "_");
				
				// 수신자 중복 제거
				rcptList = removeDuplication(rcptList);
				
				try{
					// 수신자 리스트 Insert
					mpdao.sqlInsertRcptBatch(mid, rcptList);
					
					// 메일 내용 Insert
					mpdao.sqlInsertMailFromProf(mid, sender, mailid);	
				
					// 메일 발송 History(성공)
					mpdao.sqlInsertMailHistory(mailid, mid, emplno, RTMSMail.STATUS_OK);
				}catch(SQLException e){
					// 메일 발송 History(실패)
					mpdao.sqlInsertMailHistory(mailid, mid, emplno, RTMSMail.STATUS_FAIL);
				}
			}
		}
	}
	
	
	/*
	 * 일반 메일 발송
	 * 	- mail 		: 메일 객체로 Sender, Subject, Contents가 입력되어야 함.
	 * 	- rcptList 	: 수신인 정보
	 * 	- conn 		: DBConnection 객체
	 * 	- emplno 	: 메일 발송 History에 저장할 사번(실제 발신인 사번)
	 */
	public static void sendMail(RTMSMailBean mail, List<RTMSUserBean> rcptList,  DBConnection conn, String emplno) throws Exception {
		RTMSMailDAO mpdao = new RTMSMailDAO(conn);
		String mid = "";
		
		// 메일 발송
		if(rcptList != null && rcptList.size()>0){
			// 메일 발신 ID 생성
			mid = makeMid("_" + emplno + "_");
			mail.setMid(mid);
			
			// 수신자 중복 제거
			rcptList = removeDuplication(rcptList);
			
			try{
				// 수신자 리스트 Insert
				mpdao.sqlInsertRcptBatch(mid, rcptList);
				
				// 메일 내용 Insert	
				mpdao.sqlInsertMail(mail);
			
				// 메일 발송 History(성공)
				mpdao.sqlInsertMailHistory(RTMSMail.NON_PROFILE, mid, emplno, RTMSMail.STATUS_OK);
				
			}catch(SQLException e){
				// 메일 발송 History(실패)
				mpdao.sqlInsertMailHistory(RTMSMail.NON_PROFILE, mid, emplno, RTMSMail.STATUS_FAIL);
			}
		}
		
	}
	
	// 발송 메일 ID 생성 함수
	private static String makeMid(String tag){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String mid = _syscd + tag;
		if(mid.length() > 36){
			mid = mid.substring(0, 35) + dateFormat.format(date);
		}else{
			mid = mid + dateFormat.format(date);
		}
		
		return  mid;
	}
	
	// 중복 제거 함수
	private static List<RTMSUserBean> removeDuplication(List<RTMSUserBean> rcptList){
		HashSet<RTMSUserBean> listSet = new HashSet<RTMSUserBean>(rcptList);
		return new ArrayList<RTMSUserBean>(listSet);
	}
	
	
	/*
	 * 
	public static void sendMailByProfile(String mailid, RTMSUserBean sender, ArrayList<RTMSUserBean> rcptList, DBConnection conn, String emplno) throws Exception {
		RTMSMailDAO mpdao = new RTMSMailDAO(conn);
		
		String mid = "";
	
		RTMSMailBean rm = mpdao.sqlSelectMailProfile(mailid);
		
		// 메일 프로파일 존재 여부 파악
		if(rm != null){
			
			// 메일프로파일에 등록된 수신자 리스트 가져오기
			ArrayList<RTMSUserBean> prof_rcpt = mpdao.sqlSelectRcptList(mailid);
			
			// 메일 발송 1 : 파라미터로 넘어온 수신자에게 메일 발송
			if(rcptList != null && rcptList.size()>0){
				// 메일 발신 ID 생성
				mid = makeMid("_trgt");
				
				// 수신자 중복 제거
				rcptList = removeDuplication(rcptList);
				
				try{
					// 수신자 리스트 Insert
					mpdao.sqlInsertRcptBatch(mid, rcptList);
					
					// 메일 내용 Insert
					mpdao.sqlInsertMailFromProf(mid, sender, mailid);	
				
					// 메일 발송 History(성공)
					mpdao.sqlInsertMailHistory(mailid, mid, emplno, RTMSMail.STATUS_OK);
				}catch(SQLException e){
					// 메일 발송 History(실패)
					mpdao.sqlInsertMailHistory(mailid, mid, emplno, RTMSMail.STATUS_FAIL);
				}
			}
			
			// 메일 발송 2 : 메일프로파일에 등록된 수신자에게 메일 발송
			if(prof_rcpt != null && prof_rcpt.size()>0){
				mid = makeMid("_prof");
				
				// 수신자 중복 제거
				prof_rcpt = removeDuplication(prof_rcpt);
				
				try{
					// 수신자 리스트 Insert
					mpdao.sqlInsertRcptBatch(mid, prof_rcpt);
					
					// 메일 내용 Insert
					String addedContent = makeHtmlTableRcptList(rcptList); // 메일 발송 1의 수신자리스트를 HTML Table형식으로 받아옴.
					mpdao.sqlInsertMailFromProf(mid, sender, mailid, addedContent);	
					
					// 메일 발송 History(성공)
					mpdao.sqlInsertMailHistory(mailid, mid, emplno, RTMSMail.STATUS_OK);
				}catch(SQLException e){
					// 메일 발송 History(실패)
					mpdao.sqlInsertMailHistory(mailid, mid, emplno, RTMSMail.STATUS_FAIL);
				}
			}
		}
	}
	private static String makeHtmlTableRcptList(ArrayList<RTMSUserBean> rcptList){
		String html = "";
		String thStyle = "background-color:#efefef;padding:2px 5px";
		if(rcptList.size() > 0){
			html += "<div style='font-family:dotum;font-size:12px'><br/>■ 수신자 리스트<br/><br/>";
			html += "<table style='border:none 0;width:400px;border-collapse:collapse;border:1px solid #dfdfdf;'>";
			html += "<col width='25%'><col width='25%'><col width='50%'>";
			html += "<tr>";
			html += 	"<th style='" + thStyle + "'>이름</th>";
			html += 	"<th style='" + thStyle + "'>사번</th>";
			html += 	"<th style='" + thStyle + "'>메일</th>";
			html += "</tr>";
			for(int i=0; i<rcptList.size(); i++)
				html += "<tr>" + makeHtmlTrRcptList(rcptList.get(i)) + "</tr>";
			html += "</table>";
			html += "</div>";
		}
		return html;
	}
	
	private static String makeHtmlTrRcptList(RTMSUserBean rcpt){
		String html = "";
		String tdStyle = "border:1px solid #dfdfdf;padding:2px 5px";
		html += "<td style='" + tdStyle + "'>" + rcpt.getName()	+ "</td>";
		html += "<td style='" + tdStyle + "'>" + rcpt.getId() 	+ "</td>";
		html += "<td style='" + tdStyle + "'>" + rcpt.getMail()	+ "</td>";
		return html;
	}
	
	*/
}
