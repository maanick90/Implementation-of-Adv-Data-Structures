package com.utd.Project9PartA;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
		noOfNodes = 0;
		matchingSize = 0;
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
	 * Set all nodes as unvisited.
	 * This function is called before running a BFS or DFS
	 */
	private void setAllNodesAsUnvisited() {
		for(Map.Entry<Integer, mxm135730Node> entry : nodesHashMap.entrySet())
			entry.getValue().seen = false;
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

	/**
	 * Get the maximal cardinality matching
	 */
	public void maximalCardinalityMatching() {
		
		for(mxm135730Node node : nodesList) {
			if(node.mate == null && node.adjListEdges != null)
				for(mxm135730Edge edge : node.adjListEdges)
					if(edge.a.mate == null && edge.b.mate == null) {
						edge.a.mate = edge.b;
						edge.b.mate = edge.a;
						++matchingSize;
						break;
					}
		}
	}

	/**
	 * Run the algorithm to get maximum cardinality matching
	 */
	public void maximumCardinalityMatching() {
		
		/*
		 * Add all free nodes to the queue
		 */
		Queue<mxm135730Node> q = new LinkedList<mxm135730Node>();
		boolean flag;
		mxm135730Node u,v,x;
		
		while(true) {
			q.clear();
			flag = false;
			for(mxm135730Node node : nodesList) {
				node.seen = false;
				node.parent = null;
				node.outerNode = false;
				if(node.mate == null) {
					q.add(node);
					node.seen = true;
					node.outerNode = true;
				}
			}
			while(!q.isEmpty()) {
			
				u = q.poll();
				for(mxm135730Edge edge : u.adjListEdges) {
					v = edge.getOtherEnd(u);
					if(!v.seen) {
						v.seen = true;
						v.parent = u;
						if(v.mate == null) {		
							// free inner node; process aug path
							v.outerNode = false;
							ProcessAugPath(v);
							flag = true;
							break;
						}
						else {		
							// matched edge, grow tree by 2 steps
							v.outerNode = false;
							x = v.mate;
							x.seen = true;
							x.parent = v;
							x.outerNode = true;
							q.add(x);
						}
					}
					else if(v.seen) {
						if(!v.outerNode) {
							// if v is an inner node that has already been seen
							continue;
						}
						else if(v.outerNode && LCA(u, v)==null) {
							/*
							 *  v is an outernode in different tree
							 *  The reverse the parent pointers in the other tree
							 *  Aug Path is from Path from u to its root + edge(u,v) + Path from v'root to v
							 */
							mxm135730Node parentV = getParent(v);
							reverseParentPointers(v).parent = u;
							ProcessAugPath(parentV);
							flag = true;
							break;
							
						}
						else if(v.outerNode && LCA(u, v)!=null) {
							// v is an outernode in same tree; blossom detected
						}
					}
				}
				if(flag)
					break;
			}
			if(!flag)
				break;
		}
	}
	
	/**
	 * Get the root in the tree
	 * @param v
	 * @return
	 */
	private mxm135730Node getParent(mxm135730Node v) {
		while(v.parent != null)
			v = v.parent;
		return v;
	}

	/**
	 * Reverse the parent pointers
	 * @param v
	 * @return
	 */
	private mxm135730Node reverseParentPointers(mxm135730Node v) {
		if(v.parent != null)
			reverseParentPointers(v.parent).parent = v;
		return v;
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
	 * Find the LCA of the 2 nodes
	 * @param u is the first node
	 * @param v is the second node
	 * @return
	 */
	private mxm135730Node LCA(mxm135730Node u, mxm135730Node v) {
		
		List<mxm135730Node> tempList = new LinkedList<mxm135730Node>();
		while(u != null) {
			tempList.add(u);
			u = u.parent;
		}
		while(v != null) {
			if(tempList.contains(v))
				return v;
			v = v.parent;
		}
		return null;
	}
}
