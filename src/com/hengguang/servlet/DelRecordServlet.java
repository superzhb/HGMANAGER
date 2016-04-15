package com.hengguang.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DelRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String filename;
	private int maxPage;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		filename = new String(
				req.getParameter("filename").getBytes("ISO-8859-1"), "utf-8");
		String path = getServletContext().getRealPath("/WEB-INF/upload");
		File file = new File(path);
		File[] files = file.listFiles();
		Collections.sort(Arrays.asList(files));
		for (File f : files) {
			if (f.getName().equals(filename)) {
				f.delete();
				break;
			}
		}
		if (files.length % 10 == 0) {
			maxPage = files.length / 10;
		} else {
			maxPage = files.length / 10 + 1;
		}
		req.setAttribute("curpage", 1 + "");
		req.setAttribute("maxpage", maxPage + "");
		req.getRequestDispatcher("main").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	};
}
