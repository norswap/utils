# norswap.utils

- [Maven Dependency][gh-package] ([mirror][jitpack])
- [Javadoc][javadoc]

[gh-package]: https://github.com/norswap/norswap-utils/packages/
[jitpack]: https://jitpack.io/#norswap/norswap-utils
[javadoc]: https://jitpack.io/com/github/norswap/norswap-utils/-SNAPSHOT/javadoc/

---

A collection of Java (8+) utilities.

## General Utilities

- `Chance`: Utility functions for random number generation based on a private `Random` instance.
- `IO`: Utility functions for input/output.
- `NFiles`: Utility funcitons for dealing with files and paths.
- `Predicates`: Utilities for dealing with predicate functions.
- `Strings`: Utility functions dealing with strings and string builders.
- `Vanilla`: Utility functions for Vanilla Java collections.
- `Util`: Miscellaneous utility functions.
- `NArrays`: Utility functions dealing with arrays. 

## Data Structures (`data.structures` package)

- `ArrayStack`: A stack implementation that extends ArrayList.
- `ArrayListInt`: Pendant of `ArrayList` specialized for `int`, with a small stack interface.
- `ArrayListLong`: Pendant of `ArrayList` specialized for `long`, with a small stack interface.
- `multimap` (package): defines the `MultiMap<K, V>` that extends `Map<K, Collection<V>>`, as well
  as implementations thereof. A multimap is a map where multiple values can be bound to a single key.

## Data Wrappers (`data.wrappers` package)

- `Indexed`: A pair made out of an integer and a value.
- `Pair`: A simple type for a pair of values.
- `Slot`: A simple wrapper for a single value, useful when dealing with lambda capture and mutation.

## Exceptions (`exception` package)
- `Exceptional`: Either wraps a value or an exception.
- `NoStackException`: A `RuntimeException` that does not fill the stack trace.
- `ThrowingSupplier`: Just like `java.util.function.Supplier`, but allowed to throw exceptions.
- `ThrowingRunnable`: Just like `java.lang.Runnable`, but allowd to throw exceptions.
- `Exceptions`: Utility functions dealing with exceptions and stack traces.

## Reflection (`reflection` package)
- `Reflection`: Reflection-related utilities.
- `Subtyping`: Enables subtyping checks between instances of `java.lang.reflect.Type`.
- `GenericType`: Minimal implementation of `java.lang.reflect.ParameterizedType`,
  useful with `Subtyping`.

## Visitors & Tree Walkers (`visitors` package)
- `Visitor`: a simple map-based visitor.
- `Walker`: a `Visitor` subclass that can 

## Scaffolding

- `TestFixture`: A base class for test classes that implements some handy assertion methods.
  Compatible at least with TestNG.