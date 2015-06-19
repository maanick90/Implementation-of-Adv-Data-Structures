package com.utd.Project6;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran - Project 6
 * AVLItemNode class to store the id, list of names and price of each object 
 *
 */
public class mxm135730AVLItemNode {
	
	public Integer id;
	public List<Long> name = null;
	public BigDecimal price;
	public int height;
	public mxm135730AVLItemNode left;
	public mxm135730AVLItemNode right;

	/*
	 * default constructor
	 */
	public mxm135730AVLItemNode() {
	}
	
	/*
	 * parameterised constructor
	 */
	public mxm135730AVLItemNode(Integer id, List<Long> name, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.height = 0;
		this.left = null;
		this.right = null;		
	}
}
