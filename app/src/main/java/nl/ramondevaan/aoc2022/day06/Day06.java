package nl.ramondevaan.aoc2022.day06;

import java.util.List;

public class Day06 {

  private final String datastreamBuffer;

  public Day06(final List<String> lines) {
    this.datastreamBuffer = lines.get(0);
  }

  public long solve1() {
    return solve(4);
  }

  public long solve2() {
    return solve(14);
  }

  public long solve(int window) {
    for (int from = 0, to = window; to <= datastreamBuffer.length(); from++, to++) {
      if (datastreamBuffer.substring(from, to).chars().distinct().count() == window) {
        return to;
      }
    }

    throw new IllegalArgumentException();
  }
}
