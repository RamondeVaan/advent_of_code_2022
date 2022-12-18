package nl.ramondevaan.aoc2022.day15;

import nl.ramondevaan.aoc2022.util.Position;

import java.util.List;

public interface LineSegment {
  List<Position> getBoxIntersections(final int xMin, final int xMax, final int yMin, final int yMax);

  boolean contains(final int x, final int y);
}
