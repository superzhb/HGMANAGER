package test;

public class TestNativeCode1 {
	public native String SayHello();

	static {
		System.loadLibrary("nativate");
	}

	public static void main(String[] args) {
		TestNativeCode1 code1 = new TestNativeCode1();
		System.out.println(code1.SayHello());
	}
}
