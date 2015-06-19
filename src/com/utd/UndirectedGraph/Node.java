package com.utd.UndirectedGraph;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * Node class which stores the ID, visited or not, adjListEdges(list of edges from this node to other nodes)
 */
import java.util.LinkedList;
import java.util.List;

public class Node {
	
	public Integer id;
	public boolean visited;
	public List<Edge> adjListEdges;
	public Integer L;
	public boolean outerNode;
	
	/**
	 * Default Constructor
	 */
	public Node() {
		adjListEdges = new LinkedList<>();
	}
	
	/**
	 * Parameterized constructor
	 * @param id
	 */
	public Node(Integer id) {
		this.id = id;
		this.visited = false;
		adjListEdges = new LinkedList<>();
		L = 0;
		outerNode = false;
	}

	/**
	 * add the edge
	 * @param adj
	 */
	public void addAdjNode(Edge adj) {
		this.adjListEdges.add(adj);
	}

	@Override
	public String toString() {
		return "Node: " + id;
	}

}