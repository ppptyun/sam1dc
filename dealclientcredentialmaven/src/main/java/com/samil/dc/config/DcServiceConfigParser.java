package com.samil.dc.config;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.samil.dc.core.XMLReader;

public class DcServiceConfigParser {
	private static DcServiceConfigParser instance = new DcServiceConfigParser();
	private Document dom = null;
	private JSONObject json = null;
	
	private DcServiceConfigParser(){
		try {
			dom = (new XMLReader("../config/DcServiceConfig.xml")).getXML();
			DOMSource ds = new DOMSource(dom);
			StringWriter writer = new StringWriter();
		    StreamResult result = new StreamResult(writer);
		    TransformerFactory tf = TransformerFactory.newInstance();
		    Transformer transformer = tf.newTransformer();
		    transformer.transform(ds, result);
		    json = XML.toJSONObject(writer.toString());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DcServiceConfigParser getInstance(){
		return instance;
	}
	
	public String getConfigByString(){
		return this.json.toString();
	}
	
	public JSONObject getConfigByJSON(){
		return this.json;
	}	
}
