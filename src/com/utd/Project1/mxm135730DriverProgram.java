package com.utd.Project1;

import java.io.IOException;

/**
 * @author Maaniccka Sentil Manickam Poonkundran
 * 
 * Driver Program class which calls all 3 merge functions and prints their running time for each input given.
 * 
 *   The 3 merge functions are:
 *   	1) Basic Merge Sort
 *   	2) Better Merge Sort
 *   	3) Even Better Merge Sort
 *     All the 3 ways has the same running time O(n) but time can be saved a little by modifying the underlying part of the code.
 */

public class mxm135730DriverProgram {

	/**
	 * Main function which calls the 3 merge sort functions
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		long inputArray[] = {8192, 65536, 1048576, 8388608, 16777216, 33554432, 67108864};
		for(int i=0;i<inputArray.length;i++) {
			System.out.println("\n---------------------------\nn = " + inputArray[i]);
			String[] str = new String[1];
			str[0] = Long.toString(inputArray[i]);
			mxm135730BasicMergeSort.main(str);
			mxm135730BetterMergeSort.main(str);
			mxm135730EvenBetterMergeSort.main(str);
		}

	}

}
