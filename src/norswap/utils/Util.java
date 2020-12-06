package norswap.utils;

import norswap.utils.exceptions.Exceptions;
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
     * Prints the message on the standard error and exits with error code 1.
     */
    public static void abort (String message)
    {
        System.err.println(message);
        System.exit(1);
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

    /**
     * A way to check assertions even when the -ea flag is not passed.
     *
     * <p>If the assertion fails, {@code format} and {@code args} will be passed to {@link
     * String#format(String, Object...)} to generate the thrown {@link AssertionError}.
     *
     * <p>This method itself will not appear in the stack trace.
     */
    public static void assertion(boolean condition, String format, Object... args)
            throws AssertionError
    {
        if (!condition)
            throw Exceptions.trim_stack_trace(1, new AssertionError(String.format(format, args)));
    }

    // ---------------------------------------------------------------------------------------------
}

