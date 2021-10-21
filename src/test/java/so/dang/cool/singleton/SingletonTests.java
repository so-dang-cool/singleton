package so.dang.cool.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public class SingletonTests {
    @Test
    void eagerGets() {
        String instance = "it's-a-meee";

        Singleton<String> eager = Singleton.eager(instance);

        assertEquals(instance, eager.get());
        assertIdentical(instance, eager.get());
    }

    @Test
    void lazyGets() {
        String instance = "wario";

        Singleton<String> lazy = Singleton.lazy(() -> instance);

        assertEquals(instance, lazy.get());
        assertIdentical(instance, lazy.get());
    }

    @Test
    void lazyGetsDynamic() {
        List<String> instance = List.of("waluigi");

        Singleton<List<String>> lazy = Singleton.lazy(() -> List.of("waluigi"));

        assertEquals(instance, lazy.get());
        assertNotIdentical(instance, lazy.get());
        assertIdentical(lazy.get(), lazy.get());
    }

    private <E> void assertIdentical(E expected, E actual) {
        Supplier<String> message = () -> String.format("Expected exactly same instance.\nexpected: %s\nactual: %s", expected, actual);
        assertTrue(() -> expected == actual, message);
    }

    private <E> void assertNotIdentical(E expected, E actual) {
        Supplier<String> message = () -> String.format("Expected different instance.\nexpected: %s\nactual: %s", expected, actual);
        assertFalse(() -> expected == actual, message);
    }
}
