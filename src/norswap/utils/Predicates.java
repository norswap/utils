package norswap.utils;

import java.util.function.Predicate;

import static norswap.utils.Util.attempt;

/**
 * Utilities to work with predicates.
 */
public final class Predicates
{
    // ---------------------------------------------------------------------------------------------

    /**
     * A predicate that always evaluates to true.
     */
    public static final Predicate<Object> TRUE = it -> true;

    // ---------------------------------------------------------------------------------------------

    /**
     * A predicate that always evaluates to false.
     */
    public static final Predicate<Object> FALSE = it -> false;

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a string representation of a predicate, which is either the result of its
     * {@link #toString()} method if the predicate class overrides it, or "pred" otherwise.
     */
    public static String to_string (Predicate<?> pred)
    {
        Class<?> pred_tostring_src
            = attempt(() -> pred.getClass().getMethod("toString").getDeclaringClass());

        boolean overriden
            = pred != null && !pred_tostring_src.equals(Object.class);

        return overriden
            ? pred.toString()
            : "pred";
    }

    // ---------------------------------------------------------------------------------------------
}
