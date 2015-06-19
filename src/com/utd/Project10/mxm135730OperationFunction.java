package com.utd.Project10;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class mxm135730OperationFunction {

	public mxm135730Graph root;
	public mxm135730Node rootNode;

	/**
	 * Default Constructor
	 */
	public mxm135730OperationFunction() {
		root = new mxm135730Graph();
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

		mxm135730Node a, b;
		Integer id1 = Integer.parseInt(splitStr[0]);
		Integer id2 = Integer.parseInt(splitStr[1]);
		if(root.nodesHashMap.containsKey(id1)) {
			a = root.nodesHashMap.get(id1);
		} else {
			a = new mxm135730Node(id1);
		}
		if(root.nodesHashMap.containsKey(id2)) {
			b = root.nodesHashMap.get(id2);
		} else {
			b = new mxm135730Node(id2);
		}

		if(rootNode == null) {
			rootNode = a;
		}

		root.addNodeAndEdge(a,b, Integer.parseInt(splitStr[2]));		// add Node and Edge
	}

	public void MaximumWeightedMatching() {

		mxm135730Node freeNode;
		/**
		 * 1) Feasible labelling
		 * 2) Compute Zero Graph
		 * 3) Find maximal matching
		 * 4) Then maximum matching
		 * 5)
		 */

		root.FeasibleLabelling();

		int countNo = 1;
		root.ComputeZeroGraph(countNo);
		while(!root.MaximumMatching(countNo)) {
			++countNo;
			System.out.println("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>> Count No = " + countNo + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			root.ComputeZeroGraph(countNo);
		}

		System.out.println("\n\n\n!!!!!!!!!! Perfect Matching !!!!!!!!");
		mxm135730PrintClass.PrintMaximalMatching(root);

		/**
		 * 1) Expand the blossom from topmost level onward
		 * 2) Blossoms have id's greater than initial no of nodes
		 */
		mxm135730Node blossomNodeToExpand;
		while((blossomNodeToExpand = root.getFirstBlossomNodeInTopMostLevel()) != null)
			root.ExpandBlossom(blossomNodeToExpand);

		// Find the actual Maximum weighted matching value
		Long maxWeight = root.GetMaximumWeightPerfectMatchingValue();
		System.out.println("\n\n\n\n----------------- Max Weight Value --------------------");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("mxm135730OutputMaximumMatchingValue.txt"));
			bw.write("Maximum Weighted Perfect Matching value = " + maxWeight.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(maxWeight);
	}

	public void ExecutionFunction() {


		/**
		 * 1) Kruskal algorithm to find the MST
		 * 2) Find nodes with odd degree
		 * 3) Find all pairs shortest paths
		 */


		root.Kruskal();						// mstTree contains the edges of the MST Tree
		System.out.println("\n\nEdges in the MST Tree by Kruskal Algorithm :");
		for(mxm135730Edge edge : root.mstTreeEdgesList)
			System.out.println("(" + edge.a.id + "," + edge.b.id + ") = " + edge.weight);
		List<mxm135730Node> oddDegreeNodes = root.OddDegreeNodes();
		System.out.println("\n\nOdd degree nodes are :");
		for(mxm135730Node node : oddDegreeNodes)
			System.out.println("Node : " + node.id);

		Integer[][] shortestPathMatrix = root.AllPairsShortestPathsUsingFloydWarshallAlgorithm(oddDegreeNodes);
		CreateNewGraph(oddDegreeNodes, shortestPathMatrix);

	}

	private void CreateNewGraph(List<mxm135730Node> oddDegreeNodes, Integer[][] shortestPathMatrix) {

		mxm135730Graph rootK = new mxm135730Graph();
		rootK.noOfNodes = oddDegreeNodes.size();
		rootK.noOfEdges = (root.noOfNodes * (root.noOfNodes-1)) / 2;

		int count = 1;
		for(mxm135730Node node : oddDegreeNodes) {
			node.adjListEdges.clear();
			for(mxm135730Node adjNode : oddDegreeNodes) {
				if(!node.equals(adjNode)) {
					rootK.addNodeAndEdge(node, adjNode, shortestPathMatrix[node.id-1][adjNode.id-1]);
				}
			}
			++count;
		}
	}
}
