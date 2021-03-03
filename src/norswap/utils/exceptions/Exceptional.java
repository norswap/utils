package norswap.utils.exceptions;

import java.util.function.Function;

/**
 * Either wraps a value of type {@code T} or a throwable (which we'll abusively call "exception",
 * it just rolls better off the tongue, doesn't it?).
 *
 * <p>Construct instances of this class with the {@link #value(Object)} or one of the
 * {@link #exception} methods.
 */
public final class Exceptional<T>
{
    // ---------------------------------------------------------------------------------------------

    private final T value;
    private final Throwable exception;

    // ---------------------------------------------------------------------------------------------

    private Exceptional (T value, Throwable exception) {
        this.value = value;
        this.exception = exception;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Creates an {@link Exceptional} instance by running the given supplier and wrapping its
     * result or thrown exception.
     */
    public static <T> Exceptional<T> of (ThrowingSupplier<T> supplier) {
        try {
            return Exceptional.value(supplier.get());
        } catch (Throwable t) {
            return Exceptional.exception(t);
        }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new exceptional holding the given value.
     */
    public static <T> Exceptional<T> value (T value) {
        return new Exceptional<>(value, null);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new exceptional holding the given exception.
     */
    public static <T> Exceptional<T> exception (Throwable exception) {
        return new Exceptional<>(null, exception);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new exceptional holding a stackless runtime exception having the given
     * string as its message.
     */
    public static <T> Exceptional<T> error (String message) {
        return new Exceptional<>(null, new NoStackException(message));
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * If this object holds a value, returns it, otherwise throws the held exception (wrapped in
     * a {@link RuntimeException} as per {@link Exceptions#rethrow(Throwable)}, if it is a
     * checked exception).
     */
    public T get() {
        if (exception != null) Exceptions.rethrow(exception);
        return value;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * If this object holds a value, returns it, else returns null.
     *
     * <p>Note that held values might also be null!
     */
    public T nullable() {
        return exception == null ? value : null;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns the underlying value or exception.
     */
    public Object unwrap() {
        return exception == null ? value : exception;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns the exception held by this object, or null if it holds a value instead.
     */
    public Throwable exception() {
        return exception;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Indicates whether the object holds a value.
     */
    public boolean isValue() {
        return exception == null;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Indicates whether the object holds an exception.
     */
    public boolean isException() {
        return exception != null;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * If this object holds a value, returns a new exceptional holding the result of applying
     * {@code f} to the value, else returns a new exceptional holding the exception.
     */
    public <R> Exceptional<R> map (Function<T, R> f) {
        return exception == null
            ? Exceptional.value(f.apply(value))
            : Exceptional.exception(exception);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * If this object holds a value, returns the result of applying {@code f} to the value,
     * else returns a new exceptional holding the exception.
     */
    public <R> Exceptional<R> flatmap (Function<T, Exceptional<R>> f) {
        return exception == null
            ? f.apply(value)
            : Exceptional.exception(exception);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * If the object holds an exception, return its message, otherwise returns the string "success".
     */
    public String message() {
        return exception == null
            ?  "success"
            : exception.getMessage();
    }

    // ---------------------------------------------------------------------------------------------
}
