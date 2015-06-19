package com.utd.UndirectedGraph;

public class OperationFunction {

	public Graph root;
	public Node rootNode;
	
	/**
	 * Default Constructor
	 */
	public OperationFunction() {
		root = new Graph();
		rootNode = null;
	}

	/**
	 * Parse the first line of the input
	 * @param line
	 */
	public void parseFirstLineInput(String line) {
		String splitStr[] = line.split("\\s+");
		root.noOfNodes = Integer.parseInt(splitStr[0]);
		root.noOfEdges = Integer.parseInt(splitStr[1]);
	}

	/**
	 * Parse the remaining lines of the input
	 * @param str
	 */
	public void addToGraph(String str) {
		String splitStr[] = str.trim().split("\\s+");

		Node a, b;
		Integer id1 = Integer.parseInt(splitStr[0]);
		Integer id2 = Integer.parseInt(splitStr[1]);
		if(root.nodesHashMap.containsKey(id1))
			a = root.nodesHashMap.get(id1);
		else
			a = new Node(id1);
		if(root.nodesHashMap.containsKey(id2))
			b = root.nodesHashMap.get(id2);
		else
			b = new Node(id2);
		
		if(rootNode == null)					// set the first node processed as rootNode as nothing is specified
			rootNode = a;
		
		root.addNodeAndEdge(a,b, Integer.parseInt(splitStr[2]));		// add Node and Edge
	}
}
