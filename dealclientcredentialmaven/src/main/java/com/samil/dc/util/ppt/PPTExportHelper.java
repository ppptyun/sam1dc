package com.samil.dc.util.ppt;

import java.util.HashMap;
import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.dml.CTRegularTextRun;
import org.docx4j.dml.CTTableCell;
import org.docx4j.dml.CTTextAutonumberBullet;
import org.docx4j.dml.CTTextLineBreak;
import org.docx4j.dml.CTTextParagraph;
import org.docx4j.dml.STTextAlignType;
import org.docx4j.dml.STTextAutonumberScheme;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.PresentationML.SlidePart;
import org.pptx4j.Pptx4jException;
import org.pptx4j.jaxb.Context;
import org.pptx4j.pml.CTGraphicalObjectFrame;
import org.pptx4j.pml.Shape;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.samil.dc.core.XMLReader;

/**
 * @author shyunchoi
 *
 */
public class PPTExportHelper {
	private static PPTExportHelper instance;
	private HashMap<String, String> xmlData = new HashMap<String, String>();
	private org.docx4j.dml.ObjectFactory dmlFactory = new org.docx4j.dml.ObjectFactory();
	
	private PPTExportHelper(){}
	public static PPTExportHelper getInstance () {
		if(instance == null){instance = new PPTExportHelper();}
		return instance;
	}
	
	/**
	 * 커버 슬라이드 제목 넣기
	 * @param coverSlidePart
	 * @param title
	 * @throws Pptx4jException
	 * @throws Docx4JException
	 */
	public void setCoverTitle(SlidePart coverSlidePart, String title) throws Pptx4jException, Docx4JException{setCoverTitle(coverSlidePart,title, "");}
	/**
	 * 커버 슬라이드 제목과 부제목 넣기
	 * @param coverSlidePart
	 * @param title
	 * @param subTitle
	 * @throws Pptx4jException
	 * @throws Docx4JException
	 */
	public void setCoverTitle(SlidePart coverSlidePart, String title, String subTitle) throws Pptx4jException, Docx4JException{
		Shape coverTitle = (Shape) coverSlidePart.getContents().getCSld().getSpTree().getSpOrGrpSpOrGraphicFrame().get(0);
		Shape coverSubTitle = (Shape) coverSlidePart.getContents().getCSld().getSpTree().getSpOrGrpSpOrGraphicFrame().get(1);
		((CTRegularTextRun)coverTitle.getTxBody().getP().get(0).getEGTextRun().get(0)).setT(title);
		((CTRegularTextRun)coverSubTitle.getTxBody().getP().get(0).getEGTextRun().get(0)).setT(subTitle);
	}
	
	/**
	 * <pre>
	 * SliePart에 Title 추가
	 * </pre>
	 * @param slidePart
	 * @param title
	 * @throws Exception
	 */
	public void addTitle(SlidePart slidePart, String title) throws Exception{addTitle(slidePart, title, "");}
	/**
	 * <pre>
	 * SliePart에 Title과 Sub Title 추가
	 * </pre>
	 * @param slidePart
	 * @param title
	 * @param subTitle
	 * @throws Exception
	 */
	public void addTitle(SlidePart slidePart, String title, String subTitle) throws Exception{
		String titleXML = getFragXml("template_textbox", "slide_title");
		HashMap<String, String> mappings = new HashMap<String, String>(); 
		mappings.put("title", title);
		mappings.put("subtitle", subTitle);
		Shape titleShape = (Shape) XmlUtils.unmarshallFromTemplate(titleXML, mappings, Context.jcPML);
		this.addGrahicFrame(slidePart, titleShape);
	}
	
