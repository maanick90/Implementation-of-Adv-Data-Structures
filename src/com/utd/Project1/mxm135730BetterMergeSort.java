package com.utd.Project1;

import java.io.*;

/**
 * @author Maaniccka Sentil Manickam Poonkundran
 * A better merge sort than the basic merge sort
 */

public class mxm135730BetterMergeSort {

	public static int[] B;

	/**
	 * Merge Sort function 
	 * @param A
	 * @param p
	 * @param r
	 */
	static void MergeSort(int[] A, int p, int r) {
		if (p < r) {
			if (r - p > 11) {
				int q = (p + r) / 2;
				MergeSort(A, p, q);							// Divide part
				MergeSort(A, q + 1, r);						// Divide part
				Merge(A, B, p, q, r);						// Function for the 'Conquer' part
			} else { 										// Insertion sort
				for (int i = p, j = i; i < r; j = ++i) {
					int ai = A[i + 1];
					while (ai < A[j]) {
						A[j + 1] = A[j];
						if (j-- == p) {
							break;
						}
					}
					A[j + 1] = ai;
				}
			}
		}
	}

	/**
	 * Merge part of the MergeSort function
	 * @param A
	 * @param B
	 * @param p
	 * @param q
	 * @param r
	 */
	static void Merge(int[] A, int[] B, int p, int q, int r) {
		for (int i = p; i <= r; i++)
			B[i] = A[i];
		int i = p;
		int j = q + 1;
		for (int k = p; k <= r; k++) {
			if ((j > r) || ((i <= q) && (B[i] <= B[j])))
				A[k] = B[i++];
			else
				A[k] = B[j++];
		}
		return;
	}

	/**
	 * main function 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		 int n = Integer.parseInt(args[0]);
		int[] A = new int[n];
		B = new int[n];
		for (int i = 0; i < n; i++) {
			A[i] = n - i;
		}

		long start = System.currentTimeMillis();					// start time
		MergeSort(A, 0, n - 1);
		long last = System.currentTimeMillis();						// end time

		for (int j = 0; j < A.length - 1; j++) {
			if (A[j] > A[j + 1]) {
				System.out.println("Sorting failed :-(");
				return;
			}
		}

		System.out.println("Success!");
		System.out.println("Time taken for method 2 = " + (last - start));
	}
}