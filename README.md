# norswap.utils

[![jitpack](https://jitpack.io/v/norswap/norswap-utils.svg)][jitpack]

- [Maven Dependency][jitpack] (the badge above indicates the latest commit available)
- [Javadoc][javadoc]

[jitpack]: https://jitpack.io/#norswap/norswap-utils
[javadoc]: https://jitpack.io/com/github/norswap/norswap-utils/-SNAPSHOT/javadoc/

---

## Utilities

- `Chance`: Utility functions for random number generation based on a private `Random` instance.
- `Exceptions`: Utility functions dealing with exceptions.
- `IO`: Utility functions for input/output.
- `Predicates`: Utilities for dealing with predicate functions.
- `Strings`: Utility functions dealing with strings and string builders.
- `Vanilla`: Utility functions for Vanilla Java collections.
- `Util`: Miscellaneous utility functions.
- `NArrays`: Utility functions dealing with arrays. 

## Data Structures

- `ArrayStack`: A stack implementation that extends ArrayList.
- `ArrayListInt`: Pendant of `ArrayList` specialized for `int`, with a small stack interface.
- `ArrayListLong`: Pendant of `ArrayList` specialized for `long`, with a small stack interface.
- `multi` (package): defines the `MultiMap<K, V>` that extends `Map<K, Collection<V>>`, as well as
  implementations thereof. A multimap is a map where multiple values can be bound to a single key.

## Other

- `Exceptional`: Either wraps a value or an exception.
- `Indexed`: A pair made out of an integer and a value.
- `NoStackException`: A `RuntimeException` that does not fill the stack trace.
- `Pair`: A simple type for a pair of values.
- `Slot`: A simple wrapper for a single value, useful when dealing with lambda capture and mutation.
- `ThrowingSupplier`: Just like `Supplier`, but allowed to throw exceptions.

## Scaffolding

- `TestFixture`: A base class for test classes that implements some handy assertion methods.
Compatible at least with TestNG.