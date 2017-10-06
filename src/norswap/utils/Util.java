package norswap.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static norswap.utils.Strings.append;
import static norswap.utils.Vanilla.pop;

/**
 * Miscellaneous utility functions.
 */
public final class Util
{
    // ---------------------------------------------------------------------------------------------

    /**
     * Casts the object to type T, which may be inferred.
     */
    public static <T> T cast (Object obj)
    {
        // noinspection unchecked
        return (T) obj;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a string with the file contents, or null if the file could not be read.
     */
    public static String slurp (String file)
    {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        }
        catch (IOException e) {
            return null;
        }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Prints the message on the standard error and exits with error code 1.
     */
    public static void abort (String message)
    {
        System.err.println(message);
        System.exit(1);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Attempt to run {@code supplier}, wrapping any exceptions it might throw in a
     * {@link RuntimeException}.
     */
    public static <T> T attempt (ThrowingSupplier<T> supplier)
    {
        try { return supplier.get(); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns to string representation of {@code list} that has the string representation of
     * each item on its own line.
     */
    public static String lines (Object[] array)
    {
        StringBuilder b = new StringBuilder(array.length * 8);
        for (Object it: array) append(b, it.toString(), "\n");
        if (array.length > 0) pop(b, 1); // final newline
        return b.toString();
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns to string representation of {@code list} that has the string representation of
     * each item on its own line.
     */
    public static String lines (List<?> list)
    {
        StringBuilder b = new StringBuilder(list.size() * 8);
        for (Object it: list) append(b, it.toString(), "\n");
        if (list.size() > 0) pop(b, 1); // final newline
        return b.toString();
    }

    // ---------------------------------------------------------------------------------------------
}

