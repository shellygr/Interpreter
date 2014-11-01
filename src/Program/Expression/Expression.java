package Program.Expression;

import Interpreter.InterpreterEnvironment;

public interface Expression {

	public int evaluate(InterpreterEnvironment environment);

	public String toString();
}
