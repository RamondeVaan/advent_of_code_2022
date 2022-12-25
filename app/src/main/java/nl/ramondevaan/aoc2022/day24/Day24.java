package nl.ramondevaan.aoc2022.day24;

import nl.ramondevaan.aoc2022.util.IntMap;

import java.util.List;

public class Day24 {

  private final static int EMPTY = 0;
  private final static int PLAYER = 32;
  private final IntMap map;
  private final int startRow;
  private final int startColumn;
  private final int endRow;
  private final int endColumn;

  public Day24(final List<String> lines) {
    final var parser = new BasinParser();
    final var basin = parser.parse(lines);
    this.map = basin.map;
    this.startRow = basin.startRow;
    this.startColumn = basin.startColumn;
    this.endRow = basin.endRow;
    this.endColumn = basin.endColumn;
  }

  public long solve1() {
    return solve(map.toBuilder(), startRow, startColumn, endRow, endColumn).rounds;
  }

  public long solve2() {
    final var toEnd = solve(map.toBuilder(), startRow, startColumn, endRow, endColumn);
    final var backToStart = solve(clearPlayer(toEnd.map), endRow, endColumn, startRow, startColumn);
    final var backToEnd = solve(clearPlayer(backToStart.map), startRow, startColumn, endRow, endColumn);

    return toEnd.rounds + backToStart.rounds + backToEnd.rounds;
  }

  private Result solve(final IntMap.Builder map, final int startRow, final int startColumn,
                       final int targetRow, final int targetColumn) {
    var currentMap = map.set(startRow, startColumn, PLAYER).build();

    for (int round = 0; true; round++) {
      if (currentMap.valueAt(targetRow, targetColumn) == PLAYER) {
        return new Result(round, currentMap);
      }
      var builder = next(currentMap);

      for (int row = 0; row < map.rows; row++) {
        for (int column = 0; column < map.columns; column++) {
          if (currentMap.valueAt(row, column) == PLAYER) {
            for (final var move : Move.values()) {
              final var nextRow = row + move.rowOffset;
              final var nextColumn = column + move.columnOffset;
              if (0 <= nextRow && nextRow < builder.rows && 0 <= nextColumn && nextColumn < builder.columns &&
                  builder.get(nextRow, nextColumn) == EMPTY) {
                builder.flag(nextRow, nextColumn, PLAYER);
              }
            }
          }
        }
      }

      currentMap = builder.build();
    }
  }

  private IntMap.Builder next(final IntMap map) {
    final var next = IntMap.builder(map.rows(), map.columns());

    for (int row = 0; row < map.rows(); row++) {
      for (int column = 0; column < map.columns(); column++) {
        final var value = map.valueAt(row, column);
        if (value == 1) {
          next.set(row, column, 1);
          continue;
        }
        if ((value & Blizzard.RIGHT.flag) != 0) {
          Blizzard.RIGHT.setNext(map, next, row, column);
        }
        if ((value & Blizzard.LEFT.flag) != 0) {
          Blizzard.LEFT.setNext(map, next, row, column);
        }
        if ((value & Blizzard.UP.flag) != 0) {
          Blizzard.UP.setNext(map, next, row, column);

        }
        if ((value & Blizzard.DOWN.flag) != 0) {
          Blizzard.DOWN.setNext(map, next, row, column);
        }
      }
    }

    return next;
  }

  private IntMap.Builder clearPlayer(final IntMap map) {
    final var builder = map.toBuilder();

    for (int row = 0; row < map.rows(); row++) {
      for (int column = 0; column < map.columns(); column++) {
        if (builder.get(row, column) == PLAYER) {
          builder.set(row, column, EMPTY);
        }
      }
    }

    return builder;
  }
}
