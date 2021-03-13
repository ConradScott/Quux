package uk.me.conradscott.quux;

import com.google.errorprone.annotations.Immutable;

/**
 * Does Java have a unit type ('a type with only one value'). Well, yes, it does: Void.
 * It is though somewhat unfortunate that the only one value it has is 'null', which fails to interact well with
 * {@code @Nonnull}.
 * So, here's another Java unit type, which (assuming @NonNullByDefault) has only one value, Unit.UNIT.
 */
@SuppressWarnings({ "Singleton", "InstantiationOfUtilityClass", "unused" })
@Immutable final class Unit
{
    static final Unit UNIT = new Unit();

    private Unit() {}
}
