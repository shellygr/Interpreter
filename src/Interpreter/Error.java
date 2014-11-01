package Interpreter;

import Test.Debug;

public enum Error {
	SYNTAX_ERROR(1), BAD_GOTO(2), BAD_LABELS(3), UNINIT_VAR_USED(4);
	public int code;
	
	private Error(int code) {
		this.code = code;
	}
	
	public static void error(int line, Error err) {
		Printer.PrintError(line, err.code);
		Debug.debug(new Exception());
		System.exit(1);
	}

	public static void error(int line, Error err, Exception e) {
		Printer.PrintError(line, err.code);
		Debug.debug(e);
		System.exit(1);
	}
}
