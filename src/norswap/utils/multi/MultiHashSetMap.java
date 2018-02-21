package norswap.utils.multi;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A straightfoward multimap implementation based on {@link HashMap}.
 * <p>
 * The implementation uses hash sets as collections, so duplicate values are
 * automatically eliminated.
 */
public final class MultiHashSetMap<K, V> extends AbstractMultiHashMap<K, V>
{
    // ---------------------------------------------------------------------------------------------

    @Override HashSet<V> new_collection()
    {
        return new HashSet<>();
    }

    // ---------------------------------------------------------------------------------------------
}
