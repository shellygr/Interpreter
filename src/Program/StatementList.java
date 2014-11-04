package Program;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import Test.Debug;

public class StatementList {
	private ArrayList<Statement> statements = null;
	private TreeMap<Integer, List<Integer>> lineToErrors = null;
	
	public StatementList(BufferedReader reader) {
		statements = new ArrayList<Statement>();
		lineToErrors = new TreeMap<Integer, List<Integer>>();
		
		String line = null;
		
		try {
			int lineNumber = 0;
			while ((line = reader.readLine()) != null) {
				++lineNumber;
				line = line.trim();
					
					Debug.debug("Line Number: " + lineNumber + " : " + line);
					
					try {
						Statement currentStatement = new Statement(line, lineNumber);
						statements.add(currentStatement);
					} catch (CompilationException e) {
						Debug.debug("Compilation exception in line " + lineNumber + ", continuing to next line (Program will not run anyway)");
						putNewError(lineNumber, e.getErrorCode());
						statements.add(null); // Adding the null statement to maintain line numbers
					}
			}
		} catch (IOException e) {
			System.err.println("IO Error: " + e);
			Debug.debug(e);
			return;
		}
	}

	public void putNewError(int lineNumber, int errorCode) {
		List<Integer> errorsForLine = lineToErrors.get(lineNumber);
		if (errorsForLine == null) {
			errorsForLine = new ArrayList<Integer>(3);
			lineToErrors.put(lineNumber, errorsForLine);
		}
		
		if (!errorsForLine.contains(errorCode)) {
			errorsForLine.add(errorCode);
		}	
	}

	public ArrayList<Statement> getStatements() {
		return statements;
	}

	public TreeMap<Integer, List<Integer>> getLineToErrors() {
		return lineToErrors;
	}
	
	
}