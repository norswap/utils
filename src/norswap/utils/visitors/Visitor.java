package norswap.utils.visitors;

import java.util.HashMap;
import java.util.function.BiFunction;

import static norswap.utils.Util.cast;

/**
 * An instance of this class can be used to specify an operation that has different behaviours
 * (<i>specializations</i>) for different subclasses of {@code T}.
 *
 * <p>Each specialization is represented by an instance of {@link BiFunction BiFunction}{@code <T,
 * CTX, R>}, where T is the class of the class being specialized for, CTX is a context object, and
 * R is the return type of the operation.
 *
 * <p>Specializations are registered by calling {@link #register(Class, BiFunction)}.
 * Specializations for a class are only operational for values that have that specific class —
 * inheritance does not enter into account when dispatching the operation.
 *
 * <p>The operation is invoked by calling {@link #apply(T, CTX)}.
 *
 * <p>If a specialization for the class of the value does not exist, the fallback method {@link
 * #fallback(T, CTX)} is called instead. The fallback can be specialized by overriding the method or
 * by calling {@link #register_fallback(BiFunction)}.
 *
 * <p>This is implemented on top of a class-to-specialization hashmap.
 */
public class Visitor<T, CTX, R> implements BiFunction<T, CTX, R>
{
    // ---------------------------------------------------------------------------------------------

    /**
     * Map from classes to specializations.
     */
    protected final HashMap<Class<? extends T>, BiFunction<? extends T, CTX, ? extends R>>
        dispatch = new HashMap<>();

    // ---------------------------------------------------------------------------------------------

    /**
     * The fallback specialization used by the standard implementation of {@link #fallback(T, CTX)}.
     */
    protected BiFunction<T, CTX, ? extends R> fallback_specialization;

    // ---------------------------------------------------------------------------------------------

    /**
     * Run the operation by calling the appropriate overload for {@code value}, or the fallback.
     */
    @Override public R apply (T value, CTX context)
    {
        BiFunction<? extends T, CTX, ? extends R> action = dispatch.get(value.getClass());
        if (action == null) return fallback(value, context);
        return action.apply(cast(value), context);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Called by {@code apply} whenever an overload isn't for a value.
     *
     * <p>Specify the fallback by overriding this method, or by calling {@link
     * #register_fallback(BiFunction)}.
     */
    public R fallback(T value, CTX context) {
        if (fallback_specialization == null)
            throw new IllegalStateException("no fallback specified for " + this);
        return fallback_specialization.apply(value, context);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Register a specialization for the given class.
     */
    public <S extends T> Visitor<T, CTX, R> register
            (Class<? extends T> klass, BiFunction<S, CTX, ? extends R> specialization)
    {
        dispatch.put(klass, specialization);
        return this;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Register the fallback specialization by setting the {@link #fallback_specialization} field.
     *
     * <p>Useless if {@link #fallback_specialization} has been overriden to not use the {@link
     * #fallback_specialization} field.
     */
    public Visitor<T, CTX, R> register_fallback (BiFunction<T, CTX, ? extends R> fallback)
    {
        this.fallback_specialization = fallback;
        return this;
    }

    // ---------------------------------------------------------------------------------------------
}
