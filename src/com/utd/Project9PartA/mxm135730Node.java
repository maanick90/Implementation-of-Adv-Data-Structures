package com.utd.Project9PartA;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * Node class which stores the ID, visited or not, adjListEdges(list of edges from this node to other nodes)
 */
import java.util.LinkedList; 
import java.util.List;

public class mxm135730Node {
	
	public Integer id;
	public boolean seen;
	public List<mxm135730Edge> adjListEdges;
	
	public boolean outerNode;
	public mxm135730Node mate;
	public mxm135730Node parent;
	
	/**
	 * Default Constructor
	 */
	public mxm135730Node() {
		adjListEdges = new LinkedList<>();
	}
	
	/**
	 * Parameterized constructor
	 * @param id
	 */
	public mxm135730Node(Integer id) {
		this.id = id;
		this.seen = false;
		adjListEdges = new LinkedList<>();
		outerNode = false;
		mate = null;
		parent = null;
	}

	/**
	 * add the edge
	 * @param adj
	 */
	public void addAdjNode(mxm135730Edge adj) {
		this.adjListEdges.add(adj);
	}

	@Override
	public String toString() {
		return "Node: " + id;
	}

}