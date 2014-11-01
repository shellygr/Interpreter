package Program;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import Interpreter.Error;
import Program.Commands.Command;
import Program.Commands.GotoCommand;
import Test.Debug;

public class Program {

	private StatementList stmtList;
	private SortedMap<Integer, Command> cmdLabelToCmdMap;
	
	public Program(BufferedReader reader) {
		stmtList = new StatementList(reader);
		buildMapAndVerify();
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
				Error.error(currentStatementIndex, Error.BAD_LABELS);
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
				Error.error(gotoLabelsToLineNumberMap.get(gotoLabel), Error.BAD_GOTO);
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
