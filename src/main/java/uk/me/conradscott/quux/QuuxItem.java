package uk.me.conradscott.quux;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.ImmutableTypeParameter;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.function.Function3;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

@Immutable(containerOf = "T")
@SuppressWarnings({ "PublicMethodNotExposedInInterface", "unused", "WeakerAccess" })
public abstract class QuuxItem<@ImmutableTypeParameter Q, T>
{
    /**
     * Sealed class.
     */
    private QuuxItem()
    {
    }

    @SuppressWarnings({ "unchecked", "CastToConcreteClass" })
    public static <@ImmutableTypeParameter Q, T> QuuxItem<Q, T> of(final T item)
    {
        return new Valid<>((Quuxen.Valid<Q>) Quuxen.Valid.EMPTY, item);
    }

    public static <@ImmutableTypeParameter Q, T> QuuxItem<Q, T> of(final Quuxen<Q> quuxen, final T item)
    {
        return quuxen.fold(invalid -> ofInvalidQuuxen(invalid), valid -> new Valid<>(valid, item));
    }

    public static <@ImmutableTypeParameter Q, T> Invalid<Q, T> ofInvalidQuux(final Quux.Invalid<Q> invalid)
    {
        return new Invalid<>(new Quuxen.Invalid<>(Lists.immutable.of(invalid)));
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <@ImmutableTypeParameter Q, T> Invalid<Q, T> ofInvalidQuuxes(final Quux.Invalid<Q>... invalids)
    {
        return new Invalid<>(new Quuxen.Invalid<>(Lists.immutable.of(invalids)));
    }

    public static <@ImmutableTypeParameter Q, T> Invalid<Q, T> ofInvalidQuuxen(final Quuxen.Invalid<Q> quuxen)
    {
        return new Invalid<>(quuxen);
    }

    public static <@ImmutableTypeParameter Q, T1, T2, U> QuuxItem<Q, U> combine(
        final QuuxItem<Q, ? extends T1> first,
        final QuuxItem<Q, ? extends T2> second,
        final BiFunction<? super T1, ? super T2, ? extends U> combiner)
    {
        return first.mapWith(combiner, second);
    }

    public static <@ImmutableTypeParameter Q, T1, T2, T3, U> QuuxItem<Q, U> combine(
        final QuuxItem<Q, ? extends T1> first,
        final QuuxItem<Q, ? extends T2> second,
        final QuuxItem<Q, ? extends T3> third,
        final Function3<? super T1, ? super T2, ? super T3, ? extends U> combiner)
    {
        return first.mapWith(combiner, second, third);
    }

    public static <@ImmutableTypeParameter Q, T1, T2, U> QuuxItem<Q, U> flatCombine(
        final QuuxItem<Q, ? extends T1> first,
        final QuuxItem<Q, ? extends T2> second,
        final BiFunction<? super T1, ? super T2, ? extends QuuxItem<Q, ? extends U>> combiner)
    {
        return first.flatMapWith(combiner, second);
    }

    public static <@ImmutableTypeParameter Q, T1, T2, T3, U> QuuxItem<Q, U> flatCombine(
        final QuuxItem<Q, ? extends T1> first,
        final QuuxItem<Q, ? extends T2> second,
        final QuuxItem<Q, ? extends T3> third,
        final Function3<? super T1, ? super T2, ? super T3, ? extends QuuxItem<Q, ? extends U>> combiner)
    {
        return first.flatMapWith(combiner, second, third);
    }

    public static <@ImmutableTypeParameter Q, T1, T2> Quuxen<Q> combineToQuuxen(
        final QuuxItem<Q, ? extends T1> first,
        final QuuxItem<Q, ? extends T2> second,
        final BiFunction<? super T1, ? super T2, ? extends Quuxen<Q>> combiner)
    {
        return first.mapToQuuxenWith(combiner, second);
    }

    public static <@ImmutableTypeParameter Q, T1, T2, T3> Quuxen<Q> combineToQuuxen(
        final QuuxItem<Q, ? extends T1> first,
        final QuuxItem<Q, ? extends T2> second,
        final QuuxItem<Q, ? extends T3> third,
        final Function3<? super T1, ? super T2, ? super T3, ? extends Quuxen<Q>> combiner)
    {
        return first.mapToQuuxenWith(combiner, second, third);
    }

    public abstract Quuxen<Q> getQuuxen();

    public abstract ImmutableList<? extends Quux<Q>> getQuuxes();

    public boolean isInvalid()
    {
        return !isValid();
    }

    public boolean isValid()
    {
        return !isInvalid();
    }

    public abstract QuuxItem<Q, T> withQuux(Quux<Q> quux);

    public abstract Invalid<Q, T> withInvalidQuux(Quux.Invalid<Q> invalid);

    public abstract QuuxItem<Q, T> withValidQuux(Quux.Valid<Q> valid);

    public abstract QuuxItem<Q, T> withQuuxen(Quuxen<Q> quuxen);

    public abstract Invalid<Q, T> withInvalidQuuxen(Quuxen.Invalid<Q> invalid);

    public abstract QuuxItem<Q, T> withValidQuuxen(Quuxen.Valid<Q> valid);

    public abstract QuuxItem<Q, T> withQuuxes(RichIterable<? extends Quux<Q>> quuxes);

    public abstract QuuxItem<Q, T> withQuuxens(final RichIterable<? extends Quuxen<Q>> quuxens);

    public abstract void ifInvalid(Consumer<? super Invalid<Q, T>> action);

    public abstract void ifValid(Consumer<? super Valid<Q, T>> action);

    public final <U> U apply(final Function<? super QuuxItem<Q, T>, ? extends U> function)
    {
        return function.apply(this);
    }

    public abstract <U> U fold(Function<? super Invalid<Q, T>, ? extends U> invalidMapper,
                               Function<? super Valid<Q, T>, ? extends U> validMapper);

    public abstract <U> QuuxItem<Q, U> map(Function<? super T, ? extends U> mapper);

    public abstract <T1, U> QuuxItem<Q, U> mapWith(BiFunction<? super T, ? super T1, ? extends U> mapper,
                                                   QuuxItem<Q, ? extends T1> first);

    public abstract <T1, T2, U> QuuxItem<Q, U> mapWith(Function3<? super T, ? super T1, ? super T2, ? extends U> mapper,
                                                       QuuxItem<Q, ? extends T1> first,
                                                       QuuxItem<Q, ? extends T2> second);

    public abstract <U> QuuxItem<Q, U> flatMap(Function<? super T, ? extends QuuxItem<Q, ? extends U>> mapper);

    public abstract <T1, U> QuuxItem<Q, U> flatMapWith(BiFunction<? super T, ? super T1, ? extends QuuxItem<Q, ? extends U>> mapper,
                                                       QuuxItem<Q, ? extends T1> first);

    public abstract <T1, T2, U> QuuxItem<Q, U> flatMapWith(Function3<? super T, ? super T1, ? super T2, ? extends QuuxItem<Q, ? extends U>> mapper,
                                                           QuuxItem<Q, ? extends T1> first,
                                                           QuuxItem<Q, ? extends T2> second);

    public abstract QuuxItem<Q, T> mapDown(Function<? super T, ? extends Quuxen<Q>> mapper);

    public abstract <T1> QuuxItem<Q, T> mapDownWith(BiFunction<? super T, ? super T1, ? extends Quuxen<Q>> mapper,
                                                    QuuxItem<Q, ? extends T1> first);

    public abstract <T1, T2> QuuxItem<Q, T> mapDownWith(Function3<? super T, ? super T1, ? super T2, ? extends Quuxen<Q>> mapper,
                                                        QuuxItem<Q, ? extends T1> first,
                                                        QuuxItem<Q, ? extends T2> second);

    public abstract Quuxen<Q> mapToQuuxen(Function<? super T, ? extends Quuxen<Q>> mapper);

    public abstract <T1> Quuxen<Q> mapToQuuxenWith(BiFunction<? super T, ? super T1, ? extends Quuxen<Q>> mapper,
                                                   QuuxItem<Q, ? extends T1> first);

    public abstract <T1, T2> Quuxen<Q> mapToQuuxenWith(Function3<? super T, ? super T1, ? super T2, ? extends Quuxen<Q>> mapper,
                                                       QuuxItem<Q, ? extends T1> first,
                                                       QuuxItem<Q, ? extends T2> second);

    @Immutable(containerOf = "T")
    @SuppressWarnings({ "CastToConcreteClass", "PublicMethodNotExposedInInterface", "unused", "PublicInnerClass" })
    public static final class Invalid<@ImmutableTypeParameter Q, T> extends QuuxItem<Q, T>
    {
        private final Quuxen.Invalid<Q> quuxen;

        Invalid(final Quuxen.Invalid<Q> quuxen)
        {
            this.quuxen = quuxen;
        }

        @Override
        public Quuxen.Invalid<Q> getQuuxen()
        {
            return quuxen;
        }

        @Override
        public ImmutableList<Quux<Q>> getQuuxes()
        {
            return quuxen.getQuuxes();
        }

        @Override
        public boolean isInvalid()
        {
            return true;
        }

        @Override
        public Invalid<Q, T> withQuux(final Quux<Q> quux)
        {
            return new Invalid<>(quuxen.withQuux(quux));
        }

        @Override
        public Invalid<Q, T> withInvalidQuux(final Quux.Invalid<Q> invalid)
        {
            return withQuux(invalid);
        }

        @Override
        public Invalid<Q, T> withValidQuux(final Quux.Valid<Q> valid)
        {
            return withQuux(valid);
        }

        @Override
        public Invalid<Q, T> withQuuxen(final Quuxen<Q> quuxen)
        {
            if (quuxen.isEmpty()) {
                return this;
            }

            return new Invalid<>(this.quuxen.withQuuxen(quuxen));
        }

        @Override
        public Invalid<Q, T> withInvalidQuuxen(final Quuxen.Invalid<Q> invalid)
        {
            return withQuuxen(invalid);
        }

        @Override
        public Invalid<Q, T> withValidQuuxen(final Quuxen.Valid<Q> valid)
        {
            return withQuuxen(valid);
        }

        @Override
        public Invalid<Q, T> withQuuxes(final RichIterable<? extends Quux<Q>> quuxes)
        {
            if (quuxes.isEmpty()) {
                return this;
            }

            return new Invalid<>(quuxen.withQuuxes(quuxes));
        }

        @Override
        public Invalid<Q, T> withQuuxens(final RichIterable<? extends Quuxen<Q>> quuxens)
        {
            if (quuxens.isEmpty() || quuxens.allSatisfy(Quuxen::isEmpty)) {
                return this;
            }

            return new Invalid<>(quuxen.withQuuxens(quuxens));
        }

        @Override
        public void ifInvalid(final Consumer<? super Invalid<Q, T>> action)
        {
            action.accept(this);
        }

        @Override
        public void ifValid(final Consumer<? super Valid<Q, T>> action)
        { }

        @Override
        public <U> U fold(final Function<? super Invalid<Q, T>, ? extends U> invalidMapper,
                          final Function<? super Valid<Q, T>, ? extends U> validMapper)
        {
            return invalidMapper.apply(this);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Invalid<Q, U> map(final Function<? super T, ? extends U> mapper)
        {
            return (Invalid<Q, U>) this;
        }

        @Override
        public <T1, U> Invalid<Q, U> mapWith(final BiFunction<? super T, ? super T1, ? extends U> mapper,
                                             final QuuxItem<Q, ? extends T1> first)
        {
            return combineToInvalid(first.getQuuxen());
        }

        @Override
        public <T1, T2, U> Invalid<Q, U> mapWith(final Function3<? super T, ? super T1, ? super T2, ? extends U> mapper,
                                                 final QuuxItem<Q, ? extends T1> first,
                                                 final QuuxItem<Q, ? extends T2> second)
        {
            return combineToInvalid(first.getQuuxen(), second.getQuuxen());
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Invalid<Q, U> flatMap(final Function<? super T, ? extends QuuxItem<Q, ? extends U>> mapper)
        {
            return (Invalid<Q, U>) this;
        }

        @Override
        public <T1, U> Invalid<Q, U> flatMapWith(final BiFunction<? super T, ? super T1, ? extends QuuxItem<Q, ? extends U>> mapper,
                                                 final QuuxItem<Q, ? extends T1> first)
        {
            return combineToInvalid(first.getQuuxen());
        }

        @Override
        public <T1, T2, U> Invalid<Q, U> flatMapWith(final Function3<? super T, ? super T1, ? super T2, ? extends QuuxItem<Q, ? extends U>> mapper,
                                                     final QuuxItem<Q, ? extends T1> first,
                                                     final QuuxItem<Q, ? extends T2> second)
        {
            return combineToInvalid(first.getQuuxen(), second.getQuuxen());
        }

        @Override
        public Invalid<Q, T> mapDown(final Function<? super T, ? extends Quuxen<Q>> mapper)
        {
            return this;
        }

        @Override
        public <T1> Invalid<Q, T> mapDownWith(final BiFunction<? super T, ? super T1, ? extends Quuxen<Q>> mapper,
                                              final QuuxItem<Q, ? extends T1> first)
        {
            return combineToInvalid(first.getQuuxen());
        }

        @Override
        public <T1, T2> Invalid<Q, T> mapDownWith(final Function3<? super T, ? super T1, ? super T2, ? extends Quuxen<Q>> mapper,
                                                  final QuuxItem<Q, ? extends T1> first,
                                                  final QuuxItem<Q, ? extends T2> second)
        {
            return combineToInvalid(first.getQuuxen(), second.getQuuxen());
        }

        @Override
        public Quuxen.Invalid<Q> mapToQuuxen(final Function<? super T, ? extends Quuxen<Q>> mapper)
        {
            return quuxen;
        }

        @Override
        public <T1> Quuxen.Invalid<Q> mapToQuuxenWith(final BiFunction<? super T, ? super T1, ? extends Quuxen<Q>> mapper,
                                                      final QuuxItem<Q, ? extends T1> first)
        {
            return combineToInvalidQuuxen(first.getQuuxen());
        }

        @Override
        public <T1, T2> Quuxen.Invalid<Q> mapToQuuxenWith(final Function3<? super T, ? super T1, ? super T2, ? extends Quuxen<Q>> mapper,
                                                          final QuuxItem<Q, ? extends T1> first,
                                                          final QuuxItem<Q, ? extends T2> second)
        {
            return combineToInvalidQuuxen(first.getQuuxen(), second.getQuuxen());
        }

        @SuppressWarnings("unchecked")
        private <U> Invalid<Q, U> combineToInvalid(final Quuxen<Q> quuxen)
        {
            if (quuxen.isEmpty()) {
                return (Invalid<Q, U>) this;
            }

            return new Invalid<>(this.quuxen.withQuuxen(quuxen));
        }

        @SuppressWarnings("unchecked")
        private <U> Invalid<Q, U> combineToInvalid(final Quuxen<Q> quuxen, final Quuxen<Q> quuxen2)
        {
            if (quuxen.isEmpty() && quuxen2.isEmpty()) {
                return (Invalid<Q, U>) this;
            }

            return new Invalid<>(this.quuxen.withQuuxen(quuxen).withQuuxen(quuxen2));
        }

        private Quuxen.Invalid<Q> combineToInvalidQuuxen(final Quuxen<Q> quuxen)
        {
            if (quuxen.isEmpty()) {
                return this.quuxen;
            }

            return this.quuxen.withQuuxen(quuxen);
        }

        private Quuxen.Invalid<Q> combineToInvalidQuuxen(final Quuxen<Q> quuxen, final Quuxen<Q> quuxen2)
        {
            if (quuxen.isEmpty() && quuxen2.isEmpty()) {
                return this.quuxen;
            }

            return this.quuxen.withQuuxen(quuxen).withQuuxen(quuxen2);
        }
    }

    @Immutable(containerOf = "T")
    @SuppressWarnings({ "PublicMethodNotExposedInInterface", "unused", "WeakerAccess", "PublicInnerClass" })
    public static final class Valid<@ImmutableTypeParameter Q, T> extends QuuxItem<Q, T>
    {
        private final Quuxen.Valid<Q> quuxen;
        private final T item;

        Valid(final Quuxen.Valid<Q> quuxen, final T item)
        {
            this.quuxen = quuxen;
            this.item = item;
        }

        public T getValue()
        {
            return item;
        }

        @Override
        public Quuxen.Valid<Q> getQuuxen()
        {
            return quuxen;
        }

        @Override
        public ImmutableList<Quux<Q>> getQuuxes()
        {
            return quuxen.getQuuxes();
        }

        @Override
        public boolean isValid()
        {
            return true;
        }

        @Override
        public QuuxItem<Q, T> withQuux(final Quux<Q> quux)
        {
            return quux.fold(this::withInvalidQuux, this::withValidQuux);
        }

        @Override
        public Invalid<Q, T> withInvalidQuux(final Quux.Invalid<Q> invalid)
        {
            return new Invalid<>(quuxen.withInvalidQuux(invalid));
        }

        @Override
        public Valid<Q, T> withValidQuux(final Quux.Valid<Q> valid)
        {
            return new Valid<>(quuxen.withValidQuux(valid), item);
        }

        @Override
        public QuuxItem<Q, T> withQuuxen(final Quuxen<Q> quuxen)
        {
            return quuxen.fold(this::withInvalidQuuxen, this::withValidQuuxen);
        }

        @Override
        public Invalid<Q, T> withInvalidQuuxen(final Quuxen.Invalid<Q> invalid)
        {
            return new Invalid<>(quuxen.withInvalidQuuxen(invalid));
        }

        @Override
        public Valid<Q, T> withValidQuuxen(final Quuxen.Valid<Q> valid)
        {
            if (valid.isEmpty()) {
                return this;
            }

            return new Valid<>(quuxen.withValidQuuxen(valid), item);
        }

        @Override
        public QuuxItem<Q, T> withQuuxes(final RichIterable<? extends Quux<Q>> quuxes)
        {
            if (quuxes.isEmpty()) {
                return this;
            }

            return quuxen.withQuuxes(quuxes).fold(Invalid::new, valid -> new Valid<>(valid, item));
        }

        @Override
        public QuuxItem<Q, T> withQuuxens(final RichIterable<? extends Quuxen<Q>> quuxens)
        {
            if (quuxens.isEmpty() || quuxens.allSatisfy(Quuxen::isEmpty)) {
                return this;
            }

            return quuxen.withQuuxens(quuxens).fold(Invalid::new, valid -> new Valid<>(valid, item));
        }

        public T getItem()
        {
            return item;
        }

        @Override
        public void ifInvalid(final Consumer<? super Invalid<Q, T>> action)
        { }

        @Override
        public void ifValid(final Consumer<? super Valid<Q, T>> action)
        {
            action.accept(this);
        }

        @Override
        public <U> U fold(final Function<? super Invalid<Q, T>, ? extends U> invalidMapper,
                          final Function<? super Valid<Q, T>, ? extends U> validMapper)
        {
            return validMapper.apply(this);
        }

        @Override
        public <U> QuuxItem<Q, U> map(final Function<? super T, ? extends U> mapper)
        {
            return new Valid<>(quuxen, mapper.apply(item));
        }

        @Override
        public <T1, U> QuuxItem<Q, U> mapWith(final BiFunction<? super T, ? super T1, ? extends U> mapper,
                                              final QuuxItem<Q, ? extends T1> first)
        {
            return first.fold(invalid -> new Invalid<>(quuxen.withInvalidQuuxen(invalid.getQuuxen())),
                              t1 -> new Valid<>(quuxen.withValidQuuxen(t1.quuxen),
                                                mapper.apply(item, t1.item)));
        }

        @Override
        public <T1, T2, U> QuuxItem<Q, U> mapWith(final Function3<? super T, ? super T1, ? super T2, ? extends U> mapper,
                                                  final QuuxItem<Q, ? extends T1> first,
                                                  final QuuxItem<Q, ? extends T2> second)
        {
            return first.fold(invalid -> new Invalid<>(quuxen.withInvalidQuuxen(invalid.getQuuxen())
                                                             .withQuuxen(second.getQuuxen())),
                              t1 -> second.fold(
                                  invalid -> new Invalid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                 .withInvalidQuuxen(invalid.getQuuxen())),
                                  t2 -> new Valid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                          .withValidQuuxen(t2.quuxen),
                                                    mapper.value(item, t1.item, t2.item))));
        }

        @Override
        public <U> QuuxItem<Q, U> flatMap(final Function<? super T, ? extends QuuxItem<Q, ? extends U>> mapper)
        {
            return mapper.apply(item)
                         .fold(invalid -> new Invalid<>(quuxen.withInvalidQuuxen(invalid.getQuuxen())),
                               valid -> new Valid<>(quuxen.withValidQuuxen(valid.quuxen), valid.item));
        }

        @Override
        public <T1, U> QuuxItem<Q, U> flatMapWith(final BiFunction<? super T, ? super T1, ? extends QuuxItem<Q, ? extends U>> mapper,
                                                  final QuuxItem<Q, ? extends T1> first)
        {
            return first.fold(invalid -> new Invalid<>(quuxen.withInvalidQuuxen(invalid.getQuuxen())),
                              t1 -> mapper.apply(item, t1.item)
                                          .fold(invalid -> new Invalid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                               .withInvalidQuuxen(invalid.getQuuxen())),
                                                valid -> new Valid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                           .withValidQuuxen(valid.quuxen),
                                                                     valid.item)));
        }

