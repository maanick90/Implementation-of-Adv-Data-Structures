package com.utd.Project2;

/**
 * @author Maaniccka Sentil Manickam Poonkundran - Project-2 Part-1 
 * 		   Type 1: Enumeration of Permutations and Combinations
 * 		   Program that visits all permutations/combinations of k 
 * 		   objects from a set of n distinct objects, numbered from 1...n.
 */

public class mxm135730Distinct {

	private static int n;
	private static long count=0;
	private static Boolean flagArray[];
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * get the input from stdin/console
		 */
		n=Integer.parseInt(args[0]);
		int k = Integer.parseInt(args[1]);
		int v = Integer.parseInt(args[2]);
		int A[] = new int[n];
		flagArray = new Boolean[n];
		
		/*
		 * validate the input
		 */
		if(n<0 || k<0 || v<0 || v>4) {
			System.out.println("Invalid input");
			System.exit(1);
		}
		
		for(int i=0;i<n;i++)				// Initialize the arrays
			A[i] = 0;
		for(int i=0;i<flagArray.length;i++)
			flagArray[i] = false;
					
		if(v==0||v==2) {
			/*
			 * call the Permutation function
			 */
			double startPermutationTime = System.currentTimeMillis();
			Permute(A,n,k,v);
			double endPermutationTime = System.currentTimeMillis();
			int totalTime = (int) Math.round(endPermutationTime-startPermutationTime);
			System.out.println(count +" " +totalTime);
		}
		
		if(v==1||v==3) {
			/*
			 * Call the Combination function
			 */
			double startCombinationTime = System.currentTimeMillis();
			combine(A, n, k, v);
			double endCombinationTime = System.currentTimeMillis();
			int totalTime = (int) Math.round(endCombinationTime-startCombinationTime);
			System.out.println(count +" " +totalTime);
		}
	}
	
	/**
	 * Method Permute(): Generates permutation of k objects from n distinct objects
	 * Precondition: The permutation has not been visited so far.
	 * Postcondition: The permutation is now visited.
	 * @param A
	 *            is the input array (integer array)
	 * @param i
	 *            index at which the number is to be placed
	 * @param k
	 *            length of the permutation to be generated
	 * @param verbose
	 * 			  to toggle between the method of visiting
	 */
	
	private static void Permute(int a[], int i, int k, int verbose) {
		/*
		 * when k=0, then a possible permutation is generated and the 
		 * visit function is called
		 */
		if(k == 0)
			visit(a, n, verbose);
		else {
			for(int j=n;j>0;j--) {
				if(flagArray[j-1] == false) {			// check if the value is already present in the array
					a[n-k] = j;							// set the value in the permutation array
					flagArray[j-1] = true;				// set the flag
					Permute(a,i-1,k-1,verbose);				// call the recursive function			
					flagArray[j-1] = false;				// remove the flag
					a[n-k] = 0;							 
				}
			}
		}
	}

	/**
	 * Method combine(): Generates combination of k objects from n distinct objects
	 * Precondition: The combination has not been visited so far.
	 * Postcondition: The combination is now visited.
	 * @param A
	 *            is the input array (integer array)
	 * @param i
	 *            index at which the number is to be placed
	 * @param k
	 *            length of the combination to be generated
	 * @param verbose
	 * 			  to toggle between the method of visiting
	 */

	private static void combine(int a[], int i, int k, int verbose) {
		
		if(k == 0)
			visit(a, n, verbose);
		else if(i < k)
			return;
		else {
			a[i-1] = i;							// recursive call with i as i-1 and k as k-1 as one element is chosen
			combine(a, i-1, k-1, verbose);
			a[i-1] = 0;							// recursive call with i as i-1 and k as k as the element is not chosen
			combine(a, i-1, k, verbose);
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
	 *            verbose = 0 => Prints no. of permutations of k objects out 
	 *            of n distinct objects and the total time taken in ms 
	 *            verbose = 1 => Prints no. of combinations of k objects out 
	 *            of n distinct objects and the total time taken in ms 
	 *            verbose = 2 => Prints actual permutations one line at a time 
	 *            and then output of v=0 
	 *            verbose = 3 => Prints actual combinations one line at a time 
	 *            and then output of v=1
	 */
	private static void visit(int A[], int n, int verbose)
	{
	   if(verbose > 1) {
	      for(int i = 0; i < n; i++) {
	    	  if (A[i] > 0) System.out.print(A[i] + " ");
	      }
	      System.out.println();
	   }
	   ++count;
	}
	
}
