package Program.Expression;

import Program.CompilationException;
import Test.Debug;
import Interpreter.Error;

public class ExpressionFactory {

	public static Expression buildExpression(StringBuilder exprString, int lineNumber) throws CompilationException {
		RawExpression rawExpression = new RawExpression(exprString.toString(), lineNumber);
		
		Expression retExpression = rawExpression.parseExpressionString();
		Debug.debug(rawExpression.getExprString() + " of length " + rawExpression.getExprString().length());
		if (rawExpression.getExprString().contains("  ")) {
			Error.error(lineNumber, Error.SYNTAX_ERROR);
		}
		
		exprString.replace(0, exprString.length(), rawExpression.getExprString());
				
		Debug.debug("Returning built expression: " + retExpression);
		return retExpression;
	}
	
}
