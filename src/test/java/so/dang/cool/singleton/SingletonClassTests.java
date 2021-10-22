package so.dang.cool.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public class SingletonClassTests {
    class FrozenInteger {
        private final Singleton<Integer> instance;

        FrozenInteger(int i) {
            this.instance = Singleton.eager(i);
        }

        public int value() {
            return this.instance.get();
        }
    }

    @Test
    public void integerExample() {
        FrozenInteger fint = new FrozenInteger(12345);

        assertEquals(12345, fint.value());
    }

    static class ConstantBaseballPlayer {
        // Don't worry, this is fully encapsulated!
        private class State {
            private String name;
            private int number;
            private boolean isCool;
        }

        private final Singleton<State> singleton;

        // Sometimes static behind-the-scenes constructors are preferred!
        // This particular implementation would calculate all values on a first
        // query, but instantaneous on any subsequent value.
        private ConstantBaseballPlayer(Supplier<String> name, Supplier<Integer> number, Supplier<Boolean> isCool) {
            this.singleton = Singleton.lazy(() -> {
                State state = new State();
                state.name = name.get();
                state.number = number.get();
                state.isCool = isCool.get();
                return state;
            });
        }

        // Imagine different combinations of constructors here. Here we use
        // one supplier, one instance, and one default value.
        public static ConstantBaseballPlayer of(Supplier<String> name, int number) {
            return new ConstantBaseballPlayer(name, () -> number, () -> true);
        }

        public String getName() {
            return this.singleton.get().name;
        }

        public int getNumber() {
            return this.singleton.get().number;
        }

        public boolean isCool() {
            return this.singleton.get().isCool;
        }
    }

    @Test
    public void stateObjExample() {
        String name = "Robinson";
        int number = 42;

        Supplier<String> slowNameSupplier = () -> {
            try {
                long twoSeconds = 2 * 1000;
                Thread.sleep(twoSeconds);
            } catch (InterruptedException e) {
                // Hijinks occurred, but not important to the test.
                e.printStackTrace();
                return "hijinks!";
            }
            return name;
        };

        ConstantBaseballPlayer jackie = ConstantBaseballPlayer.of(slowNameSupplier, 42);

        // A "warm-up" get.
        Instant beforeSlowGet = Instant.now();
        assertEquals(name, jackie.getName());
        assertSlowerThan(Duration.ofSeconds(2), beforeSlowGet, Instant.now());

        // "Warmed-up" gets
        Instant beforeFastGet = Instant.now();
        assertEquals(name, jackie.getName());
        assertEquals(number, jackie.getNumber());
        assertTrue(jackie.isCool());
        assertFasterThan(Duration.ofMillis(50), beforeFastGet, Instant.now());
    }

    private void assertSlowerThan(Duration expected, Instant start, Instant end) {
        _assertDuration(expected, start, end, "slower", (exp, elapsed) -> exp < elapsed);
    }

    private void assertFasterThan(Duration expected, Instant start, Instant end) {
        _assertDuration(expected, start, end, "faster", (exp, elapsed) -> exp > elapsed);
    }

    private void _assertDuration(Duration duration, Instant start, Instant end, String description, BiPredicate<Long, Long> comparator) {
        Duration elapsed = Duration.between(start, end);
        Supplier<String> getErrorMsg = () -> String.format("Expected elapsed time %s than [%s].\nStart:   %s\nEnd:     %s\nElapsed: %s", description, duration, start, end, elapsed);
        assertTrue(comparator.test(duration.toNanos(), elapsed.toNanos()), getErrorMsg);
    }
}
