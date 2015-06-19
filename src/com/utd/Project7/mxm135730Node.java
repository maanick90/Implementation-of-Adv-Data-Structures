package com.utd.Project7;

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
	public List<mxm135730Edge> incomingEdges;
	
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
		incomingEdges = new LinkedList<>();
	}

	/**
	 * add the edge
	 * @param adj
	 */
	public void addAdjNode(mxm135730Edge adj) {
		this.adjListEdges.add(adj);
	}

	/**
	 * add incoming edge
	 * @param adj
	 */
	public void addIncomingEdge(mxm135730Edge adj) {
		this.incomingEdges.add(adj);
	}
	
	@Override
	public String toString() {
		return "Node: " + id;
	}

}