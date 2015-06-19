package com.utd.Sort;

/**
 * @author Maaniccka Sentil Manickam Poonkundran
 *
 *  MergeSort implemented in 3 different ways
 *  	1) Basic Merge Sort
 *  	2) Better Merge Sort
 *  	3) Even Better Merge Sort
 *  
 *   All the 3 ways has the same running time O(n) but time can be saved a little by modifying the underlying part of the code.  
 */

public class MergeSort {

	/**
	 * main function to run the program
	 * @param args
	 */
	public static void main(String[] args) { 
		
		int n[] = {8388608, 16777216, 33554432, 67108864, 134217728};
		for(int i=0;i<n.length;i++)
			runFn(n[i]-1);
	}
	
	/**
	 * A driver function which creates an input array in the descending order
	 * @param n
	 */
	public static void runFn(int n) {

		int a[] = new int[n], b[] = new int[n];
		for(int i=0;i<n;i++) {
			a[i] = n-i;
			b[i] = n-i;
		}
			
		runAllSorting(a, b, n);			// call all the merge sort function for a given input
	}
	
	/**
	 * Function which calls all types of merge sort for the given input
	 * @param a - input
	 * @param b - another copy of input
	 * @param n - length of the input 
	 */
	public static void runAllSorting(int []a, int[] b, int n) { 
		
		System.out.println("\n\n\nN = "+n+"\nBasic Merge Sort:");
		long start = System.currentTimeMillis();
		BasicMergeSort(a, 0, n-1);
		long end = System.currentTimeMillis();
		for(int i=0;i<a.length;i++)
			if(a[i] != i+1) {
				System.out.println("Fails>>>>>>>");
				break;
			}
		System.out.println("Execution time: " + (end-start));

		System.out.println("Better Merge Sort:");
		start = System.currentTimeMillis();
		BetterMergeSort(a, b, 0, n-1);
		end = System.currentTimeMillis();
		for(int i=0;i<a.length;i++)
			if(a[i] != i+1) {
				System.out.println("Fails>>>>>>>");
				break;
			}
		System.out.println("Execution time: " + (end-start));

		System.out.println("Even Better Merge Sort:");
		start = System.currentTimeMillis();
		int h = EvenBetterMergeSort(a, b, 0, n-1);
		end = System.currentTimeMillis();
		if(h%2 == 1) 
			a = b;
		for(int i=0;i<a.length;i++)
			if(a[i] != i+1) {
				System.out.println("Fails>>>>>>> " + i);
				break;
			}
		System.out.println("Execution time: " + (end-start));

	}
	
	/**
	 * Best merge sort ever available
	 * @param a
	 * @param b
	 * @param p
	 * @param r
	 * @return
	 */
	private static int EvenBetterMergeSort(int []a, int []b, int p, int r) {
		if(p < r) {
			int q = (p+r)/2;
			int h1 = EvenBetterMergeSort(a, b, p, q);
			int h2 = EvenBetterMergeSort(a, b, q+1, r);
			if(h1 != h2) {
				/*
				 * if h1 is even, then src is A and hence copy RHS from B to A
				 * if h1 is odd, then src is B and hence copy RHS from A to B 
				 */
				if(h1%2 == 0)
					for(int i=q+1;i<=r;i++)
						a[i] = b[i];
				else
					for(int i=q+1;i<=r;i++)
						b[i] = a[i];
			}
			if(h1%2 == 0)						// gets executed even if h1 != h2
				EvenBetterMerge(a, b, p, q, r);
			else
				EvenBetterMerge(b, a, p, q, r);
			return h1+1;
		}
		else
			return 0;
	}
	
	/**
	 * Merge function of the best merge sort (EvenBetterMergeSort)
	 * @param src
	 * @param dest
	 * @param p
	 * @param q
	 * @param r
	 */
	private static void EvenBetterMerge(int[] src, int[] dest, int p, int q, int r) {
		
		for(int i=p, j=q+1, k=p; k<=r; k++) {
			if(j>r || (i<=q && (src[i] < src[j])))
				dest[k] = src[i++];
			else
				dest[k] = src[j++];
		}
	}

	/**
	 * A better merge sort than the basic merge sort
	 * @param a
	 * @param b
	 * @param p
	 * @param r
	 */
	private static void BetterMergeSort(int []a, int b[],  int p, int r) {
		if(p < r) {
			BetterMergeSort(a, b, p, (p+r)/2);
			BetterMergeSort(a, b, (p+r)/2 + 1, r);
			BetterMerge(a, b, p, (p+r)/2, r);
		}
	}

	/**
	 * Merge function of BetterMergeSort
	 * @param a
	 * @param b
	 * @param p
	 * @param q
	 * @param r
	 */
	private static void BetterMerge(int[] a, int []b, int p, int q, int r) {
		for(int i=p;i<=r;i++)
			b[i] = a[i];
		for(int i=p, j=q+1, k=p;k<=r;k++)
			if(j>r || (i<=q && (b[i] < b[j])))
				a[k] = b[i++];
			else
				a[k] = b[j++];
					
	}

	/**
	 * Basic Merge Sort
	 * @param a
	 * @param p
	 * @param r
	 */
	private static void BasicMergeSort(int[] a, int p, int r) {
		
		if(p<r) {
			int q = (p+r)/2;
			BasicMergeSort(a, p, q);
			BasicMergeSort(a, q+1, r);
			BasicMerge(a, p, q, r);
		}
	}

	/**
	 * Merge function of basic merge sort
	 * @param a
	 * @param p
	 * @param q
	 * @param r
	 */
	private static void BasicMerge(int[] a, int p, int q, int r) {
		
		int L[] = new int[q-p+1];
		int R[] = new int[r-q];
		for(int i=0;i<L.length;i++)
			L[i] = a[p+i];
		for(int i=0;i<R.length;i++)
			R[i] = a[q+1+i];
		int i = 0, j = 0;
		for(int k=p;k<=r;k++)
			if(j>=R.length || (i<L.length && (L[i] < R[j])))
				a[k] = L[i++];
			else
				a[k] = R[j++];
	}
}
