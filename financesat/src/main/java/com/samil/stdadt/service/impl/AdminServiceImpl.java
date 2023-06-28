package com.samil.stdadt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.mapper.AdminMapper;
import com.samil.stdadt.service.AdminService;
import com.samil.stdadt.vo.DivisionProfileVO;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper; 

	/**
	 * 표준감사시간 > 기초항목 년도 목록 조회
	 */
	@Override
	public List<HashMap<String, Object>> selectYearList(HashMap<String, Object> param) throws Exception {		
		return adminMapper.selectYearList(param);
	}
	/**
	 * 표준감사시간 > 기초항목 그룹 목록 조회
	 */
	@Override
	public List<HashMap<String, Object>> selectGrpList(HashMap<String, Object> param) throws Exception {
		return adminMapper.selectGrpList(param);
	}
	/**
	 * 표준감사시간 > 기초항목 적용 요율 조회
	 */
	@Override
	public List<HashMap<String, Object>> selectDataList(HashMap<String, Object> param) throws Exception {
		return adminMapper.selectDataList(param);
	}
	/**
	 * 표준감사시간 > 버전 목록 조회
	 */	
	@Override
	public List<HashMap<String, Object>> selectPrflList(HashMap<String, Object> param) throws Exception {
		return adminMapper.selectPrflList(param);
	}
	/**
	 * 표준감사시간 > 숙련도(타이틀) 조회
	 */
	@Override
	public List<HashMap<String, Object>> selectProfTitList(HashMap<String, Object> param) throws Exception {
		return adminMapper.selectProfTitList(param);
	}
	/**
	 * 표준감사시간 > 숙련도(데이터) 조회
	 */
	@Override
	public List<HashMap<String, Object>> selectProfDataList(HashMap<String, Object> param) throws Exception {
		return adminMapper.selectProfDataList(param);
	}
	/**
	 * 표준감사시간 > 기초항목 숙련도 계수 조회
	 */
	@Override
	public List<HashMap<String, Object>> selectFctrDataList(HashMap<String, Object> param) throws Exception {
		return adminMapper.selectFctrDataList(param);
	}
	/**
	 * 표준감사시간 > 기초항목 적용 요율 저장
	 */
	@Override
	public int savePrflGrpYear(HashMap<String, Object> param) throws Exception {
		return adminMapper.savePrflGrpYear(param);
	}
	/**
	 * 표준감사시간 > 기초항목 적용 요율 삭제
	 */
	@Override
	public int deletePrflGrpYear(HashMap<String, Object> param) throws Exception {
		return adminMapper.deletePrflGrpYear(param);
	}
	/**
	 * 표준감사시간 > 기초항목 숙련도 저장
	 */
	@Override
	public int insertFctrWkmnsp(HashMap<String, Object> param) throws Exception {
		return adminMapper.insertFctrWkmnsp(param);
	}
	/**
	 * 표준감사시간 > 기초항목 숙련도 수정
	 */
	@Override
	public int updateFctrWkmnsp(HashMap<String, Object> param) throws Exception {
		return adminMapper.updateFctrWkmnsp(param);
	}
	/**
	 * 표준감사시간 > 기초항목 그룹-계수 프로파일 수정
	 */
	@Override
	public int updatePrflGrpFctr(HashMap<String, Object> param) throws Exception {
		return adminMapper.updatePrflGrpFctr(param);
	}
	@Override
	public List<Map<String, Object>> getRetainProfile(Map<String, Object> param) throws Exception {
		return adminMapper.getRetainProfile(param);
	}
	@Override
	public List<DivisionProfileVO.ProfAuth> getProfileByBonb(Map<String, Object> param) throws Exception {
		return adminMapper.getProfileByBonb(param);
	}
	
	@Override
	public int saveDivisionProfile(Map<String, Object> param) throws Exception {
		return adminMapper.saveDivisionProfile(param);
	}
	
	@Override
	public int saveRetainProfile(Map<String, Object> param) throws Exception {
		/*adminMapper.delRetainProfile(param);
		return adminMapper.insertRetainProfile(param);*/
		return adminMapper.saveRetainProfile(param);
	}
	@Override
	public List<Map<String, Object>> getHistory(Map<String, Object> param) throws Exception {
		return adminMapper.getHistory(param);
	}

}
