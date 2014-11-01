package Program.Operations;

import javax.management.RuntimeErrorException;

public enum BoolOp {
	LESS_THAN, GREAT_THAN, LESS_OR_EQUAL_THAN, GREAT_OR_EQUAL_THAN, EQUAL, NON_EQUAL, INVALID;
	
	public boolean compute(int varLeft, int varRight) {
		boolean retVal = false;
		
		switch (this) {
			case LESS_THAN:
				retVal = varLeft < varRight;
				break;
			case EQUAL:
				retVal = varLeft == varRight;
				break;
			case GREAT_OR_EQUAL_THAN:
				retVal = varLeft >= varRight;
				break;
			case GREAT_THAN:
				retVal = varLeft > varRight;
				break;
			case LESS_OR_EQUAL_THAN:
				retVal = varLeft <= varRight;
				break;
			case NON_EQUAL:
				retVal = varLeft != varRight;
				break;
			default:
				// Can't happen if parsing was OK
				throw new RuntimeErrorException(new Error("Bad BoolOp"), "Bad BoolOp --> There was an uncaught error in parsing");
		}
		
		return retVal;
	}

	public static BoolOp parseBoolOp(String op) {
		if (op.length() == 2) {
			switch (op.charAt(0)) {
				case '<':
					return LESS_OR_EQUAL_THAN;
				case '=':
					return EQUAL;
				case '>':
					return GREAT_OR_EQUAL_THAN;
				case '!':
					return NON_EQUAL;
				default:
					return INVALID;
			}	
		} else {
			switch (op.charAt(0)) {
			case '<':
				return LESS_THAN;
			case '>':
				return GREAT_THAN;
			default:
				return INVALID;
			}	
		}
	}
}
