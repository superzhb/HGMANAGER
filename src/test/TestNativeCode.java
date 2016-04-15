package test;

public class TestNativeCode {
	public static void main(String[] args) {
		int i1 = 0x06;
		int i2 = 0xf1;
		int i3 = 0x81;
		int i4 = 0x1d;
		int i5 = 0xff;
		int i6 = 0x05;
		int i7 = 0xe8;
		int i8 = 0x3f;
		// System.out.println(i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8);
		System.out.println(int2dbl(960));
	}

	static double int2dbl(int val) {
		double f = 0.0;
		int k = 20;
		int v;
		int c = 0;
		float sign = 1.0f;

		v = val & 0x3FFFFFFF;
		if ((val & 0x80000000) == 0x80000000)
			sign = -1.0f;
		if (((val >>> 24) & 0x3F) != 0) {
			f = (double) (1 / (double) (1 << (15 - ((v >> 20) & 0xF))))
					+ (double) ((v >>> 16) & 0xF)
					/ (double) (1 << (19 - (v >>> 20) & 0xf))
					+ (double) ((v >>> 12) & 0xF)
					/ (double) (1 << (23 - (v >>> 20) & 0xf))
					+ (double) ((v >>> 8) & 0xF)
					/ (double) (1 << (27 - (v >>> 20) & 0xf))
					+ (double) ((v >>> 4) & 0xF)
					/ (double) (1 << (31 - (v >>> 20) & 0xf))
					+ (double) ((v >>> 0) & 0xF)
					/ (double) (1 << (31 - (v >>> 20) & 0xf)) / 16.0;
		} else if (((val >>> 28) & 0x4) == 0x04) {
			f = (1 << ((v >> k) + 1));
			while (k > 0) {
				k -= 4;
				f += (double) ((v >> k) & 0xF) / (1 << (18 - k));
			}
		} else {
			System.out.println("error\n");
		}
		return f * sign;
	}
}
