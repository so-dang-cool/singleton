package so.dang.cool.singleton;

import java.util.function.Supplier;

/**
 * A single, unchanging reference.
 * <p>
 * A {@code Singleton} can be thought of as a single value cache. Singletons
 * are best suited to holding references to instances that take some time or
 * effort to compute, but can be reused indefinitely once they have been
 * calculated.
 * <p>
 * Here we provide an eager implementation that takes an instance, and a lazy
 * implementation that takes a {@link Supplier}. A rule of thumb is to use
 * {@code Singleton.eager(...)} if you will always need the value, or if you
 * don't want to defer to the first call. Use the {@code Singleton.lazy(...)}
 * implementation if you may not need to calculate the value at all, or when
 * you want to defer to the first call.
 *
 * @since 1
 */
public abstract class Singleton<E> {

    // Private constructor here prevents subclassing outside this context.
    private Singleton() { }

    /**
     * Get the singleton instance.
     *
     * @since 1
     */
    public abstract E get();

    /**
     * Produce an eager {@link Singleton}.
     * <p>
     * The exact instance passed will always be returned by {@link #get()}.
     *
     * @since 1
     */
    public static <E> Singleton<E> eager(E instance) {
        return new Eager<>(instance);
    }

    /**
     * Produce an eager {@link Singleton}.
     * <p>
     * The supplier will be immediately invoked once, and the first result
     * always returned by {@link #get()}.
     *
     * @since 2
     */
    public static <E> Singleton<E> eager(Supplier<E> supplier) {
        return new Eager<>(supplier.get());
    }

    /**
     * Produce a lazy {@link Singleton}.
     * <p>
     * The first time {@link #get()} is invoked, the supplier will be called
     * exactly once for an instance. All subsequent calls to  {@link #get()}
     * will return that initial instance.
     *
     * @since 1
     */
    public static <E> Singleton<E> lazy(Supplier<E> supplier) {
        return new Lazy<>(supplier);
    }

    /**
     * An eager {@link Singleton}.
     * <p>
     * The exact instance passed to {@code Singleton.eager(...)} will always be
     * returned by {@link #get()}.
     */
    static final class Eager<E> extends Singleton<E> {
        private E instance;

        private Eager(E instance) {
            this.instance = instance;
        }

        public E get() {
            return instance;
        }
    }

    /**
     * A lazy {@link Singleton}.
     * <p>
     * The first time its {@link #get()} is invoked, the {@link Supplier}
     * passed to {@code Singleton.lazy(...)} will be called exactly once for an
     * instance. All subsequent calls to {@link #get()} will return that initial
     * instance.
     */
    static final class Lazy<E> extends Singleton<E> {
        private Supplier<E> supplier;
        private boolean unresolved;
        private E instance;

        Lazy(Supplier<E> supplier) {
            this.supplier = supplier;
            this.unresolved = true;
        }

        public E get() {
            if (this.unresolved) {
                this.instance = this.supplier.get();
                this.unresolved = false;
            }
            return this.instance;
        }
    }
}
