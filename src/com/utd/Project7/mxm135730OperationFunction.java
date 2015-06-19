package com.utd.Project7;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * OperationFunction class to execute the functions in the graph
 *
 */
public class mxm135730OperationFunction {
	
	
	public mxm135730Graph root;
	public mxm135730Node rootNode;
	/**
	 * Default Constructor
	 */
	public mxm135730OperationFunction() {
		root = new mxm135730Graph();
		rootNode = new mxm135730Node();
	}

	/**
	 * Parse the first line of the input
	 * @param line
	 */
	public void parseFirstLineInput(String line) {
		String splitStr[] = line.split("\\s+");
		root.noOfNodes = Integer.parseInt(splitStr[0]);
		root.noOfEdges = Integer.parseInt(splitStr[1]);
		rootNode = new mxm135730Node(Integer.parseInt(splitStr[2]));					// set the root node
		root.nodesList.add(rootNode);
		root.nodesHashMap.put(Integer.parseInt(splitStr[2]), rootNode);		// update HashMap
	}

	/**
	 * Parse the remaining lines of the input
	 * @param str
	 */
	public void addToGraph(String str) {
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
	 * Call MST here
	 */
	public void MST() {
		
		Integer totalWeightsReduced = 0;
		Integer totalNoOfNodesInitially = root.noOfNodes; 
		List<mxm135730Node> shrinkedNode = new LinkedList<mxm135730Node>();
		mxm135730Node z;

		Long startTime = System.currentTimeMillis();
		while(true) {
			
			totalWeightsReduced += root.MakeZeroWeights(rootNode, shrinkedNode);		// make 0-weights in the edges
			root.runGraphBFSWithZeroWeights(rootNode, shrinkedNode, null, false);		// run BFS 
			z = root.getNodeZ(rootNode, shrinkedNode);			// get the unvisited node which also tells if MST is formed or not
			if(z == null) 
				break;
			else 												// 0-weight cycle is there
				totalWeightsReduced += root.findZeroWeightCycleBFS(z, shrinkedNode);
		}	// end of while
		Long endTime = System.currentTimeMillis();
		
		System.out.println(totalWeightsReduced + " " + (endTime-startTime));
		if(totalNoOfNodesInitially.compareTo(50) <= 0) 						// print the output differently if |V| <= 50
			root.expandMSPAndPrintPairs(rootNode, shrinkedNode, totalNoOfNodesInitially);
	}
}
