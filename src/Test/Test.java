package Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import Interpreter.Interpreter;

public class Test {
	
	public static final String EMPTY_PROGRAM = "";
	
	public static final String EXERCISE_PROGRAM = "" +
			"10: x := 1;"
			+ "\n" + "20: y := 100;"
			+ "\n" + "30: z := 0;"
			+ "\n" + "40: if (x > y) if (y == 100) goto 90;"
			+ "\n" + "50: z := + z x;"
			+ "\n" + "60: x := + x - 2 1;"
			+ "\n" + "70: goto 40;"
			+ "\n" + "90: print(z);"
			;
	
	public static final String EXERCISE_PROGRAM_MINIMAL_SPACES_GOTO_SPACE_FAIL = "" +
			"10:x:=1;"
			+ "\n" + "20:y:=100;"
			+ "\n" + "30:z:=0;"
			+ "\n" + "40:if(x>y)if(y==100)goto90;"
			;
	
	public static final String EXERCISE_PROGRAM_MINIMAL_SPACES_FAIL_TO_PARSE_REVERSE_POLISH_NOTATION = "" +
			"10:x:=1;"
			+ "\n" + "20:y:=100;"
			+ "\n" + "30:z:=0;"
			+ "\n" + "40:if(x>y)if(y==100)goto 90;"
			+ "\n" + "50:z:=+z x;"
			;
	
	public static final String EXERCISE_PROGRAM_EXTRA_SPACES = "" +
			"10: x :=    1;"
			+ "\n" + "20: y    :=    100;"
			+ "\n" + "30:       z := 0;"
			+ "\n" + "40: if (    x  >  y  )   if    (y  ==   100    )    goto    90;"
			+ "\n" + "50: z :=    + z    x   ;    "
			+ "\n" + "60: x :=  +      x    - 2    1;"
			+ "\n" + "70: goto 40;"
			+ "\n" + "90: print (  z   );"
			;
	
	public static final String PRINT_COMMAND_WITH_NUM =  "" +
			"22: print(42   );" 
			;
	
	public static final String EMPTY_PRINT_COMMAND_1 =  "" +
			"22: print();" 
			;
	
	public static final String EMPTY_PRINT_COMMAND_2 =  "" +
			"23: print(   \t\t  );" 
			;
	
	public static final String EMPTY_IF_COMMAND_1 =  "" +
			"32: if() print(0);" 
			;
	
	public static final String EMPTY_IF_COMMAND_2 =  "" +
			"33: if(   \t\t  ) print(1);" 
			;
	
	public static final String EMPTY_GOTO_COMMAND_1 =  "" +
			"42: goto ;" 
			;
	
	public static final String EMPTY_GOTO_COMMAND_2 =  "" +
			"43: goto;" 
			;
		
	public static final String EMPTY_ASSIGN_COMMAND_1 =  "" +
			"52: x := ;" 
			;
	
	public static final String EMPTY_ASSIGN_COMMAND_2 =  "" +
			"53:    := 0;" 
			;
	
	public static final String INVALID_ASSIGN_COMMAND_2 =  "" +
			"66: 4 := 3;" 
			;
	
	public static final String MISSING_NEKPAS =  "" +
			"70: print(7);"
			+ "\n" + "71: print(8)"
			;
	
	public static final String NEGATIVE_LABEL_SHOULD_FAIL =  "" +
			"-1: print(10);"
			;
	
	public static final String ZERO_LABEL =  "" +
			"0: print(100);"
			;
	
	public static final String ZERO_LABEL_NOT_ACCORDING_TO_CFG =  "" +
			"0123: print(100);"
			;
	
	public static final String NOT_MONOTONIC_LABELS_1 =  "" +
			"500: print(500);"
			+ "\n" + "250: print(250);"
			;
	
	public static final String NOT_MONOTONIC_LABELS_2 =  "" +
			"300: print(300);"
			+ "\n" + "300: print(300);"
			;
	
	public static final String MULTI_PRINT =  "" +
			"1: print(100);"
			+ "\n" + "2: print(200);"
			;
	
	public static final String BAD_GOTO_TEST =  "" +
			"1: goto 10;"
			;
	
	public static final String BAD_GOTO_TEST_INFINITE_LOOP =  "" +
			"1: goto 1;"
			;
	
	public static final String UNINIT_VAR =  "" +
			"1: print(x);"
			;
	
