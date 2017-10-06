package norswap.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Utility functions for Vanilla Java collections.
 */
public final class Vanilla
{
    // ---------------------------------------------------------------------------------------------

    /**
     * @return the last element of {@code list}.
     * @throws NoSuchElementException if the list is empty.
     */
    public static <T> T last (List<T> list)
    {
        if (list.isEmpty())
            throw new NoSuchElementException();
        return list.get(list.size() - 1);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * @return the last element of {@code list} or {@code null} if the list is empty.
     */
    public static <T> T last_or_null (List<T> list)
    {
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Removes the last element of the list.
     * @throws NoSuchElementException if the list is empty.
     */
    public static <T> void remove_last (List<T> list)
    {
        if (list.isEmpty()) throw new NoSuchElementException();
        list.remove(list.size() - 1);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Removes the last {@code n} elements of the list.
     * @throws NoSuchElementException if the list has less than {@code n} elements.
     */
    public static <T> void remove_last (List<T> list, int n)
    {
        if (list.size() < n) throw new NoSuchElementException();
        for (int i = 0; i < n; ++i) list.remove(list.size() - 1);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new {@link ArrayList} containing the items from {@code items}.
     */
    @SafeVarargs
    public static <T> ArrayList<T> list (T... items)
    {
        ArrayList<T> out = new ArrayList<>(items.length);
        Collections.addAll(out, items);
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new {@link HashSet} containing the items from {@code items}.
     */
    @SafeVarargs
    public static <T> HashSet<T> set (T... items)
    {
        HashSet<T> out = new HashSet<>(items.length);
        Collections.addAll(out, items);
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Removes the {@code n} last characters from {@code b}.
     * @throws NoSuchElementException if the string builder has less than {@code n} characters.
     */
    public static void pop (StringBuilder b, int n)
    {
        if (b.length() < n) throw new NoSuchElementException();
        b.replace(b.length() - n, b.length(), "");
    }

    // ---------------------------------------------------------------------------------------------
}

