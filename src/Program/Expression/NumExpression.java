package Program.Expression;

import Interpreter.Error;
import Interpreter.InterpreterEnvironment;

public class NumExpression implements Expression {

	int num;
	
	public NumExpression(RawExpression rawExpression) {
		try {
			// Extract first word
			String numString = rawExpression.getFirstWord();			
			num = Integer.parseInt(numString);
		} catch (Exception e) {
			Error.error(rawExpression.getLineNumber(), Error.SYNTAX_ERROR, e);
		}
	}

	@Override
	public int evaluate(InterpreterEnvironment environment) {
		return num;
	}

	@Override
	public String toString() {
		return "NumExpression [num=" + num + "]";
	}

	
}
