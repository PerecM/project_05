/**
 *
 * @author Foothill College, Elena Aravina
 */

import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;

public class BasicArrayMerger {
    private static final int IMPOSSIBLE_INT = -1;

    /**
     * Uses the merging technique to sort the various chunks with respect to each other
     * and writes the output to a file called result_using_merge.txt
     * @param memorySize
     * @param inputChunks
     * @param potentialMinimums     the array of integer primitives to hold the current
     *                              minimums at each iteration of the sorting.
     * @param outfile       result_using_merge.txt
     */
    protected static void mergeSortedArrays(final int memorySize, ArrayList<Integer []> inputChunks,
                                  int[] potentialMinimums, String outfile) {
        FileWriter fileWriter;
        // flag indicating that all chunks are "empty"
        boolean mergeFinished = false;
        int potMinSize = potentialMinimums.length;
        try {
            fileWriter = new FileWriter(outfile, false);
            for (int i = 0; i < memorySize; i++) { // from 0 to 50, as per memory size

                for (int f = 0; f < potentialMinimums.length; f++) {//filling the mins array
                    /*
                    holding current temp variables:
                    */
                    // index of first element in working array
                    int currentMinInd = 0;
                    // first (i.e. min) element in working array
                    int currentMin = inputChunks.get(0)[currentMinInd];
                    // index of working array in inputChunks
                    int currentMinHolderInd = 0;

                    // iterate over chunks to find min
                    for (int k = 1; k < inputChunks.size(); k++) {
                        try {
                            int potentialMinInd = getArrayFirstElementInd(inputChunks.get(k));
                            int potentialMin = inputChunks.get(k)[potentialMinInd];

                            if (currentMin == IMPOSSIBLE_INT || potentialMin < currentMin) {
                                currentMin = potentialMin;
                                currentMinInd = potentialMinInd;
                                currentMinHolderInd = k;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                    // make the element that went to potentialMinimums "deleted"
                    inputChunks.get(currentMinHolderInd)[currentMinInd] = IMPOSSIBLE_INT;

                    if (currentMin == IMPOSSIBLE_INT) { // means that all chunks are "empty"
                        mergeFinished = true;
                        // adjusting the last potentialMin to avoid zeros as last "elements"
                        for (int n = 0; n < potentialMinimums.length; n++) {
                            if (potentialMinimums[n] == 0) {
                                int[] tempMins = Arrays.copyOfRange(potentialMinimums, 0, n);
                                potentialMinimums = Arrays.copyOf(tempMins, n);
                            }
                        }
                        break;
                    }
                    potentialMinimums[f] = currentMin;
                }
                String potentialMinsString = Arrays.toString(potentialMinimums);
                String potentialMinsSubstring = potentialMinsString.substring(1, potentialMinsString.length() - 1) + ",";
                System.out.println(potentialMinsString);
                fileWriter.append(potentialMinsSubstring);

                if (mergeFinished) {
                    fileWriter.close();
                    break;
                }

                potentialMinimums = new int[potMinSize];
            }

        } catch (IOException e) {
            System.out.println("Couldn't create an output file or write in.");
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
