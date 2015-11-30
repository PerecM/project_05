/**
 *
 * @author Foothill College, Elena Aravina
 */

import cs1c.FHsort;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class MinHeapArrayMerger {

    private static final int IMPOSSIBLE_INT = -1;

    /**
     * Uses the minHeap technique to sort the various chunks with respect to each other
     * and writes the output to a file called result_using_min_heap.txt
     * @param memorySize
     * @param inputChunks
     * @param minHeap
     * @param outfile   result_using_min_heap.txt
     */
    static void mergeSortedArrays(final int memorySize, ArrayList<Integer []> inputChunks,
                                  HeapTuple[] minHeap, String outfile) {
        boolean printHeap = false;
        boolean heapEmpty = false;
        FileWriter fileWriter;
        String outMin;
        try {
            fileWriter = new FileWriter(outfile, false);
            int minHeapStarter = 0;
            // initial filling of heap structure
            for (int i = minHeapStarter; i < minHeap.length; i++) {
                minHeap[i] = new HeapTuple(getArrayFirstElementInd(inputChunks.get(i)), i);
                inputChunks.get(i)[0] = IMPOSSIBLE_INT;
            }

            orderHeap(minHeapStarter, minHeap);

            while (!heapEmpty) {
                // find out what array the head is from
                int headArrayInd = minHeap[minHeapStarter].getArrayIndex(); // minHeap[0] = head

                // "remove" head and write its data to file
                outMin = minHeap[minHeapStarter].getData() + ",";
//                outMin = remove(minHeapStarter).getData() + ",";
                fileWriter.append(outMin);

                // find what to insert in the heap
                int potentialMin = getArrayFirstElementInd(inputChunks.get(headArrayInd));
                if (potentialMin < 0) { // chunk is "empty", then "decrease" minHeap size
                    minHeapStarter++;
                    printHeap = true;
                    if (minHeapStarter == minHeap.length - 1) // minHeap is "empty"
                        heapEmpty = true;
                } else {
                    printHeap = false;
                    // insert in minHeap:
                    minHeap[minHeapStarter] = new HeapTuple(potentialMin, headArrayInd);
                    inputChunks.get(headArrayInd)[potentialMin] = IMPOSSIBLE_INT;

                    // order the heap to make it ready to the next iteration
                    orderHeap(minHeapStarter, minHeap);
                }

                if (printHeap) {
                    String heapOut = "";
                    for (int i = 0; i < minHeap.length; i++) {
                        heapOut += minHeap[i].getData();
                    }
                    System.out.println(heapOut);
                }
            }

            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Couldn't create an output file or write in.");
        }
    }


    private static void orderHeap(int pointer, HeapTuple[] heapArray) {
        HeapTuple[] tempHeap = new HeapTuple[heapArray.length];
        HeapTuple tempMin = new HeapTuple(heapArray[pointer].getData(),
                heapArray[pointer].getArrayIndex());
        int minInd = pointer;
        // find min element
        for (int i = pointer + 1; i < heapArray.length; i++) {

            if (heapArray[i].getData() < tempMin.getData()) {
                tempMin = new HeapTuple(heapArray[i].getData(), heapArray[i].getArrayIndex());
                minInd = i;
            }
        }
        tempHeap[pointer] = tempMin;
        int insertInd = ++pointer;
        for (int k = pointer; k < heapArray.length; k++) {
            if (k != minInd) {
                tempHeap[insertInd] = new HeapTuple(heapArray[k].getData(),
                        heapArray[k].getArrayIndex());
                insertInd++;
            }
        }
        for (int h = pointer; h < heapArray.length; h++) {
            heapArray[h] = tempHeap[h];
        }
    }

    /**
     *
     * @param chunk the working chunk
     * @return  the index of the first "undeleted" element, i.e. the min element of chunk
     */
    public static int getArrayFirstElementInd(Integer[] chunk) {
        for (int i = 0; i < chunk.length; i++) {
            if (chunk[i] != IMPOSSIBLE_INT) {
                return i;
            }
        }
        return IMPOSSIBLE_INT;
    }

}
