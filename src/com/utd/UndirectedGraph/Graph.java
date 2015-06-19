package com.utd.UndirectedGraph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
	
	public List<Node> nodesList;
	public List<Edge> edgesList;
	public Map<Integer, Node> nodesHashMap;
	public Integer noOfNodes;
	public Integer noOfEdges;
	
	/**
	 * Default Constructor
	 */
	public Graph() {
		nodesList = new LinkedList<Node>();
		edgesList = new LinkedList<Edge>();
		nodesHashMap = new HashMap<>();
		noOfNodes = 0;
		noOfEdges = 0;
	}

	/**
	 * Add a node and edge to the graph
	 * @param node1
	 * @param node2
	 * @param weight
	 */
	public void addNodeAndEdge(Node node1, Node node2, Integer weight) {
		
		if(nodesHashMap.containsKey(node1.id))
			node1 = nodesHashMap.get(node1.id);
		else {
			nodesList.add(node1);
			nodesHashMap.put(node1.id, node1);
		}
			
		if(nodesHashMap.containsKey(node2.id))
			node2 = nodesHashMap.get(node2.id);
		else {
			nodesList.add(node2);
			nodesHashMap.put(node2.id, node2);
		}
			
		Edge edge = new Edge(node1, node2, weight);
		edgesList.add(edge);		// add the edge to the list
		node1.addAdjNode(edge);		// add the adjacency list matrix for node1
		node2.addAdjNode(edge);		// add the adjacency list matrix for node2
		
	}

	@Override
	public String toString() {
		System.out.println("Nodes List: " + nodesList);
		return "";
	}

	/**
	 * Print the graph when needed. Doesn't print necessarily from the root
	 */
 	public void PrintGraph() {
		System.out.println(nodesList);
		System.out.println(edgesList);
		for(Node node : nodesList)
			System.out.println("For node : " + node + " >> " + node.adjListEdges);
	}

}
