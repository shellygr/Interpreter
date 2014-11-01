package Program;

import Interpreter.Error;
import Program.Commands.Command;
import Program.Commands.CommandFactory;
import Test.Debug;

public class Statement {
	Integer num = null;
	Command cmd = null;
	
	public Statement(String statementSource, int lineNumber) throws CompilationException {
		int delimiterNumCmd = statementSource.indexOf(" : ");
		
		if ((delimiterNumCmd == -1) || // No command label
			(statementSource.length() <= delimiterNumCmd+1) // No command
			) {
			Error.error(lineNumber, Error.SYNTAX_ERROR);
		}
			
		String numString = statementSource.substring(0, delimiterNumCmd);
		String cmdString = statementSource.substring(delimiterNumCmd+2, statementSource.length()-1).trim(); // Trimming ';' too
		
		if (numString.equals("") 
				|| cmdString.equals("")
				|| (numString.charAt(0) == '0' && numString.length() > 1)) {
			Error.error(lineNumber, Error.SYNTAX_ERROR);
		}
		
		num = Integer.valueOf(numString);
		Debug.debug("Got command num " + num);
		if (num < 0) {
			Debug.debug("Negative command label error");
			Error.error(lineNumber, Error.SYNTAX_ERROR);
		}
		
		cmd = CommandFactory.buildCommandByType(cmdString, lineNumber);
		
		if (!statementSource.endsWith(" ;")) { // Doesn't end with " ;"
			Error.error(lineNumber, Error.SYNTAX_ERROR);
		}
	}

	public Integer getNum() {
		return num;
	}

	public Command getCmd() {
		return cmd;
	}
	
	
	
}
