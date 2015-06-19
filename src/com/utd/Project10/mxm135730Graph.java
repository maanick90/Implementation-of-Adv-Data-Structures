package com.utd.Project10;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class mxm135730Graph {

	public Integer noOfNodes;
	public Integer noOfEdges;
	public Map<Integer, mxm135730Node> nodesHashMap;
	public List<mxm135730Node> nodesList;
	public List<mxm135730Edge> edgesList;

	public List<mxm135730Edge> mstTreeEdgesList;

	public Long matchingSize;

	public List<mxm135730Edge> zeroGraphEdges;
	public List<mxm135730Node> zeroGraphNodes;

	public List<mxm135730Edge> blossomInsideEdges;

	public mxm135730Graph() {
		nodesList = new LinkedList<mxm135730Node>();
		edgesList = new LinkedList<mxm135730Edge>();
		nodesHashMap = new HashMap<>();
		noOfNodes = 0;
		noOfEdges = 0;
		mstTreeEdgesList = new LinkedList<mxm135730Edge>();
		matchingSize = 0L;
		zeroGraphEdges = new LinkedList<mxm135730Edge>();
		zeroGraphNodes = new LinkedList<mxm135730Node>();
		blossomInsideEdges = new LinkedList<mxm135730Edge>();
	}

	/**
	 * add the nodes and edge to the graph
	 * @param node1
	 * @param node2
	 * @param weight
	 */
	public void addNodeAndEdge(mxm135730Node node1, mxm135730Node node2, Integer weight) {

		if(nodesHashMap.containsKey(node1.id)) {
			node1 = nodesHashMap.get(node1.id);
		} else {
			nodesList.add(node1);
			nodesHashMap.put(node1.id, node1);
		}

		if(nodesHashMap.containsKey(node2.id)) {
			node2 = nodesHashMap.get(node2.id);
		} else {
			nodesList.add(node2);
			nodesHashMap.put(node2.id, node2);
		}

		mxm135730Edge edge = new mxm135730Edge(node1, node2, weight);
		edgesList.add(edge);		// add the edge to the list
		node1.addAdjNode(edge);		// add the adjacency list matrix for node1
		node2.addAdjNode(edge);		// add the adjacency list matrix for node2
	}

	/**
	 * Provide a feasible labeling initially
	 */
	public void FeasibleLabelling() {
		for(mxm135730Node node : nodesList)
			node.L = 0.0;
		double max;
		for(int i=1;i<=2;i++) {
			for(mxm135730Node node : nodesList) {
				max = 0;
				for(mxm135730Edge edge : node.adjListEdges) {
					if(max <= ((double) edge.weight - edge.getOtherEnd(node).L))
						max = (double) edge.weight - edge.getOtherEnd(node).L;
				}

				node.L = max;
			}
		}
		mxm135730PrintClass.PrintLabelling(this);
	}

	public void ComputeZeroGraph(int countNo) {	// has to be computed on originalA and originalB in each node

		/**
		 * 1) Keep the zero graph edges between nodes inside the same blossom for blossom expansion
		 * 2) Clear ZGNodes and ZGEdges list
		 * 3) Recompute ZG again for edges between nodes with blossom parent as itself
		 */
		for(mxm135730Edge edge : zeroGraphEdges)		// Add edges between nodes inside the blossom to the permanent list
			if(sameBlossom(edge) && !blossomInsideEdges.contains(edge))	// Inefficient I guess
				blossomInsideEdges.add(edge);

		zeroGraphEdges.clear();
		zeroGraphNodes.clear();
		for(mxm135730Edge edge : edgesList) {
			if(!sameBlossom(edge) && (edge.originalA.L + edge.originalB.L) == (double) edge.weight )
			{
				zeroGraphEdges.add(edge);

				edge.originalA.zeroAdjEdges.add(edge);	// update zeroAdjEdges list in each end of the node
				edge.originalB.zeroAdjEdges.add(edge);

				if(!edge.a.equals(edge.originalA))	// update zeroAdjEdges list in blossom node also
					edge.a.zeroAdjEdges.add(edge);
				if(!edge.b.equals(edge.originalB))
					edge.b.zeroAdjEdges.add(edge);

				if(!zeroGraphNodes.contains(edge.a))	// add blossom nodes in the zeroGraphNodes
					zeroGraphNodes.add(edge.a);
				if(!zeroGraphNodes.contains(edge.b))
					zeroGraphNodes.add(edge.b);
			}
		}

		mxm135730PrintClass.PrintZeroGraph(this);
	}

	/**
	 * if 2 nodes are in the same blossom
	 * @param edge
	 * @return
	 */
	private boolean sameBlossom(mxm135730Edge edge) {
		if(edge.a.blossomParent.equals(edge.b.blossomParent))
			return true;
		return false;
	}

	/**
	 * Get the free node
	 * @return
	 */
	private mxm135730Node getFreeNode() {
		// First return the free node in the zeroGraphNodes
		List<mxm135730Node> freeNodeList = new LinkedList<mxm135730Node>();
		for(mxm135730Node node : zeroGraphNodes) 				// blossom nodes are also added to zeroGraphNodes
			if(node.mate == null && node.blossomParent.equals(node) && node.L > 0)					// nodes inside the blossom should not be included
				return node;
		for(mxm135730Node node : nodesList)
			if(node.mate == null && node.blossomParent.equals(node) && node.L > 0)						// nodes inside the blossom should not be included; inefficient
				return node;
		return null;
	}

	/**
	 * Greedy approach for maximal matching
	 * @param countNo
	 */
	private void MaximalMatching(int countNo) {

		/**
		 * 1) Mating need not be done for nodes inside the blossom
		 * 2) Initialize |M| = 0;
		 * 3) ZeroGraphEdges never contain edges inside the same blossom. Still we are checking again.
		 */
		matchingSize = 0L;
		for(mxm135730Node node : nodesList)
			node.mate = null;
		for(mxm135730Edge edge : zeroGraphEdges) {
			if(!sameBlossom(edge) && (edge.a.mate == null) && (edge.b.mate == null)) {		// mating is not done on nodes that are inside the blossom
				edge.a.mate = edge.b;
				edge.b.mate = edge.a;
				++matchingSize;
			}
		}

		mxm135730PrintClass.PrintMaximalMatching(this);

	}

	private void ReduceLabels(mxm135730Node freeNode, double minSlack) {
		/**
		 * 1) If real outer node, -Delta
		 * 2) If blossom outer node, +2*Delta and all real nodes inside the blossom by -Delta
		 * 3) If real inner node, +Delta
		 * 4) If blossom inner node, -2*Delta and all real nodes inside the blossom by +Delta
		 */
		/*System.out.println("\n\n\n---------- Updating Labels (before) ------------");
		while(node1 != null) {
			System.out.println(node1);
			node1 = node1.parentAugPath;
		}*/

		Queue<mxm135730Node> q = new LinkedList<mxm135730Node>();
		Queue<mxm135730Node> qTree = new LinkedList<mxm135730Node>();
		q.add(freeNode);
		qTree.add(freeNode);
		while(!q.isEmpty()) {
			mxm135730Node u = q.poll();
			for(mxm135730Node node : u.childAugPath) {
				q.add(node);
				qTree.add(node);
			}
		}

		while(!qTree.isEmpty()) {
			mxm135730Node node = qTree.poll();
			if(node.outerNode && node.blossomChilds.size() == 0)	// Point 1
				node.L -= minSlack;
			else if(node.outerNode && node.blossomChilds.size() > 0){
				node.L += 2*minSlack;
				for(mxm135730Node nodeChild : node.blossomChilds)
					if(nodeChild.blossomChilds.size() == 0)
						nodeChild.L -= minSlack;
			}
			else if(!node.outerNode && node.blossomChilds.size() == 0)
				node.L += minSlack;
			else if(!node.outerNode && node.blossomChilds.size() > 0) {
				node.L -= 2*minSlack;
				for(mxm135730Node nodeChild : node.blossomChilds)
					if(nodeChild.blossomChilds.size() == 0)
						nodeChild.L += minSlack;
			}
		}
		/*System.out.println("\n\n\n---------- Updating Labels (after) ------------");
		node1 = lastNodeInHT;
		while(node1 != null) {
			System.out.println(node1);
			node1 = node1.parentAugPath;
		}*/
		mxm135730PrintClass.PrintLabelling(this);
	}

	private double CalculateMinSlacks(mxm135730Node freeNode) {
		/**
		 * 1) Edge between outer node and node outside the tree
		 * 2) Real Edge between 2 outer nodes in the same tree
		 * 3) Labels of real outer nodes (if blossom is outer node, then consider all the real nodes inside the blossom)
		 * 4) Half the labels of inner blossoms
		 */

		mxm135730Node v, head1 = freeNode;
		double minSlack = Double.MAX_VALUE, slack = 0.0;

		Queue<mxm135730Node> q = new LinkedList<mxm135730Node>();
		Queue<mxm135730Node> qTree = new LinkedList<mxm135730Node>();
		q.add(freeNode);
		while(!q.isEmpty()) {
			mxm135730Node u = q.poll();
			u.seen = false;
			for(mxm135730Node node : u.childAugPath) {
				q.add(node);
				qTree.add(node);
			}
		}

		q.clear();
		q.add(freeNode);
		while(!q.isEmpty()) {
			mxm135730Node u = q.poll();
			for(mxm135730Node tempNode: u.childAugPath)			// add all the childs
				q.add(tempNode);
			if(u.outerNode) {
				if(u.isRealOuterNode() && u.L <= minSlack)						// Point 3
				{
					System.out.println("Slack 3: " + u.L);
					minSlack = u.L;
				}

				for(mxm135730Edge edge : u.adjListEdges) {
					v = edge.getOtherEnd(u);
					if(v.seen)
						continue;
					if(!isNodeInTree(qTree,v)) {	// Point 1: edge to node outside the tree; Inefficient
						slack = (edge.originalA.L+edge.originalB.L-(double)edge.weight);
						System.out.println("Slack1 = " + slack + " for Edge : " + edge);
						if(slack <= minSlack)
							minSlack = slack;
					}
					else if(v.outerNode && isNodeInTree(qTree,v) && !edge.isZeroEdge()) {	//Point 2;
						slack = (edge.originalA.L + edge.originalB.L - (double) edge.weight)/2;
						System.out.println("Slack2 = " + slack + " for Edge : " + edge);
						if(slack <= minSlack)
							minSlack = slack;
					}
				}
			}
			else {					// Point 4 : if inner node and it is blossom node, then
				if(!u.isRealOuterNode() && u.L <= minSlack)
					minSlack = u.L;
			}
		}

		if(minSlack < 0)
			System.exit(1);
		return minSlack;
	}

	private boolean isNodeInTree(Queue<mxm135730Node> qTree,mxm135730Node v) {
		if(qTree.contains(v))
			return true;
		return false;
	}

	/**
	 * Process the augmenting path to increase the matching
	 * @param v
	 */
	private void ProcessAugPath(mxm135730Node v) {

		mxm135730Node p,x,nmx,y;
		p = v.parentAugPath;
		x = p.parentAugPath;
		p.mate = v;
		v.mate = p;

		while(x != null) {
			nmx = x.parentAugPath;
			y = nmx.parentAugPath;
			x.mate = nmx;
			nmx.mate = x;
			x = y;
		}
		++matchingSize;

	}


	public boolean MaximumMatching(int countNo) {			// Returns true if matching found

		/**
		 * 1) Find Maximal Matching
		 * 2) Find a free node
		 * 3) If no free node found, then exit the algorithm saying that perfect matching is found
		 * 4) Then construct Aug path.
		 * 5) If alternating path is found, then increase the |M| and return
		 * 6) If Hungarian Tree is found, then find the slack, change labels and return
		 * 		Note: |M| will not be increased in (6)
		 * 7) Then compute zero graph and run this function again
		 */
		MaximalMatching(countNo);
		mxm135730Node freeNode = getFreeNode();
		if(freeNode == null)					// Perfect matching found
			return true;
		System.out.println("\n\nFree Node : "+ freeNode);

		/*
		 * Construct Augmenting Path
		 */

		Queue<mxm135730Node> q = new LinkedList<mxm135730Node>();
		for(mxm135730Node node : nodesList) {
			node.seen = false;
			node.parentAugPath = null;
			node.childAugPath.clear();
		}
		freeNode.seen = true;
		freeNode.outerNode = true;							// inefficient
		q.add(freeNode);

		mxm135730Node u = null,v,x;
		boolean flag = false;

		while(!q.isEmpty()) {
			u = q.poll();
			for(mxm135730Edge edge : u.adjListEdges) {
				if(edge.isZeroEdge()) {					// if that edge in zero graph
					v = edge.getOtherEnd(u);
					if(!v.isRealOuterNode() && v.L == 0) {			// if blossom node as inner node and if its label is 0, then expand it
						System.out.println("\n\n\n Expanding inner Blossom with label = 0 for node " + v);
						ExpandBlossom(v);
						MaximumMatching(countNo);
					}
					if(!v.seen) {
						v.seen = true;
						v.parentAugPath = u;
						u.childAugPath.add(v);
						v.outerNode = false;
						if(v.mate == null) {				// free inner node is found. increase |M| by 1
							ProcessAugPath(v);
							if(getFreeNode() == null)		// when matching increases by 1, there is a chance of getting perfect matching
								return true;
							flag = true;
							break;
						}
						else {								// grow the tree
							x = v.mate;
							x.seen = true;
							x.parentAugPath = v;
							v.childAugPath.add(x);
							x.outerNode=true;
							q.add(x);
						}
					}
					else {
						//						if(v.outerNode && !isInSameTree(u,v)) {	// u and v are in different tree and AugPath found
						//							/*
						//							 *  v is an outernode in different tree
						//							 *  The reverse the parent pointers in the other tree
						//							 *  Aug Path is from Path from u to its root + edge(u,v) + Path from v'root to v
						//							 */
						//							System.out.println(" @@@@@@@ LCA in the same tree @@@@@@@");
						//							Node parentV = getParent(v);
						//							reverseParentPointers(v).parent = u;
						//							ProcessAugPath(parentV);
						//							if(getFirstFreeNode().size() == 0)		// when matching increases by 1, there is a chance of getting perfect matching
						//								return true;
						//
						//							try {
						//								Thread.sleep(4000);
						//							} catch (InterruptedException e) {
						//								e.printStackTrace();
						//							}
						//							flag = true;
						//							break;
						//						}
						if(v.outerNode) {						// Blossom detected
							System.out.println("Blossom detected for edge " + edge);
							mxm135730Node newBlossomNode = BlossomDetectedFunction(u,v);
							q.remove(v);						// remove v as it will already be added in the queue
							u = newBlossomNode;
						}
					}

				}	// end of if
			} // end of for
			if(flag)
				break;
		}	// end of while

		//		PrintClass.PrintAugmentingPath(u);
		mxm135730PrintClass.PrintChildAugPath(getParent(u));

		if(!flag && u.outerNode) { 						// if u is outer node, then Hungarian Tree is formed
			/**
			 * 1) Calculate All Slacks and fin Min of it
			 * 2) Update labels
			 */

			double minSlack = CalculateMinSlacks(getParent(u));
			System.out.println("\n\nMinSlack = "+ minSlack);
			ReduceLabels(getParent(u), minSlack);
		}

		return false;
	}

	/**
	 * Reverse the parent pointers to handle the augmenting path between 2 different trees
	 * @param v
	 * @return
	 */
	private mxm135730Node reverseParentPointers(mxm135730Node v) {
		if(v.parentAugPath != null)
			reverseParentPointers(v.parentAugPath).parentAugPath = v;
		return v;
	}

	private mxm135730Node getParent(mxm135730Node v) {
		while(v.parentAugPath != null)
			v = v.parentAugPath;
		return v;
	}

	private boolean isInSameTree(mxm135730Node u, mxm135730Node v) {

		System.out.println("Is in same tree :" + u + v);
		List<mxm135730Node> tempList1 = new LinkedList<mxm135730Node>();
		while(u != null) {
			tempList1.add(u);
			u =u.parentAugPath;
		}
		while(v!=null){
			if(tempList1.contains(v))
				return true;
			v=v.parentAugPath;
		}
		return false;
	}

	private mxm135730Node BlossomDetectedFunction(mxm135730Node u, mxm135730Node v) {

		/**
		 * 1) Find LCA of u and v by storing all the nodes in the path from u to LCA and from v to LCA in a tempList
		 * 2) This list has to be used later
		 * 3) Construct the blossom node and update all the edges
		 */

		mxm135730Node LCA = null, newBlossomNode;
		List<mxm135730Node> blossomNodesList = FindLCA(u,v);
		LCA = blossomNodesList.remove(0);
		System.out.println("\n\nBlossom Details: ");
		System.out.println("Blossom Nodes List = " + blossomNodesList);
		System.out.println("LCA = " + LCA);
		newBlossomNode = ConstructBlossomNode(blossomNodesList, LCA);
		return newBlossomNode;
	}

	private mxm135730Node ConstructBlossomNode(List<mxm135730Node> blossomNodesList, mxm135730Node LCA) {
		++noOfNodes;
		mxm135730Node newBlossomNode = new mxm135730Node(noOfNodes);
		nodesList.add(newBlossomNode);
		nodesHashMap.put(newBlossomNode.id, newBlossomNode);
		zeroGraphNodes.add(newBlossomNode);

		newBlossomNode.mate = LCA.mate;					// always I guess
		if(LCA.mate != null)					// if blossom is first node in the tree
			LCA.mate.mate = newBlossomNode;
		newBlossomNode.parentAugPath = LCA.parentAugPath;
		if(LCA.parentAugPath != null) {
			LCA.parentAugPath.childAugPath.add(newBlossomNode);
			LCA.parentAugPath.childAugPath.remove(LCA);
		}
		newBlossomNode.outerNode = true;					// when a blossom is created, it is always an outer node
		for(mxm135730Node node : blossomNodesList) {
			newBlossomNode.blossomChilds.add(node);
			node.seen = false;
			node.mate = null;
			node.blossomParent = newBlossomNode;
			// update edges
			for(mxm135730Edge edge : node.adjListEdges) {
				if(!blossomNodesList.contains(edge.getOtherEnd(node))) { 		// edges from that node to outside the blossom
					newBlossomNode.adjListEdges.add(edge);
					edge.UpdateEdge(newBlossomNode, node);						// update the edge
					if(edge.originalA.L + edge.originalB.L == (double) edge.weight)		// if that edge is zero graph edge
						newBlossomNode.zeroAdjEdges.add(edge);
				}
			}
		}

		mxm135730PrintClass.PrintCompleteNodeDetails(newBlossomNode);
		return newBlossomNode;
	}

	private List<mxm135730Node> FindLCA(mxm135730Node u, mxm135730Node v) {

		//		PrintClass.PrintAugmentingPath(u);
		//		PrintClass.PrintAugmentingPath(v);
		List<mxm135730Node> blossomNodesList = new LinkedList<mxm135730Node>();		// Use hasing instead; inefficient
		mxm135730Node LCA = null;
		while(u != null) {
			blossomNodesList.add(u);
			u = u.parentAugPath;
		}
		while(v != null) {
			if(blossomNodesList.contains(v)) {
				LCA = v;
				break;
			}
			blossomNodesList.add(v);
			v = v.parentAugPath;
		}
		mxm135730Node tempLCA = LCA.parentAugPath;
		while(tempLCA != null) {
			blossomNodesList.remove(tempLCA);
			tempLCA = tempLCA.parentAugPath;
		}

		blossomNodesList.add(0, LCA);		// add the LCA as first of the list and remove it later
		return blossomNodesList;
	}

	public mxm135730Node getFirstBlossomNodeInTopMostLevel() {

		if(nodesHashMap.get(noOfNodes).blossomChilds.size() == 0)		// no further blossoms found
			return null;
		if(!nodesHashMap.get(noOfNodes).blossomParent.equals(nodesHashMap.get(noOfNodes)))
			System.out.println("\n\n%%%%%%%%%%%%%%%%%% getFirstBlossomNodeInTopMostLevel : SHOULD NOT BE EXECUTED %%%%%%%%%%%%%%%%%%%%%%");
		return nodesHashMap.get(noOfNodes);
	}

	public void ExpandBlossom(mxm135730Node blossomNodeToExpand) {


		// Cross Check
		for(mxm135730Node node : blossomNodeToExpand.blossomChilds)
			if(node.mate != null || node.seen)
				System.out.println("\n\n%%%%%%%%%%%%%%%%%% ExpandBlossom : SHOULD NOT BE EXECUTED %%%%%%%%%%%%%%%%%%%%%%");

		/**
		 * 1) First find the node inside the blossom which is mating with the outside node
		 * 2) There cannot be 2 such nodes
		 */

		mxm135730Node blossomNodeMatingOutside = getBlossomNodeMatingOutside(blossomNodeToExpand);
		System.out.println("Blossom node mating outside is " + blossomNodeMatingOutside);

		blossomNodeMatingOutside.mate = blossomNodeToExpand.mate;
		blossomNodeToExpand.mate.mate = blossomNodeMatingOutside;


		mxm135730Node current = blossomNodeMatingOutside, next=null, otherEndNode;
		while(current != null) {
			current.seen = true;
			next=null;
			for(mxm135730Edge edge : current.adjListEdges) {
				otherEndNode = edge.getOtherEnd(current);
				if(!otherEndNode.blossomParent.equals(current.blossomParent))	// node outside blossom
					edge.UpdateOriginalValue();
				else if(!otherEndNode.seen && next == null)				// get the next blossom node in the linear order
					next = otherEndNode;
				else
					continue;
			}
			if(current.mate == null && next != null)
				MutualMate(current, next);
			current.blossomParent = current;		//remove it from the blossom
			current = next;
		}
		blossomNodeToExpand.blossomChilds.clear();

		nodesHashMap.remove(noOfNodes);
		nodesList.remove(blossomNodeToExpand);
		--noOfNodes;

		mxm135730PrintClass.PrintMaximalMatching(this);

	}

	private void MutualMate(mxm135730Node a, mxm135730Node b) {
		a.mate = b;
		b.mate = a;
	}

	/**
	 * Get the blossom node that is mating outside the blossom
	 * @param blossomNodeToExpand
	 * @return
	 */
	private mxm135730Node getBlossomNodeMatingOutside(mxm135730Node blossomNodeToExpand) {

		mxm135730PrintClass.PrintCompleteNodeDetails(blossomNodeToExpand);
		for(mxm135730Edge edge : blossomNodeToExpand.adjListEdges) {
			if(edge.getOtherEnd(blossomNodeToExpand).equals(blossomNodeToExpand.mate))
				return edge.getOriginalNode(blossomNodeToExpand);
		}

		System.out.println("\n\n%%%%%%%%%%%%%%%%%% getBlossomNodeMatingOutside : SHOULD NOT BE EXECUTED %%%%%%%%%%%%%%%%%%%%%%");
		return null;
	}
	/**
	 * Get the maximum perfect matching value which is the output
	 * @return
	 */
	public Long GetMaximumWeightPerfectMatchingValue() {

		Long maxValue = 0L;
		mxm135730Edge edge = null;
		for(mxm135730Node node : nodesList)
			node.seen = false;
		for(mxm135730Node node : nodesList) {
			if(node.seen)
				continue;
			edge = node.getEdgeForGivenNode(node.mate);
			if(edge == null)
				System.out.println("\n\n%%%%%%%%%%%%%%%%%% GetMaximumWeightPerfectMatchingValue : SHOULD NOT BE EXECUTED %%%%%%%%%%%%%%%%%%%%%%");

			maxValue += edge.weight;
			node.seen = true;
			node.mate.seen = true;
		}

		return maxValue;
	}

	/**
	 * Kruskal algorithm for MST
	 */
	public void Kruskal() {

		mxm135730Node ru, rv;
		for(mxm135730Node node: nodesList)
			MakeSet(node);
		for(mxm135730Edge edge : edgesList) {					// edges will already be in non-decreasing order
			ru = Find(edge.originalA);
			rv = Find(edge.originalB);
			if(!ru.equals(rv)) {
				mstTreeEdgesList.add(edge);
				edge.originalA.mstEdge.add(edge);
				edge.originalB.mstEdge.add(edge);
				Union(ru,rv);
			}
		}
	}
	private void Union(mxm135730Node ru, mxm135730Node rv) {
		if(ru.rank > rv.rank)
			rv.mstParent = ru;
		else if(rv.rank > ru.rank)
			ru.mstParent = rv;
		else {
			rv.mstParent = ru;
			++ru.rank;
		}
	}
	private mxm135730Node Find(mxm135730Node node) {
		if(node.mstParent.equals(node))
			return node;
		return Find(node.mstParent);
	}
	private void MakeSet(mxm135730Node node) {
		node.mstParent = node;
	}

	/**
	 * Find the odd degree nodes
	 * @return
	 */
	public List<mxm135730Node> OddDegreeNodes() {

		List<mxm135730Node> oddDegreeNodes = new LinkedList<mxm135730Node>();
		for(mxm135730Node node : nodesList)
			if(node.mstEdge.size() % 2 == 1)
				oddDegreeNodes.add(node);
		return oddDegreeNodes;
	}

	/**
	 * Implement Floyd Warshall Algorithm to dins all pairs shortest paths
	 * @param oddDegreeNodes
	 * @return
	 */
	public Integer[][] AllPairsShortestPathsUsingFloydWarshallAlgorithm(List<mxm135730Node> oddDegreeNodes) {

		Integer[][] shortestPathMatrix = new Integer[noOfNodes][noOfNodes];

		for(int i=0;i<shortestPathMatrix.length;i++)
			for(int j=i;j<shortestPathMatrix.length;j++) {
				shortestPathMatrix[i][j] = 0;
				shortestPathMatrix[j][i] = 0;
			}

		for(mxm135730Edge edge : edgesList) {
			shortestPathMatrix[edge.originalA.id-1][edge.originalB.id-1] = edge.weight;
			shortestPathMatrix[edge.originalB.id-1][edge.originalA.id-1] = edge.weight;
		}

		for(int k=0;k<noOfNodes;k++)
			for(int i=0;i<shortestPathMatrix.length;i++)
				for(int j=i;j<shortestPathMatrix.length;j++) {
					if(shortestPathMatrix[i][j] > shortestPathMatrix[i][k] + shortestPathMatrix[k][j]) {
						shortestPathMatrix[i][j] = shortestPathMatrix[i][k] + shortestPathMatrix[k][j];
						shortestPathMatrix[j][i] = shortestPathMatrix[i][k] + shortestPathMatrix[k][j];
					}
				}

		System.out.println("\n\nAll Pairs Shortest Path: ");
		for(int i=0;i<noOfNodes;i++) {
			for(int j=0;j<noOfNodes;j++)
				System.out.print(shortestPathMatrix[i][j] + "  ");
			System.out.println();
		}
		return shortestPathMatrix;
	}

}
