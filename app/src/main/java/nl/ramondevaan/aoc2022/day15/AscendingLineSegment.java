package nl.ramondevaan.aoc2022.day15;

import nl.ramondevaan.aoc2022.util.Position;

import java.util.ArrayList;
import java.util.List;

public class AscendingLineSegment implements LineSegment {
  public final int x;
  public final int y;
  public final int originY;
  public final int length;

  public AscendingLineSegment(final int x, final int y, final int length) {
    this.x = x;
    this.y = y;
    this.length = length;
    this.originY = y - x;
  }

  @Override
  public List<Position> getBoxIntersections(final int xMin, final int xMax, final int yMin, final int yMax) {
    final var positions = new ArrayList<Position>();

    final var xIsMinDiff = xMin - x;
    if (0 <= xIsMinDiff && xIsMinDiff < length) {
      positions.add(new Position(xMin, y + xIsMinDiff));
    }

    final var xIsMaxDiff = xMax - x;
    if (0 <= xIsMaxDiff && xIsMaxDiff < length) {
      positions.add(new Position(xMax, y + xIsMaxDiff));
    }

    final var yIsMinDiff = yMin - y;
    if (0 <= yIsMinDiff && yIsMinDiff < length) {
      positions.add(new Position(x + yIsMinDiff, yMin));
    }

    final var yIsMaxDiff = yMax - y;
    if (0 <= yIsMaxDiff && yIsMaxDiff < length) {
      positions.add(new Position(x + yIsMaxDiff, yMax));
    }

    return positions;
  }

  public Position getIntersection(final DescendingLineSegment descendingLineSegment) {
    final var diff = descendingLineSegment.originY - originY;
    if (diff % 2 != 0) {
      return null;
    }

    final var x = diff / 2;
    final var y = (descendingLineSegment.originY + originY) / 2;

    if (!contains(x, y) || !descendingLineSegment.contains(x, y)) {
      return null;
    }

    return new Position(x, y);
  }

  @Override
  public boolean contains(final int x, final int y) {
    final var xDiff = x - this.x;
    final var yDiff = y - this.y;

    return 0 <= xDiff && xDiff < length && 0 <= yDiff && yDiff < length;
  }
}
