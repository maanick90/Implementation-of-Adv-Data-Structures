package com.utd.Project9PartA;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * Edge class that stores an edge from a to b along with its weight
 */
public class mxm135730Edge {

	public mxm135730Node a, b;
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
		this.b = b;
		this.weight = weight;
		this.originalWeight = weight;
	}
	
	/**
	 * Eg. For edge (1,2), both Node 1 and 2 stores the edge as (1,2). So we will not know which is the adjNode
	 * for 1 and 2 individually.
	 * @param node
	 * @param edge
	 * @return
	 */
	public mxm135730Node getOtherEnd(mxm135730Node node) {
		if(a.equals(node))
			return b;
		else
			return a;
	}
	
	@Override
	public String toString() {
		return "("+ a.id + "," + b.id + ")=" + weight;
	}
}
