package projectDAA;

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class HybridSort {

    // reads input, performs hybrid sort, and prints output
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter the number of elements: ");
//        int n = scanner.nextInt();
//
//        //  non-positive array size
//        if (n <= 0) {
//            System.out.println("No elements to sort.");
//            scanner.close();
//            return;
//        }
//
//        int[] arr = new int[n];
//        System.out.println("Enter the elements:");
//        for (int i = 0; i < n; i++) {
//            arr[i] = scanner.nextInt();
//        }
//
//        // Calculate recursion depth threshold (log2(n))
//        // If recursion depth exceeds this, we switch to MergeSort 
//        int depthLimit = (int) (Math.log(n) / Math.log(2));  // floor(log2(n))
//
//        // Perform the hybrid QuickSort/MergeSort
//        hybridQuickMergeSort(arr, 0, n - 1, depthLimit);
//
//        // Output the sorted array
//        System.out.println("Sorted array:");
//        for (int num : arr) {
//            System.out.print(num + " ");
//        }
//        System.out.println();
//        scanner.close();
//        
        testcase();
    }

    //swap function for quick sort
    static void swap(int arr[],int a,int b)
	{
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
    
    /*
      Hybrid QuickSort + MergeSort function.
      Uses QuickSort (partitioning) until a recursion depth limit, 
      then switches to MergeSort to avoid QuickSort's worst-case performance.
     */
    private static void hybridQuickMergeSort(int[] arr, int low, int high, int depthLimit) {
        // Base case: single element (or invalid range) means sorted
        if (low >= high) {
            return;
        }
        // If depth limit reached, use MergeSort for this segment
        if (depthLimit <= 0) {
            mergeSort(arr, low, high);
            return;
        }

        // QuickSort partition for this segment
        int pivotIndex = partition(arr, low, high);

        // Recursively sort left and right partitions with decremented depth limit
        hybridQuickMergeSort(arr, low, pivotIndex - 1, depthLimit - 1);
        hybridQuickMergeSort(arr, pivotIndex + 1, high, depthLimit - 1);
    }

    /*
      Partition function for QuickSort.
      Chooses the first element as pivot and partitions the array such that
      elements <= pivot are on left, > pivot on right.
     */
    private static int partition(int[] arr, int low, int high) {
        
        // Choose the pivot
        int pivot = arr[high];
        
        // Index of smaller element and indicates 
        // the right position of pivot found so far
        int i = low - 1;

        // Traverse arr[low..high] and move all smaller
        // elements to the left side. Elements from low to 
        // i are smaller after every iteration
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        // Move pivot after smaller elements and
        // return its position
        swap(arr, i + 1, high);  
        return i + 1;
    }
//    private static int partition(int[] arr, int low, int high) {
//    	int pivot = arr[low];  //set pivot to first element
//		int i = low+1;
//		int j = high;
//		
//		while(i<=j)
//		{
//			while(i<=high && arr[i]<pivot)
//				i++;
//			while(j>=low && arr[j]>pivot)
//				j--;
//			if(i<j)
//			swap(arr,i,j);
//		}
//		swap(arr,low,j);
//		return j;
//    }
    
    private static void quickSort(int[] arr,int low,int high) 
    {
    	if(low < high)
    	{
    		int pi = partition(arr,low,high);
    		quickSort(arr,low,pi-1);
    		quickSort(arr,pi+1,high);
    	}
    }

    /*
      MergeSort function.
      Recursively divides the array in halves and merges sorted halves.
     */
    private static void mergeSort(int[] arr, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            // Sort first and second halves
            mergeSort(arr, low, mid);
            mergeSort(arr, mid + 1, high);
            // Merge the sorted halves
            merge(arr, low, mid, high);
        }
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;

        // Create temp arrays
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i) {
            leftArr[i] = arr[low + i];
        }
        for (int j = 0; j < n2; ++j) {
            rightArr[j] = arr[mid + 1 + j];
        }

        // Merge the temp arrays back into arr[low..high]
        int i = 0, j = 0;
        int k = low;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        // Copy any remaining elements of leftArr
        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        // Copy any remaining elements of rightArr
        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }
    
    /*
     This is just a method used to test and compare
      the time taken by this sort. 
      */
    private static void testcase()
    {
    	int n[] = {10,50,100,500,1000,2000,4000,5000,7500,10000};
    	int arr[];
    	int size;
    	long start,end;
    	Random rand = new Random();
    	int depthLimit;
    	
    	System.out.println("Time taken");
    	System.out.println("Size of Array \t Hybrid Sort \t Quick Sort \t Merge Sort ");
    	for(int i=0;i<10;i++)
    	{
    		size = n[i];
    		depthLimit = (int) (Math.log(size) / Math.log(2));  // floor(log2(n))
    		arr = new int[size];
    		
    		for(int j=0;j<size;j++)
    			arr[j] = rand.nextInt(100000);
    		Arrays.sort(arr);
    		//copy of the array
    		int copy1[] = Arrays.copyOf(arr,arr.length);
    		int copy2[] = Arrays.copyOf(arr,arr.length);
    		
    		
    		
    		//print details
    		System.out.print(size+"\t\t");
    		
    		//Hybridsort
    		start = System.nanoTime();
    		hybridQuickMergeSort(arr,0,size-1,depthLimit);
    		end = System.nanoTime();
    		System.out.print(((end-start)/1000000.0)+"\t\t");
    		
    		//quicksort
    		start = System.nanoTime();
    		quickSort(copy1,0,size-1);
    		end = System.nanoTime();
    		System.out.print(((end-start)/1000000.0)+"\t\t");
    		
    		//mergesort
    		start = System.nanoTime();
    		mergeSort(copy2,0,size-1);
    		end = System.nanoTime();
    		System.out.println(((end-start)/1000000.0));
    		
    	}
    }
}
