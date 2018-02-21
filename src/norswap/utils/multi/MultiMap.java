package norswap.utils.multi;

import norswap.utils.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * An extension of a regular {@code K -> Collection<V>} map to enable/improve its use as a multimap:
 * a map from  {@link K} to a collection of invididual {@link V} values.
 */
public interface MultiMap<K, V> extends Map<K, Collection<V>>
{
    // ---------------------------------------------------------------------------------------------

    /**
     * Return an unmodifiable view of the collection of values to which {@code key} is mapped, or an
     * empty collection if there is no mapping for the key.
     *
     * <p>Also see {@link Map#get} for more details. Notably, when this method returns an
     * empty collection, there may be no mapping for the key, or there may be a mapping from the key
     * to an empty collection.
     */
    @Override Collection<V> get (Object key);

    // ---------------------------------------------------------------------------------------------

    /**
     * Removes the mapping for {@code key} from the map, if present.
     *
     * @return An unmodifiable view of the collection of values previously associated with the key,
     * or an empty map if not values were associated with the key.
     */
    @Override Collection<V> remove(Object key);

    // ---------------------------------------------------------------------------------------------

    /**
     * Associates {@code value} with {@code key}, while preserving all other associated values.
     *
     * @return The collection of values associated with the key after the operation has taken place.
     */
    Collection<V> add (K key, V value);

    // ---------------------------------------------------------------------------------------------

    /**
     * Associates all values in {@code values}, while preserving all other associated values.
     *
     * @return The collection of values associated with the key after the operation has taken place.
     */
    Collection<V> add_all (K key, V[] values);

    // ---------------------------------------------------------------------------------------------

    /**
     * Associates all values in {@code values}, while preserving all other associated values.
     *
     * @return The collection of values associated with the key after the operation has taken place.
     */
    Collection<V> add_all (K key, Iterable<V> values);

    // ---------------------------------------------------------------------------------------------

    /**
     * Associates all values in {@code values}, while preserving all other associated values.
     *
     * @return The collection of values associated with the key after the operation has taken place.
     */
    default Collection<V> add_all (K key, Collection<V> values)
    {
        return add_all(key, (Iterable<V>) values);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a flattened collection containing all the values associated with all keys in the
     * multimap.
     */
    default Collection<V> all_values()
    {
        ArrayList<V> out = new ArrayList<>();
        for (Collection<V> col: values()) out.addAll(col);
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Adds all key-value pairs from {@code single_map} into this multimap (as per {@link
     * #add(Object, Object)}).
     */
    default void add (Map<K, V> single_map)
    {
        for (Entry<K, V> e: single_map.entrySet())
            add(e.getKey(), e.getValue());
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Adds all key-value associations from {@code multi_map} into this multimap (as per {@link
     * #add_all(Object, Collection)}. This basically merges {@code multi_map} inside the receiver.
     */
    default void add (MultiMap<K, V> multi_map)
    {
        for (Entry<K, Collection<V>> e: multi_map.entrySet())
            add_all(e.getKey(), e.getValue());
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * For each item in {@code items}, apply the function {@code f} and insert the resulting
     * binding into the multi map, before returning it.
     */
    default <X> MultiMap<K, V> assoc (Iterable<X> items, Function<X, Pair<K, V>> f)
    {
        for (X item: items) {
            Pair<K, V> pair = f.apply(item);
            if (pair == null) continue;
            add(pair.a, pair.b);
        }
        return this;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * For each item in {@code items}, apply the function {@code f} and insert the resulting
     * binding into the multi map, before returning it.
     */
    default <X> MultiMap<K, V> assoc (X[] items, Function<X, Pair<K, V>> f)
    {
        for (X item: items) {
            Pair<K, V> pair = f.apply(item);
            if (pair == null) continue;
            add(pair.a, pair.b);
        }
        return this;
    }

    // ---------------------------------------------------------------------------------------------
}
