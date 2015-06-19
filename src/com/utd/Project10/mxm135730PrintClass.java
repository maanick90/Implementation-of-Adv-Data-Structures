package com.utd.Project10;

import java.util.LinkedList;
import java.util.Queue;

public class mxm135730PrintClass {

	public static void PrintGraph(mxm135730Graph graph) {
		System.out.println("\n\n\n------------- Printing the graph --------------\n");
		System.out.println(graph.nodesList);
		System.out.println(graph.edgesList);
		for(mxm135730Node node : graph.nodesList)
			System.out.println("For node : " + node + " >> " + node.adjListEdges);
	}

	public static void PrintLabelling(mxm135730Graph graph) {

		System.out.println("\n\n\n------------ Printing Labels -----------\n");
		for(int i=1;i<=graph.noOfNodes;i++)
			System.out.println(graph.nodesHashMap.get(i).id + " >> label = " + graph.nodesHashMap.get(i).L);
	}

	public static void PrintZeroGraph(mxm135730Graph graph) {

		System.out.println("\n\n\n------------ Printing Zero Graph-----------\n");
		System.out.println(graph.zeroGraphNodes);
		for(mxm135730Edge edge : graph.zeroGraphEdges)
			System.out.println(edge);
	}

	public static void PrintFreeNodes(mxm135730Graph graph) {
		// First return the free node in the hungarian tree
		System.out.println("\n\n\n------------ Printing Free Nodes in ZG-----------\n");
		for(mxm135730Node node : graph.zeroGraphNodes) 				// blossom nodes are also added to zeroGraphNodes
			if(node.mate == null && node.blossomParent.equals(node))					// nodes inside the blossom should not be included
				System.out.println("ZG Free Node = " + node + " >> " + node.mate);
		for(mxm135730Node node : graph.nodesList)
			if(node.mate == null && node.blossomParent.equals(node))						// nodes inside the blossom should not be included
				System.out.println("Free Node = " + node + " >> " + node.mate);
	}

	public static void PrintMaximalMatching(mxm135730Graph graph) {

		System.out.println("\n\n\n------------ Printing Maximal Matching Nodes -----------\n");
		for(mxm135730Node node : graph.nodesList)
			if(node.mate != null) {
				System.out.println("("+node.id + "," + node.mate.id+")");
				node.seen = true;
				node.mate.seen = true;
			}
		for(mxm135730Node node : graph.nodesList)
			node.seen = false;
	}

	public static void PrintAugmentingPath(mxm135730Node u) {

		System.out.println("\n\n\n------------ Printing Augmenting Path -----------\n");
		while(u != null) {
			System.out.println(u + " and Parent: " + u.parentAugPath + " and Outer Node = " + u.outerNode);
			u = u.parentAugPath;
		}

	}

	public static void PrintCompleteNodeDetails(mxm135730Node blossomNode) {

		System.out.println("\n\n\n------------ Printing Complete Node details-----------\n");
		System.out.println("Id : " + blossomNode.id);
		System.out.println("Adj List Edges : " + blossomNode.adjListEdges);
		System.out.println("Label : " + blossomNode.L);
		System.out.println("Mate : " + blossomNode.mate);
		System.out.println("OuterNode : " + blossomNode.outerNode);
		System.out.println("Parent : " + blossomNode.parentAugPath);
		System.out.println("ZeroAdjEdges : " + blossomNode.zeroAdjEdges);
		System.out.println("Blossom Parent : " + blossomNode.blossomParent);
		System.out.println("Blossom Child Nodes : " + blossomNode.blossomChilds);

	}

	public static void PrintChildAugPath(mxm135730Node freeNode) {

		System.out.println("\n\n\n------------ Printing ChildAug Path details-----------\n");
		Queue<mxm135730Node> q = new LinkedList<mxm135730Node>();
		q.add(freeNode);
		while(!q.isEmpty()) {
			mxm135730Node u = q.poll();
			for(mxm135730Node node : u.childAugPath) {
				System.out.println(node + " has parent as " + u);
				q.add(node);
			}

		}
	}

}
