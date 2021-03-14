package uk.me.conradscott.quux;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.ImmutableTypeParameter;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Immutable
@SuppressWarnings({ "unused", "ClassReferencesSubclass", "PublicMethodNotExposedInInterface", "WeakerAccess" })
public abstract class Quuxen<@ImmutableTypeParameter Q>
{
    /**
     * Sealed class.
     */
    private Quuxen()
    {
    }

    @SuppressWarnings("unchecked")
    public static <@ImmutableTypeParameter Q> Quuxen<Q> empty()
    {
        return (Quuxen<Q>) Quuxen.Valid.EMPTY;
    }

    public static <@ImmutableTypeParameter Q> Quuxen<Q> ofQuux(final Quux<Q> quux)
    {
        return quux.fold(Quuxen::ofInvalidQuux, Quuxen::ofValidQuux);
    }

    public static <@ImmutableTypeParameter Q> Quuxen<Q> ofInvalidQuux(final Quux.Invalid<Q> invalid)
    {
        return new Invalid<>(Lists.immutable.of(invalid));
    }

    public static <@ImmutableTypeParameter Q> Quuxen<Q> ofValidQuux(final Quux.Valid<Q> valid)
    {
        return new Valid<>(Lists.immutable.of(valid));
    }

    public static <@ImmutableTypeParameter Q> Quuxen<Q> ofQuuxes(final RichIterable<? extends Quux<Q>> quuxes)
    {
        return Quuxen.<Q>empty().withQuuxes(quuxes);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <@ImmutableTypeParameter Q> Quuxen<Q> ofQuuxes(final Quux<Q>... quuxes)
    {
        return Quuxen.<Q>empty().withQuuxes(quuxes);
    }

    public static <@ImmutableTypeParameter Q> Quuxen<Q> ofQuuxens(final RichIterable<? extends Quuxen<Q>> quuxens)
    {
        if (quuxens.size() == 1) {
            return quuxens.getOnly();
        }

        return Quuxen.<Q>empty().withQuuxens(quuxens);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <@ImmutableTypeParameter Q> Quuxen<Q> ofQuuxens(final Quuxen<Q>... quuxens)
    {
        if (quuxens.length == 1) {
            return quuxens[0];
        }

        return Quuxen.<Q>empty().withQuuxens(quuxens);
    }

    public static <@ImmutableTypeParameter Q> Quuxen<Q> ofQuuxItems(final RichIterable<? extends QuuxItem<Q, ?>> items)
    {
        if (items.size() == 1) {
            return items.getOnly().getQuuxen();
        }

        return Quuxen.<Q>empty().withQuuxItems(items);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <@ImmutableTypeParameter Q> Quuxen<Q> ofQuuxItems(final QuuxItem<Q, ?>... items)
    {
        if (items.length == 1) {
            return items[0].getQuuxen();
        }

        return Quuxen.<Q>empty().withQuuxItems(items);
    }

    public abstract ImmutableList<Quux<Q>> getQuuxes();

    public abstract boolean isEmpty();

    public boolean notEmpty()
    {
        return !isEmpty();
    }

    public boolean isInvalid()
    {
        return !isValid();
    }

    public boolean isValid()
    {
        return !isInvalid();
    }

    public abstract Quuxen<Q> withQuux(Quux<Q> quux);

    public abstract Quuxen<Q> withInvalidQuux(Quux.Invalid<Q> invalid);

    public abstract Quuxen<Q> withValidQuux(Quux.Valid<Q> valid);

    public abstract Quuxen<Q> withQuuxen(Quuxen<Q> quuxen);

    public abstract Quuxen<Q> withInvalidQuuxen(Invalid<Q> invalid);

    public abstract Quuxen<Q> withValidQuuxen(Valid<Q> valid);

    public abstract Quuxen<Q> withQuuxes(RichIterable<? extends Quux<Q>> quuxes);

    @SafeVarargs
    @SuppressWarnings("varargs")
    public final Quuxen<Q> withQuuxes(final Quux<Q>... quuxes)
    {
        return withQuuxes(Lists.immutable.of(quuxes));
    }

    public final Quuxen<Q> withQuuxens(final RichIterable<? extends Quuxen<Q>> quuxens)
    {
        if (quuxens.allSatisfy(Quuxen::isEmpty)) {
            return this;
        }

        final MutableList<Quux<Q>> quuxes = Lists.mutable.empty();

        quuxens.forEach(quuxen -> quuxes.addAllIterable(quuxen.getQuuxes()));

        return withQuuxes(quuxes);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public final Quuxen<Q> withQuuxens(final Quuxen<Q>... quuxens)
    {
        return withQuuxens(Lists.immutable.of(quuxens));
    }

    public final Quuxen<Q> withQuuxItems(final RichIterable<? extends QuuxItem<Q, ?>> items)
    {
        if (items.isEmpty()) {
            return this;
        }

        final MutableList<Quux<Q>> quuxes = Lists.mutable.empty();

        items.forEach(item -> quuxes.addAllIterable(item.getQuuxes()));

        return withQuuxes(quuxes);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public final Quuxen<Q> withQuuxItems(final QuuxItem<Q, ?>... items)
    {
        return withQuuxItems(Lists.immutable.of(items));
    }

    public abstract void ifInvalid(Consumer<? super Invalid<Q>> action);

    public abstract void ifValid(Consumer<? super Valid<Q>> action);

    public abstract <U> U fold(Function<? super Invalid<Q>, ? extends U> invalidMapper,
                               Function<? super Valid<Q>, ? extends U> validMapper);

    public abstract Quuxen<Q> flatMap(Supplier<? extends Quuxen<Q>> supplier);

    public abstract <T> QuuxItem<Q, T> mapToItem(Supplier<? extends T> supplier);

    public abstract <T> QuuxItem<Q, T> mapToQuuxItem(Supplier<? extends QuuxItem<Q, T>> supplier);

    @Immutable
    @SuppressWarnings({ "PublicMethodNotExposedInInterface", "unused", "PublicInnerClass" })
    public static final class Invalid<@ImmutableTypeParameter Q> extends Quuxen<Q>
    {
        @SuppressWarnings("Immutable") private final ImmutableList<Quux<Q>> quuxes;

        @SuppressWarnings("InstanceofConcreteClass")
        Invalid(final RichIterable<? extends Quux<Q>> quuxes)
        {
            assert quuxes.anySatisfy(quux -> quux instanceof Quux.Invalid);

            this.quuxes = Lists.immutable.ofAll(quuxes);
        }

        @Override
        public ImmutableList<Quux<Q>> getQuuxes()
        {
            return quuxes;
        }

        @Override
        public boolean isEmpty()
        {
            assert !quuxes.isEmpty();

            return false;
        }

        @Override
        public boolean isInvalid()
        {
            return true;
        }

        @Override
        public Invalid<Q> withQuux(final Quux<Q> quux)
        {
            return new Invalid<>(quuxes.newWith(quux));
        }

        @Override
        public Invalid<Q> withInvalidQuux(final Quux.Invalid<Q> invalid)
        {
            return withQuux(invalid);
        }

        @Override
        public Invalid<Q> withValidQuux(final Quux.Valid<Q> valid)
        {
            return withQuux(valid);
        }

        @Override
        public Invalid<Q> withQuuxen(final Quuxen<Q> quuxen)
        {
            return withQuuxes(quuxen.getQuuxes());
        }

        @Override
        public Invalid<Q> withInvalidQuuxen(final Invalid<Q> invalid)
        {
            return withQuuxen(invalid);
        }

        @Override
        public Invalid<Q> withValidQuuxen(final Valid<Q> valid)
        {
            return withQuuxen(valid);
        }

        @Override
        public Invalid<Q> withQuuxes(final RichIterable<? extends Quux<Q>> quuxes)
        {
            if (quuxes.isEmpty()) {
                return this;
            }

            return new Invalid<>(this.quuxes.newWithAll(quuxes));
        }

        @Override
        public void ifInvalid(final Consumer<? super Invalid<Q>> action)
        {
            action.accept(this);
        }

        @Override
        public void ifValid(final Consumer<? super Valid<Q>> action)
        { }

        @Override
        public <U> U fold(final Function<? super Invalid<Q>, ? extends U> invalidMapper,
                          final Function<? super Valid<Q>, ? extends U> validMapper)
        {
            return invalidMapper.apply(this);
        }

        @Override
        public Invalid<Q> flatMap(final Supplier<? extends Quuxen<Q>> supplier)
        {
            return this;
        }

        @Override
        public <T> QuuxItem.Invalid<Q, T> mapToItem(final Supplier<? extends T> supplier)
        {
            return new QuuxItem.Invalid<>(this);
        }

        @Override
        public <T> QuuxItem.Invalid<Q, T> mapToQuuxItem(final Supplier<? extends QuuxItem<Q, T>> supplier)
        {
            return new QuuxItem.Invalid<>(this);
        }
    }

    @Immutable
    @SuppressWarnings({ "PublicMethodNotExposedInInterface", "InstanceofConcreteClass", "unused", "PublicInnerClass" })
    public static final class Valid<@ImmutableTypeParameter Q> extends Quuxen<Q>
    {
        static final Valid<Unit> EMPTY = new Valid<>(Lists.immutable.empty());

        @SuppressWarnings("Immutable") private final ImmutableList<Quux<Q>> quuxes;

        Valid(final RichIterable<? extends Quux<Q>> quuxes)
        {
            assert quuxes.allSatisfy(Quux::isValid);

            this.quuxes = Lists.immutable.ofAll(quuxes);
        }

        @Override
        public ImmutableList<Quux<Q>> getQuuxes()
        {
            return quuxes;
        }

        @Override
        public boolean isEmpty()
        {
            return quuxes.isEmpty();
        }

        @Override
        public boolean isValid()
        {
            return true;
        }

        @Override
        public Quuxen<Q> withQuux(final Quux<Q> quux)
        {
            return quux.fold(this::withInvalidQuux, this::withValidQuux);
        }

        @Override
        public Invalid<Q> withInvalidQuux(final Quux.Invalid<Q> invalid)
        {
            return new Invalid<>(quuxes.newWith(invalid));
        }

        @Override
        public Valid<Q> withValidQuux(final Quux.Valid<Q> valid)
        {
            return new Valid<>(quuxes.newWith(valid));
        }

        @Override
        public Quuxen<Q> withQuuxen(final Quuxen<Q> quuxen)
        {
            return quuxen.fold(this::withInvalidQuuxen, this::withValidQuuxen);
        }

        @Override
        public Invalid<Q> withInvalidQuuxen(final Invalid<Q> invalid)
        {
            return new Invalid<>(quuxes.newWithAll(invalid.getQuuxes()));
        }

        @Override
        public Valid<Q> withValidQuuxen(final Valid<Q> valid)
        {
            return new Valid<>(quuxes.newWithAll(valid.quuxes));
        }

        @Override
        public Quuxen<Q> withQuuxes(final RichIterable<? extends Quux<Q>> quuxes)
        {
            if (quuxes.isEmpty()) {
                return this;
            }

            return quuxes.anySatisfy(quux -> quux instanceof Quux.Invalid)
                   ? new Invalid<>(this.quuxes.newWithAll(quuxes))
                   : new Valid<>(this.quuxes.newWithAll(quuxes));
        }

        @Override
        public void ifInvalid(final Consumer<? super Invalid<Q>> action)
        { }

        @Override
        public void ifValid(final Consumer<? super Valid<Q>> action)
        {
            action.accept(this);
        }

        @Override
        public <U> U fold(final Function<? super Invalid<Q>, ? extends U> invalidMapper,
                          final Function<? super Valid<Q>, ? extends U> validMapper)
        {
            return validMapper.apply(this);
        }

        @Override
        public Quuxen<Q> flatMap(final Supplier<? extends Quuxen<Q>> supplier)
        {
            return withQuuxen(supplier.get());
        }

        @Override
        public <T> QuuxItem.Valid<Q, T> mapToItem(final Supplier<? extends T> supplier)
        {
            return new QuuxItem.Valid<>(this, supplier.get());
        }

        @Override
        public <T> QuuxItem<Q, T> mapToQuuxItem(final Supplier<? extends QuuxItem<Q, T>> supplier)
        {
            return supplier.get()
                           .fold(invalid -> new QuuxItem.Invalid<>(withInvalidQuuxen(invalid.getQuuxen())),
                                 valid -> new QuuxItem.Valid<>(withValidQuuxen(valid.getQuuxen()),
                                                               valid.getItem()));
        }
    }
}
