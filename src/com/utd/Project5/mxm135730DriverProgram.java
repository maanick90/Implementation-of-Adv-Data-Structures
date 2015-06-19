package com.utd.Project5;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Maaniccka Sentil Manickam Poonkundran - Project-5 
 * 		   DriverProgram class to parse the input and call addition and other operations
 */
public class mxm135730DriverProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * validate the input
		 */
		if(args.length > 2000) {
			System.out.println("Invalid input!!!");
			System.exit(1);
		}
		
		System.out.println(mxm135730OperationFunctions.parseSubtraction("-13", "-7"));
		System.exit(1);
		
		/*
		 * convert the input into 2D String array
		 */
		String input[] = parseCommanLineArgumentsTo2DStringArray(args);

		/*
		 * parse each line of the input and execute the operations 
		 */
		Map<Character,String> map = parseInputAndExecuteOperations(input);
		for(Map.Entry<Character, String> entry : map.entrySet()) 
			System.out.println(entry.getKey() + "    " + entry.getValue());
	}

	/**
	 * Method parseCommanLineArgumentsTo2DStringArray(): Generates 2D String array. When a space is given in the command line argument,
	 * then it will be treated as 2 different strings in args[]. So we parse it and take it to the input specification as given
	 * by the professor
	 * Precondition: Input from the stdin
	 * Postcondition: 2D String array is generated 
	 * @param args
	 *            command line arguments
	 */
	private static String[] parseCommanLineArgumentsTo2DStringArray(String[] args) {

		String input[] = new String[args.length/2];
		for(int i=0;i<args.length;i=i+2)
			input[i/2] = args[i] + " " + args[i+1];
		return input;
	}

	/**
	 * Method parseInputAndExecuteOperations(): parse each line of the input and call operation functions like addition,
	 * subtraction, multiplication, power of linked lists
	 * Precondition: Input is validated
	 * Postcondition: a map is generated which contains the variables along with its values 
	 * @param input
	 *            is the input array
	 */
	private static Map<Character, String> parseInputAndExecuteOperations(String[] input) {

		String operationsArray[] = {"+", "-", "*", "^", "/", "%", "~", ")"};				// totally only four operations 
		Map<Character, String> map = new LinkedHashMap<>();
		for(int i=0;i<input.length;i++) {

			String line = input[i].split(" ")[1];						// exclude the line number

			if(!(line.contains("=") || line.contains("?"))) {			// if just a variable is present, then print to the console
				System.out.println(map.get(line.charAt(0)));
			}
			else if(line.contains("?")) {								// for looping
				String tempStr[] = line.split("\\?");
				if(Integer.parseInt(map.get(tempStr[0].charAt(0))) != 0) {
					i = Integer.parseInt(tempStr[1])-2;
					continue;
				}
			}
			else {
				String temp1[] = line.split("=");							// split LHS and RHS of "="
				int indexOfOperator = containsOperatorSymbols(temp1[1], operationsArray);		// find which operator is specified and its index
				if(indexOfOperator > 0) 
					map.put(temp1[0].charAt(0), fnGetValue(map, temp1, operationsArray[indexOfOperator-1]));	// calculate the operation and update the value 
				else 														// just a simple assignment like x=999
					map.put(temp1[0].charAt(0), temp1[1]);					
			}
		}

		return map;
	}


	/**
	 * Method containsOperatorSymbols(): check if the operators +,-,*,^ is present in given line. If so return its index, 
	 * else return 0 which means the operator is not present
	 * @param str
	 *            is the given line input array
	 * @param operationsArray
	 * 				is the array which contains the operators
	 */	
	private static int containsOperatorSymbols(String str, String[] operationsArray) {
		for(int i=0;i<operationsArray.length;i++)
			if(str.contains(operationsArray[i]))
				return i+1;
		return 0;
	}

	/**
	 * Method fnGetValue(): calls the addition, subtraction, multiplication, power functions accordingly 
	 * returns the result in the form of String
	 * @param map
	 *            is the parsed input map
	 * @param temp1
	 * 				is the RHS of '=' in the given line of the input array
	 * @param patter
	 * 				is the opertor symbol
	 */
	private static String fnGetValue(Map<Character, String> map, String[] temp1, String pattern) {
		String str1 = map.get(temp1[1].split("\\"+pattern)[0].charAt(0));		// get var1
		String str2 = "";
		if(!(pattern.equals("~") || pattern.equals(")")))
			str2 = map.get(temp1[1].split("\\"+pattern)[1].charAt(0));		// get var2
		String tempStr = "0";

		if(pattern.equals("+"))
			tempStr = mxm135730OperationFunctions.parseAddition(str1, str2);		// call addition function
		else if(pattern.equals("-"))
			tempStr = mxm135730OperationFunctions.parseSubtraction(str1,str2);	// call subtraction function
		else if(pattern.equals("*"))
			tempStr = mxm135730OperationFunctions.multiplyLists(str1, str2);	// call multiplication function
		else if(pattern.equals("^"))
			tempStr = mxm135730OperationFunctions.powerLists(str1, str2);	// call power function
		else if(pattern.equals("/"))
			tempStr = mxm135730AdditionalOperations.divideAndRemainderFunction(str1, str2).quotient;
		else if(pattern.equals("%"))
			tempStr = mxm135730AdditionalOperations.divideAndRemainderFunction(str1, str2).remainder;
		else if(pattern.equals("~"))
			tempStr = mxm135730AdditionalOperations.squareRootLists(str1);
		else if(pattern.equals(")"))
			tempStr = mxm135730OperationFunctions.NumToStr(mxm135730AdditionalOperations.maxPowerIn15Secs(str1));

		return tempStr;									// return the String value (list converted to String)
	}

}
