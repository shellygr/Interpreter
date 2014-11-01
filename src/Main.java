import java.io.BufferedReader;
import java.io.FileReader;

import Interpreter.Interpreter;
import Test.Debug;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("No file given");
			System.exit(1);
		}
		
		String inputFilename = args[0];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
			Interpreter.runProgramFromReader(reader);			
		} catch (Throwable t) {
			Debug.debug(t); // Shouldn't get here.
			System.exit(1);
		}
	}
}
