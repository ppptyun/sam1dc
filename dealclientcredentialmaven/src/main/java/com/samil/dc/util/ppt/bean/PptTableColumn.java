package com.samil.dc.util.ppt.bean;

import org.docx4j.dml.STTextAlignType;

public class PptTableColumn {
	private String name;
	private long width;
	private double widthP;
	private STTextAlignType align = STTextAlignType.CTR;
	
	public PptTableColumn(String name, long width){
		this.name = name;	
		this.width = width;
	}
	public PptTableColumn(String name, double widthP){
		this.name = name;
		this.widthP = widthP;
	}
	
	public PptTableColumn(String name, long width, STTextAlignType align){
		this.name = name;	
		this.width = width;
		this.setAlign(align);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getWidth() {
		return width;
	}
	public void setWidth(long width) {
		this.width = width;
	}
	public double getWidthP() {
		return widthP;
	}
	public void setWidthP(double widthP) {
		this.widthP = widthP;
	}
	public STTextAlignType getAlign() {
		return align;
	}
	public void setAlign(STTextAlignType align) {
		this.align = align;
	}
}
