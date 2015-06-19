package com.utd.Project8PartA;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 
 * @author mxm135730
 * Graph class that implements all the functions in the graph
 */
public class mxm135730Graph {

	public Map<Integer, mxm135730Node> nodesHashMap;
	public List<mxm135730Node> nodesList;
	public List<mxm135730Edge> edgesList;

	/**
	 * Default Constructor
	 */
	public mxm135730Graph() {
		nodesList = new LinkedList<mxm135730Node>();
		edgesList = new LinkedList<mxm135730Edge>();
		nodesHashMap = new HashMap<Integer, mxm135730Node>();
	}
	
	/**
	 * Adding nodes and edges in the graph
	 * 
	 * @param node1
	 * 				: node at one end of the edge
	 * @param node2
	 * 				: node at other end of the edge
	 * @param weight
	 * 				: weight of the actual edge
	 */
	public void addNodeAndEdge(mxm135730Node node1, mxm135730Node node2, Integer weight) {
		if(nodesHashMap.containsKey(node1.id))
			node1 = nodesHashMap.get(node1.id);
		else {
			nodesHashMap.put(node1.id, node1);
			nodesList.add(node1);
		}
			
		if(nodesHashMap.containsKey(node2.id))
			node2 = nodesHashMap.get(node2.id);
		else {
			nodesHashMap.put(node2.id, node2);
			nodesList.add(node2);
		}
		
		mxm135730Edge edge = new mxm135730Edge(node1, node2, weight);
		node1.outgoingEdge.add(edge);
		edgesList.add(edge);
	}
	
	/**
	 *  Call the revised BellManFord Algorithm
	 *  
	 * @param startNode
	 * 					: node to start		
	 * @param targetNode
	 * 					: node which is the target
	 */
	public void BellManFordAlgorithm(mxm135730Node startNode, mxm135730Node targetNode) {
		
		Integer noOfNodes = nodesList.size();
		Queue<mxm135730Node> q = new LinkedList<mxm135730Node>();
		
		startNode.d = 0;
		q.add(startNode);
		
		while(!q.isEmpty()) {
			mxm135730Node u = q.poll();
			
			if(u.countNegativeCycle++ > noOfNodes) {
				System.out.println("Non-positive cycle in graph.  DAC is not applicable");
				System.exit(1);
			}
			if(u.outgoingEdge!=null) {
				for(mxm135730Edge edge : u.outgoingEdge) {
					if(Relax(u, edge.endNode, edge) && !q.contains(edge.endNode))
						q.add(edge.endNode);				// if v.d changes, then add it to the queue
				}
			}
		}
		return;
	}
	
	/**
	 * Relax function which calculates the change in the distance of the nodes
	 * @param u
	 * @param v
	 * @param edge
	 * @return
	 */
	public boolean Relax(mxm135730Node u, mxm135730Node v, mxm135730Edge edge) {
		if(v.d.compareTo(u.d + edge.weight) > 0){
			v.d = u.d + edge.weight;
			v.pred = u; 
			v.countNoOfPaths = u.countNoOfPaths;
			return true;
		}
		else if(v.d.equals(u.d + edge.weight))
			v.countNoOfPaths = u.countNoOfPaths + v.countNoOfPaths;
		
		return false;
	}
	
	
/*	@Override
	public String toString() {
		System.out.println("\n------ Printing the Graph!!! ------ ");
		System.out.println("Printing the nodes: " + nodesList);
		System.out.println("Printing the edges: " + edgesList);
		return "";
	}
*/
	/**
	 * Function to print the path in the shortest path when the no of nodes is less than 101
	 */
	public void PrintPath() {
		
		Collections.sort(nodesList, new Comparator<mxm135730Node>() {
			@Override
			public int compare(mxm135730Node arg0, mxm135730Node arg1) {		
				if(arg0.id.compareTo(arg1.id) > 0)
					return 0;
				return -1;
			}
		});
		for(mxm135730Node node : nodesList) {
			System.out.print(node.id + " "); 
			if(node.d.equals(Integer.MAX_VALUE))
				System.out.print("INF ");
			else
				System.out.print(node.d + " ");
			if(node.pred == null)
				System.out.print("- ");
			else
				System.out.print(node.pred.id + " ");
			System.out.println(node.countNoOfPaths);
		}
	}
}
