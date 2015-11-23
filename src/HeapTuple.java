/**
 * Helper class for sorting input chunks via minHeap.
 */
public class HeapTuple
{
	/**
	 *  The number we read from a file
	 */
	private int data;
	
	/**
	 *  The index of the array that this data belongs to
	 */
	private int arrayIndex;
	
	/**
	 * Constructs a tuple to stores the value and the 
	 * array number it belongs to.
	 * @param data          the number
	 * @param arrayIndex    the index of the number
	 */
	public HeapTuple(int number, int index)
	{
		data = number;
		arrayIndex = index;
	}
	
	/**
	 * Accessor method returns the data.
	 * @return the data
	 */
	public int getData()
	{	return data;	}
	
	/**
	 * Accessor method returns which array the data is from.
	 * @return the array index
	 */
	public int getArrayIndex()
	{	return arrayIndex;	}
	
	/**
	 * String representation of the tuple.
	 * NOTE: For debugging purposes.
	 *       You may change the formatting of the String representation
	 *       as you see fit.
	 */
	public String toString()
	{	return "array #" + arrayIndex + "=" + data ;	}
}
