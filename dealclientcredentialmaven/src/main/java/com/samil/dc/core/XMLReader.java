package com.samil.dc.core;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLReader {
	//private String m_path = "";
	private Document m_doc = null;
	DocumentBuilderFactory f = null;
	DocumentBuilder bu = null;
	
	public XMLReader(String path) throws SAXException, IOException, ParserConfigurationException{
		f = DocumentBuilderFactory.newInstance();
		// A1 Injection - XML External Entity Injection
		f.setFeature("http://xml.org/sax/features/external-general-entities", false);
		f.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
		
		bu = f.newDocumentBuilder();
		m_doc = bu.parse(ContextLoader.class.getResource(path).getFile());
		m_doc.getDocumentElement().normalize();
	}
	
	public XMLReader(String path, Class c) throws SAXException, IOException, ParserConfigurationException{
		f = DocumentBuilderFactory.newInstance();
		// A1 Injection - XML External Entity Injection
		f.setFeature("http://xml.org/sax/features/external-general-entities", false);
		f.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
		
		bu = f.newDocumentBuilder();
		m_doc = bu.parse(c.getResource(path).getFile());
		m_doc.getDocumentElement().normalize();
	}
	
	public Document getXML(){
		return m_doc;
	}
}
