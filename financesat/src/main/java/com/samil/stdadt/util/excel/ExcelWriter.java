package com.samil.stdadt.util.excel;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.samil.stdadt.comm.vo.ApplicationConfigVO;

import jxl.Cell;
import jxl.FormulaCell;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.formula.FormulaException;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
 
@Controller
@RequestMapping("/excel")
public class ExcelWriter extends BaseWriter {
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	private WritableWorkbook wb;
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
	
	private String writerType = "";
	private String filepath;
	private String filename;
	
	private int adjHeaderXAxis = 0;
	private int adjHeaderYAxis = 0;
	private int adjRowXAxis = 0;
	private int adjRowYAxis = 0;
	private int[] adjHeadSkip;
	private int[] adjColSkip;
	private boolean isSkip = false;
	private WritableCellFormat nf1 = null;
	private WritableCellFormat nf2 = null;
	
	@RequestMapping("/writer")
	public void writer(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		resp.setCharacterEncoding( "UTF-8" );
		req.setCharacterEncoding( "UTF-8" );
		
		String xml = req.getParameter("grid_xml");
		String writerType = req.getParameter("writerType");
		String prjtCd = StringUtils.defaultIfBlank(req.getParameter("prjtCd"), "");
		String prjtNm = StringUtils.defaultIfBlank(req.getParameter("prjtNm"), "");
		
		filename = req.getParameter("filename");
		
		xml = URLDecoder.decode(xml, "UTF-8");
		filename = URLDecoder.decode(filename, "UTF-8");

		if(writerType != null && !writerType.equals("") ) {
			String cfilepath = req.getSession().getServletContext().getRealPath(appConfig.getExcelTemplatePath());
			
			(new ExcelWriter()).budgetGenerate(xml, resp, cfilepath, filename, prjtCd, prjtNm);
		}else {
			(new ExcelWriter()).generate(xml, resp);	
		}
	}

