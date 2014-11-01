package Program.Expression;

import Interpreter.Error;
import Interpreter.InterpreterEnvironment;
import Interpreter.Variable;

public class VarExpression implements Expression {

	Variable var;
	
	public VarExpression(RawExpression rawExpression) {
		try {
			// Extract first word
			String varString = rawExpression.getFirstWord();
			
			if (varString.length() != 1) { // Must be a valid variable word - length 1
				Error.error(rawExpression.getLineNumber(), Error.SYNTAX_ERROR);
			}
			
			var = new Variable(varString.charAt(0));
		} catch (Exception e) {
			Error.error(rawExpression.getLineNumber(), Error.SYNTAX_ERROR, e);
		}
	}
	
	@Override
	public int evaluate(InterpreterEnvironment environment) {
		return environment.getValue(var);
	}

	@Override
	public String toString() {
		return "VarExpression [var=" + var + "]";
	}
	
	

}
