package norswap.utils;

/**
 * Similar to {@link Runnable}, but allowed to throw exceptions.
 */
@FunctionalInterface
public interface ThrowingRunnable
{
    void run() throws Throwable;
}