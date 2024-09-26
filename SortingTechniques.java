package sorting;

import java.util.Random;

public class SortingTechniques {

		private static final Random rand = new Random();
	    //Method for InsertionSort
	    public static void insertionSort(int[] arr) {
	        for (int i = 1; i < arr.length; i++) {
	            int key = arr[i];
	            int j = i - 1;

	            // Move elements greater than key one position ahead
	            while (j >= 0 && arr[j] > key) {
	                arr[j + 1] = arr[j];
	                j--;
	            }
	            arr[j + 1] = key;
	        }
	    }

	    //Method for MergeSort
	    public static void mergeSort(int[] arr, int low, int high) {
	        if (low < high) {
	            int mid = (low + high) / 2;
	            mergeSort(arr, low, mid);
	            mergeSort(arr, mid + 1, high);
	            merge(arr, low, mid, high);
	        }
	    }
	    // Merge step of the merge sort algorithm for sorted arrays
	    private static void merge(int[] arr, int low, int mid, int high) {

	        //calculate the sizes of subarrays to be merged
	        int m1 = mid - low + 1;
	        int m2 = high - mid;
	//initialize left and right subarray
	        int[] left = new int[m1];
	        int[] right = new int[m2];

	        // Copy data to left and right arrays
	        try {
	            System.arraycopy(arr, low, left, 0, m1);
	            System.arraycopy(arr, mid + 1, right, 0, m2);
	        } catch (ArrayIndexOutOfBoundsException exe) {
	            // Handle exception appropriately
	            System.err.println("out of bound index: "+ exe.getMessage());
	        }
	        int i = 0, j = 0, key = low;
	        // Merge the left and right arrays back into arr[low..high]
	        while (i < m1 && j < m2)
	        {
	            if (left[i] <= right[j])
	            {
	                arr[key] = left[i];
	                i++;
	            }
	            else
	            {
	                arr[key] = right[j];
	                j++;
	            }
	            key++;
	        }
	        //copying remaining elements of left array in to input
	        while (i < m1)
	        {
	            arr[key] = left[i];
	            i++;
	            key++;
	        }
	        //copying remaining elements of right array in to input
	        while (j < m2)
	        {
	            arr[key] = right[j];
	            j++;
	            key++;
	        }
	    }



	    //method for heap sort
	    public static void heapSort(int[] arr) {
	        int n = arr.length;

	        // Build heap
	        for (int i = n / 2 - 1; i >= 0; i--) {
	            heapify(n, i,arr);
	        }

	        // Extract elements one by one
	        for (int i = n - 1; i >= 0; i--) {
	            int temp = arr[0];
	            arr[0] = arr[i];
	            arr[i] = temp;

	            heapify(i, 0,arr);
	        }
	    }
	    public static void heapify(int m, int i,int inputArray[])
	    {
	        int big = i;
	        int l = 2*i + 1;
	        int r = 2*i + 2;
	        // Check if left child is larger than root
	        if (l < m && inputArray[l] > inputArray[big])
	            big = l;
	        // Check if right child is larger than largest so far
	        if (r < m && inputArray[r] > inputArray[big])
	            big = r;
	        // If largest is not root
	        if (big != i)
	        {
	            int swap = inputArray[i];
	            inputArray[i] = inputArray[big];
	            inputArray[big] = swap;
	            // Recursively heapify the affected sub-tree
	            heapify( m, big,inputArray);
	        }
	    }

	    //method for inplace QickSort

	    //In-Place quick sort
	    static void inPlaceQuickSort( int leftelement, int rightelement, int inputArray[]) {
	        if(leftelement >= rightelement)
	            return;
	        final int randomInt = rightelement - leftelement + 1;
	        //adds the random index to the leftelement to get pivot index.
	        int pivot = rand.nextInt(randomInt) + leftelement;
	        //method returns the new pivot element after partitioning
	        int newPivot = inPlacePartition(leftelement, rightelement, pivot,inputArray);
	        //recursive call for subarray left of the pivot
	        inPlaceQuickSort(leftelement, newPivot-1,inputArray);
	        //recursive call for subarray right of the pivot
	        inPlaceQuickSort(newPivot+1, rightelement,inputArray);
	    }
	    private static int inPlacePartition(int leftelement, int rightelement, int pivot,int
	            inputArray[]) {
	        int pivotTemp = inputArray[pivot];
	        swap( inputArray,pivot, rightelement);
	        int temp = leftelement;
	        for(int i = leftelement; i <= (rightelement - 1); i++) {
	            if(inputArray[i] < pivotTemp) {
	                swap( inputArray,i, temp);
	                temp++;
	            }
	        }
	        swap( inputArray,temp, rightelement);
	        return temp;
	    }



	    //Modified Quicksort using median
	    //quick sort for larger subarrays and insertion sort for smaller subarrays.
	    public static void modifiedQuickSort(int[] inputarr, int leftelement, int rightelement) {
	        if(leftelement + 8 <= rightelement) {
	            //using median element as pivot
	            int pivot = getMedian(inputarr, leftelement, rightelement);
	            //partition the array arround pivot to get sub arrays
	            int partitionIndex = partition(inputarr, leftelement, rightelement, pivot);
	            modifiedQuickSort(inputarr, leftelement, partitionIndex-1);
	            modifiedQuickSort(inputarr, partitionIndex+1, rightelement);
	        }else {
	            //Insertion sort is called for small arrays
	            insertionSort(inputarr);
	        }
	    }

	    private static int partition(int[] arr, int leftelement, int rightelement, int pivot) {
	        int i = leftelement, j = rightelement - 1;
	        while(true) {
	            while(arr[++i] < pivot)
	                ;
	            while(pivot < arr[--j])
	                ;
	            if(i >= j)
	                break;
	            else
	                swap(arr, i, j);
	        }
	        swap(arr, i, rightelement-1);
	        return i;
	    }
	    private static int getMedian(int[] arr, int leftelement, int rightelement) {
	        int center = (leftelement+rightelement)/2;

	        if(arr[center] < arr[leftelement])
	            swap(arr, center, leftelement);
	        if(arr[rightelement] < arr[leftelement])
	            swap(arr, rightelement, leftelement);
	        if(arr[rightelement] < arr[center])
	            swap(arr, rightelement, center);

	        swap(arr, center, rightelement-1);

	        return arr[rightelement-1];
	    }

	    private static void swap(int[] arr, int a, int b) {
	        int temp = arr[a];
	        arr[a] = arr[b];
	        arr[b] = temp;
	    }
}