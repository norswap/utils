# norswap.utils

- [Install](doc/INSTALL.md)
- [Javadoc] (updates daily) / [Mirror] (might need loading)

[Javadoc]: https://javadoc.io/doc/com.norswap/utils/
[Mirror]: https://jitpack.io/com/github/norswap/utils/-SNAPSHOT/javadoc/

---

A collection of Java (8+) utilities.

## General Utilities

- `Chance`: Utility functions for random number generation based on a private `Random` instance.
- `Concurrency`: Utility functions dealing with concurrency and asynchronicity (futures etc).
- `IO`: Utility functions for input/output.
- `NFiles`: Utility funcitons for dealing with files and paths.
- `Predicates`: Utilities for dealing with predicate functions.
- `Strings`: Utility functions dealing with strings and string builders.
- `Vanilla`: Utility functions for Vanilla Java collections.
- `Util`: Miscellaneous utility functions.
- `NArrays`: Utility functions dealing with arrays.
   (Other array functions of interest are defined in `Vanilla`.)

## Data Structures
(`data.structures` package)

- `ArrayStack`: A stack implementation that extends ArrayList.
- `ArrayListInt`: Pendant of `ArrayList` specialized for `int`, with a small stack interface.
- `ArrayListLong`: Pendant of `ArrayList` specialized for `long`, with a small stack interface.
- `multimap` (package): defines the `MultiMap<K, V>` that extends `Map<K, Collection<V>>`, as well
  as implementations thereof. A multimap is a map where multiple values can be bound to a single key.

## Data Wrappers
(`data.wrappers` package)

- `Indexed`: A pair made out of an integer and a value.
- `Maybe`: Better alternative to `java.util.Optional` that notably supports `null` values.
- `Pair`: A simple type for a pair of values.
- `Slot`: A simple wrapper for a single value, useful when dealing with lambda capture and mutation.

## Functional Interfaces
(`data.functions` package)
- `Indexed<XXX>`: A variant of `java.util.funciton.<XXX>` functional interface that accepts an `int`
  index as first argument.

## Exceptions
(`exception` package)

- `Exceptional`: Either wraps a value or an exception.
- `NoStackException`: A `RuntimeException` that does not fill the stack trace.
- `Throwing{Runnable, Consumer, Supplier}`: Just like `java.lang.Runnable` /
  `java.util.function.{Consumer, Supplier}`, but allowed to throw exceptions.
- `Exceptions`: Utility functions dealing with exceptions and stack traces.

## Reflection
(`reflection` package)

- `Reflection`: Reflection-related utilities.
- `Subtyping`: Enables subtyping checks between instances of `java.lang.reflect.Type`.
- `GenericType`: Minimal implementation of `java.lang.reflect.ParameterizedType`,
  useful with `Subtyping`.

## Visitors & Tree Walkers
(`visitors` package)

- `Visitor`: a simple map-based visitor.
- `ValuedVisitor`: a variant of `Visitor` that support returning a value from the visitor method.  
- `Walker`: a visitor variant that can be used to perform a depth-first tree walk in pre-, post- or
  in-order (multiple orders can be used during the same walk).

## Scaffolding

- `TestFixture`: A base class for test classes that implements some handy assertion methods.
  Compatible at least with TestNG.

-----

## Versioning

Versions are `M.m.p`

- Major (`M`) is incremented when significant changes are made to the library. It might take
  non-trivial time to migrate.
- Minor (`m`) is incremented when new features are added, or existing features are modified.
  The main contract here is that migration should be quick, and a clear migration path exists.
- Patch (`p`) is incremented for hotfixes, or tiny / quality-of-life improvements. Patch **never**
  introduce breaking changes, excepted under the guise of bug-fixes.