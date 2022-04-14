# 🔂 𝕊𝕚𝕟𝕘𝕝𝕖𝕥𝕠𝕟

[![Maven Central](https://img.shields.io/maven-central/v/so.dang.cool/z.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22so.dang.cool%22%20AND%20a:%22singleton%22)
[![Javadoc](https://javadoc.io/badge2/so.dang.cool/z/javadoc.svg)](https://javadoc.io/doc/so.dang.cool/singleton)
![License](https://img.shields.io/github/license/hiljusti/singleton)

Dirt-simple Java implementation of the classic singleton.

tl;dr: `Singleton.eager(instance)` or `Singleton.lazy(() -> instance)`

## Singletons made trivial

No more null checking or sentinel values.

```java
class FrozenInteger {
    private final Singleton<Integer> instance;

    FrozenInteger(int i) {
        this.instance = Singleton.eager(i);
    }

    public int value() {
        return this.instance.get();
    }
}
```

_(Of course... this is a too-trivial example. For primitives and immutable objects,
just use a `public static final` constant.)_

## Self-contained Singletons

`Singleton` is intentionally not inheritable or subclassable in Java. "Self"
Singletons are recommended to prefer [composition over inheritance][comp].

[comp]: https://en.wikipedia.org/wiki/Composition_over_inheritance

```java
class BigImportantThing {
    // All the internal state you want...

    private static final Singleton<BigImportantThing> self = Singleton.lazy(() -> {
        // Calls constructor, return an instance.
    });

    // Private constructor to prevent too many "big important things."
    private BigImportantThing(/* some parameters */) {
        // Some construction
    }

    // Static provider to get the one and only "big important thing."
    public static BigImportantThing get() {
        return self.get();
    }

    // All the methods you want...
}
```

## When to choose Singleton

There are many ways to implement singletons. (Effective Java has a couple
strategies, your DI framework has one, other JVM languages have options,
Google around for many more.)

This lib is good for a general-purpose Singleton implementation that can be
used in many contexts.

If you have a primitive or immutable class and need a singleton, use a
`public static final` value. I give you permission.

If you need more performance or less overhead, look into rolling your own
implementation that works for your needs. (But wow, what would you even be
doing where you'd need to create so many Singleton instances so fast?)

## Credits

Available under an MIT license.

A side quest of J.R. Hill | https://so.dang.cool | https://github.com/hiljusti
