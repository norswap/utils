package norswap.utils.multi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * A straightfoward multimap implementation based on {@link HashMap}.
 * <p>
 * The implementation uses array lists as collections, so duplicate values are permitted.
 * Cast of collections {@link java.util.List} are safe.
 */
public final class MultiHashMap<K, V> extends AbstractMultiHashMap<K, V>
{
    // ---------------------------------------------------------------------------------------------

    @Override ArrayList<V> new_collection()
    {
        return new ArrayList<>();
    }

    // ---------------------------------------------------------------------------------------------

    @Override Collection<V> empty_collection ()
    {
        return Collections.emptyList();
    }

    // ---------------------------------------------------------------------------------------------
}
