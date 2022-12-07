package nl.ramondevaan.aoc2022.day06;

import nl.ramondevaan.aoc2022.util.SlidingWindow.Window;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

import static nl.ramondevaan.aoc2022.util.SlidingWindow.windowStream;

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

  public long solve(int windowSize) {
    final var chars = Arrays.asList(ArrayUtils.toObject(datastreamBuffer.toCharArray()));
    return windowStream(chars, windowSize, new UniqueCharWindowConsumer())
        .filter(window -> window.value() == windowSize)
        .map(Window::endIndexExclusive)
        .findFirst().orElseThrow();
  }
}
