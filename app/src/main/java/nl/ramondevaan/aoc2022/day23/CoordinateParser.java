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

    return Collections.unmodifiableSet(set);
  }
}
