package norswap.utils.visitors;

import norswap.utils.reflection.GenericType;
import norswap.utils.reflection.Subtyping;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link Walker} where the children of a node of type {@code T} are take to be
 * all accessible zero-argument methods of the node whose return value is assignable to {@code T}.
 *
 * @see ReflectiveAccessorWalker ReflectiveAccessorWalker for something similar that uses
 * fields instead of accessor methods.
 */
public final class ReflectiveAccessorWalker<T> extends ReflectiveWalker<T>
{
    // ---------------------------------------------------------------------------------------------

    /**
     * @param node_type The class for the node type being walked.
     */
    public ReflectiveAccessorWalker (Class<? extends T> node_type, WalkVisitType... visit_types) {
        super(node_type, visit_types);
    }

    // ---------------------------------------------------------------------------------------------

    @Override public List<HandleWrapper> handles_for (Class<? extends T> klass)
    {
        ArrayList<HandleWrapper> list = new ArrayList<>();
        try {
            for (Method method: klass.getMethods())
            {
                if (method.getParameterCount() > 0) continue;
                Type accessor_type = method.getGenericReturnType();
                if (Subtyping.check(accessor_type, node_type))
                    list.add(new HandleWrapper(lookup.unreflect(method), false));
                else if (Subtyping.check(accessor_type, new GenericType(null, Collection.class, node_type)))
                    list.add(new HandleWrapper(lookup.unreflect(method), true));
            }
        } catch (IllegalAccessException e) {
            throw new Error(e); // should not reach here
        }
        return list;
    }

    // ---------------------------------------------------------------------------------------------
}
