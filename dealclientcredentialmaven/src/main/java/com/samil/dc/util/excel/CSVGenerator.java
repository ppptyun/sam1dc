package com.samil.dc.util.excel;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CSVGenerator extends HttpServlet {

		public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			req.setCharacterEncoding("UTF-8");
			String xml = req.getParameter("grid_xml");
			xml = URLDecoder.decode(xml, "UTF-8");
			CSVWriter writer = new CSVWriter();
			writer.generate(xml, resp);
		}
}
