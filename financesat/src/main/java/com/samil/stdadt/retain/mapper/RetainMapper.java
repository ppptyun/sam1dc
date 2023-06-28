package com.samil.stdadt.retain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.samil.stdadt.retain.vo.RetainVO;

@Mapper
public interface RetainMapper {
	
	public Long getResId(RetainVO param) throws Exception;
	public int insertBookingInfo(RetainVO param) throws Exception;
	public int insertBCG(RetainVO param) throws Exception;
	public int insertConflict(RetainVO param) throws Exception;
	public int insertLog(RetainVO param) throws Exception;
	
	public Long getBkgId(@Param("maxId") Long maxId) throws Exception;
}
