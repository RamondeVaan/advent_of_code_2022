package nl.ramondevaan.aoc2022.day18;

import nl.ramondevaan.aoc2022.util.Parser;
import nl.ramondevaan.aoc2022.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PositionsParser implements Parser<List<String>, Positions> {
  @Override
  public Positions parse(final List<String> toParse) {
    final var positions = new ArrayList<Position>(toParse.size());

    byte xMin = Byte.MAX_VALUE, yMin = Byte.MAX_VALUE, zMin = Byte.MAX_VALUE;
    byte xMax = Byte.MIN_VALUE, yMax = Byte.MIN_VALUE, zMax = Byte.MIN_VALUE;

    for (final var line : toParse) {
      final var parser = new StringIteratorParser(line);
      final var x = parser.parseByte();
      xMin = x < xMin ? x : xMin;
      xMax = x > xMax ? x : xMax;
      parser.consume(',');
      final var y = parser.parseByte();
      yMin = y < yMin ? y : yMin;
      yMax = y > yMax ? y : yMax;
      parser.consume(',');
      final var z = parser.parseByte();
      zMin = z < zMin ? z : zMin;
      zMax = z > zMax ? z : zMax;
      positions.add(new Position(x, y, z));
    }

    return new Positions(Collections.unmodifiableList(positions), xMin, xMax, yMin, yMax, zMin, zMax);
  }
}
