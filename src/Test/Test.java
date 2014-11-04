package Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import Interpreter.Interpreter;

public class Test {
	
	public static final String EMPTY_PROGRAM = "\n";
	
	public static final String EXERCISE_PROGRAM = "" +
			"10 : x := 1 ;"
			+ "\n" + "20 : y := 100 ;"
			+ "\n" + "30 : z := 0 ;"
			+ "\n" + "40 : if(x > y) if(x != z) goto 90 ;"
			+ "\n" + "50 : z := + z x ;"
			+ "\n" + "60 : x := + x - 2 1 ;"
			+ "\n" + "70 : goto 40 ;"
			+ "\n" + "90 : print(z) ;"
			+ "\n";
	
	public static final String EXERCISE_PROGRAM_NO_SPACE_BEFORE_NEKPAS = "" +
			"10 : x := 1;"
			+ "\n" + "20 : y := 100;"
			+ "\n" + "30 : z := 0;"
			+ "\n" + "40 : if(x > y) if(x != z) goto 90;"
			+ "\n" + "50 : z := + z x;"
			+ "\n" + "60 : x := + x - 2 1;"
			+ "\n" + "70 : goto 40;"
			+ "\n" + "90 : print(z);"
			+ "\n";
	
	public static final String EXERCISE_PROGRAM_MINIMAL_SPACES_GOTO_SPACE_FAIL = "" +
			"5:a := 0 ;"
			+ "\n" +"10 :x := 1 ;"
			+ "\n" + "15: t := 0 ;"
			+ "\n" + "20 : y :=100 ;"
			+ "\n" + "30 : z:= 0 ;"
			+ "\n" + "40 : if(x > y) if(z != z) goto90;"
			+ "\n" + "40 : if(x > y) if(z != z) goto 90;"
			+ "\n" + "40 : if(x > y) if(z != z) goto90 ;"
			+ "\n";
	
	public static final String EXERCISE_PROGRAM_MINIMAL_SPACES_FAIL_TO_PARSE_REVERSE_POLISH_NOTATION = "" +
			"10 : x := 1 ;"
			+ "\n" + "20 : y := 100 ;"
			+ "\n" + "30 : z := 0 ;"
			+ "\n" + "40 : if(x > y) if(x!=z) if(x <= z)goto 90 ;"
			+ "\n" + "50 : z := +z x ;"
			+ "\n";
	
	public static final String EXERCISE_PROGRAM_EXTRA_SPACES_SHOULD_FAIL = "" +
			"10 : x :=    1 ;"
			+ "\n" + "20 : y    :=    100 ;"
			+ "\n" + "30 :       z := 0 ;"
			+ "\n" + "40 : if(    x  >  y  )   if    (x  !=   z    )    goto    90 ;"
			+ "\n" + "50 : z :=    + z    x   ;    "
			+ "\n" + "60 : x :=  +      x    - 2    1 ;"
			+ "\n" + "70 : goto 40    ;"
			+ "\n" + "90 : print (  z   ) ;"
			+ "\n";
	
	public static final String PRINT_COMMAND_WITH_NUM_AND_SPACES_SHOULD_FAIL =  "" +
			"22 : print(42   ) ;" 
			+ "\n";
	
	public static final String EMPTY_PRINT_COMMAND_1 =  "" +
			"22 : print() ;" 
			+ "\n";
	
	public static final String EMPTY_PRINT_COMMAND_2 =  "" +
			"23 : print(   \t\t  ) ;" 
			+ "\n";
	
	public static final String EMPTY_IF_COMMAND_1 =  "" +
			"32 : if() print(0) ;" 
			+ "\n" + "33 : if (x > y) ;"
			+ "\n";
	
	public static final String EMPTY_IF_COMMAND_2 =  "" +
			"34 : if(   \t\t  ) print(1) ;" 
			+ "\n";
	
	public static final String EMPTY_GOTO_COMMAND_1 =  "" +
			"42 : goto ;" 
			+ "\n";
	
	public static final String EMPTY_GOTO_COMMAND_2 =  "" +
			"43 : goto;" 
			+ "\n";
		
