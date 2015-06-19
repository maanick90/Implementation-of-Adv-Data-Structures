package com.utd.UndirectedGraph;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * Edge class that stores an edge from a to b along with its weight
 */
public class Edge {

	public Node a, b;
	public Integer weight;
	
	/**
	 * Default Constructor
	 */
	public Edge() {
	}
	
	/**
	 * Parameterized constructor
	 * @param a
	 * @param b
	 * @param weight
	 */
	public Edge(Node a, Node b, Integer weight) {
		this.a = a;
		this.b = b;
		this.weight = weight;
	}
	
	/**
	 * Eg. For edge (1,2), both Node 1 and 2 stores the edge as (1,2). So we will not know which is the adjNode
	 * for 1 and 2 individually.
	 * @param node
	 * @param edge
	 * @return
	 */
	public Node getOtherEnd(Node node) {
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
