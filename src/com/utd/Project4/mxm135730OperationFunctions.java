package com.utd.Project4;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * @author Maaniccka Sentil Manickam Poonkundran - Project-4 
 * 		   OperationFunctions class to do all the operations
 */
public class mxm135730OperationFunctions {

	/**
	 * Method powerLists(): returns the linked list which contains the power value of list1, list2 in reverse order
	 * @param list1
	 *            is the 1st input list
	 * @param list2
	 *            is the 2nd input list
	 */
	public static LinkedList<Integer> powerLists(LinkedList<Integer> list1, LinkedList<Integer> list2) {
		
		LinkedList<Integer> powerLists = new LinkedList<Integer>();
		Iterator<Integer> i2 = list2.iterator();
		powerLists.add(1);														// just add 1 for easy convenience in multiplication. eg a=a*1
		int count = 0;
		while(i2.hasNext()) {
			int tempValue = i2.next() * (int) (Math.pow(10, count++));			// works when list2 value is >10
			for(int i=tempValue;i>0;i--)
				powerLists = multiplyLists(list1, powerLists);					// perform multiplication until power value gets to 0
		}
		return powerLists;
	}

	/**
	 * Method multiplyLists(): returns the linked list which contains the multiplied  value of list1, list2 in reverse order
	 * @param list1
	 *            is the 1st input list
	 * @param list2
	 *            is the 2nd input list
	 */
	public static LinkedList<Integer> multiplyLists(LinkedList<Integer> list1, LinkedList<Integer> list2) {
		
		int count=0;
		LinkedList<Integer> multiplyList = new LinkedList<>();
		multiplyList.add(0);												// for easy convenience in addition
		Iterator<Integer> i2 = list2.iterator();
		while(i2.hasNext()) {
			LinkedList<Integer> tempList = MultiplyListWithNo(list1, i2.next());	// multiply 1st list with each digit in list2
			for(int i=0;i<count;i++)
				tempList.addFirst(0);											// add 0's to the head 
			multiplyList = addLists(multiplyList, tempList);					// add lists to itself to get the result
			++count;
		}
		return multiplyList;
	}

	/**
	 * Method MultiplyListWithNo(): returns the linked list which contains the multiplied  value of list1,digit in reverse order
	 * @param list1
	 *            is the 1st input list
	 * @param no
	 *            is a single digit (0-9)
	 */
	private static LinkedList<Integer> MultiplyListWithNo(LinkedList<Integer> list1, Integer no) {
		
		int carry = 0, tempMultipliedValue = 0, tempValue = 0;
		LinkedList<Integer> multiplyList = new LinkedList<Integer>();
		Iterator<Integer> i1 = list1.iterator();
		while(i1.hasNext()) {
			tempMultipliedValue = (i1.next()*no) + carry;
			tempValue = tempMultipliedValue % 10;								// digit to be added in the linked list
			carry = tempMultipliedValue / 10;									// carry
			multiplyList.add(tempValue);
		}
		if(carry > 0)
			multiplyList.add(carry);
		
		return multiplyList;
	}

	/**
	 * Method subtractLists(): returns the linked list which contains the subtracted value of list1,digit in reverse order
	 * if the result is negative, then it returns 0
	 * @param list1
	 *            is the 1st input list
	 * @param list2
	 *            is the 2nd input list
	 */
	public static LinkedList<Integer> subtractLists(LinkedList<Integer> list1, LinkedList<Integer> list2) {
		
		LinkedList<Integer> subtractList = new LinkedList<>();
		Iterator<Integer> i1 = list1.iterator();
		Iterator<Integer> i2 = list2.iterator();
		int diff = 0;
		IntWrapper carryWrapper = new IntWrapper(0);	// wrapper class to carry carry value
		
		if(list1.size() < list2.size()) {				// if list2 > list1 in terms of number, then the result will be negative and hence return 0
			subtractList.add(0);
			return subtractList;
		}
		else if(list1.size() == list2.size()) {
			if(checkIfMin(list1, list2) == false) {		// if the size is equal, it can still be list2 > list2 
				subtractList.add(0);
				return subtractList;
			}
		}
		while(i1.hasNext() && i2.hasNext()) {
			diff = subtractNos(i1.next(), i2.next(), carryWrapper);		// subtract nos and store the result
			subtractList.add(diff);
		}
		while(i1.hasNext()) {
			diff = subtractNos(i1.next(), 0, carryWrapper);				// subtract the remaning no in the list1
			subtractList.add(diff);
		}
		return removeZeroes(subtractList);								// subtract unnecessary 0's in the first. Eg. when u subtract 1701-1700 = 1 
	}
	
