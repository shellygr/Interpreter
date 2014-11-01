package Program.Expression;

import Test.Debug;

public class ExpressionFactory {

	public static Expression buildExpression(StringBuilder exprString, int lineNumber) {
		RawExpression rawExpression = new RawExpression(exprString.toString().trim(), lineNumber);
		
		Expression retExpression = rawExpression.parseExpressionString();
		exprString.replace(0, exprString.length(), rawExpression.getExprString().trim());
		
		Debug.debug("Returning built expression: " + retExpression);
		return retExpression;
	}
	
}