	/**
	 * <pre>
	 * CellType에 따른 Style 적용된 Table Cell 생성 후 리턴
	 * </pre>
	 * @param cellType
	 * @param text
	 * @return
	 * @throws JAXBException
	 */
	public CTTableCell createTc(String cellType, String text) throws JAXBException{
		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("text", text);
		if(cellType.equals("")){
			return (CTTableCell) XmlUtils.unmarshallFromTemplate(getFragXml("template_tc", "noStyleCell"), mappings, org.docx4j.jaxb.Context.jc, CTTableCell.class);
		}else{
			return (CTTableCell) XmlUtils.unmarshallFromTemplate(getFragXml("template_tc", cellType), mappings, org.docx4j.jaxb.Context.jc, CTTableCell.class);	
		}
	}
	public CTTableCell createTc(String cellType, String text, STTextAlignType align) throws JAXBException{
		CTTableCell cTTableCell= null;
		
		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("text", text);
		if(cellType.equals("")){
			cTTableCell = (CTTableCell) XmlUtils.unmarshallFromTemplate(getFragXml("template_tc", "noStyleCell"), mappings, org.docx4j.jaxb.Context.jc, CTTableCell.class);
		}else{
			cTTableCell = (CTTableCell) XmlUtils.unmarshallFromTemplate(getFragXml("template_tc", cellType), mappings, org.docx4j.jaxb.Context.jc, CTTableCell.class);	
		}
		cTTableCell.getTxBody().getP().get(0).getPPr().setAlgn(align);		
		return cTTableCell;
	}
	
	/**
	 * PPTExport에서 사용하는 Resource XML 데이터 메모리에 로드 후 데이터 리턴
	 * @param filename
	 * @param key
	 * @return
	 */
	public String getFragXml(String filename, String key){
		String xmlDataKey = filename + "_" + key;
		if(!xmlData.containsKey(xmlDataKey)){
			NodeList nodeList = getResourceXML(filename);
			if(nodeList != null){
				for(int i=0; i<nodeList.getLength(); i++){
					Element el = (Element)nodeList.item(i);					
					String data = el.getTextContent().trim();
					xmlData.put(filename + "_" + el.getAttribute("id"), data);	
				}
			}else{
				System.out.println("NodeList is null");
			}
		}
		return xmlData.get(xmlDataKey);
	}
	
