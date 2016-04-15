package com.hengguang.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hengguang.jni.ReadRdf;

public class ShowMTIEDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String filename;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String path = getServletContext().getRealPath("/WEB-INF/upload");
		filename = req.getParameter("filename");
		String filepath = path + "/" + filename;
		// 获取mtie数据
		ReadRdf readRdf = new ReadRdf();
		Map<Double, Double> mteiData = readRdf.getMteiData(filepath);
		Set<Double> keySet = mteiData.keySet();
		List<Double> doubles = new ArrayList<Double>(keySet);
		Collections.sort(doubles);
		StringBuilder mtie = new StringBuilder();
		mtie.append("[");
		for (int i = 1; i < doubles.size() - 1; i++) {
			mtie.append("[" + doubles.get(i) + ","
					+ mteiData.get(doubles.get(i)) + "],");
		}
		mtie.append("[" + doubles.get(doubles.size() - 1) + ","
				+ mteiData.get(doubles.get(doubles.size() - 1)) + "]]");
		req.setAttribute("mtie", mtie.toString());
		// 获取tdev数据
		StringBuilder tdev = new StringBuilder();
		tdev.append("[");
		for (int i = 1; i < doubles.size() - 1; i++) {
			tdev.append("[" + doubles.get(i) + ","
					+ readRdf.getTdev((int) (i / 0.02)) + "],");
		}

		tdev.append("[" + doubles.get(doubles.size() - 1) + ","
				+ readRdf.getTdev((int) (doubles.size() / 0.02)) + "]]");
		req.setAttribute("tdev", tdev.toString());
		req.getRequestDispatcher("WEB-INF/jsp/showmtiedata.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