	public static final String ILLEGAL_VAR_NAME =  "" +
			"1: X := 2;"
			;
	
	public static final String COMPLEX_IF =  "" +
			"1: x := 2;"
			+ "\n" + "2: y := 4;"
			+ "\n" + "3: if (* x x == y) print(1);"
			+ "\n" + "4: if (x == / y 2) print(1);"
			+ "\n" + "5: if (+ x x == * x x) print(1);"
			+ "\n" + "6: if (+ x + x + x x > - x x) print(1);"
			+ "\n" + "7: if (+ x x <= * x x) print(1);"
			+ "\n" + "8 : if (+ x x > / 2 y) print(1);"
			;
	
	// In comment: failing tests (if they fail, means the test was successful)
	public static void main(String[] args) {
		try {
			runAllTests();
		} catch (Throwable t) {
			System.err.println("An error occurred during the tests");
			t.printStackTrace();
		}
	}

	private static void runAllTests() {
		Debug.DEBUG = true;
		test("EMPTY_PROGRAM", EMPTY_PROGRAM);
		test("EXERCISE_PROGRAM", EXERCISE_PROGRAM);
		//test("EXERCISE_PROGRAM_MINIMAL_SPACES_GOTO_SPACE_FAIL", EXERCISE_PROGRAM_MINIMAL_SPACES_GOTO_SPACE_FAIL);
		//test("EXERCISE_PROGRAM_MINIMAL_SPACES_FAIL_TO_PARSE_REVERSE_POLISH_NOTATION", EXERCISE_PROGRAM_MINIMAL_SPACES_FAIL_TO_PARSE_REVERSE_POLISH_NOTATION);
		test("EXERCISE_PROGRAM_EXTRA_SPACES", EXERCISE_PROGRAM_EXTRA_SPACES);
		test("PRINT_COMMAND_WITH_NUM", PRINT_COMMAND_WITH_NUM);
		//test("EMPTY_PRINT_COMMAND_1", EMPTY_PRINT_COMMAND_1);
		//test("EMPTY_PRINT_COMMAND_2", EMPTY_PRINT_COMMAND_2);
		//test("EMPTY_IF_COMMAND_1", EMPTY_IF_COMMAND_1);
		//test("EMPTY_IF_COMMAND_2", EMPTY_IF_COMMAND_2);
		//test("EMPTY_GOTO_COMMAND_1", EMPTY_GOTO_COMMAND_1);
		//test("EMPTY_GOTO_COMMAND_2", EMPTY_GOTO_COMMAND_2);
		//test("EMPTY_ASSIGN_COMMAND_1", EMPTY_ASSIGN_COMMAND_1);
		//test("EMPTY_ASSIGN_COMMAND_2", EMPTY_ASSIGN_COMMAND_2);
		//test("MISSING_NEKPAS", MISSING_NEKPAS);
		//test("NEGATIVE_LABEL_SHOULD_FAIL", NEGATIVE_LABEL_SHOULD_FAIL);
		test("ZERO_LABEL", ZERO_LABEL);
		//test("ZERO_LABEL_NOT_ACCORDING_TO_CFG", ZERO_LABEL_NOT_ACCORDING_TO_CFG);
		//test("NOT_MONOTONIC_LABELS_1", NOT_MONOTONIC_LABELS_1);
		//test("NOT_MONOTONIC_LABELS_2", NOT_MONOTONIC_LABELS_2);
		test("MULTI_PRINT", MULTI_PRINT);
		//test("BAD_GOTO_TEST", BAD_GOTO_TEST);
		//test("BAD_GOTO_TEST_INFINITE_LOOP", BAD_GOTO_TEST_INFINITE_LOOP);
		//test("UNINIT_VAR", UNINIT_VAR);
		//test("ILLEGAL_VAR_NAME", ILLEGAL_VAR_NAME);
		test("COMPLEX_IF", COMPLEX_IF);
	}
	
	private static void test(String testName, String programStr) {
		System.out.println(testName);
		testProgramAsString(programStr);
		System.out.println("");
	}

	public static void testProgramAsString(String programStr) {
		InputStream is = new ByteArrayInputStream(programStr.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		try {
			Interpreter.runProgramFromReader(br);
		} catch (Throwable t) {
			System.err.println("--------------->FAILED : " + t);
			t.printStackTrace();
			return;
		}
		
		System.out.println("--------------->SUCCESS");
	}
}
