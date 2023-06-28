package com.samil.stdadt.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.ExcelExportService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.util.exceldownload.ExportExcelBdgtByEmp;
import com.samil.stdadt.util.exceldownload.ExportExcelBdgtByEmpV3;
import com.samil.stdadt.util.exceldownload.ExportExcelSatBdgtInfo;
import com.samil.stdadt.util.exceldownload.ExportExcelSatBdgtInfoV3;
import com.samil.stdadt.util.exceldownload.ExportExcelSatPrjtInfo;
import com.samil.stdadt.util.exceldownload.ExportExcelSatPrjtInfoV2;
import com.samil.stdadt.util.exceldownload.ExportExcelSatPrjtInfoV3;
import com.samil.stdadt.vo.WeekVO;

@Controller
@RequestMapping(path="/excel/export", method=RequestMethod.POST)
public class ExcelExportController {
	static final Logger logger = LoggerFactory.getLogger(ExcelExportController.class);
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@Autowired
	ExcelExportService excelService;
	
	@Autowired
	CommCodeService commCdService;
	
	

	@RequestMapping("/bdgtByEmp")	
	public void bdgtByEmp(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> param = AppHelper.convertReqeustToMap(request);
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v1"));
		
		List<WeekVO> weeks = excelService.getWeeks(param);
		param.put("weeks", weeks);
		List<Map<String, Object>> data = excelService.getBdgtByMemb(param);
		
		Map<String, Object> option = new HashMap<String, Object>();
		option.put("weeks", weeks);
		option.put("data", data);
		
		request.getSession().getServletContext().getRealPath(appConfig.getExcelTemplatePath());
		ExportExcelBdgtByEmp excelExport = new ExportExcelBdgtByEmp(option);
		
		excelExport.ready();
		Workbook excel = excelExport.generate();
		
		if(excel != null) {
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			setResponseHeader(request, response, param.get("filename").toString() + "_" + format.format(now) + ".xlsx");
			excel.write(response.getOutputStream());
			excel.close();
		}
	}
	
	@RequestMapping("/satBdgtInfo")
	public void satBdgtInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> param = AppHelper.convertReqeustToMap(request);
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v1"));
		
		List<Map<String, Object>> data = excelService.getSatBdgtInfo(param);
		
		Map<String, Object> option = new HashMap<String, Object>();
		option.put("data", data);
		
		request.getSession().getServletContext().getRealPath(appConfig.getExcelTemplatePath());
		ExportExcelSatBdgtInfo excelExport = new ExportExcelSatBdgtInfo(option);
		
		excelExport.ready();
		Workbook excel = excelExport.generate();
		
