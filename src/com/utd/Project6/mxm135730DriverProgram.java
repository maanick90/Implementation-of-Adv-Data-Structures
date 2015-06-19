package com.utd.Project6;

import java.io.BufferedReader; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * DriverProgram class - to run and execute the whole project
 * @author Maaniccka Sentil Manickam Poonkundran
 *
 */
public class mxm135730DriverProgram {
	

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		mxm135730OperationFunction of = new mxm135730OperationFunction();

		/*
		 * Read the input from a file 
		 */
		BufferedReader br;
		String inputLine;
		BigDecimal outputSum = new BigDecimal("0");

		try {
			br = new BufferedReader(new FileReader("mxm135730InputProject6.txt"));
			long startTime = System.currentTimeMillis();
			while((inputLine=br.readLine()) != null) {
				if(inputLine.charAt(0) != '#') {
					// call the below function which calls appropriate functions of the project
					BigDecimal temp = of.parseInputAndAdd(inputLine.trim()).setScale(2, RoundingMode.FLOOR);
					outputSum = outputSum.add(temp);
				}
			}
			long endTime = System.currentTimeMillis();
			System.out.println(outputSum);		// print the final output
			System.out.println("Execution Time >> " + (endTime - startTime) );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
