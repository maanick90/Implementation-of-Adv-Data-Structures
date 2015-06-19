package com.utd.Project7;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * Edge class that stores an edge from a to b along with its weight
 */
public class mxm135730Edge {

	public mxm135730Node a, b;
	public mxm135730Node originalA, originalB;
	public Integer weight;
	public Integer originalWeight;
	
	/**
	 * Default Constructor
	 */
	public mxm135730Edge() {
	}
	
	/**
	 * Parameterized constructor
	 * @param a
	 * @param b
	 * @param weight
	 */
	public mxm135730Edge(mxm135730Node a, mxm135730Node b, Integer weight) {
		this.a = a;
		this.originalA = a;
		this.b = b;
		this.originalB = b;
		this.weight = weight;
		this.originalWeight = weight;
	}
	
	@Override
	public String toString() {
		return "("+ a.id + "," + b.id + ")=" + weight;
	}
}
