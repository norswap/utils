package norswap.utils.visitors;

/**
 * Types of visit performed by a {@link Walker}.
 */
public enum VisitType {
    /**
     * Call the visitor operation before visiting the node's children.
     */
    PRE_VISIT,
    /**
     * Call the visitor operation after visiting the node's children.
     */
    POST_VISIT,
    /**
     * Call the visitor operation in between visiting every pair of successive children.
     */
    IN_VISIT
}
