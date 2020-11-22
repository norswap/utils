package norswap.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

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
}
