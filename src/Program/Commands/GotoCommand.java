package Program.Commands;

import java.util.HashMap;
import java.util.Map;

import Interpreter.Error;
import Interpreter.InterpreterEnvironment;
import Test.Debug;

public class GotoCommand implements Command {

	private Integer cmdLabel;
	private static Map<Integer, Integer> commandLabelsInProgram = new HashMap<Integer, Integer>();
	
	public GotoCommand(String cmdString, int lineNumber) {
		String prefix = "goto ";
		
		if ((cmdString.indexOf(prefix) != 0)
			|| (cmdString.length() == prefix.length()))
		{
			Error.error(lineNumber, Error.SYNTAX_ERROR);
		}
		
		try {
			cmdLabel = Integer.valueOf(cmdString.substring(prefix.length()).trim());		
			commandLabelsInProgram.put(cmdLabel, lineNumber); // Added once per constructor (new goto command) run.
		} catch (Exception e) {
			Error.error(lineNumber, Error.SYNTAX_ERROR, e);
		}		
	}

	@Override
	public void run(InterpreterEnvironment environment) {
		environment.setLabel(cmdLabel);
		environment.setGotoIssued(true);
	}
	
	public static void reset() {
		Debug.debug("Resetting static for proper testing of multiple runs");
		commandLabelsInProgram = new HashMap<Integer, Integer>();
	}
	

	public static Map<Integer, Integer> getGotoCommandLabelsInProgram() {
		return commandLabelsInProgram;
	}

	@Override
	public String toString() {
		return "GotoCommand [cmdLabel=" + cmdLabel + "]";
	}

}
