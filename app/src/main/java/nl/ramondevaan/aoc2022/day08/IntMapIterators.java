package nl.ramondevaan.aoc2022.day08;

import nl.ramondevaan.aoc2022.util.IntMap;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public final class IntMapIterators {

  public static IntStream valuesUp(final IntMap map, final int row, final int column) {
    return IntStream.rangeClosed(1, row).map(i -> map.valueAt(row - i, column));
  }

  public static IntStream valuesDown(final IntMap map, final int row, final int column) {
    return IntStream.range(row + 1, map.rows()).map(r -> map.valueAt(r, column));
  }

  public static IntStream valuesLeft(final IntMap map, final int row, final int column) {
    return IntStream.rangeClosed(1, column).map(i -> map.valueAt(row, column - i));
  }

  public static IntStream valuesRight(final IntMap map, final int row, final int column) {
    return IntStream.range(column + 1, map.columns()).map(c -> map.valueAt(row, c));
  }

  public static LongStream byRow(final IntMap map, final int row, final IntMapEntryLongFunction function) {
    return IntStream.range(0, map.columns()).mapToLong(column -> function.apply(row, column, map.valueAt(row, column)));
  }

  public static void byRow(final IntMap map, int row, final IntMapEntryConsumer consumer) {
    for (int column = 0; column < map.columns(); column++) {
      consumer.accept(row, column, map.valueAt(row, column));
    }
  }

  public static void byRowReversed(final IntMap map, final int row, final IntMapEntryConsumer consumer) {
    for (int column = map.columns() - 1; column >= 0; column--) {
      consumer.accept(row, column, map.valueAt(row, column));
    }
  }

  public static void byColumn(final IntMap map, final int column, final IntMapEntryConsumer consumer) {
    for (int row = 0; row < map.rows(); row++) {
      consumer.accept(row, column, map.valueAt(row, column));
    }
  }

  public static void byColumnReversed(final IntMap map, final int column, final IntMapEntryConsumer consumer) {
    for (int row = map.rows() - 1; row >= 0; row--) {
      consumer.accept(row, column, map.valueAt(row, column));
    }
  }
}
