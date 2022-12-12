package nl.ramondevaan.aoc2022.day12;

import nl.ramondevaan.aoc2022.util.Coordinate;
import nl.ramondevaan.aoc2022.util.IntMap;
import nl.ramondevaan.aoc2022.util.Parser;

import java.util.List;

public class HeightMapParser implements Parser<List<String>, HeightMap> {
  @Override
  public HeightMap parse(List<String> toParse) {
    final var rows = toParse.size();
    final var columns = toParse.size() == 0 ? 0 : toParse.get(0).length();

    final var builder = IntMap.builder(rows, columns);
    Coordinate start = null;
    Coordinate end = null;

    for (int row = 0; row < rows; row++) {
      final var line = toParse.get(row);
      final char[] chars = line.toCharArray();
      if (chars.length != columns) {
        throw new IllegalArgumentException("Inconsistent number of columns");
      }
      for (int column = 0; column < columns; column++) {
        if (chars[column] == 'S') {
          builder.set(row, column, 0);
          start = Coordinate.of(row, column);
        } else if (chars[column] == 'E') {
          builder.set(row, column, 25);
          end = Coordinate.of(row, column);
        } else {
          builder.set(row, column, chars[column] - 'a');
        }
      }
    }
    if (start == null) {
      throw new IllegalArgumentException("Could not find start");
    }
    if (end == null) {
      throw new IllegalArgumentException("Could not find end");
    }

    final var map = builder.build();

    return new HeightMap(map, start, end);
  }
}
