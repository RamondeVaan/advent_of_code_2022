package nl.ramondevaan.aoc2022.util;

@FunctionalInterface
public interface LongBiPredicate {
    boolean test(long left, long right);
}
