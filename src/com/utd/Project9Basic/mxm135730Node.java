package com.utd.Project9Basic;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * Node class which stores the ID, visited or not, adjListEdges(list of edges from this node to other nodes)
 */
import java.util.LinkedList;
import java.util.List;

public class mxm135730Node {
	
	public Integer id;
	public boolean visited;
	public List<mxm135730Edge> adjListEdges;
	
	public Integer L;
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
		this.visited = false;
		adjListEdges = new LinkedList<>();
		L = 0;
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