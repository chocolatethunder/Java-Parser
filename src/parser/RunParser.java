package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;

import static parser.Utilities.*;

/**
 * Contains various methods which parse the command-line input.
 */
public class RunParser {

	private boolean quit = false;
	private String userInput = "";
    private String input = "";
	ArrayList<Character> allowedExprStarts = new ArrayList<Character>(Arrays.asList('(','+','-','"'));

	/**
	 * Runs the parser on the command-line input
	 * @param jarLoad - Represents the loaded jar file
	 */
	public RunParser(JarFileLoader jarLoad) {
		
		Scanner reader = new Scanner(System.in);
		
		// Print start message
		this.printStartup();

		while (!quit) {
			
			// Print prompt
			System.out.print("> ");
			
			// Capture input
			userInput = reader.nextLine();  // used to parse expression
			
			// Process
			
			// Trim leading and trailing spaces
			input = userInput.trim();
			
			// Capture the first character
			char meta = input.charAt(0);
			
			// Either a meta command has been entered
			if (input.length() == 1) {
				
				// Process meta commands
				switch(meta) {
					
					// Quit
					case 'q':
					this.quit = true;
					System.out.println("bye.");
					break;
					
					// Verbose
					case 'v':
					ExceptionHandler.toggleVerbose();
					if (ExceptionHandler.isVerbose()) {
						System.out.println("Verbose on");
					} else {
						System.out.println("Verbose off");
					}
					break;
					
					// Function List
					case 'f':
					printFunctionList(jarLoad.getLoadedClass());
					break;
					
					// Pring Help
					case '?':
					this.printStartup();
					break;
					
					default:
					break;
					
				}
				
			}
			
			// or an expression is being entered
			else if (allowedExprStarts.contains(meta)) {				
	
				try {
					
					// Tokenize
					Tokenizer tokenizer = new Tokenizer(input, jarLoad);
					
					// Check if the order of tokens is corret
					if (checkOrderOfTokens(tokenizer.getTokens()) == -1) {
				
						// Construct Parse Trees. We must construct additional parsetrees!!
						ParseTreeConstructor parseTree = new ParseTreeConstructor(tokenizer);
						
						// Evaluate expression
						Evaluator evaluator = new Evaluator(parseTree);
						
						
                        // parse tree 
                        evaluator.parse(evaluator.getParseTree(), jarLoad.getLoadedClass());
                         
                        // print result 
						System.out.println(evaluator.toString());
					
					}
					
				} catch (Exception e) { /*Do nothing*/ }
				
			} else {
				// throw exception. 
				// @Kaylee you might need to overload the ParseException function to 
				// not have a String cmd parameter because I just saw Rob's output and it says:
				// Unexpected character encounterd at offset 0
			}	
			
		}

	}
    
	/**
	 * Prints the appropriate start-up messages to the console
	 */
	private void printStartup(){
		System.out.println(
		"q           : Quit the program.\n" +
		"v           : Toggle verbose mode (stack traces).\n" +
		"f           : List all known functions.\n" +
		"?           : Print this helpful text.\n" +
		"<expression>: Evaluate the expression.\n" +
		"Expressions can be integers, floats, strings (surrounded in double quotes) or function calls of the form '(identifier {expression}*)'."
		);
	}    
    
    
}
