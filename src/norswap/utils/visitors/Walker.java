package norswap.utils.visitors;

import norswap.utils.NArrays;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This class {@link Visitor} implements a visitor for classes whose instances are arranged in
 * a tree-like hierarchy.
 *
 * <p>The class offers the {@link #walk(T, CTX)} method, which calls the visitor operation on
 * the node (possibly multiple times, see below) and recursively calls itself on the children of the
 * node.
 *
 * <p>To use this class, you must subclass it and override the {@link #children(T)} method to
 * instruct the walker how to find the children of a node.
 *
 * <p>The walker can call the visitor operation on the node before visiting its children ({@link
 * VisitType#PRE_VISIT}), after visiting its children ({@link VisitType#POST_VISIT}), or in-between
 * every pair of successive children ({@link VisitType#IN_VISIT}). It's possible to combine multiple
 * visit modalities, and all those that apply should be passed to {@link #Walker(VisitType...) the
 * constructor}.
 *
 * <p>The visitor's context is a {@link Context} object, which holds an inner context of type
 * {@code CTX}, a variable to indicate which kind of visit is being performed (pre, post or in), and
 * a stack that can be used to exchange values.
 */
public abstract class Walker<T, CTX> extends Visitor<T, Walker.Context<CTX>, Void>
{
    // ---------------------------------------------------------------------------------------------

    /** Does this walker perform a pre-visit? ({@link VisitType#PRE_VISIT}) */
    public final boolean pre_visit;
    /** Does this walker perform a post-visit? ({@link VisitType#POST_VISIT}) */
    public final boolean post_visit;
    /** Does this walker perform an in-visit? ({@link VisitType#IN_VISIT}) */
    public final boolean in_visit;

    // ---------------------------------------------------------------------------------------------

    protected Walker (VisitType... visit_types)
    {
        this.pre_visit  = NArrays.contains(visit_types, VisitType.PRE_VISIT);
        this.post_visit = NArrays.contains(visit_types, VisitType.POST_VISIT);
        this.in_visit   = NArrays.contains(visit_types, VisitType.IN_VISIT);
    }

    // ---------------------------------------------------------------------------------------------

    public static final class Context<CTX>
    {
        public final CTX inner;
        public VisitType visit_type;
        public final Deque<Object> stack = new ArrayDeque<>();

        public Context (CTX inner) {
            this.inner = inner;
        }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Specifies how to retrieve the children of a node.
     */
    public abstract Iterable<T> children (T node);

    // ---------------------------------------------------------------------------------------------

    /**
     * Walk the tree-like hierarchy rooted at the node, see {@link Walker}.
     */
    public void walk (T node, CTX ctx) {
        walk_internal(node, new Context<>(ctx));
    }

    // ---------------------------------------------------------------------------------------------

    final void walk_internal (T node, Context<CTX> context)
    {
        if (pre_visit) {
            context.visit_type = VisitType.PRE_VISIT;
            apply(node, context);
        }

        boolean first = true;
        for (T child: children(node)) {
            if (!first) {
                context.visit_type = VisitType.IN_VISIT;
                apply(node, context);
            } else {
                first = false;
            }
            walk_internal(child, context);
        }
        if (post_visit) {
            context.visit_type = VisitType.POST_VISIT;
            apply(node, context);
        }
    }

    // ---------------------------------------------------------------------------------------------
}
