package com.utd.Project8PartA;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author mxm135730
 * Node class which stores the ID, visited or not, adjListEdges, shortest distance from the source node,
 * parent in the shortest path, no of shortest paths possible from the source node, a count to detect the negative cycle
 */
public class mxm135730Node {

	public Integer id;
	public boolean visited;
	public List<mxm135730Edge> outgoingEdge;
	public Integer d;
	public mxm135730Node pred;
	public Long countNoOfPaths;
	public Integer countNegativeCycle;

	/**
	 * Default Constructor 
	 */
	public mxm135730Node() {
	}
	
	/**
	 * Parameterized constructor
	 * 
	 * @param id
	 * 			: id of the node
	 */
	public mxm135730Node(Integer id) {
		this.id = id;
		this.visited = false;
		this.outgoingEdge = new LinkedList<mxm135730Edge>();
		this.d = Integer.MAX_VALUE;
		this.pred = null;
		this.countNoOfPaths = 1L;
		this.countNegativeCycle = 0;
	}
	
	/*@Override
	public String toString() {
		return "Node: " + id;
	}*/
}