	public static final String EMPTY_ASSIGN_COMMAND_1 =  "" +
			"52 : x := ;" 
			+ "\n";
	
	public static final String EMPTY_ASSIGN_COMMAND_2 =  "" +
			"53 :    := 0;" 
			+ "\n";
	
	public static final String INVALID_ASSIGN_COMMAND_2 =  "" +
			"66 : 4 := 3;" 
			+ "\n";
	
	public static final String MISSING_NEKPAS =  "" +
			"70 : print(7) ;"
			+ "\n" + "71 : print(8)"
			+ "\n";
	
	public static final String NEGATIVE_LABEL_SHOULD_FAIL =  "" +
			"-1 : print(10) ;"
			+ "\n";
	
	public static final String ZERO_LABEL =  "" +
			"0 : print(100) ;"
			+ "\n";
	
	public static final String ZERO_LABEL_NOT_ACCORDING_TO_CFG =  "" +
			"0123 : print(100) ;"
			+ "\n";
	
	public static final String NOT_MONOTONIC_LABELS_1_WITH_SYNTAX_ERROR =  "" +
			"1 : print(2) ;"
			+ "\n" + "500 : print(500) ;"
			+ "\n" + "250 : print(250) ;"
			+ "\n" + "200 : print(270) ;"
			+ "\n";
	
	public static final String NOT_MONOTONIC_LABELS_2 =  "" +
			"300 : print(300);"
			+ "\n" + "300 : print(300);"
			+ "\n";
	
	public static final String MULTI_PRINT =  "" +
			"1 : print(100) ;"
			+ "\n" + "2 : print(200) ;"
			+ "\n";
	
	public static final String BAD_GOTO_TEST =  "" +
			"1 : goto 10 ;"
			+ "\n";
	
	public static final String BAD_GOTO_TEST_INFINITE_LOOP =  "" +
			"1 : goto 1 ;"
			+ "\n";
	
	public static final String UNINIT_VAR =  "" +
			"1 : print(x) ;"
			+ "\n";
	
	public static final String ILLEGAL_VAR_NAME =  "" +
			"1 : X := 2 ;"
			+ "\n";
	
	public static final String COMPLEX_IF_SHOULD_FAIL =  "" +
			"1 : x := 2 ;"
			+ "\n" + "2 : y := 4 ;"
			+ "\n" + "3 : if(* x x == y) print(1) ;"
			+ "\n" + "4 : if(x == / y 2) print(1) ;"
			+ "\n" + "5 : if(+ x x == * x x) print(1) ;"
			+ "\n" + "6 : if(+ x + x + x x > - x x) print(1) ;"
			+ "\n" + "7 : if(+ x x <= * x x) print(1) ;"
			+ "\n" + "8 : if(+ x x > / 2 y) print(1) ;"
			+ "\n";
	
	public static final String DIVIDE =  "" +
			"1 : print(\\ 8 2) ;"
			+ "\n" + "2 : x := \\ 8 2 ;"
			+ "\n" + "3 : print(x) ;"
			+ "\n";
	
	public static final String LINE_FEEDS_ONLY = "" +
			"\n"
			+ "\n"
			+ "\n"
			+ "\n";
		
	public static void main(String[] args) {
		try {
			runAllTests();
		} catch (Throwable t) {
			System.err.println("An error occurred during the tests");
			t.printStackTrace();
		}
	}

