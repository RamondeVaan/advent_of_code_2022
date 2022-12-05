package nl.ramondevaan.aoc2022.day05;

import nl.ramondevaan.aoc2022.util.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;

public class StackParser implements Parser<List<String>, List<List<Character>>> {
  @Override
  public List<List<Character>> parse(final List<String> toParse) {
    final var numberOfStacks = Math.toIntExact(Arrays.stream(toParse.get(toParse.size() - 1).split("\\s+"))
        .filter(not(String::isBlank)).count());

    return IntStream.range(0, numberOfStacks).mapToObj(stackIndex -> readStack(toParse, stackIndex)).toList();
  }

  private List<Character> readStack(final List<String> toParse, final int stackIndex) {
    final var charIndex = stackIndex * 4 + 1;

    return IntStream.range(0, toParse.size() - 1)
        .mapToObj(toParse::get)
        .filter(line -> line.length() > charIndex)
        .map(line -> line.charAt(charIndex))
        .filter(Character::isAlphabetic)
        .toList();
  }
}
