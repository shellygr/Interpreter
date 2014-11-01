package Program.Commands;

import Interpreter.InterpreterEnvironment;

public interface Command {
	
	public void run(InterpreterEnvironment environment);	
	
	public String toString();
}
