# singleton

Dirt simple singleton implementations.

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

A WIP by J.R. Hill

