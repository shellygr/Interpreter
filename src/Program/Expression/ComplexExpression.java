package Program.Expression;

import Interpreter.Error;
import Interpreter.InterpreterEnvironment;
import Program.Operations.BinaryOp;
import Test.Debug;

public class ComplexExpression implements Expression {

	private BinaryOp binaryOp;
	private Expression exprLeft;
	private Expression exprRight;
	
	// TODO: I enforce spaces here because that's polish notation so I'm not confined to C-style spacing.
	public ComplexExpression(RawExpression rawExpression) {
		try {
			String binaryOpString = rawExpression.getFirstWord();
			binaryOp = BinaryOp.parseBinaryOp(binaryOpString);
			if (binaryOp == BinaryOp.INVALID) {
				Error.error(rawExpression.getLineNumber(), Error.SYNTAX_ERROR);
			}
			
			exprLeft = rawExpression.parseExpressionString();
			exprRight = rawExpression.parseExpressionString();
		} catch (Exception e) {
			Error.error(rawExpression.getLineNumber(), Error.SYNTAX_ERROR, e);
		}
	}

	@Override
	public int evaluate(InterpreterEnvironment environment) {
		int valLeft = exprLeft.evaluate(environment);
		int valRight = exprRight.evaluate(environment);
		
		int computedVal = binaryOp.compute(valLeft, valRight);
		Debug.debug("Evaluated val of " + this + " = " + computedVal);
		
		return computedVal;
	}

	@Override
	public String toString() {
		return "ComplexExpression [binaryOp=" + binaryOp + ", exprLeft="
				+ exprLeft + ", exprRight=" + exprRight + "]";
	}
	
	

}
