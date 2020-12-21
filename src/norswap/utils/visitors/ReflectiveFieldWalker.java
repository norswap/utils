package norswap.utils.visitors;

import norswap.utils.reflection.GenericType;
import norswap.utils.reflection.Subtyping;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link Walker} where the children of a node of type {@code T} are
 * take to be all accessible fields of the node whose value is assignable to {@code T}.
 *
 * @see ReflectiveAccessorWalker ReflectiveAccessorWalker for something similar that uses
 * accessor methods instead of fields.
 */
public final class ReflectiveFieldWalker<T> extends ReflectiveWalker<T>
{
    // ---------------------------------------------------------------------------------------------

    /**
     * @param node_type The class for the node type being walked.
     */
    public ReflectiveFieldWalker (Class<? extends T> node_type, WalkVisitType... visit_types) {
        super(node_type, visit_types);
    }

    // ---------------------------------------------------------------------------------------------

    @Override public List<HandleWrapper> handles_for (Class<? extends T> klass)
    {
        ArrayList<HandleWrapper> list = new ArrayList<>();
        try {
            for (Field field: klass.getFields())
            {
                Type field_type = field.getGenericType();
                if (Subtyping.check(field_type, node_type))
                    list.add(new HandleWrapper(lookup.unreflectGetter(field), false));
                else if (Subtyping.check(field_type, new GenericType(null, Collection.class, node_type)))
                    list.add(new HandleWrapper(lookup.unreflectGetter(field), true));
            }
        } catch (IllegalAccessException e) {
            throw new Error(e); // should not reach here
        }
        return list;
    }

    // ---------------------------------------------------------------------------------------------
}
