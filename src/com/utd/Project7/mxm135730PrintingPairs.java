package com.utd.Project7;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * PrintingPairs class used to print the output if the |V| <= 50
 */
public class mxm135730PrintingPairs {

	public Integer a;
	public Integer b;
	
	/**
	 * Default Constructor
	 */
	public mxm135730PrintingPairs() {
	}

	/**
	 * Parameterized Constructor
	 * @param a
	 * @param b
	 */
	public mxm135730PrintingPairs(Integer a, Integer b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String toString() {
		return "("+a+","+b+")";
	}
}
