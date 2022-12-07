package nl.ramondevaan.aoc2022.day06;

import nl.ramondevaan.aoc2022.util.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class UniqueCharWindowConsumer implements SlidingWindow.WindowConsumer<Character, Integer> {
  private final Map<Character, Integer> counts;

  public UniqueCharWindowConsumer() {
    this.counts = new HashMap<>();
  }

  @Override
  public void initializeAdd(Character add) {
    counts.merge(add, 1, Integer::sum);
  }

  @Override
  public Integer getValue() {
    return counts.size();
  }

  @Override
  public Integer slide(Character remove, Character add) {
    counts.compute(remove, UniqueCharWindowConsumer::decrementRemoveWhenZero);
    counts.merge(add, 1, Integer::sum);
    return counts.size();
  }

  private static Integer decrementRemoveWhenZero(final Character character, final Integer count) {
    final var ret = count - 1;

    if (ret == 0) {
      return null;
    }

    return ret;
  }
}
