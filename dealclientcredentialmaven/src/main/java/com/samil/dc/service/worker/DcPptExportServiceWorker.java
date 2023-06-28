package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcCredentialDAO;
import com.samil.dc.domain.DcCredentialListBean;
import com.samil.dc.domain.DcCredentialSearchConditionBean;
import com.samil.dc.util.ppt.AbstractPptProcessor;
import com.samil.dc.util.ppt.impl.PptBrsProcessor;
import com.samil.dc.util.ppt.impl.PptCfProcessor;
import com.samil.dc.util.ppt.impl.PptCpniProcessor;

public class DcPptExportServiceWorker extends AbstractServiceWorker {
	private static final Logger logger = Logger.getRootLogger();
	private static final String PARAM_CTGCD = "ctgcd";
	
	
	
	
	public DcPptExportServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		ValidationResult validationResult = ServiceHelper.validateParameterBlank(request, PARAM_CTGCD);
		if(!validationResult.isValid()){
			return validationResult;	
		}
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		DcCredentialSearchConditionBean searchCondition = ServiceHelper.parseSearchCondition(request);
		DcCredentialDAO credDAO = new DcCredentialDAO(connection);
		List<DcCredentialListBean> list = new ArrayList<DcCredentialListBean>();
		try {
			list = credDAO.sqlSelectSetupConsultTypeList(searchCondition);
			if (list == null) {
				list = new ArrayList<DcCredentialListBean>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**
		 * F1 CF
		 * F3 Valudation
		 * F7 Transaction Guide
		 * F5 BRS
		 * FZ CP&I
		 */
		
		String ctgcd = StringUtils.trim(ServiceHelper.getParameter(request, PARAM_CTGCD));
		
		HashMap<String, String> tmp = new HashMap<String, String>();
		tmp.put("F1", "CORPORATE FINANCE");
		tmp.put("F3", "Valuation & Economics");
		tmp.put("F7", "Transaction Services");
		tmp.put("F5", "Business Recovery Services");
		tmp.put("FZ", "CP&I");
		
		AbstractPptProcessor ps = null;
		
		if(ctgcd.equals("F1")||ctgcd.equals("F3")||ctgcd.equals("F7")){
			ps = new PptCfProcessor(tmp.get(ctgcd), list, 11);
		}else if(ctgcd.equals("FZ")){
			ps = new PptCpniProcessor(tmp.get(ctgcd), list, 12);
		}else if(ctgcd.equals("F5")){
			ps = new PptBrsProcessor(tmp.get(ctgcd), list, 12);
		}
		
		return ps.ready().make();
	}

}
