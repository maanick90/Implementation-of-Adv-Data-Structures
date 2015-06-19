package com.utd.Project8PartA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author mxm135730
 * DriverProgram which runs the Project 8 Part a
 */
public class mxm135730DriverProgram {

	public static void main(String[] args) throws IOException {

	
		mxm135730OperationFunction of = new mxm135730OperationFunction();
		
		/**
		 * Read the input from the file "mxm135730InputProject8PartA.txt"
		 */
		BufferedReader br = new BufferedReader(new FileReader("mxm135730InputProject8PartA.txt"));
		Integer countInput = 1;
		String line = null;
		try {
			of.parseFirstLineInput(br.readLine());   // read the first line to set the no of nodes, edges and root node
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		// read the rest of the lines
		while( (line = br.readLine()) != null) {
			if(countInput++ >= 10000000)
				break;
			try {
				of.addToGraph(line);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		br.close();
		
		/**
		 * Call the actual Revised Bellma Ford Algorithm
		 */
		of.callRevisedBellmanFordAlgorithm();

		
	}

}
