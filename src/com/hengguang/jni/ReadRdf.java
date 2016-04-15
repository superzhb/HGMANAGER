package com.hengguang.jni;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hengguang.model.Record;

public class ReadRdf {
	public static final int FIRSTDATA = 1812 * 16 - 1;
	static DecimalFormat df = new DecimalFormat("#0.00");

	public native String readRdfPath(long path);

	static {
		System.loadLibrary("myjdll");
	}

	public Record getHead(String path) {
		Record record = new Record();
		// 标识
		String type = encodeHead1(0, 20, path);
		record.setType(type);
		// 数据偏移地址
		String dataAdd = encodeHead1(20, 4, path);
		record.setOffset(dataAdd);

		// 名称
		String name = encodeHead1(24, 32, path);
		record.setName(name);
		// UTC秒数
		long startMi = encodeTime(59, 4, path);

		// UTC秒数
		long endMi = encodeTime(67, 4, path);

		record.setTotaldata(endMi - startMi);
		// 开始年
		long staY = encodeTime(73, 2, path);

		// 开始月
		long staM = encodeTime(75, 2, path);

		// 开始日
		long staD = encodeTime(77, 2, path);

		// 开始时
		long staH = encodeTime(79, 2, path);

		// 开始分
		long stam = encodeTime(81, 2, path);

		// 开始秒
		long staS = encodeTime(83, 2, path);

		// 结束年
		long endY = encodeTime(85, 2, path);

		// 结束月
		long endM = encodeTime(87, 2, path);

		// 结束日
		long endD = encodeTime(89, 2, path);

		// 结束时
		long endH = encodeTime(91, 2, path);

		// 结束分
		long endm = encodeTime(93, 2, path);

		// 结束秒
		long endS = encodeTime(95, 2, path);

		// 数量
		int iTotal = (int) encodeTime(141, 2, path);
		record.setTotalnum(iTotal);

		// 最大
		// System.out.println("sType:" + getMax(path));
		// 最小
		// System.out.println("sType:" + getMin(path));

		record.setStartdata(staY + "-" + staM + "-" + staD + " " + staH + ":"
				+ stam + ":" + staS);
		record.setEnddata(endY + "-" + endM + "-" + endD + " " + endH + ":"
				+ endm + ":" + endS);
		return record;
	}

	public List<Double> getBodyData(String path) {
		List<Double> data = new ArrayList<Double>();

		byte[] is = getBytes(path);
		for (int i = FIRSTDATA; i < is.length; i++) {
			if (i % 8 == 7) {
				String str = "";
				byte name1 = is[i + 4];
				byte name2 = is[i + 3];
				byte name3 = is[i + 2];
				byte name4 = is[i + 1];
				if (printHexString(name1).equalsIgnoreCase("bf")) {
					str = printHexString((byte) 3f) + printHexString(name2)
							+ printHexString(name3) + printHexString(name4);
					long b = 0;
					b = Long.parseLong(str.replaceAll("^0[x|X]", ""), 16);
					double dl = Double.valueOf(new ReadRdf().readRdfPath(b));
					if (Math.abs(dl) > 1) {
						dl = -dl + 8;
					} else {
						dl = -dl;
					}
					data.add(Double.valueOf(df.format(dl)));
				} else {
					str = printHexString(name1) + printHexString(name2)
							+ printHexString(name3) + printHexString(name4);
					long b = 0;
					b = Long.parseLong(str.replaceAll("^0[x|X]", ""), 16);
					double dl = Double.valueOf(new ReadRdf().readRdfPath(b));
					if (Math.abs(dl) > 1) {
						dl = dl - 8;
					}
					data.add(Double.valueOf(df.format(Double.valueOf(dl))));
				}
			}
		}
		return data;
	}

	private byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	private String printHexString(byte b) {
		String str = "";
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		str += hex.toUpperCase();
		return str;
	}

	private String encodeHead1(int first, int len, String path) {
		byte[] is = getBytes(path);
		String str = "";
		for (int i = first; i < first + len; i++) {
			if ((char) (is[i]) == 0) {
				continue;
			}
			str += (char) (is[i]);
		}
		return str.trim();
	}

