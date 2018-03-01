package norswap.utils.multi;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

abstract class AbstractMultiHashMap<K, V>
        extends HashMap<K, Collection<V>>
        implements MultiMap <K, V>
{
    // ---------------------------------------------------------------------------------------------

    abstract Collection<V> new_collection();

    // ---------------------------------------------------------------------------------------------

    abstract Collection<V> empty_collection();

    // ---------------------------------------------------------------------------------------------

    @Override public Collection<V> get (Object key)
    {
        Collection<V> out = super.get(key);
        return out == null ? empty_collection() : Collections.unmodifiableCollection(out);
    }

    // ---------------------------------------------------------------------------------------------

    @Override public Collection<V> remove (Object key)
    {
        Collection<V> out = super.remove(key);
        return out == null ? empty_collection() : Collections.unmodifiableCollection(out);
    }

    // ---------------------------------------------------------------------------------------------

    @Override public Collection<V> add (K key, V value)
    {
        Collection<V> out = computeIfAbsent(key, k -> new_collection());
        out.add(value);
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    @Override public Collection<V> delete (K key, V value)
    {
        Collection<V> out = super.get(key);
        if (out == null) return empty_collection();
        out.remove(value);
        return out.isEmpty()
                ? remove(key)
                : Collections.unmodifiableCollection(out);
    }

    // ---------------------------------------------------------------------------------------------

    @Override public Collection<V> delete_pollute (K key, V value)
    {
        Collection<V> out = super.get(key);
        if (out == null) return empty_collection();
        out.remove(value);
        return Collections.unmodifiableCollection(out);
    }

    // ---------------------------------------------------------------------------------------------

    @Override public Collection<V> add_all (K key, V[] values)
    {
        Collection<V> out = computeIfAbsent(key, k -> new_collection());
        Collections.addAll(out, values);
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    @Override public Collection<V> add_all (K key, Iterable<V> values)
    {
        Collection<V> out = computeIfAbsent(key, k -> new_collection());
        for (V v: values) out.add(v);
        return out;
    }

    // ---------------------------------------------------------------------------------------------

    @Override public Collection<V> add_all (K key, Collection<V> values)
    {
        Collection<V> out = computeIfAbsent(key, k -> new_collection());
        out.addAll(values);
        return out;
    }

    // ---------------------------------------------------------------------------------------------
}
