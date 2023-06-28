package com.samil.stdadt.service.v3;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.vo.ResultVO;

public interface ExcelImportV3Service {
	public ResultVO importBudgetHour(Map<String, Object> param, File excel, Map<String, List<CommCodeVO>> codes) throws Exception;
	
	public Map<String, Object> getWkmnspMinMax() throws Exception;
}
