package Program;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import Interpreter.Error;
import Interpreter.Printer;
import Program.Commands.Command;
import Program.Commands.GotoCommand;
import Test.Debug;

public class Program {

	private StatementList stmtList = null;
	private SortedMap<Integer, Command> cmdLabelToCmdMap = null;
	
	public Program(BufferedReader reader) throws CompilationException {
		stmtList = new StatementList(reader);
		buildMapAndVerify();
		if (printErrors(stmtList.getLineToErrors())) {
			this.fini();
			throw new CompilationException(0);
		}
	}

	private boolean printErrors(TreeMap<Integer, List<Integer>> lineToErrors) {
		boolean hadError = false;
		
		for (Integer lineNumber : lineToErrors.keySet()) {
			List<Integer> errorsForLine = lineToErrors.get(lineNumber);
			Collections.sort(errorsForLine);
			for (Integer error : errorsForLine) {
				Printer.PrintError(lineNumber, error);
				hadError = true;
			}
		}
		
		return hadError;
	}

	public void buildMapAndVerify() {
		cmdLabelToCmdMap = new TreeMap<Integer, Command>();
		
		ArrayList<Statement> statements = stmtList.getStatements();
		Integer currentCommandLabel = null;
		int currentStatementIndex = 0;
		
		if (statements.size() > 0) {
			currentCommandLabel = statements.get(0).getNum()-1; // current will be updated each round, must increase
		}
		
		for (Statement statement : statements) {
			++currentStatementIndex;
			if (statement.getNum() <= currentCommandLabel) {
				try {
					Error.error(currentStatementIndex, Error.BAD_LABELS);
				} catch (CompilationException e) {
					stmtList.putNewError(currentStatementIndex, Error.BAD_LABELS.code);
				}
			}
			
			currentCommandLabel = statement.getNum();
			cmdLabelToCmdMap.put(currentCommandLabel, statement.getCmd());
		}
		
		verifyGotos();
	}

	private void verifyGotos() {
		Map<Integer, Integer> gotoLabelsToLineNumberMap = GotoCommand.getGotoCommandLabelsInProgram();
		Debug.debug("gotos: " + gotoLabelsToLineNumberMap);
		
		for (Integer gotoLabel : gotoLabelsToLineNumberMap.keySet()) {
			if (!cmdLabelToCmdMap.keySet().contains(gotoLabel)) {
				Debug.debug("Set " + cmdLabelToCmdMap.keySet() + " doesn't contain " + gotoLabel);
				try {
					Error.error(gotoLabelsToLineNumberMap.get(gotoLabel), Error.BAD_GOTO);
				} catch (CompilationException e) {
					stmtList.putNewError(gotoLabelsToLineNumberMap.get(gotoLabel), Error.BAD_GOTO.code);
				}
			}
		}
	}
	
	public void fini() {
		GotoCommand.reset();
	}

	public SortedMap<Integer, Command> getCmdLabelToCmdMap() {
		return cmdLabelToCmdMap;
	}


}
