package nl.ramondevaan.aoc2022.day23;

import nl.ramondevaan.aoc2022.util.Cycle;

import java.util.*;
import java.util.stream.IntStream;

public class Day23 {

  private static final List<Consideration> CONSIDERATIONS = List.of(
      new Consideration(Direction.NORTH, Set.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_EAST)),
      new Consideration(Direction.SOUTH, Set.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_EAST)),
      new Consideration(Direction.WEST, Set.of(Direction.WEST, Direction.NORTH_WEST, Direction.SOUTH_WEST)),
      new Consideration(Direction.EAST, Set.of(Direction.EAST, Direction.NORTH_EAST, Direction.SOUTH_EAST))
  );
  private static final List<List<Consideration>> CONSIDERATION_ROTATIONS = IntStream.range(0, CONSIDERATIONS.size())
      .mapToObj(i -> {
        final var list = new ArrayList<>(CONSIDERATIONS);
        Collections.rotate(list, -i);
        return Collections.unmodifiableList(list);
      })
      .toList();

  private final Set<Coordinate> coordinates;

  public Day23(final List<String> lines) {
    final var parser = new CoordinateParser();
    this.coordinates = parser.parse(lines);
  }

  public long solve1() {
    final int rounds = 10;
    final var considerationCycle = new Cycle<>(CONSIDERATION_ROTATIONS);
    var positions = Set.copyOf(coordinates);

    for (int round = 0; round < rounds; round++) {
      positions = next(positions, considerationCycle.next()).positions;
    }

    return emptyTiles(positions);
  }

  public long solve2() {
    final var considerationCycle = new Cycle<>(CONSIDERATION_ROTATIONS);
    var positions = Set.copyOf(coordinates);

    for (int round = 1; true; round++) {
      final var result = next(positions, considerationCycle.next());
      if (result.moved == 0) {
        return round;
      }
      positions = result.positions;
    }
  }

  private Result next(final Set<Coordinate> positions, final List<Consideration> considerations) {
    final var proposals = new HashSet<Coordinate>(coordinates.size());
    var moved = 0;

    coordinate:
    for (final var coordinate : positions) {
      if (hasNeighbors(positions, coordinate)) {
        consideration:
        for (final var consideration : considerations) {
          for (final var direction : consideration.checkDirections) {
            if (positions.contains(coordinate.neighbor(direction))) {
              continue consideration;
            }
          }
          final var proposal = coordinate.neighbor(consideration.proposal);
          if (proposals.remove(proposal)) {
            proposals.add(proposal.neighbor(consideration.proposal));
            proposals.add(coordinate);
            moved--;
          } else {
            proposals.add(proposal);
            moved++;
          }
          continue coordinate;
        }
      }
      proposals.add(coordinate);
    }

    return new Result(moved, Collections.unmodifiableSet(proposals));
  }

  private boolean hasNeighbors(final Set<Coordinate> positions, final Coordinate coordinate) {
    for (final Direction value : Direction.values()) {
      if (positions.contains(coordinate.neighbor(value))) {
        return true;
      }
    }

    return false;
  }

  private long emptyTiles(final Set<Coordinate> positions) {
    var rowMin = Integer.MAX_VALUE;
    var rowMax = Integer.MIN_VALUE;
    var columnMin = Integer.MAX_VALUE;
    var columnMax = Integer.MIN_VALUE;

    for (final var position : positions) {
      rowMin = Math.min(position.row, rowMin);
      rowMax = Math.max(position.row, rowMax);
      columnMin = Math.min(position.column, columnMin);
      columnMax = Math.max(position.column, columnMax);
    }

    final long size = (long) (rowMax - rowMin + 1) * (columnMax - columnMin + 1);
    return size - positions.size();
  }
}
