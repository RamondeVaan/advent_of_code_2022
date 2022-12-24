package nl.ramondevaan.aoc2022.day23;

import nl.ramondevaan.aoc2022.util.Parser;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoordinateParser implements Parser<List<String>, Set<Integer>> {
  @Override
  public Set<Integer> parse(final List<String> toParse) {
    final var rows = toParse.size();
    final var columns = toParse.get(0).length();
    final var set = new HashSet<Integer>(rows * columns);

    for (int row = 0; row < toParse.size(); row++) {
      final var chars = toParse.get(row).toCharArray();
      for (int column = 0; column < chars.length; column++) {
        switch (chars[column]) {
          case '#' -> set.add(((row + 1000) * 2000) + column + 1000);
          case '.' -> {}
          default -> throw new IllegalArgumentException();
        }
      }
    }

    int r = ((50 + 0x8000) << 16) + 0x8000;
    System.out.println(Integer.toBinaryString(r));
    System.out.println(Integer.toBinaryString(r >>> 16));
    System.out.println(Integer.toBinaryString(-1 << 10));
    System.out.println(Integer.toBinaryString(-(1 << 10)));
    System.out.println(((r + Direction.NORTH.offset) >>> 16) - (0x8000));
    System.out.println(((r + Direction.EAST.offset) >>> 16) - (0x8000));
    System.out.println(((r + Direction.SOUTH.offset) >>> 16) - (0x8000));
    System.out.println(((r + Direction.WEST.offset) >>> 16) - (0x8000));
    System.out.println(((r + Direction.NORTH_EAST.offset) >>> 16) - (0x8000));
    System.out.println(((r + Direction.NORTH_WEST.offset) >>> 16) - (0x8000));
    System.out.println(((r + Direction.SOUTH_WEST.offset) >>> 16) - (0x8000));
    System.out.println(((r + Direction.SOUTH_EAST.offset) >>> 16) - (0x8000));

    return Collections.unmodifiableSet(set);
  }
}
