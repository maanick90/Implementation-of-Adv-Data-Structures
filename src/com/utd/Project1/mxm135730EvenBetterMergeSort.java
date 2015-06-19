package com.utd.Project1;

import java.io.*;

/**
 * @author Maaniccka Sentil Manickam Poonkundran
 * Best merge sort ever available
 */

public class mxm135730EvenBetterMergeSort {
	
	public static int[] B; 
	
	/**
	 * Merge Sort function of EvenBetterMergeSort 
	 * @param A
	 * @param p
	 * @param r
	 * @return
	 */
    static int MergeSort(int[] A, int p, int r) {
        if (p < r) {
		    if (r-p > 11) {
	            int q = (p+r)/2;
	            int h1 = MergeSort(A, p, q);
	            MergeSort(A, q+1, r);
	           if(h1%2 == 1)					// even though 'n' is not power of 2, and if h1 is odd, then src->B and Dest->A
	            	Merge(B, A, p, q, r);
	            else
	            	Merge(A, B, p, q, r);
	            return h1+1;
	        } 
		    else {  									// Insertion sort
	        	for(int i=p, j=i; i<r; j=++i) {
				    int ai = A[i+1];
				    while(ai < A[j]) {
				    	A[j+1] = A[j];
						if (j-- == p) {
						    break;
						}
				    }
				    A[j+1] = ai;
	        	}
	        	return 0;
		    }
		}
        else
        	return 0;
    }

    /**
     * Merge function 
     * @param src
     * @param dest
     * @param p
     * @param q
     * @param r
     */
    static void Merge(int[] src, int[] dest, int p, int q, int r) {
	    int i = p; int j = q+1;
	    for(int k=p; k<=r; k++) {
	    	if ((j>r) || ((i<=q) && (src[i] <= src[j])))
	    		dest[k] = src[i++];
	    	else
	    		dest[k] = src[j++];
		}
        return;
    }

    /**
     * main function 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
    	int n = Integer.parseInt(args[0]);
        int[] A = new int[n];
        B = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = n-i;
        }

	    long start = System.currentTimeMillis();				// start time
	    int value = MergeSort(A, 0, n-1);
		long last = System.currentTimeMillis();					// end time
		
		if(value % 2 != 0)
			A = B;
		
		for (int j = 0; j < A.length-1; j++) {
		    if(A[j] > A[j+1]) {
		    	System.out.println("Sorting failed :-(");
		    	return;
		    }
		}
		
		System.out.println("Success!");
		System.out.println("Time taken for method 3 (handled not powers of 2) = " + (last-start));
    }
}