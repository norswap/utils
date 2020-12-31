package norswap.utils.exceptions;

import java.util.function.Function;

/**
 * Either wraps a value of type {@code T} or a runtime exception.
 *
 * <p>Construct instances of this class with the {@link #value(Object)} or one of the
 * {@link #exception} methods.
 */
public final class Exceptional<T>
{
    // ---------------------------------------------------------------------------------------------

    private final T value;
    private final RuntimeException exception;

    // ---------------------------------------------------------------------------------------------

    private Exceptional (T value, RuntimeException exception)
    {
        this.value = value;
        this.exception = exception;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new exceptional holding the given value.
     */
    public static <T> Exceptional<T> value (T value)
    {
        return new Exceptional<>(value, null);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new exceptional holding a stackless runtime exception having the given throwable
     * as its cause.
     */
    public static <T> Exceptional<T> exception (Throwable throwable)
    {
        return new Exceptional<>(null, new NoStackException(throwable));
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new exception holding the given exception.
     */
    public static <T> Exceptional<T> exception (RuntimeException exception)
    {
        return new Exceptional<>(null, exception);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new exceptional holding a stackless runtime exception having the given
     * string as its message.
     */
    public static <T> Exceptional<T> error (String message)
    {
        return new Exceptional<>(null, new NoStackException(message));
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * If this object holds a value, returns it, otherwise throws the held exception.
     */
    public T get()
    {
        if (exception != null) throw exception;
        return value;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * If this object holds a value, returns it, else returns null.
     *
     * <p>Note that held values might also be null!
     */
    public T nullable()
    {
        return exception == null ? value : null;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns the underlying value or exception.
     */
    public Object unwrap()
    {
        return exception == null ? value : exception;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns the exception held by this object, or null if it holds a value instead.
     */
    public RuntimeException exception()
    {
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
    public <R> Exceptional<R> map (Function<T, R> f)
    {
        return exception == null
            ? Exceptional.value(f.apply(value))
            : Exceptional.exception(exception);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * If this object holds a value, returns the result of applying {@code f} to the value,
     * else returns a new exceptional holding the exception.
     */
    public <R> Exceptional<R> flatmap (Function<T, Exceptional<R>> f)
    {
        return exception == null
            ? f.apply(value)
            : Exceptional.exception(exception);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * If the object holds an exception, return its message, otherwise returns the string "success".
     */
    public String message()
    {
        return exception == null
            ?  "success"
            : exception.getMessage();
    }

    // ---------------------------------------------------------------------------------------------
}
