package com.samil.dc.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.util.CustomRuntimeException;

public class SQLManagement {
	
	private String m_sql;
	private LinkedHashMap<String, SQLBean> m_subSql;	// dynamic 쿼리 생성을 위함.
	private boolean isGenerate;
	private boolean isBatch;
	private ArrayList<Object> m_param = new ArrayList<Object>();
	private ArrayList<ArrayList<Object>> m_paramBatch;
	private LinkedHashMap<String, String> m_sqlReplacement;// SQL 삽입하기 위한 것
	private LinkedHashMap<String, Object> m_valueReplacement;// Key에 맵핑되는 Value값을 Replace하기 위한 것
	private ArrayList<LinkedHashMap<String, Object>> m_valueReplacementBatch;	// batch
	
	public SQLManagement(String sql){init(sql);}
	
	public void init(String _sql){
		m_sql					= _sql;
		m_subSql 				= new LinkedHashMap<String, SQLBean>();
		isGenerate 				= false;
		isBatch 				= false;
		m_param 				= new ArrayList<Object>();
		m_paramBatch			= new ArrayList<ArrayList<Object>> ();
		m_sqlReplacement 		= new LinkedHashMap<String, String>(); 
		m_valueReplacement 		= new LinkedHashMap<String, Object>();
		m_valueReplacementBatch = new ArrayList<LinkedHashMap<String, Object>>();
	}
	
	// Dynamic Query를 위한 것
	// SQL을 삽입
	public void addSQLReplacement(String key, String value){
		m_sqlReplacement.put(key, value);
		isGenerate = false;
	}
	
	// Key에 맵핑되는 Value 추가
	public void addValueReplacement(String key, Object value){
		m_valueReplacement.put(key, value);
		isGenerate = false;
	}
	
	public void addSubSQLReplacement(String key, String subSql, String subKey, Object value){
		if(m_sqlReplacement.containsKey(key)){
			throw CustomRuntimeException.DUPLICATE_KEY;
		}else{
			if(!m_subSql.containsKey(key)){
				m_subSql.put(key, new SQLBean());
			}
			m_subSql.get(key).addSql(subSql);
			m_subSql.get(key).addValue(subKey, value);	
		}
	}
	
	public void addSubSQLReplacement(String key, String subSql, Map<String, Object> valueMap){
		if(m_sqlReplacement.containsKey(key)){
			throw CustomRuntimeException.DUPLICATE_KEY;
		}else{
			if(!m_subSql.containsKey(key)){
				m_subSql.put(key, new SQLBean());
			}
			m_subSql.get(key).addSql(subSql);
			
			Iterator<String> itr = valueMap.keySet().iterator();
			while(itr.hasNext()){
				String subKey = itr.next();
				m_subSql.get(key).addValue(subKey, valueMap.get(subKey));	
			}
		}
	}
	
