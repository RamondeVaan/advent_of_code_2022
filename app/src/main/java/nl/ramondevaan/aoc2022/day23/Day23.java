package nl.ramondevaan.aoc2022.day23;

import com.google.common.primitives.Ints;

import java.util.List;
import java.util.Set;

import static nl.ramondevaan.aoc2022.day23.Coordinate.OFFSET;
import static nl.ramondevaan.aoc2022.day23.Coordinate.ROW_MULTIPLIER;

public class Day23 {

  private final Set<Integer> coordinates;

  public Day23(final List<String> lines) {
    final var parser = new CoordinateParser();
    this.coordinates = parser.parse(lines);
  }

  public long solve1() {
    return emptyTiles(solve(10).positions);
  }

  public long solve2() {
    return solve(Integer.MAX_VALUE).rounds;
  }

  private boolean[] initializePositions() {
    final var ret = new boolean[ROW_MULTIPLIER * ROW_MULTIPLIER + OFFSET];

    for (final var position : coordinates) {
      ret[position] = true;
    }

    return ret;
  }

  private Result solve(final int rounds) {
    var positions = coordinates.stream().mapToInt(Integer::intValue).toArray();
    var considerations = considerations();
    var considerationSize = considerations.length / 2;
    var newPositions = new int[positions.length];
    var set = initializePositions();
    var proposals = new int[set.length];
    int round = 0;
    int moved = 1;

    for (; round < rounds && moved != 0; round++) {
      moved = 0;

      coordinate:
      for (int i = 0; i < positions.length; i++) {
        final var coordinate = positions[i];
        final int[] neighbors = neighbors(coordinate);
        if (hasNeighbors(set, neighbors)) {
          final var roundModConsiderations = round % considerationSize;
          final var considerationTarget = roundModConsiderations + considerationSize;
          consideration:
          for (var considerationIndex = roundModConsiderations; considerationIndex < considerationTarget; considerationIndex++) {
            final var consideration = considerations[considerationIndex];
            for (final var neighborIndex : consideration) {
              if (set[neighbors[neighborIndex]]) {
                continue consideration;
              }
            }
            final var proposal = neighbors[consideration[0]];
            moved += handleProposal(positions, newPositions, proposals, i, coordinate, proposal) ? 1 : -1;
            continue coordinate;
          }
        }
        newPositions[i] = coordinate;
      }

      finishRound(positions, newPositions, set, proposals);
    }

    return new Result(round, Ints.asList(positions));
  }

  private static void finishRound(final int[] positions, final int[] newPositions, final boolean[] set, final int[] proposals) {
    for (int i = 0; i < positions.length; i++) {
      set[positions[i]] = false;
      set[positions[i] = newPositions[i]] = true;
      proposals[newPositions[i]] = 0;
    }
  }

  private static boolean handleProposal(final int[] positions, final int[] newPositions, final int[] proposals,
                                        final int i, final int coordinate, final int proposal) {
    int index;
    if ((index = proposals[proposal]) != 0) {
      index -= 1;
      newPositions[index] = positions[index];
      newPositions[i] = coordinate;
      proposals[proposal] = 0;
      return false;
    }
    newPositions[i] = proposal;
    proposals[proposal] = i + 1;
    return  true;
  }

  private boolean hasNeighbors(final boolean[] positions, final int[] neighbors) {
    for (final var neighbor : neighbors) {
      if (positions[neighbor]) {
        return true;
      }
    }

    return false;
  }

  private int[] neighbors(final int coordinate) {
    final var minMultiplier = coordinate - ROW_MULTIPLIER;
    final var plusMultiplier = coordinate + ROW_MULTIPLIER;

    return new int[] {
        minMultiplier - 1, // North-West
        minMultiplier, // North
        minMultiplier + 1, // North-East
        coordinate + 1, // East
        plusMultiplier + 1, // South-East
        plusMultiplier, // South
        plusMultiplier - 1, // South-West
        coordinate - 1, // West
    };
  }

  private int[][] considerations() {
    return new int[][] {
        {1, 0, 2}, // North
        {5, 4, 6}, // South
        {7, 6, 0}, // West
        {3, 2, 4}, // East
        {1, 0, 2}, // North
        {5, 4, 6}, // South
        {7, 6, 0}, // West
        {3, 2, 4}, // East
    };
  }

  private long emptyTiles(final List<Integer> positions) {
    var rowMin = Integer.MAX_VALUE;
    var rowMax = Integer.MIN_VALUE;
    var columnMin = Integer.MAX_VALUE;
    var columnMax = Integer.MIN_VALUE;

    for (final int position : positions) {
      final int row = position / ROW_MULTIPLIER;
      final int column = position % ROW_MULTIPLIER;

      rowMin = Math.min(row, rowMin);
      rowMax = Math.max(row, rowMax);
      columnMin = Math.min(column, columnMin);
      columnMax = Math.max(column, columnMax);
    }

    final long size = (long) (rowMax - rowMin + 1) * (columnMax - columnMin + 1);
    return size - coordinates.size();
  }
}
