import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;

public class BasicArrayMerger {
    private static final int IMPOSSIBLE_INT = -1;
    private static final int IMPOSSIBLE_IND = 100;


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
        boolean mergeFinished = false;
        int mergedElementsNum = 0;
        int potMinSize = potentialMinimums.length;
        try {
            fileWriter = new FileWriter(outfile, true);
            for (int i = 0; i < memorySize; i++) { // from 0 to 50

                for (int f = 0; f < potentialMinimums.length; f++) {// filling the mins array
                    int currentMinInd = 0;
                    int currentMin = inputChunks.get(0)[currentMinInd]; //getArrayFirstElementInd(inputChunks.get(0));
                    int currentMinHolderInd = 0;

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
                    inputChunks.get(currentMinHolderInd)[currentMinInd] = IMPOSSIBLE_INT;

                    if (currentMin == IMPOSSIBLE_INT) {
                        mergeFinished = true;
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
                String potentialMinsSubstring = potentialMinsString.substring(1, potentialMinsString.length() - 1) + ", ";
                System.out.println(potentialMinsString);
                fileWriter.append(potentialMinsSubstring);
                mergedElementsNum += potentialMinimums.length;

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

    protected static int getArrayFirstElementInd(Integer[] chunk) {
        for (int i = 0; i < chunk.length; i++) {
            if (chunk[i] != IMPOSSIBLE_INT) {
                return i;
            }
        }
        return IMPOSSIBLE_INT;
    }

}
