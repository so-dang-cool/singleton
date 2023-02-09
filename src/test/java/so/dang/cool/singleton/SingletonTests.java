package so.dang.cool.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
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
    void eagerGetsSupplied() {
        String first = "bros";
        String second = "yoshis";
        LinkedList<String> list = new LinkedList<>();
        list.push(first);
        list.push(second);

        Singleton<String> eager = Singleton.eager(list::pop);

        assertEquals(1, list.size(), "(Already popped) List retains one element.");
        assertEquals(second, eager.get(), "It pops one off the list.");
        assertEquals(1, list.size(), "Not popped again.");
        assertEquals(second, eager.get(), "It only pops once.");
        assertEquals(1, list.size(), "Not popped again.");
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
        List<String> instance = Arrays.asList("waluigi");

        Singleton<List<String>> lazy = Singleton.lazy(() -> Arrays.asList("waluigi"));

        assertEquals(instance, lazy.get());
        assertNotIdentical(instance, lazy.get());
        assertIdentical(lazy.get(), lazy.get());
    }

    @Test
    void allowedNullCases() {
        String nullInstance = null;
        Supplier<String> nonNullSupplierOfNull = () -> null;

        assertNull(Singleton.eager(nullInstance).get());
        assertNull(Singleton.eager(nonNullSupplierOfNull).get());
        assertNull(Singleton.lazy(nonNullSupplierOfNull).get());
    }

    @Test
    void disallowedNullCases() {
        Supplier<String> nullSupplier = null;

        assertThrows(NullPointerException.class, () -> Singleton.eager(nullSupplier));
        assertThrows(NullPointerException.class, () -> Singleton.lazy(nullSupplier));
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
