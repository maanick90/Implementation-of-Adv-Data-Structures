package com.utd.Project10;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * Edge class that stores an edge from a to b along with its weight
 */
public class mxm135730Edge {

	public mxm135730Node a, b;
	public mxm135730Node originalA, originalB;
	public Integer weight;

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
		this.originalA = a;
		this.originalB = b;
		this.weight = weight;
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
		else if(b.equals(node))
			return a;
		else if(originalA.equals(node))
			return originalB;
		else
			return originalA;
	}

	@Override
	public String toString() {
		return "("+ (int)originalA.L + "," + (int)originalB.L + ")=" + weight + " or ("+originalA.id+","+originalB.id+")";
	}

	public void UpdateEdge(mxm135730Node blossomNode, mxm135730Node node) {
		if(a.equals(node))
			a = blossomNode;
		else if(b.equals(node))
			b = blossomNode;
	}

	/**
	 * tells if this edge is zero weight edge or no
	 * @return
	 */
	public boolean isZeroEdge() {
		if(originalA.L + originalB.L == (double) weight)
			return true;
		return false;
	}

	/**
	 * Returns the original node that was first incident
	 * @param blossomNodeToExpand
	 * @return
	 */
	public mxm135730Node getOriginalNode(mxm135730Node blossomNodeToExpand) {
		if(a.equals(blossomNodeToExpand))
			return originalA;
		return originalB;
	}

	public void UpdateOriginalValue() {
		a = originalA;
		b = originalB;
	}

}
