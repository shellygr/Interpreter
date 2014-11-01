package Program;

public class CompilationException extends Exception {

	private int errorCode;
	
	public CompilationException(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
