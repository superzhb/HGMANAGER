package com.hengguang.servlet;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hengguang.jni.ReadRdf;

public class ShowTIEDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String filename;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ReadRdf rdf = new ReadRdf();
		filename = req.getParameter("filename");
		String path = this.getServletContext().getRealPath("/WEB-INF/upload");
		List<Double> bodyData = rdf.getBodyData(path + "/" + filename);

		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		DecimalFormat df = new DecimalFormat("#0.00");
		for (int i = 0; i < bodyData.size() - 1; i++) {
			buffer.append("[" + df.format(i * 0.02) + "," + bodyData.get(i)
					+ "],");
		}
		buffer.append("[" + (bodyData.size() - 1) * 0.02 + ","
				+ bodyData.get(bodyData.size() - 1) + "]]");
		System.out.println(buffer.toString());
		req.setAttribute("tie", buffer.toString());

		req.getRequestDispatcher("WEB-INF/jsp/showtiedata.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
