package com.samil.stdadt.util.excel;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ExcelCell {

	private String value = "";
	private String bgColor = "";
	private String textColor = "";
	private String bold = "";
	private String italic = "";
	private String align = "";
	private String type = "";
	private String format = "";

	public void parse(Node parent) {
		//(REV)we may have empty cell, which will return null for getFirstChild
		if(parent.getFirstChild()==null){
			value = "";
		}else{
			value = parent.getFirstChild().getNodeValue();			
		}
		
		Element el = (Element) parent;
		bgColor = (el.hasAttribute("bgColor")) ? el.getAttribute("bgColor") : "";
		textColor = (el.hasAttribute("textColor")) ? el.getAttribute("textColor") : "";
		bold = (el.hasAttribute("bold")) ? el.getAttribute("bold") : "";
		italic = (el.hasAttribute("italic")) ? el.getAttribute("italic") : "";
		align = (el.hasAttribute("align")) ? el.getAttribute("align") : "";
		type = (el.hasAttribute("type")) ? el.getAttribute("type") : "";
		format = (el.hasAttribute("format")) ? el.getAttribute("format") : "";
	}

	public String getValue() {
		return value;
	}

	public String getBgColor() {
		return bgColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public Boolean getBold() { //(REV)why we store string , and not boolean?
		if (bold.equals("bold"))
			return true;
		else
			return false;
	}

	public Boolean getItalic() { //(REV)why we store string , and not boolean?
		if (italic.equals("italic"))
			return true;
		else
			return false;
	}

	public String getAlign() {
		return align;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}