package norswap.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Utility methods related to exceptions.
 */
public final class Exceptions
{
    /**
     * Returns a string representation of the stack trace of the given throwable, as per {@link
     * Throwable#printStackTrace()}.
     */
    public static String string_stack_trace (Throwable t)
    {
        StringWriter trace = new StringWriter();
        PrintWriter w = new PrintWriter(trace);
        t.printStackTrace(w);
        w.close();
        return trace.toString();
    }

    /**
     * Trims the given stack trace, removing {@code peel} stack trace elements at the top of the
     * stack trace (the most recently called methods).
     */
    public static StackTraceElement[] trim_stack_trace (int peel, StackTraceElement[] trace) {
        return Arrays.copyOfRange(trace, peel, trace.length);
    }

    /**
     * Trims the stack trace of the given throwable, removing {@code peel} stack trace elements at
     * the top of the stack trace (the most recently called methods). Mutates the throwable and
     * returns it.
     */
    public static <T extends Throwable> T trim_stack_trace (int peel, T throwable) {
        throwable.setStackTrace(trim_stack_trace(peel, throwable.getStackTrace()));
        return throwable;
    }

    /**
     * Return the value returned by the given supplier, suppressing any {@link Throwable} it might
     * throw. To only suppress checked {@link Exception}, use {@link #suppress}.
     */
    public static <T> T suppress_all (ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable t) {
            throw new Error(
                    "Exception explicitly suppressed by programmer should not have been thrown", t);
        }
    }

    /**
     * Return the value returned by the given supplier, suppressing any checked {@link Exception} it
     * might throw. To suppress all {@link Throwable}, use {@link #suppress_all}.
     */
    public static <T> T suppress (ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception t) {
            throw new Error(
                    "Exception explicitly suppressed by programmer should not have been thrown", t);
        }
    }
}
