package nl.ramondevaan.aoc2022.day08;

@FunctionalInterface
public interface IntMapEntryLongFunction {

  long apply(final int row, final int column, final int value);
}
