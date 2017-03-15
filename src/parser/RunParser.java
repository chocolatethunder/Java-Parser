package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.net.*;

import static parser.Utilities.*;


/**
 * 
 * 
 *
 */
public class RunParser {

	private boolean quit = false;
	private String input = "";
	    
	public RunParser(JarFileLoader jarLoad) {
		
		Scanner reader = new Scanner(System.in);
		
		// Print start message
		this.printStartup();

		while (!quit) {
			
			// Print prompt
			System.out.print("> ");
			
			// Capture input
			input = reader.next();
			
			// Process
			
			// Trim leading and trailing spaces
			input.trim();
			
			if (input.length() == 1) {
				
				char meta = input.charAt(0);
				
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
					printFunctionList(jarLoad);
					break;
					
					// Pring Help
					case '?':
					this.printStartup();
					break;
					
					default:
					break;
					
				}
				
			}			
			
		}

	}
    
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
