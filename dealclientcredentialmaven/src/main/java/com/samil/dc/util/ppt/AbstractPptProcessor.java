package com.samil.dc.util.ppt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.PresentationML.MainPresentationPart;
import org.docx4j.openpackaging.parts.PresentationML.SlideLayoutPart;
import org.docx4j.openpackaging.parts.PresentationML.SlidePart;
import org.pptx4j.pml.CTGraphicalObjectFrame;

import com.samil.dc.core.ContextLoader;
import com.samil.dc.dao.DcCredentialDAO;
import com.samil.dc.domain.DcCredentialListBean;
import com.samil.dc.domain.DcCredentialSearchConditionBean;
import com.samil.dc.service.worker.ServiceHelper;
import com.samil.dc.util.SamilUtil;
import com.samil.dc.util.excel.CustomExcelWriter;
import com.samil.dc.util.ppt.bean.PptBrsBean;
import com.samil.dc.util.ppt.bean.PptTableColumn;

public abstract class AbstractPptProcessor {
	protected static final Logger logger = Logger.getRootLogger();
	protected String filename = "data";
	protected String headerName = "data";
	protected String outputfilepath;
	protected PresentationMLPackage presentationMLPackage = null;
	protected MainPresentationPart pp;
	protected SlideLayoutPart layoutPartContent;
	protected org.docx4j.dml.ObjectFactory dmlFactory; 
	protected List<DcCredentialListBean> list;
	protected ArrayList<PptTableColumn> columns = new ArrayList<PptTableColumn>();
	protected int maxRowPerSlide = 0;
	
	public AbstractPptProcessor(String filename, List<DcCredentialListBean> list, int maxRowPerSlide) throws Docx4JException  {
		dmlFactory = new org.docx4j.dml.ObjectFactory();
		this.presentationMLPackage = (PresentationMLPackage) OpcPackage.load(new java.io.File(AbstractPptProcessor.class.getResource("resources/template.pptx").getPath()));
		if (ContextLoader.getContext("ConnectToOper").equals("Y")) {
			outputfilepath = ContextLoader.getContext("Path", "FileUploadInfo");
		} else {
			// 2023.04 cm
			// 경로 변경
			//outputfilepath = System.getProperty("user.dir") + "/WebContent/files/";
			outputfilepath = System.getProperty("user.dir") + "/src/main/webapp/files/";
		}
		
		this.maxRowPerSlide = maxRowPerSlide;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		this.filename = SamilUtil.escapeFileName(filename + "_" + transFormat.format(new Date()));
		this.headerName = this.escape(filename);
		this.list = list;
		this.pp = (MainPresentationPart) this.presentationMLPackage.getParts().getParts().get(new PartName("/ppt/presentation.xml"));
		this.layoutPartContent = (SlideLayoutPart) this.presentationMLPackage.getParts().getParts().get(new PartName("/ppt/slideLayouts/slideLayout2.xml"));
	}
	
	/**
	 * ppt 만들기전 사전 작업 : 파일명 지정,  
	 * @return
	 * @throws Docx4JException
	 */
	public abstract AbstractPptProcessor ready() throws Docx4JException;
	
	
	/**
	 * PPT 만들기
	 */
	public abstract AbstractPptProcessor make() throws Exception;
	
	
	/**
	 * @param idx
	 * @param count
	 * @return
	 * @throws Exception
	 */
	protected abstract CTGraphicalObjectFrame getTable(int idx, int count) throws Exception;
	
	/**
	 * PPT 저장 후 저장된 파일 명 리턴
	 * @return
	 * @throws Docx4JException
	 * @throws IOException 
	 */
	public void output(HttpServletResponse res) throws Docx4JException, IOException{
		res.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
		res.setCharacterEncoding("UTF-8");
		res.setHeader("Content-Disposition", "attachment; filename=" + this.filename + ".pptx");
		res.setHeader("Cache-Control", "max-age=0");
		this.presentationMLPackage.save(res.getOutputStream());
	}
	
	
	/**
	 * layout 적용된 빈 슬라이드 생성
	 * @return
	 * @throws Exception
	 */
	public SlidePart createEmptySlide() throws Exception{
		int slideIndex = this.pp.getSlideCount()+1;
		SlidePart slidePart = new SlidePart(new PartName("/ppt/slides/slide" + slideIndex + ".xml"));
		slidePart.setContents( SlidePart.createSld() );
		this.pp.addSlide(slidePart);
		
		slidePart.addTargetPart(this.layoutPartContent);
		return slidePart;
	}
	
	public String escape(String text){
		return StringEscapeUtils.escapeXml11(text);
	}
}
