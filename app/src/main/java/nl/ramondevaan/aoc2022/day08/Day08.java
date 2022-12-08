package nl.ramondevaan.aoc2022.day08;

import com.google.common.collect.Streams;
import nl.ramondevaan.aoc2022.util.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Day08 {

  private final IntMap map;

  public Day08(final List<String> lines) {
    final var parser = new IntMapParser();
    this.map = parser.parse(lines);
  }

  public long solve1() {
    final var minCoordinate = Coordinate.of(0, 0);
    final var maxCoordinate = Coordinate.of(map.rows() - 1, map.columns() - 1);

    final var leftToRight = coordinateStream(minCoordinate, Direction.DOWN)
        .map(coordinate -> coordinateStream(coordinate, Direction.RIGHT));
    final var topToBottom = coordinateStream(minCoordinate, Direction.RIGHT)
        .map(coordinate -> coordinateStream(coordinate, Direction.DOWN));
    final var rightToLeft = coordinateStream(maxCoordinate, Direction.UP)
        .map(coordinate -> coordinateStream(coordinate, Direction.LEFT));
    final var bottomToTop = coordinateStream(maxCoordinate, Direction.LEFT)
        .map(coordinate -> coordinateStream(coordinate, Direction.UP));

    final var visibilityMap = new MutableIntMap(map.rows(), map.columns());

    Streams.concat(leftToRight, topToBottom, rightToLeft, bottomToTop)
        .forEach(stream -> setVisible(stream, visibilityMap));

    return visibilityMap.values().filter(value -> value > 0).count();
  }

  private void setVisible(final Stream<Coordinate> coordinates, final MutableIntMap visibilityMap) {
    final var max = new AtomicInteger(Integer.MIN_VALUE);

    coordinates.map(map::withValueAt).forEachOrdered(entry -> {
      if (entry.value() > max.getAndAccumulate(entry.value(), Math::max)) {
        visibilityMap.setValueAt(entry.coordinate(), 1);
      }
    });
  }

  public long solve2() {
    return map.keys().mapToLong(this::scenicScore).max().orElseThrow();
  }

  private long scenicScore(final Coordinate coordinate) {
    final var value = map.valueAt(coordinate);
    return Flux.fromArray(Direction.values())
        .map(direction -> coordinateStream(coordinate, direction).skip(1).mapToInt(map::valueAt))
        .map(values -> viewDistance(values, value))
        .takeUntil(viewDistance -> viewDistance == 0)
        .toStream()
        .mapToLong(Long::longValue)
        .reduce((a, b) -> a * b)
        .orElse(0L);
  }

  private Stream<Coordinate> coordinateStream(final Coordinate from, final Direction direction) {
    return Stream.iterate(from, map::contains, offset(direction));
  }

  private static UnaryOperator<Coordinate> offset(final Direction direction) {
    return from -> Coordinate.of(from.row() + direction.getRowOffset(), from.column() + direction.getColumnOffset());
  }

  private long viewDistance(final IntStream values, int height) {
    return Flux.fromStream(values.boxed()).takeUntil(i -> i >= height).toStream().count();
  }
}
