package com.samil.dc.core;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ContextLoader {

//	private static final Logger logger = Logger.getRootLogger();
	
	public static String getContext(String contextName) {
		String context = null;
		
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder bu = f.newDocumentBuilder();
			Document dom = bu.parse(ContextLoader.class.getResource("../../../../ApplicationContext.xml").getFile());
			
			dom.getDocumentElement().normalize();

			NodeList tag = dom.getElementsByTagName(contextName);
			Element code = (Element) tag.item(0);
			Node contextNode = code.getChildNodes().item(0);
			
			context = contextNode.getNodeValue();
		
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return context;
	}
	
	public static String getContext(String contextName, String parentName) {
		String context = null;
		Node contextNode = null;
		Element code = null;
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder bu = f.newDocumentBuilder();
			Document dom = bu.parse(ContextLoader.class.getResource("../../../../ApplicationContext.xml").getFile());
			
			dom.getDocumentElement().normalize();
			
			NodeList tag = dom.getElementsByTagName(contextName);
			
			for(Integer i = 0 ; i  < tag.getLength() ; i ++){
				code = (Element) tag.item(i);
				
				if(code.getParentNode().getNodeName().equals(parentName)){
					contextNode = code.getChildNodes().item(0);
					context = contextNode.getNodeValue();
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return context;
	}
}
