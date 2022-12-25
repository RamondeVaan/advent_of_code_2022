package nl.ramondevaan.aoc2022.day24;

import nl.ramondevaan.aoc2022.util.IntMap;
import nl.ramondevaan.aoc2022.util.Parser;

import java.util.List;
import java.util.stream.IntStream;

public class BasinParser implements Parser<List<String>, Basin> {
  @Override
  public Basin parse(final List<String> toParse) {
    final var rows = toParse.size();
    final var columns = toParse.get(0).length();
    final var builder = IntMap.builder(rows, columns);

    for (int row = 0; row < rows; row++) {
      final var chars = toParse.get(row).toCharArray();
      for (int column = 0; column < columns; column++) {
        switch (chars[column]) {
          case '#' -> builder.set(row, column, 1);
          case '>' -> builder.set(row, column, Blizzard.RIGHT.flag);
          case '<' -> builder.set(row, column, Blizzard.LEFT.flag);
          case '^' -> builder.set(row, column, Blizzard.UP.flag);
          case 'v' -> builder.set(row, column, Blizzard.DOWN.flag);
          case '.' -> {}
          default -> throw new IllegalArgumentException();
        }
      }
    }

    final var map = builder.build();
    final var startColumn = IntStream.range(0, columns).filter(column -> map.valueAt(0, column) == 0)
        .findFirst().orElseThrow();
    final var endColumn = IntStream.range(0, columns).filter(column -> map.valueAt(rows - 1, column) == 0)
        .findFirst().orElseThrow();

    return new Basin(map, 0, startColumn, rows - 1, endColumn);
  }
}
