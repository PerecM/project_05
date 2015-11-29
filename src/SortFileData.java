/**
 * Reads multiple plain text files, which contain unsorted numbers.
 * 
 * Sorts the input files by dividing them into multiple chunks.
 * Note: Each chunk size is determined by the size of memory.
 * 
 * Sorting is done in two phases:
 * 
 * Phase 1. Each individual chunk is sorted.
 * 
 * Phase 2. Use two different sorting techniques to sort all chunks
 *          with respect to each other. 
 * 
 *          Algorithm A. Sort each array by merging.
 *          Note: Do not use any external tools to accomplish storing
 *          (i.e. saving) and sorting, such as those from the 
 *          Java collections and java.util package. 
 *          This includes use of ArrayList class, Arrays.sort, FHsort, etc.
 *          
 *          Algorithm B. Sort each array by using minHeap.
 *          Suggestion: Take advantage of the logic from our heap sorting algorithm
 *          we studied in modules.
 *          
 *          Store the result of each technique into an output file.
 *
 * @author Foothill College, Elena Aravina
 */

import cs1c.*;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SortFileData
{
	/**
	 * Restricting the size of available memory to simulate large
	 * input file(s) that do not fit in memory.
	 * The size of a chunk is determined by the size of the memory.
	 */
	private static int MEM_SIZE = 50;
    private static BufferedReader fileIn;

	private static final boolean ENABLE_DEBUG = false;
	private static final int OUTPUT_WIDTH = 70;
	private static final String OUTPUT_SEPARATOR = "----------------------------------------------------------------------";


	/**
	 * Display the contents of a chunk.
	 * @param chunk    a subset of the data as a chunk 
	 */
	public static void displayChunkContent(Integer [] chunk)
	{
		System.out.println(OUTPUT_SEPARATOR);

		String outStr = "";
		for (int elem : chunk)
		{
			outStr += elem + ",";            
			if (outStr.length() > OUTPUT_WIDTH)
			{
				System.out.println(outStr);
				outStr = "";
			}
		}

		if (outStr != "")
			System.out.println(outStr);  // print out left left one

		System.out.println(OUTPUT_SEPARATOR);	    
	}


	/**
	 * Display the chunk number and contents.
	 * @param chunk    a subset of the data as a chunk 
	 * @param index    the position of the data with respect to original
	 */
	public static void displayFileChunk(Integer [] chunk, int index)
	{
		System.out.println("file chunk[" + index + "] with size " + chunk.length + " :");
		displayChunkContent(chunk);
	}
	
	
	/**
	 * For debugging and displaying results.
	 * Outputs the array of Integer objects.
	 * @param array1    a subset of the data sorted 
	 * @param index1    the position of the data with respect to original
	 * @param array2    a subset of the data sorted 
	 * @param index2    the position of the data with respect to original
	 */
    public static void displaySortedChunks(
    		Integer [] array1, int index1, 
            Integer [] array2, int index2)	
    {
        System.out.println("sort file chunk[" + index1 + "] :");
        displayChunkContent(array1);


        System.out.println("\nsort file chunk[" + index2 + "] :");
        displayChunkContent(array2);


		System.out.println("");
	}


	/**
	 * For debugging and displaying results.
	 * Used to output a sample number of chunks. 
	 */
	protected static void displaySampleChunks(ArrayList<Integer[]> fileChunksAsArrays, int numOfChunks)
	{		
        int numOfFileChunks = fileChunksAsArrays.size();
//        System.out.println(numOfFileChunks);

        int allElementsNum = 0;

        for (int i = 0; i < numOfChunks;)
        {
        	// check if requested number of chunks to display is valid	  
            if (i < numOfFileChunks)
            {
                System.out.println();
                System.out.println("Phase 1 : Sorted file chunks " + i + " and " + (i+1) + ":");
                displaySortedChunks(fileChunksAsArrays.get(i), i, fileChunksAsArrays.get(i + 1), (i + 1));
//                System.out.println("Chunk 1 size: " + fileChunksAsArrays.get(i).length +
//                        ". Chunk 2 size: " + fileChunksAsArrays.get(i+1).length);
                allElementsNum += fileChunksAsArrays.get(i).length + fileChunksAsArrays.get(i + 1).length;

                i += 2;
            }       
        } // for all the chunks up to the requested number
    }


    /**
     * Sorts each individual chunk using shell sort.
     * @param array     the chunk to sort
     */
    static void sortChunk(Integer[] array) {
        FHsort.shellSort1(array);
    }

    /**
     * Reads the plain text file and creates a list of chunks of data.
     * @param filePath      file path
     * @param chunkList     the list where all chunks will be stored
     * @throws Exception    if the file is not found
     */
    protected static void readFileIntoIntArrays(String filePath, ArrayList<Integer[]> chunkList)
    throws Exception {
        // reading file
        try {
            fileIn = new BufferedReader(new FileReader(filePath));
            // read the only line of numbers and transform it into the array of strings
            String numbersLine = fileIn.readLine();
            fileIn.close();
            String [] tokens = numbersLine.split(",");

            // how many chunks are we going to have
            int chunksNum;
            if (tokens.length % MEM_SIZE == 0) {
                chunksNum = tokens.length / MEM_SIZE;
            } else {
                chunksNum = tokens.length / MEM_SIZE + 1;
            }

            // forming the chunks and adding to chunkList
            for (int i = 0; i < chunksNum; i++) {
                int memLimit;
                // the last chunk - if it's going to be less than default memory limit
                if (i == chunksNum - 1 && tokens.length % MEM_SIZE != 0) {
                    memLimit = tokens.length - i * MEM_SIZE;
                } else {
                    memLimit = MEM_SIZE;
                }

                Integer[] thisChunk = new Integer[memLimit];
                for (int j = 0; j < memLimit; j++) {
                    thisChunk[j] = Integer.parseInt(tokens[j + MEM_SIZE * i]);
                }
                chunkList.add(thisChunk);
            }
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND!");
        }
    }

	public static void main(String[] args) throws Exception
	{
		final String filePath = "resources/";	// Directory path for Mac OS X
		//final String filePath = "resources\\";	// Directory path for Windows OS (i.e. Operating System)

		// Sample input files in Comma-Seperated-Value (CSV) format
		final String [] fileNames = {"numbers01.txt", "numbers02.txt", "numbers03.txt", "numbers04.txt"};

		ArrayList<Integer[]> fileChunksAsArrays = new ArrayList<Integer[]>();

		for (String fname : fileNames)
		{
			// Reads the file and divides the file into chunk(s), which are represented
			// by an array of Integers of length MEM_SIZE
			// Adds the chunk(s) to the list of chunks called fileChunksAsArrays
			// Suggestion: Use Arrays.copyOfRange(int[] original, int from, int to)
			// to copy a chunk found into fileChunksAsArrays
			// for more details see:
			// http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#copyOfRange(int[],%20int,%20int)
			readFileIntoIntArrays(filePath + fname, fileChunksAsArrays);
		}


		// Phase 1. Sort each individual chunk ---------------------------------------
		//
		// Note: the total size of all chunks should be the same as the total number
		// of values in each file divided by the memory size.
		int numOfChunks = fileChunksAsArrays.size();
		System.out.println("Number of arrays holding file input = " + numOfChunks);

        int chunkIndex = 0;
		for (Integer[] chunk : fileChunksAsArrays)
		{
			if (ENABLE_DEBUG)
			{
				displayFileChunk(chunk, chunkIndex);
            	chunkIndex++;
			}

			// Sorts each individual chunk.
			// The sorted result is stored in the argument "chunk".
			sortChunk(chunk);
		}

		// Display the result of various chunks after sorting.
		displaySampleChunks(fileChunksAsArrays, numOfChunks);


		// Phase 2. Use two different sorting techniques to sort all chunks ---------- 
		// 
		long startTime, estimatedTime;


		// Algorithm A. Use the merging technique we learned in modules to sort 
		// the various chunks with respect to each other and write the output to
		// a file called "result_using_merge.txt"
		// Note: The merge technique is a part of the merge sort algorithm. 
		//        In class BasicArrayMerger,
		//        we are *not* explicitly calling FHsort.mergeSort
		// 
		// Use the array of integer primitives called "potentialMinimums"
		// to hold the current minimums at each iteration of the sorting.
		// Suggestion: after finding the set of minimums in the current pass write
		//             the result to the output file.
		// In your RUN_merge.txt file show a sample number of iterations.
		// For example, the minimums found after the first pass, the 10th pass, etc.		
		int [] potentialMinimums = new int[fileChunksAsArrays.size()];

		// capture start time
		startTime = System.nanoTime();

		// Algorithm A
		BasicArrayMerger.mergeSortedArrays(MEM_SIZE, fileChunksAsArrays,
				potentialMinimums, filePath + "result_using_merge.txt");

		// stop and calculate elapsed time
		estimatedTime = System.nanoTime() - startTime;

		// report algorithm time
		System.out.println("\nAlgorithm A Elapsed Time: "
				+ TimeConverter.convertTimeToString(estimatedTime) + "\n");


//		// Algorithm B. Use the minHeap technique we learned in modules to sort
//		// the various chunks with respect to each other and write the output to
//		// a file called "result_using_min_heap.txt"
//		// Note: In class MinHeapArrayMerger, we are *not* explicitly calling FHsort.heapSort.
//		//
//		// Use the array of HeapTuple objects called "minHeap" to hold the current minimums.
//		// In your RUN_min_heap.txt file show a sample number of iterations.
//		HeapTuple[] minHeap = new HeapTuple[fileChunksAsArrays.size()];
//
//		// capture start time
//		startTime = System.nanoTime();
//
//		// Algorithm B
//		MinHeapArrayMerger.mergeSortedArrays(MEM_SIZE, fileChunksAsArrays,
//				minHeap, filePath + "result_using_min_heap.txt");
//
//
//		// stop and calculate elapsed time
//		estimatedTime = System.nanoTime() - startTime;
//
//		// report algorithm time
//		System.out.println("\nAlgorithm B Elapsed Time: "
//				+ TimeConverter.convertTimeToString(estimatedTime) + "\n");
	}
}
