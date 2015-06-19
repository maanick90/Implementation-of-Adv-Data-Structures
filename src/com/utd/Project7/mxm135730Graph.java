package com.utd.Project7;

import java.util.Collections; 
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class mxm135730Graph {
	
	public List<mxm135730Node> nodesList;
	public Map<Integer, mxm135730Node> nodesHashMap;
	public Integer noOfNodes;
	public Integer noOfEdges;
	
	/**
	 * Default Constructor
	 */
	public mxm135730Graph() {
		nodesList = new LinkedList<mxm135730Node>();
		nodesHashMap = new HashMap<>();
		noOfNodes = 0;
		noOfEdges = 0;
	}

	/**
	 * Add a node and edge to the graph
	 * @param node
	 * @param adjNode
	 * @param weight
	 */
	public void addNodeAndEdge(mxm135730Node node, mxm135730Node adjNode, Integer weight) {
		
		if(nodesHashMap.containsKey(node.id))
			node = nodesHashMap.get(node.id);
		else {
			nodesList.add(node);
			nodesHashMap.put(node.id, node);
		}
			
		if(nodesHashMap.containsKey(adjNode.id))
			adjNode = nodesHashMap.get(adjNode.id);
		else {
			nodesList.add(adjNode);
			nodesHashMap.put(adjNode.id, adjNode);
		}
			
		mxm135730Edge edge = new mxm135730Edge(node, adjNode, weight);
		node.addAdjNode(edge);		// add the adjacency list matrix
		adjNode.addIncomingEdge(edge);
	}

	@Override
	public String toString() {
		System.out.println("Nodes List: " + nodesList);
		return "";
	}

	/**
	 * Set the weight as zero in the minimum incoming edge and change other weights appropriately
	 * @param rootNode
	 * @param shrinkedNode
	 * @return
	 */
	public Integer MakeZeroWeights(mxm135730Node rootNode, List<mxm135730Node> shrinkedNode) {

		Map<mxm135730Node, Integer> mapZeroWeightsArray = new HashMap<mxm135730Node, Integer>();
		Map<mxm135730Node, mxm135730Node> mapZeroWeightsPrevNodesArray = new HashMap<mxm135730Node, mxm135730Node>();
		
		for(mxm135730Node node : nodesList) 
			mapZeroWeightsArray.put(node, Integer.MAX_VALUE);
		
		Integer totalWeightReduced = 0;

		// find the min incoming weight
		for(mxm135730Node node : nodesList) {
			if(shrinkedNode.contains(node))
				continue;
			List<mxm135730Edge> adjListEdges = node.adjListEdges;
			for(mxm135730Edge edge : adjListEdges)
				if(!edge.b.equals(rootNode)) 
				{
					if(mapZeroWeightsArray.get(edge.b).compareTo(edge.weight) > 0) {
						mapZeroWeightsArray.put(edge.b, edge.weight);
						mapZeroWeightsPrevNodesArray.put(edge.b, edge.a);
					}
				}
		}
		
		// reduce the weight of each edge by its appropriate min incoming edge value
		for(mxm135730Node node : nodesList) {
			if(shrinkedNode.contains(node))
				continue;
			for(mxm135730Edge edge : node.adjListEdges)
				if(!edge.b.equals(rootNode))
				{
					edge.weight -= mapZeroWeightsArray.get(edge.b);
					if(edge.weight < 0)
						edge.weight = 0;
				}
		}
		
		for(Map.Entry<mxm135730Node, Integer> entry : mapZeroWeightsArray.entrySet())
			if(!entry.getValue().equals(Integer.MAX_VALUE))
				totalWeightReduced += entry.getValue();

		return totalWeightReduced;
	}

	/**
	 * Run the BFS in the graph thru 0-weight edges
	 * @param rootNode
	 * @param shrinkedNode
	 * @param pairingLists
	 * @param toPrint
	 */
	public void runGraphBFSWithZeroWeights(mxm135730Node rootNode, List<mxm135730Node> shrinkedNode, List<mxm135730PrintingPairs> pairingLists, boolean toPrint) {

		Queue<mxm135730Node> queue = new LinkedList<mxm135730Node>();
		queue.add(rootNode);

		setAllNodesVisited(false);			// generally everything would be visited=false in the starting of the algorithm
		rootNode.visited = true;
		
		while(true) {
			if(queue.isEmpty())
				break;
			mxm135730Node node = queue.poll();
			for(mxm135730Edge adjEdge : node.adjListEdges) 
				if(adjEdge.weight.equals(0) && adjEdge.b.visited == false) { 
					if(toPrint)
						pairingLists.add(new mxm135730PrintingPairs(adjEdge.a.id, adjEdge.b.id));
					queue.add(adjEdge.b);
					adjEdge.b.visited = true;
				}
		}
	}

	/**
	 * set all nodes as either visited or unvisited
	 * @param bool
	 */
	public void setAllNodesVisited(boolean bool) {		// just set all nodes as visited or not visited
		for(mxm135730Node node : nodesList)
			node.visited = bool;
	}

	/**
	 * get the unvisited node; ignore the rootNode
	 * @param rootNode
	 * @param shrinkedNode
	 * @return
	 */
	public mxm135730Node getNodeZ(mxm135730Node rootNode, List<mxm135730Node> shrinkedNode) {		 
		for(mxm135730Node node : nodesList)
			if(!shrinkedNode.contains(node) && !node.visited)
				return node;
		return null;
	}

	/**
	 * Find the 0-weight cycle using BFS
	 * @param z
	 * @param shrinkedNode
	 * @return
	 */
	public Integer findZeroWeightCycleBFS(mxm135730Node z, List<mxm135730Node> shrinkedNode) {
		
		List<mxm135730Node> cycleNodes = new LinkedList<mxm135730Node>();
		mxm135730Node n1 = z, n2;
		while(true) {

			n1.visited = true;
			n2 = getIncomingEdgeNodeWithZeroWeight(n1);
			if(n2 == null)										// end of the unvisited nodes
				break;
			else if(cycleNodes.contains(n2))
				break;
			else if(n2.visited) 
				cycleNodes.add(n2);
			n1 = n2;
		}

		/*
		 * Shrink nodes in the cycle here
		 */
		ShrinkCycleNodes(cycleNodes, shrinkedNode);
		return 0;
	}

	/**
	 * Find the incoming edge with 0-weight
	 * @param z
	 * @return
	 */
	public mxm135730Node getIncomingEdgeNodeWithZeroWeight(mxm135730Node z) {
		for(mxm135730Edge edge : z.incomingEdges)
			if(edge.weight.equals(0))
				return edge.a;
		return null;
	}
	
	/**
	 * Shrink the cycle to 1 node
	 * @param cycleNodes
	 * @param shrinkedNode
	 */
	private void ShrinkCycleNodes(List<mxm135730Node> cycleNodes, List<mxm135730Node> shrinkedNode) {
		addNewNode(cycleNodes);
		addToShrinkingNodes(cycleNodes, shrinkedNode);
	}

	/**
	 * Add shrinking nodes to a list
	 * @param cycleNodes
	 * @param shrinkedNode
	 */
	private void addToShrinkingNodes(List<mxm135730Node> cycleNodes, List<mxm135730Node> shrinkedNode) {
		shrinkedNode.addAll(cycleNodes);
	}

	/**
	 * Add a new node during shrinking
	 * @param cycleNodes
	 */
	private void addNewNode(List<mxm135730Node> cycleNodes) {

		++noOfNodes;
		mxm135730Node newContractNode = new mxm135730Node(noOfNodes);
		nodesList.add(newContractNode);
		nodesHashMap.put(noOfNodes, newContractNode);
		
		mxm135730Edge adjEdge, incomingEdge;
		// transfer weights and remove unwanted edges with non zero weights inside the cycle
		for(mxm135730Node node : cycleNodes) {
			Iterator<mxm135730Edge> i1 = node.adjListEdges.iterator();
			Iterator<mxm135730Edge> i2 = node.incomingEdges.iterator();
			while(i1.hasNext()) {
				adjEdge = i1.next();
				if(!cycleNodes.contains(adjEdge.b)) {
					adjEdge.a = newContractNode;
					newContractNode.addAdjNode(adjEdge);
				}
				else if(!adjEdge.weight.equals(0)) 
					i1.remove();
			}

			while(i2.hasNext()) {
				incomingEdge = i2.next();
				if(!cycleNodes.contains(incomingEdge.a)) {
					incomingEdge.b = newContractNode;
					newContractNode.addIncomingEdge(incomingEdge);
				}
				else if(!incomingEdge.weight.equals(0))
					i2.remove();
			}
		}
		
		
	}

	/**
	 * Print the graph when needed. Doesn't print necessarily from the root
	 */
	public void PrintGraph() {
		System.out.println(nodesList);
		for(mxm135730Node node : nodesList)
			System.out.println("For node : " + node + " >> " + node.adjListEdges + " Incoming Edges <<< " + node.incomingEdges);
	}

	/**
	 *  When |V| <= 50, print the pairs by expanding the contracted nodes
	 * @param rootNode
	 * @param shrinkedNode
	 * @param totalNoOfNodesInitially
	 */
	public void expandMSPAndPrintPairs(mxm135730Node rootNode, List<mxm135730Node> shrinkedNode, Integer totalNoOfNodesInitially) {
		for(mxm135730Node node : nodesList) {
			if(node.id.compareTo(totalNoOfNodesInitially) > 0)
				break;
			for(mxm135730Edge edge : node.adjListEdges) {
				edge.a = edge.originalA;
				edge.b = edge.originalB;
			}
		}
		List<mxm135730PrintingPairs> tracingList = new LinkedList<mxm135730PrintingPairs>();
		runGraphBFSWithZeroWeights(rootNode, shrinkedNode, tracingList, true);
		
		Collections.sort(tracingList, new Comparator<mxm135730PrintingPairs>() {
			@Override
			public int compare(mxm135730PrintingPairs p1, mxm135730PrintingPairs p2) {
				return p1.b - p2.b;
			}
		});
		for(mxm135730PrintingPairs pp : tracingList)
			System.out.println(pp);
	}
}
