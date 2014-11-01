package Interpreter;

import Program.CompilationException;
import Test.Debug;

public enum Error {
	SYNTAX_ERROR(1), BAD_GOTO(2), BAD_LABELS(3), UNINIT_VAR_USED(4);
	public int code;
	
	private Error(int code) {
		this.code = code;
	}
	
	public static void error(int line, Error err) throws CompilationException {
		error(line, err, new Exception());
	}

	public static void error(int line, Error err, Exception e) throws CompilationException {
		Debug.debug(e);
		if (err.code < 4) {
			throw new CompilationException(err.code);
		} else {
			Printer.PrintError(line, err.code);
			Debug.debug("Runtime exception!");
			throw new RuntimeException();
		}
	}
}
