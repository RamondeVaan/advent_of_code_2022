package nl.ramondevaan.aoc2022.day04;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public class Day04 {

  private final List<ImmutablePair<Range<Integer>, Range<Integer>>> sectionPairs;

  public Day04(final List<String> lines) {
    final var parser = new SectionPairParser();

    this.sectionPairs = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    return sectionPairs.stream()
        .filter(pair -> pair.left.containsRange(pair.right) || pair.right.containsRange(pair.left))
        .count();
  }

  public long solve2() {
    return sectionPairs.stream()
        .filter(pair -> pair.left.isOverlappedBy(pair.right))
        .count();
  }
}
