package Program.Commands;

import Program.CompilationException;

public class CommandFactory {
	
	public static Command buildCommandByType(String cmdString, int lineNumber) throws CompilationException {
		
		// Very basic testing for type, Command constructor will check for syntax errors
		if (isIf(cmdString)) { // First letter is "i"
			return new IfCommand(cmdString, lineNumber);	
		} else if (isGoto(cmdString)) { // First letter is "g"
			return new GotoCommand(cmdString, lineNumber);
		} else if (isPrint(cmdString)) { // First letter is "p"
			return new PrintCommand(cmdString, lineNumber);
		} else { // Must be assignment then.
			return new AssignmentCommand(cmdString, lineNumber);
		}
	}
	
	private static boolean isIf(String cmdString) {
		return testFirstLetter(cmdString, 'i');
	}
	
	private static boolean isGoto(String cmdString) {
		return testFirstLetter(cmdString, 'g');
	}
	
	private static boolean isPrint(String cmdString) {
		return testFirstLetter(cmdString, 'p');
	}

	private static boolean testFirstLetter(String string, char letter) {
		return string.charAt(0) == letter;
	}

}
