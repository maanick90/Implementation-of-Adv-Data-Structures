package com.utd.Project2;


/**
 * @author Maaniccka Sentil Manickam Poonkundran - Project-2 Part-2 
 * 		    (Type 1: Enumeration of Permutations and Combinations) 
 * 			Program that visits all permutations of n(not necessarily 
 * 			distinct) objects.
 * 			Reference: The program uses Prof Knuth's L algorithm 
 * 			from the book "The Art of Computer Programming"
 */

import java.util.Arrays;

public class mxm135730NonDistinct {
	
	private static int n;
	private static long noOfPermutations = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * get the input from stdin/console
		 */
		n=Integer.parseInt(args[0]);
		int v = Integer.parseInt(args[1]);
		int []a = new int[n+1];
		for(int i=1;i<a.length;i++)
			a[i] = Integer.parseInt(args[i+1]);
		
		/*
		 * validate the input
		 */
		if(n<0 || n>1000 || v<0) {
			System.out.println("Invalid input");
			System.exit(1);
		}
		/* 
		 * Sort the array
		 */
		Arrays.sort(a);
		/*
		 * Sentinel value at the start of the array such that 
		 * all the values in the array are greater than this value  
		 */
		a[0] = Integer.MIN_VALUE;
		
		long startPermutation = System.currentTimeMillis();
		PermuteNonDistinct(a,n,v);								// call the actual function
		long endPermutation = System.currentTimeMillis();
		System.out.println(noOfPermutations + " " + (endPermutation-startPermutation));
				

	}

	/**
	 * Method PermuteNonDistinct():Implementation of Knuth's Algorithm L Algorithm L: 
	 * Given a sequence of n elements a0, a1, …, an-1 , generate all permutations of the
	 * sequence in lexicographically correct order. 
	 * Precondition: The permutation has not been visited so far
	 * Postcondition: The permutation is visited now
	 * 
	 * @param a 
	 *				is the input array (integer array)
	 * @param n 
	 * 				is the size of the array (integer)
	 * @param verbose 
	 * 				is the parameter that defines the type of 
	 * 				visiting each permutation 
	 */

	private static void PermuteNonDistinct(int[] a, int n, int verbose) {
		
		int j,l;
		visit(a, n, verbose);		// Initially the Sorted arrays is printed as it's one of the permutation value 
		
		while(true) {
			/*
			 * Finding out the value of j, the smallest subscript such that all
			 * permutations A[1...j] are already visited
			 */
			for(j = n-1;a[j] >= a[j+1];j--) {
				if(a[j] < a[j+1])
					break;
			}
			/*
			 * Exiting when all permutations are visited
			 */
			if(j == 0)
				break;

			/*
			 * Find max L
			 */
			for(l=n;a[j] >= a[l];l--) {
				if(a[j] < a[l])
					break;
			}
			/*
			 * Exchange a[j] and a[l]
			 */
			int temp = a[l];
			a[l] = a[j];
			a[j] = temp;
			
			/*
			 * Reverse the array. Descending order to ascending order
			 */
			for (int first = j + 1, last = n; first < last; first++, last--) {
				temp = a[first];
				a[first] = a[last];
				a[last] = temp;
			}
			/* Finally, call visit function to print the permutation */
			visit(a, n, verbose);		
		}
	}
	
	/**
	 * Method visit(): visits a permutation/combination, and prints it, if the
	 * parameter "verbose" is a natural number. 
	 * Precondition: The permutation/combination has not been printed/counted so far 
	 * Postcondition: The permutation/combination is now counted/printed.
	 * 
	 * @param A 
	 * 				is the input array (integer array)
	 * @param n 
	 * 				is the size of the array (integer)
	 * @param verbose 
	 * 				is the parameter which toggles between printing the entire
	 *            permutations/combinations or just their count alone along
	 *            with the total time in milliseconds 
	 *            verbose = 0 => Prints total no. of permutations and the 
	 *            total time taken in milli seconds 
	 *            verbose > 0 => Prints each distinct permutation in one line 
	 *            of output along with the output when verbose = 0
	 */
	private static void visit(int A[], int n, int verbose)
	{
	   if(verbose > 0) {							// to satisfy the condition
	      for(int i = 1; i <= n; i++) {
	    	  if (A[i] > 0) System.out.print(A[i] + " ");
	      }
	      System.out.println();
	   }
	   ++noOfPermutations;							// calculate the total no of permutations
	}
}
