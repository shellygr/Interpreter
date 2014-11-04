package Program.Operations;

import javax.management.RuntimeErrorException;

public enum BinaryOp {
	ADD, MULT, MINUS, DIVIDE, INVALID;
	
	public int compute(int varLeft, int varRight) {
		int retVal = 0;
		
		switch (this) {
			case ADD:
				retVal = varLeft + varRight;
				break;
			case MULT:
				retVal = varLeft * varRight;
				break;
			case MINUS:
				retVal = varLeft - varRight;
				break;
			case DIVIDE:
				retVal = varLeft / varRight;
				break;
			default:
				// Can't happen if parsing was OK
				throw new RuntimeErrorException(new Error("Bad BinaryOp"), "Bad BinaryOp --> There was an uncaught error in parsing");
		}
		
		return retVal;
	}

	public static BinaryOp parseBinaryOp(String op) {
		if (op.length() != 1) {
			return INVALID;
		}
		
		switch (op.charAt(0)) {
			case '+':
				return ADD;
			case '*':
				return MULT;
			case '-':
				return MINUS;
			case '\\':
				return DIVIDE;
			default:
				return INVALID;
		}	
	}
}
