package com.utd.Project10;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * Node class which stores the ID, visited or not, adjListEdges(list of edges from this node to other nodes)
 */
import java.util.LinkedList;
import java.util.List;

public class mxm135730Node {

	public Integer id;
	public boolean visited;
	public List<mxm135730Edge> adjListEdges;

	public mxm135730Node mstParent;
	public Integer rank;
	public List<mxm135730Edge> mstEdge;

	public double L;
	public mxm135730Node mate;
	public boolean seen;
	public boolean outerNode;
	public mxm135730Node parentAugPath;
	public List<mxm135730Node> childAugPath;
	public List<mxm135730Edge> zeroAdjEdges;

	public mxm135730Node blossomParent;
	public List<mxm135730Node> blossomChilds;


	/**
	 * Default Constructor
	 */
	public mxm135730Node() {
		adjListEdges = new LinkedList<>();
	}

	/**
	 * Parameterized constructor
	 * @param id
	 */
	public mxm135730Node(Integer id) {
		this.id = id;
		this.visited = false;
		adjListEdges = new LinkedList<>();
		mstParent = null;
		rank = 0;
		mstEdge = new LinkedList<mxm135730Edge>();
		L = 0;
		mate = null;
		seen = false;
		outerNode = false;
		parentAugPath = null;
		childAugPath = new LinkedList<mxm135730Node>();
		zeroAdjEdges = new LinkedList<mxm135730Edge>();
		blossomParent = this;
		blossomChilds = new LinkedList<mxm135730Node>();
	}

	/**
	 * add the edge
	 * @param adj
	 */
	public void addAdjNode(mxm135730Edge adj) {
		this.adjListEdges.add(adj);
	}

	@Override
	public String toString() {				// Print the labels instead of id
		return "Node: " + L + " (id:"+id+")";
	}

	public boolean isRealOuterNode() {
		if(blossomChilds.size() == 0)
			return true;
		return false;
	}

	public mxm135730Edge getEdgeForGivenNode(mxm135730Node mate) {
		for(mxm135730Edge edge : adjListEdges)
		{
			if(edge.getOtherEnd(this).equals(mate))
				return edge;
		}

		return null;
	}

}