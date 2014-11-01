package Test;

public class Debug {
	public static boolean DEBUG = false;
	
	public static void debug(String str) {
		if (DEBUG) {
			System.out.println(str);
		}
	}

	public static void debug(Throwable throwable) {
		if (DEBUG) {
			throwable.printStackTrace();
		}
	}
}
