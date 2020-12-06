package norswap.utils.visitors;

import norswap.utils.reflection.GenericType;
import norswap.utils.reflection.Subtyping;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static norswap.utils.Util.cast;
import static norswap.utils.reflection.Reflection.try_handles;

/**
 * Implementation of {@link Walker} where the children of a node of type {@code T} are
 * take to be all accessible fields of the node whose value is assignable to {@code T}.
 *
 * @see ReflectiveAccessorWalker ReflectiveAccessorWalker for something similar that uses
 * accessor methods instead of fields.
 */
public final class ReflectiveFieldWalker<T> extends Walker<T>
{
    // ---------------------------------------------------------------------------------------------

    public final Class<? extends T> node_type;

    // ---------------------------------------------------------------------------------------------

    /**
     * @param node_type The class for the node type being walked.
     */
    public ReflectiveFieldWalker (Class<? extends T> node_type, WalkVisitType... visit_types) {
        super(visit_types);
        this.node_type = node_type;
    }

    // ---------------------------------------------------------------------------------------------

    private static final MethodHandles.Lookup lookup = MethodHandles.lookup();

    // ---------------------------------------------------------------------------------------------

    private class ClassData {
        public final ArrayList<MethodHandle> simple_accessors = new ArrayList<>();
        public final ArrayList<MethodHandle> collection_accessors = new ArrayList<>();

        private ClassData (Class<?> klass) {
            try {
                for (Field field: klass.getFields())
                {
                    Type field_type = field.getGenericType();
                    if (Subtyping.check(field_type, node_type))
                        simple_accessors.add(lookup.unreflectGetter(field));
                    else if (Subtyping.check(field_type, new GenericType(null, Collection.class, node_type)))
                        collection_accessors.add(lookup.unreflectGetter(field));
                }
            } catch (IllegalAccessException e) {
                throw new Error(e); // should not reach here
            }
        }
    }

    // ---------------------------------------------------------------------------------------------

    private final HashMap<Class<?>, ClassData> class_data = new HashMap<>();

    // ---------------------------------------------------------------------------------------------

    @Override public Iterable<T> children (T node)
    {
        ClassData data = class_data.computeIfAbsent(node.getClass(), ClassData::new);
        int initial_capacity = data.simple_accessors.size() + data.collection_accessors.size();
        ArrayList<T> children = new ArrayList<>(initial_capacity);

        try_handles(() -> {
            for (MethodHandle handle: data.simple_accessors)
                children.add(cast(handle.invoke(node)));
            for (MethodHandle handle: data.collection_accessors)
                children.addAll(cast(handle.invoke(node)));
        });

        return children;
    }

    // ---------------------------------------------------------------------------------------------
}
