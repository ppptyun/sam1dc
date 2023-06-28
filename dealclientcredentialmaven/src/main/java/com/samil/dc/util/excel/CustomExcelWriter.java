package com.samil.dc.util.excel;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Blank;
import jxl.write.DateFormat;
import jxl.write.DateFormats;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.DateTime;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class CustomExcelWriter extends BaseWriter {
	private WritableWorkbook wb;
	private WritableSheet conditionSheet;
	private WritableSheet sheet;
	private ExcelColumn[][] cols;
	private int colsNumber = 0;
	private ExcelXmlParser parser;
	
	public int headerOffset = 0;
	public int scale = 6;
	public String pathToImgs = "";//optional, physical path
	public int fontSize = 10;

	String bgColor = "";
	String lineColor = "";
	String headerTextColor = "";
	String scaleOneColor = "";
	String scaleTwoColor = "";
	String gridTextColor = "";
	String watermarkTextColor = "";

	private int cols_stat;
	private int rows_stat;
	RGBColor colors;
	private String watermark = null;
	
	private JSONArray m_condition = null;
	
	@Override
	public void generate(String xml, HttpServletResponse resp) throws IOException {
		parser = new ExcelXmlParser();
		try {
			parser.setXML(xml);
			createExcel(resp);
			setColorProfile();
			headerPrint(parser);
			rowsPrint(parser,resp);
			footerPrint(parser);
			insertHeader(parser,resp);
			insertFooter(parser,resp);
			watermarkPrint(parser);
			outputExcel(resp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void generate(String xml, String filename, HttpServletResponse resp){
		parser = new ExcelXmlParser();
		try {
			parser.setXML(xml);
			createExcel(resp);
			setColorProfile();
			headerPrint(parser);
			rowsPrint(parser,resp);
			footerPrint(parser);
			insertHeader(parser,resp);
			insertFooter(parser,resp);
			watermarkPrint(parser);
			outputExcel(resp, filename);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	
	public void generate(String xml, String filename, JSONArray condition, HttpServletResponse resp){
		parser = new ExcelXmlParser();
		try {
			m_condition = condition;
			parser.setXML(xml);
			createExcel(resp);
			setColorProfile();
			headerPrint(parser);
			rowsPrint(parser,resp);
			footerPrint(parser);
			conditionPrint(parser);
			insertHeader(parser,resp);
			insertFooter(parser,resp);
			watermarkPrint(parser);
			outputExcel(resp, filename);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private void createExcel(HttpServletResponse resp) throws IOException {
		/* Save generated excel to file.
		 * Can be useful for debug output.
		 * */
		/*
		FileOutputStream fos = new FileOutputStream("d:/test.xls");
		wb = Workbook.createWorkbook(fos);
		*/
		
		wb = Workbook.createWorkbook(resp.getOutputStream());
		if(m_condition == null || m_condition.size()==0){
			sheet = wb.createSheet("데이터", 0);
		}else{
			conditionSheet = wb.createSheet("검색 조건", 0);
			sheet = wb.createSheet("데이터", 1);
		}
		colors = new RGBColor();
	}

	private void outputExcel(HttpServletResponse resp, String filename) throws IOException, WriteException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls");
		resp.setHeader("Cache-Control", "max-age=0");
		
		wb.write();
		wb.close();
		
	}
	
	private void outputExcel(HttpServletResponse resp) throws IOException, WriteException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename=grid.xls");
		resp.setHeader("Cache-Control", "max-age=0");
		
		wb.write();
		wb.close();
		
	}

	private void headerPrint(ExcelXmlParser parser) throws RowsExceededException, WriteException, IOException {
		cols = parser.getColumnsInfo("head");
		
		int widths[] = parser.getWidths();
		this.cols_stat = widths.length;
		
		int sumWidth = 0;
		for (int i = 0; i < widths.length; i++) {
			sumWidth += widths[i];
		}
		if (parser.getWithoutHeader() == false) {
			for (int i = 0; i < cols.length; i++) {
				sheet.setRowView(i, 450);
				sheet.getSettings().setVerticalFreeze(i + 1);
				for (int j = 0; j < cols[i].length; j++) {
					sheet.setColumnView(j, widths[j]/scale);
					WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize - 1, WritableFont.BOLD);
					font.setColour(colors.getColor(headerTextColor, wb));
					WritableCellFormat f = new WritableCellFormat (font);
					f.setBackground(colors.getColor(bgColor, wb));
					f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
					f.setVerticalAlignment(VerticalAlignment.CENTRE);
	
					f.setAlignment(Alignment.CENTRE);
					String name = cols[i][j].getName();
					Label label = new Label(j, i, name, f);
					sheet.addCell(label);
					colsNumber = j;
				}
			}
			headerOffset = cols.length;
			for (int i = 0; i < cols.length; i++) {
				for (int j = 0; j < cols[i].length; j++) {
					int cspan = cols[i][j].getColspan();
					if (cspan > 0) {
						sheet.mergeCells(j, i, j + cspan - 1, i);
					}
					int rspan = cols[i][j].getRowspan();
					if (rspan > 0) {
						sheet.mergeCells(j, i, j, i + rspan - 1);
					}
				}
			}
		}
	}

	private void footerPrint(ExcelXmlParser parser) throws RowsExceededException, WriteException, IOException {
		cols = parser.getColumnsInfo("foot");
		if (cols == null) return;
		if (parser.getWithoutHeader() == false) {
			for (int i = 0; i < cols.length; i++) {
				sheet.setRowView(i + headerOffset, 450);
				for (int j = 0; j < cols[i].length; j++) {
					WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD);
					font.setColour(colors.getColor(headerTextColor, wb));
					WritableCellFormat f = new WritableCellFormat (font);
					f.setBackground(colors.getColor(bgColor, wb));
					f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
					f.setVerticalAlignment(VerticalAlignment.CENTRE);

					f.setAlignment(Alignment.CENTRE);
					String name = cols[i][j].getName();
					Label label = new Label(j, i + headerOffset, name, f);
					sheet.addCell(label);
				}
			}
			for (int i = 0; i < cols.length; i++) {
				for (int j = 0; j < cols[i].length; j++) {
					int cspan = cols[i][j].getColspan();
					if (cspan > 0) {
						sheet.mergeCells(j, headerOffset + i, j + cspan - 1, headerOffset + i);
					}
					int rspan = cols[i][j].getRowspan();
					if (rspan > 0) {
						sheet.mergeCells(j, headerOffset + i, j, headerOffset + i + rspan - 1);
					}
				}
			}
		}
		headerOffset += cols.length;
	}

	private void watermarkPrint(ExcelXmlParser parser) throws WriteException {
		if (watermark == null) return;
		
		WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD);
		font.setColour(colors.getColor(watermarkTextColor, wb));
		WritableCellFormat f = new WritableCellFormat (font);
		f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
		f.setVerticalAlignment(VerticalAlignment.CENTRE);

		f.setAlignment(Alignment.CENTRE);
		Label label = new Label(0, headerOffset, watermark , f);
		sheet.addCell(label);
		sheet.mergeCells(0, headerOffset, colsNumber, headerOffset);
	}
	
	private void conditionPrint(ExcelXmlParser parser) throws WriteException{
		JSONObject tmp = null;
		if(m_condition != null){
			for(int i=0; i<m_condition.size(); i++){
				
				WritableFont font = null;
				WritableCellFormat f = null;
				
				Colour bg;
				tmp = (JSONObject) m_condition.get(i);
				
				conditionSheet.setRowView(i, 450);
				
				conditionSheet.setColumnView(0, 100/scale);
				font = new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD, false);
				font.setColour(colors.getColor(gridTextColor, wb));
				f = new WritableCellFormat (font);
				bg = colors.getColor(scaleTwoColor, wb);
				f.setBackground(bg);
				f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
				f.setVerticalAlignment(VerticalAlignment.CENTRE);
			
				f.setAlignment(Alignment.LEFT);
				Label label = new Label(0, i, (String) tmp.get("label"), f);
				conditionSheet.addCell(label);
				
				conditionSheet.setColumnView(1, 200/scale);
				font = new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.NO_BOLD, false);
				font.setColour(colors.getColor(gridTextColor, wb));
				f = new WritableCellFormat (font);
				bg = colors.getColor(scaleOneColor, wb);
				f.setBackground(bg);
				f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
				f.setVerticalAlignment(VerticalAlignment.CENTRE);
				f.setAlignment(Alignment.LEFT);
				if(tmp.get("value") instanceof  JSONArray){
					label = new Label(1, i, StringUtils.join((JSONArray)tmp.get("value"), ", "), f);
				}else{
					label = new Label(1, i, (String) tmp.get("value"), f);	
				}
				conditionSheet.addCell(label);	
			}
		}
	}

	private void rowsPrint(ExcelXmlParser parser, HttpServletResponse resp) throws WriteException, IOException {
		//do we really need them?
		ExcelRow[] rows = parser.getGridContent();
		if (rows == null) return;
		this.rows_stat = rows.length;
		
		for (int i = 0; i < rows.length; i++) {
			ExcelCell[] cells = rows[i].getCells();
			sheet.setRowView(i + headerOffset, 400);
			for (int j = 0; j < cells.length; j++) {
				
				WritableCellFormat f;
				
				// 셀 형식 설정(문자, 숫자, 날짜)
				if("number".equals(cols[0][j].getType())){
					if("#,##0".equals(cols[0][j].getFormat())){
						f = new WritableCellFormat (NumberFormats.FORMAT1);
					}else if("#,##0.00".equals(cols[0][j].getFormat())){
						f = new WritableCellFormat (NumberFormats.FORMAT3);
					}else{
						f = new WritableCellFormat(NumberFormats.DEFAULT);
					}	
				}else{
					//f = new WritableCellFormat (font);
					f = new WritableCellFormat ();
				}
				
				// sets cell font
				WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize, (cells[j].getBold()) ? WritableFont.BOLD : WritableFont.NO_BOLD, (cells[j].getItalic()) ? true : false);
				if ((!cells[j].getTextColor().equals(""))&&(parser.getProfile().equals("full_color")))
					font.setColour(colors.getColor(cells[j].getTextColor(), wb));
				else
					font.setColour(colors.getColor(gridTextColor, wb));
				
				f.setFont(font);
				
				// sets cell background color
				if ((!cells[j].getBgColor().equals(""))&&(parser.getProfile().equals("full_color"))) {
					Colour col = colors.getColor(cells[j].getBgColor(), wb);
					f.setBackground(col);
				} else {
					Colour bg;
					if (i%2 == 1) {
						bg = colors.getColor(scaleTwoColor, wb);
						
					} else {
						bg = colors.getColor(scaleOneColor, wb);
					}
					f.setBackground(bg);
				}

				f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
				f.setVerticalAlignment(VerticalAlignment.CENTRE);

				String al = cells[j].getAlign();
				if (al == "")
					al = cols[0][j].getAlign();
				if (al.equalsIgnoreCase("left")) {
					f.setAlignment(Alignment.LEFT);
				} else {
					if (al.equalsIgnoreCase("right")) {
						f.setAlignment(Alignment.RIGHT);
					} else {
						f.setAlignment(Alignment.CENTRE);
					}
				}
				
				String val = cells[j].getValue();
				if(val == null || "".equals(val.trim())){
					sheet.addCell(new Blank(j, i+headerOffset, f));
				}else{
					if("number".equals(cols[0][j].getType())){
						try{
							sheet.addCell(new Number(j, i + headerOffset, Double.parseDouble(val), f));	
						}catch(NumberFormatException e){
							sheet.addCell(new Label(j, i + headerOffset, val, f));
						}
						
					}else{
						sheet.addCell(new Label(j, i + headerOffset, val, f));
					}
				}
			}
		}
		headerOffset += rows.length;
	}

	private void insertHeader(ExcelXmlParser parser, HttpServletResponse resp) throws IOException, RowsExceededException {
		if (parser.getHeader() == true) {
			sheet.insertRow(0);
			sheet.setRowView(0, 5000);
			//File imgFile = new File(pathToImgs + "/header.png");
			//WritableImage img = new WritableImage(0, 0, cols[0].length, 1, imgFile);
			//sheet.addImage(img);
			headerOffset++;
		}
	}

	private void insertFooter(ExcelXmlParser parser, HttpServletResponse resp) throws IOException, RowsExceededException {
		if (parser.getFooter() == true) {
			sheet.setRowView(headerOffset, 5000);
			File imgFile = new File(pathToImgs + "/footer.png");
			WritableImage img = new WritableImage(0, headerOffset, cols[0].length, 1, imgFile);
			sheet.addImage(img);
		}
	}

	public int getColsStat() {
		return this.cols_stat;
	}
	
	public int getRowsStat() {
		return this.rows_stat;
	}

	private void setColorProfile() {
		String profile = parser.getProfile();
		if ((profile.equalsIgnoreCase("color"))||profile.equalsIgnoreCase("full_color")) {
			bgColor = "D1E5FE";
			lineColor = "A4BED4";
			headerTextColor = "000000";
			scaleOneColor = "FFFFFF";
			scaleTwoColor = "E3EFFF";
			gridTextColor = "000000";
			watermarkTextColor = "8b8b8b";
		} else {
			if (profile.equalsIgnoreCase("gray")) {
				bgColor = "E3E3E3";
				lineColor = "B8B8B8";
				headerTextColor = "000000";
				scaleOneColor = "FFFFFF";
				scaleTwoColor = "EDEDED";
				gridTextColor = "000000";
				watermarkTextColor = "8b8b8b";
			} else {
				bgColor = "FFFFFF";
				lineColor = "000000";
				headerTextColor = "000000";
				scaleOneColor = "FFFFFF";
				scaleTwoColor = "FFFFFF";
				gridTextColor = "000000";
				watermarkTextColor = "000000";
			}
		}
	}
	
	public void setWatermark(String mark) {
		watermark = mark;	
	}
	
	public void setFontSize(int fontsize) {
		this.fontSize = fontsize;
	}
}

