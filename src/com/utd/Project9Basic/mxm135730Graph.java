package com.utd.Project9Basic;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class mxm135730Graph {
	
	public List<mxm135730Node> nodesList;
	public List<mxm135730Edge> edgesList;
	public Map<Integer, mxm135730Node> nodesHashMap;
	public Integer noOfNodes;
	
	public Integer matchingSize;
	
	/**
	 * Default Constructor
	 */
	public mxm135730Graph() {
		nodesList = new LinkedList<mxm135730Node>();
		edgesList = new LinkedList<mxm135730Edge>();
		nodesHashMap = new HashMap<>();
		matchingSize = 0;
		noOfNodes = 0;
	}

	/**
	 * Add a node and edge to the graph
	 * @param node1
	 * @param node2
	 * @param weight
	 */
	public void addNodeAndEdge(mxm135730Node node1, mxm135730Node node2, Integer weight) {
		
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
			
		mxm135730Edge edge = new mxm135730Edge(node1, node2, weight);
		edgesList.add(edge);
		node1.addAdjNode(edge);		// add the adjacency list matrix for node1
		node2.addAdjNode(edge);
		
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
		for(mxm135730Node node : nodesList)
			System.out.println("For node : " + node + " >> " + node.adjListEdges);
	}

 	/**
 	 * Check if the graph is bipartite
 	 * @param rootNode
 	 * @return
 	 */
	public boolean isBipartite() {
		
		/*
		 * 1) Run BFS to mark outer node and inner node respectively
		 * 2) Then process each node and its adjNode to check if the graph is Bipartite or no
		 */
		
		setAllNodesAsUnvisited();
		for(mxm135730Node node : nodesList) {
			if(!node.visited) 
				runBipartite(node);
		}
		
		if(edgesList != null) {
			for(mxm135730Edge edge : edgesList)
				if(edge.b.outerNode == edge.a.outerNode) 
					return false;
		}
		return true;
	}
	
	/**
	 * Run the bipartite function using BFS
	 * @param node
	 */
	public void runBipartite(mxm135730Node node) {
		
		Queue<mxm135730Node> q = new LinkedList<mxm135730Node>();
		mxm135730Node tempNode, adjNode;
		
		q.add(node);
		node.outerNode = true;
		
		while(!q.isEmpty()) {
			tempNode = q.poll();
			for(mxm135730Edge edge : tempNode.adjListEdges) {
				adjNode = edge.getOtherEnd(tempNode);
				if(!adjNode.visited) {
					adjNode.visited = true;
					adjNode.outerNode = !tempNode.outerNode;
					q.add(adjNode);
				}
			}
		}
	}

	/**
	 * Set all nodes as unvisited
	 * This function is used before calling BFS or DFS
	 */
	private void setAllNodesAsUnvisited() {
		for(Map.Entry<Integer, mxm135730Node> entry : nodesHashMap.entrySet())
			entry.getValue().visited = false;
	}

	/**
	 * Get the maximal cardinality matching
	 */
	public void maximalCardinalityMatching() {
		
		if(edgesList != null) {
			for(mxm135730Edge edge : edgesList) {
				if(edge.a.mate == null && edge.b.mate == null) {
					edge.a.mate = edge.b;
					edge.b.mate = edge.a;
					++matchingSize;
				}
			}
		}
	}

	/**
	 * Get the maximum cardinality matching
	 */
	public void maximumCardinalityMatching() {

		mxm135730Node u,v,x;
		boolean flag;
		
		while(true) {
		
			flag = false;
			Queue<mxm135730Node> q = new LinkedList<mxm135730Node>();
			if(nodesList != null) {
				for(mxm135730Node node : nodesList) {
					node.visited = false;
					node.parent = null;
					if(node.mate == null && node.outerNode == true) {
						node.visited = true;
						q.add(node);
					}
				}
			}	// end of finding free nodes
			
			while(!q.isEmpty()) {
				u = q.poll();
				for(mxm135730Edge edge : u.adjListEdges) {
					v = edge.getOtherEnd(u);
					if(v.visited == false) {
						v.visited = true;
						v.parent = u;
						if(v.mate == null) {	// Augmenting path found
							ProcessAugPath(v);
							flag = true;
							break;
						}
						else {
							x = v.mate;
							x.visited = true;
							x.parent = v;
							q.add(x);
						}
					}
				}
				if(flag)
					break;
			}	// end of inner while loop
			if(!flag)
				break;
		}	// end of outer while loop 
		
	}

	/**
	 * Helper function to process augmenting path which increases the matchingCount by 1
	 * @param v
	 */
	private void ProcessAugPath(mxm135730Node v) {

		mxm135730Node p,x,nmx,y;
		p = v.parent;
		x = p.parent;
		p.mate = v;
		v.mate = p;
		
		while(x != null) {
			nmx = x.parent;
			y = nmx.parent;
			x.mate = nmx;
			nmx.mate = x;
			x = y;
		}
		++matchingSize;
	}

	/**
	 * Print the matching edges in pairs
	 */
	public void PrintPairs() {
		
		Collections.sort(nodesList, new Comparator<mxm135730Node>() {
			@Override
			public int compare(mxm135730Node arg0, mxm135730Node arg1) {		
				if(arg0.id.compareTo(arg1.id) > 0)
					return 0;
				return -1;
			}
		});
		if(nodesList != null)
			for(mxm135730Node node : nodesList) {
				if(node.mate != null)
					System.out.println(node.id + "  " + node.mate.id);
				else
					System.out.println(node.id + "  " + "-");
			}
	}
}