		if(excel != null) {
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			setResponseHeader(request, response, param.get("filename").toString() + "_" + format.format(now) + ".xlsx");
			excel.write(response.getOutputStream());
			excel.close();
		}
	}
	
	@RequestMapping("/satPrjtInfo")
	public void satPrjtInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> param = AppHelper.convertReqeustToMap(request);
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v1"));
		
		List<Map<String, Object>> data = excelService.getSatPrjtInfo(param);
		
		Map<String, Object> option = new HashMap<String, Object>();
		option.put("data", data);
		
		ExportExcelSatPrjtInfo excelExport = new ExportExcelSatPrjtInfo(option);
		
		excelExport.ready();
		Workbook excel = excelExport.generate();
		
		if(excel != null) {
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			setResponseHeader(request, response, param.get("filename").toString() + "_" + format.format(now) + ".xlsx");
			excel.write(response.getOutputStream());
			excel.close();
		}
	}
	
	@RequestMapping("/bdgtByEmpV2")	
	public void bdgtByEmpV2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> param = AppHelper.convertReqeustToMap(request);
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v2"));
		
		List<WeekVO> weeks = excelService.getWeeks(param);
		param.put("weeks", weeks);
		List<Map<String, Object>> data = excelService.getBdgtByMemb(param);
		
		Map<String, Object> option = new HashMap<String, Object>();
		option.put("weeks", weeks);
		option.put("data", data);
		
		request.getSession().getServletContext().getRealPath(appConfig.getExcelTemplatePath());
		ExportExcelBdgtByEmp excelExport = new ExportExcelBdgtByEmp(option);
		
		excelExport.ready();
		Workbook excel = excelExport.generate();
		
		if(excel != null) {
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			setResponseHeader(request, response, param.get("filename").toString() + "_" + format.format(now) + ".xlsx");
			excel.write(response.getOutputStream());
			excel.close();
		}
	}
	
	@RequestMapping("/satBdgtInfoV2")
	public void satBdgtInfoV2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> param = AppHelper.convertReqeustToMap(request);
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v2"));
		
		
		List<Map<String, Object>> data = excelService.getSatBdgtInfo(param);
		
		Map<String, Object> option = new HashMap<String, Object>();
		option.put("data", data);
		
		request.getSession().getServletContext().getRealPath(appConfig.getExcelTemplatePath());
		ExportExcelSatBdgtInfo excelExport = new ExportExcelSatBdgtInfo(option);
		
		excelExport.ready();
		Workbook excel = excelExport.generate();
		
		if(excel != null) {
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			setResponseHeader(request, response, param.get("filename").toString() + "_" + format.format(now) + ".xlsx");
			excel.write(response.getOutputStream());
			excel.close();
		}
	}
	
	@RequestMapping("/satPrjtInfoV2")
	public void satPrjtInfoV2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> param = AppHelper.convertReqeustToMap(request);
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v2"));
		
		
		List<Map<String, Object>> data = excelService.getSatPrjtInfoV2(param);
		
		Map<String, Object> option = new HashMap<String, Object>();
		option.put("data", data);
		
		ExportExcelSatPrjtInfoV2 excelExport = new ExportExcelSatPrjtInfoV2(option);
		
		excelExport.ready();
		Workbook excel = excelExport.generate();
		
		if(excel != null) {
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			setResponseHeader(request, response, param.get("filename").toString() + "_" + format.format(now) + ".xlsx");
			excel.write(response.getOutputStream());
			excel.close();
		}
	}
	
	
	@RequestMapping("/bdgtByEmpV3")	
	public void bdgtByEmpV3(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> param = AppHelper.convertReqeustToMap(request);
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v3","v4"));
		
		List<WeekVO> weeks = excelService.getWeeks(param);
		param.put("weeks", weeks);
		List<Map<String, Object>> data = excelService.getBdgtByMembV3(param);
		
		Map<String, Object> option = new HashMap<String, Object>();
		option.put("weeks", weeks);
		option.put("data", data);
		option.put("satTrgtYn", param.get("satTrgtYn"));
		
		request.getSession().getServletContext().getRealPath(appConfig.getExcelTemplatePath());
		ExportExcelBdgtByEmpV3 excelExport = new ExportExcelBdgtByEmpV3(option);
		
		excelExport.ready();
		Workbook excel = excelExport.generate();
		
		if(excel != null) {
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			setResponseHeader(request, response, param.get("filename").toString() + "_" + format.format(now) + ".xlsx");
			excel.write(response.getOutputStream());
			excel.close();
		}
	}
	
	@RequestMapping("/satBdgtInfoV3")
	public void satBdgtInfoV3(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> param = AppHelper.convertReqeustToMap(request);
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v3"));
		
		List<Map<String, Object>> data = excelService.getSatBdgtInfoV3(param);
		
		Map<String, Object> option = new HashMap<String, Object>();
		option.put("data", data);
		option.put("satTrgtYn", param.get("satTrgtYn"));
		
		request.getSession().getServletContext().getRealPath(appConfig.getExcelTemplatePath());
		ExportExcelSatBdgtInfoV3 excelExport = new ExportExcelSatBdgtInfoV3(option);
		
		excelExport.ready();
		Workbook excel = excelExport.generate();
		
		if(excel != null) {
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			setResponseHeader(request, response, param.get("filename").toString() + "_" + format.format(now) + ".xlsx");
			excel.write(response.getOutputStream());
			excel.close();
		}
	}
	
	@RequestMapping("/satPrjtInfoV3")
	public void satPrjtInfoV3(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> param = AppHelper.convertReqeustToMap(request);
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v3"));
		
		
		List<Map<String, Object>> data = excelService.getSatPrjtInfoV3(param);
		
		Map<String, Object> option = new HashMap<String, Object>();
		
		option.put("data", data);
		option.put("satTrgtYn", param.get("satTrgtYn"));
		
		ExportExcelSatPrjtInfoV3 excelExport = new ExportExcelSatPrjtInfoV3(option);
		
		excelExport.ready();
		Workbook excel = excelExport.generate();
		
		if(excel != null) {
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			setResponseHeader(request, response, param.get("filename").toString() + "_" + format.format(now) + ".xlsx");
			excel.write(response.getOutputStream());
			excel.close();
		}
	}
	
	private void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		String header = request.getHeader("User-Agent");

		if (header.contains("MSIE") || header.contains("Trident")) {
			String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
			response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
		} else {
			String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
		}
		
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
	}
	
}
