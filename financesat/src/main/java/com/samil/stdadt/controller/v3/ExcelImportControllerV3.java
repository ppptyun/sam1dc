package com.samil.stdadt.controller.v3;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.v3.ExcelImportV3Service;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.util.FileUploader;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping(path="/excel/import/v3", method=RequestMethod.POST)
public class ExcelImportControllerV3 {
	static final Logger logger = LoggerFactory.getLogger(ExcelImportControllerV3.class);
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@Autowired
	ExcelImportV3Service excelService;
	
	@Autowired
	CommCodeService commCdService;

	@RequestMapping("/budgetHours")	
	public ModelAndView budgetHours(HttpServletRequest request, MultipartFile file, String prjtCd) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			UserSessionVO userSession= AppHelper.getSession(request);
			
			param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
			param.put("prjtCd", prjtCd);
			param.put(Constant.PARAM_NAME.SESSION, userSession);
						
			if(file == null) {
				result.setStatus(Constant.RESULT.FAIL);
				result.setMsg(Constant.RESULT_MSG.EMPTY_FILE);
			}else {
				String originalName = file.getOriginalFilename();
				int pos = originalName.lastIndexOf(".");
				String ext = originalName.substring(pos + 1);
					
				if(!Arrays.asList(Constant.ALLOW_EXTENSION).contains(ext)) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.NOT_ALLOW_FILE);
				}else {	
					File uploadedFile = FileUploader.uploadFile(request.getSession().getServletContext().getRealPath(appConfig.getUploadPath()), originalName, file.getBytes());
					
					Map<String, List<CommCodeVO>> codes = new HashMap<String, List<CommCodeVO>>();
					codes.put("ACTV_V3", commCdService.getCodeList(appConfig.getAppCd(), "ACTV_V3"));
					codes.put("LOCA", commCdService.getCodeList(appConfig.getAppCd(), "LOCA"));
					Map<String, Object> wkmnspMinMax = excelService.getWkmnspMinMax();
					param.put("minWkmnsp", ((BigDecimal) wkmnspMinMax.get("minWkmnsp")).doubleValue());
					param.put("maxWkmnsp", ((BigDecimal) wkmnspMinMax.get("maxWkmnsp")).doubleValue());
					
					result = excelService.importBudgetHour(param, uploadedFile, codes);
				}				
			}
		}catch(Exception e) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.ERROR);
			e.printStackTrace();
		}
		
		mv.addObject("result", result);
		return mv;
	}
}
