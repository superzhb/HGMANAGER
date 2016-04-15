package test;

import java.util.List;

import com.hengguang.jni.ReadRdf;

public class ReadData {
	public static void main(String[] args) {
		ReadRdf rdf = new ReadRdf();
		List<Double> bodyData = rdf.getBodyData("d:/test.rdf");
		int t = bodyData.size() - 1;

		int m = 0;
		// 计算某个点的TDEV数据
		int n = 3000;

		double res = 0;
		double n2 = 0;
		for (int j = 1; j <= t - 3 * n + 1; j++) {
			double n1 = 0;
			for (int i = j; i <= n + j - 1; i++) {
				n1 += bodyData.get(i + 2 * n) - 2 * bodyData.get(i + n)
						+ bodyData.get(i);
			}
			n2 += Math.pow(n1, 2);
			res = n2 / (6 * Math.pow(n, 2) * (t - 3 * n + 1));
		}
		System.out.println(Math.sqrt(res) + "-------" + m++);
	}
}