	public void addSubSQLReplacement(String key, String subSql){
		if(m_sqlReplacement.containsKey(key)){
			throw CustomRuntimeException.DUPLICATE_KEY;
		}else{
			if(!m_subSql.containsKey(key)){
				m_subSql.put(key, new SQLBean());
			}
			m_subSql.get(key).addSql(subSql);	
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void addBatch(){
		m_valueReplacementBatch.add((LinkedHashMap<String, Object>) m_valueReplacement.clone());
		isGenerate 	= false;
		isBatch		= true;
	}
	
	
	// 세팅된 값으로 SQL과 맵핑되는 파라메터를 생성 (m_sql, m_param)
	public void generate(){
		if(isBatch){
			generateBatch();
		}else{
			generateCommon();
		}
	}
	
	// 세팅된 값으로 SQL과 맵핑되는 파라메터를 생성 (m_sql, m_param)
	private void generateCommon(){
		Pattern p = Pattern.compile(SQLConstant.PATTERN_SQL_PARAM);
		Matcher m = null;
		
		Iterator<String> itr = null;
		String key = "";
		
		// Sub SQL 처리
		itr = m_subSql.keySet().iterator();
		while(itr.hasNext()){
			key = (String) itr.next();
			// Main SQL과 Sub SQL 키값 중복을 없
			if(!m_sqlReplacement.containsKey(key) && m_subSql.containsKey(key)){
				// Sub SQL 추가
				m_sql = m_sql.replaceAll("<#"+key+"#>", m_subSql.get(key).getSql());
				// Sub SQL의 Value 값을 추가
				Map<String, Object> subValue = m_subSql.get(key).getValue();
				Iterator<String> subItr = subValue.keySet().iterator();
				while(subItr.hasNext()){
					String subKey = (String) subItr.next();
					m_valueReplacement.put(subKey, subValue.get(subKey));
				}
			}
		}
		
		// SQL 변환
		m = p.matcher(m_sql);
		itr = m_sqlReplacement.keySet().iterator();
		while(itr.hasNext()){
			key = (String) itr.next();
			m_sql = m_sql.replaceAll("<#"+key+"#>", m_sqlReplacement.get(key));
		}
		
		// Value 변환
		m = p.matcher(m_sql);
		Object tmp = null;
		while (m.find()) {
			key = sepcialCharacter_replace(m.group());
			tmp = m_valueReplacement.get(key);
			if(tmp != null){
				m_sql = m_sql.replace("<#"+key+"#>", "?");
				m_param.add(tmp);
			}	
		}
		
		
		isGenerate = true;
	}
	
	private void generateBatch(){
		Pattern p = Pattern.compile(SQLConstant.PATTERN_SQL_PARAM);
		Matcher m = null;
		
		String key = "";

		for(int i=0; i<m_valueReplacementBatch.size(); i++){
			ArrayList<Object> tmpParam = new ArrayList<Object>();
			Object tmp = null;
			
			m = p.matcher(m_sql);
			while (m.find()) {
				key = sepcialCharacter_replace(m.group());
				tmp = m_valueReplacementBatch.get(i).get(key);
				tmpParam.add(tmp);
			}
			m_paramBatch.add(tmpParam);
		}
		
		m = p.matcher(m_sql);
		while (m.find()) {
			m_sql = m_sql.replace(m.group(), "?");
		}
		
		isGenerate = true;
	}
	
	
	//
	public void generate(boolean isDelete){
		generate();
		
		if(isDelete){
			Pattern p = Pattern.compile(SQLConstant.PATTERN_SQL_PARAM);
			Matcher m = p.matcher(m_sql);
			m_sql = m.replaceAll("");
		}
	}
	
	// 전체 Row 개수를 출력하는 SQL 리턴
	public String getRowCountSql() {
		if (isGenerate)
			return "SELECT count(*) CNT FROM (" + m_sql + ") TBL";
		else
			return null;
	}
	
	// 특정 범위의 데이터만 출력할 수 있는 SQL 리턴
	// select에 반드시 포함 되어야 함.>> (Row_Number() OVER (ORDER BY [정렬할 컬럼명] ASC) - 1) ROWNUM
	public String getRangeSql(int posStart, int count){
		return "SELECT * FROM (" + m_sql + ") TMP WHERE " +  posStart + " <= ROWNUM AND ROWNUM < " + (posStart + count) + "";
	}
	
	// 특정 범위의 데이터만 출력할 수 있는 SQL 리턴 오라클 용
	// select에 반드시 포함 되어야 함.>> ROWNUM RNUM
	public String getRangeSqlOracle(int posStart, int count){
		String retVal = "SELECT ROWNUM TEMP_RNUM, TMP.* FROM (" + m_sql + ") TMP WHERE ROWNUM <= " + (posStart + count);
		retVal = "SELECT * FROM (" + retVal + ") WHERE TEMP_RNUM > " + posStart;
		
		return retVal;
	}
	
	// SQL 리턴
	public String getSql() {
		if (isGenerate)
			return m_sql;
		else
			return null;
	}
	
	// value 값에 맵핑되는 파라메터 리턴
	public ArrayList<Object> getParameter() {
		if (isGenerate)
			return m_param;
		else
			return null;
	}
	
	public ArrayList<ArrayList<Object>> getParameterBatch() {
		if (isGenerate)
			return m_paramBatch;
		else
			return null;
	}
	
	//-----------------------------------------
	// <# #>을 제거하는 함수
	private String sepcialCharacter_replace(String str) {
		str = StringUtils.replaceChars(str, "<>#", "");

		if (str.length() < 1) {
			return null;
		}

		return str;
	}
}
