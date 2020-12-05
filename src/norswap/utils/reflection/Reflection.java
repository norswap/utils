package norswap.utils.reflection;

import norswap.utils.exceptions.Exceptions;
import norswap.utils.NArrays;
import norswap.utils.exceptions.ThrowingRunnable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import static norswap.utils.Util.cast;

public final class Reflection
{
    // ---------------------------------------------------------------------------------------------

    private Reflection () {}

    // ---------------------------------------------------------------------------------------------

    /**
     * {@code (Class<?>) type.getRawType()}
     */
    public static Class<?> raw (ParameterizedType type) {
        return (Class<?>) type.getRawType();
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a {@link ParameterizedType} for the superclass of {@code type} with actual type
     * arguments.
     *
     * <p>You would normally get the parameterized type for the superclass by running {@code
     * ((Class<?>)type.getRawType().getGenericSuperclass()}. The issue is that this type has
     * references to type variables that appear in the declaration of the subclass. So you need
     * to substitute these type variables with their actual value from the passed to get a
     * proper supertype representation.
     *
     * <p>Example: {@code type} represents {@code ArrayDeque<String>}. Then the {@code
     * getGenericSuperClass} bit above would return a representation for {@code
     * AbstractCollection<E>} because {@code ArrayDeque} is declared as {@code class ArrayDeque<E>
     * extends AbstractCollection<E>}. This methods substitutes {@code String} for {@code E}
     * and returns a representation of {@code AbstractCollection<String>}.
     *
     * <p>Returns {@code null} if {@code type} represents {@code Object}.
     */
    public static ParameterizedType actual_parameterized_supertype (ParameterizedType type)
    {
        Type parameterized_superclass = raw(type).getGenericSuperclass();
        if (parameterized_superclass == null) return null;
        return parameterized_superclass instanceof Class<?>
            ? new GenericType(parameterized_superclass, cast(parameterized_superclass))
            : substitute_type_vars(cast(parameterized_superclass), type);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Same idea as {@link #actual_parameterized_supertype(ParameterizedType)} but for interfaces.
     */
    public static ParameterizedType[] actual_parameterized_interfaces (ParameterizedType type)
    {
        Type[] parameterized_interface = raw(type).getGenericInterfaces();
        return NArrays.map(parameterized_interface, new GenericType[0], iface ->
            iface instanceof Class<?>
                ? new GenericType(null, cast(iface))
                : substitute_type_vars(cast(iface), type));
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Same as {@link #actual_parameterized_supertype(ParameterizedType)} but also accepts
     * {@code Class<?>} arguments (in which case there is no parameter substitution to be done).
     */
    public static Type actual_parameterized_supertype (Type type)
    {
        if (type instanceof Class<?>)
            return ((Class<?>) type).getGenericSuperclass();
        if (type instanceof ParameterizedType)
            return actual_parameterized_supertype((ParameterizedType) type);
        else
            throw new IllegalArgumentException("not an class-based type: " + type);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Same idea as {@link #actual_parameterized_interfaces(ParameterizedType)} but also accepts
     * {@code Class<?>} arguments (in which case there is no parameter substitution to be done).
     */
    public static Type[] actual_parameterized_interfaces (Type type)
    {
        if (type instanceof Class<?>)
            return ((Class<?>) type).getGenericInterfaces();
        if (type instanceof ParameterizedType)
            return actual_parameterized_interfaces((ParameterizedType) type);
        else
            throw new IllegalArgumentException("not an class-based type: " + type);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a {@link GenericType} mimicking {@code super_class}, after performing substitution of
     * its type arguments based on {@code sub_class}.
     *
     * <p>{@code super_class} is meant to be a type that appears in an {@code extends} or {@code
     * implements} clause in the class of {@code sub_class}, and as such may contain references to
     * type variables introduce in the sub classclass. This method substitutes these type variables
     * for their actual value in {@code sub_class}.
     *
     * <p>The method is offered for completeness, but {@link #actual_parameterized_supertype} and
     * {@link #actual_parameterized_interfaces} should subsumes most pratical uses.
     */
    public static GenericType substitute_type_vars
            (ParameterizedType super_class, ParameterizedType sub_class)
    {
        TypeVariable<?>[] sub_params = raw(sub_class).getTypeParameters();
        Type[] sub_args = sub_class.getActualTypeArguments();
        return substitute_type_vars(super_class, sub_params, sub_args);
    }

    // ---------------------------------------------------------------------------------------------

    private static GenericType substitute_type_vars
            (ParameterizedType type, TypeVariable<?>[] subst_from, Type[] subst_to)
    {
        Type[] type_args = type.getActualTypeArguments();
        Type[] new_type_args = new Type[type_args.length];

        for (int i = 0; i < type_args.length; ++i)
        {
            Type type_arg = type_args[i];

            if (type_arg instanceof Class<?>) {
                new_type_args[i] = type_arg;
            }

            else if (type_arg instanceof ParameterizedType) {
                ParameterizedType ptype_arg = cast(type_arg);
                if (ptype_arg.getActualTypeArguments().length == 0)
                    new_type_args[i] = ptype_arg;
                else
                    new_type_args[i] = substitute_type_vars(ptype_arg, subst_from, subst_to);
            }

            else if (type_arg instanceof TypeVariable<?>) {
                int index = NArrays.index_of(subst_from, type_arg);
                if (index < 0)
                    throw new RuntimeException("could not find type var to subtitute: " + type_arg);
                new_type_args[i] = subst_to[index];
            }

            else throw new Error(
                        "unknown kind of Type: " + type_arg + "(" + type_arg.getClass() + ")");
        }

        return new GenericType(null, raw(type), new_type_args);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Provides a try statement for invocations of {@link MethodHandle#invoke}, {@link
     * MethodHandle#invokeExact}, etc. Suppressed any checked exceptions thrown by the method using
     * {@link Exceptions#rethrow}, and wraps any {@link WrongMethodTypeException} resulting from an
     * incorrect invocation in an {@link Error}.
     */
    public static void try_handles (ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (WrongMethodTypeException e) {
            throw new Error(e); // should not reach here
        } catch (Throwable t) {
            Exceptions.rethrow(t);
        }
    }

    // ---------------------------------------------------------------------------------------------
}