        @Override
        public <T1, T2, U> QuuxItem<Q, U> flatMapWith(final Function3<? super T, ? super T1, ? super T2, ? extends QuuxItem<Q, ? extends U>> mapper,
                                                      final QuuxItem<Q, ? extends T1> first,
                                                      final QuuxItem<Q, ? extends T2> second)
        {
            return first.fold(invalid -> new Invalid<>(quuxen.withInvalidQuuxen(invalid.getQuuxen())
                                                             .withQuuxen(second.getQuuxen())),
                              t1 -> second.fold(
                                  invalid -> new Invalid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                 .withInvalidQuuxen(invalid.getQuuxen())),
                                  t2 -> mapper.value(item, t1.item, t2.item)
                                              .fold(invalid -> new Invalid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                                   .withValidQuuxen(t2.quuxen)
                                                                                   .withInvalidQuuxen(invalid.getQuuxen())),
                                                    valid -> new Valid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                               .withValidQuuxen(t2.quuxen)
                                                                               .withValidQuuxen(valid.quuxen),
                                                                         valid.item))));
        }

        @Override
        public QuuxItem<Q, T> mapDown(final Function<? super T, ? extends Quuxen<Q>> mapper)
        {
            return mapper.apply(item)
                         .fold(invalid -> new Invalid<>(quuxen.withInvalidQuuxen(invalid)),
                               valid -> new Valid<>(quuxen.withValidQuuxen(valid), item));
        }

        @Override
        public <T1> QuuxItem<Q, T> mapDownWith(final BiFunction<? super T, ? super T1, ? extends Quuxen<Q>> mapper,
                                               final QuuxItem<Q, ? extends T1> first)
        {
            return first.fold(invalid -> new Invalid<>(quuxen.withInvalidQuuxen(invalid.getQuuxen())),
                              t1 -> mapper.apply(item, t1.item)
                                          .fold(invalid -> new Invalid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                               .withInvalidQuuxen(invalid)),
                                                valid -> new Valid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                           .withValidQuuxen(valid),
                                                                     item)));
        }

        @Override
        public <T1, T2> QuuxItem<Q, T> mapDownWith(final Function3<? super T, ? super T1, ? super T2, ? extends Quuxen<Q>> mapper,
                                                   final QuuxItem<Q, ? extends T1> first,
                                                   final QuuxItem<Q, ? extends T2> second)
        {
            return first.fold(invalid -> new Invalid<>(quuxen.withInvalidQuuxen(invalid.getQuuxen())
                                                             .withQuuxen(second.getQuuxen())),
                              t1 -> second.fold(
                                  invalid -> new Invalid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                 .withInvalidQuuxen(invalid.getQuuxen())),
                                  t2 -> mapper.value(item, t1.item, t2.item)
                                              .fold(invalid -> new Invalid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                                   .withValidQuuxen(t2.quuxen)
                                                                                   .withInvalidQuuxen(invalid)),
                                                    valid -> new Valid<>(quuxen.withValidQuuxen(t1.quuxen)
                                                                               .withValidQuuxen(t2.quuxen)
                                                                               .withValidQuuxen(valid),
                                                                         item))));
        }

        @Override
        public Quuxen<Q> mapToQuuxen(final Function<? super T, ? extends Quuxen<Q>> mapper)
        {
            return quuxen.withQuuxen(mapper.apply(item));
        }

        @Override
        public <T1> Quuxen<Q> mapToQuuxenWith(final BiFunction<? super T, ? super T1, ? extends Quuxen<Q>> mapper,
                                              final QuuxItem<Q, ? extends T1> first)
        {
            return first.fold(invalid -> quuxen.withInvalidQuuxen(invalid.getQuuxen()),
                              t1 -> quuxen.withValidQuuxen(t1.quuxen).withQuuxen(mapper.apply(item, t1.item)));
        }

        @Override
        public <T1, T2> Quuxen<Q> mapToQuuxenWith(final Function3<? super T, ? super T1, ? super T2, ? extends Quuxen<Q>> mapper,
                                                  final QuuxItem<Q, ? extends T1> first,
                                                  final QuuxItem<Q, ? extends T2> second)
        {
            return first.fold(invalid -> quuxen.withInvalidQuuxen(invalid.getQuuxen())
                                               .withQuuxen(second.getQuuxen()),
                              t1 -> second.fold(
                                  invalid -> quuxen.withValidQuuxen(t1.quuxen)
                                                   .withInvalidQuuxen(invalid.getQuuxen()),
                                  t2 -> quuxen.withValidQuuxen(t1.quuxen)
                                              .withValidQuuxen(t2.quuxen)
                                              .withQuuxen(mapper.value(item, t1.item, t2.item))));
        }
    }
}
