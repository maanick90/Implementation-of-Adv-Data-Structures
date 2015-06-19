package com.utd.Project8PartA;

/**
 * 
 * @author mxm135730
 * OperationFunction class to execute the functions in the graph
 */
public class mxm135730OperationFunction {
	
	public mxm135730Graph root;
	public mxm135730Node startNode;
	public mxm135730Node targetNode;
	
	/**
	 * Default Constructor
	 */
	public mxm135730OperationFunction() {
		root = new mxm135730Graph();
		startNode = new mxm135730Node();
		targetNode = new mxm135730Node();
	}

	/**
	 * Parse the first line of the input
	 * @param line
	 */
	public void parseFirstLineInput(String line) throws Exception {
		String splitStr[] = line.split("\\s+");
		startNode = new mxm135730Node(Integer.parseInt(splitStr[2]));					// set the root node
		targetNode = new mxm135730Node(Integer.parseInt(splitStr[3]));				// set the target node
		root.nodesList.add(startNode);
		root.nodesList.add(targetNode);
		root.nodesHashMap.put(startNode.id, startNode);
		root.nodesHashMap.put(targetNode.id, targetNode);
	}

	/**
	 * Parse the remaining lines of the input
	 * @param str
	 */
	public void addToGraph(String str) throws Exception {
		String splitStr[] = str.trim().split("\\s+");

		mxm135730Node a, b;
		Integer id1 = Integer.parseInt(splitStr[0]);
		Integer id2 = Integer.parseInt(splitStr[1]);

		if(root.nodesHashMap.containsKey(id1))
			a = root.nodesHashMap.get(id1);
		else
			a = new mxm135730Node(id1);
		if(root.nodesHashMap.containsKey(id2))
			b = root.nodesHashMap.get(id2);
		else
			b = new mxm135730Node(id2);
		
		root.addNodeAndEdge(a,b, Integer.parseInt(splitStr[2]));		// add Node and Edge
	}

	/**
	 * Function that calculates the no of shortest paths and its predecessor
	 */
	public void callRevisedBellmanFordAlgorithm() {
		
		long startTime = System.currentTimeMillis();
		root.BellManFordAlgorithm(startNode, targetNode);		
		long endTime = System.currentTimeMillis();
		System.out.println(targetNode.d + " " + targetNode.countNoOfPaths + " " + (endTime-startTime));
		if(root.nodesList.size() <= 100)
			root.PrintPath();
	}
	
	/**
	 * Function to print the graph
	 */
	public void PrintGraph() {
		System.out.println(root);
	}
	
}
