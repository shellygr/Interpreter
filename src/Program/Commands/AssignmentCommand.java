package Program.Commands;

import Interpreter.Error;
import Interpreter.InterpreterEnvironment;
import Interpreter.Variable;
import Program.Expression.Expression;
import Program.Expression.ExpressionFactory;
import Test.Debug;

public class AssignmentCommand implements Command {

	private Variable var;
	private Expression expression;
	
	public AssignmentCommand(String cmdString, int lineNumber) {
		String assignmentString = ":=";
		
		try {
			var = new Variable(cmdString.charAt(0)); // Handle var
			if (!var.isLegalName()) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
			
			String cutString = cmdString.substring(1).trim(); // Handle :=
			if (cutString.indexOf(assignmentString) != 0) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
			
			cutString = cutString.substring(2).trim(); // Handle expression
			expression = ExpressionFactory.buildExpression(new StringBuilder(cutString), lineNumber);
			
			Debug.debug("Got expression for assignment command: " + expression);
			if (expression == null) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
		} catch (Exception e) {
			Error.error(lineNumber, Error.SYNTAX_ERROR, e);
		}
	}

	@Override
	public void run(InterpreterEnvironment environment) {
		int expressionValue = expression.evaluate(environment);
		environment.putValue(var, expressionValue);
	}

	@Override
	public String toString() {
		return "AssignmentCommand [var=" + var + ", expression=" + expression
				+ "]";
	}
	
	
}
