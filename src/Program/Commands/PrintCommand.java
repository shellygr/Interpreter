package Program.Commands;

import Interpreter.InterpreterEnvironment;
import Interpreter.Error;
import Interpreter.Printer;
import Program.Expression.Expression;
import Program.Expression.ExpressionFactory;
import Test.Debug;

public class PrintCommand implements Command {
	
	Expression expression;
	
	public PrintCommand(String cmdString, int lineNumber) {
		cmdString = cmdString.trim();
		
		String prefix = "print";
		String suffix = ")";
		if (cmdString.indexOf(prefix) != 0
				|| cmdString.lastIndexOf(suffix) != cmdString.length()-1)
		{
			Error.error(lineNumber, Error.SYNTAX_ERROR);
		}
		
		cmdString = cmdString.substring(prefix.length()).trim();
		Debug.debug("Look for '(': " + cmdString);
		if (cmdString.charAt(0) != '(') {
			Error.error(lineNumber, Error.SYNTAX_ERROR);
		}
		
		String expressionToParseString = cmdString.substring(1, cmdString.length()-1).trim();
			
		StringBuilder expressionToParse =  new StringBuilder(expressionToParseString);
		Debug.debug("Getting expression for print command: " + expressionToParseString);
		expression = ExpressionFactory.buildExpression(expressionToParse, lineNumber);
		
		if (expression == null) {
			Debug.debug("Null expression for print command");
			Error.error(lineNumber, Error.SYNTAX_ERROR);
		}
	}

	@Override
	public void run(InterpreterEnvironment environment) {
		Printer.Print(expression.evaluate(environment));
	}

	@Override
	public String toString() {
		return "PrintCommand [expression=" + expression + "]";
	}
	
	
}