	private String encodeHead2(int last, int len, String path) {
		byte[] is = getBytes(path);
		String str = "";
		for (int i = last; i > last - len; i--) {
			if ((char) (is[i]) == 0) {
				continue;
			}
			str += (char) (is[i]);
		}
		return str.trim();
	}

	// 转化0x67dfd4 -> 6807508 反
	private long encodeTime(int last, int len, String path) {
		byte[] is = getBytes(path);
		String str = "";
		for (int i = last; i > last - len; i--) {
			str += printHexString(is[i]);
		}
		long i = Long.parseLong(str, 16);
		return i;
	}

	// 转化0xd4df67-> 6807508 正
	private long encodeTime2(int first, int len, String path) {
		byte[] is = getBytes(path);
		String str = "";
		for (int i = first; i < first + len; i++) {
			str += printHexString(is[i]);
		}
		long i = Long.parseLong(str, 16);
		return i;
	}

	private String getMax(String path) {
		byte[] is = getBytes(path);
		byte name1 = is[151];
		byte name2 = is[150];
		byte name3 = is[149];
		byte name4 = is[148];

		String str = printHexString(name1) + printHexString(name2)
				+ printHexString(name3) + printHexString(name4);
		long b = 0;
		b = Long.parseLong(str.replaceAll("^0[x|X]", ""), 16);
		String stype = new ReadRdf().readRdfPath(b);
		return stype;
	}

	private String getMin(String path) {
		byte[] is = getBytes(path);
		byte name1 = is[159];
		byte name2 = is[158];
		byte name3 = is[157];
		byte name4 = is[156];

		String str = printHexString(name1) + printHexString(name2)
				+ printHexString(name3) + printHexString(name4);
		long b = 0;
		b = Long.parseLong(str.replaceAll("^0[x|X]", ""), 16);

		double dl = Double.valueOf(new ReadRdf().readRdfPath(b));
		if (Math.abs(dl) > 4) {
			dl = -dl + 8;
		} else {
			dl = -dl;
		}
		return df.format(dl) + "";
	}

	public Map<Double, Double> getMteiData(String path) {
		Map<Double, Double> map = new HashMap<Double, Double>();
		Record record = new ReadRdf().getHead(path);
		long total = record.getTotaldata();
		for (int i = -2; i < 100; i++) {
			double o = Math.pow(10, i);
			// 超出范围直接终止
			if (o > total) {
				break;
			}
			if (o != 0.01) {
				map.put(o, getMtie(Integer.valueOf((int) (o / 0.02)), path));
			}

			// List
			if (o * 2 < total) {
				map.put(Double.valueOf(df.format(o * 2)),
						getMtie(Integer.valueOf((int) (o * 2 / 0.02)), path));
			}
			if (o * 4 < total) {
				map.put(Double.valueOf(df.format(o * 4)),
						getMtie(Integer.valueOf((int) (o * 4 / 0.02)), path));
			}
			if (o * 6 < total) {
				map.put(Double.valueOf(df.format(o * 6)),
						getMtie(Integer.valueOf((int) (o * 6 / 0.02)), path));
			}
			if (o * 8 < total) {
				map.put(Double.valueOf(df.format(o * 8)),
						getMtie(Integer.valueOf((int) (o * 8 / 0.02)), path));
			}

		}

		return map;
	}

	private double getMtie(int position, String path) {
		List<Double> bodyData = new ReadRdf().getBodyData(path);
		List<Double> doubles = bodyData.subList(1, position + 1);
		Collections.sort(doubles);
		double min = doubles.get(0);
		double max = doubles.get(doubles.size() - 1);
		return Double.valueOf(df.format(max - min));
	}

	public double getTdev(int n) {
		ReadRdf rdf = new ReadRdf();
		List<Double> bodyData = rdf.getBodyData("d:/test.rdf");
		int t = bodyData.size() - 1;

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
		return Math.sqrt(res);
	}

}
