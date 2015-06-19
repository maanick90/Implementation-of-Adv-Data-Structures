package com.utd.Project5;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Maaniccka Sentil Manickam Poonkundran - Project-5 
 * 		   Additional Operation Functions class to do all the additional operations
 */
public class mxm135730AdditionalOperations {

	/**
	 * Method divideAndRemainderFunction(): returns an object which contains the quotient and remainder value
	 * @param strNum
	 *            is the numerator
	 * @param strDen
	 *            is the denominator
	 *            
	 * RUNNING TIME: O(log n)
	 */
	public static QuotientAndRemainderWrapperClass divideAndRemainderFunction(String strNum, String strDen) {  

		LinkedList<Integer> num = mxm135730OperationFunctions.StrToNum(strNum);
		LinkedList<Integer> den = mxm135730OperationFunctions.StrToNum(strDen);
		
		LinkedList<Integer> quotient = mxm135730OperationFunctions.StrToNum("0");
		LinkedList<Integer> remainder = mxm135730OperationFunctions.StrToNum("0");
		/*
		 * handling denominator of zero
		 */
		if(den.equals(mxm135730OperationFunctions.StrToNum("0")))
			return new QuotientAndRemainderWrapperClass(mxm135730OperationFunctions.NumToStr(quotient), mxm135730OperationFunctions.NumToStr(remainder));
			 
		LinkedList<Integer> tempListValue1 = mxm135730OperationFunctions.StrToNum("1");
		LinkedList<Integer> tempListValue2 = mxm135730OperationFunctions.StrToNum("2");
		LinkedList<Integer> temp1 = mxm135730OperationFunctions.StrToNum("1");
		/*
		 * find the range in powers of 2 where the quotient lies
		 */
		while(checkMin(num,den,false) == false) {
			den = mxm135730OperationFunctions.StrToNum(mxm135730OperationFunctions.multiplyLists(mxm135730OperationFunctions.NumToStr(den), mxm135730OperationFunctions.NumToStr(tempListValue2)));
			temp1 = mxm135730OperationFunctions.StrToNum(mxm135730OperationFunctions.multiplyLists(mxm135730OperationFunctions.NumToStr(temp1), mxm135730OperationFunctions.NumToStr(tempListValue2)));
		}
		while(checkMin(temp1, tempListValue1, true) == false ) {
			den = divideListBy2(den);
			temp1 = divideListBy2(temp1);
			if(checkMin(num, den, false) == false) {
				num = mxm135730OperationFunctions.StrToNum(mxm135730OperationFunctions.parseSubtraction(mxm135730OperationFunctions.NumToStr(num), mxm135730OperationFunctions.NumToStr(den)));
				quotient = mxm135730OperationFunctions.StrToNum(mxm135730OperationFunctions.parseAddition(mxm135730OperationFunctions.NumToStr(quotient), mxm135730OperationFunctions.NumToStr(temp1)));
			}
		}
		remainder = num;
		
		/*
		 * handling negative numbers accordingly
		 */
		if( (mxm135730OperationFunctions.isNegative(strNum) && !mxm135730OperationFunctions.isNegative(strDen)) || (!mxm135730OperationFunctions.isNegative(strNum) && mxm135730OperationFunctions.isNegative(strDen)) )
			return new QuotientAndRemainderWrapperClass("-".concat(mxm135730OperationFunctions.NumToStr(quotient)), "-".concat(mxm135730OperationFunctions.NumToStr(num)));
		else
			return new QuotientAndRemainderWrapperClass(mxm135730OperationFunctions.NumToStr(quotient), mxm135730OperationFunctions.NumToStr(num));
	}

	/**
	 * Method QuotientAndRemainderWdivideListBy2(): takes a list as input, divide it by 2 and returns another list
	 * @param list
	 *            is the input list
	 */
	public static LinkedList<Integer> divideListBy2(LinkedList<Integer> list) {
		
		LinkedList<Integer> rL = new LinkedList<>();
		Iterator<Integer> i = list.iterator();
		int prev = i.next(), current;
		/*
		 * as the list is reversed, handle properly
		 */
		while(i.hasNext()) {
			current = i.next();
			if(current % 2 == 1)
				rL.add((10+prev)/2);
			else
				rL.add(prev/2);
			prev = current;
		}
		rL.add(prev/2);
		return mxm135730OperationFunctions.removeZeroes(rL, false);
	}

