package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class ReadRdf {
	public static void main(String[] args) {
		try {
			// 创建文件输入流对象
			FileInputStream is = new FileInputStream("d:/test.rdf");
			// 设定读取的字节数
			int n = is.available();
			byte buffer[] = new byte[n];
			StringBuffer buffer1 = new StringBuffer();
			// 读取输入流
			while ((is.read(buffer, 0, n) != -1) && (n > 0)) {
				byte name1 = buffer[57];
				int name2 = (int) buffer[58];
				int name3 = (int) buffer[59];
				int name4 = (int) buffer[60];
			}
			System.out.println(0x97f4);
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");

			// System.out.println(dateFormat.format(0x417a7256));
			// 关闭输入流
			is.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	static double int2dbl(int val) {
		double f = 0.0;
		int k = 20;
		int v;
		float sign = 1.0f;

		v = val & 0x3FFFFFFF;
		if (((val >> 24) & 0x3F) == 0x3F) {
			f = (double) (1 / (double) (1 << (15 - ((v >> 20) & 0xF))))
					+ (double) ((v >> 16) & 0xF)
					/ (double) (1 << (19 - (v >> 20) & 0xf))
					+ (double) ((v >> 12) & 0xF)
					/ (double) (1 << (23 - (v >> 20) & 0xf))
					+ (double) ((v >> 8) & 0xF)
					/ (double) (1 << (27 - (v >> 20) & 0xf))
					+ (double) ((v >> 4) & 0xF)
					/ (double) (1 << (31 - (v >> 20) & 0xf))
					+ (double) ((v >> 0) & 0xF)
					/ (double) (1 << (31 - (v >> 20) & 0xf)) / 16.0;
		} else if (((val >> 28) & 0x4) == 0x4) {
			f = (1 << ((v >> k) + 1));
			while (k > 0) {
				k -= 4;
				f += (double) ((v >> k) & 0xF) / (1 << (18 - k));
			}
		}
		return f * sign;
	}
}
