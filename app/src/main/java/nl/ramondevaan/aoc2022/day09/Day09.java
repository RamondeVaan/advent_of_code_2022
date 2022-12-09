package nl.ramondevaan.aoc2022.day09;

import nl.ramondevaan.aoc2022.util.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Integer.signum;
import static java.util.stream.Collectors.toCollection;

public class Day09 {

  private final List<Motion> motions;

  public Day09(final List<String> lines) {
    final var parser = new MotionParser();
    this.motions = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    return solve(2);
  }

  public long solve2() {
    return solve(10);
  }

  private long solve(final int ropeSize) {
    final var rope = Stream.generate(Coordinate::new).limit(ropeSize).collect(toCollection(ArrayList::new));

    final var coordinateStream = motions.stream()
        .flatMap(motion -> Stream.generate(motion::direction).limit(motion.distance()))
        .map(direction -> moveAndGetTailCoordinate(rope, direction));

    return Stream.concat(Stream.of(new Coordinate()), coordinateStream).distinct().count();
  }

  private Coordinate moveAndGetTailCoordinate(final List<Coordinate> rope, final Direction direction) {
    var last = offset(rope.get(0), direction);
    rope.set(0, last);
    for (int j = 1; j < rope.size(); j++) {
      rope.set(j, last = follow(rope.get(j), last));
    }
    return last;
  }

  private Coordinate offset(final Coordinate from, final Direction direction) {
    return new Coordinate(from.row() + direction.getRowOffset(), from.column() + direction.getColumnOffset());
  }

  private Coordinate follow(final Coordinate from, final Coordinate to) {
    final int rowDifference = to.row() - from.row();
    final int columnDifferenceDifference = to.column() - from.column();

    if (Math.abs(rowDifference) <= 1 && Math.abs(columnDifferenceDifference) <= 1) {
      return from;
    }

    return Coordinate.of(from.row() + signum(rowDifference), from.column() + signum(columnDifferenceDifference));
  }
}
