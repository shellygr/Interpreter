package Interpreter;

public class Variable {
	public static final int VAR_SIZE = 1;
	
	private char name;
	
	public Variable(char name) {
		if (isLegalName(name)) {
			this.name = name;
		} else {
			this.name = 0;
		}
	}

	public boolean isLegalName() {
		return isLegalName(name);
	}
	
	public static boolean isLegalName(char givenName) {
		if ('a' <= givenName 
				&& givenName <= 'z') {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variable other = (Variable) obj;
		if (name != other.name)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Variable [name=" + name + "]";
	}

	
	
}