	/**
	 * PPTExport에서 사용하는 Resource XML 파일 읽기
	 * @param filename
	 * @return
	 */
	private NodeList getResourceXML(String filename){
		try{
			Document dom = (new XMLReader("resources/" + filename + ".xml", PPTExportHelper.class)).getXML();
			dom.getDocumentElement().normalize();
			NodeList nodeList = dom.getElementsByTagName("data");
			return nodeList;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * <pre>
	 * 일반 테이블 생성 후 리턴
	 * </pre>
	 * @param mappings
	 * @return
	 * @throws Exception
	 */
	public CTGraphicalObjectFrame getTableType1(HashMap<String, String> mappings) throws Exception{
		CTGraphicalObjectFrame cTGraphicalObjectFrame = (CTGraphicalObjectFrame)XmlUtils.unmarshallFromTemplate(
				  getFragXml("template_table", "type1")
				, mappings
				, org.pptx4j.jaxb.Context.jcPML
				, CTGraphicalObjectFrame.class);
		return cTGraphicalObjectFrame;
	}
	
	/**
	 * Slide에 GraphicFrame 추가하기
	 * @param slidePart
	 * @param graphicFrame
	 * @throws Docx4JException
	 */
	public void addGrahicFrame(SlidePart slidePart, Object graphicFrame) throws Docx4JException {
		slidePart.getContents().getCSld().getSpTree().getSpOrGrpSpOrGraphicFrame().add(graphicFrame);
	}
	
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public CTTextParagraph getCTTextParagraph(String[] text){return getCTTextParagraphByAutoNum(0, text);}
	/**
	 * @param text
	 * @return
	 */
	public CTTextParagraph getCTTextParagraph(String text){return getCTTextParagraphByAutoNum(0, new String[]{text});}
	
	
	/**
	 * @param lvl
	 * @param text
	 * @return
	 */
	public CTTextParagraph getCTTextParagraphByAutoNum(int lvl, String text){return getCTTextParagraphByAutoNum(lvl, new String[]{text});}
	/**
	 * @param lvl
	 * @param text
	 * @return
	 */
	public CTTextParagraph getCTTextParagraphByAutoNum(int lvl, String[] text){
		CTTextParagraph cTTextParagraph = dmlFactory.createCTTextParagraph();
		cTTextParagraph.setPPr(dmlFactory.createCTTextParagraphProperties());
		
		for(int i=0; i<text.length; i++){
			CTRegularTextRun cTRegularTextRun = dmlFactory.createCTRegularTextRun();
			cTTextParagraph.getEGTextRun().add(cTRegularTextRun);
			cTRegularTextRun.setRPr(dmlFactory.createCTTextCharacterProperties());
			cTRegularTextRun.setT(text[i]);
			
			if(i != text.length -1 ){
				CTTextLineBreak cTTextLineBreak = dmlFactory.createCTTextLineBreak();
				cTTextParagraph.getEGTextRun().add(cTTextLineBreak);
			}
		}
		
		CTTextAutonumberBullet cTTextAutonumberBullet1 = dmlFactory.createCTTextAutonumberBullet();
		cTTextAutonumberBullet1.setType(STTextAutonumberScheme.ARABIC_PERIOD);
		
		CTTextAutonumberBullet cTTextAutonumberBullet2 = dmlFactory.createCTTextAutonumberBullet();
		cTTextAutonumberBullet2.setType(STTextAutonumberScheme.ARABIC_PAREN_R);
		
		CTTextAutonumberBullet cTTextAutonumberBullet3 = dmlFactory.createCTTextAutonumberBullet();
		cTTextAutonumberBullet3.setType(STTextAutonumberScheme.ROMAN_LC_PAREN_R);
		
		switch(lvl){
			case 1:
				cTTextParagraph.getPPr().setBuAutoNum(cTTextAutonumberBullet1);
				cTTextParagraph.getPPr().setLvl(lvl);
				for(int i=0; i<cTTextParagraph.getEGTextRun().size(); i++){
					if(cTTextParagraph.getEGTextRun().get(i) instanceof CTRegularTextRun ){
						((CTRegularTextRun)cTTextParagraph.getEGTextRun().get(i)).getRPr().setB(true);
						((CTRegularTextRun)cTTextParagraph.getEGTextRun().get(i)).getRPr().setSz(1800);
					}
				}
				break;
			case 2:
				cTTextParagraph.getPPr().setBuAutoNum(cTTextAutonumberBullet2);
				cTTextParagraph.getPPr().setLvl(lvl);
				for(int i=0; i<cTTextParagraph.getEGTextRun().size(); i++){
					if(cTTextParagraph.getEGTextRun().get(i) instanceof CTRegularTextRun ){
						((CTRegularTextRun)cTTextParagraph.getEGTextRun().get(i)).getRPr().setSz(1600);
					}
				}
				break;
			case 3:
				cTTextParagraph.getPPr().setBuAutoNum(cTTextAutonumberBullet3);
				cTTextParagraph.getPPr().setLvl(lvl);
				for(int i=0; i<cTTextParagraph.getEGTextRun().size(); i++){
					if(cTTextParagraph.getEGTextRun().get(i) instanceof CTRegularTextRun ){
						((CTRegularTextRun)cTTextParagraph.getEGTextRun().get(i)).getRPr().setSz(1400);
					}
				}
				break;
			default:
				for(int i=0; i<cTTextParagraph.getEGTextRun().size(); i++){
					if(cTTextParagraph.getEGTextRun().get(i) instanceof CTRegularTextRun ){
						((CTRegularTextRun)cTTextParagraph.getEGTextRun().get(i)).getRPr().setSz(1800);
					}
				}
				break;
		}
		
		return cTTextParagraph;
	}
}
