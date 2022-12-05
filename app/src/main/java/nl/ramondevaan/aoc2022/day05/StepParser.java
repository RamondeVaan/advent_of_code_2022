package nl.ramondevaan.aoc2022.day05;

import nl.ramondevaan.aoc2022.util.Parser;

public class StepParser implements Parser<String, Step> {
  @Override
  public Step parse(String toParse) {
    final var fromStringIndex = toParse.indexOf("from", 7);
    final var toStringIndex = toParse.indexOf("to", fromStringIndex + 7);

    final var crates = Integer.parseInt(toParse.substring(5, fromStringIndex - 1));
    final var fromIndex = Integer.parseInt(toParse.substring(fromStringIndex + 5, toStringIndex - 1)) - 1;
    final var toIndex = Integer.parseInt(toParse.substring(toStringIndex + 3)) - 1;

    return new Step(crates, fromIndex, toIndex);
  }
}
