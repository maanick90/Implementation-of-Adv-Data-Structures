package com.utd.Project8PartA;

/**
 * 
 * @author mxm135730
 * Edge class that stores an edge from a to b along with its weight
 */
public class mxm135730Edge {

	public mxm135730Node startNode;
	public mxm135730Node endNode;
	public Integer weight;
	
	/**
	 * Default Constructor 
	 */
	public mxm135730Edge() {
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param startNode
	 * 					: node at one end of the edge
	 * @param endNode
	 * 					: node at the other end of the edge
	 * @param weight
	 * 					: weight of the edge
	 */
	public mxm135730Edge(mxm135730Node startNode, mxm135730Node endNode, Integer weight) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return "("+ startNode.id + "," + endNode.id + ")=" + weight;
	}

}
