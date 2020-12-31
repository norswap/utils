package norswap.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * Utilities to deal with arrays.
 */
public final class NArrays
{
    // ---------------------------------------------------------------------------------------------

    /**
     * Returns an array obtained by applying the function {@code f} to each item in {@code array}.
     *
     * <p>The {@code witness} is any array with the proper type (including a zero-sized one). This
     * is necessary to be able to generate a return value with the proper type, but this array not
     * be mutated in any way.
     */
    public static <T, R> R[] map (T[] array, R[] witness, Function<T, R> f)
    {
        R[] out = java.util.Arrays.copyOf(witness, array.length);
        for (int i = 0; i < array.length; ++i)
            out[i] = f.apply(array[i]);
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a copy of {@code array} whose size is the least power of two greater or equal
     * to {@code minSize}. Max admissible value for minSize is 2^30.
     */
    public static <T> T[] resizeBinaryPower (T[] array, int minSize)
    {
        int size = 1;
        while (size < minSize) size <<= 1;
        return java.util.Arrays.copyOf(array, size);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a copy of {@code array} whose size is the least power of two greater or equal
     * to {@code minSize}. Max admissible value for minSize is 2^30.
     */
    public static int[] resizeBinaryPower (int[] array, int minSize)
    {
        int size = 1;
        while (size < minSize) size <<= 1;
        return java.util.Arrays.copyOf(array, size);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a copy of {@code array} whose size is the least power of two greater or equal
     * to {@code minSize}. Max admissible value for minSize is 2^30.
     */
    public static long[] resizeBinaryPower (long[] array, int minSize)
    {
        int size = 1;
        while (size < minSize) size <<= 1;
        return java.util.Arrays.copyOf(array, size);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Easy way to create an array with type inference.
     * <p>
     * e.g. {@code strs = array("a", "b");} instead of {@code strs = new String[] { "a", "b" }}
     */
    @SafeVarargs
    public static <T> T[] array (T... items) {
        return items;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Packs the given array, by moving all null values to the end of the array.
     * @return The number of non-null items in the array.
     */
    public static <T> int pack (T[] array)
    {
        int j = 0;
        for (int i = 0; i < array.length ; ++i)
        {
            if (array[i++] == null) continue;
            array[j++] = array[i-1];
        }
        return j;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Packs the given array, by moving all null values to the end of the array.
     * @return {@code array}
     */
    public static <T> T[] packRet (T[] array) {
        pack(array);
        return array;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a packed copy of the given array, where all null values have been removed and
     * the array is exactly big enough to hold all the non-null values.
     */
    public static <T> T[] packed (T[] array)
    {
        T[] copy = array.clone();
        int len = pack(copy);
        return Arrays.copyOf(copy, len);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Return the index of {@code item} within {@code array}, or -1 if the array does not contain
     * the item. Null items are supported.
     */
    public static <T> int indexOf (T[] array, T item)
    {
        for (int i = 0; i < array.length; ++i)
            if (Objects.equals(array[i], item))
                return i;
        return -1;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns true if and only if the {@code array} contains {@code item}.
     * Null items are supported.
     */
    public static <T> boolean contains (T[] array, T item)
    {
        return indexOf(array, item) >= 0;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a copy of array with the items in {@code items} appended at the end.
     */
    public static <T> T[] append (T[] array, T... items)
    {
        T[] copy = Arrays.copyOf(array, array.length + items.length);
        System.arraycopy(items, 0, copy, array.length, items.length);
        return copy;
    }

    // ---------------------------------------------------------------------------------------------
}
