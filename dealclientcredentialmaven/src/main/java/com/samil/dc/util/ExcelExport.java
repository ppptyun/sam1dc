package com.samil.dc.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.samil.dc.access.DBConnection;
import com.samil.dc.access.OracleConnection;
import com.samil.dc.util.excel.CustomExcelWriter;

@SuppressWarnings("serial")
public class ExcelExport extends HttpServlet{
	private static final Logger logger = Logger.getRootLogger();
	
	private	String m_package		= "com.samil.dc.dao.";
	private DBConnection m_dbcon	=	null;
	private String m_className		=	"";
	private String m_methodName		=	"";
	private JSONObject m_excelJson 	= null;
	private JSONArray m_condition 	= null;
	private JSONArray m_header		= null;
	
	private String m_filename 	= "";
	private String m_xmlHead 	= "";
	private String m_xmlBody 	= "";
	private String m_filetype	= "";
	
	ArrayList<Integer> m_colIdx = new ArrayList<Integer>();		// 출력할 컬럼의 Index 리스트
	
	
	//Column 속성
	private ArrayList<__COLUMN_ATTR> column_attr = new ArrayList<__COLUMN_ATTR>();
	
	class __COLUMN_ATTR{
		public String m_name		= "";
		public String m_align 		= "";
		public String m_type 		= "";
		public String m_format		= "";
		public JSONObject m_action 	= null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		ResultSet rs		= null;
		Class tmpClass 		= null;
		Method tmpMethod 	= null;
		Object instance 	= null;
		
		JSONParser jsonParser = new JSONParser();
		try {
			m_dbcon 		= new OracleConnection();
			m_filetype	 	= req.getParameter("filetype");		// Export Type : 현재는 excel 뿐
			m_className 	= req.getParameter("SystemCode");	//
			m_methodName 	= req.getParameter("Method");		//
			m_excelJson 	= (JSONObject) jsonParser.parse(req.getParameter("excelJSON"));
			m_header 		= (JSONArray) m_excelJson.get("column");
			m_filename 		= StringUtils.defaultIfBlank(StringUtils.trim((String) m_excelJson.get("filename")), "grid");
			m_condition 	= (JSONArray) m_excelJson.get("condition");
			req.removeAttribute("excelJSON");
			Enumeration<String> params = (Enumeration<String>)req.getParameterNames();
			String key = null;
			String param = null;
			while(params.hasMoreElements()){
				key = params.nextElement();
				if(param == null){
					param = key + ":" + req.getParameter(key);
				}else{
					if(!"excelJSON".equals(key)){
						param = param + ", " + key + ":" + req.getParameter(key);	
					}
				}
			}
			logger.debug("<" + m_className + ":" + m_methodName + "> Request{" + param + "}");
			logger.debug("<" + m_className + ":" + m_methodName + "> excelJson Request{" + m_excelJson.toJSONString() + "}");
			
			// Excel Head 그리기
			makeHeader();
				
			// Excel Body 그리기
			if(m_className != null && m_methodName != null){
				Object[] args = { req, m_dbcon };
				tmpClass 	= Class.forName(m_package + m_className);
				instance 	= tmpClass.newInstance();
				tmpMethod 	= tmpClass.getMethod(m_methodName, HttpServletRequest.class, DBConnection.class);
				rs 			= (ResultSet) tmpMethod.invoke(instance, args);
				makeBody(rs);
			}
			// Excel 파일 다운로드
			outputExcel(res);
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			m_dbcon.close();
		}
	}
	
	private void makeHeader(){
		StringBuffer xml = new StringBuffer();
		JSONArray columns = null;
		JSONObject column = null;
		
		xml.append("<head>");
		int header_row_cnt = ((JSONArray) m_header.get(0)).size();
		
		for(int i=0; i < header_row_cnt; i++){
			xml.append("<columns>");
			for(int j=0; j<m_header.size(); j++){
				columns = (JSONArray) m_header.get(j);
				column = (JSONObject) columns.get(i);
				
				xml.append("<column");
				if(column.get("width") != null)		{xml.append(" width=\"").append(column.get("width")).append("\" ");}
				if(column.get("rowspan") != null)	{xml.append(" rowspan=\"").append(column.get("rowspan")).append("\" ");}
				if(column.get("colspan") != null)	{xml.append(" colspan=\"").append(column.get("colspan")).append("\" ");}
				if(column.get("align") != null)		{xml.append(" align=\"").append(column.get("align")).append("\" ");}
				if(column.get("type") != null)		{xml.append(" type=\"").append(column.get("type")).append("\" ");}
				if(column.get("format") != null)	{xml.append(" format=\"").append(column.get("format")).append("\" ");}
				
				xml.append("><![CDATA[").append(column.get("dspname")).append("]]></column>");
				
				if(i==0){
					__COLUMN_ATTR tmp = new __COLUMN_ATTR();
					if(column.get("name") != null)	{tmp.m_name 	= StringUtils.defaultIfBlank(StringUtils.trim((String)column.get("name")), "");}
					if(column.get("align") != null)	{tmp.m_align 	= StringUtils.defaultIfBlank(StringUtils.trim((String)column.get("align")), "left");}else{tmp.m_align	= "left";}
					if(column.get("type") != null)	{tmp.m_type 	= StringUtils.defaultIfBlank(StringUtils.trim((String)column.get("type")), "");}
					if(column.get("format") != null){tmp.m_format 	= StringUtils.defaultIfBlank(StringUtils.trim((String)column.get("format")), "");}
					column_attr.add(tmp);
				}
			}
			xml.append("</columns>");
		}
		xml.append("</head>");
		m_xmlHead =  xml.toString();
	}
	
	private void makeBody(ResultSet rs) throws SQLException {
		StringBuffer xml = new StringBuffer();
		String value = "";
		while (rs.next()) {
			xml.append("<row>");
			for(int i=0; i < column_attr.size(); i++){
				value = StringUtils.defaultIfBlank(rs.getString(column_attr.get(i).m_name),"");
				xml.append("<cell")
				.append(" align=\"").append(column_attr.get(i).m_align).append("\"")
				.append(" type=\"").append(column_attr.get(i).m_type).append("\"")
				.append(" format=\"").append(column_attr.get(i).m_format).append("\"")
				.append("><![CDATA[").append(value).append("]]></cell>");
			}
			xml.append("</row>");
		};
		m_xmlBody = xml.toString();
	}
	

	private void outputExcel(HttpServletResponse res) throws UnsupportedEncodingException {
		
		StringBuffer output_xml = new StringBuffer();
		output_xml.append("<rows profile=\"gray\" filename=\"").append(m_filename +"\">")
		.append(m_xmlHead).append(m_xmlBody)
		.append("</rows>");

		m_filename = URLEncoder.encode(m_filename, "UTF-8").replaceAll("\\+", "%20");
		// m_filename = new String(m_filename.getBytes("KSC5601"),"8859_1");
		if ("excel".equalsIgnoreCase(m_filetype)) {
			(new CustomExcelWriter()).generate(output_xml.toString(), m_filename, m_condition, res);
		}
	}
}
