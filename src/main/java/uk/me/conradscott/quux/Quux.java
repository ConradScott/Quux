package uk.me.conradscott.quux;

import java.util.function.Consumer;
import java.util.function.Function;

import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.ImmutableTypeParameter;

@Immutable
@SuppressWarnings({ "ClassReferencesSubclass", "PublicMethodNotExposedInInterface", "unused" })
public abstract class Quux<@ImmutableTypeParameter Q>
{
    private final Q info;

    /**
     * Sealed class.
     */
    private Quux(final Q info)
    {
        this.info = info;
    }

    public static <@ImmutableTypeParameter Q> Invalid<Q> invalid(final Q info)
    {
        return new Invalid<>(info);
    }

    public static <@ImmutableTypeParameter Q> Valid<Q> valid(final Q info)
    {
        return new Valid<>(info);
    }

    public final Q getInfo()
    {
        return info;
    }

    public boolean isInvalid()
    {
        return !isValid();
    }

    public boolean isValid()
    {
        return !isInvalid();
    }

    public abstract void ifInvalid(Consumer<? super Invalid<Q>> action);

    public abstract void ifValid(Consumer<? super Valid<Q>> action);

    public abstract <U> U fold(Function<? super Invalid<Q>, ? extends U> invalidMapper,
                               Function<? super Valid<Q>, ? extends U> validMapper);

    @SuppressWarnings({ "PublicMethodNotExposedInInterface", "unused", "PublicInnerClass" })
    @Immutable
    public static final class Invalid<@ImmutableTypeParameter Q> extends Quux<Q>
    {
        Invalid(final Q info)
        {
            super(info);
        }

        @Override
        public boolean isInvalid()
        {
            return true;
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
    }

    @Immutable
    @SuppressWarnings({ "PublicMethodNotExposedInInterface", "unused", "PublicInnerClass" })
    public static final class Valid<@ImmutableTypeParameter Q> extends Quux<Q>
    {
        Valid(final Q info)
        {
            super(info);
        }

        @Override
        public boolean isValid()
        {
            return true;
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
    }
}
