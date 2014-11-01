package Interpreter;

import java.io.BufferedReader;
import java.util.SortedMap;

import Program.CompilationException;
import Program.Program;
import Program.Commands.Command;
import Test.Debug;

public class Interpreter {
	
		private InterpreterEnvironment environment = null;
		private boolean isFinishedRunning = false;
		
		public Interpreter() {
			environment = new InterpreterEnvironment();
			isFinishedRunning = false;
		}
		
		// Assumes p is initialised with source text
		public void run(Program p) {
			environment.init(p.getCmdLabelToCmdMap().keySet()); // Set is sorted! Will init currentCmdLabel to first command.
			if (environment.getCurrentCmdLabel() == null) {
				isFinishedRunning = true;
			}
			
			while (!isFinishedRunning) {
				environment.updateLine(); // Must update the line number for correct error messages
				
				Command currentCmd = p.getCmdLabelToCmdMap().get(environment.getCurrentCmdLabel());
				Debug.debug("Running cmd: " + currentCmd.toString());
				
				currentCmd.run(environment); // Will take necessary action according to the statement
				if (environment.isGotoIssued()) { // Goto could be issued in an if command
					environment.setGotoIssued(false);
					continue; // Goto executed successfully - tested by run() method.
				}
				
				if (environment.getCurrentCmdLabel().equals(p.getCmdLabelToCmdMap().lastKey())) { // We just executed the last command label
					isFinishedRunning = true;
				} else {
					Integer nextCmdLabel = getNextCmdLabel(p.getCmdLabelToCmdMap(), environment.getCurrentCmdLabel());
					environment.setLabel(nextCmdLabel);	
					// Ready and set for next command
				}
			}
		}

		private Integer getNextCmdLabel(SortedMap<Integer, Command> map, int currentCmdLabel) {
			return (map.subMap(currentCmdLabel+1, map.lastKey()+1)).firstKey(); // Use subMap to get the following statement
		}
		
		@SuppressWarnings("finally")
		public static int runProgramFromReader(BufferedReader reader) {
			boolean hadError = false;
			Program program = null;
			
			try {
				program = new Program(reader);
				Interpreter interpreter = new Interpreter();
			
				interpreter.run(program);
			} catch (CompilationException e) {
				hadError = true;
			} catch (RuntimeException e) {
				hadError = true;
			} finally {
				if (program != null) {
					program.fini();
				}
				
				return (hadError ? 1 : 0);
			}
		}
		
}
