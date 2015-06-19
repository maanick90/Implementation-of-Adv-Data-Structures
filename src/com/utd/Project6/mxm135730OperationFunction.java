package com.utd.Project6;

 import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Maaniccka Sentil Manickam Poonkundran - Project 6
 * OperationFunction class - a class which includes all the functions of the project
 */
public class mxm135730OperationFunction {

	public mxm135730AVLItemTree idTree = null;										// AVL Tree to store the nodes based on id's
	/*
	 * HashMap of HashMap
	 * Outer HashMap - <Name,InnerHashMap>
	 * Inner HashMap - <Id, ObjectItem>
	 */
	public Map<Long, HashMap<Integer, mxm135730AVLItemNode>> nameHashMap = null;		 
	
	/**
	 * Default Constructor
	 */
	public mxm135730OperationFunction() {
		idTree = new mxm135730AVLItemTree();
		nameHashMap = new HashMap<Long, HashMap<Integer, mxm135730AVLItemNode>>();
	}
	
	/**
	 * Insert a new item
	 * @param id
	 * @param price
	 * @param name
	 * @return 1 if new item inserted, 0 if item updated
	 */
	public BigDecimal Insert(Integer id, BigDecimal price, List<Long> name) {
		
		mxm135730AVLItemNode node = new mxm135730AVLItemNode(id, name, price);	
		mxm135730AVLItemNode searchedNode = idTree.search(id);			// search if the item is already present

		if(searchedNode != null) {			// already present
			if(name != null) {			
				for(long l : searchedNode.name) {		// delete old names from HashMap
					if(nameHashMap.containsKey(l))
						if(nameHashMap.get(l).size() == 1)
							nameHashMap.remove(l);
						else 
							nameHashMap.get(l).remove(id);
				}
				searchedNode.name = name;			// update the new 'names'
			}
			searchedNode.price = price;				// update the price
			node = searchedNode;					// assign for future use in the function
		}
		else {
			idTree.insert(node);				// if item not present already, then insert it into the tree
		}
		if(name != null) {
			for(long l : name) { 				// insert new names into the HashMap 
				if(nameHashMap.containsKey(l))
					nameHashMap.get(l).put(id, node);
				else {
					HashMap<Integer, mxm135730AVLItemNode> hm = new HashMap<>();
					hm.put(id, node);
					nameHashMap.put(l, hm);
				}
			}
		}
		
		if(searchedNode != null)
			return new BigDecimal("0");				// return 0 if item updated
		else
			return new BigDecimal("1");				// return 1 if new item inserted
	}

	/**
	 * To find the price of the item with the given id
	 * @param id
	 * @return - price
	 */
	private BigDecimal Find(Integer id) {
		mxm135730AVLItemNode searchedNode = idTree.search(id);
		if(searchedNode != null)			
			return searchedNode.price;
		return new BigDecimal("0");				// if item not present, then return 0
	}

	/**
	 * Delete an item with the given id
	 * @param id
	 * @return - sum of all the names in the deleted item
	 */
	private BigDecimal Delete(Integer id) {
		
		BigDecimal sumName = new BigDecimal("0");
		mxm135730AVLItemNode searchedNode = idTree.search(id);		// search the node
		if(searchedNode != null ) {
			
			List<Long> nameList = searchedNode.name;
			idTree.delete(id);								// delete from the tree
			for(long l : nameList) {
				sumName = sumName.add(new BigDecimal(l));
				if(nameHashMap.containsKey(l))				// delete from the HashMap
					if(nameHashMap.get(l).size() == 1)
						nameHashMap.remove(l);
					else 
						nameHashMap.get(l).remove(id);
			}
		}
		return sumName;
	}

	/**
	 * Find the min price of all nodes with the given name
	 * @param name
	 * @return
	 */
	private BigDecimal FindMinPrice(long name) {
		
		HashMap<Integer, mxm135730AVLItemNode> idList = nameHashMap.get(name);		// get the list of nodes from the HashMap 
		BigDecimal minPrice = new BigDecimal(Double.MAX_VALUE);
		if(idList != null) {
			// Run thru the list and find the min price
			for(Map.Entry<Integer, mxm135730AVLItemNode> entry : idList.entrySet()) {
				mxm135730AVLItemNode tempValue = entry.getValue();
				if(tempValue.price.compareTo(minPrice) < 0)
					minPrice = tempValue.price;
			}
			return minPrice;
		}
		else
			return new BigDecimal("0");
	}
	