	/*
	 * return false is list1>list2
	 */
	public static boolean checkMin(LinkedList<Integer> list1, LinkedList<Integer> list2, boolean returnTrueIfExactlyEqual) {
		if(list1.size() > list2.size())
			return false;
		else if(list1.size() == list2.size())
			return mxm135730OperationFunctions.checkMinEqualSize(list1, list2, returnTrueIfExactlyEqual);
		else
			return true;
	}
	
	/**
	 * QuotientAndRemainderWrapperClass(): a wrapper class to hold both quotient and remainder value together
	 */
	public static class QuotientAndRemainderWrapperClass {
		public String quotient;
		public String remainder;
		public QuotientAndRemainderWrapperClass() { 
			quotient = "0";
			remainder = "0";
		}
		public QuotientAndRemainderWrapperClass(String quotient, String remainder) {
			this.quotient = quotient;
			this.remainder = remainder;
		}
	}

	/**
	 * Method squareRootLists(): takes a no as input string and returns another string
	 * @param strNo
	 *            is the input string
	 * RUNNING TIME: O(log n) using binary search
	 */
	public static String squareRootLists(String strNo) {
		
		LinkedList<Integer> list = mxm135730OperationFunctions.StrToNum(strNo);
		LinkedList<Integer> numberVal1 = new LinkedList<Integer>();
		numberVal1.add(1);
		LinkedList<Integer> numberVal2 = new LinkedList<Integer>();
		numberVal2.add(2);
		/*
		 * low = 0;
		 * high = n/2 + 1;
		 * mid = low + (high-low)/2
		 */
		LinkedList<Integer> low = mxm135730OperationFunctions.StrToNum("0");
		LinkedList<Integer> high = mxm135730OperationFunctions.StrToNum(mxm135730OperationFunctions.parseAddition(mxm135730OperationFunctions.NumToStr(divideListBy2(list)), mxm135730OperationFunctions.NumToStr(numberVal1)));
		LinkedList<Integer> mid = new LinkedList<Integer>();
		
		if(checkMin(list, numberVal2, false) == true)
			return mxm135730OperationFunctions.NumToStr(list);

		/*
		 * Binary search between 1 and n/2 values as square root of 'n' always lie between them
		 */
		while( checkMin(mxm135730OperationFunctions.StrToNum(mxm135730OperationFunctions.parseAddition(mxm135730OperationFunctions.NumToStr(low), mxm135730OperationFunctions.NumToStr(numberVal1))), high, false) == true) {
			
			LinkedList<Integer> tempLL = divideListBy2(mxm135730OperationFunctions.StrToNum(mxm135730OperationFunctions.parseSubtraction(mxm135730OperationFunctions.NumToStr(high),mxm135730OperationFunctions.NumToStr(low))));
			mid = mxm135730OperationFunctions.StrToNum(mxm135730OperationFunctions.parseAddition(mxm135730OperationFunctions.NumToStr(low), mxm135730OperationFunctions.NumToStr(tempLL)));
			
			LinkedList<Integer> square = mxm135730OperationFunctions.StrToNum(mxm135730OperationFunctions.powerLists(mxm135730OperationFunctions.NumToStr(mid),mxm135730OperationFunctions.NumToStr(numberVal2)));
			if(checkMin(square, list, true) == false)
				high = mid;
			else if(checkMin(square, list, false) == true)
				low = mid;
			else
				return mxm135730OperationFunctions.NumToStr(mid);
		}
		/*
		 * add a 'i' to handle square root of negative values
		 */
		if(mxm135730OperationFunctions.isNegative(strNo))
			return mxm135730OperationFunctions.NumToStr(low).concat("i");
		return mxm135730OperationFunctions.NumToStr(low);
	}

	/**
	 * Method maxPowerIn15Secs(): takes a no as input string and returns the maximum power that can be generated in 15000 seconds by sqauring
	 * @param no
	 *            is the input string
	 */
	public static LinkedList<Integer> maxPowerIn15Secs(String no) {
		
		LinkedList<Integer> resultList = mxm135730OperationFunctions.StrToNum(no);
		LinkedList<Integer> listNum2 = new LinkedList<Integer>();
		listNum2.add(2);
		
		long start = System.currentTimeMillis();
		long end;
		while(true) {
			end = System.currentTimeMillis();																	// calculate end time repeatedly
//			System.out.println(end + ">>>" + (end-start));
			
			if( (end-start) > 1500)
				break;
			resultList = mxm135730OperationFunctions.powerListsUsingLinkedList(resultList, listNum2);				// power by 2
		}
		return resultList;
	}

}
