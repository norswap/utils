package norswap.utils;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Utilities to deal with arrays.
 */
public final class NArrays
{
    // ---------------------------------------------------------------------------------------------

    /**
     * Returns an array obtained by applying the function {@code f} to each item in {@code array}.
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
     * to {@code min_size}. Max admissible value for min_size is 2^30.
     */
    public static <T> T[] resize_binary_power (T[] array, int min_size)
    {
        int size = 1;
        while (size < min_size) size <<= 1;
        return java.util.Arrays.copyOf(array, size);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a copy of {@code array} whose size is the least power of two greater or equal
     * to {@code min_size}. Max admissible value for min_size is 2^30.
     */
    public static int[] resize_binary_power (int[] array, int min_size)
    {
        int size = 1;
        while (size < min_size) size <<= 1;
        return java.util.Arrays.copyOf(array, size);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a copy of {@code array} whose size is the least power of two greater or equal
     * to {@code min_size}. Max admissible value for min_size is 2^30.
     */
    public static long[] resize_binary_power (long[] array, int min_size)
    {
        int size = 1;
        while (size < min_size) size <<= 1;
        return java.util.Arrays.copyOf(array, size);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Easy way to create an array with type inference.
     * <p>
     * e.g. {@code strs = array("a", "b");} instead of {@code strs = new String[] { "a", "b" }}
     */
    @SafeVarargs
    public static <T> T[] array (T... items)
    {
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
    public static <T> T[] pack_ret (T[] array) {
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
}
