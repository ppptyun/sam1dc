package com.samil.stdadt.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.vo.DivisionProfileVO;


@Mapper
public interface AdminMapper {
	
	//표준감사시간 > 기초항목 년도 목록 조회
	public List<HashMap<String, Object>> selectYearList(HashMap<String, Object> param) throws Exception;
	//표준감사시간 > 기초항목 그룹 목록 조회
	public List<HashMap<String, Object>> selectGrpList(HashMap<String, Object> param) throws Exception;
	//표준감사시간 > 기초항목 적용 요율 조회
	public List<HashMap<String, Object>> selectDataList(HashMap<String, Object> param) throws Exception;
	
	//표준감사시간 > 버전 목록 조회
	public List<HashMap<String, Object>> selectPrflList(HashMap<String, Object> param) throws Exception;
	
	//표준감사시간 > 숙련도(타이틀) 조회
	public List<HashMap<String, Object>> selectProfTitList(HashMap<String, Object> param) throws Exception;
    //표준감사시간 > 숙련도(데이터) 조회
	public List<HashMap<String, Object>> selectProfDataList(HashMap<String, Object> param) throws Exception;
	//표준감사시간 > 기초항목 숙련도 계수 조회
	public List<HashMap<String, Object>> selectFctrDataList(HashMap<String, Object> param) throws Exception;
	
	//표준감사시간 > 기초항목 적용 요율 저장
	public int savePrflGrpYear(HashMap<String, Object> param) throws Exception;
	//표준감사시간 > 기초항목 적용 요율 삭제
	public int deletePrflGrpYear(HashMap<String, Object> param) throws Exception;
	
	//표준감사시간 > 기초항목 숙련도 저장
	public int insertFctrWkmnsp(HashMap<String, Object> param) throws Exception;
	//표준감사시간 > 기초항목 숙련도 수정
	public int updateFctrWkmnsp(HashMap<String, Object> param) throws Exception;
	
	//표준감사시간 > 기초항목 그룹-계수 프로파일 수정
	public int updatePrflGrpFctr(HashMap<String, Object> param) throws Exception;
	
	
	
	public List<DivisionProfileVO.ProfAuth> getProfileByBonb(Map<String, Object> param) throws Exception;
	public int saveDivisionProfile(Map<String, Object> param) throws Exception;
	public List<Map<String, Object>> getRetainProfile(Map<String, Object> param) throws Exception;
	
	
	public int delRetainProfile(Map<String, Object> param) throws Exception;
	public int insertRetainProfile(Map<String, Object> param) throws Exception;
	public int saveRetainProfile(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getHistory(Map<String, Object> param) throws Exception;

}
