package nl.ramondevaan.aoc2022.day08;

import nl.ramondevaan.aoc2022.util.IntMap;
import nl.ramondevaan.aoc2022.util.IntMapParser;
import nl.ramondevaan.aoc2022.util.MutableIntMap;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static nl.ramondevaan.aoc2022.day08.IntMapIterators.*;


public class Day08 {

  private final IntMap map;

  public Day08(final List<String> lines) {
    final var parser = new IntMapParser();
    this.map = parser.parse(lines);
  }

  public long solve1() {
    final var visibilityMap = new MutableIntMap(map.rows(), map.columns());
    IntStream.range(0, map.rows()).forEach(row -> byRow(map, row, visibilitySetter(visibilityMap)));
    IntStream.range(0, map.rows()).forEach(row -> byRowReversed(map, row, visibilitySetter(visibilityMap)));
    IntStream.range(0, map.columns()).forEach(column -> byColumn(map, column, visibilitySetter(visibilityMap)));
    IntStream.range(0, map.columns()).forEach(column -> byColumnReversed(map, column, visibilitySetter(visibilityMap)));

    return visibilityMap.values().filter(value -> value > 0).count();
  }

  private IntMapEntryConsumer visibilitySetter(final MutableIntMap visibilityMap) {
    final AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);

    return (row, column, value) -> Optional.of(max.getAndAccumulate(value, Math::max))
        .filter(currentMax -> value > currentMax)
        .ifPresent(ignored -> visibilityMap.setValueAt(row, column, 1));
  }

  public long solve2() {
    return IntStream.range(0, map.rows())
        .mapToLong(row -> byRow(map, row, this::scenicScoreFun).max().orElseThrow())
        .max()
        .orElseThrow();
  }

  private long scenicScoreFun(final int row, final int column, final int value) {
    return viewDistance(valuesUp(map, row, column), value) *
        viewDistance(valuesLeft(map, row, column), value) *
        viewDistance(valuesDown(map, row, column), value) *
        viewDistance(valuesRight(map, row, column), value);
  }

  private long viewDistance(final IntStream values, int height) {
    var count = 0;

    final var iterator = values.iterator();

    while (iterator.hasNext()) {
      count++;
      if (iterator.nextInt() >= height) {
        break;
      }
    }

    return count;
  }
}
