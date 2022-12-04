package nl.ramondevaan.aoc2022.day04;

import nl.ramondevaan.aoc2022.util.Parser;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class SectionPairParser implements Parser<String, ImmutablePair<Range<Integer>, Range<Integer>>> {
  @Override
  public ImmutablePair<Range<Integer>, Range<Integer>> parse(final String toParse) {
    final var pairSeparatorIndex = toParse.indexOf(',');
    return ImmutablePair.of(parseRange(toParse.substring(0, pairSeparatorIndex)),
        parseRange(toParse.substring(pairSeparatorIndex + 1)));
  }

  private Range<Integer> parseRange(final String toParse) {
    final var rangeSeparatorIndex = toParse.indexOf('-');
    return Range.between(Integer.parseInt(toParse.substring(0, rangeSeparatorIndex)),
        Integer.parseInt(toParse.substring(rangeSeparatorIndex + 1)));
  }
}
