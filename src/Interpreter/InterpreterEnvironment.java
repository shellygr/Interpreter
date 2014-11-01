package Interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class InterpreterEnvironment {
	private Integer currentCmdLabel;
	private int lineNumber;
	private Map<Variable, Integer> memory;
	private SortedSet<Integer> cmds;
	private boolean gotoIssued = false;

	public InterpreterEnvironment() {
		currentCmdLabel = null;
		memory = new HashMap<Variable, Integer>();
		gotoIssued = false;
	}
	
	public void putValue(Variable var, int value) {
		memory.put(var, value);
	}
	
	public int getValue(Variable var) {
		Integer value = memory.get(var);
		
		if (value == null) {
			Error.error(lineNumber, Error.UNINIT_VAR_USED);
		}
		
		return value.intValue();
	}
		
	public boolean isGotoIssued() {
		return gotoIssued;
	}

	public void setGotoIssued(boolean gotoIssued) {
		this.gotoIssued = gotoIssued;
	}

	public Integer getCurrentCmdLabel() {
		return currentCmdLabel;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public void setLabel(Integer label) {
		this.currentCmdLabel = label;
	}

	public void updateLine() {
		this.lineNumber = cmds.headSet(currentCmdLabel).size()+1;
		
	}
	
	public void init(Set<Integer> keySet) {
		cmds = (SortedSet<Integer>) keySet;
		
		if (cmds.size() == 0) {
			currentCmdLabel = null;
			return;
		}
		
		currentCmdLabel = cmds.first();
	}

}
