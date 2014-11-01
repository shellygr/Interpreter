package Program;

import java.io.BufferedReader;
import java.util.ArrayList;

import Test.Debug;

public class StatementList {
	private ArrayList<Statement> statements;
	
	public StatementList(BufferedReader reader) {
		statements = new ArrayList<Statement>();
		
		String line = null;
		
		try {
			int lineNumber = 0;
			while ((line = reader.readLine()) != null) {
				++lineNumber;
				line = line.trim();
				
				Debug.debug("Line Number: " + lineNumber + " : " + line);
				
				Statement currentStatement = new Statement(line, lineNumber);
				statements.add(currentStatement);			
			}
		} catch (Exception e) {
			System.err.println("IO Error: " + e);
			Debug.debug(e);
			return;
		}
	}

	public ArrayList<Statement> getStatements() {
		return statements;
	}
	
	
}
