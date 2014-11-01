package Program.Commands;

import Interpreter.InterpreterEnvironment;
import Program.Expression.Expression;
import Program.Expression.ExpressionFactory;
import Program.Operations.BoolOp;
import Test.Debug;
import Interpreter.Error;

public class IfCommand implements Command {

	private Expression exprLeft;
	private Expression exprRight;
	private BoolOp boolOp;
	private Command command;
	
	public IfCommand(String cmdString, int lineNumber) {
		// One big try-catch to match all syntax errors not caught elsewhere.
		try {
			String prefix = "if";
			if (cmdString.indexOf(prefix) != 0) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
			
			StringBuilder cutString = new StringBuilder(cmdString.substring(prefix.length()).trim());
			
			// Note: Var and BoolOp do not contain '(' neither ')'
			
			if (cutString.indexOf("(") != 0) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
			
			// START CALC VAR LEFT
			cutString.deleteCharAt(0); // Step 1 more char (after '(') 
			Debug.debug("Looking for exprLeft: " + cutString);
			exprLeft = ExpressionFactory.buildExpression(cutString, lineNumber);
			// END CALC VAR LEFT
			
			// START CALC BOOL OP
			Debug.debug("Looking for boolOp: " + cutString);
			
			int lenOfBoolOp = 0;
			if (cutString.charAt(1) == '=') { // It is a BoolOp of 2 chars
				lenOfBoolOp = 2;
			} else { // It is a BoolOp of 1 char
				lenOfBoolOp = 1;
			}
			
			String boolOpString = cutString.substring(0, lenOfBoolOp);
			boolOp = BoolOp.parseBoolOp(boolOpString);
			if (boolOp == BoolOp.INVALID) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
			// END CALC BOOL OP
			
			// START CALC VAR RIGHT
			cutString.delete(0, lenOfBoolOp); // Step after the boolOp
			Debug.debug("Looking for exprRight: " + cutString);
			exprRight = ExpressionFactory.buildExpression(cutString, lineNumber);
			// END CALC VAR RIGHT		
			
			Debug.debug("Looking for ')': " + cutString);
			if (cutString.indexOf(")") != 0) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
			
			cutString.deleteCharAt(0); // Now we get to the command
			Debug.debug("Looking for a command: " + cutString);
			command = CommandFactory.buildCommandByType(cutString.toString().trim(), lineNumber);
		} catch (Exception e) {
			Error.error(lineNumber, Error.SYNTAX_ERROR, e);
		}
		
	}

	@Override
	public void run(InterpreterEnvironment environment) {
		boolean clauseValue = calcClause(environment);
		
		if (clauseValue == true) {
			command.run(environment);
		}
	}

	private boolean calcClause(InterpreterEnvironment environment) {
		int valLeft = exprLeft.evaluate(environment);
		int valRight = exprRight.evaluate(environment);
		
		return boolOp.compute(valLeft, valRight);
	}

	@Override
	public String toString() {
		return "IfCommand [exprLeft=" + exprLeft + ", exprRight=" + exprRight
				+ ", boolOp=" + boolOp + ", command=" + command + "]";
	}
	
	
}
