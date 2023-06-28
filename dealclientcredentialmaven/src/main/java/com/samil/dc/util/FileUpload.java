package com.samil.dc.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.samil.dc.core.ContextLoader;

public class FileUpload {

	DiskFileItemFactory factory;
	ServletFileUpload upload;
	String defaultDirPath;
	String prgcd;
	int maxSize;
	String allowExt;
	String tmpFileName;

	public FileUpload() {
		prgcd = ContextLoader.getContext("ProgramCode");

		if (ContextLoader.getContext("ConnectToOper").equals("Y")) {
			defaultDirPath = ContextLoader.getContext("Path", "FileUploadInfo");
			maxSize = Integer.parseInt(ContextLoader.getContext("MaxSize", "FileUploadInfo"));
			allowExt = ContextLoader.getContext("AllowExt", "FileUploadInfo");
		} else {
			defaultDirPath = ContextLoader.getContext("Path", "FileUploadInfoDev");
			maxSize = Integer.parseInt(ContextLoader.getContext("MaxSize", "FileUploadInfoDev"));
			allowExt = ContextLoader.getContext("AllowExt", "FileUploadInfoDev");
		}
	}

	public String upload(HttpServletRequest req) throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		HashMap<String, String> dataMap = new HashMap<String, String>();
		FileItem tmpFile = null;
		String fileName = "";

		if (!isMultipart) {
			return null;
		}

		// Create a factory for disk-based file items
		factory = new DiskFileItemFactory();
		// 업로드 파일의 max size 지정
		factory.setSizeThreshold(maxSize);
		// maxSize 넘길 경우 해당 폴더에 임시로 다운받은 후에 처리, 아니면 메모리에서 처리
		factory.setRepository(new File(defaultDirPath));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");

		// Parse the request
		List<FileItem> items = upload.parseRequest(req);

		// Process the uploaded items
		for (FileItem item : items) {
			String fieldName = item.getFieldName();

			// processFormField
			if (item.isFormField()) {
				dataMap.put(fieldName, item.getString("UTF-8"));
			} else {
				if (item.getSize() > 0) {
					tmpFile = item;
					fileName = item.getName();
					if (fileName != null) {
						fileName = FilenameUtils.getName(fileName);
					}
					break;
				}
			}
		}
		
		// 파일 확장자 체크
		String fileExt = StringUtils.substringAfterLast(fileName, ".");
		if (StringUtils.isBlank(fileExt)) {
			return "NOT_ALLOWED_EXT";
		}
		boolean allowed = false;
		for (String allow : StringUtils.split(allowExt, ",")) {
			if (fileExt.equals(allow)) {
				allowed = true;
				break;
			}
		}
		if (!allowed) {
			return "NOT_ALLOWED_EXT";
		}

		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		fileName = prgcd + "_" + timeStamp + "_" + fileName;

		File saveFolder = new File(defaultDirPath);
		if (!saveFolder.exists())
			saveFolder.mkdirs();

		File uploadFile = new File(defaultDirPath, fileName);

		// Save file object
		if (tmpFile.getSize() > 0) {
			tmpFile.write(uploadFile);
			tmpFile.delete();

			//return uploadFile.getAbsolutePath();
			return uploadFile.getName();
		}

		return null;
	}
}
