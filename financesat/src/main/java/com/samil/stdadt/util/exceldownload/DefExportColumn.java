package com.samil.stdadt.util.exceldownload;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;

import com.samil.stdadt.util.Constant;

public class DefExportColumn {
	private String code;
	private List<String> title;
	private int type;
	private String format;
	private CellStyle style;
	
	public DefExportColumn(String code, String title) {this._init(code, title, Constant.COLUMN_TYPE.STRING, null);}
	public DefExportColumn(String code, List<String> title) {this._init(code, title, Constant.COLUMN_TYPE.STRING, null);}
	
	public DefExportColumn(String code, String title, int type) {this._init(code, title, type, null);}
	public DefExportColumn(String code, List<String> title, int type) {this._init(code, title, type, null);}
	
	public DefExportColumn(String code, String title, CellStyle style) {this._init(code, title, Constant.COLUMN_TYPE.STRING, style);}
	public DefExportColumn(String code, List<String> title, CellStyle style) {this._init(code, title, Constant.COLUMN_TYPE.STRING, style);}
	
	public DefExportColumn(String code, String title, int type, CellStyle style) {this._init(code, title, type, style);}
	public DefExportColumn(String code, List<String> title, int type, CellStyle style) {this._init(code, title, type, style);}

	
	private void _init(String code, String title, int type, CellStyle style) {
		_init(code, Arrays.asList(title), type, style);
	}
	private void _init(String code, List<String> title, int type, CellStyle style) {
		this.code	= code;
		this.title 	= title;
		this.type 	= type;
		this.style 	= style;
	}
	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getFormat() {
		return format;
	}

	public DefExportColumn setFormat(String format) {
		this.format = format;
		return this;
	}

	public String getTitle() {
		return title.get(0);
	}
	
	public String getTitle(int row) {
		return title.get(row);
	}
	
	public int getHeaderRowCount() {
		return title.size();
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}
	
	@Override
	public boolean equals(Object o) {
		if( o instanceof String) {
			String tmp = (String) o;
			return this.code.equals(tmp);
		}else if( o instanceof DefExportColumn) {
			DefExportColumn tmp = (DefExportColumn) o;
			return this.code.equals(tmp.code);
		}else {
			return false;
		}
	}

	public CellStyle getStyle() {
		return style;
	}

	public void setStyle(CellStyle style) {
		this.style = style;
	}
}
