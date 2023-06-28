package com.samil.stdadt.retain.service.impl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.mapper.ProjectBudgetMapper;
import com.samil.stdadt.retain.mapper.RetainMapper;
import com.samil.stdadt.retain.service.RetainService;
import com.samil.stdadt.retain.vo.RetainVO;
import com.samil.stdadt.util.SHConverter;

@Service("retainService")
public class RetainServiceImpl implements RetainService {
	
	static final Logger logger = LoggerFactory.getLogger(RetainServiceImpl.class);
	
	static final String tagStart 	= "[Retain 전송 시작]";
	static final String tagComplete = "[Retain 전송 완료]";
	static final String tagFail 	= "[Retain 전송 실패]";

	@Autowired
	ApplicationConfigVO appConfig; 
	
	@Autowired
	RetainMapper retainMapper;
	
	@Autowired 
	ProjectBudgetMapper bdgtMapper;
		
	@Autowired
	CommCodeService commCodeService;
	
	
	/**
	 * 전송 예약 되어있는 프로젝트의 Budget정보를 Retain으로 전송
	 * 스케쥴로 실행 됨(운영서버 프로파일에만 스케쥴 등록되어 있음)
	 * 개발 또는 Test서버 환경파일에서는 스케쥴 등록안되어 있음.
	 */
	@Override
	public void schedSaveRetain() throws Exception {
		RetainVO retain = null;
		
		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			String env = appConfig.getEnv();
			
			// Retain 전송 관련 Config 가져오기(최대 생성가능한 BKG_ID 값, 스케쥴이 동작하는 서버)
			Map<String, String> retainConfig = commCodeService.getCodeMap(
					appConfig.getAppCd(), 
					"RETAIN_TRAN_CONFIG", 
					"VAL1", 
					"Y", 
					new ArrayList<String>(Arrays.asList("MAX_BKG_ID", "SCHED_SERVER_NAME")));
			
			
			String schedServerName = retainConfig.get("SCHED_SERVER_NAME");		// 스케쥴 동작할 서버명
			if(env.equals("development") || env.equals("test")) {
				// 로컬 개발과 테스트 서버에서는 그냥 통과
				// 다만 스케쥴은 등록안되어 있음. 
				// Retain 일괄전송 버튼 클릭하면 실행 됨.
			}else if(!hostName.equals(schedServerName)) {
				// 운영 환경일 경우, SCHED_SERVER_NAME와 동일한 명인 서버에서만 실행
				logger.info("==> 리테인 데이터 전송은 <{}> 서버에서만 실행 합니다.(현재 서버명: {})", schedServerName, hostName);
				return;
			}
			
			// BKG Max ID
			if(retainConfig.get("MAX_BKG_ID") == null) throw new Exception("BKG MAX ID가 설정되어있지 않습니다.");
			Long bkgMaxId = Long.parseLong(retainConfig.get("MAX_BKG_ID"));

			
			// Retain전송할 데이터 가져오기(데이터의 Sorting 순서는 project code, member emplno, budget date)
			List<RetainVO> data = bdgtMapper.selectScheduledRetainData();
			Map<String, Object> updateParam = new HashMap<String, Object>();
			Map<String, Long> resIdMap = new HashMap<String, Long>();
			RetainVO lastRetainData = null;
			Long resId = null;
			String mainPrjtCd ="";
			String prjtCd = "";
			
			if(data.size() > 0) {
				logger.info("=================== Retain 전송 시작 ===================");
				for(RetainVO retainObj : data) {
					retain = retainObj;
					
					// after work activity 설정(Field, Non-Field)
					retain.generateData();
					
					
					// 프로젝트 별 전송 시작-완료 구분 및 완료 Flag 업데이트
					if(!mainPrjtCd.equals(retain.getMainPrjtCd())) {
						if(!mainPrjtCd.equals("")) {
							updateParam.put("prjtCd", mainPrjtCd);
							bdgtMapper.updateRetainTran(updateParam);
							// logger.info("[Retain 전송 완료] 프로젝트: " + prjtCd);
							logger.info("{} 프로젝트: {}", tagComplete, mainPrjtCd);	
						}
						
						logger.info("{} 프로젝트: {}, 전송자 정보: {}({})", tagStart, retain.getMainPrjtCd(), retain.getRetainTranKorNm(), retain.getRetainTranEmplNo());
						mainPrjtCd = retain.getMainPrjtCd();
						prjtCd = retain.getPrjtCd();
					}
					
					
					// BKG_ID 생성
					Long bkgId = retainMapper.getBkgId(bkgMaxId);
					if(bkgId > bkgMaxId) throw new Exception("BKG_ID가 최대값을 초과 하였습니다.(생성된 BKG_ID:" + bkgId + ", 설정된 MAX_BKG_ID:" + bkgMaxId + ")");
					retain.setBkgId(bkgId);
					
					
					// Resource ID 설정
					if(resIdMap.get(retain.getMembEmplNo()) == null) {
						resId = retainMapper.getResId(retain);
						if(resId == null) throw new Exception("Retain에서 프로젝트 멤버("+ retain.getMembEmplNo() +")에 대한 RES_ID값이 존재하지 않습니다.");
						resIdMap.put(retain.getMembEmplNo(), resId);
					}
					retain.setResId(resIdMap.get(retain.getMembEmplNo()));
					
					
					// Retain으로 데이터 전송
					try {	
						retainMapper.insertBookingInfo(retain);
						retainMapper.insertBCG(retain);
						//retainMapper.insertConflict(retain);	// Conflict 테이블에 추가하는 것은 제외 됨.
						retainMapper.insertLog(retain);
					} catch (Exception e) {
						throw new Exception("Retain에 데이터 Insert 중 오류가 발생하였습니다. => ([Exception Message] " + e.getMessage() + ")");
					}
					
					logger.info("- 전송 데이터	: " + SHConverter.convertObjectToJsonString(retain));
					lastRetainData = retain;
				}
				
				if(lastRetainData != null) {
					updateParam.put("prjtCd", lastRetainData.getMainPrjtCd());
					bdgtMapper.updateRetainTran(updateParam);
					logger.info("{} 프로젝트: {}", tagComplete, lastRetainData.getMainPrjtCd());	
				}
				
				logger.info("=================== Retain 전송 완료 ===================");
			}else {
				logger.info("Retain 전송할 데이터가 없습니다.");
			}
		} catch (Exception e) {
			logger.error("{} {}([Location] {}.{}:{})", tagFail, e.getMessage(), e.getStackTrace()[0].getClassName(),e.getStackTrace()[0].getMethodName(), e.getStackTrace()[0].getLineNumber());
			if(retain != null)
				try {
					logger.error(" - Data: " + SHConverter.convertObjectToJsonString(retain));
				} catch (JsonProcessingException e1) {
					logger.error(" - Data: " + e1.getMessage());
				}
			logger.info("=================== Retain 전송 실패 ===================");
			throw e;
		}
	}
}
