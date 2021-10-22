/**
 * A {@link Singleton} is a class that holds a single reference throughout its
 * lifetime.
 * <p>
 * It can be thought of as a single value cache. Singletons are best suited to
 * holding references to instances that take some time or effort to compute,
 * but can be reused indefinitely once they have been calculated.
 * <p>
 * Here we provide an eager implementation that takes an instance, and a lazy
 * implementation that takes a {@link java.util.function.Supplier}. A rule of
 * thumb is to use {@code Singleton.eager(...)} if you will always need the
 * value, or if you don't want to defer it to the first call. Use the
 * {@code Singleton.lazy(...)} implementation if you may not need to calculate
 * the value at all, or when you want to defer to the first call.
 * <p>
 * This is certainly not the only Singleton implementation. The idea was
 * formalized in the book <i>Design Patterns: Elements of Reusable
 * Object-Oriented Software"</i> in 1994, and has been implemented at least
 * twice by every programmer and their pet cat.
 * <p>
 * If you already have mechanisms that do the same thing, use those instead.
 * Examples include dependency injection frameworks (Dagger, Guice, etc.), the
 * {@code object} keyword present in both Kotlin and Scala, the
 * {@code @Singleton} annotation in Groovy, and many more.
 * <p>
 * In its favor, this is a very lightweight implementation in pure Java. It's
 * most suitable for minimalistic, "plain old Java" applications.
 *
 * @author J.R. Hill 2021-10-21
 */
package so.dang.cool.singleton;
