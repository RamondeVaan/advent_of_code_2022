package nl.ramondevaan.aoc2022.day08;

@FunctionalInterface
public interface IntMapEntryConsumer {

  void accept(final int row, final int column, final int value);
}