	private static void runAllTests() {
		Debug.DEBUG = false;
		test("EMPTY_PROGRAM", EMPTY_PROGRAM, 1); // The "no line-feed in end" case
		test("EXERCISE_PROGRAM", EXERCISE_PROGRAM, 0);
		test("EXERCISE_PROGRAM_NO_SPACE_BEFORE_NEKPAS", EXERCISE_PROGRAM_NO_SPACE_BEFORE_NEKPAS, 1);
		test("EXERCISE_PROGRAM_MINIMAL_SPACES_GOTO_SPACE_FAIL", EXERCISE_PROGRAM_MINIMAL_SPACES_GOTO_SPACE_FAIL ,1);
		test("EXERCISE_PROGRAM_MINIMAL_SPACES_FAIL_TO_PARSE_REVERSE_POLISH_NOTATION", EXERCISE_PROGRAM_MINIMAL_SPACES_FAIL_TO_PARSE_REVERSE_POLISH_NOTATION, 1);
		test("EXERCISE_PROGRAM_EXTRA_SPACES_SHOULD_FAIL", EXERCISE_PROGRAM_EXTRA_SPACES_SHOULD_FAIL, 1);
		test("PRINT_COMMAND_WITH_NUM_AND_SPACES_SHOULD_FAIL", PRINT_COMMAND_WITH_NUM_AND_SPACES_SHOULD_FAIL, 1);
		test("EMPTY_PRINT_COMMAND_1", EMPTY_PRINT_COMMAND_1, 1);
		test("EMPTY_PRINT_COMMAND_2", EMPTY_PRINT_COMMAND_2, 1);
		test("EMPTY_IF_COMMAND_1", EMPTY_IF_COMMAND_1, 1);
		test("EMPTY_IF_COMMAND_2", EMPTY_IF_COMMAND_2, 1);
		test("EMPTY_GOTO_COMMAND_1", EMPTY_GOTO_COMMAND_1, 1);
		test("EMPTY_GOTO_COMMAND_2", EMPTY_GOTO_COMMAND_2, 1);
		test("EMPTY_ASSIGN_COMMAND_1", EMPTY_ASSIGN_COMMAND_1, 1);
		test("EMPTY_ASSIGN_COMMAND_2", EMPTY_ASSIGN_COMMAND_2, 1);
		test("MISSING_NEKPAS", MISSING_NEKPAS, 1);
		test("NEGATIVE_LABEL_SHOULD_FAIL", NEGATIVE_LABEL_SHOULD_FAIL, 1);
		test("ZERO_LABEL", ZERO_LABEL, 0);
		test("ZERO_LABEL_NOT_ACCORDING_TO_CFG", ZERO_LABEL_NOT_ACCORDING_TO_CFG, 1);
		test("NOT_MONOTONIC_LABELS_1_WITH_SYNTAX_ERROR", NOT_MONOTONIC_LABELS_1_WITH_SYNTAX_ERROR, 1);
		test("NOT_MONOTONIC_LABELS_2", NOT_MONOTONIC_LABELS_2, 1);
		test("MULTI_PRINT", MULTI_PRINT, 0);
		test("BAD_GOTO_TEST", BAD_GOTO_TEST, 1);
		//test("BAD_GOTO_TEST_INFINITE_LOOP", BAD_GOTO_TEST_INFINITE_LOOP, 0);
		test("UNINIT_VAR", UNINIT_VAR, 1);
		test("ILLEGAL_VAR_NAME", ILLEGAL_VAR_NAME, 1);
		test("COMPLEX_IF_SHOULD_FAIL", COMPLEX_IF_SHOULD_FAIL, 1);
		test("DIVIDE", DIVIDE, 0);
		test("LINE_FEEDS_ONLY", LINE_FEEDS_ONLY, 1);
	}
	
	private static void test(String testName, String programStr, int expectedResult) {
		System.out.println("\u001B[34m" + testName + "\u001B[37m");
		System.out.println("\u001B[36m" + programStr + "\u001B[37m");
		testProgramAsString(programStr, expectedResult);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		System.out.println("");
	}

	public static void testProgramAsString(String programStr, int expectedResult) {
		InputStream is = new ByteArrayInputStream(programStr.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		int retVal = 0;
		
		try {
			retVal = Interpreter.runProgramFromReader(br);
		} catch (Throwable t) {
			System.err.println("\u001B[31m--------------->FAILED : " + t + "\u001B[37m");
			t.printStackTrace();
			return;
		}
		
		if (retVal == expectedResult) {
			System.out.println("\u001B[32m--------------->SUCCESS\u001B[37m");
		} else {
			System.err.println("\u001B[31m--------------->FAILED\u001B[37m");
		}
	}
}
