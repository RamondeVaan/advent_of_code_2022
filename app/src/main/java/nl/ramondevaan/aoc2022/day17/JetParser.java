package nl.ramondevaan.aoc2022.day17;

import nl.ramondevaan.aoc2022.util.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JetParser implements Parser<List<String>, List<Jet>> {
  @Override
  public List<Jet> parse(final List<String> toParse) {
    if (toParse.size() == 0) {
      return Collections.emptyList();
    }
    char[] characters = toParse.get(0).toCharArray();
    final var jets = new ArrayList<Jet>(characters.length);

    for (char character : characters) {
      jets.add(switch (character) {
        case '>' -> Jet.RIGHT;
        case '<' -> Jet.LEFT;
        default -> throw new IllegalArgumentException();
      });
    }

    return Collections.unmodifiableList(jets);
  }
}
