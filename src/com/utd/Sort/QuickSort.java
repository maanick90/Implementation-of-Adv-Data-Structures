package com.utd.Sort;

/**
 * QuickSort with 2 pivots(p1 and p2): so there are 3 partitions in the array
 * and 3 recursive calls
 * 
 * choose p1 and p2 as p1<p2 and keep p1 at the start and p2 at the end iterate
 * thru the unexamined values in between the start and the end and keep it in
 * the following order a => [p1] [< p1] [(p1-p2)] [> p2] [p2] now swap p1 and p2
 * at appropriate positions and finally, a => [< p1] [p1] [(p1-p2)] [p2] [> p2]
 * return the 2 pivot positions as integer array
 */

public class QuickSort {

	/**
	 * Main function to run the program
	 * @param args
	 */
	public static void main(String[] args) {

		int n[] = { 8388608, 16777216, 33554432, 67108864, 134217728 };
		// int n[] = {9, 25, 56, 87};
		for (int i = 0; i < n.length; i++)
			runFn(n[i]);

	}

	/**
	 * Driver function to run the program
	 * @param n
	 */
	public static void runFn(int n) {

		int a[] = new int[n];
		runAllSorting(a, n);
	}

	/**
	 * Calls all QuickSorting algorithms
	 * @param a
	 * @param n
	 */
	public static void runAllSorting(int[] a, int n) {

		for (int i = 0; i < n; i++)
			a[i] = n - i;
		System.out.println("\n\n\nN = " + n + "\nRandomized Quick Sort:");
		long start = System.currentTimeMillis();
		RandomizedQuickSort(a, 0, n - 1);
		long end = System.currentTimeMillis();
		for (int i = 0; i < a.length; i++)
			if (a[i] != i + 1) {
				System.out.println("Fails>>>>>>>");
				break;
			}
		System.out.println("Execution time: " + (end - start));

		for (int i = 0; i < n; i++)
			a[i] = n - i;
		System.out.println("Quick Sort with 2 pivots:");
		start = System.currentTimeMillis();
		QuickSortWithDualPivots(a, 0, n - 1);
		end = System.currentTimeMillis();
		for (int i = 0; i < a.length; i++)
			if (a[i] != i + 1) {
				System.out.println("\nFails>>>>>>>");
				break;
			}
		System.out.println("Execution time: " + (end - start));
	}

	/**
	 * QuickSort with 2 pivots
	 * @param a
	 * @param p
	 * @param r
	 */
	private static void QuickSortWithDualPivots(int[] a, int p, int r) {
		if (p < r) {
			int q[] = DualPivotsPartition(a, p, r); // get 2 partition points =>
													// 3 partitions totally
			QuickSortWithDualPivots(a, p, q[0] - 1);
			QuickSortWithDualPivots(a, q[0] + 1, q[1] - 1);
			QuickSortWithDualPivots(a, q[1] + 1, r);
		}
	}

	/**
	 * Partition for quick sort with 2 pivots
	 * @param a
	 * @param p
	 * @param r
	 * @return
	 */
	private static int[] DualPivotsPartition(int[] a, int p, int r) {
		int p1, p2, i = p + 1, j = r - 1, k;
		/*
		 * choose p1 and p2 randomly from the array place it in the first index
		 * and last index make sure that a[p1] < a[p2]
		 */
		p1 = (int) (Math.random() * (r - p + 1)) + p;
		p2 = (int) (Math.random() * (r - p + 1)) + p;
		swap(a, p1, p);
		swap(a, p2, r);
		if (a[p] > a[r])
			swap(a, p, r);

		/*
		 * iterate from p+1 to r-1(not necessary to r-1) keep 3 pointers k ->
		 * iterator i -> ++ when swapping with values < p1 and do k++ j -> --
		 * when swapping with values > p2 but don't do k++ if (p1 < values < p2)
		 * -> only k++;
		 * 
		 * if(k > j), exit the loop because u would have already encountered the
		 * values to the right of k
		 */
		for (k = p + 1; k <= j;) {
			if (a[k] < a[p])
				swap(a, k++, i++);
			else if (a[k] > a[r])
				swap(a, k, j--);
			else
				++k;
		}
		/*
		 * i -> start of (p1-p2) or at one after (values<p1) j -> one before
		 * (values>p2) or end of (p1-p2)
		 */
		swap(a, --i, p);
		swap(a, ++j, r);
		int q[] = { i, j };
		return q;
	}

	/**
	 * Basic randomized quick sort
	 * @param a
	 * @param p
	 * @param r
	 */
	private static void RandomizedQuickSort(int[] a, int p, int r) {
		if (p < r) {
			int q = RandomizedPartition(a, p, r);
			RandomizedQuickSort(a, p, q - 1);
			RandomizedQuickSort(a, q + 1, r);
		}
	}

	/**
	 * Partition for basic randomized quick sort
	 * @param a
	 * @param p
	 * @param r
	 * @return
	 */
	private static int RandomizedPartition(int[] a, int p, int r) {

		int q = (int) (Math.random() * (r - p + 1)) + p;
		swap(a, q, r);
		int i = p;
		while (p < r) {
			if (a[p] < a[r])
				swap(a, i++, p);
			++p;
		}
		swap(a, i, r);
		return i;
	}

	/**
	 * Swap function to swap 2 values in the given array
	 * @param a
	 * @param i
	 * @param j
	 */
	private static void swap(int a[], int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}
