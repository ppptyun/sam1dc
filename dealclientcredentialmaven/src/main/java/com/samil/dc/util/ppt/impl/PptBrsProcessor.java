package com.samil.dc.util.ppt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.StringEscapeUtils;
import org.docx4j.dml.CTLineProperties;
import org.docx4j.dml.CTTable;
import org.docx4j.dml.CTTableCell;
import org.docx4j.dml.CTTableCol;
import org.docx4j.dml.CTTableRow;
import org.docx4j.dml.STTextAlignType;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.PresentationML.SlidePart;
import org.pptx4j.pml.CTGraphicalObjectFrame;
import org.xlsx4j.sml.STTableStyleType;

import com.samil.dc.domain.DcCredentialListBean;
import com.samil.dc.util.ppt.AbstractPptProcessor;
import com.samil.dc.util.ppt.PPTExportHelper;
import com.samil.dc.util.ppt.bean.PptBrsBean;
import com.samil.dc.util.ppt.bean.PptTableColumn;

public class PptBrsProcessor extends AbstractPptProcessor {
	private ArrayList<PptBrsBean> exportList = new ArrayList<PptBrsBean>();
	
	public PptBrsProcessor(String headerNm, List<DcCredentialListBean> list, int maxRowPerSlide) throws Docx4JException {
		super(headerNm, list, maxRowPerSlide);	
	}

	@Override
	public AbstractPptProcessor ready() throws Docx4JException {
		columns.add(new PptTableColumn("순번", 432047L));
		columns.add(new PptTableColumn("Client", 1296144L, STTextAlignType.L));
		columns.add(new PptTableColumn("용역명", 3297346L, STTextAlignType.L));
		columns.add(new PptTableColumn("기간", 1018822L));
		columns.add(new PptTableColumn("OPB", 1018822L, STTextAlignType.L));
		columns.add(new PptTableColumn("매수처", 1018822L, STTextAlignType.L));
		
		for(int i=0; i<list.size(); i++){
			PptBrsBean tmp = new PptBrsBean();
			tmp.setClient(this.escape(list.get(i).getClientnm()));
			tmp.setPrjtnm(this.escape(list.get(i).getPrjtnm()));
			tmp.setYear(this.escape(list.get(i).getTermidt().substring(0, 4)));
			tmp.setOpb(this.escape(list.get(i).getBrsopb()));
			tmp.setBuyer(this.escape(list.get(i).getBrsbuyernm()));
			exportList.add(tmp);
		}
		return this;
	}

	@Override
	public AbstractPptProcessor make() throws Exception {
		SlidePart slidePart = null;
		PPTExportHelper helper = PPTExportHelper.getInstance();
		int page = (exportList.size() / this.maxRowPerSlide) + ((exportList.size() % this.maxRowPerSlide) == 0 ? 0 : 1); 
		
		for(int i=0; i<page; i++){
			slidePart = this.createEmptySlide();
			if(page == 1){
				helper.addTitle(slidePart, this.headerName);	
			}else{
				helper.addTitle(slidePart, this.headerName + "(" + (i+1) + "/" + page + ")");
			}
			helper.addGrahicFrame(slidePart, getTable(i*this.maxRowPerSlide, this.maxRowPerSlide));
		}
		
		return this;
	}
	
	@Override
	protected CTGraphicalObjectFrame getTable(int idx, int count) throws Exception{
		HashMap<String, String> mappings = new HashMap<String, String>();
		PPTExportHelper helper = PPTExportHelper.getInstance(); 
		CTTableRow cTTableRow;
		mappings.put("tbl_id", "1");
		mappings.put("tbl_name", "Table 1");
		mappings.put("tbl_x", "467545");
		mappings.put("tbl_y", "1752601");
		mappings.put("tbl_cx", "8080251");
		mappings.put("tbl_cy", "0");
		
		CTLineProperties noLine = dmlFactory.createCTLineProperties();
		noLine.setNoFill(dmlFactory.createCTNoFillProperties());
		
		CTGraphicalObjectFrame gof = helper.getTableType1(mappings);
		JAXBElement<CTTable> tbl = (JAXBElement<CTTable>) gof.getGraphic().getGraphicData().getAny().get(0);
		CTTable cTable = tbl.getValue();
		if(columns != null){
			cTTableRow = dmlFactory.createCTTableRow();
			cTTableRow.setH(370910L);
			for(int i=0; i<columns.size(); i++){
				CTTableCol cTTableCol = dmlFactory.createCTTableCol();
				cTable.getTblGrid().getGridCol().add(cTTableCol);
				cTTableCol.setW(columns.get(i).getWidth());
				cTTableRow.getTc().add(helper.createTc("firstRowCell", columns.get(i).getName()));
			}
			cTable.getTr().add(cTTableRow);
		}
		int eidx = idx + count > exportList.size()?exportList.size():idx + count;
		for(int i=idx; i<eidx; i++){
			cTTableRow = dmlFactory.createCTTableRow();
			cTTableRow.setH(347116L);
//			CTTableCell num 	= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", Integer.toString(i+1));
			CTTableCell num 	= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", "");
			CTTableCell client 	= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getClient(), columns.get(1).getAlign());
			CTTableCell prjtnm 	= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getPrjtnm(), columns.get(2).getAlign());
			CTTableCell year 	= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getYear(), columns.get(3).getAlign());
			CTTableCell opb 	= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getOpb(), columns.get(4).getAlign());
			CTTableCell buyer 	= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getBuyer(), columns.get(5).getAlign());
			
			num.getTcPr().setLnL(noLine);
			buyer.getTcPr().setLnR(noLine);
			
			cTTableRow.getTc().add(num);
			cTTableRow.getTc().add(client);
			cTTableRow.getTc().add(prjtnm);
			cTTableRow.getTc().add(year);
			cTTableRow.getTc().add(opb);
			cTTableRow.getTc().add(buyer);
			
			cTable.getTr().add(cTTableRow);
		}
		return gof;
	}
}
