import java.util.ArrayList;

/**
 * Created by Elena Aravina on 11/28/15.
 * Provides basic test for Assignment Part I
 */
public class DivideTest {
    public static void main(String[] args) throws Exception{
        ArrayList<Integer[]> fileChunksArrays = new ArrayList<Integer[]>();
        SortFileData.readFileIntoIntArrays("resources/numbers01.txt", fileChunksArrays);
        SortFileData.displaySampleChunks(fileChunksArrays, 2);
    }
}