	// 2020.05.11
	@RequestMapping("/writerV3")
	public void writerV3(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		resp.setCharacterEncoding( "UTF-8" );
		req.setCharacterEncoding( "UTF-8" );
		
		String xml = req.getParameter("grid_xml");
		String writerType = req.getParameter("writerType");
		String prjtCd = StringUtils.defaultIfBlank(req.getParameter("prjtCd"), "");
		String prjtNm = StringUtils.defaultIfBlank(req.getParameter("prjtNm"), "");
		
		filename = req.getParameter("filename");
		
		xml = URLDecoder.decode(xml, "UTF-8");
		filename = URLDecoder.decode(filename, "UTF-8");

		if(writerType != null && !writerType.equals("") ) {
			String cfilepath = req.getSession().getServletContext().getRealPath(appConfig.getExcelTemplatePath());
			(new ExcelWriter()).budgetGenerateV3(xml, resp, cfilepath, filename, prjtCd, prjtNm);
			
			
		}else {
			(new ExcelWriter()).generate(xml, resp);	
		}
	}
	
	
	public void generate(String xml, HttpServletResponse resp){
		parser = new ExcelXmlParser();
		try {
			adjHeadSkip = new int[1];
			adjHeadSkip[0] = 999;
			
			adjColSkip = new int[1];
			adjColSkip[0] = 999;
			
			nf1 = new WritableCellFormat(new NumberFormat("#,##0.00"));
			nf2 = new WritableCellFormat(new NumberFormat("#,##0.000"));
			
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

	public void budgetGenerate(String xml, HttpServletResponse resp, String cfilepath, String cfilename, String prjtCd, String prjtNm){
		parser = new ExcelXmlParser();
		filepath = cfilepath;
		filename = cfilename;
		try {
			adjHeaderXAxis = 0;
			adjHeaderYAxis = 1;
			
			adjRowXAxis = 0;
			adjRowYAxis = 2;
			
			adjHeadSkip = new int[1];
			adjHeadSkip[0] = 999;
			
			adjColSkip = new int[2];
			adjColSkip[0] = 7;
			adjColSkip[1] = 8;
			
			nf1 = new WritableCellFormat(new NumberFormat("#,##0.00"));
			nf2 = new WritableCellFormat(new NumberFormat("#,##0.000"));
			
			parser.setXML(xml);
			createTemplate(resp);
			setColorProfile();
			
			// 프로젝트 코드 추가
			Label prjtCdLabel = new Label(0, 0, prjtCd);
			Label prjtNmLabel = new Label(1, 0, prjtNm);
			sheet.addCell(prjtCdLabel);
			sheet.addCell(prjtNmLabel);
			
			headerPrint(parser);
			rowsPrint(parser,resp);
			
			// Set Custom Sheet Format
			SheetSettings ss = sheet.getSettings();
			ss.setHorizontalFreeze(8);
			ss.setVerticalFreeze(4);
			
			sheet.setRowView(0, 30*55);
			
			outputExcel(resp);
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
		sheet = wb.createSheet("First Sheet", 0);
		colors = new RGBColor();
	}

	public void createTemplate(HttpServletResponse resp) throws IOException, BiffException, URISyntaxException{
		//URL mPath = new URL(filepath + "SAT_BUDGET.xls");
		/*InputStream mObj = mPath.openStream();*/
		File mObj = new File(filepath, "SAT_BUDGET.xls");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmSS");
		java.util.Date today = new java.util.Date();
		String dateStr = formatter.format(today);
		
		WorkbookSettings settings = new WorkbookSettings();
		settings.setRationalization(false);
		
		Workbook mTmpWorkbook = Workbook.getWorkbook(mObj);
		wb = Workbook.createWorkbook(resp.getOutputStream(), mTmpWorkbook, settings);
		sheet = wb.getSheet(0);
		colors = new RGBColor();
	}

	private void outputExcel(HttpServletResponse resp) throws IOException, WriteException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmSS");
		java.util.Date today = new java.util.Date();
		String dateStr = formatter.format(today);
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename="+(filename == null || filename.equals("")?"grid":URLEncoder.encode(filename, "UTF-8"))+ "_" + dateStr + ".xls");
		resp.setHeader("Cache-Control", "max-age=0");
		wb.write();
		wb.close();
	}

	private void headerPrint(ExcelXmlParser parser) throws RowsExceededException, WriteException, IOException {
		cols = parser.getColumnsInfo("head");
		int k;
		
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
					
					if(cols[i][j].getName().indexOf("도움말") > 0) {
						name = cols[i][j].getName().substring(0, cols[i][j].getName().indexOf("도움말"));	
					}
					
					for(k = 0; k < adjHeadSkip.length; k++) {
						if(adjHeadSkip[k] == j) {
							isSkip = true;
							break;
						}
					}
					
					if(!isSkip) {
						if(adjHeadSkip[adjHeadSkip.length-1] < j) {
							Label label = new Label(j+adjHeaderXAxis-adjHeadSkip.length, i+adjHeaderYAxis, name, f);
							sheet.addCell(label);
						}else {
							Label label = new Label(j+adjHeaderXAxis, i+adjHeaderYAxis, name, f);
							sheet.addCell(label);
						}
					}else {
						isSkip = false;
					}

					colsNumber = j;
				}
			}
			headerOffset = cols.length;
			for (int i = 0; i < cols.length; i++) {
				for (int j = 0; j < cols[i].length; j++) {
					int cspan = cols[i][j].getColspan();
					
					for(k = 0; k < adjHeadSkip.length; k++) {
						if(adjHeadSkip[k] == j) {
							isSkip = true;
							break;
						}
					}
					
					if (cspan > 0) {
						if(!isSkip) {
							if(adjHeadSkip[adjHeadSkip.length-1] < j) {
								sheet.mergeCells(j+adjHeaderXAxis-adjHeadSkip.length, i+adjHeaderYAxis, j+adjHeaderXAxis + cspan - 1-adjHeadSkip.length, i);
							}else {
								sheet.mergeCells(j+adjHeaderXAxis, i+adjHeaderYAxis, j+adjHeaderXAxis + cspan - 1, i);
							}
						}else {
							isSkip = false;
						}
					}
					int rspan = cols[i][j].getRowspan();
					
					for(int g = 0; g < cols.length; g++) {
						if(rspan <= 0 && cols.length != rspan && i < g && cols[g][j].getName().equals("")) {
							rspan = g-i+1;
							break;
						}
					}
					
					if (rspan > 0 && cols.length == rspan && filepath == null) {
						if(!isSkip) {
							if(adjHeadSkip[adjHeadSkip.length-1] < j) {
								sheet.mergeCells(j+adjHeaderXAxis-adjHeadSkip.length, i+adjHeaderYAxis, j+adjHeaderXAxis-adjHeadSkip.length, i+adjHeaderYAxis + rspan - 1);
							}else {
								sheet.mergeCells(j+adjHeaderXAxis, i+adjHeaderYAxis, j+adjHeaderXAxis, i+adjHeaderYAxis + rspan - 1);
							}
						}else {
							isSkip = false;
						}
					}
				}
			}
		}
	}

	private void footerPrint(ExcelXmlParser parser) throws RowsExceededException, WriteException, IOException {
		ExcelColumn[][] fcols = parser.getColumnsInfo("foot");
		if (fcols == null) return;
		if (parser.getWithoutHeader() == false) {
			for (int i = 0; i < fcols.length; i++) {
				sheet.setRowView(i + headerOffset, 450);
				for (int j = 0; j < fcols[i].length; j++)
				{
					String value = fcols[i][j].getName();
					String type = cols[0][j].getType();
					
					if (type.equals("ron"))
						setSheetValue(value,i,j,cols[0][j].getType(),null);
					else
					{
						WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD);
						font.setColour(colors.getColor(headerTextColor, wb));
						WritableCellFormat f = new WritableCellFormat (font);
//						f.setBackground(colors.getColor(bgColor, wb));
//						f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
//						f.setVerticalAlignment(VerticalAlignment.CENTRE);
	
						f.setAlignment(Alignment.CENTRE);
						
						Label label = new Label(j, i + headerOffset, value, f);
						sheet.addCell(label);
					}
				}
			}
			for (int i = 0; i < fcols.length; i++) {
				for (int j = 0; j < fcols[i].length; j++) {
					int cspan = fcols[i][j].getColspan();
					if (cspan > 0) {
						sheet.mergeCells(j, headerOffset + i, j + cspan - 1, headerOffset + i);
					}
					int rspan = fcols[i][j].getRowspan();
					if (rspan > 0) {
						sheet.mergeCells(j, headerOffset + i, j, headerOffset + i + rspan - 1);
					}
				}
			}
		}
		headerOffset += fcols.length;
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

	private void rowsPrint(ExcelXmlParser parser, HttpServletResponse resp) throws WriteException, IOException {
		//do we really need them?
		ExcelRow[] rows = parser.getGridContent();
		if (rows == null) return;
		this.rows_stat = rows.length;
		
		for (int i = 0; i < rows.length; i++) {
			ExcelCell[] cells = rows[i].getCells();
			sheet.setRowView(i + headerOffset, 400);
			
			for (int j = 0; j < cells.length; j++)
			{
				String value = cells[j].getValue().toString();
				setSheetValue(value,i,j,cols[0][j].getType(),cells[j]);
			}
		}
		headerOffset += rows.length;
	}

	private void setSheetValue(String value,int i, int j, String type, ExcelCell cell) throws RowsExceededException, WriteException
	{
		int k;
		isSkip = false;
		for(k = 0; k < adjColSkip.length; k++) {
			if(adjColSkip[k] == j) {
				isSkip = true;
				break;
			}
		}
		
		if (type.equals("ron"))
		{
			if (value.equals(" ") || value.equals(" ") || value.equals(""))
				value = "0";
			
			String ss = value.replace(",","");
			ss = ss.replace("%","");
			ss = ss.replace(" ","");
			
			double val = 0;
			if (!ss.equals(" ") || !ss.equals(" ") || !ss.equals("")) {
				val = Double.parseDouble(ss);
			}
			
			if(!isSkip) {
				if((ss.indexOf(".") > 0?ss.length()-ss.indexOf(".")-1:0) > 2) {
					Number label = new Number(j+adjRowXAxis, i+adjRowYAxis + headerOffset, val, nf2);
					sheet.addCell(label);	
				}else {
					Number label = new Number(j+adjRowXAxis, i+adjRowYAxis + headerOffset, val, nf1);
					sheet.addCell(label);
				}
			}else {
				isSkip = false;
				if(sheet.getCell(j+adjRowXAxis, i+adjRowYAxis + headerOffset).getType().toString().equals("Numerical Formula")) {
					FormulaCell fc = (FormulaCell)sheet.getCell(j+adjRowXAxis, i+adjRowYAxis + headerOffset);
					try {
						Formula fm = new Formula(j+adjRowXAxis, i+adjRowYAxis + headerOffset, fc.getFormula(), nf1);
					    sheet.addCell(fm);
					} catch (FormulaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					
				}
			}
		}
		else
		{
			if(!isSkip) {
				Label label = new Label(j+adjRowXAxis, i+adjRowYAxis + headerOffset, value);
				sheet.addCell(label);
			}else {
				isSkip = false;
			}
		}
		
		// 수식 틀어짐으로 인해 한번씩 다시 써줌
		for(int r=1; r <=sheet.getRows(); r++) {
			for(int c=0; c < adjColSkip.length; c++) {
				Cell cl = sheet.getCell(adjColSkip[c], r);
				
				if(cl.getType().toString().equals("Numerical Formula")) {
					try {
						Formula fl = new Formula(adjColSkip[c], r, ((FormulaCell)cl).getFormula(), nf1);
						sheet.addCell(fl);
					} catch (FormulaException e) {
						e.printStackTrace();
					}
				}
			};
		};
	}
	
	private void insertHeader(ExcelXmlParser parser, HttpServletResponse resp) throws IOException, RowsExceededException {
		if (parser.getHeader() == true) {
			sheet.insertRow(0);
			sheet.setRowView(0, 5000);
			File imgFile = new File(pathToImgs + "/header.png");
			WritableImage img = new WritableImage(0, 0, cols[0].length, 1, imgFile);
			sheet.addImage(img);
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
	
	// ---------------------------------------------------------------------------------------------------------------
	// 2020.05.11 V3 관련 메소드들 추가
	// ---------------------------------------------------------------------------------------------------------------
	public void budgetGenerateV3(String xml, HttpServletResponse resp, String cfilepath, String cfilename, String prjtCd, String prjtNm){
		parser = new ExcelXmlParser();
		filepath = cfilepath;
		filename = cfilename;
		try {
			adjHeaderXAxis = 0;
			adjHeaderYAxis = 1;
			
			adjRowXAxis = 0;
			adjRowYAxis = 2;
			
			adjHeadSkip = new int[1];
			adjHeadSkip[0] = 999;
			
			adjColSkip = new int[2];
			adjColSkip[0] = 9;
			adjColSkip[1] = 10;
			
			nf1 = new WritableCellFormat(new NumberFormat("#,##0.00"));
			nf2 = new WritableCellFormat(new NumberFormat("#,##0.000"));
			
			parser.setXML(xml);
			
			// 2020.05.11
			createTemplateV3(resp);
			setColorProfile();
			
			// 프로젝트 코드 추가
			Label prjtCdLabel = new Label(0, 0, prjtCd);
			Label prjtNmLabel = new Label(1, 0, prjtNm);
			sheet.addCell(prjtCdLabel);
			sheet.addCell(prjtNmLabel);
			
			headerPrint(parser);
			rowsPrint(parser,resp);
			
			// Set Custom Sheet Format
			SheetSettings ss = sheet.getSettings();
			ss.setHorizontalFreeze(11); // 2020.05.11 틀고정
			ss.setVerticalFreeze(4);
			
			sheet.setRowView(0, 30*55);
			
			outputExcel(resp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}	
		
	// 2020.05.11
	public void createTemplateV3(HttpServletResponse resp) throws IOException, BiffException, URISyntaxException{
		File mObj = new File(filepath, "SAT_BUDGET_V3.xls");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmSS");
		java.util.Date today = new java.util.Date();
		String dateStr = formatter.format(today);
		
		WorkbookSettings settings = new WorkbookSettings();
		settings.setRationalization(false);
		
		Workbook mTmpWorkbook = Workbook.getWorkbook(mObj);
		wb = Workbook.createWorkbook(resp.getOutputStream(), mTmpWorkbook, settings);
		sheet = wb.getSheet(0);
		colors = new RGBColor();
	}
		
	/* ===========================================================
	[시작] 20230207 Budget v4
	=========================================================== */	
	@RequestMapping("/writerV4")
	public void writerV4(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		resp.setCharacterEncoding( "UTF-8" );
		req.setCharacterEncoding( "UTF-8" );
		
		String xml = req.getParameter("grid_xml");
		String writerType = req.getParameter("writerType");
		String prjtCd = StringUtils.defaultIfBlank(req.getParameter("prjtCd"), "");
		String prjtNm = StringUtils.defaultIfBlank(req.getParameter("prjtNm"), "");
		// Budget 입력 주기 추가
		String budgetInputCycle = StringUtils.defaultIfBlank(req.getParameter("budgetInputCycle"), "");
		
		filename = req.getParameter("filename");
		
		xml = URLDecoder.decode(xml, "UTF-8");
		filename = URLDecoder.decode(filename, "UTF-8");

		if(writerType != null && !writerType.equals("") ) {
			String cfilepath = req.getSession().getServletContext().getRealPath(appConfig.getExcelTemplatePath());
			(new ExcelWriter()).budgetGenerateV4(xml, resp, cfilepath, filename, prjtCd, prjtNm, budgetInputCycle);					
		}else {
			(new ExcelWriter()).generate(xml, resp);	
		}
	}
		
	public void budgetGenerateV4(String xml, HttpServletResponse resp, String cfilepath, String cfilename, String prjtCd, String prjtNm, String budgetInputCycle){
		parser = new ExcelXmlParser();
		filepath = cfilepath;
		filename = cfilename;
		try {
			adjHeaderXAxis = 0;
			adjHeaderYAxis = 1;
			
			adjRowXAxis = 0;
			adjRowYAxis = 2;
			
			adjHeadSkip = new int[1];
			adjHeadSkip[0] = 999;
			
			adjColSkip = new int[2];
			adjColSkip[0] = 9;
			adjColSkip[1] = 10;
			
			nf1 = new WritableCellFormat(new NumberFormat("#,##0.00"));
			nf2 = new WritableCellFormat(new NumberFormat("#,##0.000"));
			
			parser.setXML(xml);
			
			createTemplateV4(resp);
			setColorProfile();
			
			// 프로젝트 코드 추가
			Label prjtCdLabel = new Label(0, 0, prjtCd);
			Label prjtNmLabel = new Label(1, 0, prjtNm);
			
			// Budget 입력 주기 추가
			Label budgetInputCycleLabel = new Label(10, 0, budgetInputCycle); //셀 K1		
			sheet.addCell(budgetInputCycleLabel);
			
			sheet.addCell(prjtCdLabel);
			sheet.addCell(prjtNmLabel);
			
			headerPrint(parser);
			rowsPrint(parser,resp);
			
			// Set Custom Sheet Format
			SheetSettings ss = sheet.getSettings();
			ss.setHorizontalFreeze(11); // 2020.05.11 틀고정
			ss.setVerticalFreeze(4);
			
			sheet.setRowView(0, 30*55);
			
			outputExcel(resp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}	
	
	public void createTemplateV4(HttpServletResponse resp) throws IOException, BiffException, URISyntaxException{
		File mObj = new File(filepath, "SAT_BUDGET_V4.xls");
		
		//jxl에서 지원하지 않음.
		//File mObj = new File(filepath, "SAT_BUDGET_V4.xlsx");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmSS");
		java.util.Date today = new java.util.Date();
		String dateStr = formatter.format(today);
		
		WorkbookSettings settings = new WorkbookSettings();
		settings.setRationalization(false);
		
		Workbook mTmpWorkbook = Workbook.getWorkbook(mObj);
		wb = Workbook.createWorkbook(resp.getOutputStream(), mTmpWorkbook, settings);
		sheet = wb.getSheet(0);
		colors = new RGBColor();
	}	
	
	/* ===========================================================
	[종료] 20230207 Budget v4
	=========================================================== */	

	
}
