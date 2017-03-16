package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.text.*;

/**
 * Class contains methods used globally throughout the program.
 */
public class Utilities {
	
	/**
	 * Prints out each function (with its parameters' types and return type) for the specified class
	 * @param cls - Represents the class under consideration for printing functions
	 */
    public static void printFunctionList(Class cls) {
    	String functionList = "";
    	
    	// Retrieves methods from the loaded class
    	Method[] methods = cls.getDeclaredMethods();
    	String methodName;
    	
    	Class[] parameters = null;
    	String returnType = null;
    	
    	for (int i = 0; i < methods.length; i++) {
    		// Retrieves specific method's parameters and return type
    		methodName = methods[i].getName();
    		parameters = methods[i].getParameterTypes();
    		Type temp = methods[i].getReturnType();
    		if (temp == String.class)
    		{
    			returnType = "string";
    		}
    		else if (temp == float.class || temp == Float.class)
    		{
    			returnType = "float";
    		}
    		else if (temp == int.class || temp == Integer.class)
    		{
    			returnType = "int";
    		}
    		temp = null;
    		
    		functionList += "("  + methodName;
    		
			for (int j = 0; j < parameters.length; j++) {
    			temp = parameters[j];
    			if (temp == String.class)
        		{
    				functionList += " " + "string";
        		}
        		else if (temp == float.class || temp == Float.class)
        		{
        			functionList += " " + "float";
        		}
        		else if (temp == int.class || temp == Integer.class)
        		{
        			functionList += " " + "int";
        		}
    		}
			
    		functionList += ") : " + returnType + "\n";
    	}
    	
    	System.out.println(functionList);
    }
	

    /**
     * @param s - Represents the input string being checked for matching brackets
     * @return - Boolean representing if the brackets in the input match
     */
	public static boolean checkBrackets (String s) {
		
		Stack<Character> stack = new Stack<Character>();
		
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				stack.push(c);
			} else if (c == ')') {
				if (stack.isEmpty()) { return false; }
				if (stack.pop() != '(') { return false; }
			}
		}
		
		return stack.isEmpty();
		
	}
	
    /**
	 * @param s - Represents the string of interest for counting a specific character's number of occurrences
	 * @param c - Represents the character being counted
	 * @return - Integer representing the number of times a character appears in a string
	 */
	public static int characterCount (String s, char c) {	
		return s.length() - s.replace(Character.toString(c),"").length();
	}
	
	/**
	 * Allowable types are string, int, float or identifier.
	 * @param s - Represents the string under consideration for identifying its type
	 * @return - Class representing the type of the input string
	 */
	public static Class getType (String s , Class jarLoad) {
			
        if(isInt(s))  return int.class; 
        if(isString(s)) return String.class; 
        if(isFloat(s))  return float.class; 
        
        // if not method return generic class
        if(isMethod(s, jarLoad)) return Method.class;	
		
		return Class.class;
	}
	
	/**
	 * Allowable types are string, int, float or identifier.
	 * @param s - Represents the string under consideration for indentifying its type
	 * @return - String representing the type of the input string
	 */
	public static String getStringType (String s, Class jarLoad ) {
		   
        if(isInt(s)) return "int"; 
        if(isString(s)) return "string"; 
        if(isFloat(s))  return "float"; 
        
        // if not method return generic class
        if(isMethod(s, jarLoad)) return "identifier"; 
        
		return "unidentified";
	}
	
	/**
	 * Checks if the argument is a valid integer.
	 * @param s - Represents the value under consideration 
	 * @return - Boolean representing whether or not the input string is a valid integer
	 */
    public static boolean isInt(String s)
    {
        boolean valid = false;
        int start = 0;
        if (s.charAt(0) == '-' || s.charAt(0) == '+')
        { start = 1; }
        
        try{
        	int temp = Integer.parseInt(s.substring(start, s.length()));
        	return true;
        }
        catch (Exception e) { return false; }
    }
    
    /**
     * Checks if the argument is a valid float.
     * @param s - Represents the value under consideration
     * @return - Boolean representing whether or not the input string is a valid float
     */
    public static boolean isFloat(String s)
    {
        int start = 0;
        if (s.charAt(0) == '-' || s.charAt(0) == '+')
        { start = 1; }
        
        if (!isInt(s) && s.charAt(start) != '.')
        {
	        try{
	        	float temp = Float.parseFloat(s.substring(start, s.length()));
	        	return true;
	        }
	        catch (Exception e) { return false; }
        }
        else { return false; }
    }
    
    /**
     * Checks if the argument is a valid string.
     * @param s - Represents the value under consideration
     * @return - Boolean representing whether or not the input string is a valid string
     */
    public static boolean isString(String s)
    {
        boolean valid_start = false;
        boolean valid_end = false;
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == '"' && i == 0)
            { valid_start = true; }
            
            if (s.charAt(i) == '"' && i != 0 && i != (s.length() - 1)) 
            { return false; }
            
            if (s.charAt(i) == '"' && i == (s.length() - 1))
            { valid_end = true; }
        }
        if (valid_start && valid_end) { return true; }
        else { return false; }
    }
    
	/**
	* Checks if the argument is a valid method.
	* @param s - Represents the value under consideration
	* @param jarLoad - Represents the class under consideration
	* @return - Boolean representing whether or not the input string is a valid method
    */ 
    public static boolean isMethod(String s, Class jarLoad)
    {
    	//check and see if s is a valid method
    	Method[] methods = jarLoad.getMethods();
    	//if (!validName(s)) { return false; }
    	
    	for (int i = 0; i < methods.length; i++)
    	{
    		String method = methods[i].getName();
    		if (method.equals(s))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
	 * If the ordering is not correct, returns the index where the error occurs.
	 * Else returns -1
	 * @param input
	 * @return
	 */
	public static int checkOrderOfTokens( ArrayList<Token> input ) {
		String previousToken = input.get(0).getStringType();
		String currentToken;
		
		for (int index = 1; index < input.size(); index ++ ) {
			currentToken = input.get(index).getStringType();
			switch( currentToken ) {
				case "identifier":
					if (!previousToken.equals("openBracket" ))		// previous type does not equal openBracket
						return index;
					break;
				case "int":
					if (previousToken.equals("openBracket" ))
						return index;
					break;
				case "float":
					if (previousToken.equals("openBracket" ))
						return index;
					break;
				case "string":
					if (previousToken.equals("openBracket" ) )
						return index;
					break;
				case "closedBracket":
					if (previousToken.equals("openBracket" ))
						return index;
					break;
				case "openBracket":
					if (previousToken.equals("openBracket" ))
						return index;
					break;
			
			}
			previousToken = currentToken;
		}
		return -1;
	}

}