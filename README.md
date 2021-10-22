# 𝕊𝕚𝕟𝕘𝕝𝕖𝕥𝕠𝕟 🔂

Dirt-simple singleton implementation.

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

## Credits

Available under an MIT license.

A side quest of J.R. Hill | https://so.dang.cool | https://github.com/hiljusti
