package nl.ramondevaan.aoc2022.day09;

import nl.ramondevaan.aoc2022.util.Coordinate;

import java.util.ArrayList;
import java.util.HashSet;
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
    final var uniqueSet = new HashSet<Integer>();
    uniqueSet.add(0);

    for(final var motion : motions) {
      for (int i = 0; i < motion.distance(); i++) {
        final var coordinate = moveAndGetTailCoordinate(rope, motion.direction());
        uniqueSet.add((coordinate.row() << 16) | (coordinate.column() & 0xFFFF));
      }
    }

    return uniqueSet.size();
  }

  private Coordinate moveAndGetTailCoordinate(final List<Coordinate> rope, final Direction direction) {
    var last = offset(rope.get(0), direction);
    rope.set(0, last);
    for (int j = 1; j < rope.size(); j++) {
      final var current = rope.get(j);
      final int rowOffset = last.row() - current.row();
      final int columnOffset = last.column() - current.column();

      if (Math.abs(rowOffset) > 1 || Math.abs(columnOffset) > 1) {
        rope.set(j, last = Coordinate.of(current.row() + signum(rowOffset), current.column() + signum(columnOffset)));
        continue;
      }
      last = current;
    }
    return last;
  }

  private Coordinate offset(final Coordinate from, final Direction direction) {
    return new Coordinate(from.row() + direction.getRowOffset(), from.column() + direction.getColumnOffset());
  }
}
