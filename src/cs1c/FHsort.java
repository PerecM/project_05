package cs1c;

public class FHsort {
    public static < E extends Comparable< ? super E > >
    void shellSort1(E[] a)
    {
        int gap = 1;
        int k, pos, arraySize;
        E tmp;

        arraySize = a.length;
        for (gap = arraySize/2;  gap > 0;  gap /= 2)
            for(pos = gap; pos < arraySize; pos++ )
            {
                tmp = a[pos];
                for(k = pos; k >= gap && tmp.compareTo(a[k-gap]) < 0; k -= gap )
                    a[k] = a[k-gap];
                a[k] = tmp;
            }
    }
}
