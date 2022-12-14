package nl.ramondevaan.aoc2022.day14;

import nl.ramondevaan.aoc2022.util.MutableIntMap;

import java.util.List;

public class Day14 {
  private final static int ROCK = 1;
  private final static int SAND = 2;

  private final List<Path> paths;
  private final int maxDepth;

  public Day14(final List<String> lines) {
    final var pathParser = new PathParser();
    this.paths = lines.stream().map(pathParser::parse).toList();

    var maxDepth = Integer.MIN_VALUE;

    for (final var path : paths) {
      for (final var point : path.getPoints()) {
        maxDepth = Math.max(point.row(), maxDepth);
      }
    }

    this.maxDepth = maxDepth;
  }

  public long solve1() {
    return solve(initializeMap(maxDepth + 1));
  }

  public long solve2() {
    return solve(infiniteMap());
  }

  private MutableIntMap infiniteMap() {
    final var height = maxDepth + 3;
    final var mutableMap = initializeMap(height);
    final var bottomRow = mutableMap.rows() - 1;

    for (int column = 0; column < mutableMap.columns(); column++) {
      mutableMap.setValueAt(bottomRow, column, 1);
    }

    return mutableMap;
  }

  private long solve(final MutableIntMap mutableMap) {
    final var center = mutableMap.columns() / 2;
    var count = 0;

    while (mutableMap.valueAt(0, center) == 0 && dropSand(mutableMap, center)) {
      count++;
    }

    return count;
  }

  private boolean dropSand(final MutableIntMap mutableMap, final int fromColumn) {
    int row = 0, column = fromColumn, nextRow = 0, nextColumn = fromColumn;

    while (mutableMap.contains(nextRow, nextColumn)) {
      if (mutableMap.valueAt(nextRow, nextColumn) != 0) {
        if (nextColumn > 0 && mutableMap.valueAt(nextRow, --nextColumn) != 0 &&
            nextColumn < (mutableMap.columns() - 1) && mutableMap.valueAt(nextRow, nextColumn += 2) != 0) {
          mutableMap.setValueAt(row, column, SAND);
          return true;
        }
      }
      column = nextColumn;
      row = nextRow++;
    }

    return false;
  }

  private MutableIntMap initializeMap(final int rows) {
    final var width = 2 * rows + 1;
    final var columnOffset = 500 - rows;

    final var map = new MutableIntMap(rows, width);

    for (final var path : paths) {
      tracePath(map, path, columnOffset);
    }

    return map;
  }

  private void tracePath(final MutableIntMap map, final Path path, final int minColumn) {
    final var points = path.getPoints();
    var last = points.get(0);

    for (int i = 1; i < points.size(); i++) {
      final var current = points.get(i);
      final var lastColumn = last.column() - minColumn;
      final var rowOffset = Integer.signum(current.row() - last.row());
      final var columnOffset = Integer.signum(current.column() - last.column());

      for (int row = last.row(); row != current.row(); row += rowOffset) {
        if (map.contains(row, lastColumn)) {
          map.setValueAt(row, lastColumn, ROCK);
        }
      }

      final var columnTarget = current.column() - minColumn;
      for (int column = lastColumn; column != columnTarget; column += columnOffset) {
        if (map.contains(current.row(), column)) {
          map.setValueAt(current.row(), column, ROCK);
        }
      }

      last = current;
    }

    map.setValueAt(last.row(), last.column() - minColumn, ROCK);
  }
}
