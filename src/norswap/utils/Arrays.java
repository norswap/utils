package norswap.utils;

import java.util.function.Function;

/**
 * Utilities to deal with arrays.
 */
public final class Arrays
{
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
}
