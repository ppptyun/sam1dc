package com.samil.stdadt.util.excel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Controller
@RequestMapping("/excel")
public class ExcelWriterV4 {
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	private ExcelColumn[][] cols;
	private ExcelXmlParser parser;
	
	public int headerOffset = 0;
	public int scale = 40;
	
	public String pathToImgs = "";//optional, physical path
	public int fontSize = 10;

	XSSFColor bgColor = null;
	XSSFColor lineColor = null;
	XSSFColor headerTextColor = null;
	XSSFColor scaleOneColor = null;
	XSSFColor scaleTwoColor = null;
	XSSFColor gridTextColor = null;
	XSSFColor watermarkTextColor = null;

	private int cols_stat;
	private int rows_stat;
	RGBColor colors;

	private String filepath;
	private String filename;
	
	private int adjHeaderXAxis = 0;
	private int adjHeaderYAxis = 0;
	private int adjRowXAxis = 0;
	private int adjRowYAxis = 0;
	private int[] adjHeadSkip;
	private int[] adjColSkip;
	private boolean isSkip = false;

	private void outputExcel(HttpServletResponse resp) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmSS");
		java.util.Date today = new java.util.Date();
		String dateStr = formatter.format(today);
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename="+(filename == null || filename.equals("")?"grid":URLEncoder.encode(filename, "UTF-8"))+ "_" + dateStr + ".xlsx");
		resp.setHeader("Cache-Control", "max-age=0");
		wb.write(resp.getOutputStream());
		wb.close();
	}

	private void headerPrint(ExcelXmlParser parser) throws IOException {
		cols = parser.getColumnsInfo("head");
		int k;
		
		int widths[] = parser.getWidths();
		this.cols_stat = widths.length;

		if (parser.getWithoutHeader() == false) {
			for (int i = 0; i < cols.length; i++) {
				XSSFRow hRow = sheet.getRow(i);
				hRow.setHeight((short) 450);// Cell의 행 높이 지정
				for (int j = 0; j < cols[i].length; j++) {					
					sheet.setColumnWidth((short)j, (short)(widths[j]*scale)); // 열 너비	
					
					Font font = wb.createFont();                		//폰트 객체 생성
					font.setFontHeightInPoints((short)(fontSize - 1));  //글자크기 지정
					font.setColor(headerTextColor.getIndexed());      //색 지정
					font.setBold(true); 								//선굵기지정
					font.setFontName("Arial");          				//폰트설정
					
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
						XSSFRow row = sheet.getRow(i+adjHeaderYAxis); 
						XSSFCell cellName = null;
						if(adjHeadSkip[adjHeadSkip.length-1] < j) {
							cellName = row.getCell(j+adjHeaderXAxis-adjHeadSkip.length);
						}else {
							cellName = row.getCell(j+adjHeaderXAxis);							
						}
						
						XSSFCellStyle cellNameStyle = cellName.getCellStyle();
					    if(cellNameStyle == null) {
					        cellNameStyle = wb.createCellStyle();
					    }
					    
					    cellNameStyle.setFillForegroundColor(bgColor);
					    cellNameStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					    
					    cellNameStyle.setFont(font);
					    cellNameStyle.setBorderTop(BorderStyle.THIN);
					    cellNameStyle.setBorderLeft(BorderStyle.THIN);
					    cellNameStyle.setBorderRight(BorderStyle.THIN);
					    cellNameStyle.setBorderBottom(BorderStyle.THIN);
					    cellNameStyle.setTopBorderColor(lineColor);
					    cellNameStyle.setLeftBorderColor(lineColor);
					    cellNameStyle.setRightBorderColor(lineColor);
					    cellNameStyle.setBottomBorderColor(lineColor);
					    cellNameStyle.setVerticalAlignment(VerticalAlignment.CENTER);
					    cellNameStyle.setAlignment(HorizontalAlignment.CENTER);

						cellName.setCellStyle(cellNameStyle);							
						cellName.setCellValue(name); // 데이터 입력
					}else {
						isSkip = false;
					}
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
								sheet.addMergedRegion(new CellRangeAddress(
										i+adjHeaderYAxis, //시작 행번호
							            i, //마지막 행번호
							            j+adjHeaderXAxis-adjHeadSkip.length, //시작 열번호
							            j+adjHeaderXAxis + cspan - 1-adjHeadSkip.length  //마지막 열번호
							    ));	
							}else {
								sheet.addMergedRegion(new CellRangeAddress(
										i+adjHeaderYAxis, //시작 행번호
							            i, //마지막 행번호
							            j+adjHeaderXAxis, //시작 열번호
							            j+adjHeaderXAxis + cspan - 1  //마지막 열번호
							    ));
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
								sheet.addMergedRegion(new CellRangeAddress(
										i+adjHeaderYAxis, //시작 행번호
										i+adjHeaderYAxis + rspan - 1, //마지막 행번호
										j+adjHeaderXAxis-adjHeadSkip.length, //시작 열번호
										j+adjHeaderXAxis-adjHeadSkip.length  //마지막 열번호
							    ));
								
							}else {
								sheet.addMergedRegion(new CellRangeAddress(
										i+adjHeaderYAxis, //시작 행번호
										i+adjHeaderYAxis + rspan - 1, //마지막 행번호
							            j+adjHeaderXAxis, //시작 열번호
							            j+adjHeaderXAxis  //마지막 열번호
							    ));
								
							}
						}else {
							isSkip = false;
						}
					}
				}
			}
		}
	}

	private void rowsPrint(ExcelXmlParser parser, HttpServletResponse resp) throws IOException {
		ExcelRow[] rows = parser.getGridContent();
		if (rows == null) return;
		this.rows_stat = rows.length;
		
		// 음.. 모든 셀을 다 그린 후 마지막에 다시 계산식을 그려주는 말도 안되는 코드가 있었는데, 
		// 진짜루 FORMULA 식들이 제대로 동작하지 않는다.
		// 어쩔 수 없이 나도 ...
		// 3 행 J 부터 ~ 상단 계산식 들 재정의 
		XSSFRow row = sheet.getRow(3); //상단 계산식 row 
		for(int c=8; c < cols_stat; c++) {
			XSSFCell cellName = row.getCell(c);
			if(cellName.getCellType() == CellType.FORMULA) {
				//계산식을 다시 그려준다.
				cellName.setCellFormula(cellName.getCellFormula());
			}
		}
		
		for (int i = 0; i < rows.length; i++) {
			ExcelCell[] cells = rows[i].getCells();
						
			XSSFRow hRow = sheet.getRow(i + headerOffset);
			hRow.setHeight((short) 400);// Cell의 행 높이 지정
			
			for (int j = 0; j < cells.length; j++)
			{
				String value = cells[j].getValue().toString();
				setSheetValue(value,i,j,cols[0][j].getType(),cells[j]);
			}
		}
		headerOffset += rows.length;
	}

	private void setSheetValue(String value,int i, int j, String type, ExcelCell cell) 
	{
		int k;
		isSkip = false;
		// J, K 일때는 skip
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
					XSSFRow row = sheet.getRow(i+adjRowYAxis + headerOffset); 
					XSSFCell cellName = row.getCell(j+adjRowXAxis);

					XSSFCellStyle cellNameStyle = cellName.getCellStyle();
				    if(cellNameStyle == null) {
				        cellNameStyle = wb.createCellStyle();
				    }
					XSSFDataFormat format = wb.createDataFormat(); 
					cellNameStyle.setDataFormat(format.getFormat("#,##0.000"));
				    cellName.setCellStyle(cellNameStyle);							
					cellName.setCellValue(val); // 데이터 입력
				}else {
					XSSFRow row = sheet.getRow(i+adjRowYAxis + headerOffset); 
					XSSFCell cellName = row.getCell(j+adjRowXAxis);

					XSSFCellStyle cellNameStyle = cellName.getCellStyle();
				    if(cellNameStyle == null) {
				        cellNameStyle = wb.createCellStyle();
				    }
				    XSSFDataFormat format = wb.createDataFormat(); 
				    cellNameStyle.setDataFormat(format.getFormat("#,##0.00"));
				    cellName.setCellStyle(cellNameStyle);							
					cellName.setCellValue(val); // 데이터 입력
				}
			}else {
				isSkip = false;
				
				XSSFRow row = sheet.getRow(i+adjRowYAxis + headerOffset); 
				XSSFCell cellName = row.getCell(j+adjRowXAxis);

				XSSFCellStyle cellNameStyle = cellName.getCellStyle();
			    if(cellNameStyle == null) {
			        cellNameStyle = wb.createCellStyle();
			    }		
			    // FORMULA 식들이 제대로 동작하지 않는다.
			    // 원래의 계산식을 다시 기록해 준다.
				if(cellName.getCellType() == CellType.FORMULA) {					
					cellName.setCellFormula(cellName.getCellFormula());

					XSSFDataFormat format = wb.createDataFormat(); 
					cellNameStyle.setDataFormat(format.getFormat("#,##0.00"));
					cellName.setCellStyle(cellNameStyle);
				}
			}
		}
		else
		{
			if(!isSkip) {
				XSSFRow row = sheet.getRow(i+adjRowYAxis + headerOffset); 
				XSSFCell cellName = row.getCell(j+adjRowXAxis);
				cellName.setCellValue(value); // 데이터 입력
				
			}else {
				isSkip = false;
			}
		}
	}

	private void setColorProfile() {
		bgColor = new XSSFColor(wb.getStylesSource().getIndexedColors());
		lineColor = new XSSFColor(wb.getStylesSource().getIndexedColors());
		headerTextColor = new XSSFColor(wb.getStylesSource().getIndexedColors());
		scaleOneColor = new XSSFColor(wb.getStylesSource().getIndexedColors());
		scaleTwoColor = new XSSFColor(wb.getStylesSource().getIndexedColors());
		gridTextColor = new XSSFColor(wb.getStylesSource().getIndexedColors());
		watermarkTextColor = new XSSFColor(wb.getStylesSource().getIndexedColors());
		
		String profile = parser.getProfile();
		if ((profile.equalsIgnoreCase("color"))||profile.equalsIgnoreCase("full_color")) {
			bgColor.setARGBHex("D1E5FE");			
			lineColor.setARGBHex("A4BED4");
			headerTextColor.setARGBHex("000000");
			scaleOneColor.setARGBHex("FFFFFF");
			scaleTwoColor.setARGBHex("E3EFFF");
			gridTextColor.setARGBHex("000000");
			watermarkTextColor.setARGBHex("8b8b8b");
		} else {
			if (profile.equalsIgnoreCase("gray")) {				
				bgColor.setARGBHex("E3E3E3");			
				lineColor.setARGBHex("B8B8B8");
				headerTextColor.setARGBHex("000000");
				scaleOneColor.setARGBHex("FFFFFF");
				scaleTwoColor.setARGBHex("EDEDED");
				gridTextColor.setARGBHex("000000");
				watermarkTextColor.setARGBHex("8b8b8b");
			} else {
				bgColor.setARGBHex("FFFFFF");			
				lineColor.setARGBHex("000000");
				headerTextColor.setARGBHex("000000");
				scaleOneColor.setARGBHex("FFFFFF");
				scaleTwoColor.setARGBHex("FFFFFF");
				gridTextColor.setARGBHex("000000");
				watermarkTextColor.setARGBHex("000000");
			}
		}
	}

	@RequestMapping("/writerPoiV4")
	public void writerV4(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		resp.setCharacterEncoding( "UTF-8" );
		req.setCharacterEncoding( "UTF-8" );
		
		String xml = req.getParameter("grid_xml");
		String writerType = req.getParameter("writerType");
		String prjtCd = StringUtils.defaultIfBlank(req.getParameter("prjtCd"), "");
		String prjtNm = StringUtils.defaultIfBlank(req.getParameter("prjtNm"), "");
		// Budget 입력 주기 추가
		String budgetInputCycle = StringUtils.defaultIfBlank(req.getParameter("budgetInputCycle"), "");
		
//		System.out.println(" xml :: " + xml);
//		System.out.println(" writerType :: " + writerType);
//		System.out.println(" prjtCd :: " + prjtCd);
//		System.out.println(" prjtNm :: " + prjtNm);
//		System.out.println(" budgetInputCycle :: " + budgetInputCycle);
			
		filename = req.getParameter("filename");
		
		xml = URLDecoder.decode(xml, "UTF-8");
		filename = URLDecoder.decode(filename, "UTF-8");

		String cfilepath = req.getSession().getServletContext().getRealPath(appConfig.getExcelTemplatePath());
		(new ExcelWriterV4()).budgetGenerateV4(xml, resp, cfilepath, filename, prjtCd, prjtNm, budgetInputCycle);					
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
			adjColSkip[0] = 9;  // J
			adjColSkip[1] = 10; // K
			parser.setXML(xml);
			
			FileInputStream file = new FileInputStream(filepath + "/SAT_BUDGET_V4.xlsx");
			wb = new XSSFWorkbook(file);
			sheet = wb.getSheetAt(0);
			
			setColorProfile();
			
			CellStyle prjtCdLabel = wb.createCellStyle(); 
			CellStyle prjtNmLabel = wb.createCellStyle(); 
			CellStyle budgetInputCycleLabel = wb.createCellStyle(); 
			
			XSSFRow row = sheet.getRow(0);
			XSSFCell cellPrjtCd = row.createCell(0);
			cellPrjtCd.setCellStyle(prjtCdLabel); // 셀에 스타일 지정
			cellPrjtCd.setCellValue(prjtCd); // 데이터 입력

			XSSFCell cellPrjtNm = row.createCell(1);
			cellPrjtNm.setCellStyle(prjtNmLabel); // 셀에 스타일 지정
			cellPrjtNm.setCellValue(prjtNm); // 데이터 입력

			XSSFCell cellBudgetInputCycle = row.createCell(10);
			cellBudgetInputCycle.setCellStyle(budgetInputCycleLabel); // 셀에 스타일 지정
			cellBudgetInputCycle.setCellValue(budgetInputCycle); // 데이터 입력

			headerPrint(parser);
			rowsPrint(parser,resp);

			//틀고정
			sheet.createFreezePane(11, 4);
			
			XSSFRow hRow = sheet.getRow(0);
			hRow.setHeight((short) (30*55));// Cell의 행 높이 지정

			outputExcel(resp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}	

}
