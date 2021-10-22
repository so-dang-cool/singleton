# 🔂 `Singleton` 🔂

Dirt simple singleton implementations.

tl;dr: `Singleton.eager(instance)` or `Singleton.lazy(() -> instance)`

## Singletons made trivial:

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

"Self" Singletons should still prefer composition over inheritance. (TODO: Add an example here)

## Credits

Available under an MIT license.

A side quest of J.R. Hill | https://so.dang.cool | https://github.com/hiljusti

