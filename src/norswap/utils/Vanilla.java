package norswap.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static norswap.utils.Util.cast;

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
     *
     * <p>If the list doesn't need to grow, use {@link java.util.Arrays#asList} instead.
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

    /**
     * Adds all {@code items} to {@code col}.
     */
    @SafeVarargs
    public static <T> void add_array (Collection<T> col, T... items)
    {
        col.addAll(java.util.Arrays.asList(items));
    }


    // ---------------------------------------------------------------------------------------------

    /**
     * Adds all {@code items} to {@code col}.
     */
    public static <T> void add_all (Collection<T> col, Iterable<T> items)
    {
        if (items instanceof Collection)
            col.addAll((Collection<T>) items);
        else
            items.forEach(col::add);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Concatenates all the items into a single array list, flattening them <b>at most once</b> if
     * necessary.
     *
     * <p>Flattened items include arrays, {@link Iterable} (so collections as well), {@link
     * Enumeration} and {@link Stream}.
     */
    public static ArrayList<Object> concat (Object... items)
    {
        return concat_into(new ArrayList<>(), items);
    }

    // ---------------------------------------------------------------------------------------------


    /**
     * Concatenates all the items into a single array list, flattening them <b>at most once</b> if
     * necessary.
     *
     * <p>Flattened items include arrays, {@link Iterable} (so collections as well), {@link
     * Enumeration} and {@link Stream}.
     */
    public static <T extends Collection<Object>> T concat_into (T col, Object... items)
    {
        for (Object item: items)
        {
            /**/ if (item instanceof Object[])
                add_array(col, (Object[]) items);
            else if (item instanceof Iterable<?>)
                add_all(col, cast(item));
            else if (item instanceof Enumeration<?>)
                col.addAll(Collections.list((Enumeration<?>) item));
            else if (item instanceof Stream<?>)
                ((Stream<?>) item).forEachOrdered(col::add);
            else
                col.add(item);
        }

        return col;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new array list containing the result of applying {@code f} to all items
     * in the supplied collection.
     */
    public static <T, R> ArrayList<R> map (Collection<T> collection, Function<T, R> f)
    {
        ArrayList<R> out = new ArrayList<>(collection.size());
        for (T it: collection)
            out.add(f.apply(it));
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new array list containing the result of applying {@code f} to all items
     * in the supplied iterable.
     */
    public static <T, R> ArrayList<R> map (Iterable<T> iterable, Function<T, R> f)
    {
        ArrayList<R> out = new ArrayList<>();
        for (T it: iterable)
            out.add(f.apply(it));
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new array containing the result of applying {@code f} to all items
     * in the supplied collection.
     *
     * <p>The {@code witness} is any array with the proper type (including a zero-sized one). This
     * is necessary to be able to generate a return value with the proper type, but this array not
     * be mutated in any way.
     */
    public static <T, R> R[] map (Collection<T> collection, R[] witness, Function<T, R> f)
    {
        R[] out = java.util.Arrays.copyOf(witness, collection.size());
        int i = 0;
        for (T item: collection)
            out[i++] = f.apply(item);
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new array containing the result of applying {@code f} to all items
     * in the supplied iterable.
     *
     * <p>The {@code witness} is any array with the proper type (including a zero-sized one). This
     * is necessary to be able to generate a return value with the proper type, but this array not
     * be mutated in any way.
     */
    public static <T, R> R[] map (Iterable<T> iterable, R[] witness, Function<T, R> f)
    {
        return map(iterable, f).toArray(witness);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a new array list containing the result of applying {@code f} to all items
     * in the supplied array.
     */
    public static <T, R> ArrayList<R> map (T[] array, Function<T, R> f)
    {
        ArrayList<R> out = new ArrayList<>();
        for (T it: array)
            out.add(f.apply(it));
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns an array obtained by applying the function {@code f} to each item in {@code array}.
     *
     * <p>The {@code witness} is any array with the proper type (including a zero-sized one). This
     * is necessary to be able to generate a return value with the proper type, but this array not
     * be mutated in any way.
     *
     * <p>This function delegates to {@link NArrays#map}, it is redefined here because it would be
     * hidden by the other map functions otherwise.
     */
    public static <T, R> R[] map (T[] array, R[] witness, Function<T, R> f)
    {
        return NArrays.map(array, witness, f);
    }

    // ---------------------------------------------------------------------------------------------

    private static void check_deque_size (Deque<?> deque, int amount)
    {
        if (amount < 0 || deque.size() < amount)
            throw new IndexOutOfBoundsException(
                "amount (" + amount + ") too large for eque of size (" + deque.size() + ")");
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns the item at position {@code depth} from the front/top of the {@code deque} (use index
     * 0 to get the front of the deque).
     *
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public static <T> T peek_index (Deque<T> deque, int depth)
    {
        check_deque_size(deque, depth + 1);
        int i = 0;
        for (T it: deque) {
            if (i++ == depth)
                return it;
        }
        throw new Error(); // unreachable
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns an array containing the {@code amount} items at the front/top of the {@code deque},
     * in reverse order of distance to the front (the front of the deque will be the last element of
     * the array).
     */
    public static <T> T[] peek (Deque<T> deque, int amount)
    {
        check_deque_size(deque, amount);
        @SuppressWarnings("unchecked")
        T[] args = (T[]) new Object[amount];
        int i = 1;
        for (T it: deque)
            if (i <= amount) args[amount - i++] = it;
            else break;
        return args;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns an array containing the items between {@code index} and the front/top of the {@code
     * deque}, where {@code index} is the distance from the end/bottom of the deque (specifying an
     * {@code index} of 0 returns all values in the deque).
     */
    public static <T> T[] peek_from (Deque<T> deque, int index)
    {
        return peek(deque, deque.size() - index);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns an array containing the {@code amount} items at the front/top of the {@code deque},
     * in reverse order of distance to the front (the front of the deque will be the last element of
     * the array).
     *
     * <p>All values returned are popped from the stack.
     */
    public static <T> T[] pop (Deque<T> deque, int amount)
    {
        check_deque_size(deque, amount);
        @SuppressWarnings("unchecked")
        T[] args = (T[]) new Object[amount];
        for (int i = 1; i <= amount; ++i)
            args[amount - i] = deque.pop();
        return args;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns an array containing the items between {@code index} and the front/top of the {@code
     * deque}, where {@code index} is the distance from the end/bottom of the deque (specifying an
     * {@code index} of 0 returns all values in the deque).
     *
     * <p>All values returned are popped from the stack.
     */
    public static <T> T[] pop_from (Deque<T> deque, int index)
    {
        return pop(deque, deque.size() - index);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Collects the items of {@code stream} into a list.
     *
     * <p>Equivalent to {@code stream.collect(Collectors.toList())}, which is a mouthful for such
     * a common operation.
     *
     * <p>Compared to {@link #concat(Object...)}, the returned list has the proper type parameter.
     */
    public static <T> List<T> as_list(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }

    // ---------------------------------------------------------------------------------------------
}

