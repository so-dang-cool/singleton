package so.dang.cool.singleton;

import java.util.function.Supplier;

/**
 * A Singleton is a class that holds a single reference throughout its
 * lifetime.
 *
 * It can be thought of as a single value cache. Singletons are best suited to
 * holding references to instances that take some time or effort to compute,
 * but can be reused indefinitely once they have been calculated.
 *
 * Here we provide an eager implementation that takes an instance, and a lazy
 * implementation that takes a Supplier. A rule of thumb is to use the eager
 * implementation if you will always need the value, or if you do not want to
 * defer it to the first call. Use the lazy implementation if you may not need
 * to calculate the value at all, or when it's appropriate to defer. 
 */
public abstract class Singleton<E> {

    // Private constructor here prevents subclassing outside this context.
    private Singleton() { }

    public abstract E get();

    /**
     * Produce an eager Singleton.
     *
     * The exact instance passed will always be returned by get().
     */
    public static <E> Singleton<E> eager(E instance) {
        return new Eager<>(instance);
    }

    /**
     * Produce a lazy Singleton. 
     *
     * The first time get() is invoked, the supplier will be called
     * exactly once for an instance. All subsequent calls to get()
     * will return that initial instance.
     */
    public static <E> Singleton<E> lazy(Supplier<E> supplier) {
        return new Lazy<>(supplier);
    }


    /**
     * An eager Singleton.
     *
     * The exact instance passed to Singleton.eager(instance) will always be
     * returned by get().
     */
    public static final class Eager<E> extends Singleton<E> {
        private E instance;

        private Eager(E instance) {
            this.instance = instance;
        }

        public E get() {
            return instance;
        }
    }

    /**
     * A lazy Singleton. 
     *
     * The first time get() is invoked, the supplier passed to
     * Singleton.lazy(supplier) will be called exactly once for an instance.
     * All subsequent calls to get() will return that initial instance.
     */
    public static final class Lazy<E> extends Singleton<E> {
        private Supplier<E> supplier;
        private boolean isSupplied;
        private E instance;

        Lazy(Supplier<E> supplier) {
            this.supplier = supplier;
            this.isSupplied = false;
        }

        public E get() {
            if (!this.isSupplied) {
                this.instance = this.supplier.get();
                this.isSupplied = true;
            }
            return this.instance;
        }
    }
}
