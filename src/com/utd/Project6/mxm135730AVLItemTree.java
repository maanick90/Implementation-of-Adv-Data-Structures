package com.utd.Project6;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran
 * AVLItemTree class - which constructs an AVL Tree with the AVLItemNode as the nodes
 * The tree is constructed using the id's of each node as the comparator
 */
public class mxm135730AVLItemTree {

	mxm135730AVLItemNode root;

	/**
	 * Default Constructor
	 */
	public mxm135730AVLItemTree() {
		this.root = null;
	}
	
	/**
	 * To insert a new node in the tree
	 * @param node - a new item node which is to be inserted into the tree 
	 */
	public void insert(mxm135730AVLItemNode node) {
		root = insert(root, node);
	}
	/**
	 * To insert a new node in the tree
	 * @param root - root node of the tree 
	 * @param node - new node to be inserted
	 * @return	- the root of the tree
	 */
	private mxm135730AVLItemNode insert(mxm135730AVLItemNode root, mxm135730AVLItemNode node) {
		if (root == null)
			root = node;
		else if (node.id <= root.id) {
			root.left = insert(root.left, node);
			root.height = root.left.height + 1;
		}
		else {
			root.right = insert(root.right, node);
			root.height = root.right.height + 1;
		}
		
		return rotate(root);
	}

	/**
	 * Find the difference in height between the 2 sub trees
	 * @param root
	 * @return the difference
	 */
	private int diffHeight(mxm135730AVLItemNode root) {
		int h1 = -1, h2 = -1;
		if(root.left != null)
			h1 = root.left.height;
		if(root.right != null)
			h2 = root.right.height;
		return h1 - h2;
	}

	/**
	 * To delete a node with given id in the tree
	 * @param id - delete node with the given id 
	 */
	public void delete(Integer id) {
		if (isEmpty() == true)
			System.out.println("\nTree is empty!");
		else if (search(id) == null)
			System.out.println("\nData not found!");
		else
			root = delete(root, id);
	}
	/**
	 * To delete a node with given id in the tree
	 * @param root - root node of the tree 
	 * @param id - the node to be deleted with the given id  
	 * @return	- root of the tree
	 */
	private mxm135730AVLItemNode delete(mxm135730AVLItemNode root, Integer id) { 
		if (root.id.equals(id)) {
			if (root.left == null && root.right == null)
				return null;
			else if (root.left == null)
				return root.right;
			else if (root.right == null)
				return root.left;
			else {
				mxm135730AVLItemNode tempL = root.left;				// store the left node 
				mxm135730AVLItemNode tempMin = findMin(root.right);	// find the min node in the right subtree
				mxm135730AVLItemNode tempR = removeMin(root.right);	// remove that min node in the right subtree
				root = tempMin;								// copy the min node to the root
				root.left = tempL;							// update the new root.left node
				root.right = tempR;							// update the new root.right node
			}

		} else if (root.id.compareTo(id) < 0)
			root.right = delete(root.right, id);
		else if (root.id.compareTo(id) > 0)
			root.left = delete(root.left, id);
		
		return rotate(root);
	}
	
	/**
	 * Perform LL, LR, RL, RR rotation if necessary 
	 * @param root
	 * @return - root after rotation
	 */
	private mxm135730AVLItemNode rotate(mxm135730AVLItemNode root) {
		if(diffHeight(root) == 2) {		// LL rotation
			if(diffHeight(root.left) == -1) 	// LR rotation
				LR_Rotation(root);
			root = LL_Rotation(root);
		}
		else if(diffHeight(root) == -2) {			// RR rotation
			if(diffHeight(root.right) == 1) 		// RL rotation
				RL_Rotation(root);
			root = RR_Rotation(root);
		}
		return root;
	}

	/**
	 * Perform RR rotation
	 * @param root
	 * @return
	 */
	private mxm135730AVLItemNode RR_Rotation(mxm135730AVLItemNode root) {
		mxm135730AVLItemNode tempR = root.right;
		root.right = tempR.left;
		tempR.left = root;
		root.height -= 2;
		root = tempR;
		return root;
	}

	/**
	 * Perform RL rotation
	 * @param root
	 */
	private void RL_Rotation(mxm135730AVLItemNode root) {
		mxm135730AVLItemNode tempL = root.right.left;
		root.right.left = tempL.right;
		tempL.right = root.right;
		root.right = tempL;
		tempL.height += 1;
		tempL.right.height -= 1;		
	}

	/**
	 * Perform LL rotation
	 * @param root
	 * @return
	 */
	private mxm135730AVLItemNode LL_Rotation(mxm135730AVLItemNode root) {
		mxm135730AVLItemNode tempL = root.left;
		root.left = tempL.right;
		tempL.right = root;
		root.height -= 2;
		root = tempL;
		return root;
	}

	/**
	 * Perform LR rotation
	 * @param root
	 */
	private void LR_Rotation(mxm135730AVLItemNode root) {
		mxm135730AVLItemNode tempR = root.left.right;
		root.left.right = tempR.left;
		tempR.left = root.left;
		root.left = tempR;
		tempR.height += 1;
		tempR.left.height -= 1;
	}