	/**
	 * Find the max price of all the nodes with the given name
	 * @param name
	 * @return
	 */
	private BigDecimal FindMaxPrice(long name) {
		
		HashMap<Integer, mxm135730AVLItemNode> idList = nameHashMap.get(name);
		BigDecimal maxPrice = new BigDecimal(Double.MIN_VALUE);
		if(idList != null) {
			// Run thru the list to get the max price
			for(Map.Entry<Integer, mxm135730AVLItemNode> entry : idList.entrySet()) {
				mxm135730AVLItemNode tempValue = entry.getValue();
				if(tempValue.price.compareTo(maxPrice) > 0)
					maxPrice = tempValue.price;
			}
			return maxPrice;
		}
		else
			return new BigDecimal("0");
	}

	/**
	 * To get the no of items with the given name and between lowPrice and highPrice
	 * @param name
	 * @param lowPrice
	 * @param highPrice
	 * @return
	 */
	private BigDecimal FindPriceRange(long name, BigDecimal lowPrice, BigDecimal highPrice) {
		
		HashMap<Integer, mxm135730AVLItemNode> idList = nameHashMap.get(name);	// get the list of nodes with the given name
		int count = 0;
		if(idList != null) {
			for(Map.Entry<Integer, mxm135730AVLItemNode> entry : idList.entrySet()) {
			BigDecimal price = entry.getValue().price;
			if(price.compareTo(lowPrice) >= 0 && price.compareTo(highPrice) <= 0)
				count++;
			}
		}
		return new BigDecimal(count);
	}
	
	/**
	 * Hike the price of all nodes with id between id1 and id2 by r%
	 * @param id1
	 * @param id2
	 * @param r
	 * @return
	 */
	private BigDecimal PriceHike(Integer id1, Integer id2, BigDecimal r) {

		BigDecimal returnValue = idTree.priceHike(id1, id2, r);
		return returnValue;
	}
	
	/**
	 * Parse the given input strInput and call the appropriate functions 
	 * @param strInput
	 * @return
	 */
	public BigDecimal parseInputAndAdd(String strInput) {

		String inputArray[] = strInput.split("\\s+");
		
		if(inputArray[0].equals("Insert")) {
			Integer id = Integer.parseInt(inputArray[1]);
			BigDecimal price = new BigDecimal(inputArray[2]).setScale(2, RoundingMode.FLOOR);
			List<Long> name = new LinkedList<Long>();
			int inputArrayLength = inputArray.length;
			for(int i=3;i<inputArrayLength-1;i++)
				name.add(Long.parseLong(inputArray[i]));
			if(name.size() == 0)
				name = null;
			return Insert(id, price, name);
		}
		else if(inputArray[0].equals("Find"))
			return Find(Integer.parseInt(inputArray[1]));
		else if(inputArray[0].equals("Delete"))
			return Delete(Integer.parseInt(inputArray[1]));
		else if(inputArray[0].equals("FindMinPrice"))
			return FindMinPrice(Long.parseLong(inputArray[1]));
		else if(inputArray[0].equals("FindMaxPrice"))
			return FindMaxPrice(Long.parseLong(inputArray[1]));
		else if(inputArray[0].equals("FindPriceRange")) 
			return FindPriceRange(Long.parseLong(inputArray[1]), new BigDecimal(inputArray[2]).setScale(2, RoundingMode.FLOOR), new BigDecimal(inputArray[3]).setScale(2, RoundingMode.FLOOR));
		else if(inputArray[0].equals("PriceHike")) {
			BigDecimal temp1 = PriceHike(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2]), new BigDecimal(inputArray[3]).setScale(2, RoundingMode.FLOOR));
			return temp1;
		}
		return new BigDecimal("0");
	}
	
	@Override
	public String toString() {
		return "\n\n------ OF -------\nidTreeMap >> " + idTree + "\nnameHashMap >> " + nameHashMap;
	}

}

