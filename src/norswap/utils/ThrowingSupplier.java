package norswap.utils;

import java.util.function.Supplier;

/**
 * Similar to {@link Supplier}, but allowed to throw exceptions.
 */
@FunctionalInterface
public interface ThrowingSupplier<T>
{
    T get () throws Exception;
}
