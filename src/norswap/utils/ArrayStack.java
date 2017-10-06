package norswap.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import static norswap.utils.Vanilla.remove_last;

/**
 * A stack implementation that extends {@link ArrayList}.
 */
public final class ArrayStack<T> extends ArrayList<T>
{
    // ---------------------------------------------------------------------------------------------

    public ArrayStack() {}

    // ---------------------------------------------------------------------------------------------

    /**
     * Creates a new array stack with the given capacity.
     */
    public ArrayStack (int n) {
        super(n);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Pushes {@code item} at the top of the stack.
     */
    public void push (T item) {
        add(item);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Pushes {@code item} at the top of the stack.
     */
    @SafeVarargs
    public final void push (T... items) {
        addAll(Arrays.asList(items));
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Removes and returns the item at the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    public T pop()
    {
        if (isEmpty()) throw new NoSuchElementException();
        return remove(size() - 1);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Removes the {@code n} items at the top of the stack.
     * @throws NoSuchElementException if the stack does not have that many items, in which case
     * no items are removed.
     */
    public void pop (int n)
    {
        if (size() < n) throw new NoSuchElementException();
        remove_last(this, n);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Removes and returns the item at the top of the stack, or null if the stack is empty.
     */
    public T poll()
    {
        if (isEmpty()) return null;
        return remove(size() - 1);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * @return the item at the top of the stack, or null if the stack is empty.
     */
    public T peek()
    {
        if (isEmpty()) return null;
        return get(size() - 1);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * @return the item that is {@code n} items below the top of the stack (0 = top), or null
     * if the stack does not have that many items.
     */
    public T back (int n)
    {
        if (size() <= n) return null;
        return get(size() - 1 - n);
    }

    // ---------------------------------------------------------------------------------------------
}
