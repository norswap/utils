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
     * Return the value returned by the given supplier, suppressing any checked {@link Exception} it
     * might throw bu wrapping it in a {@link RuntimeException}.
     */
    public static void suppress (ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Return the value returned by the given supplier, suppressing any checked {@link Exception} it
     * might throw bu wrapping it in a {@link RuntimeException}.
     */
    public static <T> T suppress (ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Rethrows {@code t} as-is if it isn't a checked exception, otherwise wraps it in
     * a {@link RuntimeException} before rethrowing it.
     */
    public static void rethrow (Throwable t) {
        if (t instanceof RuntimeException)
            throw (RuntimeException) t;
        if (t instanceof Error)
            throw (Error) t;
        else
            throw new RuntimeException(t);
    }
}
