package Program.Commands;

import Interpreter.InterpreterEnvironment;
import Interpreter.Variable;
import Program.CompilationException;
import Program.Operations.BoolOp;
import Test.Debug;
import Interpreter.Error;

public class IfCommand implements Command {

	private Variable varLeft;
	private Variable varRight;
	private BoolOp boolOp;
	private Command command;
	
	public IfCommand(String cmdString, int lineNumber) throws CompilationException {
		// One big try-catch to match all syntax errors not caught elsewhere.
		try {
			String prefix = "if(";
			if (cmdString.indexOf(prefix) != 0) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
			
			StringBuilder cutString = new StringBuilder(cmdString.substring(prefix.length()).trim());
				
			// START CALC VAR LEFT
			Debug.debug("Looking for varLeft: " + cutString);
			varLeft = new Variable(cutString.charAt(0));
			if (!varLeft.isLegalName()) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
			// END CALC VAR LEFT
			
			// START CALC BOOL OP
			cutString.delete(0, Variable.VAR_SIZE+1);
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
			cutString.delete(0, lenOfBoolOp+1); // Step after the boolOp
			Debug.debug("Looking for varRight: " + cutString);
			varRight = new Variable(cutString.charAt(0));
			if (!varRight.isLegalName()) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
			// END CALC VAR RIGHT		
			
			cutString.delete(0, Variable.VAR_SIZE);
			Debug.debug("Looking for ') ': " + cutString);
			if (cutString.indexOf(")") != 0) {
				Error.error(lineNumber, Error.SYNTAX_ERROR);
			}
			
			cutString.delete(0, ") ".length()); // Now we get to the command
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
		int valLeft = environment.getValue(varLeft);
		int valRight = environment.getValue(varRight);
		
		return boolOp.compute(valLeft, valRight);
	}

	@Override
	public String toString() {
		return "IfCommand [varLeft=" + varLeft + ", varRight=" + varRight
				+ ", boolOp=" + boolOp + ", command=" + command + "]";
	}
	
	
}
