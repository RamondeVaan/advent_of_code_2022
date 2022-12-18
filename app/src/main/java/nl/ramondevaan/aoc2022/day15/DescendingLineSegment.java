package nl.ramondevaan.aoc2022.day15;

import nl.ramondevaan.aoc2022.util.Position;

import java.util.ArrayList;
import java.util.List;

public class DescendingLineSegment implements LineSegment {
  public final int x;
  public final int y;
  public final int originY;
  public final int length;

  public DescendingLineSegment(final int x, final int y, final int length) {
    this.x = x;
    this.y = y;
    this.length = length;
    this.originY = y + x;
  }

  @Override
  public List<Position> getBoxIntersections(final int xMin, final int xMax, final int yMin, final int yMax) {
    final var positions = new ArrayList<Position>();

    final var xIsMinDiff = xMin - x;
    if (0 <= xIsMinDiff && xIsMinDiff < length) {
      positions.add(new Position(xMin, y - xIsMinDiff));
    }

    final var xIsMaxDiff = xMax - x;
    if (0 <= xIsMaxDiff && xIsMaxDiff < length) {
      positions.add(new Position(xMax, y - xIsMaxDiff));
    }

    final var yIsMinDiff = yMin - y;
    if (0 <= yIsMinDiff && yIsMinDiff < length) {
      positions.add(new Position(x - yIsMinDiff, yMin));
    }

    final var yIsMaxDiff = yMax - y;
    if (0 <= yIsMaxDiff && yIsMaxDiff < length) {
      positions.add(new Position(x - yIsMaxDiff, yMax));
    }

    return positions;
  }

  @Override
  public boolean contains(final int x, final int y) {
    final var xDiff = x - this.x;
    final var yDiff = this.y - y;

    return 0 <= xDiff && xDiff < length && 0 <= yDiff && yDiff < length;
  }
}