	/**
	 * To remove the min node in the subtree
	 * @param root - root node of any subtree                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
	 * @return	- root node after removing the min node in the subtree
	 */
	private mxm135730AVLItemNode removeMin(mxm135730AVLItemNode root) { 
		if (root == null)
			return root;
		else if (root.left != null) {
			root.left = removeMin(root.left);
			return root;
		} else
			return root.right;
	}
	/**
	 * To find the min node in the subtree
	 * @param root - root node of any subtree 
	 * @return - min node in the given subtree
	 */
	private mxm135730AVLItemNode findMin(mxm135730AVLItemNode root) {
		if (root != null)
			while (root.left != null)
				root = root.left;
		return root;
	}

	/**
	 * To search a node in the tree
	 * @param id - id of the node to be searched 
	 * @return - searched node (null if node not found)
	 */
	public mxm135730AVLItemNode search(Integer id) {
		mxm135730AVLItemNode temp = search(root, id);
		return temp;
	}
	/**
	 * To search a node in the tree
	 * @param root - root node of the tree 
	 * @param id - id of the node to be searched
	 * @return
	 */
	private mxm135730AVLItemNode search(mxm135730AVLItemNode root, Integer id) {
		if(root == null)
			return null;
		else if(root.id.compareTo(id) == 0)
			return root;
		else if(root.id.compareTo(id) < 0)
			return search(root.right, id);
		else
			return search(root.left, id);
	}

	/**
	 * To check if the tree is empty or not
	 * @return - TRUE if tree is empty, else return FALSE 
	 */
	private boolean isEmpty() {
		if (root == null)
			return true;
		return false;
	}

	/**
	 * To print the inorder traversal of the tree 
	 */
	public void inOrder() {
		System.out.println("Printing InOrder Traversal!");
		inOrderId(root);
	}
	/**
	 * To print the inorder traversal of the tree 
	 * @param root - root node 
	 */
	public void inOrderId(mxm135730AVLItemNode root) {
		if (root != null) {
			inOrderId(root.left);
			System.out.print(root + "  ");
			inOrderId(root.right);
		}
	}

	/**
	 * To print the pre order traversal of the tree
	 */
	public void preOrder() {
		System.out.println("Printing PreOrder Traversal!");
		preOrderId(root);
	}
	/**
	 * To print the pre order traversal of the tree
	 * @param root - root node of the tree
	 */
	public void preOrderId(mxm135730AVLItemNode root) {
		if (root != null) {
			System.out.print(root.id + "  ");
			preOrderId(root.left);
			preOrderId(root.right);
		}
	}

	/**
	 * To print the post order traversal of the tree
	 */
	public void postOrder() {
		postOrderId(root);
	}
	/**
	 * To print the post order traversal of the tree
	 * @param root - root node
	 */
	public void postOrderId(mxm135730AVLItemNode root) {
		if (root != null) {
			postOrderId(root.left);
			postOrderId(root.right);
			System.out.print(root.id + "  ");
		}
	}

	/**
	 * To update the price of the node with the given id
	 * @param id - id of the node to be updated
	 * @param price - new price
	 */
	public void updatePrice(Integer id, BigDecimal price) { 
		updatePrice(root, id, price);
	}
	/**
	 * To update the price of the node with the given id
	 * @param root - root node
	 * @param id - id of the node to be updated
	 * @param price - new price
	 */
	private void updatePrice(mxm135730AVLItemNode root, Integer id, BigDecimal price) { 
		if (root == null)
			return;
		else if (root.id.equals(id))
			root.price = price;
		else if (root.id < id)
			updatePrice(root.right, id, price);
		else
			updatePrice(root.left, id, price);
	}

	/**
	 * To hike the price of the nodes with id between id1 and id2 
	 * @param id1 - lower limit of id
	 * @param id2 - upper limit of id
	 * @param r - hike by r%
	 * @return - sum of increased price
	 */
	public BigDecimal priceHike(Integer id1, Integer id2, BigDecimal r) { 
		return priceHike(root, id1, id2, r);
	}
	/**
	 * To hike the price of the nodes with id between id1 and id2
	 * @param root - root node 
	 * @param id1 - lower limit of id
	 * @param id2 - upper limit of id
	 * @param r - hike by r%
	 * @return - sum of increased price
	 */
	private BigDecimal priceHike(mxm135730AVLItemNode root, Integer id1, Integer id2, BigDecimal r) { 
		if(root == null)
			return new BigDecimal("0");
		else {
			BigDecimal sumOfNetIncrease = new BigDecimal("0");
			if(root.id.compareTo(id1) >= 0 && root.id.compareTo(id2) <= 0) {
				// find the price amount to be increased
				BigDecimal temp = root.price.multiply(r.divide(new BigDecimal(100))).setScale(2, RoundingMode.FLOOR);	
				sumOfNetIncrease = temp;
				root.price = root.price.add(temp).setScale(2, RoundingMode.FLOOR);	// set the new price
			}
			// call recursively on both left and right subtrees and add the result
			return sumOfNetIncrease.add(priceHike(root.left, id1, id2, r).add(priceHike(root.right, id1, id2, r)));
		}
	}

}
