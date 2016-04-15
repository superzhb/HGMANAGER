package com.hengguang.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		File tempfile = new File(this.getServletContext().getRealPath(
				"/WEB-INF/temp"));
		if (!tempfile.exists() && !tempfile.isDirectory()) {
			tempfile.mkdir();
		}
		factory.setRepository(tempfile);// 设置文件缓存目录
		// 创建解析器
		ServletFileUpload load = new ServletFileUpload(factory);
		load.setHeaderEncoding("UTF-8");
		// 解析request得到封装FileItem的list
		try {
			@SuppressWarnings("rawtypes")
			List list = load.parseRequest(req);
			for (int i = 0; i < list.size(); i++) {
				FileItem item = (FileItem) list.get(i);
				if (!item.isFormField()) {
					String name = item.getName();
					if (!name.toLowerCase().endsWith(".rdf")) {
						resp.getWriter().write("{\"msg\":\"rdf\"}");
						item.delete();
						return;
					}
					if (!name.trim().equals("")) {
						name = name.substring(name.lastIndexOf("\\") + 1);
						// 拿到inoutStream流
						InputStream inputStream = item.getInputStream();
						String path = this.getServletContext().getRealPath(
								"/WEB-INF/upload");

						File file = new File(path);
						if (!file.exists()){
							file.mkdirs();
						}
						// 定义输出流
						OutputStream outputStream = new FileOutputStream(path
								+ "\\" + name);

						int len = 0;
						byte data[] = new byte[1024];
						while ((len = inputStream.read(data)) != -1)
							outputStream.write(data, 0, len);
						inputStream.close();
						outputStream.flush();
						outputStream.close();
						item.delete();// 删除临时文件
					}
				}
			}
			
			Thread.sleep(2000);
			resp.getWriter().write("{\"data\":\"success\"}");
		} catch (Exception e) {
			resp.getWriter().write("{\"data\":\"fail\"}");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
