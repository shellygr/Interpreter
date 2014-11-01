package Program.Expression;

import Interpreter.Variable;
import Program.CompilationException;
import Program.Operations.BinaryOp;
import Test.Debug;

public class RawExpression {

	private String exprString;
	private int lineNumber;
	
	public RawExpression(String exprString, int lineNumber) {
		this.exprString = exprString;
		this.lineNumber = lineNumber;
	}
	
	public Expression parseExpressionString() throws CompilationException {
		// Very basic testing for type, Expression constructor will check for syntax errors
		Debug.debug(exprString);
		if (exprString.isEmpty()) {
			return null;
		}
		
		if (isVar(exprString)) { // First letter is alphabet
			Debug.debug(exprString + " is a Var");
			return new VarExpression(this);	
		} else if (isNum(exprString)) { // First letter is numeric
			Debug.debug(exprString + " is a Num");
			return new NumExpression(this);
		} else if (isComplex(exprString)) { // Complex expression
			Debug.debug(exprString + " is a complex expression");
			return new ComplexExpression(this);
		} else {
			return null;
		}
	}
	
	private static boolean isVar(String exprString) {
		return Variable.isLegalName(exprString.charAt(0));
	}
	
	private static boolean isComplex(String exprString) {
		return BinaryOp.parseBinaryOp(exprString.substring(0,1)) != BinaryOp.INVALID;
	}

	private static boolean isNum(String exprString) {
		char firstCh = exprString.charAt(0);
		
		boolean isNonZeroNumber = charIs1to9(firstCh);
		
		if (isNonZeroNumber) {
			return true;
		}
		
		// Else - can only be a 0. Verify.
		if (exprString.length() == 1) {
			return '0' == firstCh;
		}
		
		char secondCh = exprString.charAt(1);
		boolean isZeroNumber = ('0' == firstCh  // First is 0
									&& (Character.isWhitespace(secondCh) || ';' == secondCh)); // The seocnd is whitespace, or ';'
		
		return isZeroNumber;
	}
	
	public static boolean charIs1to9(char ch) {
		return '1' <= ch
					&& ch <= '9';
	}
	
	public String getFirstWord() {
		String firstWord = null;
		
		// Extract first word
		int firstDelimiterIndex = findFirstSpaceOrEndOfWord();
		
		if (firstDelimiterIndex == -1) {
			firstWord = exprString;
		} else {
			firstWord = exprString.substring(0, firstDelimiterIndex);
			exprString = exprString.substring(firstDelimiterIndex+1);
		}
		
		Debug.debug("First word: " + firstWord);
		
		return firstWord;
	}
	
	private int findFirstSpaceOrEndOfWord() {
		for (int i = 0 ; i < exprString.length() ; ++i) {
			char currentChar = exprString.charAt(i);
			if (Character.isWhitespace(currentChar)) {
					return i;
				}
		}
		
		return -1;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public String getExprString() {
		return exprString;
	}
	
	
}
