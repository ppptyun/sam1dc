package com.samil.dc.util.ppt.impl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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

import com.samil.dc.domain.DcCredentialListBean;
import com.samil.dc.util.ppt.AbstractPptProcessor;
import com.samil.dc.util.ppt.PPTExportHelper;
import com.samil.dc.util.ppt.bean.PptCfBean;
import com.samil.dc.util.ppt.bean.PptTableColumn;

public class PptCfProcessor extends AbstractPptProcessor {
	protected ArrayList<PptCfBean> exportList = new ArrayList<PptCfBean>();
	public PptCfProcessor(String headerNm, List<DcCredentialListBean> list, int maxRowPerSlide) throws Docx4JException {
		super(headerNm, list, maxRowPerSlide);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AbstractPptProcessor ready() throws Docx4JException {
		columns.add(new PptTableColumn("순번", 432047L));
//		columns.add(new PptTableColumn("Client", 943736L));
//		columns.add(new PptTableColumn("Target", 1008897L));
//		columns.add(new PptTableColumn("매도인", 1008897L));
//		columns.add(new PptTableColumn("매수인", 2736304L));
		columns.add(new PptTableColumn("Client", 1424459L, STTextAlignType.L));
		columns.add(new PptTableColumn("Target", 1424459L, STTextAlignType.L));
		columns.add(new PptTableColumn("매도인", 1424459L, STTextAlignType.L));
		columns.add(new PptTableColumn("매수인", 1424459L, STTextAlignType.L));
		columns.add(new PptTableColumn("거래규모\n(단위: 억원)", 736131L, STTextAlignType.R));
		columns.add(new PptTableColumn("지분율", 625742L));
		columns.add(new PptTableColumn("기간", 588497L));
		
		for(int i=0; i<list.size(); i++){
			PptCfBean tmp = new PptCfBean();
			tmp.setClient(this.escape(list.get(i).getClientnm()));
			tmp.setTarget(this.escape(list.get(i).getTcomphannm1()));
			tmp.setSeller(this.escape(list.get(i).getActor1()));
         	tmp.setBuyer(this.escape(list.get(i).getActor2()));
         	tmp.setAmt(this.escape(list.get(i).getAmt()));
         	tmp.setShareRatio(this.escape(list.get(i).getDealnm()));
         	tmp.setYear(this.escape(list.get(i).getTermidt().substring(0, 4)));
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
			cTTableRow.setH(397526L);
			for(int i=0; i<columns.size(); i++){
				CTTableCol cTTableCol = dmlFactory.createCTTableCol();
				cTable.getTblGrid().getGridCol().add(cTTableCol);
				cTTableCol.setW(columns.get(i).getWidth());
				cTTableRow.getTc().add(helper.createTc("firstRowCell", columns.get(i).getName()));
			}
			cTable.getTr().add(cTTableRow);
		}
		int eidx = idx + count > exportList.size()?exportList.size():idx + count;
		DecimalFormat df = new DecimalFormat("#,##0.0");
		df.setRoundingMode(RoundingMode.HALF_UP);
		for(int i=idx; i<eidx; i++){
			cTTableRow = dmlFactory.createCTTableRow();
			cTTableRow.setH(372952L);
//			CTTableCell num 		= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", Integer.toString(i+1));
			CTTableCell num 		= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", "");
			CTTableCell client 		= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getClient(), columns.get(1).getAlign());
			CTTableCell target 		= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getTarget(), columns.get(2).getAlign());
			CTTableCell seller 		= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getSeller(), columns.get(3).getAlign());
			CTTableCell buyer 		= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getBuyer(), columns.get(4).getAlign());
			String tmpAmt = "";
			if(!exportList.get(i).getAmt().equals("")){
				tmpAmt = df.format(Long.parseLong(exportList.get(i).getAmt()) / 100000000.0d);
//				System.out.println(exportList.get(i).getAmt() + " : " + Long.parseLong(exportList.get(i).getAmt()) / 100000000.0d + " : " + tmpAmt);
			}
			CTTableCell amt 		= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", tmpAmt, columns.get(5).getAlign());
			CTTableCell shareratio 	= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getShareRatio(), columns.get(6).getAlign());
			CTTableCell year 		= helper.createTc(i == eidx - 1 ? "lastRowCell" : "midRowCell", exportList.get(i).getYear(), columns.get(7).getAlign()); 
			
			num.getTcPr().setLnL(noLine);
			year.getTcPr().setLnR(noLine);
			
			cTTableRow.getTc().add(num);
			cTTableRow.getTc().add(client);
			cTTableRow.getTc().add(target);
			cTTableRow.getTc().add(seller);
			cTTableRow.getTc().add(buyer);
			cTTableRow.getTc().add(amt);
			cTTableRow.getTc().add(shareratio);
			cTTableRow.getTc().add(year);
			
			cTable.getTr().add(cTTableRow);
		}
		return gof;
	}
}
