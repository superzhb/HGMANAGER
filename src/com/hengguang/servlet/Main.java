package com.hengguang.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hengguang.jni.ReadRdf;
import com.hengguang.model.Record;

public class Main extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private List<Record> list;
	private int curpage;
	private int maxPage;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String curpage1 = request.getParameter("curpage");
		if (curpage1 == null || curpage1.trim() == ""
				|| Integer.valueOf(curpage1) <= 1) {
			curpage1 = "1";
		}
		curpage = Integer.valueOf(curpage1);
		list = new ArrayList<Record>();
		String path = getServletContext().getRealPath("/WEB-INF/upload");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		File[] files = file.listFiles();
		if (files.length == 0) {
			request.setAttribute("curpage", 0 + "");
			request.setAttribute("maxpage", 0 + "");
			request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(
					request, response);
			return;

		}
		if (files.length % 10 == 0) {
			maxPage = files.length / 10;
		} else {
			maxPage = files.length / 10 + 1;
		}
		curpage = curpage > maxPage ? maxPage : curpage;
		int i = 1;

		for (File f : files) {
			if (f.getName().toLowerCase().endsWith(".rdf")) {
				Record e = new ReadRdf().getHead(f.getPath());
				e.setFilename(f.getName());
				e.setId(i++);
				list.add(e);
			}
		}
		if (files.length < 1) {
			request.setAttribute("list", new ArrayList<Record>());
		} else {
			request.setAttribute("list", list.subList(10 * (curpage - 1),
					10 * curpage > list.size() ? list.size() : 10 * curpage));
		}

		request.setAttribute("curpage", curpage + "");
		request.setAttribute("maxpage", maxPage + "");

		request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