	/**
	 * Method checkIfMin(): compares 2 lists of same size. Returns true if list1>list2 else returns false
	 * @param list1
	 *            is the 1st input list
	 * @param list2
	 *            is the 2nd input list
	 */
	private static boolean checkIfMin(LinkedList<Integer> list1, LinkedList<Integer> list2) {
		Iterator<Integer> i1 = list1.descendingIterator();
		Iterator<Integer> i2 = list2.descendingIterator();
		int temp1, temp2;
		while(i1.hasNext()) {
			temp1 = i1.next(); temp2 = i2.next();
			if(temp1 < temp2)
				return false;
			else if(temp1 > temp2)
				return true;
		}
		return false;
	}

	/**
	 * Method removeZeroes(): subtract unnecessary 0's in the first. Eg. when u subtract 1701-1700 = 1
	 * @param subtractList
	 *            is the subtracted list
	 */
	private static LinkedList<Integer> removeZeroes(LinkedList<Integer> subtractList) {
		
		Iterator<Integer> i1 = subtractList.descendingIterator();
		while(i1.hasNext())
			if(i1.next() == 0)
				i1.remove();
			else
				break;
		return subtractList;
	}

	/**
	 * Method subtractNos(): subtracts 2 nos and returns the subtracted value
	 * @param i1
	 *            is the 1st no
	 * @param i2
	 *            is the 2nd no
	 * @param carryWrapper
	 * 				is the wrapper class to carry the carry value         
	 */
	private static int subtractNos(Integer i1, Integer i2, IntWrapper carryWrapper) {
		if((i1-carryWrapper.i) >= i2) {							// when subtract 8-5
			int diff = (i1-carryWrapper.i)-i2;
			carryWrapper.i = 0;
			return diff;
		}
		else {
			int diff = (i1-carryWrapper.i+10) - i2;				// when subtract 5-8
			carryWrapper.i = 1;
			return diff;
		}
	}

	/**
	 * Method addLists(): returns the linked list which contains the added value of list1, list2 in reverse order
	 * @param list1
	 *            is the 1st input list
	 * @param list2
	 *            is the 2nd input list
	 */
	public static LinkedList<Integer> addLists(LinkedList<Integer> list1, LinkedList<Integer> list2) {
		
		LinkedList<Integer> addList = new LinkedList<Integer>();
		Iterator<Integer> i1 = list1.iterator();
		Iterator<Integer> i2 = list2.iterator();
		int sum=0;
		IntWrapper carryWrapper = new IntWrapper(0);
		
		while(i1.hasNext() && i2.hasNext()) {
			sum = addNos(i1.next(), i2.next(), carryWrapper);		// adding lists of same size
			addList.add(sum%10);
		}
		while(i1.hasNext()) {										// adding remaining values in the list1
			sum = addNos(i1.next(), 0, carryWrapper);
			addList.add(sum%10);
		}
		while(i2.hasNext()) {										// adding remaining values in the list2
			sum = addNos(0, i2.next(), carryWrapper);
			addList.add(sum%10);
		}
		if(carryWrapper.i > 0)										// finally add the carry value
			addList.add(carryWrapper.i);
		
		return addList;
	}
	
	/**
	 * Method addNos(): returns the added value of 2 nos
	 * @param i1
	 *            is the 1st no
	 * @param i2
	 *            is the 2nd no
	 * @param carryWrapper
	 * 				is the wrapper class to carry the carry value	 
	 */
	private static int addNos(Integer i1, Integer i2, IntWrapper carryWrapper) {
		int sum = i1+i2+carryWrapper.i;
		carryWrapper.i = sum / 10;
		return sum;
	}

	/**
	 * Method StrToNum(): returns a linked list which contains the digits of the given input string in reverse order
	 * @param inputNumber
	 *            is the given input string
	 */
	public static LinkedList<Integer> StrToNum(String inputNumber) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		for(int i=inputNumber.length()-1;i>=0;i--)
			list.add(inputNumber.charAt(i)-48);
		return list;
	}
	
	/**
	 * Method NumToStr(): returns a string value of the given linked list
	 * @param list
	 *            is the given input linked list
	 */
	public static String NumToStr(LinkedList<Integer> list) {
		StringBuilder sb = new StringBuilder();
		for(Integer i : list)
			sb.append(i);
		return sb.reverse().toString();
	}

	/*
	 * A wrapper class to wrap the 'carry' value used in both addition and subtraction
	 * In order to return 2 integer values, I used a wrapper class
	 */
	public static class IntWrapper { 
		int i;
		public IntWrapper(int i) {
			this.i = i;
		}
	}
}
