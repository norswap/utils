package norswap.utils.visitors;

import norswap.utils.reflection.GenericType;
import norswap.utils.reflection.Subtyping;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static norswap.utils.Util.cast;
import static norswap.utils.reflection.Reflection.try_handles;

/**
 * Implementation of {@link Walker} where the children of a node of type {@code T} are take to be
 * all accessible zero-argument methods of the node whose return value is assignable to {@link T}.
 *
 * @see ReflectiveAccessorWalker ReflectiveAccessorWalker for something similar that uses
 * fields instead of accessor methods.
 */
public final class ReflectiveAccessorWalker<T, CTX> extends Walker<T, CTX>
{
    // ---------------------------------------------------------------------------------------------

    public final Class<? extends T> node_type;

    // ---------------------------------------------------------------------------------------------

    /**
     * @param node_type The class for the node type being walked.
     */
    public ReflectiveAccessorWalker (Class<? extends T> node_type, VisitType... visit_types) {
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
                for (Method method: klass.getMethods())
                {
                    if (method.getParameterCount() > 0) continue;
                    Type accessor_type = method.getGenericReturnType();
                    if (Subtyping.check(accessor_type, node_type))
                        simple_accessors.add(lookup.unreflect(method));
                    else if (Subtyping.check(accessor_type, new GenericType(null, Collection.class, node_type)))
                        collection_accessors.add(lookup.unreflect(method));
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
