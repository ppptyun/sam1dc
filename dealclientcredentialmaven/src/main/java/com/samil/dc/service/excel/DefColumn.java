package com.samil.dc.service.excel;

public class DefColumn {

	private boolean used = false;
	private boolean required = false;
	private String code = "";
	private String title = "";
	private int type = ExcelHelper.COLUMN_TYPE_TEXT;
	private String[] items = new String[]{};
	
	public DefColumn(boolean used) {
		this.used = used;
	}
	
	public DefColumn(String code, String title) {
		this.used = true;
		this.code = code;
		this.title = title;
	}
	
	public DefColumn(String code, String title, boolean required) {
		this.used = true;
		this.required = required;
		this.code = code;
		this.title = title;
	}
	
	public DefColumn(String code, String title, int type) {
		this.used = true;
		this.code = code;
		this.title = title;
		this.type = type;
	}
	
	public DefColumn(String code, String title, int type, boolean required) {
		this.used = true;
		this.required = required;
		this.code = code;
		this.title = title;
		this.type = type;
	}
	
	public DefColumn(String code, String title, String[] items) {
		this.used = true;
		this.code = code;
		this.title = title;
		this.type = ExcelHelper.COLUMN_TYPE_COMCD;
		this.items = items;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}
}
